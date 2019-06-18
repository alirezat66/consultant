package greencode.ir.consulant.dialog;

import java.util.List;

import greencode.ir.consulant.objects.MultiChoseObject;
import greencode.ir.consulant.objects.SingleChoseObject;

/**
 * Created by alireza on 5/18/18.
 */

public interface MultiChoserInterface {
    public void onSuccess(List<MultiChoseObject> chosenObjects);
    public void onRejected();
}
