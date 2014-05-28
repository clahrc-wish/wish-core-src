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
import uk.ac.imperial.clahrc.entity.*;
import uk.ac.imperial.clahrc.utilities.loaders.RoleIDs;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

@Name("rolesList")
public class RolesList extends EntityQuery<Roles> {

	@In CustomIdentity identity;
	@In RoleIDs roleIDs;
	
	private static final long serialVersionUID = 1L;
	private static final String EJBQL = "select roles from Roles roles";
	private static final String[] RESTRICTIONS = {
			"lower(roles.name) like lower(concat(#{rolesList.roles.name},'%'))",
			"lower(roles.description) like lower(concat(#{rolesList.roles.description},'%'))", };

	private Roles roles = new Roles();

	public RolesList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Roles getRoles() {
		return roles;
	}
	
	@Override
	public List<Roles> getResultList() {
		
		if ( !identity.hasRole( roleIDs.getSUPER_ADMIN_id() ) ) {
			
			List<String> whereClauses = new ArrayList<String>( Arrays.asList( RESTRICTIONS ) );
			if ( identity.hasRole( roleIDs.getPROGRAMME_LEAD_id() ) ) {
				// allow progamme_lead and wrt_admin to see their role levels
				whereClauses.add( "roles.id >= #{roleIDs." + roleIDs.getIDs_id().get(identity.getTopRoleId()) + "}" );
				
			} else {
				whereClauses.add( "roles.id > #{roleIDs." + roleIDs.getIDs_id().get(identity.getTopRoleId()) + "}" );
			}
			
			if ( identity.getTopRoleId() == roleIDs.getPROJECT_ADMIN_id() ) {
				whereClauses.add( "roles.id != #{roleIDs." + roleIDs.getIDs_id().get(roleIDs.getPROGRAMME_EVALUATOR_id()) + "}" ); // roles hierarchy hack
			}
			setRestrictionExpressionStrings( whereClauses );
		}
		
		return super.getResultList();
	}
	
	public List<SelectItem> getNameIdList() {

		List<SelectItem> NameIdList = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
		NameIdList.add( new SelectItem( null, "Click to select..." ) );
		
		List<Roles> UserRoles = this.getResultList();
		for ( Roles Role : UserRoles ) {
	
			NameIdList.add( new SelectItem( Role.getId(), Role.getName() ) );
		}
	
		return NameIdList;
	}
	
	public List<SelectItem> getNameObjectList() {

		List<SelectItem> NameIdList = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
		NameIdList.add( new SelectItem( null, "Click to select..." ) );
		
		List<Roles> UserRoles = this.getResultList();
		for ( Roles Role : UserRoles ) {
	
			NameIdList.add( new SelectItem( Role, Role.getName() ) );
		}
	
		return NameIdList;
	}
}
