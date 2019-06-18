package greencode.ir.consulant.addinfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import greencode.ir.consulant.R;
import greencode.ir.consulant.objects.JobItem;


public class AddressItemAdapter extends RecyclerView.Adapter<AddressItemAdapter.ViewHolder> {
    public List<JobItem> list;
    public onItemClick myListener;
    public Context context;
    ViewHolder viewHolder;


    public AddressItemAdapter(List<JobItem> list, onItemClick myListener, Context context) {
        this.list = list;
        this.myListener = myListener;
        this.context = context;
    }

    public void setCountryChecked(String country) {
        list.get(0).setChosenItem(country);
    }

    public void setProviceChecked(String provice) {
        list.get(1).setChosenItem(provice);
    }

    public void choseCity(String city) {
        list.get(2).setChosenItem(city);
    }

    public void setAreaChose(String area) {
        list.get(3).setChosenItem(area);
    }


    public interface onItemClick {
        public void onClick(JobItem item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_profile, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobItem data = list.get(position);
        holder.bind(list.get(position), myListener);
        holder.txtTitle.setText(data.getTitle());
        if(data.getId()==5){
            holder.imgLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.address_icon));
        }else if(data.getId()==6){
            holder.imgLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.location_icon));

        }
        else {
            holder.imgLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.detail_icon));
        }
         holder.txtDesc.setText(data.getChosenItem());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.img_logo)
        ImageView imgLogo;
        @BindView(R.id.txt_desc)
                TextView txtDesc;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final JobItem item, final onItemClick listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }
    }
}
