package com.page5of4.ms.camel;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;

public class XmlDataFormat implements DataFormat {
   @Override
   public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {

   }

   @Override
   public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
      return null;
   }
}
