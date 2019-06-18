package greencode.ir.consulant.signin;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nirigo.mobile.view.passcode.PasscodeView;
import com.onesignal.OneSignal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greencode.ir.consulant.R;
import greencode.ir.consulant.addinfo.ProfileAddInfoActivity;
import greencode.ir.consulant.addinfo.WaitedPage;
import greencode.ir.consulant.main.DeptorActivity;
import greencode.ir.consulant.main.MainActivity;
import greencode.ir.consulant.retrofit.respObject.AuthenticationRes;
import greencode.ir.consulant.utils.BaseActivity;
import greencode.ir.consulant.utils.Constants;
import greencode.ir.consulant.utils.PreferencesData;
import greencode.ir.consulant.utils.Utility;

public class VerificationActivity extends BaseActivity implements SignInInterface {
    @BindView(R.id.txtUser)
    TextView txtUser;
    @BindView(R.id.edtCode)
    TextInputEditText edtCode;
    @BindView(R.id.lyOne)
    LinearLayout lyOne;
    @BindView(R.id.passcode_view)
    PasscodeView passcodeView;
    @BindView(R.id.btnSendAgain)
    Button btnSendAgain;
    @BindView(R.id.remindTime)
    TextView remindTime;
    @BindView(R.id.lyTimer)
    LinearLayout lyTimer;
    @BindView(R.id.btnChangePhone)
    Button btnChangePhone;
    SignInPresenter presenter;
    String phone;
    String code;
    KProgressHUD progress;
    KeyAdapter iosPasscodeAdapter;
    StringBuilder yourCurrentPasscode = new StringBuilder();
    @BindView(R.id.root)
    ConstraintLayout root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            phone = bundle.getString("phone");
            txtUser.setText(phone);
            code = bundle.getString("code");
        }
        presenter = new SignInPresenter(this);
        iosPasscodeAdapter = new KeyAdapter(this);

        passcodeView.setAdapter(iosPasscodeAdapter);
        passcodeView.setOnItemClickListener(new PasscodeView.OnItemClickListener() {
            @Override
            public void onItemClick(PasscodeView passcodeView, int i, View view, final Object o) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (o.toString().equals("<")) {
                            Log.d("checking", "deleted");

                            if (edtCode.length() != 0) {
                                yourCurrentPasscode.deleteCharAt(yourCurrentPasscode.length() - 1);
                                edtCode.setText(yourCurrentPasscode.toString());

                            }
                        } else {
                            Log.d("checking", "new char");
                            yourCurrentPasscode.append(o.toString());
                            edtCode.setText(yourCurrentPasscode.toString());
                            if (yourCurrentPasscode.length() == 4) {
                                if (yourCurrentPasscode.toString().equals(code)) {
                                    PreferencesData.saveBool(Constants.PREF_LOGIN,true,VerificationActivity.this);
                                    FirebaseInstanceId.getInstance().getInstanceId()
                                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                    if (!task.isSuccessful()) {
                                                        int activate =         PreferencesData.getInt(Constants.Pref_USer_Active,VerificationActivity.this);
                                                        int state = PreferencesData.getInt(Constants.Pref_User_State,VerificationActivity.this);
                                                        Intent intent;
                                                        if(activate==1){
                                                            intent = new Intent(VerificationActivity.this, ProfileAddInfoActivity.class);

                                                        }else if(activate==2){
                                                            intent = new Intent(VerificationActivity.this, WaitedPage.class);
                                                        }else {
                                                            if(state==2 || state==3){
                                                                intent = new Intent(VerificationActivity.this, DeptorActivity.class);
                                                            }else {
                                                                intent = new Intent(VerificationActivity.this, MainActivity.class);
                                                            }
                                                        }
                                                        startActivity(intent);

                                                        finish();
                                                        return;
                                                    }else {
                                                        progress = Utility.makeWaiting("در حال همگام سازی با سرور","در حال ارسال اطلاعات ....",VerificationActivity.this);
                                                        progress.show();
                                                        presenter.sendGCMCode(Utility.getToken(),task.getResult().getToken());
                                                        Log.w("hilevel", "getInstanceId failed", task.getException());


                                                    }
                                                    // Get new Instance ID token
                                                    String token = task.getResult().getToken();
                                                    // Log and toast
                                                    String msg = getString(R.string.msg_token_fmt, token);
                                                    Log.d("hilevel", msg);
                                                }
                                            });
                                 //   OneSignal.sendTag("userId",PreferencesData.getInt(Constants.Pref_USER_ID,VerificationActivity.this)+"");

                                } else {
                                    yourCurrentPasscode = new StringBuilder();
                                    edtCode.setText("");
                                }
                            }
                        }
                    }
                });
            }
        });


        startCountDown();

    }

    public void startCountDown() {
        lyTimer.setVisibility(View.VISIBLE);
        btnSendAgain.setVisibility(View.GONE);
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {

                remindTime.setText("" + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                lyTimer.setVisibility(View.GONE);
                btnSendAgain.setVisibility(View.VISIBLE);
            }

        }.start();
    }

    @OnClick({R.id.btnSendAgain, R.id.btnChangePhone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSendAgain:

                progress = Utility.makeWaiting(Constants.WaitingTitle, Constants.WaitingDesc, this);
                progress.show();
                presenter.checkValidationPhone(phone);

                break;
            case R.id.btnChangePhone:
                Intent intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onPhoneInvalid() {
        if (progress.isShowing())
            progress.dismiss();
        Snackbar.make(root,"شماره تماس اشتباه است.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticate(AuthenticationRes res) {
        code = res.getResult().getCode();
        progress.dismiss();
        startCountDown();
    }

    @Override
    public void onAuthenticateFaild(String resp) {
        progress.dismiss();
        Snackbar.make(root,resp, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onGCMOK() {
        progress.dismiss();
        int activate =         PreferencesData.getInt(Constants.Pref_USer_Active,VerificationActivity.this);
        int state = PreferencesData.getInt(Constants.Pref_User_State,VerificationActivity.this);
        Intent intent;
        if(activate==1){
            intent = new Intent(VerificationActivity.this, ProfileAddInfoActivity.class);

        }else if(activate==2){
            intent = new Intent(VerificationActivity.this, WaitedPage.class);
        }else {
            if(state==2 || state==3){
                intent = new Intent(VerificationActivity.this, DeptorActivity.class);
            }else {
                intent = new Intent(VerificationActivity.this, MainActivity.class);
            }
        }
        startActivity(intent);

        finish();
    }
}
