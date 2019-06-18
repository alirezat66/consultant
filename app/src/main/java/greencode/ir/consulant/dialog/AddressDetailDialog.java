package greencode.ir.consulant.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greencode.ir.consulant.R;

public class AddressDetailDialog extends Dialog {

    Context context;
    @BindView(R.id.edt_address)
    EditText edtAddress;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_cancel)
    Button btnCancel;

    AddressDetailInterface myInterface;

    public AddressDetailDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public void setListener(AddressDetailInterface listener) {
        this.myInterface = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        View view = View.inflate(context, R.layout.dialog_address_detail, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        setCancelable(false);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = -1;
        getWindow().setAttributes(params);
    }

    @OnClick({R.id.btn_save, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                myInterface.onAccept(edtAddress.getText().toString());
                break;
            case R.id.btn_cancel:
                myInterface.onRejected();
                break;
        }
    }
}
