package com.page5of4.ms;

import java.io.Serializable;

public class EndpointAddress implements Serializable {
   private static final long serialVersionUID = 1L;
   private final String address;

   public EndpointAddress(String address) {
      super();
      this.address = address;
   }

   @Override
   public String toString() {
      return address;
   }

   @Override
   public int hashCode() {
      return address.hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      if(this == obj) return true;
      if(obj == null) return false;
      if(getClass() != obj.getClass()) return false;
      return address.equals(((EndpointAddress)obj).address);
   }
}
