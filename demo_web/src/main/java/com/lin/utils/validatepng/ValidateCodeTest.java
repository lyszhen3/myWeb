package com.lin.utils.validatepng;
  
import java.io.File;
import java.io.IOException;
import java.util.Date;  
  
public class ValidateCodeTest {  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        ValidateCode vCode = new ValidateCode(120,40,5,100);  
        try {  
            String path="D:/t/"+new Date().getTime()+".png";  
            System.out.println(ValidateCode.getCode()+" >"+path);
            File file = new File(path);
            if(!file.exists()){

                file.createNewFile();
            }

            vCode.write(path);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
} 