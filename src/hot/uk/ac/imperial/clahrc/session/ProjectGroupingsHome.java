package uk.ac.imperial.clahrc.session;

import uk.ac.imperial.clahrc.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("projectGroupingsHome")
public class ProjectGroupingsHome extends EntityHome<ProjectGroupings> {

	private static final long serialVersionUID = 1L;

	public void setProjectGroupingsId(Integer id) {
		setId(id);
	}

	public Integer getProjectGroupingsId() {
		return (Integer) getId();
	}

	@Override
	protected ProjectGroupings createInstance() {
		ProjectGroupings projectGroupings = new ProjectGroupings();
		return projectGroupings;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public ProjectGroupings getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Projects> getProjectses() {
		return getInstance() == null ? null : new ArrayList<Projects>(
				getInstance().getProjectses());
	}

}
