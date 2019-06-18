package greencode.ir.consulant.requests;

import java.util.List;

import greencode.ir.consulant.Pojo.POJOModel;
import greencode.ir.consulant.retrofit.respObject.Request;

public class RequestPresenter {
    RequestInterface myInterface;
    POJOModel model;
    public RequestPresenter(RequestInterface myInterface) {
        this.myInterface = myInterface;
        this.model = new POJOModel(this);
    }

    public void getRequests(String token) {
        model.getRequests(token);
    }

    public void onErrorReady(String str) {
        myInterface.onError(str);
    }

    public void onListReady(List<Request> posts) {
        myInterface.onListReady(posts);
    }
}
