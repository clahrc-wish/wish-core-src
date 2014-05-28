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
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("milestoneHome")
public class MilestoneHome extends EntityHome<Milestone> {

	private static final long serialVersionUID = 1L;

	public void setMilestoneId(Integer id) {
		setId(id);
	}

	public Integer getMilestoneId() {
		return (Integer) getId();
	}

	@Override
	protected Milestone createInstance() {
		Milestone milestone = new Milestone();
		return milestone;
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

	public Milestone getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
