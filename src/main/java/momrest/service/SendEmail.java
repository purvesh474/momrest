package momrest.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import momrest.model.Meetings;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.parameter.CuType;
import net.fortuna.ical4j.model.parameter.PartStat;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.parameter.Rsvp;
import net.fortuna.ical4j.model.parameter.TzId;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.util.UidGenerator;

public class SendEmail {

	//PP added String place, String note,String owner
		public static void calenderInvite(Meetings orgMeeting)
		{	  System.out.println("start calenderInvite: "+orgMeeting);
		  try
		  {
			  String[] stDate = orgMeeting.getStartdate().split("-|T|:|T| ");  //by PP added "|T| "  -space
			  String[] eDate = orgMeeting.getEnddate().split("-|T|:|T| "); //by PP added "|T| "   -space
			  String[] participate = orgMeeting.getParticipants().split(",");
			  //String[] path = req.getServletContext().getRealPath("/").split("webapp/");
			  			 // String realPath = path[0]+"resources/static/";
			  System.out.println(1);
			  System.out.println("user.home:"+System.getProperty("user.home"));
			  System.out.println("user.URL:"+System.getProperty("user.URL"));
			  System.out.println("user.dir:"+System.getProperty("user.dir"));
			 // String[] path = System.getProperty("user.dir").split("webapp/");
			  //String realPath = path[0]+"/WEB-INF/classes/";
			  String realPath = System.getProperty("user.dir")+"/WEB-INF/classes/";
			 // String realPath="E:\\MOM\\MOMRest\\momrest\\";
			  String calFile = "mycalendar.ics";
			  System.out.println(2);
			
				// Create a TimeZone
				  TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
				  TimeZone timezone = registry.getTimeZone("Asia/Kolkata");
				  VTimeZone tz = timezone.getVTimeZone();
				  for(int i=0;i<stDate.length;i++) {
					  System.out.println("start data array :"+i+"--->"+stDate[i]); 
				  }
				  System.out.println("start data arra ::: ");
				  java.util.Calendar startDate = new GregorianCalendar();
				  startDate.setTimeZone(timezone);
				  startDate.set(java.util.Calendar.MONTH, Integer.parseInt(stDate[1]) - 1);
				  startDate.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(stDate[2]));
				  startDate.set(java.util.Calendar.YEAR, Integer.parseInt(stDate[0]));
				  startDate.set(java.util.Calendar.HOUR_OF_DAY, Integer.parseInt(stDate[3]));
				  startDate.set(java.util.Calendar.MINUTE, Integer.parseInt(stDate[4]));
				  startDate.set(java.util.Calendar.SECOND, 00);	
				  
				  java.util.Calendar endDate = new GregorianCalendar();
				  endDate.setTimeZone(timezone);
				  endDate.set(java.util.Calendar.MONTH, Integer.parseInt(eDate[1]) - 1);
				  endDate.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(eDate[2]));
				  endDate.set(java.util.Calendar.YEAR, Integer.parseInt(eDate[0]));
				  endDate.set(java.util.Calendar.HOUR_OF_DAY, Integer.parseInt(eDate[3]));
				  endDate.set(java.util.Calendar.MINUTE, Integer.parseInt(eDate[4]));	
				  endDate.set(java.util.Calendar.SECOND, 00);

				  // Create the event
				  String eventName = orgMeeting.getSubject();
				  DateTime start = new DateTime(startDate.getTime());
				  DateTime end = new DateTime(endDate.getTime());
				  VEvent meeting = new VEvent(start, end, eventName);
				  
				  //PP added below to add Location and Description and Organizer 
				  meeting.getProperties().add(new Location(orgMeeting.getPlace()));
				  meeting.getProperties().add(new Description(orgMeeting.getNote()));
				  meeting.getProperties().add(new Organizer(orgMeeting.getOwner()));
				  
