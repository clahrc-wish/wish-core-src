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

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Synchronized;
import org.jboss.seam.annotations.remoting.WebRemote;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.web.ServletContexts;
import org.jboss.seam.ScopeType;

@Name("httpSessionChecker")
@Scope(ScopeType.APPLICATION)
@Synchronized
public class HttpSessionChecker
{
	@Logger private	Log log;
	
    @WebRemote
    public boolean isNewSession()
    {
        // implement your business logic here
    	log.debug("Is new session req for: #0", ServletContexts.instance().getRequest().getSession().getId());
        return ServletContexts.instance().getRequest().getSession().isNew();
    }

    // add additional action methods

}
