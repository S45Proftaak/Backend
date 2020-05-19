package com.foodplanner.rest_service.configuration;


import com.foodplanner.rest_service.exceptions.FileNotWritable;
import org.springframework.core.io.ClassPathResource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WritePropperties {


    public static void writePropsToFile(String property, Object value, String filename) throws FileNotWritable {
        Properties prop = new Properties();
        ClassPathResource resource = new ClassPathResource(filename);
        try {
            InputStream in = resource.getInputStream();
            prop.load(in);
            prop.setProperty(property, String.valueOf(value));
            prop.store(new FileOutputStream(resource.getURI().getPath().substring(1)), null);
        } catch (IOException e) {
            throw new FileNotWritable(e.getMessage(), e.getCause());
        }
    }

}
