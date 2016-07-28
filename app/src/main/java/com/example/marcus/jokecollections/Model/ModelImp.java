package com.example.marcus.jokecollections.Model;

import android.util.Log;

import com.example.marcus.jokecollections.Presenter.IPresenter;
import com.example.marcus.jokecollections.Model.Data.PicJokes;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by marcus on 16/7/25.
 */
public class ModelImp implements IModel {
    private final String TAG = "ModelImp.class";
    private List<PicJokes.ResultBean> picJokes;

    @Inject
    public ModelImp() {}

    @Override
    public void solveData(final IPresenter IPresenter) {
        Map<String, String> params = new HashMap<>();
        params.put("key", "1a8238a73e1a9c5077995da8493e3452");
        params.put("type", "pic");
        RequestParams requestParams = new RequestParams("http://v.juhe.cn/joke/randJoke.php");
        for (Map.Entry p : params.entrySet()) {
            String key = (String) p.getKey();
            Object val = p.getValue();
            requestParams.addParameter(key, val);
        }
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "onSuccess: " + result);
                Gson gson = new Gson();
                PicJokes jokes = gson.fromJson(result, PicJokes.class);
                picJokes = jokes.getResult();
                //通过 IPresenterImp 来返回数据到 View，并通知 View 做出更改
                IPresenter.returnData(picJokes);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG, "onError: " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                //同上注释
                IPresenter.refreshFinished();
            }
        });
    }
}
