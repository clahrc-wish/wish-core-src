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
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("userProjectRolesAuditList")
public class UserProjectRolesAuditList extends EntityQuery<UserProjectRolesAudit>
{

	private static final long serialVersionUID = 1L;
	private static final String EJBQL = "select userProjectRolesAudit from UserProjectRolesAudit userProjectRolesAudit";
    private static final String[] RESTRICTIONS = {
        "lower(userProjectRolesAudit.action) like lower(concat(#{userProjectRolesAuditList.userProjectRolesAudit.action},'%'))",
    };

    private UserProjectRolesAudit userProjectRolesAudit = new UserProjectRolesAudit();

    public UserProjectRolesAuditList()
    {
        setEjbql(EJBQL);
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setMaxResults(25);
		setOrderColumn( "userProjectRolesAudit.resTimestamp" );
        setOrderDirection( "desc" );
    }

    public UserProjectRolesAudit getUserProjectRolesAudit()
    {
        return userProjectRolesAudit;
    }
}
