package ru.sultanyarov.catancounter;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.List;

import ru.sultanyarov.catancounter.models.Player;
import ru.sultanyarov.catancounter.models.ScreenKeys;
import ru.sultanyarov.catancounter.presentation.view.EndGameFragment;
import ru.sultanyarov.catancounter.presentation.view.GameActivity;
import ru.sultanyarov.catancounter.presentation.view.MainActivity;
import ru.sultanyarov.catancounter.presentation.view.PlayerCreateFragment;
import ru.sultanyarov.catancounter.presentation.view.PlayersCreateView;
import ru.sultanyarov.catancounter.presentation.view.PlayersFragment;
import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {
    public static final class EndGameScreen extends SupportAppScreen {
        private EndGameFragment fragment;

        @Override
        public String getScreenKey() {
            return ScreenKeys.END_GAME;
        }

        @Override
        public Fragment getFragment() {
            fragment = EndGameFragment.newInstance();
            return fragment;
        }

        public int getPointsToWin() {
            return fragment.getPointsToWin();
        }
    }

    public static final class PlayersScreen extends SupportAppScreen {
        PlayersFragment fragment;

        @Override
        public String getScreenKey() {
            return ScreenKeys.PLAYERS;
        }

        @Override
        public Fragment getFragment() {
            fragment = PlayersFragment.newInstance();
            return fragment;
        }

        public List<Player> getPlayers() {
            return fragment.getPlayersString();
        }
    }

    public static final class CreatePlayerScreen extends SupportAppScreen {
        private PlayersCreateView playerCreateView;

        @Override
        public Fragment getFragment() {
            final Fragment playerCreateFragment = PlayerCreateFragment.newInstance();
            playerCreateView = (PlayersCreateView) playerCreateFragment;
            return playerCreateFragment;
        }

        @Override
        public String getScreenKey() {
            return ScreenKeys.CREATE_PLAYER;
        }

        public Player addPlayer() {
            return playerCreateView.addPlayer();
        }
    }

    public static final class MainScreen extends SupportAppScreen {
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, MainActivity.class);
        }

        @Override
        public String getScreenKey() {
            return ScreenKeys.MAIN;
        }
    }

    public static final class GameScreen extends SupportAppScreen {
        private int points;
        private String players;

        @Override
        public Intent getActivityIntent(Context context) {
            Intent intent = new Intent(context, GameActivity.class);
            intent.putExtra("points", points);
            intent.putExtra("players", players);
            return intent;
        }

        public void setPointsToWin(int points) {
            this.points = points;
        }

        public void setPlayers(String players) {
            this.players = players;
        }

        @Override
        public String getScreenKey() {
            return ScreenKeys.GAME;
        }
    }
}
