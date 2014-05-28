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
package uk.ac.imperial.clahrc.uicomponents;

import java.io.Serializable;
import java.util.List;

import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.jboss.seam.ui.component.html.HtmlLink;

import uk.ac.imperial.clahrc.entity.Projects;
import uk.ac.imperial.clahrc.session.ProjectsList;
import uk.ac.imperial.clahrc.utilities.NameStringUtility;
import uk.ac.imperial.clahrc.utilities.loaders.ProjectIDs;

@Name("dynamicSLink")
public class DynamicSLink implements Serializable {

	@Logger private Log log;
	@In(create=true) NameStringUtility nameStringUtility;
	@In ProjectIDs projectIDs;
	
	private static final long serialVersionUID = 1L;

	private HtmlLink UserProjectSLink;

	public HtmlLink getUserProjectSLink() {
		
		try {
			ProjectsList projectsList = (ProjectsList) Component.getInstance( ProjectsList.class, true );
			Integer InitialMaxResults = projectsList.getMaxResults();
			String InitialGroupBy = projectsList.getGroupBy();
			projectsList.setMaxResults( null );
			projectsList.setGroupBy( "projects.grouping, projects.name" );
			List<Projects> UserProjects = projectsList.getResultList();
			projectsList.setMaxResults( InitialMaxResults );
			projectsList.setGroupBy( InitialGroupBy );

			if ( UserProjects.size() > 1 ) {
				log.error("more than one project found where only one expected");
				throw new IllegalStateException();
			}
			else {

				UserProjectSLink = new HtmlLink();
				UserProjectSLink.setView( "/projectHome.xhtml" );
				UserProjectSLink.setValue( " click here ..." );
				UserProjectSLink.setId( nameStringUtility.toCamelCase( UserProjects.get(0).getName().replace("@", "at") ) + "LinkId" );
				UserProjectSLink.setIncludePageParams( false );
				UserProjectSLink.setActionExpression( ExpressionFactory.newInstance().createMethodExpression( FacesContext.getCurrentInstance().getELContext(),
						                             "#{activeProjectHome.wire(projectIDs." + projectIDs.getIDs_id().get(UserProjects.get(0).getId()) + ")}",
						                             null, new Class[0] )
						                 );
				// disable if the project is not active
				if ( !UserProjects.get(0).isActive() ) {
					UserProjectSLink.setDisabled( true );
					UserProjectSLink.setValue( ((String)UserProjectSLink.getValue()) + " (deactivated)" );
					UserProjectSLink.setStyle( "color: red;" );
				}
			}
		} catch ( Throwable exc ) {
			log.error("project sLink creation failed: ", exc);
		}
		return UserProjectSLink;
	}

	public void setUserProjectSLink(HtmlLink userProjectSLink) {
		UserProjectSLink = userProjectSLink;
	}
}
