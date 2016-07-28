package com.example.marcus.jokecollections.Presenter;

import com.example.marcus.jokecollections.Model.Data.PicJokes;

import java.util.List;

/**
 * Created by marcus on 16/7/25.
 */
public interface IPresenter {
    void returnData(List<PicJokes.ResultBean> resultBeanList);

    void loadData();

    void refreshFinished();
}
