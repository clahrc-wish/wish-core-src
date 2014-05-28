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
import java.util.Arrays;
import java.util.List;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import uk.ac.imperial.clahrc.common.CustomIdentity;
import uk.ac.imperial.clahrc.common.IdentityHome;
import uk.ac.imperial.clahrc.entity.UserProjectRoles;
import uk.ac.imperial.clahrc.entity.UserProjectRolesId;
import uk.ac.imperial.clahrc.utilities.loaders.ProjectIDs;
import uk.ac.imperial.clahrc.utilities.loaders.RoleIDs;

@Name("userProjectRolesList")
@AutoCreate
public class UserProjectRolesList extends EntityQuery<UserProjectRoles> {

	@In CustomIdentity identity;
	@In IdentityHome identityHome;
	@In ProjectIDs projectIDs;
	@In RoleIDs roleIDs;
	
	private static final long serialVersionUID = 1L;
	private static final String EJBQL = "select userProjectRoles from UserProjectRoles userProjectRoles";
	private static final String[] RESTRICTIONS = {};
	private UserProjectRoles userProjectRoles;

	public UserProjectRolesList() {
		userProjectRoles = new UserProjectRoles();
        userProjectRoles.setId( new UserProjectRolesId() );
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public UserProjectRoles getUserProjectRoles() {
		return userProjectRoles;
	}
	
	@Override
	public List<UserProjectRoles> getResultList() {

		if ( !identity.hasRole( roleIDs.getWRT_ADMIN_id() ) ) {

			List<String> whereClauses = new ArrayList<String>( Arrays.asList( RESTRICTIONS ) );
			List<UserProjectRoles> UserProjectRoles = identityHome.getUserProjectRoleses();
			for ( UserProjectRoles UserProjectRole : UserProjectRoles ) {
				whereClauses.add( "userProjectRoles.id.projectId = #{projectIDs." + projectIDs.getIDs_id().get(UserProjectRole.getProjects().getId()) + "}" );
			}
			
			setRestrictionExpressionStrings( whereClauses );
			setRestrictionLogicOperator( "or" );
		}
		return super.getResultList();
	}
	
}
