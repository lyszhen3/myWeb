package example.designpattern.behavioral.Memento;

import java.util.ArrayList;

class MementoCaretaker {
    //定义一个集合来存储多个备忘录  
    private ArrayList mementolist = new ArrayList();

    public ChessmanMemento getMemento(int i) {  
        return (ChessmanMemento)mementolist.get(i);  
    }  

    public void setMemento(ChessmanMemento memento) {  
        mementolist.add(memento);  
    }  
}