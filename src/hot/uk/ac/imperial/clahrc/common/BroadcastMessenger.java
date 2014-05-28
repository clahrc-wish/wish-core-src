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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Synchronized;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;

import uk.ac.imperial.clahrc.entity.LoginAudit;
import uk.ac.imperial.clahrc.session.LoginAuditList;

@Name("broadcastMessenger")
@Scope(ScopeType.SESSION)
@Synchronized
public class BroadcastMessenger implements java.io.Serializable {
	
	@Logger private Log log;
	@In FacesContext facesContext;
	@In EntityManager entityManager;

	private static final long serialVersionUID = 1L;
	private String bMessage;
	private List<String> broadcastedMessages;

	public List<String> getLoggedInUsers() {
		
		try {
			LoginAuditList loginAuditList = ((LoginAuditList) Component.getInstance(LoginAuditList.class, true));
			loginAuditList.setRestrictionExpressionStrings(Arrays.asList(new String[]{"loginAudit.loggedOutDateTime IS NULL","loginAudit.sessionExpiredDateTime IS NULL"}));
			loginAuditList.setRestrictionLogicOperator("and");
			List<LoginAudit> LoginRecords = loginAuditList.getResultList();
			
			List<String> LoggedInUsers = Collections.checkedList( new ArrayList<String>(), String.class );
			for ( LoginAudit LoggedInUser : LoginRecords ) {
				
				LoggedInUsers.add( LoggedInUser.getUsers().getFirstName() + " " + 
				                   LoggedInUser.getUsers().getLastName() + " " + 
						           LoggedInUser.getId().getSessionId() + " " + 
				                   LoggedInUser.getId().getLoggedInDateTime() );
				
			}
			return LoggedInUsers;
			
		} catch ( Throwable exc ) {
			log.error("retrieving logged in user info failed: ", exc);
		}
		return null;
	}
	
	public void broadcastMessage() {
		
		try {
			if ( (bMessage != null) && !bMessage.isEmpty() ) {
				facesContext.addMessage( "broadcastMsgPanel:broadcastMsgPanelDiv", 
						                 FacesMessages.createFacesMessage(FacesMessage.SEVERITY_WARN, bMessage, new Object[]{}) );
				
				broadcastedMessages.add( bMessage );
				bMessage = null;
			}
		} catch ( Throwable exc ) {
			log.error("broadcasting message failed: ", exc);
		}
	}

	public String getbMessage() {
		return bMessage;
	}

	public void setbMessage(String bMessage) {
		this.bMessage = bMessage;
	}
}
