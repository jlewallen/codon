# TODO

1. Flexible configuration of:
    1. Naming convention - primarily Application name.
    1. Creation of destination Components, which uses ActiveMQ by default now.
1. Archetypes that do database access and provide web UIs.
1. Grid and webconsole for managing things.
1. JMS to JMS store and forwarding (primarily from local queue to local/froeign servers)

# Grid TODO

1. Publish NodeAddedMessage, NodeRemovedMessage, NodePaused, NodeRemoved
1. Respond to PauseNode, ResumeNode

# Web Console TODO

1. Connect to ActiveMQ/JMS broker via JMX and display:
    1. All Queues
        1. Oldest message information.
        1. Queue length.
    1. Posion Queues
        1. Reprocess poison messages.

# Provisioning Karaf

Rough idea of provisioning a Karaf instance for using this.

    features:addurl mvn:org.apache.camel.karaf/apache-camel/2.9.2/xml/features
    features:addurl mvn:com.page5of4.codon/core/1.0.0-SNAPSHOT/xml/features
    features:addurl mvn:org.apache.servicemix.nmr/apache-servicemix-nmr/1.5.0/xml/features
    features:addUrl mvn:org.apache.activemq/activemq-karaf/5.5.0/xml/features
    features:install activemq
    features:install camel-jms
    osgi:install -s mvn:org.apache.activemq/activemq-camel/5.5.0
    osgi:install -s mvn:org.codehaus.jackson/jackson-core-asl/1.7.5
    osgi:install -s mvn:org.codehaus.jackson/jackson-mapper-asl/1.7.5
    osgi:install -s mvn:com.atomikos/transactions-osgi/3.8.0
    
    osgi:install -s mvn:commons-io/commons-io/2.0.1
    osgi:install -s mvn:commons-lang/commons-lang/2.5
    osgi:install -s mvn:com.googlecode.guava-osgi/guava-osgi/9.0.0
    osgi:install -s mvn:com.page5of4.commons/mustache-mvc/1.2.3-SNAPSHOT
    osgi:install -s mvn:com.page5of4.codon/libraries/1.0.0-SNAPSHOT

# Scratch Area

These aren't used, but I'm keeping them around in here just in case.

    osgi:install -s mvn:org.codehaus.jackson/jackson-core-asl/1.9.5
    osgi:install -s mvn:org.codehaus.jackson/jackson-mapper-asl/1.9.5

    mvn:org.apache.activemq/activemq-karaf/5.5.0-fuse-00-61/xml/features
    mvn:org.apache.karaf.assemblies.features/enterprise/2.2.0-fuse-00-61/xml/features
    mvn:org.apache.servicemix/apache-servicemix/4.4.0-fuse-00-61/xml/features
    mvn:org.apache.servicemix.nmr/apache-servicemix-nmr/1.5.0-fuse-00-61/xml/features
    mvn:org.apache.camel.karaf/apache-camel/2.7.3-fuse-00-61/xml/features
    mvn:org.apache.camel.karaf/apache-camel/2.9.2/xml/features
    mvn:com.page5of4.codon/core/1.0.0-SNAPSHOT/xml/features
    mvn:org.apache.karaf.assemblies.features/standard/2.2.0-fuse-00-61/xml/features
