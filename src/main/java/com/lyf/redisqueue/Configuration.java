package com.lyf.redisqueue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件读取类
 * Created by lyf on 2017/12/2.
 */
public class Configuration extends Properties{

    private static final long serialVersionUID = -8230884645896500767L;
    private static Configuration instance = null;

    public static synchronized Configuration getInstance(){
        if(instance == null){
            instance = new Configuration();
        }
        return instance;
    }

    public String getProperty(String key,String defaultValue){
        String val = getProperty(key);
        return (val == null || val.length() < 1) ? defaultValue :val;
    }

    public String getString(String key,String defaultValue){
        return this.getProperty(key,defaultValue);
    }

    public int getInt(String name,int defaultValue){
        String val = this.getProperty(name);
        return(val == null || val.length()< 1) ? defaultValue :Integer.parseInt(val);
    }

    public long getLong(String name,long defaultValue){
        String val = this.getProperty(name);
        return(val == null || val.length()< 1) ? defaultValue :Long.parseLong(val);
    }

    public float getFloat(String name,float defaultValue){
        String val = this.getProperty(name);
        return(val == null || val.length()< 1) ? defaultValue :Float.parseFloat(val);
    }

    public double getDouble(String name,double defaultValue){
        String val = this.getProperty(name);
        return(val == null || val.length()< 1) ? defaultValue :Double.parseDouble(val);
    }

    public byte geBytes(String name,byte defaultValue){
        String val = this.getProperty(name);
        return(val == null || val.length()< 1) ? defaultValue :Byte.parseByte(val);
    }

    public Configuration(){
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("config.xml");

        try {
            this.loadFromXML(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}