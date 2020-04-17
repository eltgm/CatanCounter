package ru.sultanyarov.catancounter.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.sultanyarov.catancounter.data.PlayerDao;
import ru.sultanyarov.catancounter.repositories.DatabaseDataStore;
import ru.sultanyarov.catancounter.repositories.PlayerDataStore;

@Module(includes = {RoomModule.class})
class DataModule {

    @Provides
    @Singleton
    @Named("DatabaseDataStore")
    PlayerDataStore provideDatabaseDataStore(PlayerDao playerDao) {
        return new DatabaseDataStore(playerDao);
    }

}
