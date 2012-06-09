package com.page5of4.ms.subscriptions;

public class Subscription {
   private final String address;
   private final String messageType;

   public Subscription(String address, String messageType) {
      super();
      this.address = address;
      this.messageType = messageType;
   }

   public String getAddress() {
      return address;
   }

   public String getMessageType() {
      return messageType;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int hash = 1;
      hash = prime * hash + address.hashCode();
      hash = prime * hash + messageType.hashCode();
      return hash;
   }

   @Override
   public boolean equals(Object obj) {
      if(this == obj) return true;
      if(obj == null) return false;
      if(getClass() != obj.getClass()) return false;
      Subscription other = (Subscription)obj;
      if(!address.equals(other.address)) return false;
      if(!messageType.equals(other.messageType)) return false;
      return true;
   }

   @Override
   public String toString() {
      return "Subscription [address=" + address + ", messageType=" + messageType + "]";
   }
}