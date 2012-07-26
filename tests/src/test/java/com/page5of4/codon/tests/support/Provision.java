package com.page5of4.codon.tests.support;

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
      executor.executeCommand("features:install codon-dependencies");
      executor.executeCommand("features:install codon-core");
      executor.executeCommand("features:install codon-persistence-memory");
      return this;
   }

   public Provision web() {
      return this;
   }

   public Provision riak() {
      executor.executeCommand("features:install codon-persistence-riak");
      return this;
   }

   public Provision jpa() {
      executor.executeCommand("features:install codon-persistence-jpa");
      return this;
   }

   public Provision hibernate() {
      jpa();
      executor.executeCommand("features:install codon-persistence-jpa-hibernate");
      return this;
   }

   public Provision eclipseLink() {
      jpa();
      executor.executeCommand("features:install codon-persistence-jpa-eclipselink");
      return this;
   }

   public Provision core() {
      executor.executeCommand("features:install codon-core");
      return this;
   }
}
