package com.linkedDemo;

/**
 * Created by pc on 2017-08-22.
 * 双端链表
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class FirstLastLinkedMy<T> {
    private class Data {

        private T t;
        private Data next;

        Data(T t) {
            this.t = t;
        }
    }

    private Data first;
    private Data last;

    public void insertFirst(T t) {
        Data data = new Data(t);
        if (first == null) {
            last = data;
        }
        data.next = first;
        first = data;
    }

    public void insertLast(T t) {
        Data data = new Data(t);
        if (first == null) {
            first = data;
        } else {
            last.next = data;
        }
        last = data;

    }

    public T deleteFirst() throws Exception {
        if (first == null) {
            throw new Exception("empty");
        }
        Data temp = first;
        first = temp.next;
        return temp.t;
    }

    public void deleteLast() throws Exception {
        if (first == null) {
            throw new Exception("empty");
        }
        if (first.next == null) {
            first = null;
            last = null;
        } else {
            Data temp = first;
            while (temp != null) {
                if (temp.next == last) {
                    last = temp;
                    last.next = null;
                }
                temp = temp.next;
            }
        }


    }

    public T find(T t) throws Exception {
        if (first == null) {
            throw new Exception("empty");
        }
        Data cur = first;
        while (cur != null) {
            if (cur.t.equals(t)) {
                return cur.t;
            }
            cur = cur.next;
        }
        return null;
    }

    public void remove(T t) throws Exception {
        if (first == null) {
            throw new Exception("empty");
        }
        if (first.t.equals(t)) {
            if (first.next == null) {
                last = null;
            }
            first = null;

        } else {
            Data pre = first;
            Data cur = pre.next;
            while (cur != null) {
                if (cur.t.equals(t)) {
                    pre.next = cur.next;
                }
                pre = cur;
                cur = cur.next;
            }
        }
    }

    public void display() {
        if (first == null) {
            System.out.println("empty");
        }
        Data data = first;
        while (data != null) {
            System.out.print(data.t.toString() + "->");
            data = data.next;
        }
        System.out.print("\n");
    }

    public static void main(String[] args) throws Exception {
        FirstLastLinkedMy<String> firstLastLinkedMy = new FirstLastLinkedMy<>();
        firstLastLinkedMy.insertFirst("3");
        firstLastLinkedMy.insertFirst("2");
        firstLastLinkedMy.insertFirst("1");
        firstLastLinkedMy.insertLast("4");
        firstLastLinkedMy.insertLast("5");
        firstLastLinkedMy.insertLast("6");
        firstLastLinkedMy.insertLast("7");
        firstLastLinkedMy.display();
        firstLastLinkedMy.deleteFirst();
        firstLastLinkedMy.deleteFirst();
        firstLastLinkedMy.display();
        firstLastLinkedMy.deleteLast();
        firstLastLinkedMy.display();
        firstLastLinkedMy.remove("5");
        firstLastLinkedMy.display();
        firstLastLinkedMy.remove("1");
        String t = firstLastLinkedMy.last.t;
        System.out.println("last:"+t);

    }

}
