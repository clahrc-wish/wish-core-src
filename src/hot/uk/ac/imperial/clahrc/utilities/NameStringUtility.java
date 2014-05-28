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
package uk.ac.imperial.clahrc.utilities;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("nameStringUtility")
@Scope(ScopeType.EVENT)
public class NameStringUtility {

    public String toCamelCase( String TableName ) {

        String[] Substrings = TableName.toLowerCase().split( "_" );
        StringBuilder CamelCasedName = new StringBuilder();
        for ( String Substring : Substrings ) {
            CamelCasedName.append( Character.toUpperCase( Substring.charAt(0) ) );
            CamelCasedName.append( Substring.substring( 1 ) );
        }
        return CamelCasedName.toString();
    }
    
    public String toFirstLowerCamelCase( String TableName ) {
        
        String CamelCaseName = toCamelCase( TableName );
        StringBuilder FirstLowerCamelCasedName = new StringBuilder();
        FirstLowerCamelCasedName.append( Character.toLowerCase( CamelCaseName.charAt(0) ) );
        FirstLowerCamelCasedName.append( CamelCaseName.substring( 1 ) );
        return FirstLowerCamelCasedName.toString();
    }
}
