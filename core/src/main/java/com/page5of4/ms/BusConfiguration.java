package com.page5of4.ms;

public interface BusConfiguration {

   String getApplicationName();

   // String getLocalComponentName() ;

   String getOwnerAddress(String messageType);

   String getLocalAddress(String messageType);

}
