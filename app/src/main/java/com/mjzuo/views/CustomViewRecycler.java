package com.mjzuo.views;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mjzuo.views.constant.Constant;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 *  首页列表recycler
 *
 * @author mjzuo
 * @since 19/11/19
 */
public class CustomViewRecycler extends RecyclerView.Adapter<CustomViewRecycler.ViewHolder> {

    ArrayList<String> cvData = new ArrayList<>();

    /** 外部的点击监听*/
    private IClickListener clickListener;

    public CustomViewRecycler(IClickListener listener) {
        this.clickListener = listener;

        if(cvData.size() != 0)
            cvData.clear();
        cvData.add("基础绘制");
        cvData.add("环形刻度");
        cvData.add("滴滴Ui");
        cvData.add("鲸云音效");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.demo_task_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String content = cvData.get(position);
        holder.tvContent.setText(content);

        holder.listener.position = position;
        holder.listener.setViewHolder(holder);
        holder.allLayout.setOnClickListener(holder.listener);
    }

    @Override
    public int getItemCount() {
        return cvData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        MyClickListener listener = new MyClickListener();

        LinearLayout allLayout;
        TextView tvContent;

        public ViewHolder(View view) {
            super(view);
            tvContent = view.findViewById(R.id.demo_task_recycler_item_content);
            allLayout = view.findViewById(R.id.all_layout);
        }
    }

    class MyClickListener implements View.OnClickListener {

        public WeakReference<ViewHolder> wrf;
        public int position;

        public void setViewHolder(ViewHolder viewHolder) {
            wrf = new WeakReference<>(viewHolder);
        }

        @Override
        public void onClick(View v) {
            if(wrf == null || wrf.get() == null){
                Log.e(Constant.TAG_LOG, "view holder or wrf null");
                return;
            }
            if(clickListener != null){
                clickListener.onClick(position);
            }
        }
    }

    interface IClickListener {
        void onClick(int itemPosition);
    }
}
