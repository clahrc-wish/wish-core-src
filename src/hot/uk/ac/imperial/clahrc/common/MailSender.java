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


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;

import uk.ac.imperial.clahrc.entity.Users;
import uk.ac.imperial.clahrc.utilities.RandomStringGenerator;
import uk.ac.imperial.clahrc.utilities.loaders.Distribution;
import uk.ac.imperial.clahrc.utilities.loaders.MailAttachments;
import uk.ac.imperial.clahrc.utilities.loaders.MailConfigs;
import uk.ac.imperial.clahrc.utilities.loaders.MailTemplates;

@Name("mailSender")
@Scope(ScopeType.EVENT)
public class MailSender implements java.io.Serializable {
	
	@Logger private Log log;
	@In Credentials credentials;
	@In EntityManager entityManager;
    @In FacesContext facesContext;
    @In MailConfigs mailConfigs;
    @In MailTemplates mailTemplates;
    @In MailAttachments mailAttachments;
    @In Distribution distribution;

	private static final long serialVersionUID = 1L;
	
    public String sendNewAccount( String userName, String password, String firstName, 
    		            		  String lastName, String email )
    {
        log.info("mailSender.sendNewAccount() action called for: #0", userName + " at: " + email );
        String returnResult = null;
        try {
            Session emailSession = this.getEmailSession();
    	    MimeMessage emailMsg = this.getEmailMsg( emailSession, email, 
    	    		                                 distribution.getProperty("siteName") + " Notification (AUTOMATICALLY GENERATED - PLEASE DO NOT REPLY)" );

    	    MimeBodyPart rextBodyPart = new MimeBodyPart();
    	    rextBodyPart.setText( this.getNewAccountText( userName, firstName, lastName ) );
    	    List<File> emailAttachments = mailAttachments.getAttachments();

    	    Multipart multiPart = new MimeMultipart();
    	    multiPart.addBodyPart( rextBodyPart );
    	    for ( File emailAttachment : emailAttachments ) {
    	    	MimeBodyPart mimeBodyPart = new MimeBodyPart();
    	    	mimeBodyPart.attachFile( emailAttachment );
    	    	multiPart.addBodyPart( mimeBodyPart );
    	    }

    	    emailMsg.setContent( multiPart );
    	    emailMsg.setSentDate( new Date() );
    	    emailMsg.saveChanges();

    	    Transport.send( emailMsg );
    	    returnResult = "sent";
    	    log.info( "email sent to: #0", userName + " at: " + email );
    	    
    	} catch ( MessagingException mex ) {
    	    log.error( "MessagingException thrown while sending mail - Email Not Sent!", mex );
    	} catch ( IOException ioex ) {
    	    log.error( "IOException thrown while sending mail - Email Not Sent!", ioex );
    	} catch ( Throwable twbl ) {
     	    log.error( "Throwable thrown while sending mail - Email Not Sent!", twbl );
     	}
    	
    	return returnResult;
    }// end sendNewAccount()

    public String sendNewRole( String userName, String firstName, String lastName, String email,  
    						   String projectShortName,String projectDescription, String roleName )
	{
		log.info("mailSender.sendNewRole() action called for: #0", userName + " at: " + email );
		String returnResult = null;
		try {
		Session emailSession = this.getEmailSession();
		MimeMessage emailMsg = this.getEmailMsg( emailSession, email, 
				                                 distribution.getProperty("siteName") + " Notification (AUTOMATICALLY GENERATED - PLEASE DO NOT REPLY)" );
		
		MimeBodyPart textBodyPart = new MimeBodyPart();
		textBodyPart.setText( this.getNewRoleText( firstName, lastName, projectShortName, projectDescription, roleName ) );
		
		Multipart multiPart = new MimeMultipart();
		multiPart.addBodyPart( textBodyPart );
		
		emailMsg.setContent( multiPart );
		emailMsg.setSentDate( new Date() );
		emailMsg.saveChanges();
		
		Transport.send( emailMsg );
		returnResult = "sent";
		log.info( "email sent to: #0", userName + " at: " + email );
		
		} catch ( MessagingException mex ) {
			log.error( "MessagingException thrown while sending mail - Email Not Sent!", mex );
		} catch ( Throwable twbl ) {
			log.error( "Throwable thrown while sending mail - Email Not Sent!", twbl );
		}
		
		return returnResult;
	}// end sendNewRole()

