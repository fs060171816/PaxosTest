package com.lyf.redisqueue;

import java.io.*;

/**
 * Object工具类
 * Created by lyf on 2017/12/2.
 */
public class ObjectUtil {

    /***
     * 对象转byte[]
     * @param obj
     * @return
     * @throws IOException
     */
    public static byte[] object2Bytes(Object obj) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream bo = null;
        ObjectOutputStream oo = null;
        try {
            bo = new ByteArrayOutputStream();
            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bo != null)bo.close();
            if(oo != null)oo.close();
        }

        return bytes;
    }

    /***
     * Byte[] 转成对象
     * @param bytes
     * @return
     * @throws IOException
     */
    public static Object bytes2Objuect(byte[] bytes) throws IOException, ClassNotFoundException {
        return new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject();
    }
}
