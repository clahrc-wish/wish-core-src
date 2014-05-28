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

import java.util.Date;

import javax.faces.context.FacesContext;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;

import uk.ac.imperial.clahrc.common.IdentityHome;
import uk.ac.imperial.clahrc.common.MailSender;
import uk.ac.imperial.clahrc.entity.Projects;
import uk.ac.imperial.clahrc.entity.Roles;
import uk.ac.imperial.clahrc.entity.UserProjectRoleRequests;
import uk.ac.imperial.clahrc.entity.UserProjectRoleRequestsId;
import uk.ac.imperial.clahrc.entity.UserProjectRolesId;
import uk.ac.imperial.clahrc.entity.Users;

@Name("userProjectRoleRequestsHome")
public class UserProjectRoleRequestsHome extends EntityHome<UserProjectRoleRequests>
{

	private static final long serialVersionUID = 1L;
	
	@Logger private Log log;
	@In FacesContext facesContext;
	@In StatusMessages statusMessages;
	@In IdentityHome identityHome;
	@In(create=true) UsersHome usersHome;
    @In(create=true) ProjectsHome projectsHome;
    @In(create=true) RolesHome rolesHome;
	@In(create = true) MailSender mailSender;

    public void setUserProjectRoleRequestsId(UserProjectRoleRequestsId id)
    {
        setId(id);
    }

    public UserProjectRoleRequestsId getUserProjectRoleRequestsId()
    {
        return (UserProjectRoleRequestsId) getId();
    }

    public UserProjectRoleRequestsHome()
    {
        setUserProjectRoleRequestsId( new UserProjectRoleRequestsId() );
    }

    @Override
    public boolean isIdDefined()
    {
        if ( getUserProjectRoleRequestsId().getUserId()==0 ) return false;
        if ( getUserProjectRoleRequestsId().getProjectId()==0 ) return false;
        return true;
    }

    @Override
    protected UserProjectRoleRequests createInstance()
    {
        UserProjectRoleRequests userProjectRoleRequests = new UserProjectRoleRequests();
        userProjectRoleRequests.setId( new UserProjectRoleRequestsId() );
        return userProjectRoleRequests;
    }

    public void load()
    {
        if (isIdDefined())
        {
            wire();
        }
    }

    public void wire()
    {
        getInstance();
        Users users=usersHome.getDefinedInstance();
        if ( users!=null )
        {
           getInstance().setUsers(users);
		   getInstance().getId().setUserId(users.getId());
        }
        Projects projects=projectsHome.getDefinedInstance();
        if ( projects!=null )
        {
           getInstance().setProjects(projects);
		   getInstance().getId().setProjectId(projects.getId());
        }
        Roles roles=rolesHome.getDefinedInstance();
        if ( roles!=null )
        {
           getInstance().setRoles(roles);
        }
    }

    public boolean isWired()
    {
        if ( getInstance().getUsers()==null ) return false;
        if ( getInstance().getProjects()==null ) return false;
        if ( getInstance().getRoles()==null ) return false;
        return true;
    }

    public UserProjectRoleRequests getDefinedInstance()
    {
        return isIdDefined() ? getInstance() : null;
    }

    // for support of request approval
 	@Transactional
 	public String approve() {

 		String PersistResult = null;
 		String RemoveResult = null;
 		try {
 			UserProjectRolesHome UPRHome = (UserProjectRolesHome)Component.getInstance( UserProjectRolesHome.class, true );
 			UPRHome.getInstance().setId( new UserProjectRolesId(getInstance().getId().getUserId(), getInstance().getId().getProjectId()) );
 			UPRHome.getInstance().setUsers( getInstance().getUsers() );
 			UPRHome.getInstance().setProjects( getInstance().getProjects() );
 			UPRHome.getInstance().setRoles( getInstance().getRoles() );
 			PersistResult = UPRHome.persist();
 			RemoveResult = remove();

 			// user project role approval successful - email notification
 			mailSender.sendNewRole( UPRHome.getInstance().getUsers().getLoginName(),
 							 		UPRHome.getInstance().getUsers().getFirstName(),
 							 		UPRHome.getInstance().getUsers().getLastName(),
 							 		UPRHome.getInstance().getUsers().getEmail(),
 							 		UPRHome.getInstance().getProjects().getShortName(),
 							 		UPRHome.getInstance().getProjects().getDescription(),
 							 		UPRHome.getInstance().getRoles().getName()
 				           		  );
 		} catch ( Throwable exc ) {
 			log.error("user-project-role-request approval failed: ", exc );
 			statusMessages.addToControl(null," Project Role Request approval failed !", new Object[] {});
 			return null;
 		}
 		if ( PersistResult.equalsIgnoreCase("duplicate") ) {
 			log.error( "user-project-role already exists: " );
			statusMessages.addToControl(null, StatusMessage.Severity.ERROR, " User-Project-Role already exists !", new Object[] {});
			return"duplicate";
			
 		}
 		if ( PersistResult == null || PersistResult.isEmpty() || !PersistResult.equalsIgnoreCase("persisted") ||
 			 RemoveResult == null || RemoveResult.isEmpty() || !RemoveResult.equalsIgnoreCase("removed") ) {

 			log.error("user-project-role-request approval did not suceed for: {0}", getInstance().getUsers().getLoginName() + " " + 
 																					getInstance().getProjects().getName() + " " + 
 																					getInstance().getRoles().getName() );
 			statusMessages.addToControl(null," Project Role Request approval failed !", new Object[] {});
 			return null;
 		}
 		return "approved";
 	}
 	
 	// for support of request denial
  	@Transactional
  	public String deny() {

  		String PersistResult = null;
 		String RemoveResult = null;
 		try {
 			UserProjectRolesAuditHome UPRAuditHome = (UserProjectRolesAuditHome)Component.getInstance( UserProjectRolesAuditHome.class, true );
 			UPRAuditHome.getInstance().setUsersByUserId( getInstance().getUsers() );
 			UPRAuditHome.getInstance().setProjects( getInstance().getProjects() );
 			UPRAuditHome.getInstance().setRoles( getInstance().getRoles() );
 			UPRAuditHome.getInstance().setReqTimestamp( getInstance().getRcTimestamp() );
 			UPRAuditHome.getInstance().setAction( "denied" );
 			UPRAuditHome.getInstance().setResTimestamp( new Date() );
 			UPRAuditHome.getInstance().setUsersByAdminId( identityHome.getInstance() );
 			PersistResult = UPRAuditHome.persist();
 			RemoveResult = remove();

 		} catch ( Throwable exc ) {
 			log.error("user-project-role-request denial failed: ", exc );
 			log.error("user-project-role-request denial failed for: {0}", getInstance().getUsers().getLoginName() + " " + 
 		                                                                  getInstance().getProjects().getName() + " " + 
 					                                                      getInstance().getRoles().getName() );
 			
 			statusMessages.addToControl(null," Project Role Request denial failed !", new Object[] {});
 			return null;
 		}
 		if ( PersistResult == null || PersistResult.isEmpty() || !PersistResult.equalsIgnoreCase("persisted") ||
 			 RemoveResult == null || RemoveResult.isEmpty() || !RemoveResult.equalsIgnoreCase("removed") ) {

 			log.error("user-project-role-request denial did not suceed for: {0}", getInstance().getUsers().getLoginName() + " " + 
 																				  getInstance().getProjects().getName() + " " + 
 																				  getInstance().getRoles().getName() );
 			statusMessages.addToControl(null," Project Role Request denial failed !", new Object[] {});
 			return null;
 		}
 		return "denied";
  	}

}

