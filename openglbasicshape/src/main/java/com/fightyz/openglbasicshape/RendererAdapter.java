package com.fightyz.openglbasicshape;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import timber.log.Timber;

public class RendererAdapter extends RecyclerView.Adapter<RendererAdapter.RendererViewHolder> {
    private Context mContext;
    private List<String> mRendererList;
    private onItemClickListener mListener;

    public RendererAdapter(Context context, List<String> rendererList) {
        this.mContext = context;
        this.mRendererList = rendererList;
    }

    @NonNull
    @Override
    public RendererViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Timber.d("onCreateViewHolder");
        return new RendererViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.renderder_item_layout, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RendererViewHolder holder, final int position) {
        Timber.d("position " + position + mRendererList.get(position));
        holder.textView.setText(mRendererList.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRendererList.size();
    }

    public class RendererViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public RendererViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvRenderer);
        }

    }

    public void setListener(onItemClickListener listener) {
        mListener = listener;
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }
}
