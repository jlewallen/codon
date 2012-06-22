package com.page5of4.codon.support;

public class TestsConfiguration {
   public static String getProjectVersion() {
      String value = System.getProperty("page5of4.project.version");
      if(value != null) return value;
      return "1.0.0-SNAPSHOT";
   }
}
