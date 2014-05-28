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

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Name("projectTablesList")
@AutoCreate
public class ProjectTablesList extends EntityQuery<ProjectTables>
{
	private static final long serialVersionUID = 1L;
	private static final String EJBQL = "select projectTables from ProjectTables projectTables";
    private static final String[] RESTRICTIONS = {
        "lower(projectTables.id.tableName) like lower(concat(#{projectTablesList.projectTables.id.tableName},'%'))",
        "lower(projectTables.tableLabel) like lower(concat(#{projectTablesList.projectTables.tableLabel},'%'))",
    };

    private ProjectTables projectTables;

    public ProjectTablesList()
    {
        projectTables = new ProjectTables();
        projectTables.setId( new ProjectTablesId() );
        setEjbql(EJBQL);
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setMaxResults(25);
    }

    public ProjectTables getProjectTables()
    {
        return projectTables;
    }
    
    public String getLabel( String tableName ) {
    	
    	String originalTableName = this.projectTables.getId().getTableName();
    	String originalLogicOperator = this.getRestrictionLogicOperator();
    	List<String> originalRestrictions = this.getRestrictionExpressionStrings();
    	this.projectTables.getId().setTableName( tableName );
    	this.setRestrictionExpressionStrings( new ArrayList<String>( Arrays.asList( "lower(projectTables.id.tableName) = lower(#{projectTablesList.projectTables.id.tableName})" ) ) );
    	this.setRestrictionLogicOperator( "and" );
    	String tableLabel = this.getSingleResult().getTableLabel(); 
    	this.projectTables.getId().setTableName( originalTableName );
    	this.setRestrictionExpressionStrings( originalRestrictions );
    	this.setRestrictionLogicOperator( originalLogicOperator );
    	return tableLabel;
    }    
}
