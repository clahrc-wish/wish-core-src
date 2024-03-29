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
// Generated 24-Oct-2012 14:01:41 by Hibernate Tools 3.4.0.CR1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

/**
 * UserProjectRolesAudit generated by hbm2java
 */
@Entity
@Table(name="user_project_roles_audit"
    ,catalog="clahrc2"
)
public class UserProjectRolesAudit  implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private Users usersByUserId;
    private Projects projects;
    private Roles roles;
    private Users usersByAdminId;
    private Date reqTimestamp;
    private String action;
    private Date resTimestamp;

    public UserProjectRolesAudit() {
    }

    public UserProjectRolesAudit(Users usersByUserId, Projects projects, Roles roles, Users usersByAdminId, Date reqTimestamp, String action, Date resTimestamp) {
       this.usersByUserId = usersByUserId;
       this.projects = projects;
       this.roles = roles;
       this.usersByAdminId = usersByAdminId;
       this.reqTimestamp = reqTimestamp;
       this.action = action;
       this.resTimestamp = resTimestamp;
    }
   
    @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="ID", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="User_ID", nullable=false)
    @NotNull
    public Users getUsersByUserId() {
        return this.usersByUserId;
    }
    
    public void setUsersByUserId(Users usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Project_ID", nullable=false)
    @NotNull
    public Projects getProjects() {
        return this.projects;
    }
    
    public void setProjects(Projects projects) {
        this.projects = projects;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Role_ID", nullable=false)
    @NotNull
    public Roles getRoles() {
        return this.roles;
    }
    
    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Admin_ID", nullable=false)
    @NotNull
    public Users getUsersByAdminId() {
        return this.usersByAdminId;
    }
    
    public void setUsersByAdminId(Users usersByAdminId) {
        this.usersByAdminId = usersByAdminId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Req_Timestamp", nullable=false, length=19)
    @NotNull
    public Date getReqTimestamp() {
        return this.reqTimestamp;
    }
    
    public void setReqTimestamp(Date reqTimestamp) {
        this.reqTimestamp = reqTimestamp;
    }

    
    @Column(name="Action", nullable=false, length=8)
    @NotNull
    @Size(max=8)
    public String getAction() {
        return this.action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Res_Timestamp", nullable=false, length=19)
    @NotNull
    public Date getResTimestamp() {
        return this.resTimestamp;
    }
    
    public void setResTimestamp(Date resTimestamp) {
        this.resTimestamp = resTimestamp;
    }

}


