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
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Synchronized;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;

@Name("mailTemplates")
@Scope(ScopeType.APPLICATION)
@Synchronized
@AutoCreate
public class MailTemplates implements Serializable {

	private static final Log log = Logging.getLog( MailTemplates.class );
	private static final long serialVersionUID = 1L;

	private final Map<String, String> templates = Collections.checkedMap( new Hashtable<String,String>(), String.class, String.class ); 
	
	public MailTemplates() {
		
		InputStream IStream = null;
		BufferedReader BReader = null;
		try {
			File templatesFile = new File( this.getClass().getClassLoader().getResource( "mail_templates" ).toURI() );
			BReader = new BufferedReader( new FileReader( templatesFile ) );
			
		    for( String template; (template = BReader.readLine()) != null; ) {
		    	
		    	IStream = this.getClass().getClassLoader().getResourceAsStream( template.trim() );
				templates.put( template.trim(), (new Scanner( IStream )).useDelimiter( "\\A" ).next() );
				log.info( "mail template: #0", template.trim() );
				IStream.close();
		    }
		} catch ( Throwable exc ) {
			log.error( "loading of mail templates failed", exc );
			
		} finally {
			if ( IStream != null ) { try { IStream.close(); } catch (IOException e) {} }
			if ( BReader != null ) { try { BReader.close(); } catch (IOException e) {} }
		}
	}
	
	public String getTemplate( String key ) {
		return this.templates.get( key );
	}
}
