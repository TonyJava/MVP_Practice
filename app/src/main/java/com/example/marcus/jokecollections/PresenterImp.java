package com.example.marcus.jokecollections;

import com.example.marcus.jokecollections.Jokes.PicJokes;

import java.util.List;

/**
 * Created by marcus on 16/7/25.
 */
public class PresenterImp implements PresenterItf {

    private MainActivityItf mainActivityItf;
    private ModelItf modelItf;

    public PresenterImp(MainActivityItf mainActivityItf) {
        this.mainActivityItf = mainActivityItf;
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
