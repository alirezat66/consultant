package greencode.ir.consulant.signin;

import greencode.ir.consulant.Pojo.POJOModel;
import greencode.ir.consulant.retrofit.reqobject.AuthReq;
import greencode.ir.consulant.retrofit.respObject.AuthenticationRes;

public class SignInPresenter {
    SignInInterface myInterface;
    POJOModel model;
    public SignInPresenter(SignInInterface myInterface) {
        this.myInterface = myInterface;
        model = new POJOModel(this);
    }
    public void checkValidationPhone(String phone){
        if(phone.length()>0){
            if(phone.length()==11){
                model.callAuthentication(new AuthReq(phone));
            }else {
                myInterface.onPhoneInvalid();
            }
        }else {
            myInterface.onPhoneInvalid();
        }
    }

    public void onErrorReady(String str) {
        myInterface.onAuthenticateFaild(str);
    }

    public void onRespReady(AuthenticationRes res) {
        myInterface.onAuthenticate(res);
    }

    public void sendGCMCode(String token, String gcmToken) {
        model.sendGCM(token,gcmToken);
    }

    public void onGCMOK() {
        myInterface.onGCMOK();
    }

}
