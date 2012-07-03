package com.page5of4.codon.camel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;

public class XmlWireFormatter implements DataFormat {
   private Map<Class<?>, JaxbDataFormat> formatters = new HashMap<Class<?>, JaxbDataFormat>();

   @Override
   public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {
      Class<?> klass = graph.getClass();
      getFormatter(klass).marshal(exchange, graph, stream);
   }

   @Override
   public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
      Message in = exchange.getIn();
      String messageType = in.getHeader(DefaultCamelTransport.MESSAGE_TYPE_KEY).toString();
      Class<?> klass = Thread.currentThread().getContextClassLoader().loadClass(messageType);
      return getFormatter(klass).unmarshal(exchange, stream);
   }

   private JaxbDataFormat getFormatter(Class<?> klass) throws JAXBException {
      if(!formatters.containsKey(klass)) {
         formatters.put(klass, new JaxbDataFormat(JAXBContext.newInstance(klass)));
      }
      return formatters.get(klass);
   }
}
