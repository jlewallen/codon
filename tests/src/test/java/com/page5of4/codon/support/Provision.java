package com.page5of4.codon.support;

public class Provision {
   private final CommandExecutor executor;

   public Provision(CommandExecutor executor) {
      super();
      this.executor = executor;
   }

   public static Provision with(CommandExecutor executor) {
      return new Provision(executor);
   }

   public Provision base() {
      executor.executeCommands(
            "features:addurl mvn:org.apache.camel.karaf/apache-camel/2.9.2/xml/features",
            "features:addurl mvn:org.apache.activemq/activemq-karaf/5.5.0/xml/features",
            "features:addurl mvn:com.page5of4.codon/codon-core/" + TestsConfiguration.getProjectVersion() + "/xml/features"
            );
      executor.executeCommands(
            "osgi:install -s mvn:org.codehaus.jackson/jackson-core-asl/1.7.5",
            "osgi:install -s mvn:org.codehaus.jackson/jackson-mapper-asl/1.7.5",
            "osgi:install -s mvn:com.google.guava/guava/12.0"
            );
      executor.executeCommands(
            "features:install spring-dm",
            "features:install spring-dm-web"
            );
      executor.executeCommands(
            "features:install camel-jms",
            "features:install camel-jms",
            "features:install camel-jaxb",
            "features:install camel-spring",
            "features:install activemq",
            "osgi:install -s mvn:org.apache.activemq/activemq-camel/5.5.0"
            );
      return this;
   }

   public Provision web() {
      System.out.println(executor.executeCommands(
            "osgi:install -s mvn:com.page5of4.codon.bundles/com.page5of4.codon.bundles.mustache-mvc/1.2.3-SNAPSHOT",
            "features:install spring-web",
            "features:install war"
            ));
      return this;
   }

   public Provision riak() {
      executor.executeCommands(
            "osgi:install -s mvn:com.page5of4.codon.bundles/com.page5of4.codon.bundles.riak-client/1.0.5",
            "osgi:install -s mvn:com.page5of4.codon.bundles/com.page5of4.codon.bundles.kryo/2.14"
            );
      return this;
   }

   public Provision hibernate() {
      executor.executeCommand("osgi:install -s mvn:org.osgi/org.osgi.compendium/4.2.0");
      executor.executeCommand("osgi:install -s mvn:org.osgi/org.osgi.enterprise/4.2.0");
      executor.executeCommand("osgi:install -s mvn:org.apache.geronimo.specs/geronimo-jpa_2.0_spec/1.1");
      executor.executeCommand("osgi:install -s mvn:org.apache.derby/derby/10.8.1.2");

      executor.executeCommand("osgi:install -s mvn:commons-collections/commons-collections/3.2.1");
      executor.executeCommand("osgi:install -s mvn:org.apache.geronimo.specs/geronimo-jta_1.1_spec/1.1.1");
      executor.executeCommand("osgi:install -s mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.serp/1.13.1_3");
      executor.executeCommand("osgi:install -s mvn:org.apache.aries/org.apache.aries.util/0.3");
      executor.executeCommand("osgi:install -s mvn:org.apache.aries.jpa/org.apache.aries.jpa.api/0.3");
      executor.executeCommand("osgi:install -s mvn:org.apache.aries.jpa/org.apache.aries.jpa.container/0.3");
      executor.executeCommand("osgi:install -s mvn:org.apache.aries.transaction/org.apache.aries.transaction.manager/0.3");

      executor.executeCommand("osgi:install -s mvn:javax.persistence/com.springsource.javax.persistence/2.0.0");
      executor.executeCommand("osgi:install -s mvn:javax.transaction/com.springsource.javax.transaction/1.1.0");
      executor.executeCommand("osgi:install -s mvn:org.apache.commons/com.springsource.org.apache.commons.collections/3.2.1");
      executor.executeCommand("osgi:install -s mvn:com.page5of4.codon.bundles/com.page5of4.codon.bundles.hibernate/4.0.0");
      return this;
   }

   public Provision core() {
      executor.executeCommand("features:install codon-core");
      return this;
   }
}
