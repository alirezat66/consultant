package greencode.ir.consulant.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greencode.ir.consulant.R;
import greencode.ir.consulant.picker.PickerUI;
import greencode.ir.consulant.picker.PickerUISettings;

public class ExprienceYearsDialog extends BottomSheetDialog {

    Context context;

    ExprienceYearsInterface myInterface;
    String exprience = "";
    @BindView(R.id.btn_accept)
    Button btnAccept;
    @BindView(R.id.picker_price)
    PickerUI pickerPrice;

    final String[] start = {""};
    ArrayList<String> years = new ArrayList<>();

    public ExprienceYearsDialog(@NonNull Context context, String exprience) {
        super(context);
        this.context = context;
        this.exprience = exprience;
    }

    public void setListener(ExprienceYearsInterface listener) {
        this.myInterface = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        View view = View.inflate(context, R.layout.dialog_exprience_year, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        setCancelable(false);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = -1;
        getWindow().setAttributes(params);




        years.add("بدون سابقه کار");
        for(int i = 1; i<=50;i++){
            years.add(i+"");
        }
        PickerUISettings pickerUISettings = new PickerUISettings.Builder()
                .withItems(years)

                .withBackgroundColor(context.getResources().getColor(R.color.white))
                .withAutoDismiss(false)
                .withItemsClickables(false)
                .withUseBlur(false)

                .build();
        int startIndex = 6;
        if(!exprience.equals("0") && !exprience.equals("")){
            int number = Integer.parseInt(exprience);
            startIndex = number;
        }

        pickerPrice.setSettings(pickerUISettings);
        pickerPrice.slide(startIndex);
        start[0]="5";

        pickerPrice.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() {
            @Override
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                start[0] = valueResult;
            }
        });


    }



    @OnClick(R.id.btn_accept)
    public void onClick() {
        if(start[0].equals("بدون سابقه کار")){
            myInterface.onAccept("0");

        }else {
            myInterface.onAccept(start[0]);
        }
    }
}
