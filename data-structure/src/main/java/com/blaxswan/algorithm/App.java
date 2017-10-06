package com.blaxswan.algorithm;

import com.blaxswan.algorithm.heap.BinaryHeap;
import com.blaxswan.algorithm.heap.BinaryHeapArray;

import java.util.Random;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        HeapFoo();
    }

    private static void HeapFoo(){
        final BinaryHeapArray<Integer> heapArray = new BinaryHeapArray<Integer>(BinaryHeap.OrderingType.MAX);

        final Random random = new Random();
        int i;
        String sep = "";
        for(i = 0; i < 1500; i++){
            int randomInt = random.nextInt(1000);
            System.out.print(sep + randomInt);
            sep = ", ";

            heapArray.add(randomInt);
        }

        System.out.println();
        System.out.println();
        System.out.println(heapArray.toString());
    }
}
