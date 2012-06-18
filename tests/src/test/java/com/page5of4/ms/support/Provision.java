package com.page5of4.ms.support;

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
            "features:addurl mvn:com.page5of4.ms/core/" + TestsConfiguration.getProjectVersion() + "/xml/features"
            );
      executor.executeCommands(
            "features:install camel-jms",
            "features:install spring-dm",
            "features:install spring-web",
            "features:install camel-jms",
            "features:install camel-jaxb",
            "features:install camel-spring",
            "features:install activemq",
            "osgi:install -s mvn:org.apache.activemq/activemq-camel/5.5.0"
            );
      return this;
   }

   public Provision core() {
      executor.executeCommand("features:install ms-core");
      return this;
   }
}
