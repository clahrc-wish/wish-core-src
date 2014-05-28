package uk.ac.imperial.clahrc.session;

import uk.ac.imperial.clahrc.entity.*;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

@Name("projectGroupingsList")
public class ProjectGroupingsList extends EntityQuery<ProjectGroupings> {

	private static final long serialVersionUID = 1L;
	private static final String EJBQL = "select projectGroupings from ProjectGroupings projectGroupings";
	private static final String[] RESTRICTIONS = {
			"lower(projectGroupings.name) like lower(concat(#{projectGroupingsList.projectGroupings.name},'%'))",
			"lower(projectGroupings.description) like lower(concat(#{projectGroupingsList.projectGroupings.description},'%'))",
			"lower(projectGroupings.theme) like lower(concat(#{projectGroupingsList.projectGroupings.theme},'%'))", };

	private ProjectGroupings projectGroupings = new ProjectGroupings();

	public ProjectGroupingsList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public ProjectGroupings getProjectGroupings() {
		return projectGroupings;
	}
	
	public List<SelectItem> getResultListAsSelectItems() {

		Integer RecordsNumLimit = this.getMaxResults();
		setMaxResults( null );
		List<SelectItem> nameGroupingList = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
		
		List<ProjectGroupings> projectGroupings = this.getResultList();
		for ( ProjectGroupings grouping : projectGroupings ) {
			nameGroupingList.add( new SelectItem( grouping, grouping.getName() ) );
		}
		
		setMaxResults( RecordsNumLimit );
		return nameGroupingList;
	}
}
