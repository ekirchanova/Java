package task2;

import com.sun.jdi.Value;
import task2.hashTable.HashTable;

import java.security.Key;

public class Main {
    static public void main(String [] args){
        HashTable<Integer, String> myHashTable = new HashTable<>();
        myHashTable.add(8, "aaa");
        myHashTable.add(23, "asdrr");
        myHashTable.add(2, "www");
        myHashTable.add(12, "vbnm");
        String findedValue = myHashTable.get(23);
        String undiscoveredValue = myHashTable.get(1234);
        System.out.println(findedValue + "  is exist");
        System.out.println(undiscoveredValue + " is not exist");
        System.out.println(myHashTable.toString());

        boolean findedItem = myHashTable.find(23);
        boolean undiscoveredItem = myHashTable.find(12334);
        System.out.println("23" + " is " + findedItem);
        System.out.println("12334" + " is " + undiscoveredItem +"\n");

        myHashTable.remove(432);
        myHashTable.remove(8);
        System.out.println(myHashTable.toString());

        System.out.println("Iterator: \n");

        System.out.println("Next: \n");
        HashTable<Integer, String>.Iterator iter = myHashTable.iterator();
        System.out.println(iter.toString());
        while(iter.hasNext()){
            iter.next();
            System.out.println(iter.toString());
        }
        System.out.println("Previous: \n");

        System.out.println(iter.toString());
        while(iter.hasPrevious()){
            iter.previous();
            System.out.println(iter.toString());
        }

        System.out.println("Remove: \n");
        iter.remove();
        System.out.println(myHashTable.toString());


        myHashTable.clear();
        System.out.println(myHashTable.toString());
    }
}
