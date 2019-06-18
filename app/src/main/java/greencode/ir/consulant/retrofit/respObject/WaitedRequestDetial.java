package greencode.ir.consulant.retrofit.respObject;

import java.util.List;

import greencode.ir.consulant.objects.DocumentImg;
import greencode.ir.consulant.objects.DocumentOfReq;
import greencode.ir.consulant.objects.Lawyer;


public class WaitedRequestDetial {
    int requestId;
    String context;
    List<DocumentOfReq> documents;


    public int getRequestId() {
        return requestId;
    }

    public String getContext() {
        return context;
    }

    public List<DocumentOfReq> getDocuments() {
        return documents;
    }
}
