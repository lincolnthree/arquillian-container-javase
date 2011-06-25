/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.container.java;

import org.jboss.arquillian.container.java.shrinkwrap.ShrinkwrapJavaDeploymentArchive;
import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;
import org.jboss.arquillian.container.spi.client.protocol.ProtocolDescription;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.arquillian.container.spi.context.annotation.DeploymentScoped;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.descriptor.api.Descriptor;

/**
 * WeldSEContainer
 * 
 * @author <a href="mailto:aslak@conduct.no">Aslak Knutsen</a>
 * @version $Revision: $
 */
public class JavaContainer implements DeployableContainer<JavaConfiguration>
{
   @Inject
   @DeploymentScoped
   private InstanceProducer<ContextClassLoaderManager> classLoaderManagerProducer;

   public ProtocolDescription getDefaultProtocol()
   {
      return new ProtocolDescription("Local");
   }

   public Class<JavaConfiguration> getConfigurationClass()
   {
      return JavaConfiguration.class;
   }

   public void setup(final JavaConfiguration configuration)
   {
   }

   public void start() throws LifecycleException
   {
   }

   public void stop() throws LifecycleException
   {
   }

   public void deploy(final Descriptor descriptor) throws DeploymentException
   {
      throw new UnsupportedOperationException("Descriptors not supported by Java SE");
   }

   public void undeploy(final Descriptor descriptor) throws DeploymentException
   {
      throw new UnsupportedOperationException("Descriptors not supported by Java SE");
   }

   public ProtocolMetaData deploy(final Archive<?> archive) throws DeploymentException
   {
      final ShrinkwrapJavaDeploymentArchive beanArchive = archive.as(ShrinkwrapJavaDeploymentArchive.class);

      ContextClassLoaderManager classLoaderManager = new ContextClassLoaderManager(beanArchive.getClassLoader());
      classLoaderManager.enable();

      classLoaderManagerProducer.set(classLoaderManager);

      return new ProtocolMetaData(); // local execution only, not specific protocol metadata needed
   }

   public void undeploy(final Archive<?> archive) throws DeploymentException
   {
      ContextClassLoaderManager classLoaderManager = classLoaderManagerProducer.get();
      classLoaderManager.disable();
   }
}