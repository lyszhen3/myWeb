package example.designpattern.creational.abstractfactory;

interface SkinFactory {
    Button createButton();

    TextField createTextField();

    ComboBox createComboBox();
}  