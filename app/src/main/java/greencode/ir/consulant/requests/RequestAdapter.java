package greencode.ir.consulant.requests;

import android.content.Context;
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
import greencode.ir.consulant.objects.SingleChoseObject;
import greencode.ir.consulant.retrofit.respObject.Request;
import greencode.ir.consulant.utils.PersianCalculater;
import greencode.ir.consulant.utils.Utility;
import saman.zamani.persiandate.PersianDate;


public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    public List<Request> list;
    public onItemClick myListener;
    public Context context;
    ViewHolder viewHolder;


    public RequestAdapter(List<Request> list, onItemClick myListener, Context context) {
        this.list = list;
        this.myListener = myListener;
        this.context = context;
    }

    public interface onItemClick {
        public void onClick(Request item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_request, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Request data = list.get(position);
        holder.bind(list.get(position), myListener);
        holder.txtTitle.setText(data.getTitle());
        holder.txtSubTitle.setText(data.getSubTitle());
        PersianDate date = new PersianDate(data.getRequestTime()*1000);
        holder.txtDate.setText("تاریخ ثبت");
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

        public void bind(final Request item, final onItemClick listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }
    }
}
