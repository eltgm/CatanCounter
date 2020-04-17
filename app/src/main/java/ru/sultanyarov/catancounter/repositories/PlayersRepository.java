package ru.sultanyarov.catancounter.repositories;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.sultanyarov.catancounter.models.Player;

public class PlayersRepository {
    private final PlayerDataStore databaseDataStore;

    @Inject
    public PlayersRepository(DatabaseDataStore databaseDataStore) {
        this.databaseDataStore = databaseDataStore;
    }

    public Observable<List<Player>> getPlayers() {
        return databaseDataStore.getPlayers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> addPlayer(Player player) {
        return databaseDataStore.savePlayer(player)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> removePlayer(Player player) {
        return databaseDataStore.removePlayer(player)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
