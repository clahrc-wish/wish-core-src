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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Synchronized;
import org.jboss.seam.framework.EntityHome;

import uk.ac.imperial.clahrc.entity.LoginAudit;
import uk.ac.imperial.clahrc.entity.Projects;
import uk.ac.imperial.clahrc.entity.UserMessages;
import uk.ac.imperial.clahrc.entity.UserProjectRoles;
import uk.ac.imperial.clahrc.entity.Users;

@Name("identityHome")
@Scope(ScopeType.SESSION)
@Synchronized
public class IdentityHome extends EntityHome<Users> {

	private static final long serialVersionUID = 1L;
	
	@In CustomIdentity identity;
	
	public void setUsersId(Integer id) {
		setId(id);
	}

	public Integer getUsersId() {
		return (Integer) getId();
	}

	@Override
	protected Users createInstance() {
		Users users = new Users();
		return users;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		setId(identity.getId());
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Users getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}
	
	private List<LoginAudit> getLoginAudits() {
		return getInstance() == null ? null : new ArrayList<LoginAudit>(
				getInstance().getLoginAudits());
	}
	
	public List<UserProjectRoles> getUserProjectRoleses() {
		return getInstance() == null ? null : new ArrayList<UserProjectRoles>(
				getInstance().getUserProjectRoleses());
	}
	
	public List<UserMessages> getUserMessageses() {
        return getInstance() == null ?
            null : new ArrayList<UserMessages>( getInstance().getUserMessageses() );
    }
	
	public Date getLastLogin() {
		
		List<LoginAudit> LoginAudits = getLoginAudits();
		List<Date> LoginDates = Collections.checkedList( new ArrayList<Date>(), Date.class );
		for ( LoginAudit Audit : LoginAudits ) {
			LoginDates.add( Audit.getId().getLoggedInDateTime() );
		}
		Collections.sort( LoginDates );
		Collections.reverse( LoginDates );
		return ( (LoginDates.size() < 2) ? LoginDates.get(0) : LoginDates.get(1) );
	}
	
	public Set<String> getRoleNames() {

		Set<String> RolesSet = Collections.checkedSet( new HashSet<String>(), String.class );
		Set<UserProjectRoles> ProjectRoles = getInstance().getUserProjectRoleses();
		for ( UserProjectRoles ProjectRole : ProjectRoles ) {
			RolesSet.add( ProjectRole.getRoles().getName() );
		}
		return RolesSet;
	}
	
	public Set<Integer> getRoleIds() {

		Set<Integer> RolesSet = Collections.checkedSet( new HashSet<Integer>(), Integer.class );
		Set<UserProjectRoles> ProjectRoles = getInstance().getUserProjectRoleses();
		for ( UserProjectRoles ProjectRole : ProjectRoles ) {
			RolesSet.add( ProjectRole.getRoles().getId() );
		}
		return RolesSet;
	}

	public Set<String> getProjectNames() {

		Set<String> ProjectsSet = Collections.checkedSet( new HashSet<String>(), String.class );
		Set<UserProjectRoles> ProjectRoles = getInstance().getUserProjectRoleses();
		for ( UserProjectRoles ProjectRole : ProjectRoles ) {
			ProjectsSet.add( ProjectRole.getProjects().getName() );
		}
		return ProjectsSet;
	}
	
	public Set<Integer> getProjectIds() {

		Set<Integer> ProjectsSet = Collections.checkedSet( new HashSet<Integer>(), Integer.class );
		Set<UserProjectRoles> ProjectRoles = getInstance().getUserProjectRoleses();
		for ( UserProjectRoles ProjectRole : ProjectRoles ) {
			ProjectsSet.add( ProjectRole.getProjects().getId() );
		}
		return ProjectsSet;
	}
	
	//To deal with the project selection and activeProjectHome initialization
	private Projects Project;
	public Projects getProject() {
		return Project;
	}
	public void setProject(Projects project) {
		Project = project;
	}
	//
}
