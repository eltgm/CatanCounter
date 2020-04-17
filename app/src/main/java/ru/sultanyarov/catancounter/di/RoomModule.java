package ru.sultanyarov.catancounter.di;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.sultanyarov.catancounter.data.AppDatabase;
import ru.sultanyarov.catancounter.data.PlayerDao;

@Module
public class RoomModule {
    private AppDatabase appDatabase;

    public RoomModule(Context context) {
        this.appDatabase = Room.databaseBuilder(context, AppDatabase.class, "catanCounter").build();
    }

    @Singleton
    @Provides
    AppDatabase providesRoomDatabase() {
        return appDatabase;
    }

    @Singleton
    @Provides
    PlayerDao providesCallerDao(AppDatabase demoDatabase) {
        return demoDatabase.playerDao();
    }
}
