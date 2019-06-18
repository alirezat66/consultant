package greencode.ir.consulant.retrofit.reqobject;

import greencode.ir.consulant.utils.Utility;

public class CancelOfferReq {
    int offerId;
    int adviserId;
    String token;

    public CancelOfferReq(int offerId) {
        this.offerId = offerId;
        this.adviserId = Utility.getId();
        this.token = Utility.getToken();
    }

    public int getOfferId() {
        return offerId;
    }

    public int getAdviserId() {
        return adviserId;
    }

    public String getToken() {
        return token;
    }
}
