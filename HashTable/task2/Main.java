package task2;

import task2.hashTable.HashTable;


public class Main {
    static public void main(String [] args){
        HashTable<Integer, String> myHashTable = new HashTable<>();
        System.out.println(myHashTable.getSize());
        myHashTable.add(8, "aaa");
        myHashTable.add(23, "asdrr");
        myHashTable.add(2, "www");
        myHashTable.add(12, "vbnm");
        myHashTable.add(73, "pkd");
        String findedValue = myHashTable.get(23);
        String undiscoveredValue = myHashTable.get(1234);
        System.out.println(findedValue + "  is exist");
        System.out.println(undiscoveredValue + " is not exist");
        System.out.println(myHashTable);

        boolean findedItem = myHashTable.find(23);
        boolean undiscoveredItem = myHashTable.find(12334);
        System.out.println("23" + " is " + findedItem);
        System.out.println("12334" + " is " + undiscoveredItem +"\n");

        myHashTable.remove(432);
        myHashTable.remove(8);
        System.out.println(myHashTable);

        System.out.println("Iterator: \n");

        System.out.println("Next: \n");
        HashTable<Integer, String>.Iterator iter = myHashTable.iterator();
        System.out.println(iter.toString());
        while(iter.hasNext()){
            iter.next();
            System.out.println(iter);
        }

        System.out.println("Previous: \n");

        System.out.println(iter);
        while(iter.hasPrevious()){
            iter.previous();
            System.out.println(iter);
        }

        System.out.println("Remove: \n");
        iter.remove();
        System.out.println(myHashTable);

        for(HashTable.Item<Integer, String> item : myHashTable)
        {
            System.out.println(item);
        }
        myHashTable.clear();
        System.out.println(myHashTable);
    }
}
