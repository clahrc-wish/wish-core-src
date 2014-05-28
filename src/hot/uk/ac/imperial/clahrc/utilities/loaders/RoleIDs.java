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
package uk.ac.imperial.clahrc.utilities.loaders;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Synchronized;

@Name("roleIDs")
@Scope(ScopeType.APPLICATION)
@AutoCreate
@Synchronized
public class RoleIDs {

	//////////////////////////////////////////////////////
	private final int SUPER_ADMIN_id = 1;
	private final String SUPER_ADMIN_str = "system_administrator";
	
	private final int WRT_ADMIN_id = 2;
	private final String WRT_ADMIN_str = "programme_administrator";
	
	private final int PROGRAMME_LEAD_id = 3;
	private final String PROGRAMME_LEAD_str = "programme_lead";
	
	private final int PROJECT_ADMIN_id = 4;
	private final String PROJECT_ADMIN_str = "project_administrator";
	
	private final int INFORMATION_LEAD_id = 5;
	private final String INFORMATION_LEAD_str = "information_lead";
	
	private final int PROJECT_STAFF_id = 6;
	private final String PROJECT_STAFF_str = "project_member";
	
	private final int PROGRAMME_EVALUATOR_id = 7;
	private final String PROGRAMME_EVALUATOR_str = "programme_evaluator";
	
	private final int PROJECT_VIEWER_id = 8;
	private final String PROJECT_VIEWER_str = "project_viewer";
	//////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////	
	public int getSUPER_ADMIN_id() {
		return SUPER_ADMIN_id;
	}
	public String getSUPER_ADMIN_str() {
		return SUPER_ADMIN_str;
	}
	public int getWRT_ADMIN_id() {
		return WRT_ADMIN_id;
	}
	public String getWRT_ADMIN_str() {
		return WRT_ADMIN_str;
	}
	public int getPROGRAMME_LEAD_id() {
		return PROGRAMME_LEAD_id;
	}
	public String getPROGRAMME_LEAD_str() {
		return PROGRAMME_LEAD_str;
	}
	public int getPROJECT_ADMIN_id() {
		return PROJECT_ADMIN_id;
	}
	public String getPROJECT_ADMIN_str() {
		return PROJECT_ADMIN_str;
	}
	public int getINFORMATION_LEAD_id() {
		return INFORMATION_LEAD_id;
	}
	public String getINFORMATION_LEAD_str() {
		return INFORMATION_LEAD_str;
	}
	public int getPROJECT_STAFF_id() {
		return PROJECT_STAFF_id;
	}
	public String getPROJECT_STAFF_str() {
		return PROJECT_STAFF_str;
	}
	public int getPROGRAMME_EVALUATOR_id() {
		return PROGRAMME_EVALUATOR_id;
	}
	public String getPROGRAMME_EVALUATOR_str() {
		return PROGRAMME_EVALUATOR_str;
	}
	public int getPROJECT_VIEWER_id() {
		return PROJECT_VIEWER_id;
	}
	public String getPROJECT_VIEWER_str() {
		return PROJECT_VIEWER_str;
	}
	//////////////////////////////////////////////////////
	
	///////////////////////////////////////////////
	//the code below required as a work-around the SEAM's eFing restrictions on 
	// 'where clause' parameter binding in *List.java files
	private Map<Integer, String> IDs_id = Collections.synchronizedMap( new HashMap<Integer, String>() );
	private Map<Integer, String> IDs_str = Collections.synchronizedMap( new HashMap<Integer, String>() );
	private Map<Integer, String> IDs_name = Collections.synchronizedMap( new HashMap<Integer, String>() );
	
	public Map<Integer, String> getIDs_id() {
		return IDs_id;
	}
	public Map<Integer, String> getIDs_str() {
		return IDs_str;
	}
	public Map<Integer, String> getIDs_name() {
		return IDs_name;
	}
	
	public RoleIDs() {
		IDs_id.put( 1, "SUPER_ADMIN_id" );
		IDs_id.put( 2, "WRT_ADMIN_id" );
		IDs_id.put( 3, "PROGRAMME_LEAD_id" );
		IDs_id.put( 4, "PROJECT_ADMIN_id" );
		IDs_id.put( 5, "INFORMATION_LEAD_id" );
		IDs_id.put( 6, "PROJECT_STAFF_id" );
		IDs_id.put( 7, "PROGRAMME_EVALUATOR_id" );
		IDs_id.put( 8, "PROJECT_VIEWER_id" );
		////////////////////////////////////
		IDs_str.put( 1, "SUPER_ADMIN_str" );
		IDs_str.put( 2, "WRT_ADMIN_str" );
		IDs_str.put( 3, "PROGRAMME_LEAD_str" );
		IDs_str.put( 4, "PROJECT_ADMIN_str" );
		IDs_str.put( 5, "INFORMATION_LEAD_str" );
		IDs_str.put( 6, "PROJECT_STAFF_str" );
		IDs_str.put( 7, "PROGRAMME_EVALUATOR_str" );
		IDs_str.put( 8, "PROJECT_VIEWER_str" );
		////////////////////////////////////
		IDs_name.put( 1, "SUPER_ADMIN" );
		IDs_name.put( 2, "WRT_ADMIN" );
		IDs_name.put( 3, "PROGRAMME_LEAD" );
		IDs_name.put( 4, "PROJECT_ADMIN" );
		IDs_name.put( 5, "INFORMATION_LEAD" );
		IDs_name.put( 6, "PROJECT_STAFF" );
		IDs_name.put( 7, "PROGRAMME_EVALUATOR" );
		IDs_name.put( 8, "PROJECT_VIEWER" );
	}
	///////////////////////////////////////////////
}
