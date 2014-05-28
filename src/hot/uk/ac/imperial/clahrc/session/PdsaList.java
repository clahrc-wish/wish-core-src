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
import uk.ac.imperial.clahrc.entity.Pdsa;
import uk.ac.imperial.clahrc.utilities.loaders.ProjectIDs;
import uk.ac.imperial.clahrc.utilities.loaders.RoleIDs;

@Name("pdsaList")
public class PdsaList extends EntityQuery<Pdsa> {

	@In CustomIdentity identity;
	@In ActiveProjectHome activeProjectHome;
	@In ProjectIDs projectIDs;
	@In RoleIDs roleIDs;
	
	private static final long serialVersionUID = 1L;
	private static final String EJBQL = "select pdsa from Pdsa pdsa";
	private static final String[] RESTRICTIONS = {
			"lower(pdsa.description) like lower(concat(#{pdsaList.pdsa.description},'%'))",
			"lower(pdsa.doDescription) like lower(concat(#{pdsaList.pdsa.doDescription},'%'))",
			"lower(pdsa.studyDescription) like lower(concat(#{pdsaList.pdsa.studyDescription},'%'))",
			"lower(pdsa.actDescription) like lower(concat(#{pdsaList.pdsa.actDescription},'%'))",
			"lower(pdsa.planTeam) like lower(concat(#{pdsaList.pdsa.planTeam},'%'))",
			"lower(pdsa.studyTeam) like lower(concat(#{pdsaList.pdsa.studyTeam},'%'))",
			"lower(pdsa.doTeam) like lower(concat(#{pdsaList.pdsa.doTeam},'%'))",
			"lower(pdsa.actTeam) like lower(concat(#{pdsaList.pdsa.actTeam},'%'))",
			"lower(pdsa.cycleTitle) like lower(concat(#{pdsaList.pdsa.cycleTitle},'%'))", };

	private Pdsa pdsa = new Pdsa();

	public PdsaList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
		setOrderColumn( "pdsa.rcTimestamp" );
        setOrderDirection( "desc" );
	}

	public Pdsa getPdsa() {
		return pdsa;
	}
	
	@Override
	public List<Pdsa> getResultList() {
		
		if ( !identity.hasRole( roleIDs.getSUPER_ADMIN_id() ) ) {
			
			List<String> whereClauses = new ArrayList<String>( Arrays.asList( RESTRICTIONS ) );
			whereClauses.add( "pdsa.projects.id = #{activeProjectHome.definedInstance.id}" );
			setRestrictionExpressionStrings( whereClauses );
		}

		return super.getResultList();
	}
}
