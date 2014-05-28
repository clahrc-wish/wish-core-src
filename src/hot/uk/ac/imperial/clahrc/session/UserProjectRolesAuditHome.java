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
package uk.ac.imperial.clahrc.session;

import uk.ac.imperial.clahrc.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("userProjectRolesAuditHome")
public class UserProjectRolesAuditHome extends EntityHome<UserProjectRolesAudit>
{
	private static final long serialVersionUID = 1L;
	
	@In(create=true) UsersHome usersHome;
    @In(create=true) ProjectsHome projectsHome;
    @In(create=true) RolesHome rolesHome;

    public void setUserProjectRolesAuditId(Integer id)
    {
        setId(id);
    }

    public Integer getUserProjectRolesAuditId()
    {
        return (Integer) getId();
    }

    @Override
    protected UserProjectRolesAudit createInstance()
    {
        UserProjectRolesAudit userProjectRolesAudit = new UserProjectRolesAudit();
        return userProjectRolesAudit;
    }

    public void load()
    {
        if (isIdDefined())
        {
            wire();
        }
    }

    public void wire()
    {
        getInstance();
        Users usersByUserId=usersHome.getDefinedInstance();
        if ( usersByUserId!=null )
        {
           getInstance().setUsersByUserId(usersByUserId);
        }
        Projects projects=projectsHome.getDefinedInstance();
        if ( projects!=null )
        {
           getInstance().setProjects(projects);
        }
        Roles roles=rolesHome.getDefinedInstance();
        if ( roles!=null )
        {
           getInstance().setRoles(roles);
        }
        Users usersByAdminId=usersHome.getDefinedInstance();
        if ( usersByAdminId!=null )
        {
           getInstance().setUsersByAdminId(usersByAdminId);
        }
    }

    public boolean isWired()
    {
        if ( getInstance().getUsersByUserId()==null ) return false;
        if ( getInstance().getProjects()==null ) return false;
        if ( getInstance().getRoles()==null ) return false;
        if ( getInstance().getUsersByAdminId()==null ) return false;
        return true;
    }

    public UserProjectRolesAudit getDefinedInstance()
    {
        return isIdDefined() ? getInstance() : null;
    }


}

