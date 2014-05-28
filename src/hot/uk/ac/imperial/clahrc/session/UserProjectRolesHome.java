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

import java.util.Map;

import javax.faces.model.SelectItem;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;

import uk.ac.imperial.clahrc.common.MailSender;
import uk.ac.imperial.clahrc.entity.Projects;
import uk.ac.imperial.clahrc.entity.Roles;
import uk.ac.imperial.clahrc.entity.UserProjectRoles;
import uk.ac.imperial.clahrc.entity.UserProjectRolesId;
import uk.ac.imperial.clahrc.entity.Users;
import uk.ac.imperial.clahrc.utilities.UserProjectRolesUtility;

@Name("userProjectRolesHome")
public class UserProjectRolesHome extends EntityHome<UserProjectRoles> {

	@Logger private Log log;
	@In StatusMessages statusMessages;
	@In(create = true) UsersHome usersHome;
	@In(create = true) ProjectsHome projectsHome;
	@In(create = true) RolesHome rolesHome;
	@In(create = true) MailSender mailSender;
	@In(create = true) UserProjectRolesUtility userProjectRolesUtility;

	private static final long serialVersionUID = 1L;

	public void setUserProjectRolesId(UserProjectRolesId id) {
        setId(id);
    }

    public UserProjectRolesId getUserProjectRolesId() {
        return (UserProjectRolesId) getId();
    }

    public UserProjectRolesHome(){
        setUserProjectRolesId( new UserProjectRolesId() );
    }

    @Override
    public boolean isIdDefined() {
        if ( getUserProjectRolesId().getUserId() == 0 ) return false;
        if ( getUserProjectRolesId().getProjectId() == 0 ) return false;
        return true;
    }
    
    @Override
    protected UserProjectRoles createInstance() {
        UserProjectRoles userProjectRoles = new UserProjectRoles();
        userProjectRoles.setId( new UserProjectRolesId() );
        return userProjectRoles;
    }

    public void load() {
        if (isIdDefined()) {
            wire();
        }
    }

    public void wire() {
        getInstance();
        Users users = usersHome.getDefinedInstance();
        if ( users != null ) {
        	getInstance().setUsers( users );
		    getInstance().getId().setUserId( users.getId() );
        }
        Projects projects = projectsHome.getDefinedInstance();
        if ( projects != null ) {
        	getInstance().setProjects( projects );
			getInstance().getId().setProjectId( projects.getId() );
        }
        Roles roles=rolesHome.getDefinedInstance();
        if ( roles != null ) {
        	getInstance().setRoles(roles);
        }
    }

    public boolean isWired() {
        if ( getInstance().getUsers() == null ) return false;
        if ( getInstance().getProjects() == null ) return false;
        if ( getInstance().getRoles() == null ) return false;
        return true;
    }

    public UserProjectRoles getDefinedInstance() {
        return isIdDefined() ? getInstance() : null;
    }

    @Override
	public String persist() {

		String PersistResult = null;
		try {
			PersistResult = super.persist();
			
			// user project role creation successful - email notification
			mailSender.sendNewRole( usersHome.getInstance().getLoginName(),
				             	    usersHome.getInstance().getFirstName(),
				             	    usersHome.getInstance().getLastName(),
				             	    usersHome.getInstance().getEmail(),
				             	    projectsHome.getInstance().getShortName(),
				             	    projectsHome.getInstance().getDescription(),
				             	    rolesHome.getInstance().getName()
				           		  );
			
		} catch ( EntityExistsException exc ) {
			log.error( "user-project-role already exists: ", exc );
			statusMessages.addToControl(null, StatusMessage.Severity.ERROR, " User-Project-Role already exists !", new Object[] {});
			return"duplicate";
			
		} catch ( Exception exc ) {
			
			log.error( "user-project-role persisting or emailing failed: ", exc );
			statusMessages.addToControl(null, StatusMessage.Severity.ERROR, " Creation of User-Project-Role failed !", new Object[] {});
		}
		return PersistResult;
	}
	
