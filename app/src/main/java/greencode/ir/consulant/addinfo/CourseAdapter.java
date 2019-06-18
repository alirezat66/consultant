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
import greencode.ir.consulant.objects.Course;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    public List<Course> list;
    public onItemClick myListener;
    public Context context;
    ViewHolder viewHolder;


    public CourseAdapter(List<Course> list, onItemClick myListener, Context context) {
        this.list = list;
        this.myListener = myListener;
        this.context = context;
    }

    public List<Course> getitems() {
        return list;
    }

    public void updateList(List<Course> docList) {
        list = docList;
    }

    public void deleteItem(Course item) {
        list.remove(item);
    }

    public interface onItemClick {
        public void onClick(Course item);
        public void onDelete(Course item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_courses, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Course data = list.get(position);
        holder.bind(list.get(position), myListener);
        holder.txtTitle.setText(data.getTitle());
        holder.txtDescription.setText(data.getDesc());
        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.onClick(data);

            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.onDelete(data);
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
        @BindView(R.id.txt_description)
        TextView txtDescription;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.img_delete)
        ImageView imgDelete;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final Course item, final onItemClick listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }
    }
}
