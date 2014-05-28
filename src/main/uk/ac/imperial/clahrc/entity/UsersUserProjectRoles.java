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
package uk.ac.imperial.clahrc.entity;
// Generated 17-May-2012 13:33:54 by Hibernate Tools 3.4.0.CR1


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * UsersUserProjectRoles generated by hbm2java
 */
@Entity
@Table(name="users_user_project_roles",catalog="clahrc2")
public class UsersUserProjectRoles  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private UsersUserProjectRolesId id;

    public UsersUserProjectRoles() {
    }

    public UsersUserProjectRoles(UsersUserProjectRolesId id) {
       this.id = id;
    }
   
    @EmbeddedId

    
    @AttributeOverrides( {
    @AttributeOverride(name="id", column=@Column(name="ID", nullable=false) ), 
    @AttributeOverride(name="firstName", column=@Column(name="First_Name", nullable=false, length=45) ), 
    @AttributeOverride(name="lastName", column=@Column(name="Last_Name", nullable=false, length=45) ), 
    @AttributeOverride(name="registrationDate", column=@Column(name="Registration_Date", nullable=false, length=10) ), 
    @AttributeOverride(name="active", column=@Column(name="Active", nullable=false, columnDefinition = "BIT") ), 
    @AttributeOverride(name="loginName", column=@Column(name="Login_Name", nullable=false, length=45) ), 
    @AttributeOverride(name="password", column=@Column(name="Password", nullable=false, length=45) ), 
    @AttributeOverride(name="email", column=@Column(name="Email", nullable=false, length=45) ), 
    @AttributeOverride(name="userId", column=@Column(name="User_ID", nullable=false) ), 
    @AttributeOverride(name="projectId", column=@Column(name="Project_ID", nullable=false) ), 
    @AttributeOverride(name="roleId", column=@Column(name="Role_ID", nullable=false) ) } )
    @NotNull
    public UsersUserProjectRolesId getId() {
        return this.id;
    }
    
    public void setId(UsersUserProjectRolesId id) {
        this.id = id;
    }
}


