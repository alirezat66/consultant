package greencode.ir.consulant.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;

import greencode.ir.consulant.PreSplashActivity;
import greencode.ir.consulant.R;
import greencode.ir.consulant.retrofit.CallerService;
import greencode.ir.consulant.retrofit.MyMethods;
import greencode.ir.consulant.retrofit.ServerListener;
import greencode.ir.consulant.retrofit.respObject.UserStateInfo;
import greencode.ir.consulant.utils.BaseActivity;
import greencode.ir.consulant.utils.Constants;
import greencode.ir.consulant.utils.PreferencesData;
import greencode.ir.consulant.utils.Utility;

public class DeptorActivity extends BaseActivity implements ServerListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debptor_page);

    }

    @Override
    protected void onResume() {
        super.onResume();
        CallerService.getState(this, Utility.getToken());

    }

    @Override
    public void onFailure(int i, String str) {

    }

    @Override
    public void onSuccess(int i, JsonObject jsonObject) throws JSONException {
        if(i == MyMethods.UserState.getMethodValue()){
            Gson gson = new Gson();
            JsonObject obj = jsonObject.get("result").getAsJsonObject();
            UserStateInfo info = gson.fromJson(obj,UserStateInfo.class);
            if(info.getState()==1){
                PreferencesData.saveInt(Constants.Pref_USer_Active,info.getActivate_state(),this);
                PreferencesData.saveInt(Constants.Pref_User_State,info.getState(),this);
                Intent intent = new Intent(this,PreSplashActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
