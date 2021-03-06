package com.lin.tools.generatorCreate.run.create;

import com.lin.tools.generatorCreate.configurations.DefaultConfiguration;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * Created by pc on 2017-10-09.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class Create {
    List<DefaultConfiguration> configurations;


    public void createNow(){
        configurations.forEach(s -> {
            try {
                outPut(s.getOutPutPath(),s.createCode());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void outPut(String outPutPath, String source) throws IOException {
        File file = new File(source);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        FileChannel channel = new FileOutputStream(outPutPath).getChannel();
        channel.write(ByteBuffer.wrap(source.getBytes()));
        channel.close();
//        FileWriter writer = new FileWriter(outPutPath);
//        writer.write(source);
//        writer.close();
    }

    public List<DefaultConfiguration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<DefaultConfiguration> configurations) {
        this.configurations = configurations;
    }
}
