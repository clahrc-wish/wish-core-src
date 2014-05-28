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
import java.util.Properties;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Synchronized;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;

@Name("distribution")
@Scope(ScopeType.APPLICATION)
@Synchronized
@AutoCreate
public class Distribution implements Serializable {

	private static final Log log = Logging.getLog( Distribution.class );
	private static final long serialVersionUID = 1L;

	private final Properties properties = new Properties();
	
	public Distribution() {
		
		InputStream IStream = null;
		try {
			IStream = this.getClass().getClassLoader().getResourceAsStream( "distribution.properties" );
			properties.loadFromXML( IStream );
			log.info( "distribution properties: #0", properties.toString() );
			
		} catch ( Throwable exc ) {
			log.error( "loading of distribution properties failed", exc );
			
		} finally {
			if ( IStream != null ) { try { IStream.close(); } catch (IOException e) {} }
		}
	}
	
	public String getProperty( String propertyName ) {
		return this.properties.getProperty( propertyName );
	}
}
