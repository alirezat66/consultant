package greencode.ir.consulant.offers;

import java.util.List;

import greencode.ir.consulant.retrofit.respObject.CallStateResponse;
import greencode.ir.consulant.retrofit.respObject.Offer;
import greencode.ir.consulant.retrofit.respObject.Request;

public interface OfferInterface {

    void onListReady(List<Offer> posts);

    void onError(String str);
}
