package greencode.ir.consulant.offers;

import greencode.ir.consulant.Pojo.POJOModel;
import greencode.ir.consulant.retrofit.reqobject.AssignedResponse;
import greencode.ir.consulant.retrofit.reqobject.CancelOfferReq;
import greencode.ir.consulant.retrofit.reqobject.DoneAdviceDetailReq;
import greencode.ir.consulant.retrofit.respObject.AdviceDetail;
import greencode.ir.consulant.retrofit.respObject.CallStateResponse;

public class AdvicePresenter {
    AdviceInterface myInterface;

    POJOModel model;
    public AdvicePresenter(AdviceInterface myInterface) {
        this.myInterface = myInterface;
        model = new POJOModel(this);
    }
    public void getCallState(String token,String offerId){
        model.getCallState(token,offerId);
    }

    public void getReadyToAnswerRequestDetial(DoneAdviceDetailReq req) {
        model.getReadyToAnswerRequestDetial(req);
    }

    public void onError(String str) {
        myInterface.onError(str);
    }

    public void onGetDateil(AdviceDetail detail) {
        myInterface.onDetailReady(detail);
    }


    public void cancelOffer(CancelOfferReq req) {
        model.cancelOffer(req);
    }

    public void onCancell() {
        myInterface.onCanceled();
    }

    public void getAssignedRequestDetail(DoneAdviceDetailReq req) {
        model.getAssignedRequestDetail(req);
    }
    public void getCanceledRequestBeforeAssignedDetial(DoneAdviceDetailReq req) {
        model.getCanceledRequestBeforeAssignedDetial(req);
    }
    public void onreadyCallRes(CallStateResponse res) {
        myInterface.onSuccessCallState(res);
    }
    public void onGetDateil(AssignedResponse detail) {
        myInterface.onDetailReady(detail);
    }
}
