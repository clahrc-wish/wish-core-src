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
package uk.ac.imperial.clahrc.session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import uk.ac.imperial.clahrc.common.ActiveProjectHome;
import uk.ac.imperial.clahrc.common.CustomIdentity;
import uk.ac.imperial.clahrc.entity.Comment;
import uk.ac.imperial.clahrc.utilities.loaders.ProjectIDs;
import uk.ac.imperial.clahrc.utilities.loaders.RoleIDs;

@Name("commentList")
public class CommentList extends EntityQuery<Comment> {

	@In CustomIdentity identity;
	@In ActiveProjectHome activeProjectHome;
	@In ProjectIDs projectIDs;
	@In RoleIDs roleIDs;
	
	private static final long serialVersionUID = 1L;
	private static final String EJBQL = "select comment from Comment comment";
	private static final String[] RESTRICTIONS = { "lower(comment.comment) like lower(concat(#{commentList.comment.comment},'%'))", };
	private Comment comment = new Comment();

	public CommentList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
		setOrderColumn( "comment.rcTimestamp" );
        setOrderDirection( "desc" );
	}

	public Comment getComment() {
		return comment;
	}
	
	@Override
	public List<Comment> getResultList() {
		
		if ( !identity.hasRole( roleIDs.getSUPER_ADMIN_id() ) ) {
			
			List<String> whereClauses = new ArrayList<String>( Arrays.asList( RESTRICTIONS ) );
			whereClauses.add( "comment.projects.id = #{activeProjectHome.definedInstance.id}" );

			if ( identity.hasRole( roleIDs.getPROJECT_STAFF_id() ) && !identity.hasRole( roleIDs.getPROJECT_ADMIN_id() ) ) {
				whereClauses.add( "comment.users.id = #{identity.id}" );
			}
			
			setRestrictionExpressionStrings( whereClauses );
		}

		return super.getResultList();
	}
}
