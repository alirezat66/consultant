package greencode.ir.consulant.requests;

import java.util.List;

import greencode.ir.consulant.retrofit.respObject.Request;

public interface RequestInterface {

    void onListReady(List<Request> posts);

    void onError(String str);
}
