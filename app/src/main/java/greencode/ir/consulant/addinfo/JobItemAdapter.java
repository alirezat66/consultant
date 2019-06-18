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
import greencode.ir.consulant.objects.SingleChoseObject;


public class JobItemAdapter extends RecyclerView.Adapter<JobItemAdapter.ViewHolder> {
    public List<JobItem> list;
    public onItemClick myListener;
    public Context context;
    ViewHolder viewHolder;


    public JobItemAdapter(List<JobItem> list, onItemClick myListener, Context context) {
        this.list = list;
        this.myListener = myListener;
        this.context = context;
    }

    public void selectCats(String cat) {
        list.get(0).setChosenItem(cat);
    }

    public void selectAdvocateType(String advocateType) {
        list.get(1).setChosenItem(advocateType);
    }

    public void degreeSelect(String degree) {
        list.get(2).setChosenItem(degree);
    }

    public void selectExprience(String exprience) {
        list.get(3).setChosenItem(exprience);
    }

    public void setChosenTime(String chosenTime) {
        list.get(4).setChosenItem(chosenTime);
    }

    public void eachOursSet(String eachHours) {
        list.get(5).setChosenItem(eachHours);
    }

    public void setchosenAddress(String address) {
        list.get(6).setChosenItem(address);
    }

    public void setchosenImages(String img) {
        list.get(7).setChosenItem(img);
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
        if(data.getId()==8){
            holder.imgLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add_gray));
        }else {
            holder.imgLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.detail_icon));
        }
        holder.txt_desc.setText(data.getChosenItem());

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
        TextView txt_desc;
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
