package com.example.marcus.jokecollections;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.marcus.jokecollections.Adapters.DisplayListAdapter;
import com.example.marcus.jokecollections.Jokes.PicJokes;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Activity 作为 View 层，只负责展示界面，即组件的初始化以及其他需要在 UIThread 上
 * 进行的操作，代码中可以看出这里数据的拉取是通过中介人即 Presenter 的 loadData 方法，
 * 而此 loadData 又通过 Model 的 solveData 来处理数据，因为 Model 中持有对 Presenter
 * 的一个引用，所以可以在处理完数据后调用 Presenter 中的 returnData 方法来通知 Activity 进行
 * 适配器的更新操作，从而达到了 View 和 Model 老死不相往来的目的，实现了清晰的分层效果
 **/

@ContentView(R.layout.activity_main)
public class MainActivity extends Activity implements MainActivityItf, SwipeRefreshLayout.OnRefreshListener {
    //总得认识中介人吧？
    private PresenterItf presenterItf;

    private RecyclerView displayList;
    private SwipeRefreshLayout refresh;
    private DisplayListAdapter adapter;

    private List<PicJokes.ResultBean> picJokes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //init;
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        x.Ext.init(getApplication());
        presenterItf = new PresenterImp(this);
        displayList = (RecyclerView) findViewById(R.id.display_list);
        initAdapterStyle();
        adapter = new DisplayListAdapter(picJokes);
        displayList.setAdapter(adapter);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setOnRefreshListener(this);

        //load data;
        presenterItf.loadData();

    }

    public void initAdapterStyle() {
        displayList.setHasFixedSize(true);
        displayList.setLayoutManager(new LinearLayoutManager(this));
        displayList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, 40);
            }
        });
    }

    @Override
    public void notifyAdapter(List<PicJokes.ResultBean> resultBeanList) {
        picJokes.addAll(resultBeanList);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onRefresh() {
        if (picJokes != null) {
            picJokes.clear();
        }
        presenterItf.loadData();
    }


    @Override
    public void setFinished() {
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
        }
    }
}
