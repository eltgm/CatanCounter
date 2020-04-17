package ru.sultanyarov.catancounter.repositories;

import java.util.List;

import io.reactivex.Observable;
import ru.sultanyarov.catancounter.models.Player;

public interface PlayerDataStore {
    Observable<Player> getPlayer();

    Observable<List<Player>> getPlayers();

    Observable<Boolean> savePlayer(Player caller);

    Observable<Boolean> removePlayer(Player caller);
}
