package greencode.ir.consulant.offers;

import java.util.List;

import greencode.ir.consulant.Pojo.POJOModel;
import greencode.ir.consulant.requests.RequestInterface;
import greencode.ir.consulant.retrofit.respObject.Offer;
import greencode.ir.consulant.retrofit.respObject.Request;

public class OfferPresenter {
    OfferInterface myInterface;
    POJOModel model;
    public OfferPresenter(OfferInterface myInterface) {
        this.myInterface = myInterface;
        this.model = new POJOModel(this);
    }

    public void getOffers(String token) {
        model.getOffers(token);
    }

    public void onErrorReady(String str) {
        myInterface.onError(str);
    }

    public void onListReady(List<Offer> posts) {
        myInterface.onListReady(posts);
    }
}
