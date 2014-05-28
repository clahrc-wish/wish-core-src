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
// Generated 06-Mar-2012 11:45:25 by Hibernate Tools 3.4.0.CR1


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

/**
 * UserMessages generated by hbm2java
 */
@Entity
@Table(name="user_messages",catalog="clahrc2")
public class UserMessages  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private UserMessagesId id;
    private Users users;
	private Comment comment;
    private Projects projects;
    private String message;

    public UserMessages() {
    }

    public UserMessages(UserMessagesId id, Users users, Comment comment, Projects projects, String message) {
       this.id = id;
       this.users = users;
       this.comment = comment;
       this.projects = projects;
       this.message = message;
    }
   
    @EmbeddedId
    @AttributeOverrides( {
    @AttributeOverride(name="userId", column=@Column(name="User_ID", nullable=false) ), 
    @AttributeOverride(name="projectId", column=@Column(name="Project_ID", nullable=false) ), 
    @AttributeOverride(name="rcTimestamp", column=@Column(name="Rc_Timestamp", nullable=false, length=19) ) } )
    @NotNull
    public UserMessagesId getId() {
        return this.id;
    }
    
    public void setId(UserMessagesId id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="User_ID", nullable=false, insertable=false, updatable=false)
    @NotNull
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Comment_ID")
	public Comment getComment() {
		return this.comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Project_ID", nullable=false, insertable=false, updatable=false)
    @NotNull
    public Projects getProjects() {
        return this.projects;
    }
    
    public void setProjects(Projects projects) {
        this.projects = projects;
    }

    
    @Column(name="Message", nullable=false, length=1000)
    @NotNull
    @Size(max=1000)
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

}