    public void sendForgotten() {

    	log.info("mailSender.sendForgotten() action called for: #0", credentials.getUsername() );
    	
        try {
        	Users user = (Users)entityManager.createQuery( "SELECT u FROM Users u WHERE u.loginName = :loginname" )
                                             .setParameter( "loginname", credentials.getUsername() )
                                             .getSingleResult();
        	
            String email = user.getEmail();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            
            if ( (email == null) || email.isEmpty() ) {
            	log.warn("email record not found for: #0", credentials.getUsername());
				facesContext.addMessage( null, FacesMessages.createFacesMessage(FacesMessage.SEVERITY_WARN, 
										 " No eamil found for Username !", new Object[]{}) );
            }
            
            Session emailSession = this.getEmailSession();
    	    MimeMessage emailMsg = this.getEmailMsg( emailSession, email, 
    	    		                                 distribution.getProperty("siteName") + " Notification (AUTOMATICALLY GENERATED - PLEASE DO NOT REPLY)" );
    	    
            String password = (new RandomStringGenerator()).generate();

    	    MimeBodyPart textBodyPart = new MimeBodyPart();
    	    textBodyPart.setText( this.getForgottenText( password, firstName, lastName ) );

    	    Multipart multiPart = new MimeMultipart();
    	    multiPart.addBodyPart( textBodyPart );

    	    emailMsg.setContent( multiPart );
    	    emailMsg.setSentDate( new Date() );
    	    emailMsg.saveChanges();

    	    Transport.send( emailMsg );

    	    // replace old password in DB
    	    user.setPassword( password );
    	    entityManager.persist( user );

            log.info( "Password re-set and emil sent to: #0", credentials.getUsername() + " at: " + email );
            facesContext.addMessage( null, FacesMessages.createFacesMessage(FacesMessage.SEVERITY_INFO, 
									" Temporary password has been emailed ! Use it to login and then change it to something memorable !", new Object[]{}) );
    	    
    	} catch ( NoResultException eex ) {
    	    log.warn( "EntityNotFoundException thrown while sending mail - Email Not Sent!", eex.toString() );
    	    facesContext.addMessage( null, FacesMessages.createFacesMessage(FacesMessage.SEVERITY_WARN, 
									" Invalid Username !", new Object[]{}) );
    	    
    	} catch ( Throwable twbl ) {
     	    log.error( "Exception thrown while sending mail - Email Not Sent!", twbl );
            facesContext.addMessage( null, FacesMessages.createFacesMessage(FacesMessage.SEVERITY_ERROR, 
			 						 " Password Re-set Failed !", new Object[]{}) );
     	}
    }// end sendForgotten()
    
    public String sendUpdatedAccount( String userName, String password, String firstName, String lastName, String email )
	{
		log.info("mailSender.sendUpdatedAccount() action called for: #0", userName + " at: " + email );
		String returnResult = null;
		try {
			Session emailSession = this.getEmailSession();
			MimeMessage emailMsg = this.getEmailMsg( emailSession, email, 
					                                 "Updated Details on " + distribution.getProperty("siteName") + " Tool" );
			
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText( this.getUpdatedAccountText( password, firstName, lastName ) );
			
			Multipart multiPart = new MimeMultipart();
			multiPart.addBodyPart( textBodyPart );
			
			emailMsg.setContent( multiPart );
			emailMsg.setSentDate( new Date() );
			emailMsg.saveChanges();
			
			Transport.send( emailMsg );
			returnResult = "sent";
			log.info( "Account Updated email sent to: #0", userName + " at: " + email );
			
		} catch ( MessagingException mex ) {
			log.error( "MessagingException thrown while sending mail - Email Not Sent!", mex );
			facesContext.addMessage( null, FacesMessages.createFacesMessage(FacesMessage.SEVERITY_WARN, 
					 "Account updated email Not sent", new Object[]{}) );
		} catch ( Throwable twbl ) {
			log.error( "Throwable thrown while sending mail - Email Not Sent!", twbl );
			facesContext.addMessage( null, FacesMessages.createFacesMessage(FacesMessage.SEVERITY_WARN, 
					 "Account updated email Not sent", new Object[]{}) );
		}
		
		return returnResult;
	}// end sendUpdatedAccount()
    
