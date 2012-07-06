package com.page5of4.codon.examples.subscriber.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.page5of4.codon.Bus;
import com.page5of4.codon.extender.config.ExtenderConfig;

@Configuration
@Import(value = { EnvironmentConfig.class, ExtenderConfig.class })
public class ExampleConfig {
   @Autowired
   private Bus bus;
}
