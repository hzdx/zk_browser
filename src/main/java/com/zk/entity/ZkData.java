package com.zk.entity;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.zookeeper.data.Stat;

public class ZkData {

   private byte[] data;
   private Stat stat;

   public byte[] getBytes() {
      return data;
   }

   @Override
   public String toString() {
      return "ZkData [data=" + Arrays.toString(getData()) + ",stat=" + getStat() + "]";
   }

   public String getDataString() {
	  if(data == null)
		  return "";
      return new String(getData(), Charset.forName("UTF-8"));
   }
   
   public int getDataLen(){
	   if(data == null)
		   return 0;
	   return data.length;
   }
   
   public byte[] getData() {
      return data;
   }

   public void setData(byte[] data) {
      this.data = data;
   }

   public Stat getStat() {
      return stat;
   }

   public void setStat(Stat stat) {
      this.stat = stat;
   }
}
