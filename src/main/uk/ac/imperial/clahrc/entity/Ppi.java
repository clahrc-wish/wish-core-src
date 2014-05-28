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

// Generated 09-Nov-2011 21:46:06 by Hibernate Tools 3.4.0.CR1

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
 * Ppi generated by hbm2java
 */
@Entity
@Table(name = "ppi", catalog = "clahrc2")
public class Ppi implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Users users;
	private Projects projects;
	private Date entryDate;
	private String role;
	private String receptive;
	private String difference;
	private String plans;
	private String comments;
	private Date rcTimestamp;

	public Ppi() {
	}

	public Ppi(Users users, Projects projects, Date entryDate, String role,
			String receptive, String difference, String plans, String comments,
			Date rcTimestamp) {
		this.users = users;
		this.projects = projects;
		this.entryDate = entryDate;
		this.role = role;
		this.receptive = receptive;
		this.difference = difference;
		this.plans = plans;
		this.comments = comments;
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
	@Column(name = "Entry_Date", nullable = false, length = 10)
	@NotNull
	public Date getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	@Column(name = "Role", nullable = false, length = 45)
	@NotNull
	@Size(max = 45)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Column(name = "Receptive", nullable = false, length = 200)
	@NotNull
	@Size(max = 45)
	public String getReceptive() {
		return this.receptive;
	}

	public void setReceptive(String receptive) {
		this.receptive = receptive;
	}

	@Column(name = "Difference", nullable = false, length = 200)
	@NotNull
	@Size(max = 45)
	public String getDifference() {
		return this.difference;
	}

	public void setDifference(String difference) {
		this.difference = difference;
	}

	@Column(name = "Plans", nullable = false, length = 200)
	@NotNull
	@Size(max = 45)
	public String getPlans() {
		return this.plans;
	}

	public void setPlans(String plans) {
		this.plans = plans;
	}

	@Column(name = "Comments", nullable = false, length = 1000)
	@NotNull
	@Size(max = 1000)
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Rc_Timestamp", nullable = false, length = 19)
	public Date getRcTimestamp() {
		return this.rcTimestamp;
	}

	public void setRcTimestamp(Date rcTimestamp) {
		this.rcTimestamp = (rcTimestamp == null ? new Timestamp(Calendar.getInstance().getTimeInMillis()) : rcTimestamp);
	}

	/////copied over from clahrc1 to support Java->Db->Java jive - ask Vasa
	public String receptiveVal() {
		if (receptive.equals("A")) return "The team are not receptive to the involvement of patients.";
		else if (receptive.equals("B")) return "The team are receptive to the idea of the involvement of patients but patient input is largely ignored.";
		else if (receptive.equals("C")) return "The team are receptive to the involvement of patients and patient input is valued.";
		else return null;
	}
	public String differenceVal() {
		if (difference.equals("A")) return "Involving patients makes no difference to our project.";
		else if (difference.equals("B")) return "I am not sure what difference involving patients makes to our project.";
		else if (difference.equals("C")) return "Involving patients makes a positive difference to our project.";
		else return null;
	}
	public String plansVal() {
		if (plans.equals("A")) return "We have not developed plans to involve more patients.";
		else if (plans.equals("B")) return "We have developed plans to involve more patients but have not involved them yet.";
		else if (plans.equals("C")) return "We are involving more patients.";
		else return null;
	}
	
	//public int compareTo(PPIEntry o)
	public int compareTo(Ppi o)
	{
		return -1 * entryDate.compareTo(o.entryDate);
	}
	/////copied over from clahrc1
}
