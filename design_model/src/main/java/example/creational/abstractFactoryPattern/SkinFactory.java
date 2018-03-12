package example.creational.abstractFactoryPattern;

interface SkinFactory {
    Button createButton();

    TextField createTextField();

    ComboBox createComboBox();
}  