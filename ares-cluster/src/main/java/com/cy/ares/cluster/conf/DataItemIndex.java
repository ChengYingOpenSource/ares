package com.cy.ares.cluster.conf;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class DataItemIndex {

    private String key;

    private String digest;

    private long modifyTime;

    private long signTime;

    public DataItemIndex(String key, String digest, long modifyTime, long signTime) {
        this.key = key;
        this.digest = digest;
        this.modifyTime = modifyTime;
        this.signTime = signTime;
    }

    public DataItemIndex(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public int hashCode() {
        return key.concat(digest == null ? StringUtils.EMPTY : digest).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        DataItemIndex di = (DataItemIndex)obj;
        return StringUtils.equals(key, di.getKey()) && StringUtils.equals(digest, di.getDigest());
    }

}
