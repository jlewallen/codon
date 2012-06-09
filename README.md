features:addurl mvn:org.apache.camel.karaf/apache-camel/2.9.2/xml/features
features:addurl mvn:com.page5of4.ms/core/1.0.0-SNAPSHOT/xml/features
features:addurl mvn:org.apache.servicemix.nmr/apache-servicemix-nmr/1.5.0/xml/features
features:addUrl mvn:org.apache.activemq/activemq-karaf/5.5.0/xml/features
features:install activemq
features:install camel-jms
osgi:install -s mvn:org.apache.activemq/activemq-camel/5.5.0

osgi:install -s mvn:org.codehaus.jackson/jackson-core-asl/1.7.5
osgi:install -s mvn:org.codehaus.jackson/jackson-mapper-asl/1.7.5

# osgi:install -s mvn:org.codehaus.jackson/jackson-core-asl/1.9.5
# osgi:install -s mvn:org.codehaus.jackson/jackson-mapper-asl/1.9.5

# mvn:org.apache.activemq/activemq-karaf/5.5.0-fuse-00-61/xml/features
# mvn:org.apache.karaf.assemblies.features/enterprise/2.2.0-fuse-00-61/xml/features
# mvn:org.apache.servicemix/apache-servicemix/4.4.0-fuse-00-61/xml/features
# mvn:org.apache.servicemix.nmr/apache-servicemix-nmr/1.5.0-fuse-00-61/xml/features
# mvn:org.apache.camel.karaf/apache-camel/2.7.3-fuse-00-61/xml/features
# mvn:org.apache.camel.karaf/apache-camel/2.9.2/xml/features
# mvn:com.page5of4.ms/core/1.0.0-SNAPSHOT/xml/features
# mvn:org.apache.karaf.assemblies.features/standard/2.2.0-fuse-00-61/xml/features