    public String sendSelfUpdatedAccount( String userName, String firstName, String lastName, String email )
	{
		log.info("mailSender.sendSelfUpdatedAccount() action called for: #0", userName + " at: " + email );
		String returnResult = null;
		try {
			Session emailSession = this.getEmailSession();
			MimeMessage emailMsg = this.getEmailMsg( emailSession, email, 
					                                 "Updated Details on " + distribution.getProperty("siteName") + " Tool" );
			
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText( this.getSelfUpdatedAccountText( firstName, lastName ) );
			
			Multipart multiPart = new MimeMultipart();
			multiPart.addBodyPart( textBodyPart );
			
			emailMsg.setContent( multiPart );
			emailMsg.setSentDate( new Date() );
			emailMsg.saveChanges();
			
			Transport.send( emailMsg );
			returnResult = "sent";
			log.info( "Account Updated email sent to: #0", userName + " at: " + email );
			
		} catch ( MessagingException mex ) {
			log.error( "MessagingException thrown while sending mail - Email Not Sent!", mex );
			facesContext.addMessage( null, FacesMessages.createFacesMessage(FacesMessage.SEVERITY_WARN, 
					 "Account updated email Not sent", new Object[]{}) );
		} catch ( Throwable twbl ) {
			log.error( "Throwable thrown while sending mail - Email Not Sent!", twbl );
			facesContext.addMessage( null, FacesMessages.createFacesMessage(FacesMessage.SEVERITY_WARN, 
					 "Account updated email Not sent", new Object[]{}) );
		}
		
		return returnResult;
	}// end sendUpdatedAccount()
    
    private Session getEmailSession() {
    	
    	javax.mail.Authenticator smtpAuthenticator = new javax.mail.Authenticator() {
    		
            protected PasswordAuthentication getPasswordAuthentication() {
            	return new PasswordAuthentication( mailConfigs.getCredential( "name" ), mailConfigs.getCredential( "pass" ) );
            }
         };
    	
         return ( Session.getInstance( mailConfigs.getProperties(), smtpAuthenticator ) );
    }
    
    private MimeMessage getEmailMsg( Session emailSession, String emailAddress, String subjectLine ) throws AddressException, MessagingException {
    	
    	MimeMessage emailMsg = new MimeMessage( emailSession );
	    emailMsg.setFrom( new InternetAddress( mailConfigs.getCredential( "mail" ), true ) );
	    InternetAddress[] address = { new InternetAddress( emailAddress, true ) };
	    emailMsg.setRecipients( Message.RecipientType.TO, address );
	    emailMsg.setSubject( subjectLine );

	    return emailMsg;
    }
    
	private String getNewAccountText( String userName, String firstName,  String lastName ) {
	
		String emailText = mailTemplates.getTemplate( "welcome_template" );
		emailText = emailText.replace( "<firstName>", firstName );
		emailText = emailText.replace( "<lastName>", lastName );
		emailText = emailText.replace( "<userName>", userName );
		emailText = emailText.replace( "<siteName>", distribution.getProperty( "siteName" ) );
		emailText = emailText.replace( "<siteUrl>", SiteUrl.BASE_PATH );
		
		return emailText;
	}
	
	private String getNewRoleText( String firstName, String lastName, String projectShortName, 
							       String projectDescription, String roleName ) {
		
		String emailText = mailTemplates.getTemplate( "new_role_template" );
		emailText = emailText.replace( "<firstName>", firstName );
		emailText = emailText.replace( "<lastName>", lastName );
		emailText = emailText.replace( "<projectDescription>", projectDescription );
		emailText = emailText.replace( "<projectShortName>", projectShortName );
		emailText = emailText.replace( "<roleName>", roleName );
		
		return emailText;
	}

	private String getForgottenText( String password, String firstName, String lastName ) {
		
		String emailText = mailTemplates.getTemplate( "forgotten_password_template" );
		emailText = emailText.replace( "<firstName>", firstName );
		emailText = emailText.replace( "<lastName>", lastName );
		emailText = emailText.replace( "<password>", password );
		emailText = emailText.replace( "<siteName>", distribution.getProperty( "siteName" ) );
		emailText = emailText.replace( "<siteAdminEmail>", distribution.getProperty( "siteAdminEmail" ) );

		return emailText;
	} 
	
	private String getUpdatedAccountText( String password, String firstName, String lastName  ) {

		String emailText = mailTemplates.getTemplate( "edit_account_template" );
		emailText = emailText.replace( "<firstName>", firstName );
		emailText = emailText.replace( "<lastName>", lastName );
		emailText = emailText.replace( "<password>", password );
		emailText = emailText.replace( "<siteName>", distribution.getProperty( "siteName" ) );
		
		return emailText;
	}
	
	private String getSelfUpdatedAccountText( String firstName, String lastName  ) {

		String emailText = mailTemplates.getTemplate( "self_edit_account_template" );
		emailText = emailText.replace( "<firstName>", firstName );
		emailText = emailText.replace( "<lastName>", lastName );
		emailText = emailText.replace( "<siteName>", distribution.getProperty( "siteName" ) );
		
		return emailText;
	}
}
