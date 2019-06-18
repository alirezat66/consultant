package greencode.ir.consulant.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import greencode.ir.consulant.R;
import greencode.ir.consulant.adapter.MultiChoseAdapter;
import greencode.ir.consulant.adapter.SingleChoseAdapter;
import greencode.ir.consulant.objects.MultiChoseObject;
import greencode.ir.consulant.objects.SingleChoseObject;

public class MultiChoseDialog extends BottomSheetDialog  implements MultiChoseAdapter.onItemClick {
    List<MultiChoseObject> objects;
    Context context;
    String title;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.btn_accept)
    Button btnAccept;

    MultiChoserInterface myInterface;
    MultiChoseAdapter adapter;
    public MultiChoseDialog(Context context, List<MultiChoseObject> objects, String title) {
        super(context);
        this.objects = objects;
        this.context = context;
        this.title = title;
    }
    public void setListener(MultiChoserInterface listener) {
        this.myInterface = listener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        View view = View.inflate(context, R.layout.dialog_chose_multiple , null);
        ButterKnife.bind(this, view);
        setContentView(view);
        setCancelable(false);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = -1;
        getWindow().setAttributes(params);
        txtTitle.setText(title);
        list.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        adapter = new MultiChoseAdapter(objects,getContext(),this);
        list.setAdapter(adapter);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInterface.onSuccess(adapter.getAllItems());
            }
        });
    }

    @Override
    public void onBackPressed() {
        myInterface.onRejected();
        super.onBackPressed();
    }

    @Override
    public void onClick(MultiChoseObject item) {
        adapter.changeChecked(item);
        adapter.notifyDataSetChanged();
    }
}
