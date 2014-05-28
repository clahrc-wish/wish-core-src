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

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import uk.ac.imperial.clahrc.entity.Projects;
import uk.ac.imperial.clahrc.entity.Roles;
import uk.ac.imperial.clahrc.session.ProjectsHome;
import uk.ac.imperial.clahrc.session.RolesHome;
import uk.ac.imperial.clahrc.session.UsersHome;

@Name("userProjectRolesUtility")
@Scope(ScopeType.PAGE)
public class UserProjectRolesUtility implements Serializable {
	
	@In(create = true) UsersHome usersHome;
	@In(create = true) ProjectsHome projectsHome;
	@In(create = true) RolesHome rolesHome;
	
	private static final long serialVersionUID = 1L;
	
	private List<SelectItem> SelectedProjectRoles = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
	private Integer SelectedProjectId = null;
	private Roles SelectedRole = null;
	
	public List<SelectItem> getSelectedProjectRoles() {
		return SelectedProjectRoles;
	}
	
    @SuppressWarnings("unchecked")
	public void pushSelectedProjectId( ValueChangeEvent Event ) {
    	this.SelectedProjectId = (Integer)Event.getNewValue();

    	//if already into a ProjectRole selection - ignore
		if ( !this.SelectedProjectRoles.isEmpty() ) {
			for ( SelectItem ProjectRoleSelection : this.SelectedProjectRoles ) {
				if ( ((Map.Entry<Projects, Roles>)ProjectRoleSelection.getValue()).getKey().getId().equals( SelectedProjectId ) ) {
					this.SelectedProjectId = null; 
				}
			}
		}
    }
	
	public void pushSelectedRole( ValueChangeEvent Event ) {
		this.SelectedRole = (Roles)Event.getNewValue();
    }

	public void addProjectRoleSelection() {

		if ( (this.SelectedProjectId != null) && (this.SelectedProjectId != 0) && 
			 (this.SelectedRole != null)) {

			projectsHome.setId( SelectedProjectId );
			projectsHome.wire();
			rolesHome.setId( SelectedRole.getId() );
			rolesHome.wire();

			Map.Entry<Projects, Roles> ProjectRoleSelection = new AbstractMap.SimpleEntry<Projects,Roles>( projectsHome.getInstance(), rolesHome.getInstance() );
			this.SelectedProjectRoles.add( new SelectItem(ProjectRoleSelection, projectsHome.getInstance().getShortName() + " - " + rolesHome.getInstance().getName()) );
		
			projectsHome.clearInstance();
			rolesHome.clearInstance();
			this.SelectedProjectId = null;
		}
	}
	
	public void removeProjectRoleSelection() {
		if ( !this.SelectedProjectRoles.isEmpty() ) {
			this.SelectedProjectRoles.remove( this.SelectedProjectRoles.size() - 1 );
		}
	}
}
