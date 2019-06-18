package greencode.ir.consulant.addinfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import greencode.ir.consulant.R;
import greencode.ir.consulant.objects.FreeTime;
import greencode.ir.consulant.objects.FreeTimeBaseList;
import greencode.ir.consulant.objects.FreeTimeList;
import greencode.ir.consulant.objects.JobItem;


public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {
    public List<FreeTimeList> list;
    public onItemClick myListener;
    public Context context;
    ViewHolder viewHolder;


    public TimeAdapter(List<FreeTimeList> list, onItemClick myListener, Context context) {
        this.list = list;
        this.myListener = myListener;
        this.context = context;
    }

    public void setChange(int position, FreeTimeList freeTimeList) {
       list.set(position,freeTimeList);
        notifyDataSetChanged();
    }

    public List<FreeTime> getItemList() {
        List<FreeTime> freeTimes = new ArrayList<>();
        for(FreeTimeList freeTimeList : list){
            for(FreeTime freeTime : freeTimeList.getFreeTimes()){
                if(!freeTime.getStartTime().equals("")){



                    freeTimes.add(freeTime);
                }
            }
        }
        return freeTimes;
    }

    public interface onItemClick {
        public void onClick(FreeTime item , FreeTimeList freeTimeList,int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_time, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final FreeTimeList data = list.get(position);
        holder.txtDay.setText(data.getDayName());
        if(data.getFreeTimes().get(0).getStartTime().equals("")){
            holder.txtShiftOne.setText("نوبت اول");
        }else {
            holder.txtShiftOne.setText(data.getFreeTimes().get(0).getHourStartTime()+ " الی " +data.getFreeTimes().get(0).getHourEndTime());

        }

        if(data.getFreeTimes().get(1).getStartTime().equals("")){
            holder.txtShiftTwo.setText("نوبت دوم");
        }else {
            holder.txtShiftTwo.setText(data.getFreeTimes().get(1).getHourStartTime()+ " الی " +data.getFreeTimes().get(1).getHourEndTime());

        }
        if(data.getFreeTimes().get(2).getStartTime().equals("")){
            holder.txtShiftThree.setText("نوبت سوم");
        }else {
            holder.txtShiftThree.setText(data.getFreeTimes().get(2).getHourStartTime()+ " الی " +data.getFreeTimes().get(2).getHourEndTime());

        }

        holder.txtShiftOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.onClick(data.getFreeTimes().get(0),data,position);
            }
        });
        holder.txtShiftTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.onClick(data.getFreeTimes().get(1),data,position);
            }
        });
        holder.txtShiftThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.onClick(data.getFreeTimes().get(2),data,position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_day)
        TextView txtDay;
        @BindView(R.id.txt_shift_one)
        TextView txtShiftOne;
        @BindView(R.id.txt_shift_two)
        TextView txtShiftTwo;
        @BindView(R.id.txt_shift_three)
        TextView txtShiftThree;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }
}
