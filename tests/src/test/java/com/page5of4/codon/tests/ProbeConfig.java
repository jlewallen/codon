package com.page5of4.codon.tests;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.page5of4.codon.config.OsgiBusConfig;

@Configuration
@Import(value = { EnvironmentConfig.class, OsgiBusConfig.class })
public class ProbeConfig {

}
