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

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Properties;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Synchronized;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;

@Name("bugzillaConfigs")
@Scope(ScopeType.APPLICATION)
@Synchronized
@AutoCreate
public class BugzillaConfigs implements Serializable {
	
	private static final Log log = Logging.getLog( BugzillaConfigs.class );
	private static final long serialVersionUID = 1L;

	private final Properties properties = new Properties(); 
	
	public BugzillaConfigs() {
		
		InputStream IStream = null;
		try {
			IStream = this.getClass().getClassLoader().getResourceAsStream( "bugzilla_configs.properties" );
			properties.loadFromXML( IStream );
			log.info( "bugzilla configs: #0", Arrays.toString( properties.keySet().toArray() ) );
			
		} catch ( Throwable exc ) {
			log.error( "loading of mail configs failed", exc );
			
		} finally {
			if ( IStream != null ) { try { IStream.close(); } catch (IOException e) {} }
		}
	}
	
	public String getProperty( String key ) {
		return this.properties.getProperty( key ).trim();
	}
}
