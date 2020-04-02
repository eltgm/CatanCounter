package ru.sultanyarov.catancounter.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.sultanyarov.catancounter.models.Player;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface PlayersCreateView extends MvpView {
    Player addPlayer();
}
