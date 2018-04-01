package com.lyf.paxostest.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 承诺对象
 * 记录提案状态和是否被接受
 */
public class Promise {
    // 是否接受
    private boolean isAcctped;
    private ProPosalStatus status;
    // 提案对象
    private Proposal proposal;

    public Promise(boolean isAcctped,ProPosalStatus status,Proposal proposal){
        this.isAcctped = isAcctped;
        this.status=status;
        this.proposal=proposal;
    }

    public ProPosalStatus getStatus() {
        return status;
    }

    public void setStatus(ProPosalStatus status) {
        this.status = status;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    public boolean isAcctped() {
        return isAcctped;
    }

    public void setAcctped(boolean acctped) {
        isAcctped = acctped;
    }

    @Override
    public String toString(){
        return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
    }
}
