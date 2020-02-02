package com.fightyz.openglbasicshape;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fightyz.openglbasicshape.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RendererActivity extends AppCompatActivity {
    @BindView(R.id.rvRenderer)
    RecyclerView rvRenderer;

    private List<String> mRenderList;
    private RendererAdapter mRendererAdapter;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renderer);
        ButterKnife.bind(this);

        mContext = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRendererAdapter = new RendererAdapter(this, getRenderList());
        mRendererAdapter.setListener(new RendererAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra(Constant.RENDERER_TYPE, position);
                startActivity(intent);
            }
        });
        rvRenderer.setLayoutManager(layoutManager);
        rvRenderer.setAdapter(mRendererAdapter);
    }

    private List<String> getRenderList() {
        mRenderList = new ArrayList<>();
        mRenderList.add("Point Render");
        mRenderList.add("Line Render");
        mRenderList.add("Triangle Render");
        mRenderList.add("Rectangle Render");
        mRenderList.add("Circle Render");
        return mRenderList;
    }
}
