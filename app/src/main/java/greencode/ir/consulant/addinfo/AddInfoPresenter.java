package greencode.ir.consulant.addinfo;

import java.util.List;

import greencode.ir.consulant.Pojo.POJOModel;
import greencode.ir.consulant.objects.Category;
import greencode.ir.consulant.retrofit.reqobject.AddInfoRequest;
import greencode.ir.consulant.retrofit.reqobject.CatReq;
import greencode.ir.consulant.retrofit.respObject.AdvocancyTypeName;
import greencode.ir.consulant.retrofit.respObject.Bank;
import greencode.ir.consulant.retrofit.respObject.DegreesName;

public class AddInfoPresenter {
    AddInfoInterface myInterface;
    POJOModel model;
    public AddInfoPresenter(AddInfoInterface myInterface) {
        this.myInterface = myInterface;
        this.model = new POJOModel(this);
    }

    public void addInfo(AddInfoRequest request) {
        model.AddInfo(request);
    }

    public void onErrorReady(String str) {
        myInterface.onError(str);
    }

    public void onSuccessFull() {
        myInterface.onSuccess();
    }

    public void getBanks(long timeStamp) {

            model.getBanks(timeStamp);
    }

    public void getCats(CatReq req) {
        model.getCats(req);
    }

    public void getAdvocacyTypes(long timeStamp) {
        model.getAdvocacyType(timeStamp);
    }


    public void getDegreeTypeNames(int timeStamp) {
        model.getDegreeTypeNames(timeStamp);
    }

    public void onReadyCats(List<Category> posts) {
        myInterface.onReadyCats(posts);
    }

    public void onReadyAdvocatedTYPE(List<AdvocancyTypeName> posts) {
        myInterface.onReadyAdvocacyType(posts);
    }
    public void onReadyDegrees(List<DegreesName> posts) {
        myInterface.onReadyDegrees(posts);
    }

    public void onReadyBanks(List<Bank> posts) {
        myInterface.onreadyBanks(posts);
    }
}
