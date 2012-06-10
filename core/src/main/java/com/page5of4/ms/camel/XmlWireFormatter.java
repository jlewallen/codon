package com.page5of4.ms.camel;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.camel.Exchange;
import org.apache.camel.impl.SerializationDataFormat;
import org.apache.camel.spi.DataFormat;

public class XmlWireFormatter implements DataFormat {
   private SerializationDataFormat format = new SerializationDataFormat();

   @Override
   public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {
      format.marshal(exchange, graph, stream);
   }

   @Override
   public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
      return format.unmarshal(exchange, stream);
   }
}
