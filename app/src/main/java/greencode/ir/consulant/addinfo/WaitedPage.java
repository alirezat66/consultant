package greencode.ir.consulant.addinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import greencode.ir.consulant.R;
import greencode.ir.consulant.controler.AppDatabase;
import greencode.ir.consulant.main.MainActivity;
import greencode.ir.consulant.objects.ProfileInfo;
import greencode.ir.consulant.retrofit.reqobject.ProfileInfoReq;
import greencode.ir.consulant.utils.BaseActivity;
import greencode.ir.consulant.utils.Constants;
import greencode.ir.consulant.utils.PreferencesData;
import greencode.ir.consulant.utils.Utility;

public class WaitedPage extends BaseActivity implements WaitingInterface{
    KProgressHUD progressHUD;
    WaitingPresenter presenter ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_accept);

    }

    @Override
    public void onSuccess(ProfileInfo profile) {
        progressHUD.dismiss();
        AppDatabase database = AppDatabase.getInMemoryDatabase(this);
        database.profileDao().insert(profile);

        if(profile.getAct()==3){
            PreferencesData.saveInt(Constants.Pref_USer_Active,3,this);
            Intent intent  = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onError(String message) {
        progressHUD.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter = new WaitingPresenter(this);
        progressHUD = Utility.makeWaiting(this);
        progressHUD.show();
        presenter.getUserInfo(new ProfileInfoReq(Utility.getToken(),Utility.getId()));

    }
}
