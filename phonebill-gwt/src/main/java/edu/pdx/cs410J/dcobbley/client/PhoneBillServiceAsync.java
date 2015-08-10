package edu.pdx.cs410J.dcobbley.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;
import java.util.Map;

/**
 * Created by David on 8/9/2015.
 */
public interface PhoneBillServiceAsync {
    public void add(String customer ,PhoneCall call,AsyncCallback<Void> async);

    void print(AsyncCallback<Map<String, PhoneBill>> async);

    void search(String customer, String startTime,AsyncCallback<List<PhoneCall>>async);
}
