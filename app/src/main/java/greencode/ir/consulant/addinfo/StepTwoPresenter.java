package greencode.ir.consulant.addinfo;

import java.util.List;

import greencode.ir.consulant.Pojo.POJOModel;
import greencode.ir.consulant.objects.Category;
import greencode.ir.consulant.retrofit.reqobject.CatReq;
import greencode.ir.consulant.retrofit.respObject.AdvocancyTypeName;
import greencode.ir.consulant.retrofit.respObject.DegreesName;

public class StepTwoPresenter {
    StepTwoInterface myInterface;
    POJOModel model;
    public StepTwoPresenter(StepTwoInterface myInterface) {
        this.myInterface = myInterface;
        model = new POJOModel(this);
    }

    public void onErrorReady(String message) {
        myInterface.onError(message);
    }



}
