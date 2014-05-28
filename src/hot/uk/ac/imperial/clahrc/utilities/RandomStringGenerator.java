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

import java.util.Random;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.remoting.WebRemote;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;

@Name("randomStringGenerator")
@Scope(ScopeType.EVENT)
public class RandomStringGenerator implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final Log log = Logging.getLog( RandomStringGenerator.class );
    private static long DefaultLength = 8; 
    private static final String CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!()";

    @WebRemote
    public String generate() {
    	return this.generate( DefaultLength );
    }
    
    public String generate( long StringLength )
    {
        log.info("randomStringGenerator.randomStringGenerator() action called");
    	
    	StringBuffer ReturnResult = new StringBuffer();;
    	try {
    		Random RandomGenerator = new Random( System.currentTimeMillis() );
            for ( int i = 0; i < StringLength; i++ ) {
                int RandomPosition = RandomGenerator.nextInt( CHARSET.length() );
                ReturnResult.append( CHARSET.charAt( RandomPosition ) );
            }
            Thread.sleep( 100 );
    		
    	} catch ( Throwable twbl ) {
     	    log.error( "Throwable thrown while generating random!", twbl );
     	}
    	
    	return ReturnResult.toString();
    }

}
