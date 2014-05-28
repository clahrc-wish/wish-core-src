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
package uk.ac.imperial.clahrc.utilities;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import uk.ac.imperial.clahrc.entity.Roles;

@Name("rolesConverter")
@Converter
@BypassInterceptors
public class RolesConverter implements javax.faces.convert.Converter, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {

		if ( (value == null) || (value.isEmpty()) ) {
			return null;
		}

		Roles Role = null;
		try {
			EntityManager entityManager = (EntityManager)Component.getInstance( "entityManager", true );
			Role = (Roles)entityManager.createQuery( "SELECT r FROM Roles r WHERE r.name = :roleName" )
			                                 .setParameter( "roleName", value )
			                                 .getSingleResult();
		} catch ( NoResultException e ) {
		}

		if ( Role == null ) {
			return null;
		}
		return Role;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object roleObject) {

		if (!(roleObject instanceof Roles)) {
			return null;
		}
		return ((Roles) roleObject).getName();
	}
}
