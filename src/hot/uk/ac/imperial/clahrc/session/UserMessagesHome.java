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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.faces.context.FacesContext;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;

import uk.ac.imperial.clahrc.common.ActiveProjectHome;
import uk.ac.imperial.clahrc.common.CustomIdentity;
import uk.ac.imperial.clahrc.common.IdentityHome;
import uk.ac.imperial.clahrc.entity.Projects;
import uk.ac.imperial.clahrc.entity.UserMessages;
import uk.ac.imperial.clahrc.entity.UserMessagesId;
import uk.ac.imperial.clahrc.entity.Users;

@Name("userMessagesHome")
public class UserMessagesHome extends EntityHome<UserMessages>
{

	private static final long serialVersionUID = 1L;
	
	@Logger private Log log;
	@In CustomIdentity identity;
	@In IdentityHome identityHome;
	@In FacesContext facesContext;
	@In(create=true) ActiveProjectHome activeProjectHome;
	@In(create=true) CommentHome commentHome;

	UsersHome usersHome = new UsersHome();
    ProjectsHome projectsHome = new ProjectsHome();
    List<Projects> selectedProjects = new ArrayList<Projects>();
    
    public List<Projects> getSelectedProjects() {
		return selectedProjects;
	}

	public void setSelectedProjects(List<Projects> selectedProjects) {
		this.selectedProjects = selectedProjects;
	}

	public void setUserMessagesId(UserMessagesId id)
    {
        setId(id);
    }

    public UserMessagesId getUserMessagesId()
    {
        return (UserMessagesId) getId();
    }

    public UserMessagesHome()
    {
        setUserMessagesId( new UserMessagesId() );
    }

    @Override
    public boolean isIdDefined()
    {
        if ( getUserMessagesId().getUserId()==0 ) return false;
        if ( getUserMessagesId().getProjectId()==0 ) return false;
        if ( getUserMessagesId().getRcTimestamp()==null ) return false;
        return true;
    }

    @Override
    protected UserMessages createInstance()
    {
        UserMessages userMessages = new UserMessages();
        userMessages.setId( new UserMessagesId() );
        return userMessages;
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
    	setInstance( createInstance() );
        usersHome.setUsersId( identity.getId() );
    	projectsHome.setProjectsId( (activeProjectHome.getDefinedInstance() != null ? 
    								 activeProjectHome.getDefinedInstance().getId() : 
    								 identityHome.getUserProjectRoleses().get(0).getProjects().getId()));
        getInstance().getId().setUserId( usersHome.getUsersId() );
        getInstance().getId().setProjectId( projectsHome.getProjectsId() );
        selectedProjects = Collections.emptyList();
        
        Users users=usersHome.getDefinedInstance();
        if ( users!=null )
        {
           getInstance().setUsers(users);
        }
        Projects projects=projectsHome.getDefinedInstance();
        if ( projects!=null )
        {
           getInstance().setProjects(projects);
        }
    }

    public boolean isWired()
    {
        if ( getInstance().getUsers()==null ) return false;
        if ( getInstance().getProjects()==null ) return false;
        return true;
    }

    public UserMessages getDefinedInstance()
    {
        return isIdDefined() ? getInstance() : null;
    }

    @Override
	public String persist() {
    	
    	if ( !isWired() ) { // added for seam.2.3.0.Final/jsf2.0 upgrade
			usersHome.setUsersId( identity.getId() );
	    	projectsHome.setProjectsId( activeProjectHome.getDefinedInstance().getId() );
	        getInstance().getId().setUserId( usersHome.getUsersId() );
	        getInstance().getId().setProjectId( projectsHome.getProjectsId() );
            getInstance().setUsers(usersHome.getDefinedInstance());
            getInstance().setProjects(projectsHome.getDefinedInstance());
		}
		getInstance().getId().setRcTimestamp( new Timestamp(Calendar.getInstance().getTimeInMillis()) );
		return super.persist();
	}

	@Transactional
	public String persistPerProject() {
		
		String Message = getInstance().getMessage();
		if ( !isWired() ) {// added for seam.2.3.0.Final/jsf2.0 upgrade

			usersHome.setUsersId( identity.getId() );
			getInstance().getId().setUserId( usersHome.getUsersId() );
			getInstance().setUsers(usersHome.getDefinedInstance());
		}
		
		for ( Projects UserProject : selectedProjects ) {

			getInstance().getId().setProjectId(UserProject.getId());
			getInstance().setProjects(UserProject);
			getInstance().setMessage(Message);
				
			getInstance().getId().setRcTimestamp( new Timestamp(Calendar.getInstance().getTimeInMillis()) );
			super.persist();
			wire();
		}
		return "persisted";
	}

	@Transactional
	public String promoteToComment( UserMessages UserMessage ) {

		commentHome.getInstance().setComment( UserMessage.getMessage() );
		commentHome.getInstance().setEventDate( UserMessage.getId().getRcTimestamp() );
		commentHome.getInstance().setProjects( UserMessage.getProjects() );
		commentHome.getInstance().setUsers( UserMessage.getUsers() );
		commentHome.persist();
		UserMessage.setComment( commentHome.getDefinedInstance() );
		getInstance().setId( UserMessage.getId() );
		setInstance( UserMessage );
		update();
		wire();
		commentHome.setInstance( commentHome.createInstance() );
		return "home";
	}
	
	@Transactional
	public String delete( UserMessages UserMessage ) {

		getInstance().setId( UserMessage.getId() );
		setInstance( UserMessage );
		remove();
		wire();
		return "home";
	}
}

