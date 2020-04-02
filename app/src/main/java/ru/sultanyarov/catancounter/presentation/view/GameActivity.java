package ru.sultanyarov.catancounter.presentation.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.sultanyarov.catancounter.R;
import ru.sultanyarov.catancounter.models.Player;
import ru.sultanyarov.catancounter.models.adapters.PlayersInGameAdapter;

public class GameActivity extends MvpAppCompatActivity implements GameView {
    @BindView(R.id.toolbarGame)
    Toolbar toolbar;
    @BindView(R.id.rvPlayers)
    RecyclerView rvPlayers;

    private PlayersInGameAdapter playersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.button_back:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        openQuitDialog();
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                this);
        quitDialog.setTitle("Выход: Вы уверены? Партия не сохранится!");

        quitDialog.setPositiveButton("Да", (dialog, which) -> android.os.Process.killProcess(android.os.Process.myPid()));

        quitDialog.setNegativeButton("Нет", (dialog, which) -> dialog.cancel());

        quitDialog.show();
    }

    @OnClick(R.id.fabNextPlayer)
    public void onNextPlayerClicked() {
        playersAdapter.nextPlayer();
    }

    @OnClick(R.id.fabPrevPlayer)
    public void onPrevPlayerClicked() {
        playersAdapter.prevPlayer();
    }

    @OnClick({R.id.bMinusTwo, R.id.bMinusOne, R.id.bPlusOne, R.id.bPlusTwo})
    public void onChangePointsClicked(View button) {
        boolean isEnd = false;
        switch (button.getId()) {
            case R.id.bMinusTwo:
                isEnd = playersAdapter.changePoints(-2);
                break;
            case R.id.bMinusOne:
                isEnd = playersAdapter.changePoints(-1);
                break;
            case R.id.bPlusOne:
                isEnd = playersAdapter.changePoints(1);
                break;
            case R.id.bPlusTwo:
                isEnd = playersAdapter.changePoints(2);
                break;
        }

        if (isEnd) {
            final Player winner = playersAdapter.getWinner();
            Toast.makeText(this, "Победа за " + winner.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        ButterKnife.bind(this);
        final int points = getIntent().getIntExtra("points", 0);
        toolbar.setTitle(String.format("Играем до %d", points));
        setSupportActionBar(toolbar);

        playersAdapter = new PlayersInGameAdapter(points);

        rvPlayers.setHasFixedSize(true);
        rvPlayers.setLayoutManager(new LinearLayoutManager(this));
        rvPlayers.setAdapter(playersAdapter);

        final String playersString = getIntent().getStringExtra("players");
        if (playersString != null) {
            Type itemsListType = new TypeToken<List<Player>>() {
            }.getType();
            List<Player> players = new Gson().fromJson(playersString, itemsListType);
            playersAdapter.addPlayers(players);
        }
    }
}
