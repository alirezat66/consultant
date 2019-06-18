package greencode.ir.consulant.addinfo;

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
import greencode.ir.consulant.objects.DocumentImg;

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.ViewHolder> {
    public List<DocumentImg> list;
    public onItemClick myListener;
    public Context context;
    ViewHolder viewHolder;


    public DocumentsAdapter(List<DocumentImg> list, onItemClick myListener, Context context)
    {
        this.list = list;
        this.myListener = myListener;
        this.context = context;
    }

    public void updateList(List<DocumentImg> docList) {
        this.list = docList;
    }

    public List<DocumentImg> getItems() {
        return list;
    }

    public void update(DocumentImg selectedDoc) {
            list.set(selectedDoc.getId()-1,selectedDoc);

    }

    public List<DocumentImg> getList() {
        return list;
    }


    public interface onItemClick
    {
        public void onClick(DocumentImg item);
        public void  onDelete(DocumentImg item);
        public void onItemCLick(DocumentImg item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_document, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DocumentImg data = list.get(position);
        holder.bind(list.get(position), myListener);
        holder.txtName.setText(data.getDocTitle());

        if(data.getDocSource().equals("")){
            holder.imgView.setImageDrawable(context.getResources().getDrawable(R.drawable.deactive_view_icon));
            holder.imgDelete.setImageDrawable(context.getResources().getDrawable(R.drawable.deactive_delete_icon));
        }else {
            holder.imgView.setImageDrawable(context.getResources().getDrawable(R.drawable.view_icon));
            holder.imgDelete.setImageDrawable(context.getResources().getDrawable(R.drawable.delete_icon));

        }

            holder.imgDelete.setVisibility(View.VISIBLE);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!data.getDocSource().equals("")) {
                    myListener.onDelete(data);
                }
            }
        });
        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!data.getDocSource().equals("")) {
                    myListener.onClick(data);
                }
            }
        });
        holder.rlDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.onItemCLick(data);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.img_delete)
        ImageView imgDelete;
        @BindView(R.id.rl_doc)
        RelativeLayout rlDoc;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        public void bind(final DocumentImg item, final onItemClick listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }
    }
    public void addDoc(DocumentImg doc){
        list.add(doc);
        notifyDataSetChanged();
    }
    public void removeDoc(DocumentImg doc){
        int index = list.indexOf(doc);
        list.get(index).setDocSource("");
    }
    public void deleteDoc(DocumentImg doc){
        list.remove(doc);
        notifyDataSetChanged();
    }
}
