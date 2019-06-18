package greencode.ir.consulant.requests;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greencode.ir.consulant.R;
import greencode.ir.consulant.utils.BaseActivity;

public class RequestAcceptedActivity extends BaseActivity {
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.btn_call)
    Button btnCall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_request);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_ok, R.id.btn_call})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                finish();
                break;
            case R.id.btn_call:
                break;
        }
    }
}
