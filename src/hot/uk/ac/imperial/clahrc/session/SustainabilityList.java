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
import uk.ac.imperial.clahrc.entity.Sustainability;
import uk.ac.imperial.clahrc.utilities.loaders.ProjectIDs;
import uk.ac.imperial.clahrc.utilities.loaders.RoleIDs;

@Name("sustainabilityList")
public class SustainabilityList extends EntityQuery<Sustainability> {

	@In CustomIdentity identity;
	@In ActiveProjectHome activeProjectHome;
	@In ProjectIDs projectIDs;
	@In RoleIDs roleIDs;
	
	private static final long serialVersionUID = 1L;
	private static final String EJBQL = "select sustainability from Sustainability sustainability";
	private static final String[] RESTRICTIONS = { "lower(sustainability.comment) like lower(concat(#{sustainabilityList.sustainability.comment},'%'))", };
	private Sustainability sustainability = new Sustainability();

	public SustainabilityList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
		setOrderColumn( "sustainability.rcTimestamp" );
        setOrderDirection( "desc" );
	}

	public Sustainability getSustainability() {
		return sustainability;
	}
	
	@Override
	public List<Sustainability> getResultList() {
		
		if ( !identity.hasRole( roleIDs.getSUPER_ADMIN_id() ) ) {
			
			List<String> whereClauses = new ArrayList<String>( Arrays.asList( RESTRICTIONS ) );
			whereClauses.add( "sustainability.projects.id = #{activeProjectHome.definedInstance.id}" );

			if ( identity.hasRole( roleIDs.getPROJECT_STAFF_id() ) && !identity.hasRole( roleIDs.getPROJECT_ADMIN_id() ) ) {
				whereClauses.add( "sustainability.users.id = #{identity.id}" );
			}
			
			setRestrictionExpressionStrings( whereClauses );
		}

		return super.getResultList();
	}
}
