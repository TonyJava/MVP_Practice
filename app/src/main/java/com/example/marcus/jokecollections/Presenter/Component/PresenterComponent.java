package com.example.marcus.jokecollections.presenter.component;

import com.example.marcus.jokecollections.presenter.module.PresenterModule;
import com.example.marcus.jokecollections.view.activities.MainActivity;

import dagger.Component;

/**
 * Created by marcus on 16/7/28.
 */
@Component(modules = {PresenterModule.class})
public interface PresenterComponent {
    void inject(MainActivity mainActivity);
}
