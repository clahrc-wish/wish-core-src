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
package uk.ac.imperial.clahrc.validators;

import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;

@Name("userNameValidator")
@Scope(ScopeType.EVENT)
public class UserNameValidator implements Validator {

	@Logger private Log log;
	@In StatusMessages statusMessages;
	
	public void validate(FacesContext fContext, UIComponent uiComponent, Object value) throws ValidatorException {
		
		if ( (value == null) || ((String)value).isEmpty() || ((String)value).length() > 45 ) {
			
			log.warn("invalid input for name: {0}", (String) value);
			((UIInput) uiComponent).setValid(false);
			statusMessages.addToControl(uiComponent.getId()," Must be between 1 and 45 characters long !", new Object[] {});
			
		} else if ( !Pattern.compile( "^[a-zA-Z]+[a-zA-Z\\-]*[a-zA-Z]+$" ).matcher( ((String)value)).matches() ) {
			
			log.warn("invalid characters for name: {0}", (String) value);
			((UIInput) uiComponent).setValid(false);
			statusMessages.addToControl(uiComponent.getId()," Must contain lower or upper case letters only !", new Object[] {});
		}
	}

	public void validate( ValueChangeEvent Event ) {
		this.validate( null, Event.getComponent(), Event.getNewValue() );
    }
}
