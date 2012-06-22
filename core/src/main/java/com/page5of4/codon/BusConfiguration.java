package com.page5of4.codon;

public interface BusConfiguration {

   String getApplicationName();

   // String getLocalComponentName() ;

   String getOwnerAddress(String messageType);

   String getLocalAddress(String messageType);

}
