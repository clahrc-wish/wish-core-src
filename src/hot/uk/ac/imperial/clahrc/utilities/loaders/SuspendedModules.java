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
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Synchronized;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;

@Name("suspendedModules")
@Scope(ScopeType.APPLICATION)
@Synchronized
@AutoCreate
public class SuspendedModules implements Serializable {

	private static final Log log = Logging.getLog( SuspendedModules.class );
	
	private static final long serialVersionUID = 1L;
	private final Map<String,Boolean> suspendedModules = Collections.checkedMap( new Hashtable<String,Boolean>(), String.class, Boolean.class );

	public SuspendedModules() {
		
		InputStream IStream = null;
		try {
			IStream = this.getClass().getClassLoader().getResourceAsStream( "suspended_modules.properties" );
	    	Properties suspendedProperties = new Properties();
	    	suspendedProperties.loadFromXML( IStream );
	    	Set<Map.Entry<Object,Object>> suspendedEntries = suspendedProperties.entrySet();
	    	
			for ( Map.Entry<Object,Object> entry : suspendedEntries ) {
				suspendedModules.put( ((String)entry.getKey()).trim(), Boolean.valueOf(((String)entry.getValue()).trim()) );

				if ( Boolean.valueOf(((String)entry.getValue()).trim()).booleanValue() ) {
					log.info( "suspended module: #0", ((String)entry.getKey()).trim() );
				}
			}
		} catch ( Throwable exc ) {
			log.error( "loading of suspended modules configuration failed", exc );
			
		} finally {
			if ( IStream != null ) { try { IStream.close(); } catch (IOException e) {} }
		}
	}

	public boolean isSuspended( String module ) {
		return ((module != null && !module.isEmpty()) ? suspendedModules.get( module ).booleanValue() : false);
	}
}
