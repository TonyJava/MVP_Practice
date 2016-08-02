package com.example.marcus.jokecollections.presenter.module;

import com.example.marcus.jokecollections.view.activities.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by marcus on 16/7/28.
 */
@Module
public class PresenterModule {
    private MainActivity mainActivity;

    public PresenterModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Provides
    MainActivity provideMainActivity() {
        return mainActivity;
    }
}
