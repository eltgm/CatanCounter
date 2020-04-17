package ru.sultanyarov.catancounter.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import ru.sultanyarov.catancounter.di.App;
import ru.sultanyarov.catancounter.models.Player;
import ru.sultanyarov.catancounter.presentation.view.PlayersView;
import ru.sultanyarov.catancounter.repositories.PlayersRepository;

@InjectViewState
public class PlayersPresenter extends BasePresenter<PlayersView> {
    private final CompositeDisposable disposables = new CompositeDisposable();
    @Inject
    PlayersRepository playersRepository;

    public PlayersPresenter() {
        App.INSTANCE.getAppComponent().injectPlayerPresenter(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getPlayers();
    }

    public void addPlayer(Player player) {
        addDisposable(playersRepository.addPlayer(player)
                .subscribeWith(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            //getViewState().addPlayerToScreen(player);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    public void removePlayer(Player player) {
        addDisposable(playersRepository.removePlayer(player)
                .subscribeWith(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    private void getPlayers() {
        addDisposable(playersRepository.getPlayers()
                .subscribeWith(new DisposableObserver<List<Player>>() {
                    @Override
                    public void onNext(List<Player> players) {
                        getViewState().addPlayers(players);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    protected void disconnect() {
        if (!disposables.isDisposed())
            disposables.clear();
    }

    private void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }
}
