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
package uk.ac.imperial.clahrc.common;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.util.Base64;

import javax.validation.constraints.Size;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.httpclient.*;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.*;

import uk.ac.imperial.clahrc.utilities.loaders.BugzillaConfigs;
import uk.ac.imperial.clahrc.utilities.loaders.Distribution;

@Name("bugzillaProxy")
public class BugzillaProxy implements Serializable
{
	
	@Logger private Log log;
    @In Credentials credentials;
    @In Distribution distribution;
    @In BugzillaConfigs bugzillaConfigs;

	private static final long serialVersionUID = 1L;
    private String toolComponent;
    private String summary;
    private String description;
    private List<SelectItem> components = Collections.checkedList( new ArrayList<SelectItem>(), SelectItem.class );
    {
    	components.add( new SelectItem( null, "Click to select..." ) );
    	components.add( new SelectItem( "Data Entry UI", "Entering data" ) );
    	components.add( new SelectItem( "Reporting", "Viewing a report" ) );
    	components.add( new SelectItem( "Data Entry UI", "Other" ) );
    }
    private byte[] imageAttachment;
    private String imageAttachmentContentType;
   

    public void submitBug() {

        log.info("bugzillaProxy.submitBug() action called with: #{bugzillaProxy.summary}");
    	
        XmlRpcClient XMLRPCClient = null;
    	try {
    		HttpClient HTTPClient = new HttpClient();
    		XMLRPCClient = new XmlRpcClient();
    		XmlRpcCommonsTransportFactory XMLRPCFactory = new XmlRpcCommonsTransportFactory( XMLRPCClient );
    		XmlRpcClientConfigImpl XMLRPCClientConfig = new XmlRpcClientConfigImpl();
    		
    		XMLRPCFactory.setHttpClient( HTTPClient );
    		XMLRPCClient.setTransportFactory( XMLRPCFactory );
    		XMLRPCClientConfig.setServerURL( new URL( bugzillaConfigs.getProperty( "wsUrl" ) ) );
    		XMLRPCClient.setConfig( XMLRPCClientConfig );
    		
			Map<String, Number> LoginResult = this.logIn( XMLRPCClient );

    		if ( LoginResult != null ) {
    			Map<String, Number> SubmitResult = this.submitBug( XMLRPCClient );
        		
    			if ( (SubmitResult != null) && 
    				 (imageAttachment != null) && (imageAttachment.length > 0) && 
   	    			 (imageAttachmentContentType != null) && (!imageAttachmentContentType.isEmpty()) ) {
    				
   	    			// a screen-print file was attach - send it along with the submitted bug
    				@SuppressWarnings("unused")
   	    			Map<String, Object> SubmitAttachmentResult = this.submitAttachment( XMLRPCClient, SubmitResult.get("id").intValue() );
   	    		}
    		}
    	} catch ( Throwable twbl ) {
     	    log.error( "Throwable thrown while processing a problem report", twbl );
     	   
     	} finally {
     		if (XMLRPCClient != null) {
     			try {
					XMLRPCClient.execute( "User.logout", new Object[]{} );
				} catch (XmlRpcException e) {
					log.warn( "Logging out of Bugzilla error: ", e );
				}
     		}
     	}
    }

    //
    @SuppressWarnings("unchecked")
    private Map<String, Number> logIn( XmlRpcClient XMLRPCClient ) {
    	
    	Map<String, Number> loginResult = null;
    	try {
    		Map<String, Object> LoginMap = Collections.checkedMap( new HashMap<String, Object>(), String.class, Object.class );
    		LoginMap.put( "login", bugzillaConfigs.getProperty( "login" ) );
    		LoginMap.put( "password", bugzillaConfigs.getProperty( "pass" ) );
    		
    		loginResult = ((Map<String, Number>)XMLRPCClient.execute( "User.login", new Object[]{ LoginMap } ));
    		log.info( "bugzillaProxy.submitBug() action logged to Bugzilla with id: " + loginResult.get("id") );
    		
    	} catch ( Throwable twbl ) {
    		log.error( "Throwable thrown while logging to Bugzilla - Problem Report Will Not Be Sent!", twbl );
    	}
    	return loginResult;
    }
    
