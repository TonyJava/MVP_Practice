package com.example.marcus.jokecollections;

import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.marcus.jokecollections.Adapters.DisplayListAdapter;
import com.example.marcus.jokecollections.Jokes.PicJokes;
import com.example.marcus.jokecollections.Utils.ParseJsonResult;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Map<String, String> params;
    private RecyclerView displayList;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        new MyAsyncTask().execute(params);

        displayList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = 20;
            }
        });

        refresh.setColorSchemeColors(R.color.colorAccent, R.color.colorPrimary,
                R.color.cardview_light_background, R.color.cardview_shadow_start_color);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new MyAsyncTask().execute(params);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh.setRefreshing(false);
                    }
                }, 1500);
            }
        });
    }

    private void initComponents() {
        displayList = (RecyclerView) findViewById(R.id.display_list);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        params = new HashMap<>();
        params.put("key", "1a8238a73e1a9c5077995da8493e3452");
        params.put("type", "pic");
    }

    private class MyAsyncTask extends AsyncTask<Map<String, String>, Void, List<PicJokes.ResultBean>> {

        @Override
        protected final List<PicJokes.ResultBean> doInBackground(Map<String, String>... params) {
            //TODO 当没有网络连接时,程序崩溃,原因是没有 Json 数据,在 getResult() 时会产生空指针,待修复
            String jsonData = ParseJsonResult.fetchResult(params[0]);
            Gson gson = new Gson();
            PicJokes picJokes = gson.fromJson(jsonData, PicJokes.class);
            List<PicJokes.ResultBean> resultBeanList = picJokes.getResult();
            return resultBeanList;
        }

        @Override
        protected void onPostExecute(List<PicJokes.ResultBean> titleAndURL) {
            LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
            displayList.setLayoutManager(manager);
            displayList.setHasFixedSize(true);
            displayList.setAdapter(new DisplayListAdapter(titleAndURL));
        }

    }
}
