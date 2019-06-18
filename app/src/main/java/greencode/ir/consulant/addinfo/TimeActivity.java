package greencode.ir.consulant.addinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greencode.ir.consulant.R;
import greencode.ir.consulant.dialog.FreeTimeDialog;
import greencode.ir.consulant.dialog.FreeTimeInterface;
import greencode.ir.consulant.objects.FreeTime;
import greencode.ir.consulant.objects.FreeTimeBaseList;
import greencode.ir.consulant.objects.FreeTimeList;
import greencode.ir.consulant.objects.JobItem;
import greencode.ir.consulant.objects.ListOfFreeTimes;
import greencode.ir.consulant.utils.BaseActivity;

public class TimeActivity extends BaseActivity implements TimeAdapter.onItemClick{

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.btnBack)
    Button btnBack;
    TimeAdapter adapter;
    ArrayList<FreeTimeList> freeTimeLists = new ArrayList<>();
    ArrayList<FreeTime> changedTime = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_times);
        ButterKnife.bind(this);
        makeList();
    }

    private void makeList() {


        for(int i =0;i<7;i++){
            String dayName = "";
            int offset = i;
            if(i==0){
                offset = 0;
                dayName = "شنبه";
            }else if(i==1){
                offset = 3;
                dayName="یک شنبه";
            }else if(i==2){
                offset = 6;
                dayName="دوشنبه";
            }else if(i==3){
                offset = 9;
                dayName="سه شنبه";
            }else if(i==4){
                offset = 12;

                dayName="چهارشنبه";
            }else if(i==5){
                offset = 15;
                dayName="پنج شنبه";
            }else {
                offset = 18;

                dayName="جمعه";
            }
            FreeTime freeTime = new FreeTime(0+offset,1);
            FreeTime freeTime2 = new FreeTime(1+offset,2);
            FreeTime freeTime3 = new FreeTime(2+offset,3);

            freeTime.setDayOfWeek(dayName);
            freeTime2.setDayOfWeek(dayName);
            freeTime3.setDayOfWeek(dayName);
            List<FreeTime> lTime = new ArrayList<>();
            lTime.add(freeTime);
            lTime.add(freeTime2);
            lTime.add(freeTime3);
            freeTimeLists.add(new FreeTimeList(dayName,lTime));
        }
        adapter = new TimeAdapter(freeTimeLists,this,this);
        list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        list.setAdapter(adapter);
    }

    @OnClick({R.id.btnSave, R.id.btnBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:

                ListOfFreeTimes freeTimes = new ListOfFreeTimes(adapter.getItemList());
                Intent intent = getIntent();
                intent.putExtra("chosenTime", freeTimes.toJson().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btnBack:
                finish();
                break;
        }
    }

    @Override
    public void onClick(final FreeTime item, final FreeTimeList freeTimeList, final int position) {
        String shift  = "";
        if(item.getShift()==1){
            shift = "نوبت اول";
        }else if(item.getShift()==2){
            shift = "نوبت دوم";
        }else {
            shift = "نوبت سوم";
        }
        final FreeTimeDialog dialog = new FreeTimeDialog(this,item.getTimeId(),
                "ساعات پاسخگویی "+ item.getDayOfWeek(),shift,changedTime);
        dialog.setListener(new FreeTimeInterface() {
            @Override
            public void onSuccess(String start, String end) {
                freeTimeList.changeFreeTime(item.getShift(),start,end);
                boolean isOld = false;
                int indexOld = 0;
                for(int i = 0 ;i<changedTime.size();i++){
                    if(changedTime.get(i).getTimeId()==item.getTimeId()){
                        isOld=true;
                        indexOld = i;
                    }

                }
                if(isOld){
                    changedTime.set(indexOld,freeTimeList.getFreeTimes().get(item.getShift()-1));

                }else {
                    changedTime.add(freeTimeList.getFreeTimes().get(item.getShift()-1));

                }
                adapter.setChange(position,freeTimeList);
                dialog.dismiss();
            }

            @Override
            public void onRejected() {
                dialog.dismiss();

            }
        });
        dialog.show();
        /*freeTimeList.changeFreeTime(item_chapter.getShift(),"12:00","14:00");
        adapter.setChange(position,freeTimeList);*/
    }
}
