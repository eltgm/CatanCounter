package ru.sultanyarov.catancounter.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.sultanyarov.catancounter.models.Player;

@StateStrategyType(OneExecutionStateStrategy.class)
public
interface PlayersView extends MvpView {
    void addPlayer(Player player);

    void addPlayers(List<Player> players);

    void addPlayerToScreen(Player player);
}
