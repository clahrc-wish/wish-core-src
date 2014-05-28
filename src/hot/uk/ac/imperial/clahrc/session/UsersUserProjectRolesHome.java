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
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("usersUserProjectRolesHome")
public class UsersUserProjectRolesHome extends EntityHome<UsersUserProjectRoles>
{
	private static final long serialVersionUID = 1L;

	public void setUsersUserProjectRolesId(UsersUserProjectRolesId id)
    {
        setId(id);
    }

    public UsersUserProjectRolesId getUsersUserProjectRolesId()
    {
        return (UsersUserProjectRolesId) getId();
    }

    public UsersUserProjectRolesHome()
    {
        setUsersUserProjectRolesId( new UsersUserProjectRolesId() );
    }

    @Override
    public boolean isIdDefined()
    {
        if ( getUsersUserProjectRolesId().getId()==0 ) return false;
        if ( getUsersUserProjectRolesId().getFirstName()==null || "".equals( getUsersUserProjectRolesId().getFirstName() ) ) return false;
        if ( getUsersUserProjectRolesId().getLastName()==null || "".equals( getUsersUserProjectRolesId().getLastName() ) ) return false;
        if ( getUsersUserProjectRolesId().getRegistrationDate()==null ) return false;
        //if ( getUsersUserProjectRolesId().isActive()==0 ) return false;
        if ( getUsersUserProjectRolesId().getLoginName()==null || "".equals( getUsersUserProjectRolesId().getLoginName() ) ) return false;
        if ( getUsersUserProjectRolesId().getPassword()==null || "".equals( getUsersUserProjectRolesId().getPassword() ) ) return false;
        if ( getUsersUserProjectRolesId().getEmail()==null || "".equals( getUsersUserProjectRolesId().getEmail() ) ) return false;
        if ( getUsersUserProjectRolesId().getUserId()==0 ) return false;
        if ( getUsersUserProjectRolesId().getProjectId()==0 ) return false;
        if ( getUsersUserProjectRolesId().getRoleId()==0 ) return false;
        return true;
    }

    @Override
    protected UsersUserProjectRoles createInstance()
    {
        UsersUserProjectRoles usersUserProjectRoles = new UsersUserProjectRoles();
        usersUserProjectRoles.setId( new UsersUserProjectRolesId() );
        return usersUserProjectRoles;
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
    }

    public boolean isWired()
    {
        return true;
    }

    public UsersUserProjectRoles getDefinedInstance()
    {
        return isIdDefined() ? getInstance() : null;
    }


}

