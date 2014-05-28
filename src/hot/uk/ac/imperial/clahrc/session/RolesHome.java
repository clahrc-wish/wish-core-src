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
package uk.ac.imperial.clahrc.session;

import uk.ac.imperial.clahrc.entity.*;

import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("rolesHome")
public class RolesHome extends EntityHome<Roles> {

	private static final long serialVersionUID = 1L;

	public void setRolesId(Integer id) {
		setId(id);
	}

	public Integer getRolesId() {
		return (Integer) getId();
	}

	@Override
	protected Roles createInstance() {
		Roles roles = new Roles();
		return roles;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Roles getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<UserProjectRoles> getUserProjectRoleses() {
		return getInstance() == null ? null : new ArrayList<UserProjectRoles>(
				getInstance().getUserProjectRoleses());
	}

	public List<UserProjectRoleRequests> getUserProjectRoleRequestses() {
        return getInstance() == null ?
            null : new ArrayList<UserProjectRoleRequests>( getInstance().getUserProjectRoleRequestses() );
    }
	
    public List<UserProjectRolesAudit> getUserProjectRolesAudits() {
        return getInstance() == null ?
            null : new ArrayList<UserProjectRolesAudit>( getInstance().getUserProjectRolesAudits() );
    }
    
    // a work-around the RF4 'tabPanel' throwing exceptions when no child tabs are rendered
 	// 'Role Assignments' tabPanel in Roles and RolesEdit pages
 	// it's non-sensical the way it works
 	public boolean anyActivities() {

		if ( getUserProjectRoleses() != null && !getUserProjectRoleses().isEmpty() ) { return true; }
		if ( getUserProjectRoleRequestses() != null && !getUserProjectRoleRequestses().isEmpty() ) { return true; }
		if ( getUserProjectRolesAudits() != null && !getUserProjectRolesAudits().isEmpty() ) { return true; }
 		return false;
 	}
}
