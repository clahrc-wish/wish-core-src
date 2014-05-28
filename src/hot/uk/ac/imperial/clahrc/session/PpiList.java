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

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import uk.ac.imperial.clahrc.common.ActiveProjectHome;
import uk.ac.imperial.clahrc.common.CustomIdentity;
import uk.ac.imperial.clahrc.entity.Ppi;
import uk.ac.imperial.clahrc.utilities.loaders.ProjectIDs;
import uk.ac.imperial.clahrc.utilities.loaders.RoleIDs;

@Name("ppiList")
public class PpiList extends EntityQuery<Ppi> {

	@In CustomIdentity identity;
	@In ActiveProjectHome activeProjectHome;
	@In ProjectIDs projectIDs;
	@In RoleIDs roleIDs;
	
	private static final long serialVersionUID = 1L;
	private static final String EJBQL = "select ppi from Ppi ppi";
	private static final String[] RESTRICTIONS = {
			"lower(ppi.role) like lower(concat(#{ppiList.ppi.role},'%'))",
			"lower(ppi.receptive) like lower(concat(#{ppiList.ppi.receptive},'%'))",
			"lower(ppi.difference) like lower(concat(#{ppiList.ppi.difference},'%'))",
			"lower(ppi.plans) like lower(concat(#{ppiList.ppi.plans},'%'))",
			"lower(ppi.comments) like lower(concat(#{ppiList.ppi.comments},'%'))", };

	private Ppi ppi = new Ppi();

	public PpiList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
		setOrderColumn( "ppi.rcTimestamp" );
        setOrderDirection( "desc" );
	}

	public Ppi getPpi() {
		return ppi;
	}
	
	@Override
	public List<Ppi> getResultList() {
		
		if ( !identity.hasRole( roleIDs.getSUPER_ADMIN_id() ) ) {
			
			List<String> whereClauses = new ArrayList<String>( Arrays.asList( RESTRICTIONS ) );
			whereClauses.add( "ppi.projects.id = #{activeProjectHome.definedInstance.id}" );

			if ( identity.hasRole( roleIDs.getPROJECT_STAFF_id() ) && !identity.hasRole( roleIDs.getPROJECT_ADMIN_id() ) ) {
				whereClauses.add( "ppi.users.id = #{identity.id}" );
			}
			
			setRestrictionExpressionStrings( whereClauses );
		}

		return super.getResultList();
	}
}
