package greencode.ir.consulant.calling;

import greencode.ir.consulant.Pojo.POJOModel;
import greencode.ir.consulant.retrofit.reqobject.CallUpdateReq;

public class CallPresenter {
    POJOModel model;
    public CallPresenter() {
        model = new POJOModel(this);
    }

    public void updateCall(CallUpdateReq req) {
        model.updateCall(req);
    }
}
