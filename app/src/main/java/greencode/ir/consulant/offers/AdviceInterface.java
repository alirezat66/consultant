package greencode.ir.consulant.offers;

import greencode.ir.consulant.retrofit.reqobject.AssignedResponse;
import greencode.ir.consulant.retrofit.respObject.AdviceDetail;
import greencode.ir.consulant.retrofit.respObject.CallStateResponse;

public interface AdviceInterface {
    void onDetailReady(AdviceDetail detail);
    void onError(String message);

    void onCanceled();
    void onSuccessCallState(CallStateResponse res);

    void onDetailReady(AssignedResponse detail);
}
