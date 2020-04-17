package ru.sultanyarov.catancounter.repositories;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import ru.sultanyarov.catancounter.data.PlayerDao;
import ru.sultanyarov.catancounter.models.Player;

public class DatabaseDataStore implements PlayerDataStore {
    private final PlayerDao playerDao;

    @Inject
    public DatabaseDataStore(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public Observable<Player> getPlayer() {
        return playerDao.getById("").toObservable();
    }

    @Override
    public Observable<List<Player>> getPlayers() {
        return playerDao.getAll().toObservable();
    }

    @Override
    public Observable<Boolean> savePlayer(Player player) {
        return Observable.create(emitter -> {
            long insert = 0;
            try {
                insert = playerDao.insert(player);
            } catch (Exception ex) {
                emitter.onError(ex);
            }
            player.setId((int) insert);
            emitter.onNext(insert >= 0);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<Boolean> removePlayer(Player player) {
        return Observable.create(emitter -> {
            emitter.onNext(playerDao.delete(player) >= 0);
            emitter.onComplete();
        });
    }
}
