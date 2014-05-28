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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Synchronized;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;

import uk.ac.imperial.clahrc.entity.Projects;

@Name("projectIDs")
@Scope(ScopeType.APPLICATION)
@AutoCreate
@Synchronized
public class ProjectIDs {

	private static final Log log = Logging.getLog( ProjectIDs.class );
    
	///////////////////////////////////////////////
	@SuppressWarnings("unused")
	private int CLAHRC_WISH_id;
	@SuppressWarnings("unused")
	private String CLAHRC_WISH_str;
	@SuppressWarnings("unused")
	///////////////////////////////////////////////
	
	///////////////////////////////////////////////
	public int getCLAHRC_WISH_id() {
		return projectByName.get( "CLAHRC_WISH" ).getId();
	}
	public String getCLAHRC_WISH_str() {
		return projectByName.get( "CLAHRC_WISH" ).getName();
	}
	///////////////////////////////////////////////

	///////////////////////////////////////////////
	//the code below required as a work-around the SEAM's eFing restrictions on 
	// 'where clause' parameter binding in *List.java files
	private Map<Integer, String> IDs_id = Collections.synchronizedMap( new HashMap<Integer, String>() );
	private Map<Integer, String> IDs_str = Collections.synchronizedMap( new HashMap<Integer, String>() );
	private Map<Integer, Projects> projectById = Collections.synchronizedMap( new HashMap<Integer, Projects>() );
	private Map<String, Projects> projectByName = Collections.synchronizedMap( new HashMap<String, Projects>() );
	
	public Map<Integer, String> getIDs_id() {
		return IDs_id;
	}
	public Map<Integer, String> getIDs_str() {
		return IDs_str;
	}
	public Map<Integer, Projects> getProjectById() {
		return projectById;
	}
	
	public ProjectIDs() {
		
		try {
			EntityManager entityManager = (EntityManager) Component.getInstance( "entityManager", true );
			@SuppressWarnings("unchecked")
			List<Projects> ProjectsList = entityManager.createQuery( "SELECT p FROM Projects p" ).getResultList();
		
			if ( ProjectsList == null || ProjectsList.isEmpty() ) {
				log.warn( "projects metadata expected but not found" );
			}
			for ( Projects Project : ProjectsList ) {

				IDs_id.put( Project.getId(), Project.getName() + "_id" );
				////////////////////////////////////
				IDs_str.put( Project.getId(), Project.getName() + "_str" );
				////////////////////////////////////
				projectById.put( Project.getId(), Project );
				////////////////////////////////////
				projectByName.put( Project.getName(), Project );
			}
		} catch ( Throwable exc ) {
			log.error( "loading of projects metadata failed", exc );
		}
	}
	///////////////////////////////////////////////	
}
