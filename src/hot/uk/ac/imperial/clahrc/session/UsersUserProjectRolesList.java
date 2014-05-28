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

import uk.ac.imperial.clahrc.common.CustomIdentity;
import uk.ac.imperial.clahrc.common.IdentityHome;
import uk.ac.imperial.clahrc.entity.*;
import uk.ac.imperial.clahrc.utilities.loaders.ProjectIDs;
import uk.ac.imperial.clahrc.utilities.loaders.RoleIDs;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Name("usersUserProjectRolesList")
public class UsersUserProjectRolesList extends EntityQuery<UsersUserProjectRoles>
{
	@In CustomIdentity identity;
	@In IdentityHome identityHome;
	@In ProjectIDs projectIDs;
	@In RoleIDs roleIDs;
	
	private static final long serialVersionUID = 1L;

	private static final String EJBQL = "select usersUserProjectRoles from UsersUserProjectRoles usersUserProjectRoles";

    private static final String[] RESTRICTIONS = {
        "lower(usersUserProjectRoles.id.firstName) like lower(#{usersUserProjectRolesList.usersUserProjectRoles.id.firstName})",
        "lower(usersUserProjectRoles.id.lastName) like lower(#{usersUserProjectRolesList.usersUserProjectRoles.id.lastName})",
        "lower(usersUserProjectRoles.id.loginName) like lower(#{usersUserProjectRolesList.usersUserProjectRoles.id.loginName})",
        "lower(usersUserProjectRoles.id.email) like lower(#{usersUserProjectRolesList.usersUserProjectRoles.id.email})",
    };

    private UsersUserProjectRoles usersUserProjectRoles;

    public UsersUserProjectRolesList()
    {
        usersUserProjectRoles = new UsersUserProjectRoles();
        usersUserProjectRoles.setId( new UsersUserProjectRolesId() );
        setEjbql(EJBQL);
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setMaxResults(25);
    }

    public UsersUserProjectRoles getUsersUserProjectRoles()
    {
        return usersUserProjectRoles;
    }
    
    @Override
	public List<UsersUserProjectRoles> getResultList() {

		if ( !identity.hasRole( roleIDs.getWRT_ADMIN_id() ) ) {

			List<String> whereClauses = new ArrayList<String>( Arrays.asList( RESTRICTIONS ) );
			List<UserProjectRoles> UserProjectRoles = identityHome.getUserProjectRoleses();
			for ( UserProjectRoles UserProjectRole : UserProjectRoles ) {
				whereClauses.add( "usersUserProjectRoles.id.projectId = #{projectIDs." + projectIDs.getIDs_id().get(UserProjectRole.getProjects().getId()) + "}" );
			}
			
			setRestrictionExpressionStrings( whereClauses );
			setRestrictionLogicOperator( "or" );
		}
		return super.getResultList();
	}
}
