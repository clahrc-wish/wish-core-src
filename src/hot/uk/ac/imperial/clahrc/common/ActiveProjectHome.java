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
package uk.ac.imperial.clahrc.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Synchronized;
import org.jboss.seam.framework.EntityHome;

import uk.ac.imperial.clahrc.entity.ProjectTables;
import uk.ac.imperial.clahrc.entity.Projects;
import uk.ac.imperial.clahrc.entity.Roles;
import uk.ac.imperial.clahrc.entity.UserProjectRoles;
import uk.ac.imperial.clahrc.utilities.NameStringUtility;
import uk.ac.imperial.clahrc.utilities.loaders.ProjectIDs;
import uk.ac.imperial.clahrc.utilities.loaders.RoleIDs;

@Name("activeProjectHome")
@Scope(ScopeType.SESSION)
@Synchronized
public class ActiveProjectHome extends EntityHome<Projects> {

	private static final long serialVersionUID = 1L;

	@In CustomIdentity identity;
	@In IdentityHome identityHome;
	@In ProjectIDs projectIDs;
	@In RoleIDs roleIDs;
	@In(create=true) NameStringUtility nameStringUtility;
	
	public void setProjectsId(Integer id) {
		setId(id);
	}

	public Integer getProjectsId() {
		return (Integer) getId();
	}

	@Override
	protected Projects createInstance() {
		Projects projects = new Projects();
		return projects;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}
	
	public void wire(Integer id) {
		if ( (id == null) || (id < 1) ) {
			getInstance();
		} else {
			setId(id);
			setInstance( find() );
		}
	}

	public boolean isWired() {
		return true;
	}

	public Projects getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}
	
	public Roles getRole() {
		
		if ( getDefinedInstance() == null ) {
			return null;
		}
		
		Iterator<UserProjectRoles> UserProjectRolesItr = getInstance().getUserProjectRoleses().iterator();
		while ( UserProjectRolesItr.hasNext() ) {
			UserProjectRoles UserProjectRole = UserProjectRolesItr.next();
			if ( UserProjectRole.getUsers().getId().equals(identity.getId()) ) {
				return UserProjectRole.getRoles();
			}
		}
		
		if ( identity.hasRole(roleIDs.getWRT_ADMIN_id()) ) {
			return identityHome.getUserProjectRoleses().get(0).getRoles();
		}
		
		return null;
	}
	
	public List<SelectItem> getProjectTables() {

		List<SelectItem> projectTablesList = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
		
		Set<ProjectTables> projectTables = this.instance.getProjectTableses();
		projectTablesList.add( new SelectItem( null, "Click to select..." ) );
		for ( ProjectTables projectTable : projectTables ) {
			projectTablesList.add( new SelectItem( projectTable.getId().getTableName().trim(), projectTable.getTableLabel().trim() ) );
		}
		
		return projectTablesList;
	}
	
	public String getTableLabel( String tableName ) {
		
		Set<ProjectTables> projectTables = this.instance.getProjectTableses();
		for ( ProjectTables projectTable : projectTables ) {
			if ( projectTable.getId().getTableName().equalsIgnoreCase( tableName.trim() ) ) {
				return projectTable.getTableLabel();
			}
		}
		return null;
	}	
}
