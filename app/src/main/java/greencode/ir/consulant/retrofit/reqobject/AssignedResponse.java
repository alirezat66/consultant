package greencode.ir.consulant.retrofit.reqobject;

import java.util.List;

import greencode.ir.consulant.objects.DocumentOfReq;

public class AssignedResponse {
    int requestId;
    int price;
    long timeOfAddoffer;
    long timeOfAssigned;
    long timeOfCall;
    int callId;
    int normalUserId;


    public int getCallId() {
        return callId;
    }

    public int getNormalUserId() {
        return normalUserId;
    }

    public long getTimeOfServer() {
        return timeOfServer;
    }

    long timeOfServer;
    public long getTimeOfCall() {
        return timeOfCall;
    }

    String context;
    List<DocumentOfReq> documents;

    public int getRequestId() {
        return requestId;
    }

    public int getPrice() {
        return price;
    }

    public long getTimeOfAddoffer() {
        return timeOfAddoffer;
    }

    public long getTimeOfAssigned() {
        return timeOfAssigned;
    }

    public String getContext() {
        return context;
    }

    public List<DocumentOfReq> getDocuments() {
        return documents;
    }
}
