package greencode.ir.consulant.retrofit.respObject;

import java.util.List;

import greencode.ir.consulant.objects.DocumentOfReq;

public class AdviceDetail {
    int requestId;
    int price;
    long timeOfAddoffer;
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

    public String getContext() {
        return context;
    }

    public List<DocumentOfReq> getDocs() {
        return documents;
    }
}
