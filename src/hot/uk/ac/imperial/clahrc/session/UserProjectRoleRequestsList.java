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

@Name("userProjectRoleRequestsList")
public class UserProjectRoleRequestsList extends EntityQuery<UserProjectRoleRequests>
{
	@In CustomIdentity identity;
	@In IdentityHome identityHome;
	@In ProjectIDs projectIDs;
	@In RoleIDs roleIDs;
	
	private static final long serialVersionUID = 1L;
	private static final String EJBQL = "select userProjectRoleRequests from UserProjectRoleRequests userProjectRoleRequests";
    private static final String[] RESTRICTIONS = {
    };

    private UserProjectRoleRequests userProjectRoleRequests;

    public UserProjectRoleRequestsList()
    {
        userProjectRoleRequests = new UserProjectRoleRequests();
        userProjectRoleRequests.setId( new UserProjectRoleRequestsId() );
        setEjbql(EJBQL);
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setMaxResults(25);
		setOrderColumn( "userProjectRoleRequests.rcTimestamp" );
        setOrderDirection( "desc" );
    }

    public UserProjectRoleRequests getUserProjectRoleRequests()
    {
        return userProjectRoleRequests;
    }
    
    @Override
	public List<UserProjectRoleRequests> getResultList() {
		
		if ( !identity.hasRole( roleIDs.getWRT_ADMIN_id() ) ) {
			
			List<String> whereClauses = new ArrayList<String>( Arrays.asList( RESTRICTIONS ) );
			List<UserProjectRoles> UserProjectRoles = identityHome.getUserProjectRoleses();
			for ( UserProjectRoles UserProjectRole : UserProjectRoles ) {
				if ( identity.hasPermission( UserProjectRole.getProjects().getId(), roleIDs.getPROJECT_ADMIN_id(), new Object[]{} )) {
					whereClauses.add( "userProjectRoleRequests.projects.id = #{projectIDs." + projectIDs.getIDs_id().get(UserProjectRole.getProjects().getId()) + "}" );
				}
			}

			whereClauses.add( "userProjectRoleRequests.users.id = #{identity.id}" );
			setRestrictionExpressionStrings( whereClauses );
			setRestrictionLogicOperator( "or" );
		}

		return super.getResultList();
	}
}
