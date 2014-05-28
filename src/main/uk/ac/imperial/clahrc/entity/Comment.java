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

// Generated 02-Dec-2011 18:48:54 by Hibernate Tools 3.4.0.CR1

import java.sql.Timestamp;
import java.util.Calendar;
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
 * Comment generated by hbm2java
 */
@Entity
@Table(name = "comment", catalog = "clahrc2")
public class Comment implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Users users;
	private Projects projects;
	private Date eventDate;
	private String comment;
	private Date rcTimestamp;

	public Comment() {
	}

	public Comment(Users users, Projects projects, Date eventDate,
			Date rcTimestamp) {
		this.users = users;
		this.projects = projects;
		this.eventDate = eventDate;
		this.rcTimestamp = rcTimestamp;
	}

	public Comment(Users users, Projects projects, Date eventDate,
			String comment, Date rcTimestamp) {
		this.users = users;
		this.projects = projects;
		this.eventDate = eventDate;
		this.comment = comment;
		this.rcTimestamp = rcTimestamp;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "User_ID", nullable = false)
	@NotNull
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Project_ID", nullable = false)
	@NotNull
	public Projects getProjects() {
		return this.projects;
	}

	public void setProjects(Projects projects) {
		this.projects = projects;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "Event_Date", nullable = false, length = 10)
	@NotNull
	public Date getEventDate() {
		return this.eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	@Column(name = "Comment", length = 1000)
	@Size(max = 1000)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Rc_Timestamp", nullable = false, length = 19)
	public Date getRcTimestamp() {
		return this.rcTimestamp;
	}

	public void setRcTimestamp(Date rcTimestamp) {
		this.rcTimestamp = (rcTimestamp == null ? new Timestamp(Calendar.getInstance().getTimeInMillis()) : rcTimestamp);
	}

}