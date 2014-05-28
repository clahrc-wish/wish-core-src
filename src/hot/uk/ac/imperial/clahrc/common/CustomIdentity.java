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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpServletRequest;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.Synchronized;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.core.Events;
import org.jboss.seam.log.LogProvider;
import org.jboss.seam.log.Logging;
import org.jboss.seam.security.Identity;

import uk.ac.imperial.clahrc.entity.UserProjectRoles;
import uk.ac.imperial.clahrc.entity.Users;

@Name("org.jboss.seam.security.identity")
@Scope(ScopeType.SESSION)
@Install(precedence = Install.APPLICATION)
@BypassInterceptors
@Startup
@Synchronized
public class CustomIdentity extends Identity {

	private static final long serialVersionUID = 1L;
	private static final LogProvider log = Logging.getLogProvider( CustomIdentity.class );
	private Users User;

	public Integer getId() {
		return User.getId();
	}
	
	public Users getUser() {
		return User;
	}
	
	public void setUser(Users user) {
		User = user;
	}
	
	@Override
	public boolean hasRole( String roleName ) {

		Set<UserProjectRoles> ProjectRoles = User.getUserProjectRoleses();
		for ( UserProjectRoles UserProjectRole : ProjectRoles ) {
			if ( UserProjectRole.getRoles().getName().equals( roleName ) ) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasRole( int roleId ) {

		Set<UserProjectRoles> ProjectRoles = User.getUserProjectRoleses();
		for ( UserProjectRoles UserProjectRole : ProjectRoles ) {
			if ( UserProjectRole.getRoles().getId() <= roleId ) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean hasPermission( String projectName, String roleName, Object... arg ) {

		Set<UserProjectRoles> ProjectRoles = User.getUserProjectRoleses();
		for ( UserProjectRoles UserProjectRole : ProjectRoles ) {
			if ( UserProjectRole.getProjects().getName().equals( projectName ) &&
				 UserProjectRole.getRoles().getName().equals( roleName ) ) {
				return true;
			}
		}
		return false;
	}

	public boolean hasPermission( int projectId, int roleId, Object... arg ) {

		Set<UserProjectRoles> ProjectRoles = User.getUserProjectRoleses();
		for ( UserProjectRoles UserProjectRole : ProjectRoles ) {
			if ( UserProjectRole.getProjects().getId() == projectId &&
				 UserProjectRole.getRoles().getId() <= roleId ) {
				return true;
			}
		}
		return this.hasRole( 2 ); // allow top admins
	}
	
	public Integer getTopRoleId() {
		
		Set<Integer> RolesSet = Collections.checkedSet( new HashSet<Integer>(), Integer.class );
		Set<UserProjectRoles> ProjectRoles = User.getUserProjectRoleses();
		for ( UserProjectRoles ProjectRole : ProjectRoles ) {
			RolesSet.add( ProjectRole.getRoles().getId() );
		}
		return Collections.min( RolesSet );
	}
	
	@Observer("org.jboss.seam.beforePhase")
	public void beforePhase( PhaseEvent event ) {
		
	  if(event.getPhaseId() == PhaseId.RESTORE_VIEW) {
	    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    if(request.getRequestedSessionId() != null && request.getSession().isNew()) {
	    	Events.instance().raiseEvent( "security.sessionExpired" );
	    }
	  }
	}

	@Observer("security.sessionExpired")
    public void sessionExpired( Component event ) {
		//throw new ViewExpiredException();
		log.warn("caught sessionExpired event");
    }
}
