package com.page5of4.ms.subscriptions.messages;

import java.io.Serializable;

public class SubscribeMessage implements Serializable {
   private static final long serialVersionUID = 1L;

   private String address;
   private String messageType;

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getMessageType() {
      return messageType;
   }

   public void setMessageType(String messageType) {
      this.messageType = messageType;
   }

   protected SubscribeMessage() {

   }

   public SubscribeMessage(String address, String messageType) {
      super();
      this.address = address;
      this.messageType = messageType;
   }

   @Override
   public String toString() {
      return "SubscribeMessage [address=" + address + ", messageType=" + messageType + "]";
   }
}
