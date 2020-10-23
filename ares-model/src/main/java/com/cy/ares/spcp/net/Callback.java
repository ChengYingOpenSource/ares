package com.cy.ares.spcp.net;

import java.util.concurrent.Callable;

import com.cy.ares.spcp.context.RefreshContext;

public interface Callback<T> extends Callable<T> {

    @Override
    default T call() throws Exception {
        // null 是否会触发 TODO cas
        return null;
    }

    public void success(T obj, RefreshContext contxt);

    public void error(RefreshContext contxt, Exception e);

}
