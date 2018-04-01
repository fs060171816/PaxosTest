package com.lyf.paxostest.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 提议对象.实现比较器
 *
 */
public class Proposal implements Comparable<Proposal>{

    /**
     * 主题，提议
     */
    private String subject;

    /***
     * 编码(贿选金额)
     */
    private int serialId;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  Proposal(int serialId, String subject,String name) {
        this.serialId = serialId;
        this.subject = subject;
        this.name=name;
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSerialId() {
        return serialId;
    }

    public void setSerialId(int serialId) {
        this.serialId = serialId;
    }


    @Override
    public boolean equals(Object o){
        // 判断如果是同一个实例直接返回
        if(this==o){
            return true;
        }
        // 如果不是Proposal对象直接返回
        if(o instanceof Proposal){
            Proposal obj =(Proposal)o;
            // Proposal对象判断三个属性如果相等则相等
            if(this.subject.equals(obj.getSubject())&&this.getSerialId()==obj.getSerialId()&&this.getName().equals(obj.getName())){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    @Override
    public int hashCode(){
        return subject.hashCode()+name.hashCode()+serialId;
    }

    @Override
    public int compareTo(Proposal o) {
        return Long.compare(serialId, o.serialId);
    }

    @Override
    public String toString(){
        return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
    }
}
