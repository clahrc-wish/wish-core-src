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

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;

import uk.ac.imperial.clahrc.entity.Users;

@Name("authenticator")
public class Authenticator implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Logger private Log log;
    @In CustomIdentity identity;
    @In Credentials credentials;
    @In FacesContext facesContext;
    @In EntityManager entityManager;
    @In WrtState wrtState;

    public boolean authenticate()
    {
        log.info("authenticating {0}", credentials.getUsername());
        try {
        	if ( wrtState.isSuspended() ) {
        		
        		log.warn("WRT Suspended: {0}", wrtState.isSuspended());
        		facesContext.addMessage( null, FacesMessages.createFacesMessage(FacesMessage.SEVERITY_WARN, 
						                 " WISH temporarily suspended !", new Object[]{}) );
        		return false;
        	}
        	
        	Users User = (Users)entityManager.createQuery( "SELECT u FROM Users u WHERE u.loginName = :loginname" )
        			                         .setParameter( "loginname", credentials.getUsername() )
        			                         .getSingleResult();
        	
        	if ( (User != null) && !User.isActive() ) {
        		
        		log.warn("user account not active for: {0}", credentials.getUsername());
        		facesContext.addMessage( null, FacesMessages.createFacesMessage(FacesMessage.SEVERITY_WARN, 
						                 " User account not active !", new Object[]{}) );
        		return false;
        	}
        	
        	if ( (User != null) && User.getUserProjectRoleses().isEmpty() ) {
        		
        		log.warn("no roles found for user account of: {0}", credentials.getUsername());
        		facesContext.addMessage( null, FacesMessages.createFacesMessage(FacesMessage.SEVERITY_WARN, 
						                 " User account has no roles !", new Object[]{}) );
        		return false;
        	}
        	
        	if ( (User != null) && 
           		 User.getLoginName().equals(credentials.getUsername()) && 
           		 !User.getPassword().equals(credentials.getPassword()) ) {
        		
        		log.warn("invalid password for: {0}", credentials.getUsername());
        		facesContext.addMessage( null, FacesMessages.createFacesMessage(FacesMessage.SEVERITY_WARN, 
        								 " Invalid Password !", new Object[]{}) );
        		return false;
        	}
        	
        	if ( (User != null) && 
        		 User.getLoginName().equals(credentials.getUsername()) && 
        		 User.getPassword().equals(credentials.getPassword()) ) {
        		
        		identity.setUser( User );
        		return true;
        	}
        } catch ( NoResultException exc ) {
        	
			log.warn("no user account found for: {0}", credentials.getUsername());
			facesContext.addMessage( null, FacesMessages.createFacesMessage(FacesMessage.SEVERITY_WARN, 
									 " Invalid Username !", new Object[]{}) );
			
		} catch ( Exception exc ) {
			
			log.error( "authentication failed", exc );
			facesContext.addMessage( null, FacesMessages.createFacesMessage(FacesMessage.SEVERITY_ERROR, 
									 " Authentication Failed !", new Object[]{}) );
			
		}
        return false;
    }

}
