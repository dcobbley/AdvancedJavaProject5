package edu.pdx.cs410J.dcobbley.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {

  public void onModuleLoad() {

      //Setup the webpage and stuff

      //Define all the buttons here ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      //Button button = new Button("");

      Button buttonPingServer = new Button("Ping Server");
      Button buttonCreatePhoneBill = new Button("Create Phonebill");
      Button buttonAddCall = new Button("Add Phone Call");
      Button buttonPrettyPrintAllCalls = new Button("Display All Calls");
      Button buttonSearch = new Button("Search");
      Button buttonHelp = new Button("Help");

      //Text box~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      final TextArea textBoxResults = new TextArea();
      textBoxResults.setReadOnly(true);
      textBoxResults.setSize("200px","200px");

      //Vertical Panels~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      VerticalPanel panelHeader = new VerticalPanel();
      panelHeader.add(new Label());
      panelHeader.add(textBoxResults);

      //Adding a customer
      VerticalPanel panelAddNewCustomer = new VerticalPanel();
      panelAddNewCustomer.add(new Label("Customer Name:"));
      final TextBox textBoxCustomerName = new TextBox();
      panelAddNewCustomer.add(textBoxCustomerName);

      panelAddNewCustomer.add(new Label("Caller Number:"));
      final TextBox textBoxCallerNumber = new TextBox();
      panelAddNewCustomer.add(textBoxCallerNumber);

      panelAddNewCustomer.add(new Label("Callee Number:"));
      final TextBox textBoxCalleeNumber = new TextBox();
      panelAddNewCustomer.add(textBoxCalleeNumber);

      panelAddNewCustomer.add(new Label("Start Time:"));
      final TextBox textBoxStartTime = new TextBox();
      panelAddNewCustomer.add(textBoxStartTime);

      panelAddNewCustomer.add(new Label("End Time:"));
      final TextBox textBoxEndTime = new TextBox();
      panelAddNewCustomer.add(textBoxEndTime);



      //add all the buttons to the root panel ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      RootPanel rootPanel = RootPanel.get();

      //rootPanel.add....
      rootPanel.add(buttonAddCall);
      rootPanel.add(buttonCreatePhoneBill);
      rootPanel.add(buttonPrettyPrintAllCalls);
      rootPanel.add(buttonSearch);
      rootPanel.add(buttonPingServer);
      rootPanel.add(buttonHelp);
      rootPanel.add(panelAddNewCustomer,300,150);


      //add Vertical Panels to the root panel~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      rootPanel.add(panelHeader, 50, 100);

      //Define all the addClickHandlers here~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      //Ping
      buttonPingServer.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent clickEvent) {
              PingServiceAsync async = GWT.create(PingService.class);
              async.ping(new AsyncCallback<AbstractPhoneBill>() {

                  public void onFailure(Throwable ex) {
                      Window.alert(ex.toString());
                  }

                  public void onSuccess(AbstractPhoneBill phonebill) {
                      StringBuilder sb = new StringBuilder(phonebill.toString());
                      Collection<AbstractPhoneCall> calls = phonebill.getPhoneCalls();
                      for (AbstractPhoneCall call : calls) {
                          sb.append(call);
                          sb.append("\n");
                      }
                      Window.alert(sb.toString());
                  }
              });
          }
      });

      //Help Readme
      buttonHelp.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent clickEvent) {
              Window.alert("This is the README!");
          }
      });

      //CreatePhoneBill
      buttonCreatePhoneBill.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent clickEvent) {
              try {
                  final PhoneCall tempCall = new PhoneCall(textBoxCallerNumber.getValue(), textBoxCalleeNumber.getValue(), textBoxStartTime.getValue(), textBoxEndTime.getValue());
                  PhoneBillServiceAsync async = GWT.create(PhoneBillService.class);
                  async.add(textBoxCustomerName.getValue(),tempCall , new AsyncCallback<Void>() {
                      @Override
                      public void onFailure(Throwable throwable) {
                          Window.alert(throwable.getMessage());
                      }

                      @Override
                      public void onSuccess(Void aVoid) {
                          textBoxResults.setValue(textBoxCustomerName.getValue()+" has added a new phone call: "+tempCall.toString() );
                      }
                  });
              }
              catch(Exception ex){
                  //Not enough args or something
                  Window.alert("Please provide correct arguments"+ex.getMessage());
                  return;
              }

          }
      });

      //Add a phoneCall
      buttonAddCall.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent clickEvent) {
              try {
                  final PhoneCall tempCall = new PhoneCall(textBoxCallerNumber.getValue(), textBoxCalleeNumber.getValue(), textBoxStartTime.getValue(), textBoxEndTime.getValue());
                  PhoneBillServiceAsync async = GWT.create(PhoneBillService.class);
                  async.add(textBoxCustomerName.getValue(),tempCall , new AsyncCallback<Void>() {
                      @Override
                      public void onFailure(Throwable throwable) {
                          Window.alert(throwable.getMessage());
                      }

                      @Override
                      public void onSuccess(Void aVoid) {
                          textBoxResults.setValue(textBoxCustomerName.getValue()+" has added a new phone call: "+tempCall.toString() );
                      }
                  });
              }
              catch(Exception ex){
                  //Not enough args or something
                  Window.alert("Please provide correct arguments"+ex.getMessage());
                  return;
              }

          }
      });
      //pretty Print
      buttonPrettyPrintAllCalls.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent clickEvent) {
              PhoneBillServiceAsync async = GWT.create(PhoneBillService.class);
              async.print(new AsyncCallback<Map<String, PhoneBill>>() {

                  @Override
                  public void onFailure(Throwable throwable) {
                      Window.alert(throwable.getMessage());
                  }

                  @Override
                  public void onSuccess(Map<String, PhoneBill> stringPhoneBillMap) {
                      String prettyCalls="";
                      //pretty print it
                      if(stringPhoneBillMap==null||stringPhoneBillMap.isEmpty()){
                          Window.alert("No phonebills to show");
                          return;
                      }
                      for(String customer: stringPhoneBillMap.keySet()){
                          Collection calls = stringPhoneBillMap.get(customer).getPhoneCalls();
                          prettyCalls += customer+": "+calls.toString()+"\n";
                      }
                      textBoxResults.setValue(prettyCalls);
                  }
              });
          }
      });
      //Search for user
      buttonSearch.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent clickEvent) {
              PhoneBillServiceAsync async = GWT.create(PhoneBillService.class);
              async.search(textBoxCustomerName.getValue(), textBoxStartTime.getValue(), new AsyncCallback<List<PhoneCall>>() {
                  @Override
                  public void onFailure(Throwable throwable) {
                      Window.alert("error "+throwable.getMessage());
                  }

                  @Override
                  public void onSuccess(List<PhoneCall> phoneCalls) {
                      String prettyCalls="";
                      if(phoneCalls==null||phoneCalls.isEmpty()){
                          Window.alert("No phonecalls matching under "+textBoxCustomerName.getValue());
                          return;
                      }
                      //pretty print the search calls
                      for(PhoneCall call: phoneCalls){
                          prettyCalls += call.toString()+"\n";
                      }
                      textBoxResults.setValue(prettyCalls);
                  }
              });

          }
      });



  }
}
