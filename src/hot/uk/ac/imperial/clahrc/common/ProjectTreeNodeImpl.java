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

import org.richfaces.model.TreeNodeImpl;

import uk.ac.imperial.clahrc.entity.Projects;

public class ProjectTreeNodeImpl extends TreeNodeImpl {

	private Object data;
	
	public ProjectTreeNodeImpl() {
		super();
	}
	
	public ProjectTreeNodeImpl( boolean leaf, Object data ) {
		super( leaf );
		this.data = data;
	}
	
	public Object getData() {
        return data;
    }

    @Override
    public String toString() {
    	if ( data instanceof String ) { return ((String)data); }
    	if ( data instanceof Projects ) { return ((Projects)data).getShortName(); }
        return null;
    }
}
