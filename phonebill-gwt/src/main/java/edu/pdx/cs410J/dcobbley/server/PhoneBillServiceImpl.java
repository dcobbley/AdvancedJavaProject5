package edu.pdx.cs410J.dcobbley.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.dcobbley.client.PhoneBill;
import edu.pdx.cs410J.dcobbley.client.PhoneBillService;
import edu.pdx.cs410J.dcobbley.client.PhoneCall;

import java.util.List;
import java.util.Map;

/**
 * Created by David on 8/9/2015.
 */
public class PhoneBillServiceImpl extends RemoteServiceServlet implements PhoneBillService{
    @Override
    public void add() {

    }

    @Override
    public Map<String, PhoneBill> print() {
        return null;
    }

    @Override
    public List<PhoneCall> search() {
        return null;
    }
}
