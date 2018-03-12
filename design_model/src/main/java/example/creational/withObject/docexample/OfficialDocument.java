package example.creational.withObject.docexample;

interface OfficialDocument extends Cloneable {
    OfficialDocument clone();

    void display();
}