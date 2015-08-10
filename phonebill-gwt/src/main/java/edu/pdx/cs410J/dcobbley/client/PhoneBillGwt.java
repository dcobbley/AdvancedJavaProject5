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

      //CreatePhoneBill
      buttonCreatePhoneBill.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent clickEvent) {

          }
      });

      //Add a phoneCall
      buttonAddCall.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent clickEvent) {

          }
      });
      //pretty Print
      buttonPrettyPrintAllCalls.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent clickEvent) {

          }
      });
      //Search for user
      buttonSearch.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent clickEvent) {

          }
      });



  }
}
