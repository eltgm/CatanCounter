package ru.sultanyarov.catancounter.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Module
class NavigationModule {
    private final Cicerone<Router> cicerone;

    public NavigationModule() {
        this.cicerone = Cicerone.create();
    }

    @Singleton
    @Provides
    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    @Singleton
    @Provides
    public Router getRouter() {
        return cicerone.getRouter();
    }
}
