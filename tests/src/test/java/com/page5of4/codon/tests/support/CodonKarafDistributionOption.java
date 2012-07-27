package com.page5of4.codon.tests.support;

import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.editConfigurationFilePut;

import org.apache.commons.lang.StringUtils;
import org.ops4j.pax.exam.Option;

public class CodonKarafDistributionOption {
   public static Option featuresBoot(String... features) {
      return editConfigurationFilePut("etc/org.apache.karaf.features.cfg", "featuresBoot", StringUtils.join(features, ","));
   }
}
