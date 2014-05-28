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
package uk.ac.imperial.clahrc.validators;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class EAddressVerifier {

	// Vesso: The e-mail verification process below relies on 
	// direct socket communication with mail servers on a specific default/or alternative port.
	// Neither the port number is guaranteed nor the response time seems to be acceptable most of the times.
	/*
	 * ...unused code removed...
    */
	
    //private static ArrayList getMX( String HostName ) throws NamingException {
    public static List<String> getMX( String HostName ) throws NamingException {
        //System.out.println( "getMX():ENTER" );
        
        // Perform a DNS lookup for MX records in the domain
        Hashtable<String, String> EnvParams = new Hashtable<String, String>();
        EnvParams.put( "java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory" );
        DirContext DirectoryContext = new InitialDirContext( EnvParams );
        Attributes Attribs = DirectoryContext.getAttributes( HostName, new String[]{ "MX" } );
        Attribute Attrib = Attribs.get( "MX" );

        // no MX record - try the machine itself
        if ( (Attrib == null) || (Attrib.size() == 0) ) {
            Attribs = DirectoryContext.getAttributes( HostName, new String[]{ "A" } );
            Attrib = Attribs.get( "A" );
            
            if( Attrib == null ) {
                throw new NamingException( "No match for name '" + HostName + "'" );
            }
        }
        
        // we have machines to try. Return them as an array list
        // NOTE: We SHOULD take the preference into account to be absolutely correct.
        List<String> MXHosts = java.util.Collections.checkedList( new ArrayList<String>(), String.class );
        NamingEnumeration<?> NamingEnum = Attrib.getAll();

        while ( NamingEnum.hasMore() ) {
            String MailHost;
            String HostString = ((String)NamingEnum.next());
            String Hosts[] = HostString.split( " " );
            
            //  THE fix *************
            if ( Hosts.length == 1 ) {
                MailHost = Hosts[0];
            }
                
            else if ( Hosts[1].endsWith( "." ) ) {
                MailHost = Hosts[1].substring( 0, (Hosts[1].length() - 1) );
            }
                
            else {
                MailHost = Hosts[1];
            }
            //  THE fix *************
            
            MXHosts.add( MailHost );
        }
        //System.out.println( "getMX():EXIT" );
        return MXHosts;
    }

    /*
     * ...unused code removed...
	*/
    
    /**
     * @param args the command line arguments
     */
    /*
    public static void main(String[] args) {
        System.out.println( "main():ENTER" );
        
        //String testData[] = {"vesso@doc.ic.ac.uk"};
        String testData[] = {"bullshit@doc.ic.ac.uk"};
        //String testData[] = {"bullshit@bullshit.uk"};

        for ( int ctr = 0 ; ctr < testData.length ; ctr++ ) {
            System.out.println( testData[ ctr ] + " is valid? " + isAddressValid( testData[ ctr ] ) );
        }
        System.out.println( "main():EXIT" );
        return;
    }
    */
}
