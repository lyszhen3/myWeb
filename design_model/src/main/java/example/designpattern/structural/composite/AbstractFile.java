package example.designpattern.structural.composite;

public abstract class AbstractFile {

    /*===============保留这部分是透明组合模式。对子叶毫无意义，会引起安全问题===================*/
    /*==========去掉这部分，将其写在容器中为安全组合模式，==============*/
    public abstract void add(AbstractFile file);  
    public abstract void remove(AbstractFile file);  
    public abstract AbstractFile getChild(int i);
    /*=============================*/
    public abstract void killVirus();  
}  
