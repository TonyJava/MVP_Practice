package com.example.marcus.jokecollections.Model.Component;

import com.example.marcus.jokecollections.Model.Module.ModelModule;
import com.example.marcus.jokecollections.Presenter.IPresenterImp;

import dagger.Component;

/**
 * Created by marcus on 16/7/28.
 */
@Component(modules = {ModelModule.class})
public interface ModelComponent {
    void inject(IPresenterImp presenterImp);
}
