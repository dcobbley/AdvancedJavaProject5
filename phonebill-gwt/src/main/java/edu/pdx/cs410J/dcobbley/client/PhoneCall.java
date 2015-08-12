package edu.pdx.cs410J.dcobbley.client;


import com.google.gwt.user.client.Window;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.lang.Override;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.i18n.client.DateTimeFormat;

public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall>
{
  String callerNumber;
  String calleeNumber;
  public Date startTime;
  public Date endTime;
  private boolean flag;

  /**
   * Constructor for the phonecall class. Holds all relavent data for a particular phonecall
   * @param callerNumber The phone number of the customer
   * @param calleeNumber The phone number that the customer is trying to reach
   * @param startTime The time at which the phonecall began
   * @param endTime The time at which the phonecall ended
   */
  public PhoneCall(String callerNumber, String calleeNumber, String startTime, String endTime){
    flag = false;
    //Check for bad data
    try{
      if(startTime.contains("\"")||endTime.contains("\""))
        throw new IllegalArgumentException("Date and time cannot contain quotes ");

      if(!callerNumber.matches("\\d{3}-\\d{3}-\\d{4}$")||!calleeNumber.matches("\\d{3}-\\d{3}-\\d{4}$"))
        throw new IllegalArgumentException("Valid phone numbers must contain exactly 10 numbers plus two dashes");

      String[] tempStart = startTime.split(" ");
      String[] tempEnd= endTime.split(" ");
      if(!tempStart[0].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)")||!tempEnd[0].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)")) {
        throw new IllegalArgumentException("Date format must follow mm/dd/yyyy");
      }

      if(!tempStart[1].matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")||!tempEnd[1].matches("([01]?[0-9]|2[0-3]):[0-5][0-9]"))
        throw new IllegalArgumentException("Time format must follow mm:hh (12 hour time)");
      if(!tempStart[2].matches("(am|pm|AM|PM)")&&!tempEnd[2].matches("(am|pm|AM|PM)"))
        throw new IllegalArgumentException("Time must include am/pm");
    }
    catch(IllegalArgumentException ex) {
      Window.alert(ex.getMessage());
      flag = true;
      return;
    }
    this.callerNumber = callerNumber;
    this.calleeNumber = calleeNumber;
    setDate(startTime,endTime);
  }
  public PhoneCall(){
    flag = false;
    try {
      calleeNumber = "";
      callerNumber = "";
      startTime =null;
      endTime = null;
    }
    catch(Exception ex){
      Window.alert(ex.getMessage());
      flag = true;
      return;
    }
  }


  public void setDate(String start, String end){
    flag = false;
    DefaultDateTimeFormatInfo formatInfo = new DefaultDateTimeFormatInfo();
    DateTimeFormat ShortDateFormat = new DateTimeFormat("MM/dd/yyy hh:mm a",formatInfo){};
    try {
      this.startTime=ShortDateFormat.parse(start);
      this.endTime = ShortDateFormat.parse(end);
    }
    catch(Exception ex){
      Window.alert("Error Parsing the time, please enter valid time, dont forget to include am/pm " + ex.getMessage());
      flag = true;
      return;
    }
  }

  public boolean exceptionWasThrown(){
    return flag;
  }

  /**
   *
   * @return Returns callerNumber - Getter function
   */
  @Override
  public String getCaller() {
    return callerNumber;
  }

  /**
   *
   * @return Returns calleeNumber - Getter function
   */
  @Override
  public String getCallee() {
    return calleeNumber;
  }

  /**
   *
   * @return Returns startTime - Getter function
   */
  @Override
  public String getStartTimeString() {
    DefaultDateTimeFormatInfo formatInfo = new DefaultDateTimeFormatInfo();
    DateTimeFormat ShortDateFormat = new DateTimeFormat("MM/dd/yyy hh:mm a",formatInfo){};
    if(startTime != null)
      return (ShortDateFormat.format(startTime));
    else
      return "";
  }

  /**
   *
   * @return Returns endTime - Getter function
   */
  @Override
  public String getEndTimeString() {
    DefaultDateTimeFormatInfo formatInfo = new DefaultDateTimeFormatInfo();
    DateTimeFormat ShortDateFormat = new DateTimeFormat("MM/dd/yyy hh:mm a",formatInfo){};
    if(endTime != null)
      return (ShortDateFormat.format(endTime));
    else
      return "";
  }

  public String duration(){
    long duration =startTime.getTime()-endTime.getTime();
    long diffMinutes = duration / (60 * 1000) % 60;
    long diffHours = duration / (60 * 60 * 1000);
    if(diffHours ==0)
      return -1*diffMinutes + " minutes";
    else
      return "  "+-1*diffHours+":"+ -1*diffMinutes +"min";
  }

  public Date compareSetData(String end){
    String[] endArray=end.split("[ /:]");
    Date mine = new Date(Integer.parseInt(endArray[2])-1900, (Integer.parseInt(endArray[0]))-1, Integer.parseInt(endArray[1]),Integer.parseInt(endArray[3]),Integer.parseInt(endArray[4]),0);
    return mine;
  }


  @Override
  public int compareTo(PhoneCall o) {
    try {
      if (this.startTime == null) {
        throw new NullPointerException("No start time to compare");
      }
      if (o.startTime == null) {
        throw new NullPointerException("No end time to compare");
      }
      long diff = this.startTime.getTime()-o.startTime.getTime();

      if (diff > 0) {
        return 1;
      }
      if (diff < 0) {
        return -1;
      }
      if (diff == 0) {
        //equal - differ by caller number
        //String numberDiff = this.callerNumber-o.callerNumber;
        String callerStringA = this.getCaller();
        String callerStringB = o.getCaller();
        callerStringA = callerStringA.replaceAll("\\D", "");
        callerStringB = callerStringB.replaceAll("\\D", "");
        long numberDiff = Long.parseLong(callerStringA)-Long.parseLong(callerStringB);

        if(numberDiff >0){
          return 1;
        }
        if(numberDiff<0){
          return -1;
        }
        if(numberDiff == 0){
          return 0;
        }
      }
    }
    catch(Exception ex){
      Window.alert(ex.getMessage());
      return 0;
    }
    return 0;  }
}
