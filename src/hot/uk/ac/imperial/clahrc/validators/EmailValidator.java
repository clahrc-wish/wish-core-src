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

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.naming.NamingException;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.ScopeType;

@Name("emailValidator")
@Scope(ScopeType.EVENT)
public class EmailValidator implements Validator {

	@Logger private Log log;
	@In StatusMessages statusMessages;
	
	public void validate( FacesContext fContext, UIComponent uiComponent, Object value ) throws ValidatorException {
		
		if ( (value == null) || ((String)value).isEmpty() || 
			 ((String)value).length() < 5 || ((String)value).length() > 45 ||
			 !((String)value).contains("@")) {
			
			log.warn("invalid e-mail address value: {0}", (String) value);
			((UIInput) uiComponent).setValid(false);
			statusMessages.addToControl(uiComponent.getId()," Email must be between 5 and 45 characters long and contain '@' !", new Object[] {});
			
		} else {
			String EmailAddress = (String) value;
			String DomainName = EmailAddress.substring((EmailAddress.indexOf("@") + 1));

			try {
				List<String> MailHosts = EAddressVerifier.getMX(DomainName);
				if ((MailHosts == null) || MailHosts.isEmpty()) {
					
					log.warn("domain server for this e-mail address appears to be invalid/unreachable: {0}", DomainName);
					((UIInput) uiComponent).setValid(false);
					statusMessages.addToControl(uiComponent.getId()," The domain server for this e-mail address appears to be invalid or unreachable !",new Object[] {});
				}
			} catch (NamingException exc) {

				log.warn("domain server for this e-mail address appears to be invalid or unreachable: {0}", DomainName);
				((UIInput) uiComponent).setValid(false);
				statusMessages.addToControl(uiComponent.getId()," The domain server for this e-mail address appears to be invalid or unreachable !",new Object[] {});
				
			} catch (Exception exc) {
				log.error("mail hosts lookup failed: {0}", exc);
				((UIInput) uiComponent).setValid(false);
				statusMessages.addToControl(uiComponent.getId()," Validation failed !", new Object[] {});
			}
		}
	}
	
	public void validate( ValueChangeEvent Event ) {
		this.validate( null, Event.getComponent(), Event.getNewValue() );
    }
	
}

