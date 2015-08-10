package edu.pdx.cs410J.dcobbley.client;

import com.google.gwt.user.client.rpc.RemoteService;

import java.util.List;
import java.util.Map;

/**
 * Created by David on 8/9/2015.
 */
public interface PhoneBillService extends RemoteService {
    public void add();

    public Map<String,PhoneBill> print();

    public List<PhoneCall> search();

}
