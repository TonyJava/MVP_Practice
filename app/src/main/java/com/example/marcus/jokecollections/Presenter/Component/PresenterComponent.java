package com.example.marcus.jokecollections.Presenter.Component;

import com.example.marcus.jokecollections.Presenter.Module.PresenterModule;
import com.example.marcus.jokecollections.View.Activities.MainActivity;

import dagger.Component;

/**
 * Created by marcus on 16/7/28.
 */
@Component(modules = {PresenterModule.class})
public interface PresenterComponent {
    void inject(MainActivity mainActivity);
}
