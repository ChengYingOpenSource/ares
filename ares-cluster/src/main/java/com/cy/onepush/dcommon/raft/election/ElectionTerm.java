package com.cy.onepush.dcommon.raft.election;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;

public class ElectionTerm {

    private int timeout;

    private Runnable election;

    private volatile boolean start = false;

    public ElectionTerm(Runnable election) {
        this.election = election;
    }

    public void reset() {
        int t1 = Math.abs(RandomUtils.nextInt(0, 350)) + 150;
        this.timeout = t1;
    }

    public void start() {
        if (start)
            return;
        new Thread(election).start();
        start = true;
    }

    private Object lockObj = new Object();

    public boolean wait4Candidate() {
        this.reset();
        try {
            synchronized (this) {
                lockObj.wait(timeout);
            }
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }

    }

    public void wakeup4Candidate() {

        synchronized (this) {
            lockObj.notifyAll();
        }

    }

    private CountDownLatch latch = new CountDownLatch(0);

    public boolean wait4VoteResponse(int ballot, int timeSeconds) {

        try {
            latch = new CountDownLatch(ballot);
            boolean isOk = latch.await(timeSeconds, TimeUnit.SECONDS);
            return isOk;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public void wakeup4Vote() {

        latch.countDown();

    }

    public void stop() {

    }

}
