package task2.hashTable;

import java.util.ArrayList;
import java.lang.Iterable;
import java.util.Iterator;
import java.util.LinkedList;

public class HashTable <Key, Value> implements Iterable<HashTable.Item<Key, Value>>{

    static public class Item<Key, Value>
    {
        final private Key key;
        private int hash;
        private Value value;
        ///------------------
        public Item(Key key, Value value, int hash){
            this.key = key;
            this.value = value;
            this.hash = hash;
        }

        public Item(Item<Key, Value> item){
            this(item.getKey(), item.getValue(), item.getHash());
        }


        public Value getValue(){
            return value;
        }
        public Key getKey(){
            return key;
        }
        public int getHash(){ return hash;};

        public void setValue(Value value){
            this.value = value;
        }
        public void setHash(int hash){ this.hash = hash;}

        public boolean isSameKey(Key key){
            return this.key == key;
        }

        @Override
        public String toString(){
            return key.toString() + " = " + value.toString();
        }
    }
    //-------------------------------------------------------------------
    private ArrayList<LinkedList<Item<Key,Value>>>  mas;
    private int tableSize;
    private int fullness;


    private boolean isNeedResize(){
        return fullness >= tableSize/2;
    }
    ///    ----------------------
    public HashTable(int tableSize){
        this.tableSize = tableSize;
        this.mas = new ArrayList<>(this.tableSize);
        for(int i = 0; i < tableSize;++i)
            mas.add(i, null);
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
        if(mas.get(index) == null)
            return null;
        for (Item<Key, Value> cur: mas.get(index)){
            if(cur.isSameKey(key))
                return cur.getValue();
        }
        return null;
    }

    public boolean find(Key key){
        int index = hash(key) % tableSize;
        if(mas.get(index) == null)
            return false;
        for (Item<Key, Value> cur: mas.get(index)) {
            if(cur.isSameKey(key))
                return true;
        }
        return false;
    }
    public boolean add(Key key, Value value){
        if(find(key))
            return false;
        int hash = hash(key);
        int index =  hash % tableSize;
        if(mas.get(index) == null){
            mas.set(index, new LinkedList<>());
            ++fullness;
        }
        mas.get(index).add(new Item<>(key, value, hash));

        if(isNeedResize())
            resize();

        return true;
    }

    public boolean remove(Key key){
        int index = hash(key) % tableSize;
        if(mas.get(index) == null)
            return false;
        for (Item<Key, Value> cur: mas.get(index)) {
            if(cur.isSameKey(key)) {
                mas.get(index).remove(cur);
                if(mas.get(index).size() == 0)
                    mas.set(index, null);
                return true;
            }
        }
        return false;
    }

    public int getSize(){
        return tableSize;
    }

    public void resize(){
        ArrayList<LinkedList<Item<Key,Value>>>  oldMas = mas;
        int oldSize = this.tableSize;
        this.tableSize *=2;
        this.mas = new ArrayList<>(this.tableSize);
        
        for(int i =0; i < oldSize; ++i){
            for (Item<Key, Value> cur: oldMas.get(i)) {
                add(cur.getKey(), cur.getValue());
            }
        }
    }

    public void clear(){
        for ( int k = 0; k < tableSize; k++ ) {
            if ( mas.get(k) == null )
                continue;
            for (Item<Key, Value> cur: mas.get(k))
                mas.get(k).remove(cur);
            mas.set(k, null);
        }
        fullness = 0;
    }

    public int getFullness(){
        return fullness;
    }

    public boolean isEmpty(){
        return fullness == 0;
    }

    @Override
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
            if ( mas.get(k) == null )
                continue;
            elementIndex = 0;
            sb.append("[").append(k).append("] -> {");
            for ( Item<Key, Value> i : mas.get(k) ) {
                sb.append(i.toString());
                if ( elementIndex < mas.get(k).size() -1 ) {
                    sb.append(", ");
                }
                elementIndex++;
            }
            sb.append("}\n");
        }
        return sb.toString();
    }
    ///----------------------------------------------------------------------
    public class Iterator implements java.util.Iterator<Item<Key, Value>>
    {
        private int numberCell = -1;
        private int numberCellInCell = -1;
        
        private LinkedList<HashTable.Item<Key, Value>> getCurrentMas(){
            return HashTable.this.mas.get(numberCell);
        }
        
        private Item<Key, Value> getCurrentItem() {
            LinkedList<HashTable.Item<Key, Value>> iterMas = getCurrentMas();
            return iterMas.get(numberCellInCell);
        }
        
        private boolean checkNext(){
            LinkedList<Item<Key, Value>> curMas = getCurrentMas();
            if(curMas.size() == 1)
                return false;
           Item<Key, Value> iterItem = getCurrentItem();
           for(Item<Key, Value> cur : curMas)
           {
               if(cur.isSameKey(iterItem.key))
                   return true;
           }
           return false;
        }

        private boolean checkPrevious(){
            LinkedList<Item<Key, Value>> curMas = getCurrentMas();
            if(curMas.size() == 1)
                return false;
            Item<Key, Value> iterItem = getCurrentItem();
            for(Item<Key, Value> cur : curMas)
            {
                if(cur.isSameKey(iterItem.key))
                    return true;
            }
            return false;
        }

        //-----------------------------------------------------
        public Iterator(){
            if(isEmpty()){
                return;
            }

            for(int i = 0; i < tableSize; ++i)
            {
                if(mas.get(i) == null)
                    continue;
                LinkedList<HashTable.Item<Key, Value>> curMas = HashTable.this.mas.get(i);
                for(int j = 0; j < curMas.size(); ++j )
                {
                    this.numberCell = i ;
                    this.numberCellInCell = j;
                    return;
                }
            }
        }

        @Override
        public boolean hasNext(){
            if(checkNext())
                return true;

            for(int i = numberCell + 1; i < tableSize; ++i)
            {
                if(mas.get(i) != null)
                    return true;
            }

            return false;
        }

        public boolean hasPrevious(){
            if(checkPrevious())
                return true;
            for(int i = numberCell - 1; i >= 0; --i )
            {
                if(mas.get(i) != null)
                    return true;
            }
            return false;
        }

        @Override
        public Item<Key, Value> next(){
            Item<Key, Value> iterItem = getCurrentItem();
            boolean hasNext = false;
            for(int i = numberCell + 1; i < tableSize; ++i)
            {
                if(hasNext)
                    break;
                if(mas.get(i) == null)
                    continue;
                LinkedList<HashTable.Item<Key, Value>> curMas = HashTable.this.mas.get(i);
                for(int j = 0; j < curMas.size(); ++j){
                    this.numberCell = i ;
                    this.numberCellInCell = j;
                    hasNext = true;
                }
            }
            return iterItem;
        }

        public Item<Key, Value> previous(){
            Item<Key, Value> iterItem = getCurrentItem();
            boolean hasPrev = false;
            for(int i = numberCell - 1; i >= 0; --i )
            {
                if(hasPrev)
                    break;
                if(mas.get(i) == null)
                    continue;
                LinkedList<HashTable.Item<Key, Value>> curMas = HashTable.this.mas.get(i);
                for(int j = 0; j < curMas.size(); ++j){
                    this.numberCell = i ;
                    this.numberCellInCell = j;
                    hasPrev = true;
                }
            }
            return iterItem;
        }

        @Override
        public void remove(){
            Item<Key, Value> iterItem = getCurrentItem();
            next();
            HashTable.this.remove(iterItem.key);
        }

        @Override
        public String toString(){
            Item<Key, Value> iterItem = getCurrentItem();
            return iterItem.toString();
        }
    }
}
