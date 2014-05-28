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
package uk.ac.imperial.clahrc.utilities.fieldoptions;

import java.io.Serializable;

import javax.faces.event.ValueChangeEvent;

import org.jboss.seam.annotations.Name;

@Name("activeProjectHomeFieldOptions")
//@Scope(ScopeType.SESSION)
public class ActiveProjectHomeFieldOptions implements Serializable {

	private static final long serialVersionUID = 1L;

	private String projectTableSelection = null;
	public void setProjectTableSelection(String projectTableSelection) {
		this.projectTableSelection = projectTableSelection;
	}
	public String getProjectTableSelection() { 
		return projectTableSelection;
	}
    public void changeProjectTableSelection( ValueChangeEvent event ) {
		projectTableSelection = ( event.getNewValue() == null ? null : event.getNewValue().toString() );
	}
}
