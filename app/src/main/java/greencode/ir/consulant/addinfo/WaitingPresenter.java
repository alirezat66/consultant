package greencode.ir.consulant.addinfo;

import greencode.ir.consulant.Pojo.POJOModel;
import greencode.ir.consulant.objects.ProfileInfo;
import greencode.ir.consulant.retrofit.reqobject.ProfileInfoReq;

public class WaitingPresenter {
    WaitingInterface myInterface;
    POJOModel model;
    public WaitingPresenter(WaitingInterface myInterface) {
        this.myInterface = myInterface;
        this.model = new POJOModel(this);
    }

    public void getUserInfo(ProfileInfoReq profileInfoReq) {
        model.getUserInfo(profileInfoReq);
    }

    public void onErrorReady(String message) {
        myInterface.onError(message);
    }

    public void onSuccessProfile(ProfileInfo profile) {
        myInterface.onSuccess(profile);
    }
}
