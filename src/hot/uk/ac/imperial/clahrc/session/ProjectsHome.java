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
import java.util.List;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import uk.ac.imperial.clahrc.entity.Comment;
import uk.ac.imperial.clahrc.entity.Pdsa;
import uk.ac.imperial.clahrc.entity.Ppi;
import uk.ac.imperial.clahrc.entity.ProjectTables;
import uk.ac.imperial.clahrc.entity.Projects;
import uk.ac.imperial.clahrc.entity.Sustainability;
import uk.ac.imperial.clahrc.entity.UserMessages;
import uk.ac.imperial.clahrc.entity.UserProjectRoleRequests;
import uk.ac.imperial.clahrc.entity.UserProjectRoles;
import uk.ac.imperial.clahrc.entity.UserProjectRolesAudit;

@Name("projectsHome")
public class ProjectsHome extends EntityHome<Projects> {

	private static final long serialVersionUID = 1L;

	public void setProjectsId(Integer id) {
		setId(id);
	}

	public Integer getProjectsId() {
		return (Integer) getId();
	}

	@Override
	protected Projects createInstance() {
		Projects projects = new Projects();
		return projects;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Projects getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Comment> getComments() {
		return getInstance() == null ? null : new ArrayList<Comment>(
				getInstance().getComments());
	}

	public List<Sustainability> getSustainabilities() {
		return getInstance() == null ? null : new ArrayList<Sustainability>(
				getInstance().getSustainabilities());
	}

	public List<ProjectTables> getProjectTableses() {
        return getInstance() == null ?
            null : new ArrayList<ProjectTables>( getInstance().getProjectTableses() );
    }
	
	public List<UserProjectRoles> getUserProjectRoleses() {
		return getInstance() == null ? null : new ArrayList<UserProjectRoles>(
				getInstance().getUserProjectRoleses());
	}

	public List<Ppi> getPpis() {
		return getInstance() == null ? null : new ArrayList<Ppi>(getInstance()
				.getPpis());
	}

	public List<Pdsa> getPdsas() {
		return getInstance() == null ? null : new ArrayList<Pdsa>(getInstance()
				.getPdsas());
	}

	public List<UserProjectRoleRequests> getUserProjectRoleRequestses() {
        return getInstance() == null ?
            null : new ArrayList<UserProjectRoleRequests>( getInstance().getUserProjectRoleRequestses() );
    }
	
    public List<UserProjectRolesAudit> getUserProjectRolesAudits() {
        return getInstance() == null ?
            null : new ArrayList<UserProjectRolesAudit>( getInstance().getUserProjectRolesAudits() );
    }
    
	public List<UserMessages> getUserMessageses() {
        return getInstance() == null ?
            null : new ArrayList<UserMessages>( getInstance().getUserMessageses() );
    }
	
	// a work-around the RF4 'tabPanel' throwing exceptions when no child tabs are rendered
	// 'Project Entries' tabPanel in Projects and ProjectsEdit pages
	// it's non-sensical the way it works
	public boolean anyActivities() {
		
		if ( getComments() != null && !getComments().isEmpty() ) { return true; }
		if ( getSustainabilities() != null && !getSustainabilities().isEmpty() ) { return true; }
		if ( getProjectTableses() != null && !getProjectTableses().isEmpty() ) { return true; }
		if ( getUserProjectRoleses() != null && !getUserProjectRoleses().isEmpty() ) { return true; }
		if ( getPpis() != null && !getPpis().isEmpty() ) { return true; }
		if ( getPdsas() != null && !getPdsas().isEmpty() ) { return true; }
		if ( getUserMessageses() != null && !getUserMessageses().isEmpty() ) { return true; }
		if ( getUserProjectRoleRequestses() != null && !getUserProjectRoleRequestses().isEmpty() ) { return true; }
		if ( getUserProjectRolesAudits() != null && !getUserProjectRolesAudits().isEmpty() ) { return true; }
		return false;
	}
}
