package greencode.ir.consulant.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import greencode.ir.consulant.R;
import greencode.ir.consulant.dialog.MultiChoserInterface;
import greencode.ir.consulant.objects.MultiChoseObject;
import greencode.ir.consulant.objects.SingleChoseObject;


public class MultiChoseAdapter extends RecyclerView.Adapter<MultiChoseAdapter.ViewHolder> {
    public List<MultiChoseObject> list;
    public Context context;
    ViewHolder viewHolder;
    public onItemClick myListener;

    public void changeChecked(MultiChoseObject item) {
        list.get(list.indexOf(item)).setChecked(!item.isChecked());
    }

    public List<MultiChoseObject> getAllItems() {
        List<MultiChoseObject>  selected = new ArrayList<>();
        for(MultiChoseObject object : list){
            if(object.isChecked()){
                selected.add(object);
            }
        }
        return selected;
    }

    public interface onItemClick {
        public void onClick(MultiChoseObject item);
    }

    public MultiChoseAdapter(List<MultiChoseObject> list, Context context, onItemClick myListener) {
        this.list = list;
        this.context = context;
        this.myListener = myListener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_select, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MultiChoseObject data = list.get(position);
        holder.itemTitle.setText(data.getTitle());
        if(data.isChecked()){
            holder.itemTitle.setTextColor(context.getResources().getColor(R.color.blueBack));
        }else {
            holder.itemTitle.setTextColor(context.getResources().getColor(R.color.gray_key));
        }
        holder.bind(data,myListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_item_title)
        TextView itemTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final MultiChoseObject item, final onItemClick listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }
    }
}
