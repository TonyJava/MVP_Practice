package com.example.marcus.jokecollections.presenter;

import com.example.marcus.jokecollections.model.component.DaggerModelComponent;
import com.example.marcus.jokecollections.data.PicJokes;
import com.example.marcus.jokecollections.model.ModelImp;
import com.example.marcus.jokecollections.model.module.ModelModule;
import com.example.marcus.jokecollections.view.activities.MainActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by marcus on 16/7/25.
 */
public class IPresenterImp implements IPresenter {
    //中介人必须认识双方
    private MainActivity mainActivity;
    @Inject
    ModelImp modelImp;

    @Inject
    public IPresenterImp(MainActivity mainActivity) {
        //持有对 View 的引用
        this.mainActivity = mainActivity;
        //在 IPresenterImp 中实例化 Model
        DaggerModelComponent.builder()
                .modelModule(new ModelModule())
                .build()
                .inject(this);
    }

    @Override
    public void loadData() {
        modelImp.solveData(this);
    }

    @Override
    public void refreshFinished() {
        mainActivity.setFinished();
    }

    @Override
    public void returnData(List<PicJokes.ResultBean> resultBeanList) {
        mainActivity.notifyAdapter(resultBeanList);
    }
}
