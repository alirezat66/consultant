package greencode.ir.consulant.addinfo;

import java.util.List;

import greencode.ir.consulant.Pojo.POJOModel;
import greencode.ir.consulant.retrofit.respObject.Bank;

public class PresenterStepOne {
    InterfaceStepOne myInterface;
    POJOModel model;
    public PresenterStepOne(InterfaceStepOne myInterface) {
        this.myInterface = myInterface;
        this.model = new POJOModel(this);
    }



}
