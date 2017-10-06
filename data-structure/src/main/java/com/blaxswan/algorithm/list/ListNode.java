package com.blaxswan.algorithm.list;

public class ListNode{
    private int val;
    public ListNode next;

    public ListNode(final int val){
        this.val = val;
        this.next = null;
    }

    public int value(){
        return this.val;
    }
}