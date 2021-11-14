package task2.hashTable;

import java.util.LinkedList;

public class HashTable <Key, Value>{

    static public class Item<Key, Value>
    {
        final private Key key;
        private Value value;
        ///------------------
        public Item(Key key, Value value){
            this.key = key;
            this.value = value;
        }

        public Item(Item<Key, Value> item){
            this(item.getKey(), item.getValue());
        }


        public Value getValue(){
            return value;
        }

        public Key getKey(){
            return key;
        }

        public void setValue(Value value){
            this.value = value;
        }

        public boolean isSameKey(Key key){
            return this.key == key;
        }

        @Override
        public String toString(){
            return key.toString() + " = " + value.toString();
        }
    }
    //-------------------------------------------------------------------
    private LinkedList<Item<Key,Value>> []  mas;
    private int tableSize;
    private int fullness;


    private boolean isNeedResize(){
        return fullness >= tableSize/2;
    }
    ///    ----------------------
    public HashTable(int tableSize){
        this.tableSize = tableSize;
        this.mas = (LinkedList<Item<Key, Value>> [])new LinkedList[this.tableSize];
        this.fullness = 0;
    }
    public HashTable(){
        this(100);
    }

    public int hash(Key key){
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
    public Value get(Key key){
        int index = hash(key) % tableSize;
        if(mas[index] == null)
           return null;
        for (Item<Key, Value> cur: mas[index]){
           if(cur.isSameKey(key))
               return cur.getValue();
        }
        return null;
    }

    public boolean find(Key key){
        int index = hash(key) % tableSize;
        if(mas[index] == null)
           return false;
        for (Item<Key, Value> cur: mas[index]) {
           if(cur.isSameKey(key))
               return true;
        }
        return false;
    }
    public void add(Key key, Value value){
        int index = hash(key) % tableSize;
        if(mas[index] == null){
           mas[index] = new LinkedList<> ();
           ++fullness;
        }
        for (Item<Key, Value> cur: mas[index]) {
           if(cur.isSameKey(key)){
               cur.setValue(value);
               return;
           }

        }
        mas[index].add(new Item<>(key, value));

        if(isNeedResize())
           resize();
    }

    public boolean remove(Key key){
        int index = hash(key) % tableSize;
        if(mas[index] == null)
           return false;
        for (Item<Key, Value> cur: mas[index]) {
           if(cur.isSameKey(key)) {
               mas[index].remove(cur);
               if(mas[index].size() == 0)
                   mas[index] = null;
               return true;
           }
        }
        return false;
    }
    public int getSize(){
    return tableSize;
    }

    public void resize(){
        LinkedList<Item<Key,Value>> []  oldMas = mas;
        this.mas = (LinkedList<Item<Key, Value>> [])new LinkedList[this.tableSize * 2];
        for(int i =0; i < tableSize; ++i){
           for (Item<Key, Value> cur: oldMas[i]) {
               add(cur.getKey(), cur.getValue());
           }
        }
        this.tableSize *=2;
    }

    public void clear(){
        for ( int k = 0; k < tableSize; k++ ) {
           if ( mas[k] == null )
               continue;
           for (Item<Key, Value> cur: mas[k])
               mas[k].remove(cur);
           mas[k] = null;
        }
        fullness = 0;
    }

    public int getFullness(){
        return fullness;
    }

    public boolean isEmpty(){
        return fullness == 0;
    }

    public Iterator iterator(){
        return new Iterator();
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if(isEmpty())
            sb.append("HashTable is empty\n");

        int elementIndex;
        for ( int k = 0; k < tableSize; k++ ) {
            if ( mas[k] == null )
                continue;
            elementIndex = 0;
            sb.append("[").append(k).append("] -> {");
            for ( Item<Key, Value> i : mas[k] ) {
                sb.append(i.toString());
                if ( elementIndex < mas[k].size() -1 ) {
                    sb.append(", ");
                }
                elementIndex++;
            }
            sb.append("}\n");
        }
        return sb.toString();
    }
    public class Iterator
    {
        private Item<Key, Value> item;

        private boolean checkNext(LinkedList<Item<Key, Value>> curMas){
            if(curMas.size() == 1)
                return false;
           int curNumber = 0;
           for(Item<Key, Value> cur : curMas)
           {
               if(cur.isSameKey(item.key))
                   break;
               ++curNumber;
           }
           return curNumber > curMas.size();
        }
        private Item<Key,Value> getNext(LinkedList<Item<Key, Value>> curMas){
            if(curMas.size() == 1)
                return null;

            boolean nextItem = false;
            for(Item<Key, Value> cur : curMas)
            {
                if(nextItem)
                    return cur;
                if(cur.isSameKey(item.key))
                    nextItem = true;

            }
            return null;
        }

        private boolean checkPrevious(LinkedList<Item<Key, Value>> curMas){
            if(curMas.size() == 1)
                return false;
            int curNumber = 0;
            for(Item<Key, Value> cur : curMas)
            {
                if(cur.isSameKey(item.key))
                    break;
                ++curNumber;
            }
            return curNumber > 0;
        }

        private Item<Key,Value> getPrevious(LinkedList<Item<Key, Value>> curMas){
            if(curMas.size() == 1)
                return null;

            Item<Key, Value> prev = null;
            for(Item<Key, Value> cur : curMas)
            {
                if(cur.isSameKey(item.key) && prev != null)
                    return prev;
                prev = cur;
            }
            return null;
        }

        //-----------------------------------------------------
        public Iterator(Item<Key, Value> item){
           this.item =  new Item<>(item);
        }
        public Iterator(){
            if(isEmpty()){
                this.item = null;
                return;
            }

            for(int i = 0; i < tableSize; ++i)
            {
                if(mas[i] == null)
                    continue;
                for(Item<Key, Value> cur : HashTable.this.mas[i])
                {
                    this.item =  new Item<>(cur);
                    return;
                }
            }
        }

        public boolean hasNext(){
            int index = hash(item.key) % tableSize;
            if(checkNext(mas[index]))
                return true;

            for(int i = index+1; i < tableSize; ++i)
            {
                if(mas[i] != null)
                    return true;
            }

            return false;
        }

        public boolean hasPrevious(){
            int index = hash(item.key) % tableSize;
            if(checkPrevious(mas[index]))
                return true;
            for(int i = index-1; i >=0; --i )
            {
                if(mas[i] != null)
                    return true;
            }
            return false;
        }

        public Iterator next(){
            int index = hash(item.key) % tableSize;
            Item<Key, Value> curItem = item;
            item = getNext(mas[index]);
            if(item == null){
                boolean hasNext = false;
                for(int i = index+1; i < tableSize; ++i)
                {
                    if(hasNext)
                        break;
                    if(mas[i] == null)
                        continue;
                    for(Item<Key, Value> cur : mas[i]){
                        item = cur;
                        hasNext = true;
                    }
                }
            }

            return new Iterator(curItem);
        }

        public Iterator previous(){
            int index = hash(item.key) % tableSize;
            Item<Key, Value> curItem = item;
            item = getPrevious(mas[index]);

            if(item == null){
                boolean hasPrev = false;
                for(int i = index-1; i >=0; --i )
                {
                    if(hasPrev)
                        break;
                    if(mas[i] == null)
                        continue;
                    for(Item<Key, Value> cur : mas[i]){
                        item = cur;
                        hasPrev = true;
                    }
                }
            }
            return new Iterator(curItem);
        }

        public void remove(){
            Item<Key, Value> curItem = item;
            item = next().item;
            HashTable.this.remove(curItem.key);
        }

        public void set(Item<Key, Value> newItem) {
            item = newItem;
        }

        @Override
        public String toString(){
            return item.toString();
        }
    }
}
