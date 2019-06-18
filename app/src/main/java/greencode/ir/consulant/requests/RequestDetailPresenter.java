package greencode.ir.consulant.requests;

import android.content.Intent;

import greencode.ir.consulant.Pojo.POJOModel;
import greencode.ir.consulant.retrofit.reqobject.AcceptReq;
import greencode.ir.consulant.retrofit.reqobject.GetWaitedDetailsReq;
import greencode.ir.consulant.retrofit.respObject.WaitedRequestDetial;

public class RequestDetailPresenter {
    RequestDetailInterface myInterface;
    POJOModel model;
    public RequestDetailPresenter(RequestDetailInterface myInterface) {
        this.myInterface = myInterface;
        this.model = new POJOModel(this);
    }

    public void getDetail(GetWaitedDetailsReq req) {
        this.model.getWaitedDetails(req);
    }

    public void onError(String str) {
        myInterface.onError(str);
    }

    public void onGetDateil(WaitedRequestDetial detail) {
        myInterface.onDetailReady(detail);

    }

    public void acceptReq(AcceptReq req) {
        model.acceptReq(req);
    }

    public void onAcceptRequest() {
        myInterface.onAccept();
    }
}
