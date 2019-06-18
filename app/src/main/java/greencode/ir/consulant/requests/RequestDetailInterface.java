package greencode.ir.consulant.requests;

import greencode.ir.consulant.retrofit.respObject.WaitedRequestDetial;

public interface RequestDetailInterface {
    void onDetailReady(WaitedRequestDetial detail);

    void onError(String str);

    void onAccept();
}
