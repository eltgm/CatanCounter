package ru.sultanyarov.catancounter.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.sultanyarov.catancounter.presentation.presenter.PlayersPresenter;
import ru.sultanyarov.catancounter.presentation.view.GameActivity;
import ru.sultanyarov.catancounter.presentation.view.MainActivity;
import ru.sultanyarov.catancounter.presentation.view.PlayerCreateFragment;
import ru.sultanyarov.catancounter.presentation.view.PlayersFragment;

@Singleton
@Component(modules = {NavigationModule.class, DataModule.class})
public interface AppComponent {
    void injectMainActivity(MainActivity mainActivity);

    void injectPlayersFragment(PlayersFragment playersFragment);

    void injectCreatePlayerFragment(PlayerCreateFragment playerCreateFragment);

    void injectGameActivity(GameActivity gameActivity);

    void injectPlayerPresenter(PlayersPresenter playersPresenter);
}
