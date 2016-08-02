package com.example.marcus.jokecollections.model.module;

import com.example.marcus.jokecollections.model.ModelImp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by marcus on 16/7/28.
 */
@Module
public class ModelModule {
    @Provides
    ModelImp provideModel() {
        return new ModelImp();
    }
}
