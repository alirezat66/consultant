package greencode.ir.consulant.addinfo;

import greencode.ir.consulant.objects.ProfileInfo;

public interface WaitingInterface {
    void onSuccess(ProfileInfo profile);

    void onError(String message);
}
