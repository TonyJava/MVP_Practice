package com.example.marcus.jokecollections.View;

import com.example.marcus.jokecollections.Model.Data.PicJokes;

import java.util.List;

/**
 * Created by marcus on 16/7/25.
 */
public interface IView {
    void notifyAdapter(List<PicJokes.ResultBean> resultBeanList);

    void setFinished();
}
