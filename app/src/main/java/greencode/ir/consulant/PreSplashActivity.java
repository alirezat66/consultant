package greencode.ir.consulant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import greencode.ir.consulant.addinfo.ProfileAddInfoActivity;
import greencode.ir.consulant.addinfo.WaitedPage;
import greencode.ir.consulant.main.DeptorActivity;
import greencode.ir.consulant.main.MainActivity;
import greencode.ir.consulant.signin.SignInActivity;
import greencode.ir.consulant.signin.VerificationActivity;
import greencode.ir.consulant.utils.BaseActivity;
import greencode.ir.consulant.utils.Constants;
import greencode.ir.consulant.utils.PreferencesData;

public class PreSplashActivity extends BaseActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        welcomeScreen();
    }


    private void welcomeScreen() {
       /* PillUsage pillUsage = appDatabase.pillUsageDao().getNearestUsed(System.currentTimeMillis());
        if(pillUsage!=null){
            startAlarmPillReminder(pillUsage);
        }*/



        if(!PreferencesData.getBoolean(Constants.PREF_LOGIN,this)){


            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            finish();
        }else {
            int activate =         PreferencesData.getInt(Constants.Pref_USer_Active,this);
            int state = PreferencesData.getInt(Constants.Pref_User_State,this);
            Intent intent;
            if(activate==2){
                intent = new Intent(this, WaitedPage.class);

            }else if(activate==1) {
                 intent = new Intent(this, ProfileAddInfoActivity.class);

            }else {
                if(state==2 || state==3){
                    intent = new Intent(this, DeptorActivity.class);
                }else {
                    intent = new Intent(this, MainActivity.class);
                }

            }
            startActivity(intent);
            finish();
        }

    }


}