	// for support of user account creation
	@SuppressWarnings("unchecked")
	@Transactional
	public String persistForNewUser() {

		// set the Login_Name again - the auto set up on the UsersCreate page nullifies it for some reason
		usersHome.getInstance().setLoginName(usersHome.getInstance().getFirstName().toLowerCase().trim().replace(" ", "") +
				                             "." +
				                             usersHome.getInstance().getLastName().toLowerCase().trim().replace(" ", ""));
		
		Users User = null;
		try {
			User = (Users) usersHome.getEntityManager()
					                .createQuery("SELECT u FROM Users u WHERE lower(u.loginName) like lower(:loginname)")
					                .setParameter("loginname", usersHome.getInstance().getFirstName().trim().replace(" ", "") +
				                                               "." +
					                		                   usersHome.getInstance().getLastName().trim().replace(" ", ""))
					                .getSingleResult();

		} catch (NoResultException e) {
		} catch (Exception e) {
			log.error("user look up failed for: {0}", usersHome.getInstance().getFirstName() + " " + usersHome.getInstance().getLastName());
			statusMessages.addToControl(null, StatusMessage.Severity.ERROR, " User name look up failed !", new Object[] {});
		}
		if ( User != null ) {

			log.error("login name already exists: {0}", usersHome.getInstance().getFirstName() + " " + usersHome.getInstance().getLastName());
			statusMessages.addToControl(null, StatusMessage.Severity.ERROR, " Account with this user name already exists !", new Object[] {});
			return "accountExists";
		}

		usersHome.persist();
		
		String PersistResult = null;
		// iterate trough the project roles selection list and persist
		if ( !userProjectRolesUtility.getSelectedProjectRoles().isEmpty() ) {

			for ( SelectItem SelectedEntry : userProjectRolesUtility.getSelectedProjectRoles() ) {
				
				getInstance().getId().setUserId( usersHome.getUsersId() );
				getInstance().getId().setProjectId( ((Map.Entry<Projects, Roles>)SelectedEntry.getValue()).getKey().getId() ); 
				projectsHome.setId( ((Map.Entry<Projects, Roles>)SelectedEntry.getValue()).getKey().getId() );
				projectsHome.wire();
				rolesHome.setId( ((Map.Entry<Projects, Roles>)SelectedEntry.getValue()).getValue().getId() );
				rolesHome.wire();
				wire();
				PersistResult = persist(); // persist the new user_project_role and send email notification
				setId( new UserProjectRolesId( 0, 0 ) );
			}

		// the project roles selection list is empty - use the Project and Role field values - they should not be empty
		} else if ( getInstance().getId().getProjectId() != 0 && getInstance().getRoles().getId() != null ) {

			getInstance().getId().setUserId( usersHome.getUsersId() );
			projectsHome.setId( getInstance().getId().getProjectId() );
			projectsHome.wire();
			rolesHome.setId( getInstance().getRoles().getId() );
			rolesHome.wire();
			wire();
			PersistResult = persist(); // persist the new user_project_role and send email notification

		} else {
			log.error("user_project_role(s) incorrectly set for: {0}", usersHome.getInstance().getFirstName() + " " + usersHome.getInstance().getLastName());
			statusMessages.addToControl(null, StatusMessage.Severity.ERROR, " Incorrect Project(s) and/or Role(s) !", new Object[] {});
		}

		getEntityManager().refresh(usersHome.getInstance());
		// user account successful - email notification
		mailSender.sendNewAccount( usersHome.getInstance().getLoginName(),
  					               usersHome.getInstance().getPassword(), 
						           usersHome.getInstance().getFirstName(),
						           usersHome.getInstance().getLastName(),
						           usersHome.getInstance().getEmail()
						         );
		return PersistResult;
	}
}
