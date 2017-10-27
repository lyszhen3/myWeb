package com.lin.configurations;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Created by pc on 2017-10-20.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
@Configuration
public class ElasticConfiguration {
    @Bean(name ="elasticConfig")
    Properties properties() throws IOException {
        Properties properties = new Properties();
        properties.load(new InputStreamReader(new ClassPathResource("elastic.properties").getInputStream(), Charset.forName("UTF-8")));
        return properties;
    }
    @Bean(destroyMethod = "close")
    TransportClient  transportClient(@Qualifier("elasticConfig") Properties pre) throws UnknownHostException {
       TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
       client.addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName(pre.getProperty("elastic.host"))
               ,Integer.parseInt(pre.getProperty("elastic.transport"))));

        return client;

    }
}
