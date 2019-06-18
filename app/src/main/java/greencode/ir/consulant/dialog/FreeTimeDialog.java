package greencode.ir.consulant.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greencode.ir.consulant.R;
import greencode.ir.consulant.adapter.MultiChoseAdapter;
import greencode.ir.consulant.objects.FreeTime;
import greencode.ir.consulant.objects.MultiChoseObject;
import greencode.ir.consulant.picker.PickerUI;
import greencode.ir.consulant.picker.PickerUISettings;

public class FreeTimeDialog extends BottomSheetDialog  {
    Context context;
    String title;
String shift;

    FreeTimeInterface myInterface;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_shift)
    TextView txtShift;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    @BindView(R.id.picker_end)
    PickerUI pickerEnd;
    @BindView(R.id.picker_start)
    PickerUI pickerStart;
    ArrayList<String> startEnds = new ArrayList<>();

    ArrayList<FreeTime> changedTime = new ArrayList<>();
    int id;
    public FreeTimeDialog(Context context,int id, String title, String shift,ArrayList<FreeTime> changedTime) {
        super(context);
        this.id = id;
        this.context = context;
        this.title = title;
        this.shift = shift;
        this.changedTime = changedTime;
    }

    public void setListener(FreeTimeInterface listener) {
        this.myInterface = listener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        View view = View.inflate(context, R.layout.dialog_free_time, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        setCancelable(false);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = -1;
        getWindow().setAttributes(params);
        txtTitle.setText(title);
        txtShift.setText(this.shift);

        final String[] start = {""};
        final String[] end = {""};
        startEnds.add("00:00");
        startEnds.add("01:00");
        startEnds.add("02:00");
        startEnds.add("03:00");
        startEnds.add("04:00");
        startEnds.add("05:00");
        startEnds.add("06:00");
        startEnds.add("07:00");
        startEnds.add("08:00");
        startEnds.add("09:00");
        startEnds.add("10:00");
        startEnds.add("11:00");
        startEnds.add("12:00");
        startEnds.add("13:00");
        startEnds.add("14:00");
        startEnds.add("15:00");
        startEnds.add("16:00");
        startEnds.add("17:00");
        startEnds.add("18:00");
        startEnds.add("19:00");
        startEnds.add("20:00");
        startEnds.add("21:00");
        startEnds.add("22:00");
        startEnds.add("23:00");

        PickerUISettings pickerUISettings = new PickerUISettings.Builder()
                .withItems(startEnds)
                .withBackgroundColor(context.getResources().getColor(R.color.white))
                .withAutoDismiss(false)
                .withItemsClickables(false)
                .withUseBlur(false)
                .build();

        pickerStart.setSettings(pickerUISettings);
        pickerEnd.setSettings(pickerUISettings);
        pickerStart.slide();
        pickerEnd.slide();
        start[0]="12:00";
        end[0]="12:00";
        pickerStart.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() {
            @Override
            public void onItemClickPickerUI(int which, int position, String valueResult) {
              start[0] = valueResult;
            }
        });
        pickerEnd.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() {
            @Override
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                end[0] = valueResult;
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(startEnds.indexOf(start[0])<startEnds.indexOf(end[0])){
                    if(changedTime.size()==0) {
                        myInterface.onSuccess(start[0], end[0]);
                    }else {
                        List<Integer> sameParentChild =getParentChild(id);
                        boolean hasConflict = false;
                        for(FreeTime freeTime : changedTime){
                            if(freeTime.getTimeId()==sameParentChild.get(0)||
                                    freeTime.getTimeId()==sameParentChild.get(1)){
                                if(haveConfilict(freeTime,start[0],end[0])){
                                    hasConflict = true;
                                    break;
                                }
                            }
                        }
                        if(hasConflict){
                            Toast.makeText(context, "بین مقدار انتخابی و مقادیر از پیش انتخاب شده برای این روز نباید همپوشانی وجود داشته باشد.", Toast.LENGTH_SHORT).show();

                        }else {
                            myInterface.onSuccess(start[0], end[0]);
                        }

                    }

                }
                            }
        });
    }

    private boolean haveConfilict(FreeTime freeTime, String s, String s1) {
       int startIndex = startEnds.indexOf(s);
       int endIndex = startEnds.indexOf(s1);
       int startFree  = startEnds.indexOf(freeTime.getStartTime());
       int endFree = startEnds.indexOf(freeTime.getEndTime());

       if(startFree>=endIndex || endFree<=startIndex){
           return false;
       }else {
           return true;
       }

    }

    private List<Integer> getParentChild(int id){
        List<Integer> sameParentChild = new ArrayList<>();

        if(id==0){
            sameParentChild.add(1);
            sameParentChild.add(2);
        }else if(id==1) {
            sameParentChild.add(0);
            sameParentChild.add(2);
        }else if (id==2){
            sameParentChild.add(0);
            sameParentChild.add(1);
        }else if(id==3){
            sameParentChild.add(4);
            sameParentChild.add(5);
        }else if(id==4){
            sameParentChild.add(3);
            sameParentChild.add(5);
        }else if(id==5){
            sameParentChild.add(3);
            sameParentChild.add(4);
        }else if(id==6){
            sameParentChild.add(7);
            sameParentChild.add(8);
        }else if(id==7){
            sameParentChild.add(6);
            sameParentChild.add(8);
        }else if(id==8){
            sameParentChild.add(6);
            sameParentChild.add(7);
        }else if(id==9){
            sameParentChild.add(10);
            sameParentChild.add(11);
        }else if(id==10){
            sameParentChild.add(9);
            sameParentChild.add(11);
        }else if(id==11){
            sameParentChild.add(9);
            sameParentChild.add(10);
        }else if(id==12){
            sameParentChild.add(13);
            sameParentChild.add(14);
        }else if(id==13){
            sameParentChild.add(12);
            sameParentChild.add(14);
        }else if(id==14){
            sameParentChild.add(12);
            sameParentChild.add(13);
        }else if(id==15){
            sameParentChild.add(16);
            sameParentChild.add(17);
        }else if(id==16){
            sameParentChild.add(15);
            sameParentChild.add(17);
        }else if(id==17){
            sameParentChild.add(15);
            sameParentChild.add(16);
        }else if(id==18){
            sameParentChild.add(19);
            sameParentChild.add(20);
        }else if(id==19){
            sameParentChild.add(18);
            sameParentChild.add(20);
        }else if(id==20) {
            sameParentChild.add(18);
            sameParentChild.add(19);
        }
        return sameParentChild;
    }
    @Override
    public void onBackPressed() {
        myInterface.onRejected();
        super.onBackPressed();
    }



    @OnClick(R.id.btn_accept)
    public void onClick() {
    }
}
