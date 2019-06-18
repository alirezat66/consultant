package greencode.ir.consulant.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.sinch.android.rtc.SinchError;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greencode.ir.consulant.PreSplashActivity;
import greencode.ir.consulant.R;
import greencode.ir.consulant.controler.AppDatabase;
import greencode.ir.consulant.dialog.QuestionDialog;
import greencode.ir.consulant.dialog.QuestionListener;
import greencode.ir.consulant.objects.MenuItem;
import greencode.ir.consulant.objects.ProfileInfo;
import greencode.ir.consulant.offers.AdviceViewActivity;
import greencode.ir.consulant.offers.OfferActivity;
import greencode.ir.consulant.requests.RequestActivity;
import greencode.ir.consulant.retrofit.reqobject.ProfileInfoReq;
import greencode.ir.consulant.retrofit.respObject.Offer;
import greencode.ir.consulant.services.SinchService;
import greencode.ir.consulant.signin.SignInActivity;
import greencode.ir.consulant.utils.BaseActivity;
import greencode.ir.consulant.utils.Constants;
import greencode.ir.consulant.utils.PreferencesData;
import greencode.ir.consulant.utils.Utility;


public class MainActivity extends BaseActivity implements MainInterfacde, OfferAdaoter.onItemClick , SinchService.StartFailedListener{

