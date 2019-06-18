package greencode.ir.consulant.main;

import java.util.List;

import greencode.ir.consulant.objects.ProfileInfo;
import greencode.ir.consulant.retrofit.respObject.Offer;

public interface MainInterfacde {
    public void onReadyOfferList(List<Offer> adviceList);

    void onError(String str);

    void onSuccessProfile(ProfileInfo profile);

    void onSuccessLogOut();
}