    @SuppressWarnings("unchecked")
    private Map<String, Number> submitBug( XmlRpcClient XMLRPCClient ) {
    	
    	Map<String, Number> submitBugResult = null;
    	Map<String, String> BugMap = Collections.checkedMap( new HashMap<String, String>(), String.class, String.class );
    	try {
    		BugMap.put( "product", "CLAHRC Web Tool" );
    		BugMap.put( "component", (distribution.getProperty( "site" ) + ": " + this.toolComponent) );
    		BugMap.put( "version", "2.0.0" );
    		BugMap.put( "severity", "normal" );
    		BugMap.put( "platform", "All" );
    		BugMap.put( "op_sys", "All" );
    		BugMap.put( "priority", "Normal" );
    		BugMap.put( "status", "NEW" );
    		BugMap.put( "summary", this.summary );
    		BugMap.put( "description", this.appendReporter( this.description ) );
    		
    		submitBugResult = ((Map<String, Number>)XMLRPCClient.execute( "Bug.create", new Object[]{ BugMap } ));
    		log.info( "bugzillaProxy.submitBug() action submitted a bug with id: " + submitBugResult.get("id") );
    		
    	} catch ( Throwable twbl ) {
    		log.error( "Throwable thrown while submitting a bug - Problem Report Not Sent!", twbl );
    		log.error( "Attempted to report: " + Arrays.toString( BugMap.values().toArray() ) );
    	}
    	return submitBugResult;
    }
    
    @SuppressWarnings("unchecked")
    private Map<String, Object> submitAttachment( XmlRpcClient XMLRPCClient, int BugId ) {
    	
    	Map<String, Object> submitAttachmentResult = null;
    	Map<String, Object> AttachmentMap = Collections.checkedMap( new HashMap<String, Object>(), String.class, Object.class );
    	try {
    		AttachmentMap.put( "ids", new String[]{ String.valueOf(BugId) } );
			AttachmentMap.put( "data", DatatypeConverter.parseBase64Binary(Base64.encodeBytes( this.imageAttachment, 0, this.imageAttachment.length )) );
			AttachmentMap.put( "file_name", "fileAttachmentForBug_" + BugId );
			AttachmentMap.put( "summary", "fileAttachment for - " + this.summary );
			AttachmentMap.put( "content_type", this.imageAttachmentContentType );
			
			submitAttachmentResult = ((Map<String, Object>)XMLRPCClient.execute( "Bug.add_attachment", new Object[]{ AttachmentMap } ));
			log.info("bugzillaProxy.submitBug() action submitted a bugAttachment with id: " + ((Map<String, Object>)((Map<String, Object>)submitAttachmentResult).get("attachments")).keySet().toArray()[0] );
			
    	} catch ( Throwable twbl ) {
    		log.error( "Throwable thrown while submitting a bug attachment - Problem Report Was Sent!", twbl );
    		log.error( "Attempted to report: " + Arrays.toString( AttachmentMap.values().toArray() ) );
    	}
    	return submitAttachmentResult;
    }
    
    private String appendReporter( String Description ) {
    	
    	StringBuffer SignedDescription = new StringBuffer();
    	SignedDescription.append( Description );
    	SignedDescription.append( "\n\n==[auto added]==========================\n" );
    	SignedDescription.append( "reporter login: " + credentials.getUsername() );
    	SignedDescription.append( "\n========================================" );
    	return SignedDescription.toString();
    }

  
    public String getToolComponent() {
        return toolComponent;
    }
  
    public void setToolComponent(String toolComponent) {
        this.toolComponent = toolComponent;
    }
    
    @Size(max = 100)
    public String getSummary() {
        return summary;
    }
  
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    @Size(max = 1000)
    public String getDescription() {
        return description;
    }
  
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<SelectItem> getComponents() {
    	if ( Boolean.parseBoolean( bugzillaConfigs.getProperty( "testComponent" ) ) ) {
    		components.add( new SelectItem( "BugTrashCan", "BugTrashCan - Dead-end sink for testing" ) );
    	}
        return components;
    }

	public byte[] getImageAttachment() {
		return imageAttachment;
	}

	public void setImageAttachment(byte[] imageAttachment) {
		this.imageAttachment = imageAttachment;
	}

	public String getImageAttachmentContentType() {
		return imageAttachmentContentType;
	}

	public void setImageAttachmentContentType(String imageAttachmentContentType) {
		this.imageAttachmentContentType = imageAttachmentContentType;
	}
}
