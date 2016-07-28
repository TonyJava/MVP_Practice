package com.example.marcus.jokecollections.View.Activities;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.marcus.jokecollections.Model.Data.PicJokes;
import com.example.marcus.jokecollections.Presenter.Component.DaggerPresenterComponent;
import com.example.marcus.jokecollections.Presenter.IPresenterImp;
import com.example.marcus.jokecollections.Presenter.Module.PresenterModule;
import com.example.marcus.jokecollections.R;
import com.example.marcus.jokecollections.View.Adapters.DisplayListAdapter;
import com.example.marcus.jokecollections.View.IView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * Activity 作为 View 层，只负责展示界面，即组件的初始化以及其他需要在 UIThread 上
 * 进行的操作，代码中可以看出这里数据的拉取是通过中介人即 IPresenterImp 的 loadData 方法，
 * 而此 loadData 又通过 Model 的 solveData 来处理数据，因为 Model 中持有对 IPresenterImp
 * 的一个引用，所以可以在处理完数据后调用 IPresenterImp 中的 returnData 方法来通知 Activity 进行
 * 适配器的更新操作，从而达到了 View 和 Model 老死不相往来的目的，实现了清晰的分层效果
 **/

@ContentView(R.layout.activity_main)
public class MainActivity extends Activity implements IView, SwipeRefreshLayout.OnRefreshListener {
    //总得认识中介人吧？
    @Inject
    IPresenterImp presenterImp;

    @ViewInject(R.id.display_list)
    private RecyclerView displayList;
    @ViewInject(R.id.refresh)
    private SwipeRefreshLayout refresh;

    private DisplayListAdapter adapter;

    private List<PicJokes.ResultBean> picJokes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化 xutils
        x.view().inject(this);
        x.Ext.init(getApplication());

        //presenterItf = new IPresenterImp(this);
        //使用以下方式实例化
        DaggerPresenterComponent.builder()
                .presenterModule(new PresenterModule(this))
                .build()
                .inject(this);

        adapter = new DisplayListAdapter(picJokes);
        initAdapterStyle();
        displayList.setAdapter(adapter);
        refresh.setOnRefreshListener(this);
        //load data;
        presenterImp.loadData();
    }

    //RecyclerView 的 item 间距
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

    //更新 RecyclerView
    @Override
    public void notifyAdapter(List<PicJokes.ResultBean> resultBeanList) {
        //接受到数据后通知 adapter 数据发生了变化，应该重新计算 getItemCount 中的数量，从而正确的显示数据。
        picJokes.addAll(resultBeanList);
        adapter.notifyDataSetChanged();
    }


    //下拉刷新
    @Override
    public void onRefresh() {
        if (picJokes != null) {
            picJokes.clear();
        }
        presenterImp.loadData();
    }

    //数据接收结束关闭刷新动画
    @Override
    public void setFinished() {
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
        }
    }
}
