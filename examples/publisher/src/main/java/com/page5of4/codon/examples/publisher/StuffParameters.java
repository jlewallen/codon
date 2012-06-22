package com.page5of4.codon.examples.publisher;

import java.util.UUID;

public class StuffParameters {
   private final String key;
   private final Long number;
   private final UUID id;

   public UUID getId() {
      return id;
   }

   public String getKey() {
      return key;
   }

   public Long getNumber() {
      return number;
   }

   public StuffParameters(UUID id, String key, Long number) {
      super();
      this.id = id;
      this.key = key;
      this.number = number;
   }
}
