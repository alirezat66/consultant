package greencode.ir.consulant.requests;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greencode.ir.consulant.R;
import greencode.ir.consulant.main.PhotoActivity;
import greencode.ir.consulant.objects.DocumentOfReq;
import greencode.ir.consulant.retrofit.reqobject.AcceptReq;
import greencode.ir.consulant.retrofit.reqobject.GetWaitedDetailsReq;
import greencode.ir.consulant.retrofit.respObject.WaitedRequestDetial;
import greencode.ir.consulant.utils.BaseActivity;
import greencode.ir.consulant.utils.PreferencesData;
import greencode.ir.consulant.utils.Utility;

public class RequestDetailActivity extends BaseActivity implements RequestDetailInterface,DocumentReqAdapter.onItemClick {
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.txt_cat_name)
    TextView txtCatName;
    @BindView(R.id.txt_sub_cat_name)
    TextView txtSubCatName;
    @BindView(R.id.txt_description)
    TextView txtDescription;
    @BindView(R.id.docs_recycler)
    RecyclerView docsRecycler;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn_accept)
    Button btnAccept;

    RequestDetailPresenter presenter;

    KProgressHUD progressHUD;

    DocumentReqAdapter adapter;

    int requestId = 0;
    String title ="";
    String subTitle="";
    String photo = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);
        ButterKnife.bind(this);
        progressHUD = Utility.makeWaiting(this);
        progressHUD.show();
        presenter = new RequestDetailPresenter(this);
        Bundle bundle  = getIntent().getExtras();
        if(bundle!=null){
            requestId = bundle.getInt("requestId");
            title = bundle.getString("catName");
            subTitle = bundle.getString("subCatName");
            photo = PreferencesData.getString("catImg",this);
        }
        presenter.getDetail(new GetWaitedDetailsReq(requestId,Utility.getToken()));
        txtCatName.setText(title);
        txtSubCatName.setText(subTitle);
        if(!photo.equals("")) {
            Bitmap bitmap = Utility.getBitmapFromBase(photo);
            imgLogo.setImageBitmap(bitmap);
        }

    }

    @OnClick({R.id.btn_back, R.id.btn_accept})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_accept:
                progressHUD.show();
                presenter.acceptReq(new AcceptReq(Utility.getToken(),requestId,Utility.getId()));
                break;
        }
    }

    @Override
    public void onDetailReady(WaitedRequestDetial detail) {
        progressHUD.dismiss();
        txtDescription.setText(detail.getContext());
        adapter = new DocumentReqAdapter(detail.getDocuments(),this,this);
        docsRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        docsRecycler.addItemDecoration(divider);
        docsRecycler.setAdapter(adapter);
    }

    @Override
    public void onError(String str) {
        progressHUD.dismiss();
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAccept() {
        progressHUD.dismiss();
        Intent intent = new Intent(this,RequestAcceptedActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(DocumentOfReq item) {
        Intent intent = new Intent(this, PhotoActivity.class);
        PreferencesData.saveString("myb64",item.getDocSource(),this);
        startActivity(intent);
    }
}
