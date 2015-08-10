package edu.pdx.cs410J.dcobbley.server;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.dcobbley.client.PhoneBill;
import edu.pdx.cs410J.dcobbley.client.PhoneBillService;
import edu.pdx.cs410J.dcobbley.client.PhoneCall;

import java.text.ParseException;
import java.util.*;

/**
 * Created by David on 8/9/2015.
 */
public class PhoneBillServiceImpl extends RemoteServiceServlet implements PhoneBillService{

    private final Map<String, PhoneBill> data = new HashMap<String, PhoneBill>();
    PhoneBill bill;

    @Override
    public void add(String customer, PhoneCall call) {
        if(data.get(customer)!=null){
            bill=data.get(customer);
            bill.addPhoneCall(call);
            data.put(customer, bill);
        }
        else{
            bill= new PhoneBill(customer,call);
            data.put(customer, bill);
        }
    }

    @Override
    public Map<String, PhoneBill> print() {
        return data;
    }

    @Override
    public List<PhoneCall> search(String customer, String startTime) {
        DefaultDateTimeFormatInfo formatInfo = new DefaultDateTimeFormatInfo();
        DateTimeFormat ShortDateFormat = new DateTimeFormat("MM/dd/yyy hh:mm a",formatInfo){};
        Long begin;
            try {
            Date search = ShortDateFormat.parse(startTime);
            begin = search.getTime();
        }
        catch(Exception ex){
            System.out.println("Error Parsing "+ ex);
            return null;
        }

        List<PhoneCall> data = new ArrayList<PhoneCall>();
        if(data.isEmpty())
            return null;

        bill = this.data.get(customer);
        if(bill == null)
            return null;
        Collection<PhoneCall>calls = this.data.get(customer).getPhoneCalls();

        for(PhoneCall call: calls){
            if(begin>= (Long)call.startTime.getTime()&&begin<=(Long)call.endTime.getTime()){
                data.add(call);
            }
        }
        return data;
    }
}
