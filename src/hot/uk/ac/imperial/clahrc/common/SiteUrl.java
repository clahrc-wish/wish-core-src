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

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Synchronized;

@Name("siteUrl")
@Synchronized
@AutoCreate
public class SiteUrl implements Serializable {

    @In FacesContext facesContext;
    
	private static final long serialVersionUID = 1L;
	public static final String PROTOCOL = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme();
	public static final String SERVER = FacesContext.getCurrentInstance().getExternalContext().getRequestServerName();
	public static final Integer PORT = FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort();
	public static final String CONTEXT = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	public static final String BASE_PATH = PROTOCOL + "://" + SERVER + ":" + PORT + CONTEXT;
}
