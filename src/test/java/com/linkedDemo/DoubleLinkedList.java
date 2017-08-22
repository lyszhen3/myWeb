package com.linkedDemo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by pc on 2017-08-22.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class DoubleLinkedList<E> {
    private static class Node<E> {
        E e;
        Node<E> prev;
        Node<E> next;
        Node(E e) {
            this.e = e;
        }
    }
    private transient Node<E> first;
    private transient Node<E> last;

    public void insertFirst(E e){
        Node<E> node = new Node(e);
        if(first == null){
            last = node;
        }else{
            node.next = first;
            first.prev = node;
        }
        first  = node;

    }
    public void insertLast(E e){
        Node<E> node = new Node<>(e);
        if(last ==null){
            first = node;
        }else{
            node.prev = last;
            last.next = node;
        }
        last = node;
    }


    /**
     * 从前到后
     */
    public void displayPtoN(){
        if(first == null){
            System.out.println("empty");
        }else{
            System.out.print("p-n :");
            Node<E> node = first;
            while (node!=null){
                System.out.print(node.e.toString()+"->");
                node = node.next;
            }
        }
        System.out.print("\n");

    }
    public void displayNtoP(){
        if(last == null){
            System.out.println("empty");
        }else{
            System.out.print("n-p :");
            Node<E> node = last;
            while (node!=null){
                System.out.print(node.e.toString()+"->");
                node = node.prev;
            }
        }
        System.out.print("\n");

    }


    public static void main(String[] args) {
        DoubleLinkedList<Integer> doubleLinkedList = new DoubleLinkedList<>();
        doubleLinkedList.insertFirst(4);
        doubleLinkedList.insertFirst(3);
        doubleLinkedList.insertFirst(2);
        doubleLinkedList.insertFirst(1);
        doubleLinkedList.insertLast(5);
        doubleLinkedList.insertLast(6);
        doubleLinkedList.displayPtoN();
        doubleLinkedList.displayNtoP();
        LinkedList<Integer> list = new LinkedList();
        ArrayList<Integer> arrayList = new ArrayList();
        arrayList.add(1);
//        list.get(1);
        System.out.println(1>>1);
        Date date = new Date();
    }

}