				  for(int i=0;i<participate.length;i++) {
					  Attendee dev1 = new Attendee(URI.create("mailto:"+participate[i]));
					  dev1.getParameters().add(Role.REQ_PARTICIPANT);
					  dev1.getParameters().add(Rsvp.TRUE);				  
					  dev1.getParameters().add(PartStat.NEEDS_ACTION);
					  dev1.getParameters().add(CuType.INDIVIDUAL);
					  meeting.getProperties().add(dev1);
					 
				  }
				  // add timezone info..
				  meeting.getProperties().add(tz.getTimeZoneId());
				  
				  TzId tzParam=new TzId(tz.getProperties().getProperty(Property.TZID).getValue());
				  
				  meeting.getProperties().getProperty(Property.DTSTART).getParameters().add(tzParam);
				  
				  // generate unique identifier..
				  UidGenerator ug = new UidGenerator("uidGen");
				  Uid uid = ug.generateUid();
				  meeting.getProperties().add(uid);

				  

				  // Create a calendar
				  net.fortuna.ical4j.model.Calendar icsCalendar = new net.fortuna.ical4j.model.Calendar();
				  icsCalendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
				  icsCalendar.getProperties().add(CalScale.GREGORIAN);


				  // Add the event and print
				  icsCalendar.getComponents().add(meeting);
				  
				  //Saving an iCalendar file
				  FileOutputStream fout = new FileOutputStream(realPath+calFile);
				  System.out.println(orgMeeting.toString());
				  System.out.println("details---- "+icsCalendar);
				  CalendarOutputter outputter = new CalendarOutputter();
				  outputter.setValidating(false);
				  outputter.output(icsCalendar, fout);
				  
				  //Now Parsing an iCalendar file
				  FileInputStream fin = new FileInputStream(realPath+calFile);
			
				  CalendarBuilder builder = new CalendarBuilder();
			
				  icsCalendar = builder.build(fin);
				  
				  //Iterating over a Calendar
				  for (Iterator i = icsCalendar.getComponents().iterator(); i.hasNext();) {
				      Component component = (Component) i.next();
				      System.out.println("Component [" + component.getName() + "]");
			
				      for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
				          Property property = (Property) j.next();
				          System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
				      }
				  }//for
				  
			  }
			  catch(Exception e)
			  {
				  System.out.println("Exception ::: " + e.getMessage());
			  }
		}
		
		
		public static void sendMail(String toMailId, String subject)
		{
			try
			{
				System.out.println("start sendMail");
			   //String[] path = req.getServletContext().getRealPath("/").split("webapp/");
			   //String realPath = path[0]+"WEB-INF/classes/static/";
			   //String[] path = System.getProperty("user.URL").split("webapp/");
			   String realPath = System.getProperty("user.dir")+"/WEB-INF/classes/";
			   //String realPath = path[0]+"WEB-INF/classes/";
			   String calFile = "mycalendar.ics";
			   String[] toMail = toMailId.split(",");
			   
			   Properties props = new Properties();
			   props.put("mail.smtp.auth", "true");
			   props.put("mail.smtp.starttls.enable", "true");
			   props.put("mail.smtp.host", "smtp.gmail.com");
			   props.put("mail.smtp.port", "587");
			   
			   Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			      protected PasswordAuthentication getPasswordAuthentication() {
			         return new PasswordAuthentication("hardik.kava007@gmail.com", "9824404380");
			      }
			   });
			   
			   Message msg = new MimeMessage(session);
			   msg.setFrom(new InternetAddress("hardik.kava007@gmail.com", false));
			   
			   for (int i = 0; i < toMail.length; i++)
		       {
				   msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail[i]));
		       }
			   msg.setSubject(subject);
			   msg.setContent("", "text/html");
			   msg.setSentDate(new Date());

			   MimeBodyPart messageBodyPart = new MimeBodyPart();
			   messageBodyPart.setContent("", "text/html");

			   Multipart multipart = new MimeMultipart();
			   multipart.addBodyPart(messageBodyPart);
			   MimeBodyPart attachPart = new MimeBodyPart();

			   attachPart.attachFile(realPath+calFile);
			   multipart.addBodyPart(attachPart);
			   msg.setContent(multipart);
			   Transport.send(msg);
			}
			catch(Exception e)
			{
				System.out.println("Exception During Sendmail ::: "+e);
			}
		}
		
	
	
}
