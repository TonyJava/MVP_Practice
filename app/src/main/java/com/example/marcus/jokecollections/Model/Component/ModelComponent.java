package com.example.marcus.jokecollections.model.component;

import com.example.marcus.jokecollections.model.module.ModelModule;
import com.example.marcus.jokecollections.presenter.IPresenterImp;

import dagger.Component;

/**
 * Created by marcus on 16/7/28.
 */
@Component(modules = {ModelModule.class})
public interface ModelComponent {
    void inject(IPresenterImp presenterImp);
}
