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
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import uk.ac.imperial.clahrc.common.CustomIdentity;
import uk.ac.imperial.clahrc.common.IdentityHome;
import uk.ac.imperial.clahrc.entity.Projects;
import uk.ac.imperial.clahrc.entity.UserProjectRoles;
import uk.ac.imperial.clahrc.utilities.loaders.ProjectIDs;
import uk.ac.imperial.clahrc.utilities.loaders.RoleIDs;

@Name("projectsList")
public class ProjectsList extends EntityQuery<Projects> {

	@In CustomIdentity identity;
	@In IdentityHome identityHome;
	@In ProjectIDs projectIDs;
	@In RoleIDs roleIDs;
	
	private static final long serialVersionUID = 1L;
	private static final String EJBQL = "select projects from Projects projects";
	private static final String[] RESTRICTIONS = {
		"lower(projects.name) like lower(concat(#{projectsList.projects.name},'%'))",
		"lower(projects.shortName) like lower(concat(#{projectsList.projects.shortName},'%'))",
		"lower(projects.description) like lower(concat(#{projectsList.projects.description},'%'))",
		"lower(projects.hostOrganization) like lower(concat(#{projectsList.projects.hostOrganization},'%'))",
		"lower(projects.siteStructure) like lower(concat(#{projectsList.projects.siteStructure},'%'))",
		"lower(projects.type) like lower(concat(#{projectsList.projects.type},'%'))", };

	private Projects projects = new Projects();

	public ProjectsList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
		//setGroupBy( "projects.grouping, projects.name" ); //move to member operations, it triggers a bug in SEAM
		setOrderColumn( "projects.projectGroupings.name" );
		setOrderDirection( "desc" );
	}

	public Projects getProjects() {
		return projects;
	}
	
	@Override
	public List<Projects> getResultList() {

		if ( !identity.hasRole( roleIDs.getWRT_ADMIN_id() ) && (identity.getTopRoleId() != roleIDs.getPROGRAMME_EVALUATOR_id())  ) {

			List<String> whereClauses = new ArrayList<String>( Arrays.asList( RESTRICTIONS ) );
			List<UserProjectRoles> UserProjectRoles = identityHome.getUserProjectRoleses();
			for ( UserProjectRoles UserProjectRole : UserProjectRoles ) {
				whereClauses.add( "projects.id = #{projectIDs." + projectIDs.getIDs_id().get(UserProjectRole.getProjects().getId()) + "}" );
			}
			
			setRestrictionExpressionStrings( whereClauses );
			setRestrictionLogicOperator( "or" );
		}
		
		return super.getResultList();
	}
	
	public List<Projects> getResultListRestricted() {

		if ( !identity.hasRole( roleIDs.getWRT_ADMIN_id() ) && (identity.getTopRoleId() != roleIDs.getPROGRAMME_EVALUATOR_id())  ) {

			List<String> whereClauses = new ArrayList<String>( Arrays.asList( RESTRICTIONS ) );
			List<UserProjectRoles> UserProjectRoles = identityHome.getUserProjectRoleses();
			for ( UserProjectRoles UserProjectRole : UserProjectRoles ) {
				if ( identity.hasPermission( UserProjectRole.getProjects().getId(), roleIDs.getPROJECT_ADMIN_id(), new Object[]{} ) ) {
					whereClauses.add( "projects.id = #{projectIDs." + projectIDs.getIDs_id().get(UserProjectRole.getProjects().getId()) + "}" );
				}
			}
			
			setRestrictionExpressionStrings( whereClauses );
			setRestrictionLogicOperator( "or" );
		}
		
		return super.getResultList();
	}
	
	public List<SelectItem> getResultListAsSelectItems() {

		Integer RecordsNumLimit = this.getMaxResults();
		setMaxResults( null );
		List<SelectItem> NameProjectList = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
		
		List<Projects> UserProjects = this.getResultList();
		for ( Projects Project : UserProjects ) {
			NameProjectList.add( new SelectItem( Project, Project.getShortName() ) );
		}
		
		setMaxResults( RecordsNumLimit );
		return NameProjectList;
	}
	
	public List<SelectItem> getNameIdList() {

		Integer RecordsNumLimit = this.getMaxResults();
		setMaxResults( null );
		List<SelectItem> NameIdList = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
		NameIdList.add( new SelectItem( null, "Click to select..." ) );
		
		List<Projects> UserProjects = this.getResultList();
		for ( Projects Project : UserProjects ) {
			if ( identity.hasPermission( Project.getId(), roleIDs.getPROJECT_ADMIN_id(), new Object[]{} ) ) {
				NameIdList.add( new SelectItem( Project.getId(), Project.getShortName() ) );
			}
		}
		
		setMaxResults( RecordsNumLimit );
		return NameIdList;
	}
	
}
