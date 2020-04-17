package ru.sultanyarov.catancounter.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.sultanyarov.catancounter.models.Player;

@Database(entities = {Player.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlayerDao playerDao();

}
