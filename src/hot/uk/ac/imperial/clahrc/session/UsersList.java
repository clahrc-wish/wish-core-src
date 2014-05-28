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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import uk.ac.imperial.clahrc.entity.Users;

@Name("usersList")
public class UsersList extends EntityQuery<Users> {

	@In EntityManager entityManager;
	private static final long serialVersionUID = 1L;
	private static final String EJBQL = "select users from Users users";
	private static final String[] RESTRICTIONS = {
			"lower(users.firstName) like lower(#{usersList.users.firstName})",
			"lower(users.lastName) like lower(#{usersList.users.lastName})",
			"lower(users.loginName) like lower(#{usersList.users.loginName})",
			"lower(users.email) like lower(#{usersList.users.email})", };

	private Users users = new Users();

	public UsersList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Users getUsers() {
		return users;
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> lookUp( String FirstName, String LastName ) {
		List<Users> ReturnResult = Collections.EMPTY_LIST;
		if ( FirstName != null && !FirstName.isEmpty() &&
			 LastName != null && !LastName.isEmpty() ) {
			
			users.setFirstName( FirstName.trim() );
			users.setLastName( LastName.trim() );
			ReturnResult = getResultList();
		}
		return ReturnResult;
	}
}
