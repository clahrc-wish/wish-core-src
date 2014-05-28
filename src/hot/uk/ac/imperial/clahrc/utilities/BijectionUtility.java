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
package uk.ac.imperial.clahrc.utilities;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.contexts.Contexts;

@Name("bijectionUtility")
public class BijectionUtility implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;	
	
	public void clearInstances() {
		this.clearInstances( null );
	}
	
	// destroy users, projects and roles Home objects
	public void clearInstances( String ComponentName ) {
		
		Contexts.getApplicationContext().remove( "usersHome" );
		Contexts.getBusinessProcessContext().remove( "usersHome" );
		Contexts.getConversationContext().remove( "usersHome" );
		Contexts.getEventContext().remove( "usersHome" );
		Contexts.getMethodContext().remove( "usersHome" );
		Contexts.getPageContext().remove( "usersHome" );
		Contexts.getSessionContext().remove( "usersHome" );
		
		Contexts.getApplicationContext().remove( "projectsHome" );
		Contexts.getBusinessProcessContext().remove( "projectsHome" );
		Contexts.getConversationContext().remove( "projectsHome" );
		Contexts.getEventContext().remove( "projectsHome" );
		Contexts.getMethodContext().remove( "projectsHome" );
		Contexts.getPageContext().remove( "projectsHome" );
		Contexts.getSessionContext().remove( "projectsHome" );
		
		Contexts.getApplicationContext().remove( "rolesHome" );
		Contexts.getBusinessProcessContext().remove( "rolesHome" );
		Contexts.getConversationContext().remove( "rolesHome" );
		Contexts.getEventContext().remove( "rolesHome" );
		Contexts.getMethodContext().remove( "rolesHome" );
		Contexts.getPageContext().remove( "rolesHome" );
		Contexts.getSessionContext().remove( "rolesHome" );
		
		// destroy other Home object
		if ( (ComponentName != null) && !ComponentName.isEmpty() ) {
			
			Contexts.getApplicationContext().remove( ComponentName );
			Contexts.getBusinessProcessContext().remove( ComponentName );
			Contexts.getConversationContext().remove( ComponentName );
			Contexts.getEventContext().remove( ComponentName );
			Contexts.getMethodContext().remove( ComponentName );
			Contexts.getPageContext().remove( ComponentName );
			Contexts.getSessionContext().remove( ComponentName );
		}
	}
}
