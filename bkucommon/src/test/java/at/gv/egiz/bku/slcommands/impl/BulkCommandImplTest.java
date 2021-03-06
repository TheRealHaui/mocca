/*
 * Copyright 2015 Datentechnik Innovation and Prime Sign GmbH, Austria
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * http://www.osor.eu/eupl/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * This product combines work with different licenses. See the "NOTICE" text
 * file for details on the various modules and licenses.
 * The "NOTICE" text file is part of the distribution. Any derivative works
 * that you distribute must include a readable copy of the "NOTICE" text file.
 */

package at.gv.egiz.bku.slcommands.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import iaik.asn1.ObjectID;
import iaik.cms.SignedData;
import iaik.xml.crypto.XSecProvider;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import at.buergerkarte.namespaces.securitylayer._1_2_3.BulkResponseType;
import at.gv.egiz.bku.slcommands.BulkCommand;
import at.gv.egiz.bku.slcommands.BulkSignatureResult;
import at.gv.egiz.bku.slcommands.SLCommand;
import at.gv.egiz.bku.slcommands.SLCommandContext;
import at.gv.egiz.bku.slcommands.SLCommandFactory;
import at.gv.egiz.bku.slcommands.SLResult;
import at.gv.egiz.bku.utils.urldereferencer.URLDereferencer;
import at.gv.egiz.stal.STAL;
import at.gv.egiz.stal.STALFactory;

public class BulkCommandImplTest {

  protected static ApplicationContext appCtx;
  private SLCommandFactory factory;

  private STAL stal;

  private URLDereferencer urlDereferencer;

  @BeforeClass
  public static void setUpClass() {
    appCtx = new ClassPathXmlApplicationContext("at/gv/egiz/bku/slcommands/testApplicationContext.xml");
    XSecProvider.addAsProvider(true);
  }

  @Before
  public void setUp() throws JAXBException {

    Object bean = appCtx.getBean("slCommandFactory");
    assertTrue(bean instanceof SLCommandFactory);

    factory = (SLCommandFactory) bean;

    bean = appCtx.getBean("stalFactory");
    assertTrue(bean instanceof STALFactory);

    stal = ((STALFactory) bean).createSTAL();

    bean = appCtx.getBean("urlDereferencer");
    assertTrue(bean instanceof URLDereferencer);

    urlDereferencer = (URLDereferencer) bean;

  }

  @Test
  public void testCreateCMSSignatureRequest() throws Exception {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
        "at/gv/egiz/bku/slcommands/bulksignaturerequest/BulkSignatureRequest.xml");
    assertNotNull(inputStream);

    SLCommand command = factory.createSLCommand(new StreamSource(new InputStreamReader(inputStream)));
    assertTrue(command instanceof BulkCommand);

    SLCommandContext context = new SLCommandContext(stal, urlDereferencer, null);
    SLResult result = command.execute(context);

    assertTrue(result instanceof BulkSignatureResult);

    BulkSignatureResult bulkResult = (BulkSignatureResult) result;
    System.out.println(bulkResult.getContent());

    bulkResult.getContent();

    // unmarshall response
    Unmarshaller unmarshaller = factory.getJaxbContext().createUnmarshaller();

    BulkResponseType response = unmarshaller.unmarshal(bulkResult.getContent(), BulkResponseType.class).getValue();

    // verify ContentType of singature
    byte[] cmsSignature = response.getCreateSignatureResponse().get(0).getCreateCMSSignatureResponse()
        .getCMSSignature();
    SignedData signedData = new SignedData(new ByteArrayInputStream(cmsSignature));

    assertNotNull(signedData);
    assertEquals(ObjectID.pkcs7_signedData, signedData.getContentType());
    assertNotNull(response.getCreateSignatureResponse());
    assertEquals(2, response.getCreateSignatureResponse().size());

    result.writeTo(new StreamResult(System.out), false);

  }

}
