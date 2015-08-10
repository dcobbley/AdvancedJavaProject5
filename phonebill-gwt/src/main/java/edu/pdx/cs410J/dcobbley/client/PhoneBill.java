package edu.pdx.cs410J.dcobbley.client;

import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.lang.Override;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PhoneBill extends AbstractPhoneBill
{
  String customer;
  ArrayList<PhoneCall> phoneCalls;
  PhoneCall searchCallOnly;
  PhoneCall singlePhoneCall;

  /**
   * Constructor is essentially a setter function. creates a new list which will hold all additional phonecalls.
   * @param customer Name of the customer
   * @param phoneCall An instance of the phone call which took place.
   */
  public PhoneBill(String customer, PhoneCall phoneCall)
  {
    this.customer = customer;
    phoneCalls = new ArrayList<PhoneCall>();
    addPhoneCall(phoneCall);
    searchCallOnly=null;
    singlePhoneCall = null;
  }

  public PhoneBill(String customer)
  {
    this.customer = customer;
    phoneCalls = new ArrayList<PhoneCall>();
    searchCallOnly=null;
    singlePhoneCall = null;
  }
  public PhoneBill()
  {
    //Create an empty phonebill
    customer = "";
    phoneCalls = new ArrayList<PhoneCall>();
    searchCallOnly=null;
    singlePhoneCall = null;
  }

  public PhoneBill(String customer, PhoneCall tempPhoneCall, String s) {
    if(s.equals("-search")) {
      this.customer = customer;
      phoneCalls = null;
      searchCallOnly = tempPhoneCall;
    }
    if(s.equals("-single")) {
      this.customer = customer;
      phoneCalls = null;
      singlePhoneCall = tempPhoneCall;
    }
  }



  /**
   *
   * @return Returns customer name - Getter function
   */
  @Override
  public String getCustomer() {
    return customer;
  }


  /**
   *
   * @param abstractPhoneCall Takes an instance of the phone call and adds it to the list
   */
  @Override
  public void addPhoneCall(AbstractPhoneCall abstractPhoneCall) {
    boolean addPhoneCall = true;
    for(AbstractPhoneCall call:phoneCalls){
      if(call.toString().equals(abstractPhoneCall.toString())) {
        addPhoneCall = false;
      }
    }
    if(addPhoneCall) {
      phoneCalls.add((PhoneCall)abstractPhoneCall);
    }

    Collections.sort(phoneCalls);


  }

  /**
   *
   * @return Returns a list of all phonecalls made - Getter function
   */
  @Override
  public Collection getPhoneCalls() {
    return phoneCalls;
  }
}
