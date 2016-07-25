package com.example.marcus.jokecollections;

import com.example.marcus.jokecollections.Jokes.PicJokes;

import java.util.List;

/**
 * Created by marcus on 16/7/25.
 */
public interface MainActivityItf {
    void notifyAdapter(List<PicJokes.ResultBean> resultBeanList);

    void setFinished();
}
