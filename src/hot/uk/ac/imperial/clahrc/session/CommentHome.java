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

import uk.ac.imperial.clahrc.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("commentHome")
public class CommentHome extends EntityHome<Comment> {

	@In(create = true)	UsersHome usersHome;
	@In(create = true)	ProjectsHome projectsHome;

	private static final long serialVersionUID = 1L;

	public void setCommentId(Integer id) {
		setId(id);
	}

	public Integer getCommentId() {
		return (Integer) getId();
	}

	@Override
	protected Comment createInstance() {
		Comment comment = new Comment();
		return comment;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Users users = usersHome.getDefinedInstance();
		if (users != null) {
			getInstance().setUsers(users);
		}
		Projects projects = projectsHome.getDefinedInstance();
		if (projects != null) {
			getInstance().setProjects(projects);
		}
	}

	public boolean isWired() {
		if (getInstance().getProjects() == null)
			return false;
		return true;
	}

	public Comment getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
