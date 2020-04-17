package ru.sultanyarov.catancounter.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import ru.sultanyarov.catancounter.models.Player;

@Dao
public interface PlayerDao {
    @Query("SELECT * FROM player")
    Flowable<List<Player>> getAll();

    @Query("SELECT * FROM player WHERE name = :name")
    Maybe<Player> getById(String name);

    @Insert
    long insert(Player player);

    @Delete
    int delete(Player player);
}
