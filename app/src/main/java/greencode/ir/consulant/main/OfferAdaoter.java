package greencode.ir.consulant.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import greencode.ir.consulant.R;
import greencode.ir.consulant.retrofit.respObject.Offer;
import greencode.ir.consulant.utils.PersianCalculater;
import saman.zamani.persiandate.PersianDate;

public class OfferAdaoter extends RecyclerView.Adapter<OfferAdaoter.ViewHolder> {
    public List<Offer> list;
    public onItemClick myListener;
    public Context context;
    ViewHolder viewHolder;

    public OfferAdaoter(List<Offer> list, onItemClick myListener, Context context) {
        this.list = list;
        this.myListener = myListener;
        this.context = context;
    }

    public interface onItemClick {
        public void onClick(Offer item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_active_offer, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Offer data = list.get(position);
        holder.bind(list.get(position), myListener);
        holder.txtTitle.setText(data.getTitle());
        holder.txtSubTitle.setText(data.getSubTitle());
        if(data.getState()==0) {
            holder.img_state.setImageDrawable(context.getResources().getDrawable(R.drawable.status_two));
            holder.countLayout.setVisibility(View.GONE);

        }else if(data.getState()==1) {
            holder.img_state.setImageDrawable(context.getResources().getDrawable(R.drawable.status_three));
            holder.countLayout.setVisibility(View.VISIBLE);
            PersianDate date = new PersianDate(data.getOfferTime()*1000);
            holder.txtDate.setText(PersianCalculater.getDayNameDayAndMonthName(date.getTime()));
            holder.txtTime.setText("ساعت "+PersianCalculater.getHourseAndMin(date.getTime()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends  RecyclerView.ViewHolder  {
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_sub_title)
        TextView txtSubTitle;
        @BindView(R.id.txt_date)
        TextView txtDate;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.countLayout)
        LinearLayout countLayout;

        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.img_state)
        ImageView img_state;
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
