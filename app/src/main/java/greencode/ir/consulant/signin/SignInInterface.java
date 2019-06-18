package greencode.ir.consulant.signin;

import greencode.ir.consulant.retrofit.respObject.AuthenticationRes;

public interface SignInInterface {
    void onPhoneInvalid();
    void onAuthenticate(AuthenticationRes res);
    void onAuthenticateFaild(String resp);

    void onGCMOK();
}
