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

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;

import uk.ac.imperial.clahrc.common.MailSender;
import uk.ac.imperial.clahrc.entity.Comment;
import uk.ac.imperial.clahrc.entity.LoginAudit;
import uk.ac.imperial.clahrc.entity.Pdsa;
import uk.ac.imperial.clahrc.entity.Ppi;
import uk.ac.imperial.clahrc.entity.Sustainability;
import uk.ac.imperial.clahrc.entity.UserMessages;
import uk.ac.imperial.clahrc.entity.UserProjectRoleRequests;
import uk.ac.imperial.clahrc.entity.UserProjectRoles;
import uk.ac.imperial.clahrc.entity.UserProjectRolesAudit;
import uk.ac.imperial.clahrc.entity.Users;

@Name("usersHome")
public class UsersHome extends EntityHome<Users> {

	@Logger private Log log;
	@In Credentials credentials;
	@In(create = true) MailSender mailSender;
	
	private static final long serialVersionUID = 1L;
	private String oldPassword = null;

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
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Users getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}
	
	public List<UserMessages> getUserMessageses() {
        return getInstance() == null ?
            null : new ArrayList<UserMessages>( getInstance().getUserMessageses() );
    }

	public List<UserProjectRolesAudit> getUserProjectRolesAuditsForAdminId() {
        return getInstance() == null ?
            null : new ArrayList<UserProjectRolesAudit>( getInstance().getUserProjectRolesAuditsForAdminId() );
    }
	
	public List<LoginAudit> getLoginAudits() {
		return getInstance() == null ? null : new ArrayList<LoginAudit>(
				getInstance().getLoginAudits());
	}

	public List<UserProjectRolesAudit> getUserProjectRolesAuditsForUserId() {
        return getInstance() == null ?
            null : new ArrayList<UserProjectRolesAudit>( getInstance().getUserProjectRolesAuditsForUserId() );
    }
	
	public List<Sustainability> getSustainabilities() {
		return getInstance() == null ? null : new ArrayList<Sustainability>(
				getInstance().getSustainabilities());
	}

	public List<Pdsa> getPdsas() {
		return getInstance() == null ? null : new ArrayList<Pdsa>(getInstance()
				.getPdsas());
	}

	public List<UserProjectRoles> getUserProjectRoleses() {
        return getInstance() == null ?
            null : new ArrayList<UserProjectRoles>( getInstance().getUserProjectRoleses() );
    }
	
	public List<UserProjectRoleRequests> getUserProjectRoleRequestses() {
        return getInstance() == null ?
            null : new ArrayList<UserProjectRoleRequests>( getInstance().getUserProjectRoleRequestses() );
    }
	
	public List<Ppi> getPpis() {
		return getInstance() == null ? null : new ArrayList<Ppi>(getInstance()
				.getPpis());
	}

	public List<Comment> getComments() {
		return getInstance() == null ? null : new ArrayList<Comment>(
				getInstance().getComments());
	}
	

	
	
	public void saveOldPword( ValueChangeEvent event ) {
        this.oldPassword = ( event.getOldValue() != null ? ((String)event.getOldValue()).trim() : null );
	}
	
	@Override
	public String update() {

		String UpdateResult = super.update();

		// account update successfull - email notification if password changed
		try {
			if ( UpdateResult.equals("updated") &&
				 this.oldPassword != null && 
				 !this.oldPassword.equals(getInstance().getPassword().trim()) ) {
				
				if ( credentials.getUsername().equalsIgnoreCase( getInstance().getLoginName() ) ) {
					
					mailSender.sendSelfUpdatedAccount( getInstance().getLoginName(),
			                   					   	   getInstance().getFirstName(),
			                   					   	   getInstance().getLastName(),
			                   					   	   getInstance().getEmail()
			                 					     );
				} else {
					mailSender.sendUpdatedAccount( getInstance().getLoginName(),
			                   					   getInstance().getPassword(), 
			                   					   getInstance().getFirstName(),
			                   					   getInstance().getLastName(),
			                   					   getInstance().getEmail()
			                 					 );
				}
			}
		} catch ( Throwable exc ) {
			log.error( "account update confirmation/notification failed", exc );
		} finally {
			this.oldPassword = null;
		}
		return UpdateResult;
	}

	// a work-around the RF4 'tabPanel' throwing exceptions when no child tabs are rendered
	// 'User's Entries' tabPanel in Users and UsersEdit pages
	// it's non-sensical the way it works
	public boolean anyActivities() {
		return ( (getLoginAudits() != null ? (getLoginAudits().isEmpty() ? false : true) : false) );
	}
}
