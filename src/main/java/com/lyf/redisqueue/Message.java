package com.lyf.redisqueue;

import java.io.Serializable;

/**
 * 消息类
 * Created by lyf on 2017/12/2.
 */
public class Message implements Serializable{

    private static final long serialVersionUID = -3664930098030154905L;
    private int id;
    private String content;
    public Message(int id,String content){
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
