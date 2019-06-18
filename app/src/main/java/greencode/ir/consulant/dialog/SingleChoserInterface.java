package greencode.ir.consulant.dialog;

import greencode.ir.consulant.objects.SingleChoseObject;

/**
 * Created by alireza on 5/18/18.
 */

public interface SingleChoserInterface {
    public void onSuccess(SingleChoseObject selectedObject);
    public void onRejected();
}
