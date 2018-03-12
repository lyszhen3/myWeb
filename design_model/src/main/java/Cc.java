/**
 * Created by lys on 2/24/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public abstract class Cc {
     String name;
     abstract void pp();

     public void introMy(){
          pp();
          System.out.println(this.name);
     }

}
