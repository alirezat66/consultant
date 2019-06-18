package greencode.ir.consulant.offers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.sinch.android.rtc.calling.Call;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greencode.ir.consulant.R;
import greencode.ir.consulant.calling.CallScreenActivity;
import greencode.ir.consulant.controler.AppDatabase;
import greencode.ir.consulant.dialog.QuestionDialog;
import greencode.ir.consulant.dialog.QuestionListener;
import greencode.ir.consulant.main.PhotoActivity;
import greencode.ir.consulant.objects.DocumentOfReq;
import greencode.ir.consulant.requests.DocumentReqAdapter;
import greencode.ir.consulant.retrofit.reqobject.AssignedResponse;
import greencode.ir.consulant.retrofit.reqobject.CancelOfferReq;
import greencode.ir.consulant.retrofit.reqobject.DoneAdviceDetailReq;
import greencode.ir.consulant.retrofit.respObject.AdviceDetail;
import greencode.ir.consulant.retrofit.respObject.CallStateResponse;
import greencode.ir.consulant.services.SinchService;
import greencode.ir.consulant.utils.BaseActivity;
import greencode.ir.consulant.utils.PersianCalculater;
import greencode.ir.consulant.utils.PreferencesData;
import greencode.ir.consulant.utils.Utility;

public class AdviceViewActivity extends BaseActivity implements AdviceInterface, DocumentReqAdapter.onItemClick {
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.txt_cat_name)
    TextView txtCatName;
    @BindView(R.id.txt_sub_cat_name)
    TextView txtSubCatName;
    @BindView(R.id.txt_state)
    TextView txtState;
    @BindView(R.id.lyState)
    RelativeLayout lyState;
    @BindView(R.id.txt_my_act)
    TextView txtMyAct;
    @BindView(R.id.divider_one)
    LinearLayout dividerOne;
    @BindView(R.id.txt_user_act)
    TextView txtUserAct;
    @BindView(R.id.divider_two)
    LinearLayout dividerTwo;
    @BindView(R.id.txt_payment_act)
    TextView txtPaymentAct;
    @BindView(R.id.divider_three)
    LinearLayout dividerThree;
    @BindView(R.id.txt_free_state)
    TextView txtFreeState;
    @BindView(R.id.divider_four)
    LinearLayout dividerFour;
    @BindView(R.id.txt_description)
    TextView txtDescription;
    @BindView(R.id.docs_recycler)
    RecyclerView docsRecycler;
    @BindView(R.id.second_layout)
    LinearLayout secondLayout;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.just_has_back_ly)
    LinearLayout justHasBackLy;
    @BindView(R.id.btn_back_second)
    Button btnBackSecond;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.back_and_cancel_ly)
    LinearLayout backAndCancelLy;

    String catName = "";
    String subCatName = "";
    int state = 0;
    int offerId = 0;
    long offerTime = 0;
    String catImg = "";
    String differ = "";
    AdvicePresenter presenter;
    KProgressHUD progressHUD;
    KProgressHUD callProgress;
    DocumentReqAdapter adapter;
    @BindView(R.id.btn_back_third)
    Button btnBackThird;
    @BindView(R.id.btn_call)
    Button btnCall;
    @BindView(R.id.btn_cancel_third)
    Button btnCancelThird;
    @BindView(R.id.back_and_cancel_and_call_ly)
    LinearLayout backAndCancelAndCallLy;
    int userId;
    String userName;

    AssignedResponse detailResponse;
    String[] mPermission = {
            Manifest.permission.RECORD_AUDIO
    };
    private static final int REQUEST_CODE_PERMISSION = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice_view);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            catName = bundle.getString("catName");
            subCatName = bundle.getString("subCatName");
            state = bundle.getInt("state");
            offerId = bundle.getInt("offerId");
            offerTime = bundle.getLong("offerTime");
            AppDatabase database = AppDatabase.getInMemoryDatabase(this);
            userId = database.profileDao().getProfile().getId();
            userName = "lawyer"+userId;
        }
        progressHUD = Utility.makeWaiting(this);
        presenter = new AdvicePresenter(this);
        txtCatName.setText(catName);
        txtSubCatName.setText(subCatName);
        catImg = PreferencesData.getString("catImg", this);
        if (!catImg.equals("")) {
            Bitmap bitmap = Utility.getBitmapFromBase(catImg);
            imgLogo.setImageBitmap(bitmap);
        }
        differ = Utility.getDefrence(offerTime * 1000);

        if (state == 0) {
            // مشاور تایید کرده ولی کاربر تایید نکرده
            waitStat();
            //درانتظار تایید = 0 ، لغوشده = 2، تایید شده = 1 ،انجام شده = 3،عدم تایید = 4،درحال مشاوره=
        } else if (state == 2) {
            //و نزدیک هم هست و در حال مشاوره تایید و پرداخت شده
            activeState();
            //  doneState();
            //  canceledState();
        } else if (state == 1) {
            // تایید شده و منتظر مشاوره
            activeState();
        } else if (state == 3) {
            // مکالمه تمام شد
            doneState();
        } else if (state == 4) {
            // کاربر یک مشاور دیگر را انتخاب کرده
            notAccept();
        } else if (state == 5) {

            // لغو شده توسط مشاور قبل از تایید کاربر
            canceledState();
        } else if (state == 6) {
            // لغو شده توسط مشاور بعد از تایید کاربر

            canceledState();
        }


    }

    private void inProgressState() {
    }

    private void doneState() {
        backAndCancelLy.setVisibility(View.VISIBLE);
        justHasBackLy.setVisibility(View.GONE);

        lyState.setBackgroundColor(getResources().getColor(R.color.done));
        txtState.setText("در انتظار زمان مشاوره");

        String timeS = "آمادگی شما برای مشاوره " + differ + " به کاربر اطلاع داده شد.";
        txtMyAct.setText(timeS);
        txtMyAct.setTextColor(getResources().getColor(R.color.gray_key));
        txtMyAct.setVisibility(View.VISIBLE);
        dividerOne.setVisibility(View.VISIBLE);
        txtUserAct.setVisibility(View.VISIBLE);
        dividerTwo.setVisibility(View.VISIBLE);

        txtPaymentAct.setVisibility(View.VISIBLE);
        txtPaymentAct.setTextColor(getResources().getColor(R.color.done));
        dividerThree.setVisibility(View.VISIBLE);
        txtFreeState.setVisibility(View.VISIBLE);
        dividerFour.setVisibility(View.VISIBLE);
        txtFreeState.setTextColor(getResources().getColor(R.color.canceled));
        progressHUD.show();
        presenter.getAssignedRequestDetail(new DoneAdviceDetailReq(offerId));
    }

    private void notAccept() {
    }

    private void activeState() {
        presenter.getAssignedRequestDetail(new DoneAdviceDetailReq(offerId));

        backAndCancelLy.setVisibility(View.VISIBLE);
        justHasBackLy.setVisibility(View.GONE);

        lyState.setBackgroundColor(getResources().getColor(R.color.done));





        txtState.setText("در انتظار زمان مشاوره");

        String timeS = "آمادگی شما برای مشاوره " + differ + " به کاربر اطلاع داده شد.";
        txtMyAct.setText(timeS);
        txtMyAct.setTextColor(getResources().getColor(R.color.gray_key));
        txtMyAct.setVisibility(View.VISIBLE);
        dividerOne.setVisibility(View.VISIBLE);
        txtUserAct.setVisibility(View.VISIBLE);
        dividerTwo.setVisibility(View.VISIBLE);

        txtPaymentAct.setVisibility(View.VISIBLE);
        txtPaymentAct.setTextColor(getResources().getColor(R.color.done));
        dividerThree.setVisibility(View.VISIBLE);
        txtFreeState.setVisibility(View.VISIBLE);
        dividerFour.setVisibility(View.VISIBLE);
        txtFreeState.setTextColor(getResources().getColor(R.color.canceled));

        String timeStr = PersianCalculater.getDayNameDayNumberMonthNameAndHours(offerTime * 1000);
        txtFreeState.setText("مشاوره شما " + timeStr + " می باشد.");
        progressHUD.show();


    }

    private void canceledState() {
        backAndCancelLy.setVisibility(View.GONE);
        justHasBackLy.setVisibility(View.VISIBLE);

        lyState.setBackgroundColor(getResources().getColor(R.color.canceled));
        txtState.setText("لغو شده");

        String timeS = "آمادگی شما برای مشاوره " + differ + " به کاربر اطلاع داده شد.";
        txtMyAct.setText(timeS);
        txtMyAct.setTextColor(getResources().getColor(R.color.gray_key));
        txtMyAct.setVisibility(View.VISIBLE);
        dividerOne.setVisibility(View.VISIBLE);
        txtUserAct.setVisibility(View.GONE);
        dividerTwo.setVisibility(View.GONE);

        txtPaymentAct.setVisibility(View.VISIBLE);
        txtPaymentAct.setTextColor(getResources().getColor(R.color.gray_key));
        dividerThree.setVisibility(View.VISIBLE);
        txtFreeState.setVisibility(View.VISIBLE);
        dividerFour.setVisibility(View.VISIBLE);
        txtFreeState.setTextColor(getResources().getColor(R.color.canceled));
        txtFreeState.setText("درخواست لغو شده است.");
        progressHUD.show();
        presenter.getCanceledRequestBeforeAssignedDetial(new DoneAdviceDetailReq(offerId));

    }

    private void waitStat() {

        backAndCancelLy.setVisibility(View.VISIBLE);
        justHasBackLy.setVisibility(View.GONE);

        lyState.setBackgroundColor(getResources().getColor(R.color.greenBlue));
        txtState.setText("در انتظار تایید کاربر");

        String timeS = "آمادگی شما برای مشاوره " + differ + " روز پیش به کاربر اطلاع داده شد.";
        txtMyAct.setText(timeS);
        txtMyAct.setTextColor(getResources().getColor(R.color.gray_key));
        txtMyAct.setVisibility(View.VISIBLE);
        dividerOne.setVisibility(View.VISIBLE);
        txtUserAct.setVisibility(View.GONE);
        dividerTwo.setVisibility(View.GONE);
        txtPaymentAct.setVisibility(View.VISIBLE);
        txtPaymentAct.setTextColor(getResources().getColor(R.color.gray_key));
        dividerThree.setVisibility(View.VISIBLE);
        txtFreeState.setVisibility(View.GONE);
        dividerFour.setVisibility(View.GONE);

        progressHUD.show();
        presenter.getReadyToAnswerRequestDetial(new DoneAdviceDetailReq(offerId));


    }

    @OnClick({R.id.btn_back, R.id.btn_back_second, R.id.btn_cancel,R.id.btn_back_third,R.id.btn_call,R.id.btn_cancel_third})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_back_second:
                finish();
                break;
            case R.id.btn_back_third:
                finish();
                break;
            case R.id.btn_cancel_third:
                showInsureDialog("آیا از لغو این درخواست اطمینان دارید؟");
                break;
            case R.id.btn_call:
                if (ActivityCompat.checkSelfPermission(this, mPermission[0])
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            mPermission, REQUEST_CODE_PERMISSION);
                    // If any permission aboe not allowed by user, this condition will execute every tim, else your else part will work
                }else {
                    progressHUD = Utility.makeWaiting(this);
                    progressHUD.show();
                    presenter.getCallState(Utility.getToken(),offerId+"");

                }
                        break;
            case R.id.btn_cancel:
                showInsureDialog("آیا از لغو این درخواست اطمینان دارید؟");
                break;

        }
    }

    private void callingProcess() {

        if (!userName.equals(getSinchServiceInterface().getUserName())) {
            getSinchServiceInterface().stopClient();
            getSinchServiceInterface().startClient(userName);
            callProgress = Utility.makeWaiting("در حال برقراری تماس","لطفا منتظر بمانید...",this);
            callProgress.show();
        }

        else if (!getSinchServiceInterface().isStarted()) {
            getSinchServiceInterface().startClient(userName);
            callProgress = Utility.makeWaiting("در حال برقراری تماس","لطفا منتظر بمانید...",this);
            callProgress.show();
        } else {
            openPlaceCallActivity();
        }
    }

    private void openPlaceCallActivity() {
        Call call = getSinchServiceInterface().callUser("user"+detailResponse.getNormalUserId());
        if (call == null) {
            // Service failed for some reason, show a Toast and abort
            Toast.makeText(this, "Service is not started. Try stopping the service and starting it again before "
                    + "placing a call.", Toast.LENGTH_LONG).show();
            progressHUD.dismiss();
            return;
        }
        String callId = call.getCallId();
        Intent callScreen = new Intent(this, CallScreenActivity.class);
        callScreen.putExtra(SinchService.CALL_ID, callId);
        callScreen.putExtra("ourcallId",detailResponse.getCallId());
        startActivity(callScreen);
    }

    private void showInsureDialog(String question) {
        final QuestionDialog dialog = new QuestionDialog(this, "", question);
        dialog.setListener(new QuestionListener() {
            @Override
            public void onReject() {
                dialog.dismiss();
            }

            @Override
            public void onSuccess() {
                progressHUD = Utility.makeWaiting(AdviceViewActivity.this);
                progressHUD.show();
                presenter.cancelOffer(new CancelOfferReq(offerId));
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    @Override
    public void onDetailReady(AdviceDetail detail) {

        txtDescription.setText(detail.getContext());
        txtPaymentAct.setText("مبلغ " + detail.getPrice() + " تومان برای هر ساعت مشاوره اعلام گردید.");
        if (detail.getDocs() != null) {
            adapter = new DocumentReqAdapter(detail.getDocs(), this, this);
            docsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            docsRecycler.setAdapter(adapter);
        }

        txtDescription.setText(detail.getContext());

        progressHUD.dismiss();

    }

    @Override
    public void onError(String message) {
        progressHUD.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCanceled() {
        progressHUD.dismiss();
        finish();
    }

    @Override
    public void onSuccessCallState(CallStateResponse res) {
        progressHUD.dismiss();
        if(res.getState()==1) {
            callingProcess();
        }else {
            Toast.makeText(this, "تماس در این ساعت بر اساس سفارش شما مقدور نیست . لطفا در زمان صحیح تماس بگیرید.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetailReady(AssignedResponse detail) {
        detailResponse = detail;
        txtDescription.setText(detail.getContext());
        txtPaymentAct.setText("مبلغ " + detail.getPrice() + " تومان برای هر ساعت مشاوره پرداخت گردید.");
        String defferAccept = Utility.getDefrence(detail.getTimeOfAssigned() * 1000);
        txtUserAct.setText(defferAccept + "، کاربر شما را به عنوان مشاور نهایی خود تایید نمود.");
        String timeStr = PersianCalculater.getDayNameDayNumberMonthNameAndHours(detail.getTimeOfCall() * 1000);
        txtFreeState.setText("مشاوره شما " + timeStr + " می باشد.");

        if (detail.getDocuments() != null) {
            adapter = new DocumentReqAdapter(detail.getDocuments(), this, this);
            docsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            docsRecycler.setAdapter(adapter);
        }
        txtDescription.setText(detail.getContext());

        if(detail.getTimeOfServer()>detail.getTimeOfCall() && (state==1 ||state==2)){
            stateCall();
        }
        progressHUD.dismiss();
    }

    private void stateCall() {
        backAndCancelLy.setVisibility(View.GONE);
        justHasBackLy.setVisibility(View.GONE);
        backAndCancelAndCallLy.setVisibility(View.VISIBLE);
        lyState.setBackgroundColor(getResources().getColor(R.color.done));





        txtState.setText("در حال مشاوره");

        String timeS = "آمادگی شما برای مشاوره " + differ + " به کاربر اطلاع داده شد.";
        txtMyAct.setText(timeS);
        txtMyAct.setTextColor(getResources().getColor(R.color.gray_key));
        txtMyAct.setVisibility(View.VISIBLE);
        dividerOne.setVisibility(View.VISIBLE);
        txtUserAct.setVisibility(View.VISIBLE);
        dividerTwo.setVisibility(View.VISIBLE);

        txtPaymentAct.setVisibility(View.VISIBLE);
        txtPaymentAct.setTextColor(getResources().getColor(R.color.done));
        dividerThree.setVisibility(View.VISIBLE);
        txtFreeState.setVisibility(View.VISIBLE);
        dividerFour.setVisibility(View.VISIBLE);
        txtFreeState.setTextColor(getResources().getColor(R.color.canceled));

        String timeStr = PersianCalculater.getDayNameDayNumberMonthNameAndHours(offerTime * 1000);
        txtFreeState.setText("مشاوره شما " + timeStr + " می باشد.");
    }

    @Override
    public void onClick(DocumentOfReq item) {
        Intent intent = new Intent(this, PhotoActivity.class);
        PreferencesData.saveString("myb64", item.getDocSource(), this);
        startActivity(intent);
    }
}
