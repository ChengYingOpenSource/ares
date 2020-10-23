package com.cy.cuirass.raft.event;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ElectionTime {

    public static final long election = 10000;

    public static final long hearttime = 2000;

    public static final long votetime = 5000;

    public static final long election() {

        int fd = Math.abs(ThreadLocalRandom.current().nextInt(2000));

        return election + fd;

    }

    public static long votetime() {

        int fd = Math.abs(ThreadLocalRandom.current().nextInt(3000));

        return votetime + fd;

    }

}
