package com.cy.ares.spcp.context;
import java.util.*;



public interface RefreshContext {
    
    public void refreshServer();

    public void refreshConfig();

    public void close();

    
}
