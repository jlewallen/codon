package com.page5of4.ms;

public interface Bus {
   <T> void publish(T message);

   <T> void send(T message);

   <T> void sendLocal(T message);

   void subscribe(Class<?> messageType);

   void unsubscribe(Class<?> messageType);
}
