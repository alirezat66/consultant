package greencode.ir.consulant.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

public class EachHoursDialog extends BottomSheetDialog {

    Context context;

    String eachHour;
    EachHoursInterface myInterface;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    @BindView(R.id.picker_price)
    PickerUI pickerPrice;

    final String[] start = {""};

    ArrayList<String> prices = new ArrayList<>();

    public EachHoursDialog(@NonNull Context context, String eachHours) {
        super(context);
        this.context = context;
        this.eachHour = eachHours;
    }

    public void setListener(EachHoursInterface listener) {
        this.myInterface = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        View view = View.inflate(context, R.layout.dialog_each_hours, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        setCancelable(false);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = -1;
        getWindow().setAttributes(params);


        for(int i = 100; i<=300000;i+=5000){
            prices.add(i+"");
        }
        PickerUISettings pickerUISettings = new PickerUISettings.Builder()
                .withItems(prices)

                .withBackgroundColor(context.getResources().getColor(R.color.white))
                .withAutoDismiss(false)
                .withItemsClickables(false)
                .withUseBlur(false)

                .build();
        int startIndex = 4;
        if(!eachHour.equals("0") && eachHour.equals("")){
            int number = Integer.parseInt(eachHour);
            int deffer =  number - 30000;
            startIndex = deffer/5000;
        }

        pickerPrice.setSettings(pickerUISettings);
        pickerPrice.slide(startIndex);
        start[0]="50000";

        pickerPrice.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() {
            @Override
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                start[0] = valueResult;
            }
        });



    }



    @OnClick(R.id.btn_accept)
    public void onClick() {
        myInterface.onAccept(start[0]);
    }
}
