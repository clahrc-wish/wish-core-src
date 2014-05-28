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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("ppiFieldOptions")
@Scope(ScopeType.SESSION)
public class PpiFieldOptions implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<SelectItem> roles = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
    {
    	roles.add( new SelectItem( "Patient/public adviser", "Patient/public adviser" ) );
    	roles.add( new SelectItem( "Staff", "Staff" ) );
    }
    public List<SelectItem> getRoles()
    {
        return roles;
    }
    
    private List<SelectItem> receptives = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
    {
    	receptives.add( new SelectItem( "A", "The team are not receptive to the involvement of patients" ) );
    	receptives.add( new SelectItem( "B", "The team are receptive to the idea of the involvement of patients but patient input is largely ignored" ) );
    	receptives.add( new SelectItem( "C", "The team are receptive to the involvement of patients and patient input is valued" ) );
    }
    public List<SelectItem> getReceptives()
    {
        return receptives;
    }
    
    private List<SelectItem> differences = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
    {
    	differences.add( new SelectItem( "A", "Involving patients makes no difference to our project" ) );
    	differences.add( new SelectItem( "B", "I am not sure what difference involving patients makes to our project" ) );
    	differences.add( new SelectItem( "C", "Involving patients makes a positive difference to our project" ) );
    }
    public List<SelectItem> getDifferences()
    {
        return differences;
    }
    
    private List<SelectItem> plans = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
    {
    	plans.add( new SelectItem( "A", "We have not developed plans to involve more patients" ) );
    	plans.add( new SelectItem( "B", "We have developed plans to involve more patients but have not involved them yet" ) );
    	plans.add( new SelectItem( "C", "We are involving more patients" ) );
    }
    public List<SelectItem> getPlans()
    {
        return plans;
    }
}