    QuestionDialog permissionDialog ;
    @BindView(R.id.emptyList)
    LinearLayout emptyList;
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.txtsizeCount)
    TextView txtsizeCount;
    @BindView(R.id.layoutCount)
    LinearLayout layoutCount;
    @BindView(R.id.rlList)
    RelativeLayout rlList;
    @BindView(R.id.advice_recycler)
    RecyclerView adviceRecycler;
    MainPresenter presenter;
    @BindView(R.id.btn_insert_req)
    Button btnInsertReq;
    @BindView(R.id.img_menu)
    LinearLayout imgMenu;
    @BindView(R.id.img_comment)
    LinearLayout imgComment;
    KProgressHUD progress;
    ProfileInfo profile;
    String[] mPermission = {
            Manifest.permission.RECORD_AUDIO
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.d("response","token="+Utility.getToken());



    }
    private void loginTosinch(){
        if(profile!=null) {
            String userName = "lawyer" + profile.getId();
            if (!userName.equals(getSinchServiceInterface().getUserName())) {
                getSinchServiceInterface().stopClient();
                getSinchServiceInterface().startClient(userName);

            }

            if (!getSinchServiceInterface().isStarted()) {
                getSinchServiceInterface().startClient(userName);

            }
        }
    }
    private void makeList() {
        if(progress!=null) {
            if (!progress.isShowing()) {
                if (profile != null) {
                    presenter.getUserInfo(new ProfileInfoReq(PreferencesData.getString(Constants.PREF_TOKEN, this), PreferencesData.getInt(Constants.Pref_USER_ID, this)));

                }
                progress = Utility.makeWaiting(Constants.WaitingTitle, Constants.WaitingDescNormal, this);
                progress.show();
                String token = PreferencesData.getString(Constants.PREF_TOKEN, this);
                presenter.getActiveOffers(token);
            }
        }else {
            this.presenter = new MainPresenter(this);
            if (profile != null) {
                presenter.getUserInfo(new ProfileInfoReq(PreferencesData.getString(Constants.PREF_TOKEN, this), PreferencesData.getInt(Constants.Pref_USER_ID, this)));

            }
            progress = Utility.makeWaiting(Constants.WaitingTitle, Constants.WaitingDescNormal, this);
            progress.show();
            String token = PreferencesData.getString(Constants.PREF_TOKEN, this);
            presenter.getActiveOffers(token);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.presenter = new MainPresenter(this);
        profile = AppDatabase.getInMemoryDatabase(this).profileDao().getProfile();
        if (profile != null) {
            if(getSinchServiceInterface()!=null) {
                checkPermissionExtera();
            }
        }else {
            presenter.getUserInfo(new ProfileInfoReq(Utility.getToken(),Utility.getId()));
        }
        makeList();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
    @Override
    protected void onServiceConnected() {
        getSinchServiceInterface().setStartListener(this);
        checkPermissionExtera();
    }
    private void openMenu() {
        final MenuDialog dialog = new MenuDialog(this,profile);
        dialog.setListener(new MenuDialogInterface() {
            @Override
            public void onProfileClick() {
                dialog.dismiss();
                /*Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                dialog.dismiss();*/
            }

            @Override
            public void onChosenItem(MenuItem item) {
                if(item.getPosition()==1){
                    //my request activity
                    Intent intent = new Intent(MainActivity.this, OfferActivity.class);
                    startActivity(intent);
                }else if(item.getPosition()==6){
                    FirebaseInstanceId.getInstance().getInstanceId()
                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        Log.w("hilevel", "getInstanceId failed", task.getException());
                                        return;
                                    }
                                    // Get new Instance ID token
                                    String token = task.getResult().getToken();


                                    progress = Utility.makeWaiting("در حال ","لطفا منتظر بمانید...",MainActivity.this);
                                    progress.show();
                                    presenter.logout(Utility.getToken());

                                    // Log and toast

                                }
                            });
                }
                dialog.dismiss();
            }

            @Override
            public void onFinish() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onReadyOfferList(List<Offer> offerList) {
        if(progress.isShowing())
            progress.dismiss();
        if (offerList.size() > 0) {
            emptyList.setVisibility(View.GONE);
            rlList.setVisibility(View.VISIBLE);
            txtsizeCount.setText("شما " + offerList.size() +" درخواست فعال دارید");
            txtsizeCount.setVisibility(View.VISIBLE);
            adviceRecycler.setVisibility(View.VISIBLE);
            adviceRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            adviceRecycler.setAdapter(new OfferAdaoter(offerList, this, this));
        } else {
            emptyList.setVisibility(View.VISIBLE);
            rlList.setVisibility(View.GONE);
            layoutCount.setVisibility(View.GONE);
            adviceRecycler.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(String str) {

        if(!str.equals("401")) {
            progress.dismiss();
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        }else {
            LogOut();
        }
    }

    private void LogOut() {
        PreferencesData.saveBool(Constants.PREF_LOGIN,false,this);
        Intent intent = new Intent(this, PreSplashActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccessProfile(ProfileInfo profile) {

        this.profile = profile;

        AppDatabase database = AppDatabase.getInMemoryDatabase(this);
        database.profileDao().insert(profile);
        if(getSinchServiceInterface()!=null) {
           checkPermissionExtera();
        }

    }

    @Override
    public void onSuccessLogOut() {
        PreferencesData.saveBool(Constants.PREF_LOGIN,false,this);
        PreferencesData.saveString(Constants.PREF_TOKEN,"",this);
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(Offer item) {
        Intent intent = new Intent(this,AdviceViewActivity.class);
        intent.putExtra("catName",item.getTitle());
        intent.putExtra("subCatName",item.getSubTitle());
        intent.putExtra("state",item.getState());
        intent.putExtra("offerId",item.getOfferId());
        intent.putExtra("offerTime",item.getOfferTime());
        PreferencesData.saveString("catImg",item.getImgCat(),this);
        startActivity(intent);
           /* if(item_chapter.getState()==0 || item_chapter.getState() ==1) {
                Intent intent = new Intent(this, ChoseLawyerActivity.class);
                intent.putExtra("catName", item_chapter.getTitle());
                intent.putExtra("subCatName", item_chapter.getSubTitle());
                intent.putExtra("state", item_chapter.getState());
                intent.putExtra("b64", item_chapter.getImgCat());
                intent.putExtra("numberOfLower", item_chapter.getLawersCont());

                    intent.putExtra("offerId",item_chapter.getOfferId());
                    intent.putExtra("reqId", item_chapter.getAdviceId());

                startActivity(intent);
            }else if(item_chapter.getState()==2) {
                Intent intent = new Intent(this, ActivityPreCallPage.class);
                intent.putExtra("catName",item_chapter.getTitle());
                intent.putExtra("subCatName",item_chapter.getSubTitle());
                intent.putExtra("b64",item_chapter.getImgCat());
                intent.putExtra("offerId",item_chapter.getOfferId());
                intent.putExtra("reqId",item_chapter.getAdviceId());
                startActivity(intent);

            }*/

    }

    @OnClick({R.id.btn_insert_req, R.id.img_menu, R.id.img_comment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_insert_req:
                goToRequestsPage();
                break;
            case R.id.img_menu:
                openMenu();
                break;
            case R.id.img_comment:
                break;
        }
    }

    private void goToRequestsPage() {
        Intent intent = new Intent(this, RequestActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStartFailed(SinchError error) {
    }

    @Override
    public void onStarted() {
    }


    private void checkPermissionExtera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                if (ActivityCompat.checkSelfPermission(this, mPermission[0])
                        != PackageManager.PERMISSION_GRANTED
                        ) {
                    if(permissionDialog!=null) {
                        if (!permissionDialog.isShowing()) {
                            // If any permission aboe not allowed by user, this condition will execute every tim, else your else part will work
                            showDialogForAudio();
                        }else {
                            if(getSinchServiceInterface()!=null){
                                loginTosinch();
                            }
                        }
                    }else {
                        showDialogForAudio();
                    }

                }else {
                    if(getSinchServiceInterface()!=null){
                        loginTosinch();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            loginTosinch();
        }
    }

    private void showDialogForAudio() {
        permissionDialog= new QuestionDialog(this,"اجازه برقراری تماس","برای برقراری تماس با مشاوره گیرنده نیاز است که اجازه استفاده از میکروفن برای برقراری تماس های درون برنامه ای گرفته شود. آیا دسترسی می دهید؟");

        permissionDialog.setListener(new QuestionListener() {
            @Override
            public void onReject() {
                showDialogLastChance();
                permissionDialog.dismiss();
            }

            @Override
            public void onSuccess() {
                ActivityCompat.requestPermissions(MainActivity.this,
                        mPermission, 5817);
                permissionDialog.dismiss();
                loginTosinch();
            }
        });
        permissionDialog.show();
    }

    private void showDialogLastChance() {
        final QuestionDialog dialog =new QuestionDialog(this,"","در صورت اجازه ندادن ، امکان برقراری تماس از سمت مشاوره گیرنده به شما و از سمت شما به مشاوره گیرنده وجود نخواهد داشت. آیا از عدم اجازه اطمینان دارید؟");
        dialog.setListener(new QuestionListener() {
            @Override
            public void onReject() {
                dialog.dismiss();
                checkPermissionExtera();
            }

            @Override
            public void onSuccess() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5817) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                showDialogLastChance();
             //   Toast.makeText(this, "بدون دادن اجازه نمی توانید مستندی اضافه کنید.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
