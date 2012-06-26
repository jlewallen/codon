package com.page5of4.codon.impl;

import com.page5of4.codon.BusConfiguration;

public class BusContext {
   private final TopologyConfiguration topologyConfiguration;

   public TopologyConfiguration getTopologyConfiguration() {
      return topologyConfiguration;
   }

   public BusContext(TopologyConfiguration topologyConfiguration) {
      super();
      this.topologyConfiguration = topologyConfiguration;
   }
}
