package greencode.ir.consulant.requests;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greencode.ir.consulant.R;
import greencode.ir.consulant.retrofit.respObject.Request;
import greencode.ir.consulant.utils.BaseActivity;
import greencode.ir.consulant.utils.PreferencesData;
import greencode.ir.consulant.utils.Utility;

public class RequestActivity extends BaseActivity implements RequestInterface , RequestAdapter.onItemClick {
    @BindView(R.id.empty_layout)
    RelativeLayout emptyLayout;
    @BindView(R.id.tool_bar)
    LinearLayout toolBar;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.list_layout)
    RelativeLayout listLayout;

    RequestAdapter adapter;
    RequestPresenter presenter;
    KProgressHUD progressHUD;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);
        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter = new RequestPresenter(this);
        progressHUD = Utility.makeWaiting(this);
        progressHUD.show();
        presenter.getRequests(Utility.getToken());
    }

    @OnClick(R.id.btn_back)
    public void onClick() {
        finish();
    }

    @Override
    public void onListReady(List<Request> posts) {
        progressHUD.dismiss();
        if(posts.size()==0){
            listLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }else {
            listLayout.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);

            list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
            list.addItemDecoration(divider);
            adapter = new RequestAdapter(posts,this,this);
            list.setAdapter(adapter);
        }

    }

    @Override
    public void onError(String str) {
        progressHUD.dismiss();
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        listLayout.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Request item) {
        Intent intent = new Intent(this,RequestDetailActivity.class);
        intent.putExtra("requestId",item.getRequestId());
        intent.putExtra("catName",item.getTitle());
        intent.putExtra("subCatName",item.getSubTitle());
        PreferencesData.saveString("catImg",item.getImgCat(),this);
        startActivity(intent);

    }
}
