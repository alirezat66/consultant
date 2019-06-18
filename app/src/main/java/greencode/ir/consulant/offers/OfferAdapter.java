package greencode.ir.consulant.offers;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import greencode.ir.consulant.R;
import greencode.ir.consulant.retrofit.respObject.Offer;
import greencode.ir.consulant.retrofit.respObject.Request;
import greencode.ir.consulant.utils.Utility;
import saman.zamani.persiandate.PersianDate;


public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {
    public List<Offer> list;
    public onItemClick myListener;
    public Context context;
    ViewHolder viewHolder;


    public OfferAdapter(List<Offer> list, onItemClick myListener, Context context) {
        this.list = list;
        this.myListener = myListener;
        this.context = context;
    }

    public interface onItemClick {
        public void onClick(Offer item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_offer, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Offer data = list.get(position);
        holder.bind(list.get(position), myListener);
        holder.txtTitle.setText(data.getTitle());
        holder.txtSubTitle.setText(data.getSubTitle());
        PersianDate date = new PersianDate(data.getOfferTime()*1000);
//درانتظار تایید = 0 ، لغوشده قبل تایید کاربر = 5، لغوشده بعد از تایید کاربر = 6، تایید شده = 1 ،انجام شده = 3،عدم تایید = 4،درحال مشاوره=2
        String state = "";
        int txtColor =0;
        if(data.getState()==0){
            state ="درانتظار تایید";
            txtColor = context.getResources().getColor(R.color.textBlue);
        }else if(data.getState()==1){
            txtColor = context.getResources().getColor(R.color.done);

            state ="تایید شده";
        }else if(data.getState()==2){
            txtColor = context.getResources().getColor(R.color.done);

            state = "درحال مشاوره";
        }else if(data.getState()==3){
            txtColor = context.getResources().getColor(R.color.done);

            state = "نجام شده";
        }else if(data.getState()==4){
            txtColor = context.getResources().getColor(R.color.textBlue);

            state ="عدم تایید";
        }else if(data.getState()==5){
            txtColor = context.getResources().getColor(R.color.canceled);

            state ="لغوشده";
        }else {
            txtColor = context.getResources().getColor(R.color.canceled);

            state = "لغوشده";
        }
        holder.txtDate.setText(state);
        holder.txtDate.setTextColor(txtColor);
        holder.txtTime.setText(Utility.getDefrence(date.getTime()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_sub_title)
        TextView txtSubTitle;
        @BindView(R.id.txt_date)
        TextView txtDate;
        @BindView(R.id.txt_time)
        TextView txtTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final Offer item, final onItemClick listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }
    }
}
