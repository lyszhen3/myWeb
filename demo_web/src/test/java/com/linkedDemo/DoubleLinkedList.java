package com.linkedDemo;

import java.util.*;
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

    public void insertFirst(E e) {
        Node<E> node = new Node(e);
        if (first == null) {
            last = node;
        } else {
            node.next = first;
            first.prev = node;
        }
        first = node;

    }

    public void insertLast(E e) {
        Node<E> node = new Node<>(e);
        if (last == null) {
            first = node;
        } else {
            node.prev = last;
            last.next = node;
        }
        last = node;
    }

    public void deleteFirst() throws Exception {
        if (first == null) {
            throw new Exception("empty");
        }
        if (first.next == null) {
            first = null;
            last = null;
        } else {
            first.next.prev = null;
            first = first.next;
        }

    }

    public void deleteLast() throws Exception {
        if (last == null) {
            throw new Exception("empty");
        }
        if (last.prev == null) {
            last = null;
            first = null;
        } else {
            last.prev.next = null;
            last = last.prev;
        }
    }

    public void delete(E e) throws Exception {
        if (first == null) {
            throw new Exception("empty");
        }
        Node<E> cur = first;
        while (cur != null) {
            if (cur.e.equals(e)) {
                if (cur == first) {
                    cur.next.prev = null;
                    first = cur.next;
                } else if (cur == last) {
                    cur.prev.next = null;
                    last = cur.prev;
                } else {
                    cur.prev.next = cur.next;
                    cur.next.prev = cur.prev;
                }
                break;

            }
            cur = cur.next;
        }
    }


    /**
     * 从前到后
     */
    public void displayPtoN() {
        if (first == null) {
            System.out.println("empty");
        } else {
            System.out.print("p-n :");
            Node<E> node = first;
            while (node != null) {
                System.out.print(node.e.toString() + "->");
                node = node.next;
            }
        }
        System.out.print("\n");

    }

    public void displayNtoP() {
        if (last == null) {
            System.out.println("empty");
        } else {
            System.out.print("n-p :");
            Node<E> node = last;
            while (node != null) {
                System.out.print(node.e.toString() + "->");
                node = node.prev;
            }
        }
        System.out.print("\n");

    }


    public static void main(String[] args) throws Exception {
        DoubleLinkedList<Integer> doubleLinkedList = new DoubleLinkedList<>();
        doubleLinkedList.insertFirst(4);
        doubleLinkedList.insertFirst(3);
        doubleLinkedList.insertFirst(2);
        doubleLinkedList.insertFirst(1);
        doubleLinkedList.insertLast(5);
        doubleLinkedList.insertLast(6);
        doubleLinkedList.displayPtoN();
        doubleLinkedList.displayNtoP();
        doubleLinkedList.deleteFirst();
        doubleLinkedList.displayPtoN();
        doubleLinkedList.displayNtoP();
        doubleLinkedList.deleteLast();
        doubleLinkedList.displayPtoN();
        doubleLinkedList.displayNtoP();
        doubleLinkedList.delete(2);
        doubleLinkedList.displayPtoN();
        doubleLinkedList.displayNtoP();
        //研究list源代码
        LinkedList<Integer> list = new LinkedList<>();
        ArrayList<Integer> arrayList = new ArrayList<>();
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("语文", 1);
        map.put("数学", 2);
        map.put("英语", 3);
        map.put("历史", 4);
        map.put("政治", 5);
        map.put("地理", 6);
        map.put("生物", 7);
        map.put("化学", 8);
        map.size();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        int i = 828406^(828406>>> 16);

        int i2 = 682768^(682768>>>16);
        System.out.println(8&i);
        System.out.println(8&i2);

        arrayList.add(1);
    }

}
