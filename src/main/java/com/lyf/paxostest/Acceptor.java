package com.lyf.paxostest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lyf.paxostest.bean.ProPosalStatus;
import com.lyf.paxostest.bean.Promise;
import com.lyf.paxostest.bean.Proposal;
import org.apache.commons.lang3.RandomUtils;

/**
 * Acceptor 接受者行为角色
 */
public class Acceptor {
    // 提议对象，最后一次预提议
    private Proposal lastPrePare = new Proposal(0, null, null);
    // 是否已经确认某次提议
    private boolean isAccepted;
    // 已经接受的提议
    private Proposal acceptedProposal;
    // 名称
    private String name;

    public Proposal getLastPrePare() {
        return lastPrePare;
    }

    public void setLastPrePare(Proposal lastPrePare) {
        this.lastPrePare = lastPrePare;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public Proposal getAcceptedProposal() {
        return acceptedProposal;
    }

    public void setAcceptedProposal(Proposal acceptedProposal) {
        this.acceptedProposal = acceptedProposal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Acceptor(String name) {
        this.name = name;
    }


    /****
     * 预提议方法
     * @param proposal
     * @return
     */
    public synchronized Promise onPrepare(Proposal proposal) {
        System.out.println(
            "Thread Name" + Thread.currentThread().getName() + " " + "ACCEPTER_" + name
                + "   Para proposal " + proposal + "     lastPrePare proposal " + lastPrePare);

        // 假设这个过程有50%的几率失败
        if (Math.random() - 0.5 > 0) {
            Util.printInfo(
                "Thread Name" + Thread.currentThread().getName() + " " + "ACCEPTER_" + name,
                "PREPARE", "NO RESPONSE");
            return null;
        }
        if (proposal == null)
            throw new IllegalArgumentException("null proposal");

        // 线程休眠随机毫秒
        sleepRandom();

        // 是否确认某提议
        if (!isAccepted) {
            // 当前没有确认,则本次贿选金额是否大于上次金额
            if (proposal.getSerialId() > lastPrePare.getSerialId()) {
                // 本次提交的议题的serialID大于保存中的serialID，那么就替换掉
                Promise response = new Promise(true, ProPosalStatus.PREPARE, proposal);
                lastPrePare = proposal;
                Util.printInfo(
                    "Thread Name" + Thread.currentThread().getName() + " " + "ACCEPTER_" + name,
                    "PREPARE", "OK");
                System.out.println(
                    "Thread Name" + Thread.currentThread().getName() + " " + "ACCEPTER_" + name
                        + "     current proposal " + lastPrePare);

                // 记录提议并返回承诺
                return response;
            } else {
                // 返回保存的最大号的议题
                Util.printInfo(
                    "Thread Name" + Thread.currentThread().getName() + " " + "ACCEPTER_" + name,
                    "PREPARE", "REJECTED");

                // 返回包含上次提议(已经接受提议)的承诺
                return new Promise(false, ProPosalStatus.PREPARE, lastPrePare);
            }
        } else {
            // 已经确认某一个提议，
            if (acceptedProposal.getName().equals(proposal.getName())) {
                // 表示是同一个议题的不同serialID的提交
                if (acceptedProposal.getSerialId() < proposal.getSerialId()) {
                    // 表示已经确认的提案的提交人已经有的更新，那么就去除已经确认，重新设置状态为PrePare
                    isAccepted = false;
                    lastPrePare = proposal;
                    acceptedProposal = null;
                    return new Promise(true, ProPosalStatus.PREPARE, proposal);
                } else {
                    return new Promise(false, ProPosalStatus.ACCESPTED, acceptedProposal);
                }
            } else {
                // 当前已经确认的提案与当前的提案不是来自同一个人
                // 那么就将确认的提议返回，并且保存当前最大的proposal
                lastPrePare = proposal;
                return new Promise(false, ProPosalStatus.ACCESPTED, acceptedProposal);
            }
        }
    }

    /***
     * 判断是否提议生效
     * @param proposal
     * @return
     */
    public synchronized Promise onAccept(Proposal proposal) {
        if (isAccepted) {
            // 如果已经确认，那么就返回已经确认的结果
            return new Promise(false, ProPosalStatus.ACCESPTED, acceptedProposal);
        }
        if (lastPrePare == proposal) {
            // 确认提议与当前保存的提议相同，那么就返回OK
            acceptedProposal = proposal;
            isAccepted = true;//alStatus.ACCESPTED, acceptedProposal);
        } else {
            return null;
        }
        return null;
    }

    @Override public String toString() {
        return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
    }

    private void sleepRandom() {
        int randomTime = randomTime();
        try {
            Thread.sleep(randomTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int randomTime() {
        return RandomUtils.nextInt(1, 50);
    }
}
