package com.page5of4.codon.useful.repositories;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import com.google.common.collect.Lists;

public class DefaultRepositoryProvider {
   private static final Logger logger = LoggerFactory.getLogger(DefaultRepositoryProvider.class);
   private final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
   private final MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);

   public DefaultRepositoryProvider() {
      super();
   }

   public Collection<Class<?>> findRepositoryClasses() {
      try {
         List<Class<?>> matches = Lists.newArrayList();
         Resource[] resources = resolver.getResources("classpath*:/**/codon.persistence");
         for(Resource resource : resources) {
            InputStream stream = resource.getInputStream();
            try {
               Properties properties = new Properties();
               properties.load(stream);
               String packages = (String)properties.get("packages");
               logger.info(String.format("Searching: %s", properties));
               String search = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(packages) + "/**/*.class";
               for(Resource r : resolver.getResources(search)) {
                  logger.debug("Resource: " + r);
                  if(r.isReadable()) {
                     MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(r);
                     String className = metadataReader.getClassMetadata().getClassName();
                     logger.debug(String.format("Resolving class '%s'", className));
                     Class<?> klass = resolver.getClassLoader().loadClass(className);
                     if(Repository.class.isAssignableFrom(klass)) {
                        matches.add(klass);
                     }
                  }
               }
            }
            catch(ClassNotFoundException e) {
               throw new RuntimeException(e);
            }
            finally {
               stream.close();
            }
         }
         return matches;
      }
      catch(IOException e) {
         throw new RuntimeException(e);
      }
   }
}
