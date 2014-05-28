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

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import java.util.Arrays;
import java.util.List;

@Name("loginAuditList")
public class LoginAuditList extends EntityQuery<LoginAudit> {

	private static final long serialVersionUID = 1L;
	private static final String EJBQL = "select loginAudit from LoginAudit loginAudit";
	private static final String[] RESTRICTIONS = { "loginAudit.id.sessionId = #{loginAuditList.loginAudit.id.sessionId}", };
	private LoginAudit loginAudit;

	public LoginAuditList() {
		loginAudit = new LoginAudit();
		loginAudit.setId(new LoginAuditId());
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
		setOrderColumn( "loginAudit.id.loggedInDateTime" );
        setOrderDirection( "desc" );
	}

	public LoginAudit getLoginAudit() {
		return loginAudit;
	}

	public List<LoginAudit> getLoggedInList() {
		
		setEjbql("select loginAudit from LoginAudit loginAudit where loginAudit.loggedOutDateTime is null and loginAudit.sessionExpiredDateTime is null");
		return super.getResultList();
	}

}
