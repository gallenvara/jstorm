package com.alibaba.jstorm.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.storm.Config;
import org.yaml.snakeyaml.Yaml;

public class Utils {
    public static Map LoadProperty(String prop) {
        Properties properties = new Properties();
        Map conf = new HashMap();
        try {
            InputStream stream = new FileInputStream(prop);
            properties.load(stream);
            conf.putAll(properties);
            return conf;
        } catch (FileNotFoundException e) {
            System.out.println("No such file " + prop);
            return conf;
        } catch (Exception e1) {
            e1.printStackTrace();
            
            return conf;
        }
        
        
    }
    
    public static Map LoadYaml(String confPath) {
        Map conf = new HashMap();
        
        Yaml yaml = new Yaml();
        
        try {
            InputStream stream = new FileInputStream(confPath);
            
            conf = (Map) yaml.load(stream);
            if (conf == null || conf.isEmpty() == true) {
                throw new RuntimeException("Failed to read config file");
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("No such file " + confPath);
            throw new RuntimeException("No config file");
        } catch (Exception e1) {
            e1.printStackTrace();
            throw new RuntimeException("Failed to read config file");
        }
        
        return conf;
    }
    
    public static Map LoadConf(String arg) {
        if (arg.endsWith("yaml")) {
            return LoadYaml(arg);
        } else {
            return LoadProperty(arg);
        }
    }
    
    public static Config getConfig(String[] args) {
        Config ret = new Config();
        if (args == null || args.length == 0) {
            return ret;
        }
        
        if (StringUtils.isBlank(args[0])) {
            return ret;
        }
        
        Map conf = LoadConf(args[0]);
        ret.putAll(conf);
        return ret;
    }
    
    
    public static String getErrorInfo(Throwable error) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            error.printStackTrace(pw);
            return sw.toString();
        } catch (Exception e1) {
            return "";
        }
    }
    
    
}
