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
package uk.ac.imperial.clahrc.utilities.fieldoptions;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("sustainabilityFieldOptions")
@Scope(ScopeType.SESSION)
public class SustainabilityFieldOptions implements Serializable {

	private static final long serialVersionUID = 1L;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private List<SelectItem> events = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
	{
		
		try {
			//events.add( new SelectItem( df.parse( "01/01/0001" ), "Click to select..." ) );
			events.add( new SelectItem( df.parse( "25/09/2013" ), "Autumn CLD 2013 (25th September 2013)" ) );
			events.add( new SelectItem( df.parse( "11/06/2013" ), "Summer CLD 2013 (11th June 2013)" ) );
			events.add( new SelectItem( df.parse( "23/04/2013" ), "Spring CLD 2013 (23th April 2013)" ) );
			events.add( new SelectItem( df.parse( "17/01/2013" ), "Winter CLD 2013 (17th January 2013)" ) );
			events.add( new SelectItem( df.parse( "27/09/2012" ), "Autumn CLD 2012 (27th September 2012)" ) );
			events.add( new SelectItem( df.parse( "11/07/2012" ), "Summer CLD 2012 (11th July 2012)" ) );
			events.add( new SelectItem( df.parse( "25/04/2012" ), "Spring CLD 2012 (25th April 2012)" ) );
			events.add( new SelectItem( df.parse( "18/01/2012" ), "Winter CLD 2012 (18th January 2012)" ) );
			events.add( new SelectItem( df.parse( "29/09/2011" ), "Autumn CLD 2011 (29th September 2011)" ) );
			events.add( new SelectItem( df.parse( "06/07/2011" ), "Summer CLD 2011 (6th July 2011)" ) );
			events.add( new SelectItem( df.parse( "05/04/2011" ), "Spring CLD 2011 (5th April 2011)" ) );
			events.add( new SelectItem( df.parse( "20/01/2011" ), "Winter CLD 2011 (20th Jan 2011)" ) );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<SelectItem> getEvents() {
		return events;
	}
	
	private List<SelectItem> score1 = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
	{
		score1.add( new SelectItem( 0, "The change improves efficiency and makes jobs easier" ) );
		score1.add( new SelectItem( 1, "The change improves efficiency but does not make jobs easier" ) );
		score1.add( new SelectItem( 2, "The change does not improve efficiency but does make jobs easier" ) );
		score1.add( new SelectItem( 3, "The change neither improves efficiency nor makes jobs easier" ) );
	}
	public List<SelectItem> getScore1() {
		return score1;
	}
	
	private List<SelectItem> score2 = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
	{
		score2.add( new SelectItem( 0, "Benefits of the change are immediately obvious, supported by evidence and believed by stakeholders" ) );
		score2.add( new SelectItem( 1, "Benefits of the change are not immediately obvious, even though they are supported by evidence and believed by stakeholders" ) );
		score2.add( new SelectItem( 2, "Benefits of the change are not immediately obvious, even though they are supported by evidence. They are not believed by stakeholders" ) );
		score2.add( new SelectItem( 3, "Benefits of the change are neither immediately obvious, supported by evidence nor believed by stakeholders" ) );
	}
	public List<SelectItem> getScore2() {
		return score2;
	}
	
	private List<SelectItem> score3 = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
	{
		score3.add( new SelectItem( 0, "The process can be adapted to other organisational changes and there is a system for continually improving the process" ) );
		score3.add( new SelectItem( 1, "The process can be adapted to other organisational changes but there is no system for continually improving the process" ) );
		score3.add( new SelectItem( 2, "The process is not able to adapt to other organisational changes but there is a system for continually improving the process" ) );
		score3.add( new SelectItem( 3, "The process is not able to adapt to other organisational changes and there is no system for continually improving the process" ) );
	}
	public List<SelectItem> getScore3() {
		return score3;
	}
	
	private List<SelectItem> score4 = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
	{
		score4.add( new SelectItem( 0, "There is a system in place to identify evidence of progress, monitor progress, act on it and communicate results" ) );
		score4.add( new SelectItem( 1, "There is a system in place to identify evidence of progress and act on it but the results are not communicated" ) );
		score4.add( new SelectItem( 2, "There is a system in place to identify evidence and monitor progress. The results are communicated but no one acts on them" ) );
		score4.add( new SelectItem( 3, "There is no system in place to identify evidence of progress or to monitor progress nor act on it or communicate it" ) );
	}
	public List<SelectItem> getScore4() {
		return score4;
	}
	
	private List<SelectItem> score5 = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
	{
		score5.add( new SelectItem( 0, "Staff have been involved from the beginning of the change and adequately trained to sustain the improved process" ) );
		score5.add( new SelectItem( 1, "Staff have been involved from the beginning of the change, but not adequately trained to sustain the improved process" ) );
		score5.add( new SelectItem( 2, "Staff have not been involved from the beginning of the change, but they have been adequately trained to sustain the improved process" ) );
		score5.add( new SelectItem( 3, "Staff have neither been involved from the beginning of the change nor adequately trained to sustain the improved process" ) );
	}
	public List<SelectItem> getScore5() {
		return score5;
	}
	
	private List<SelectItem> score6 = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
	{
		score6.add( new SelectItem( 0, "Staff feel empowered as part of the change process and believe the improvement will be sustained" ) );
		score6.add( new SelectItem( 1, "Staff feel empowered as part of the change process but don't believe the improvement will be sustained" ) );
		score6.add( new SelectItem( 2, "Staff don't feel empowered by the change process but believe the improvement will be sustained" ) );
		score6.add( new SelectItem( 3, "Staff don't feel empowered by the change process or believe the improvement will be sustained" ) );
	}
	public List<SelectItem> getScore6() {
		return score6;
	}
	
	private List<SelectItem> score7 = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
	{
		score7.add( new SelectItem( 0, "Organisational leaders take responsibility for efforts to sustain the change process. Staff generally share information with, and actively seek advice from, the leader" ) );
		score7.add( new SelectItem( 1, "Organisational leaders don't take responsibility for efforts to sustain the change process. Staff generally share information with, and seek advice from, the leader" ) );
		score7.add( new SelectItem( 2, "Organisational leaders take responsibility for efforts to sustain the change process. Staff typically do not share information with, or seek advice from, the leader" ) );
		score7.add( new SelectItem( 3, "Organisational leaders don't take responsibility for efforts to sustain the change process. Staff typically do not share information with, or seek advice from, the leader" ) );
	}
	public List<SelectItem> getScore7() {
		return score7;
	}
	
	private List<SelectItem> score8 = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
	{
		score8.add( new SelectItem( 0, "Clinical leaders take responsibility for efforts to sustain the change process. Staff generally share information with, and actively seek advice from, the leader" ) );
		score8.add( new SelectItem( 1, "Clinical leaders don't take responsibility for efforts to sustain the change process. Staff generally share information with, and seek advice from, the leader" ) );
		score8.add( new SelectItem( 2, "Clinical leaders take responsibility for efforts to sustain the change process. Staff typically do not share information with, or seek advice from, the leader" ) );
		score8.add( new SelectItem( 3, "Clinical leaders don't take responsibility for efforts to sustain the change process. Staff typically do not share information with, or seek advice from, the leader" ) );
	}
	public List<SelectItem> getScore8() {
		return score8;
	}
	
	private List<SelectItem> score9 = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
	{
		score9.add( new SelectItem( 0, "There is a history of successful sustainability and the improvement goals are consistent with the organisation's strategic aims" ) );
		score9.add( new SelectItem( 1, "There is a history of successful sustainability but the improvement goals are inconsistent with the organisation's strategic aims" ) );
		score9.add( new SelectItem( 2, "There is no history of successful sustainability but the improvement goals are consistent with the organisation's strategic aims" ) );
		score9.add( new SelectItem( 3, "There is no history of successful sustainability and the improvement goals are inconsistent with the organisation's strategic aims" ) );
	}
	public List<SelectItem> getScore9() {
		return score9;
	}
	
	private List<SelectItem> score10 = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
	{
		score10.add( new SelectItem( 0, "Staff, facilities and equipment, job descriptions, policies, procedures and communication systems are appropriate for sustaining the improved process" ) );
		score10.add( new SelectItem( 1, "There is an appropriate level of staff, facilities and equipment,  but inadequate job descriptions, policies, procedures and communication systems for sustaining the change" ) );
		score10.add( new SelectItem( 2, "The levels of staff, facilities and equipment to sustain the change are not appropriate although job descriptions, policies, procedures and communication systems are adequate" ) );
		score10.add( new SelectItem( 3, "Staff, facilities and equipment, job descriptions, policies, procedures and communication systems are all not appropriate for sustaining the change" ) );
	}
	public List<SelectItem> getScore10() {
		return score10;
	}
}
