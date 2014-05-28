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

import java.util.Date;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Synchronized;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.web.ServletContexts;

import uk.ac.imperial.clahrc.entity.LoginAudit;
import uk.ac.imperial.clahrc.entity.LoginAuditId;
import uk.ac.imperial.clahrc.session.LoginAuditHome;
import uk.ac.imperial.clahrc.session.LoginAuditList;

@Name("loginAuditor")
@Scope(ScopeType.SESSION)
@Synchronized
public class LoginAuditor implements java.io.Serializable 
{
	@Logger private Log log;
    @In Credentials credentials;
    @In CustomIdentity identity;

	private static final long serialVersionUID = 1L;
    private String SessionId = null;
    
    @Observer("org.jboss.seam.security.postAuthenticate")
    public void onAuthenticate()
    {
        log.info("loginAuditor.onAuthenticate() action called for #0", credentials.getUsername());

        ServletContexts.getInstance().getRequest().getSession().setMaxInactiveInterval( 1800 );
        log.info("loginAuditor.onAuthenticate() sessionTimeOut set to #0", 1800);
        
        try {
			LoginAuditId LoginRecordPrimKey = new LoginAuditId();
			LoginRecordPrimKey.setUserId( identity.getId() );
			LoginRecordPrimKey.setSessionId( ServletContexts.getInstance().getRequest().getSession().getId() );
			LoginRecordPrimKey.setIpAddress( ServletContexts.getInstance().getRequest().getRemoteAddr() );
			LoginRecordPrimKey.setLoggedInDateTime( new Date() );
			
			LoginAudit LoginRecord = new LoginAudit();
			LoginRecord.setId( LoginRecordPrimKey );
			LoginRecord.setUsers( identity.getUser());
			
			LoginAuditHome loginAuditHome = ((LoginAuditHome)Component.getInstance(LoginAuditHome.class, true));
			loginAuditHome.setInstance( LoginRecord );
			loginAuditHome.persist();
			
			this.SessionId = ServletContexts.getInstance().getRequest().getSession().getId();
		} catch ( Throwable exc ) {
			log.error( "Creating login record Failed !: ", exc );
		}
    }
    
    @Observer("org.jboss.seam.security.loggedOut")
    public void onLogout()
    {
        try {
        	log.info("loginAuditor.onLogout() action called for #0", ServletContexts.getInstance().getRequest().getSession().getId());
			LoginAuditList loginAuditList = ((LoginAuditList)Component.getInstance(LoginAuditList.class, true));
			loginAuditList.getLoginAudit().getId().setSessionId( ServletContexts.getInstance().getRequest().getSession().getId() );
			LoginAudit LoginRecord = loginAuditList.getSingleResult();
			
			if ( LoginRecord != null ) {
				LoginRecord.setLoggedOutDateTime(new Date());
				LoginAuditHome loginAuditHome = ((LoginAuditHome)Component.getInstance(LoginAuditHome.class, true));
				loginAuditHome.setInstance( LoginRecord );
				loginAuditHome.update();
				
				this.SessionId = null;
			} else {
				log.error( "DB record mising for session: #0", SessionId );
			}
			
		} catch ( Throwable exc ) {
			log.error( "Updating login record Failed !: ", exc );
		}
    }
    
    @Observer("org.jboss.seam.preDestroyContext.SESSION")
    public void onPreDestroySession()
    {
        try {
        	if ( this.SessionId != null ) {
        		log.info("loginAuditor.onPreDestroySession() processing #0", this.SessionId);
        		
				LoginAuditList loginAuditList = ((LoginAuditList) Component.getInstance(LoginAuditList.class, true));
				loginAuditList.getLoginAudit().getId().setSessionId( this.SessionId );
				LoginAudit LoginRecord = loginAuditList.getSingleResult();
				
				if ( LoginRecord != null ) {
					LoginRecord.setSessionExpiredDateTime( new Date() );
					LoginAuditHome loginAuditHome = ((LoginAuditHome)Component.getInstance(LoginAuditHome.class, true));
					loginAuditHome.setInstance( LoginRecord );
					loginAuditHome.update();

					this.SessionId = null;
				} else {
					log.error("DB record mising for session: #0", this.SessionId);
				}
			}
			
		} catch ( Throwable exc ) {
			log.error( "Updating login record Failed !: ", exc );
		}
    }
    
}
