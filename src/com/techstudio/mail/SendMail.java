
package com.techstudio.mail;
import java.io.*;
import java.util.Properties;
import java.util.Date;
import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.*;
import com.techstudio.smpp.util.*;
import org.apache.log4j.*;

/**
 * Demo app that shows how to construct and send a single part html
 * message.  Note that the same basic technique can be used to send
 * data of any type.
 *
 * @author John Mani
 * @author Bill Shannon
 * @author Max Spivak
 */

public class SendMail {
	Logger logger = ConnLogger.getLogger();

	public static void main(String arg[]){
		String c = "i m c";
		String to = "roberto.haryanto@techstudio.com";
		String from = "roberto.haryanto@techstudio.com";
		String host = "130.34.13.9";
		boolean debug = Boolean.valueOf("true").booleanValue();
		String msgText1 = c;
		String subject = "Testing";

		SendMail shtml = new SendMail();
		shtml.sendhtmlmail(to,from,host,"",msgText1 ,subject);
	}
	
	public SendMail(){}
	public void sendhtmlmail(String pto,String pfrom,String phost,String pfilename,String pmsgtext,String psubject) {
		
		String  to, subject = null, from = null, 
			cc = null, bcc = null, url = null;
		String mailhost = null;
		String mailer = "sendhtml";
		String protocol = null, host = null, user = null, password = null;
		String record = null;	// name of folder in which to record mail
		boolean debug = false;
		BufferedReader in =
			new BufferedReader(new InputStreamReader(System.in));
		int optind;
		
		try {
			to = pto;
			subject = psubject;
			from = pfrom;
			mailhost = phost;

			Properties props = System.getProperties();
			// XXX - could use Session.getTransport() and Transport.connect()
			// XXX - assume we're using SMTP
			if (mailhost != null)
				props.put("mail.smtp.host", mailhost);
			
			// Get a Session object
			Session session = Session.getDefaultInstance(props, null);
			if (debug)
				session.setDebug(true);
			
			// construct the message
			Message msg = new MimeMessage(session);
			if (from != null)
				msg.setFrom(new InternetAddress(from));
			else
				msg.setFrom();
			
			msg.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to, false));
			if (cc != null)
				msg.setRecipients(Message.RecipientType.CC,
					InternetAddress.parse(cc, false));
			if (bcc != null)
				msg.setRecipients(Message.RecipientType.BCC,
					InternetAddress.parse(bcc, false));
			
			msg.setSubject(subject);
			
			collect(in, msg,pmsgtext,pfilename);
			
			msg.setHeader("X-Mailer", mailer);
			msg.setSentDate(new Date());
			
			// send the thing off
			Transport.send(msg);
			
			logger.info("\nMail was sent successfully.");
			
			// Keep a copy, if requested.
			
			if (record != null) {
				// Get a Store object
				Store store = null;
				if (url != null) {
					URLName urln = new URLName(url);
					store = session.getStore(urln);
					store.connect();
				} else {
					if (protocol != null)		
						store = session.getStore(protocol);
					else
						store = session.getStore();
					
					// Connect
					if (host != null || user != null || password != null)
						store.connect(host, user, password);
					else
						store.connect();
				}
				
				// Get record Folder.  Create if it does not exist.
				Folder folder = store.getFolder(record);
				if (folder == null) {
					System.err.println("Can't get record folder.");
					System.exit(1);
				}
				if (!folder.exists())
					folder.create(Folder.HOLDS_MESSAGES);
				
				Message[] msgs = new Message[1];
				msgs[0] = msg;
				folder.appendMessages(msgs);
				
				logger.info("Mail was recorded successfully.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void collect(BufferedReader in, Message msg,String msgtext, String filename)
		throws MessagingException, IOException {
		String line;
		String file = "http://203.117.101.201/mmap10/images/"+filename;
		String bkgrd = "http://203.117.101.201/mmap10/images/bkgrd"+filename;
		String subject = msg.getSubject();
		StringBuffer sb = new StringBuffer();
		sb.append("<HTML>\n");
		sb.append("<HEAD>\n");
		sb.append("<meta content=\"text/html; charset=big5\" http-equiv=Content-Type>\n");
		sb.append("<TITLE>\n");
		//sb.append(msgtext + "\n");
		sb.append("</TITLE>\n");
		sb.append("</HEAD>\n");
		
		sb.append("<body bgcolor=\"#ffffff\">");
		sb.append("<p align=left>"+msgtext+"</p>");
		sb.append("</body>");
		sb.append("</HTML>\n");
		
		msg.setDataHandler(new DataHandler(
			new ByteArrayDataSource(sb.toString(), "text/html")));
	}
	
	public void sendtextmail(String pto,String pfrom,String phost,String pfilename,String pmsgtext,String psubject) {
		
		String  to, subject = null, from = null, 
			cc = null, bcc = null, url = null;
		String mailhost = null;
		String mailer = "sendtext";
		String protocol = null, host = null, user = null, password = null;
		String record = null;	// name of folder in which to record mail
		boolean debug = false;
		BufferedReader in =
			new BufferedReader(new InputStreamReader(System.in));
		int optind;

		try {
			to = pto;
			subject = psubject;
			from = pfrom;
			mailhost = phost;

			Properties props = System.getProperties();
			// XXX - could use Session.getTransport() and Transport.connect()
			// XXX - assume we're using SMTP
			if (mailhost != null)
				props.put("mail.smtp.host", mailhost);
			
			// Get a Session object
			Session session = Session.getDefaultInstance(props, null);
			if (debug)
				session.setDebug(true);
			
			// construct the message
			Message msg = new MimeMessage(session);
			if (from != null)
				msg.setFrom(new InternetAddress(from));
			else
				msg.setFrom();
			
			msg.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to, false));
			if (cc != null)
				msg.setRecipients(Message.RecipientType.CC,
					InternetAddress.parse(cc, false));
			if (bcc != null)
				msg.setRecipients(Message.RecipientType.BCC,
					InternetAddress.parse(bcc, false));
			
			msg.setSubject(subject);
			
			test(in, msg,pmsgtext,pfilename);
			
			msg.setHeader("X-Mailer", mailer);
			msg.setSentDate(new Date());
			
			// send the thing off
			Transport.send(msg);
			
			logger.info("\nMail was sent successfully.");
			
			// Keep a copy, if requested.
			
			if (record != null) {
				// Get a Store object
				Store store = null;
				if (url != null) {
					URLName urln = new URLName(url);
					store = session.getStore(urln);
					store.connect();
				} else {
					if (protocol != null)		
						store = session.getStore(protocol);
					else
						store = session.getStore();
					
					// Connect
					if (host != null || user != null || password != null)
						store.connect(host, user, password);
					else
						store.connect();
				}
				
				// Get record Folder.  Create if it does not exist.
				Folder folder = store.getFolder(record);
				if (folder == null) {
					System.err.println("Can't get record folder.");
					System.exit(1);
				}
				if (!folder.exists())
					folder.create(Folder.HOLDS_MESSAGES);
				
				Message[] msgs = new Message[1];
				msgs[0] = msg;
				folder.appendMessages(msgs);
				
				logger.info("Mail was recorded successfully.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		public void test(BufferedReader in, Message msg,String msgtext, String filename)
		throws MessagingException, IOException {
		String line;
		String subject = msg.getSubject();
		StringBuffer sb = new StringBuffer();
		sb.append(msgtext);
		msg.setDataHandler(new DataHandler(
			new ByteArrayDataSource(sb.toString(), "text/html")));
	}	
}
