package com.cy.cuirass.cluster;

import com.alibaba.fastjson.JSON;

import akka.serialization.JSerializer;

public class MyOwnSerializer extends JSerializer {
    
  @Override 
  public boolean includeManifest() {
    return true;
  }
  
  @Override public int identifier() {
    return 198767;
  }
 
  @Override public byte[] toBinary(Object obj) {
    
    return JSON.toJSONString(obj).getBytes();
  }

  @Override public Object fromBinaryJava(byte[] bytes,Class<?> clazz) {
    
    String text = new String(bytes);
    return JSON.parseObject(text, clazz);
    //#...
  }
}