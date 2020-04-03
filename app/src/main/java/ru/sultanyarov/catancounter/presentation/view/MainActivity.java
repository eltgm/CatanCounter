package ru.sultanyarov.catancounter.presentation.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import ru.sultanyarov.catancounter.R;
import ru.sultanyarov.catancounter.Screens;
import ru.sultanyarov.catancounter.di.App;
import ru.sultanyarov.catancounter.models.Player;
import ru.sultanyarov.catancounter.models.PublishSubjectHolder;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;

import static ru.sultanyarov.catancounter.models.ScreenKeys.CREATE_PLAYER;
import static ru.sultanyarov.catancounter.models.ScreenKeys.END_GAME;
import static ru.sultanyarov.catancounter.models.ScreenKeys.PLAYERS;

public class MainActivity extends MvpAppCompatActivity {
    public static String currentScreen;
    public static Screens.CreatePlayerScreen playersCreateView;
    private final Navigator navigator = new SupportAppNavigator(this,
            R.id.nav_host_fragment) {
        @Override
        public void applyCommands(@NonNull Command[] commands) {
            super.applyCommands(commands);
            getSupportFragmentManager().executePendingTransactions();
        }
    };
    @Inject
    Router router;
    @Inject
    NavigatorHolder navigatorHolder;
    private Screens.EndGameScreen endGameScreen;
    private Screens.PlayersScreen playersScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.INSTANCE.getAppComponent().injectMainActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_init);

        initView();
    }

    private void initView() {
        final Toolbar toolbar = findViewById(R.id.toolbarInit);
        toolbar.setTitle("Конец игры");
        setSupportActionBar(toolbar);

        startEndGameCounterFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_init_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.button_back:
                navigateOnFragmentsBack();
                return true;
            case R.id.button_next:
                navigateOnFragmentsForward();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void navigateOnFragmentsBack() {
        switch (currentScreen) {
            case END_GAME:
                finishAffinity();
                break;
            case PLAYERS:
                getSupportActionBar().setTitle("Конец игры");
                currentScreen = END_GAME;
                router.exit();
                break;
            case CREATE_PLAYER:
                getSupportActionBar().setTitle("Добавь/Выбери игроков");
                currentScreen = PLAYERS;
                router.exit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        navigateOnFragmentsBack();
    }

    private void navigateOnFragmentsForward() {
        switch (currentScreen) {
            case END_GAME:
                if (endGameScreen != null) {
                    final int pointsToWin = endGameScreen.getPointsToWin();
                    if (pointsToWin >= 10 && pointsToWin <= 20) {
                        getSupportActionBar().setTitle("Добавь/Выбери игроков");
                        playersScreen = new Screens.PlayersScreen();
                        currentScreen = playersScreen.getScreenKey();
                        router.navigateTo(playersScreen);
                    }
                    break;
                }
            case PLAYERS:
                final List<Player> players = playersScreen.getPlayers();
                if (players.size() >= 2 && players.size() <= 6) {
                    Screens.GameScreen gameScreen = new Screens.GameScreen();
                    gameScreen.setPointsToWin(endGameScreen.getPointsToWin());
                    gameScreen.setPlayers(new Gson().toJson(players));
                    router.navigateTo(gameScreen);
                } else {
                    Toast.makeText(this, "Для игры необходимо от 2 до 6 игроков", Toast.LENGTH_SHORT).show();
                }
                break;
            case CREATE_PLAYER:
                final Player player = playersCreateView.addPlayer();
                if (player != null) {
                    PublishSubjectHolder.getInstance().players.onNext(player);
                    getSupportActionBar().setTitle("Добавь/Выбери игроков");
                    currentScreen = PLAYERS;
                    router.exit();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

    private void startEndGameCounterFragment() {
        endGameScreen = new Screens.EndGameScreen();
        currentScreen = endGameScreen.getScreenKey();
        router.navigateTo(endGameScreen);
    }
}
