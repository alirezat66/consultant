package greencode.ir.consulant.addinfo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.kaopiz.kprogresshud.KProgressHUD;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greencode.ir.consulant.R;

public class AddDocumentDialog extends Dialog {
    Context context;
    @BindView(R.id.edt_title)
    EditText edtTitle;
    @BindView(R.id.edt_desc)
    EditText edtDesc;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    AddDocListener myListener;
    public AddDocumentDialog(@NonNull Context context) {
        super(context);
        this.context = context;

    }
    public void setListener(AddDocListener addDocListener){
        myListener = addDocListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        View view = View.inflate(context, R.layout.dialog_add_doc, null);
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
                if(edtTitle.getText().toString().trim().length()>0){
                    myListener.onAccept(edtTitle.getText().toString(),edtDesc.getText().toString());
                }else {
                    edtTitle.setError("عنوان مدرک باید ثبت شود.");
                }
                break;
            case R.id.btn_cancel:
                myListener.onRejected();
                break;
        }
    }
}
