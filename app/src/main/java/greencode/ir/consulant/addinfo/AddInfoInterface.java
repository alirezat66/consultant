package greencode.ir.consulant.addinfo;

import java.util.List;

import greencode.ir.consulant.objects.Category;
import greencode.ir.consulant.retrofit.respObject.AdvocancyTypeName;
import greencode.ir.consulant.retrofit.respObject.Bank;
import greencode.ir.consulant.retrofit.respObject.DegreesName;

public interface AddInfoInterface {

    void onError(String str);

    void onSuccess();

    void onreadyBanks(List<Bank> posts);

    void onReadyCats(List<Category> posts);

    void onReadyAdvocacyType(List<AdvocancyTypeName> posts);

    void onReadyDegrees(List<DegreesName> posts);
}
