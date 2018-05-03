/*
 * Copyright 2011 by Graz University of Technology, Austria
 * MOCCA has been developed by the E-Government Innovation Center EGIZ, a joint
 * initiative of the Federal Chancellery Austria and Graz University of Technology.
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


package at.gv.egiz.bku.slcommands.impl.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import at.gv.egiz.bku.gui.viewer.MimeTypes;
import at.gv.egiz.stal.HashDataInput;

public class CMSHashDataInput implements HashDataInput {

  public final static String DEFAULT_FILENAME = "SignatureData";

  private byte[] data;
  private byte[] digest;
  protected String mimeType;
  private String referenceId;
  private String fileName;

  public CMSHashDataInput(byte[] data, String mimeType) {
    this.data = data;
    this.mimeType = mimeType;
  }

  public CMSHashDataInput(byte[] data, String mimeType, byte[] digest) {
    this.data = data;
    this.mimeType = mimeType;
  }

	public CMSHashDataInput() {
	}

  @Override
  public String getReferenceId() {

    if (referenceId != null) {
      return referenceId;
    }
    return CMS_DEF_REFERENCE_ID;
  }

  @Override
  public String getMimeType() {
    return mimeType;
  }

  @Override
  public String getEncoding() {
    return null;
  }

  @Override
  public String getFilename() {
	  if (fileName != null) {
		  return fileName;
	  }

	  if (mimeType != null) {
    return DEFAULT_FILENAME + MimeTypes.getExtension(mimeType);
  }

	  return DEFAULT_FILENAME;
  }

  @Override
  public InputStream getHashDataInput() throws IOException {
    return new ByteArrayInputStream(data);
  }

  @Override
  public byte[] getDigest() {
    return digest;
  }


  public void setFilename(String fileName) {
    this.fileName = fileName;
  }
 
  public void setDigest(byte[] digest) {
    this.digest = digest;
  }

  public void setReferenceId(String referenceId) {
    this.referenceId = referenceId;
  }

}
