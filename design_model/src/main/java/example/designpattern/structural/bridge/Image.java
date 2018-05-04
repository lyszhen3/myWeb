package example.designpattern.structural.bridge;

/**
 * Created by lys on 3/9/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public abstract class Image  {
    protected ImageImp imp;

    public void setImp(ImageImp imp) {
        this.imp = imp;
    }
    public abstract void parseFile(String fileName);
}

