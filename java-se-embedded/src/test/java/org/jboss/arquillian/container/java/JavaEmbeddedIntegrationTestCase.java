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

import junit.framework.Assert;

import org.jboss.arquillian.container.java.test.MyBean;
import org.jboss.arquillian.container.java.test.MyExcludedBean;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * WeldEmbeddedIntegrationTestCase
 * 
 * @author <a href="mailto:aslak@redhat.com">Aslak Knutsen</a>
 * @version $Revision: $
 */
@RunWith(Arquillian.class)
public class JavaEmbeddedIntegrationTestCase
{
   @Deployment
   public static JavaArchive createdeployment()
   {
      return ShrinkWrap.create(JavaArchive.class, "test.jar")
               .addClasses(
                        JavaEmbeddedIntegrationTestCase.class,
                        MyBean.class);
   }

   @Test
   public void shouldBeAbleToInstantiateBean() throws Exception
   {
      MyBean instance = new MyBean();
      Assert.assertEquals("lincoln", instance.getName());
   }

   @Test(expected = NoClassDefFoundError.class)
   public void shouldNotBeAbltToReferenceBean() throws Exception
   {
      new MyExcludedBean();
      Assert.fail();
   }
}
