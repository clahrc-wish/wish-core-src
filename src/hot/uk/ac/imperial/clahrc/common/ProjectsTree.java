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
package uk.ac.imperial.clahrc.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.richfaces.component.UITree;
import org.richfaces.event.TreeSelectionChangeEvent;

import uk.ac.imperial.clahrc.entity.Projects;
import uk.ac.imperial.clahrc.session.ProjectsList;

@Name("projectsTree")
public class ProjectsTree implements Serializable {

	@Logger private Log log;
	@In IdentityHome identityHome;
	@In(create=true) ProjectsList projectsList;
	
	private static final long serialVersionUID = 1L;
	private ProjectTreeNodeImpl groups = new ProjectTreeNodeImpl();
	
	public ProjectTreeNodeImpl getGroups() {
		
		try {
			Integer initialMaxResults = projectsList.getMaxResults();
			String initialGroupBy = projectsList.getGroupBy();
			String initialOrderColumn = projectsList.getOrderColumn();
			String initialOrderDirection = projectsList.getOrderDirection();
			projectsList.setMaxResults( null );
			projectsList.setGroupBy( "projects.projectGroupings.theme, projects.projectGroupings.name, projects.name" );
			projectsList.setOrderColumn( "projects.projectGroupings.theme" );
			projectsList.setOrderDirection( "desc" );
			List<Projects> userProjects = projectsList.getResultList();
			projectsList.setMaxResults( initialMaxResults );
			projectsList.setGroupBy( initialGroupBy );
			projectsList.setOrderColumn( initialOrderColumn );
			projectsList.setOrderDirection( initialOrderDirection );
			
			ProjectTreeNodeImpl currentTheme = new ProjectTreeNodeImpl();
			ProjectTreeNodeImpl currentGrouping = new ProjectTreeNodeImpl();
			Integer themeCounter = 0;
			Integer groupingCounter = 0;
			Integer projectCounter = 0;
			for ( Projects userProject : userProjects ) {

				if ( !userProject.getProjectGroupings().getName().equalsIgnoreCase( "none" ) && 
					 userProject.isActive() ) {
					
					if ( currentTheme.getData() == null || 
						 !((String) currentTheme.getData()).trim().endsWith( userProject.getProjectGroupings().getTheme().trim() ) ) {

						currentTheme = new ProjectTreeNodeImpl( false, userProject.getProjectGroupings().getTheme() );
						groups.addChild( themeCounter.toString(), currentTheme );
						themeCounter++;
					}
					
					if ( currentGrouping.getData() == null || 
						 !((String) currentGrouping.getData()).trim().endsWith( userProject.getProjectGroupings().getName().trim() ) ) {

						currentGrouping = new ProjectTreeNodeImpl( false, userProject.getProjectGroupings().getName() );
						currentTheme.addChild( groupingCounter.toString(), currentGrouping );
						groupingCounter++;
					}
					
					ProjectTreeNodeImpl projectNode = new ProjectTreeNodeImpl( true, userProject );
					currentGrouping.addChild( projectCounter.toString(), projectNode );
					projectCounter++;
				}
				
			}
			
			if ( groupingCounter <= 0 ) {
				currentGrouping = new ProjectTreeNodeImpl( false, "(projects deactivated)" );
				groups.addChild( groupingCounter.toString(), currentGrouping );
			}
		} catch ( Throwable exc ) {
			log.error( "projects tree for Project Access panel creation failed: ", exc );
		}
		return groups;
	}
	
	public void nodeSelected( TreeSelectionChangeEvent event ){

		try {
			List<Object> selection = new ArrayList<Object>( event.getNewSelection() );  
	        Object currentSelectionKey = selection.get( 0 );  
	        UITree tree = (UITree)event.getSource();  
	  
	        Object storedKey = tree.getRowKey();  
	        tree.setRowKey( currentSelectionKey );  
	        Object currentSelection = ((ProjectTreeNodeImpl)tree.getRowData()).getData();  
	        tree.setRowKey( storedKey );
	        
	    	if ( currentSelection instanceof Projects ) { 
	    		identityHome.setProject( (Projects)currentSelection );
	    		
	    	} else { 
	    		identityHome.setProject( null );
	    	}
		} catch ( Throwable exc ) {
			log.error( "nodeSelected() failed: ", exc );
		}
	}
}
