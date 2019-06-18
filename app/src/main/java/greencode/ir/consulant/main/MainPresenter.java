package greencode.ir.consulant.main;

import java.util.List;

import greencode.ir.consulant.Pojo.POJOModel;
import greencode.ir.consulant.objects.ProfileInfo;
import greencode.ir.consulant.retrofit.reqobject.ProfileInfoReq;
import greencode.ir.consulant.retrofit.respObject.Offer;


public class MainPresenter {
    MainInterfacde myInterface;
    POJOModel model  ;
    public MainPresenter( MainInterfacde myInterface) {
        this.myInterface  = myInterface;
        model = new POJOModel(this);

    }


    public void getActiveOffers(String token) {
        model.getActiveOffers(token);
    }

    public void onListReady(List<Offer> offers) {
        myInterface.onReadyOfferList(offers);
    }

    public void onErrorReady(String str) {
        myInterface.onError(str);
    }

    public void getUserInfo(ProfileInfoReq profileInfoReq) {
        model.getUserInfo(profileInfoReq);
    }

    public void onSuccessProfile(ProfileInfo profile) {
        myInterface.onSuccessProfile(profile);
    }

    public void onSuccessLogout() {
        myInterface.onSuccessLogOut();
    }

    public void logout(String token) {
        model.signOut(token);
    }
}
