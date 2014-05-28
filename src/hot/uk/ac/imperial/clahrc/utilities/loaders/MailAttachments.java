/*******************************************************************************
 * Copyright 2013 Imperial College London
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package uk.ac.imperial.clahrc.utilities.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Synchronized;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;

@Name("mailAttachments")
@Scope(ScopeType.APPLICATION)
@Synchronized
@AutoCreate
public class MailAttachments implements Serializable {

	private static final Log log = Logging.getLog( MailAttachments.class );
	private static final long serialVersionUID = 1L;

	private final List<File> attachments = Collections.checkedList( new ArrayList<File>(), File.class ); 
	
	public MailAttachments() {
		
		BufferedReader BReader = null;
		try {
			File attachmentsFile = new File( this.getClass().getClassLoader().getResource( "mail_attachments" ).toURI() );
			BReader = new BufferedReader( new FileReader( attachmentsFile ) );
		    for( String attachment; (attachment = BReader.readLine()) != null; ) {
		    	attachments.add( new File( this.getClass().getClassLoader().getResource( attachment.trim() ).toURI() ) );
		    	log.info( "mail attachment: #0", attachment.trim() );
		    }
		} catch ( Throwable exc ) {
			log.error( "loading of mail attachments failed", exc );
			
		} finally {
			if ( BReader != null ) { try { BReader.close(); } catch (IOException e) {} }
		}
	}
	
	public List<File> getAttachments() {
		return this.attachments;
	}
}
