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
 * Pdsa generated by hbm2java
 */
@Entity
@Table(name = "pdsa", catalog = "clahrc2")
public class Pdsa implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Users users;
	private Projects projects;
	private Date eventDate;
	private String description;
	private String doDescription;
	private String studyDescription;
	private String actDescription;
	private String planTeam;
	private String studyTeam;
	private String doTeam;
	private String actTeam;
	private Date doDate;
	private Date studyDate;
	private Date actDate;
	private String cycleTitle;
	private Date rcTimestamp;

	public Pdsa() {
	}

	public Pdsa(Users users, Projects projects, Date eventDate, Date rcTimestamp) {
		this.users = users;
		this.projects = projects;
		this.eventDate = eventDate;
		this.rcTimestamp = rcTimestamp;
	}

	public Pdsa(Users users, Projects projects, Date eventDate,
			String description, String doDescription, String studyDescription,
			String actDescription, String planTeam, String studyTeam,
			String doTeam, String actTeam, Date doDate, Date studyDate,
			Date actDate, String cycleTitle, Date rcTimestamp) {
		this.users = users;
		this.projects = projects;
		this.eventDate = eventDate;
		this.description = description;
		this.doDescription = doDescription;
		this.studyDescription = studyDescription;
		this.actDescription = actDescription;
		this.planTeam = planTeam;
		this.studyTeam = studyTeam;
		this.doTeam = doTeam;
		this.actTeam = actTeam;
		this.doDate = doDate;
		this.studyDate = studyDate;
		this.actDate = actDate;
		this.cycleTitle = cycleTitle;
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

	@Column(name = "Description", length = 5000)
	@Size(max = 5000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "Do_Description", length = 5000)
	@Size(max = 5000)
	public String getDoDescription() {
		return this.doDescription;
	}

	public void setDoDescription(String doDescription) {
		this.doDescription = doDescription;
	}

	@Column(name = "Study_Description", length = 5000)
	@Size(max = 5000)
	public String getStudyDescription() {
		return this.studyDescription;
	}

	public void setStudyDescription(String studyDescription) {
		this.studyDescription = studyDescription;
	}

	@Column(name = "Act_Description", length = 5000)
	@Size(max = 5000)
	public String getActDescription() {
		return this.actDescription;
	}

	public void setActDescription(String actDescription) {
		this.actDescription = actDescription;
	}

	@Column(name = "Plan_Team", length = 5000)
	@Size(max = 5000)
	public String getPlanTeam() {
		return this.planTeam;
	}

	public void setPlanTeam(String planTeam) {
		this.planTeam = planTeam;
	}

	@Column(name = "Study_Team", length = 5000)
	@Size(max = 5000)
	public String getStudyTeam() {
		return this.studyTeam;
	}

	public void setStudyTeam(String studyTeam) {
		this.studyTeam = studyTeam;
	}

	@Column(name = "Do_Team", length = 5000)
	@Size(max = 5000)
	public String getDoTeam() {
		return this.doTeam;
	}

	public void setDoTeam(String doTeam) {
		this.doTeam = doTeam;
	}

	@Column(name = "Act_Team", length = 5000)
	@Size(max = 5000)
	public String getActTeam() {
		return this.actTeam;
	}

	public void setActTeam(String actTeam) {
		this.actTeam = actTeam;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "Do_Date", length = 10)
	public Date getDoDate() {
		return this.doDate;
	}

	public void setDoDate(Date doDate) {
		this.doDate = doDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "Study_Date", length = 10)
	public Date getStudyDate() {
		return this.studyDate;
	}

	public void setStudyDate(Date studyDate) {
		this.studyDate = studyDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "Act_Date", length = 10)
	public Date getActDate() {
		return this.actDate;
	}

	public void setActDate(Date actDate) {
		this.actDate = actDate;
	}

	@Column(name = "Cycle_Title", length = 2000)
	@Size(max = 2000)
	public String getCycleTitle() {
		return this.cycleTitle;
	}

	public void setCycleTitle(String cycleTitle) {
		this.cycleTitle = cycleTitle;
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
