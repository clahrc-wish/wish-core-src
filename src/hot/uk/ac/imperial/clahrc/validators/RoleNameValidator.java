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
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;

import uk.ac.imperial.clahrc.entity.Roles;

@Name("roleNameValidator")
@Scope(ScopeType.EVENT)
public class RoleNameValidator implements Validator {

	@Logger private Log log;
	@In EntityManager entityManager;
	@In StatusMessages statusMessages;
	
	public void validate( FacesContext fContext, UIComponent uiComponent, Object value ) throws ValidatorException {

		if ( !Pattern.compile( "^[a-zA-Z]+[a-zA-Z_]*[a-zA-Z]+$" ).matcher( ((String)value)).matches() ||
			 ((String)value).contains("__") ) {

			log.warn("invalid character for role name: {0}", (String) value);
			((UIInput) uiComponent).setValid(false);
			statusMessages.addToControl(uiComponent.getId()," Must contain letters only (may be separated by an underscore) !", new Object[] {});
			
		} else {
			Roles Role = null;
			try {
				Role = (Roles) entityManager.createQuery("SELECT r FROM Roles r WHERE r.name = :rolename")
						                    .setParameter("rolename", (String) value)
						                    .getSingleResult();

			} catch (NoResultException e) {
			} catch (Exception e) {
				log.error("validation failed: {0}", (String) value);
				((UIInput) uiComponent).setValid(false);
				statusMessages.addToControl(uiComponent.getId()," Validation filed !", new Object[] {});
			}
			if ( Role != null ) {

				log.warn("role name already exists: {0}", (String) value);
				((UIInput) uiComponent).setValid(false);
				statusMessages.addToControl(uiComponent.getId()," This role name already exists !", new Object[] {});
			}
		}
	}

	public void validate( ValueChangeEvent Event ) {
		this.validate( null, Event.getComponent(), Event.getNewValue() );
    }

}
