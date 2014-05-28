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

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Synchronized;
import org.jboss.seam.security.Identity;

@Name("birtFilter")
@Scope(ScopeType.APPLICATION)
@Synchronized
public class BirtFilter implements java.io.Serializable, Filter {

	private static final long serialVersionUID = 1L;
	
	private FilterConfig filterConfig;
	private String loginPage;

	@Override
	public void init(FilterConfig config) throws ServletException {

		filterConfig = config;
		if ( config != null ) { 
			loginPage = config.getInitParameter( "login_page" );
	    }
	}
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		Identity identity = (Identity)Component.getInstance( "org.jboss.seam.security.identity", true );
		if ( (identity != null) && identity.isLoggedIn() ) {
			chain.doFilter( request, response );
		}
		else {
			filterConfig.getServletContext().getRequestDispatcher( loginPage ).forward( request, response );
		}
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
