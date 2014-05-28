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
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("loginAuditHome")
public class LoginAuditHome extends EntityHome<LoginAudit> {

	@In(create = true)	UsersHome usersHome;

	private static final long serialVersionUID = 1L;

	public void setLoginAuditId(LoginAuditId id) {
		setId(id);
	}

	public LoginAuditId getLoginAuditId() {
		return (LoginAuditId) getId();
	}

	public LoginAuditHome() {
		setLoginAuditId(new LoginAuditId());
	}

	@Override
	public boolean isIdDefined() {
		if (getLoginAuditId().getUserId() == 0)
			return false;
		if (getLoginAuditId().getSessionId() == null
				|| "".equals(getLoginAuditId().getSessionId()))
			return false;
		if (getLoginAuditId().getLoggedInDateTime() == null)
			return false;
		return true;
	}

	@Override
	protected LoginAudit createInstance() {
		LoginAudit loginAudit = new LoginAudit();
		loginAudit.setId(new LoginAuditId());
		return loginAudit;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Users users = usersHome.getDefinedInstance();
		if (users != null) {
			getInstance().setUsers(users);
		}
	}

	public boolean isWired() {
		if (getInstance().getUsers() == null)
			return false;
		return true;
	}

	public LoginAudit getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
