package example.designpattern.creational.withobject.docexample;

interface OfficialDocument extends Cloneable {
    OfficialDocument clone();

    void display();
}