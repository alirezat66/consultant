package greencode.ir.consulant.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import greencode.ir.consulant.R;
import greencode.ir.consulant.adapter.SingleChoseAdapter;
import greencode.ir.consulant.objects.SingleChoseObject;

public class SingleChoseDialog extends BottomSheetDialog  implements SingleChoseAdapter.onItemClick{
    List<SingleChoseObject> objects;
    Context context;
    String title;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.list)
    RecyclerView list;

    SingleChoserInterface myInterface;

    public SingleChoseDialog(Context context, List<SingleChoseObject> objects, String title) {
        super(context);
        this.objects = objects;
        this.context = context;
        this.title = title;
    }
    public void setListener(SingleChoserInterface listener) {
        this.myInterface = listener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        View view = View.inflate(context, R.layout.dialog_chose_single, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        setCancelable(true);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = -1;
        getWindow().setAttributes(params);

        txtTitle.setText(title);
        list.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        list.setAdapter(new SingleChoseAdapter(objects,this,context));

    }

    @Override
    public void onBackPressed() {
        myInterface.onRejected();
        super.onBackPressed();
    }

    @Override
    public void onClick(SingleChoseObject item) {
        myInterface.onSuccess(item);
    }
}
