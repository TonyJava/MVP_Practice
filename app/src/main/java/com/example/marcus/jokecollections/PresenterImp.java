package com.example.marcus.jokecollections;

import com.example.marcus.jokecollections.Jokes.PicJokes;

import java.util.List;

/**
 * Created by marcus on 16/7/25.
 */
public class PresenterImp implements PresenterItf {
    //中介人必须认识双方
    private MainActivityItf mainActivityItf;
    private ModelItf modelItf;


    public PresenterImp(MainActivityItf mainActivityItf) {
        //持有对 View 的引用
        this.mainActivityItf = mainActivityItf;
        //View 不认识 Model，所以这里的实例化由 Presenter 完成
        this.modelItf = new ModelImp();
    }

    @Override
    public void loadData() {
        modelItf.solveData(this);
    }

    @Override
    public void refreshFinished() {
        mainActivityItf.setFinished();
    }

    @Override
    public void returnData(List<PicJokes.ResultBean> resultBeanList) {
        mainActivityItf.notifyAdapter(resultBeanList);
    }
}
