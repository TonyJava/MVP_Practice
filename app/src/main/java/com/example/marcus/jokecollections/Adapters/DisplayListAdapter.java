package com.example.marcus.jokecollections.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marcus.jokecollections.Jokes.PicJokes;
import com.example.marcus.jokecollections.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by marcus on 16/6/10.
 */
public class DisplayListAdapter extends RecyclerView.Adapter<DisplayListAdapter.MyViewHolder> {
    List<PicJokes.ResultBean> resultBeenList;
    private Context context;

    public DisplayListAdapter(List<PicJokes.ResultBean> resultBeanList) {
        this.resultBeenList = resultBeanList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.display_list_items, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(resultBeenList.get(position).getContent());
        Picasso.with(context)
                .load(resultBeenList.get(position).getUrl())
                .into(holder.jokePic);
    }

    @Override
    public int getItemCount() {
        return resultBeenList == null ? 0 : resultBeenList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView title;
        private ImageView jokePic;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            title = (TextView) itemView.findViewById(R.id.list_title);
            jokePic = (ImageView) itemView.findViewById(R.id.list_pic);
        }
    }
}
