package greencode.ir.consulant.requests;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import greencode.ir.consulant.R;
import greencode.ir.consulant.objects.DocumentOfReq;
import greencode.ir.consulant.retrofit.respObject.Request;
import greencode.ir.consulant.utils.Utility;
import saman.zamani.persiandate.PersianDate;


public class DocumentReqAdapter extends RecyclerView.Adapter<DocumentReqAdapter.ViewHolder> {
    public List<DocumentOfReq> list;
    public onItemClick myListener;
    public Context context;
    ViewHolder viewHolder;


    public DocumentReqAdapter(List<DocumentOfReq> list, onItemClick myListener, Context context) {
        this.list = list;
        this.myListener = myListener;
        this.context = context;
    }

    public interface onItemClick {
        public void onClick(DocumentOfReq item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_docs, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DocumentOfReq data = list.get(position);
        String title = data.getDocTitle();

        title+=data.getDocSize();
        holder.txtTitle.setText(title);
        holder.txtSize.setText(data.getDocSize()+" کیلو بایت");
        holder.rlDelete.setVisibility(View.GONE);
        holder.rlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.onClick(data);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_size)
        TextView txtSize;
        @BindView(R.id.rlDelete)
        RelativeLayout rlDelete;
        @BindView(R.id.rlView)
        RelativeLayout rlView;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }
}
