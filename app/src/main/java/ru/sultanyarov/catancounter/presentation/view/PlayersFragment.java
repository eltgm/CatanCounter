package ru.sultanyarov.catancounter.presentation.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import ru.sultanyarov.catancounter.R;
import ru.sultanyarov.catancounter.Screens;
import ru.sultanyarov.catancounter.SwipeToDeleteCallback;
import ru.sultanyarov.catancounter.di.App;
import ru.sultanyarov.catancounter.models.Player;
import ru.sultanyarov.catancounter.models.PublishSubjectHolder;
import ru.sultanyarov.catancounter.models.adapters.PlayersAdapter;
import ru.terrakok.cicerone.Router;


public class PlayersFragment extends MvpAppCompatFragment implements PlayersView {
    @BindView(R.id.rvPlayers)
    RecyclerView rvPlayers;

    @Inject
    Router router;
    private Context context;
    private PlayersAdapter playersAdapter;

    public PlayersFragment() {
    }

    public static PlayersFragment newInstance() {
        PlayersFragment fragment = new PlayersFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.INSTANCE.getAppComponent().injectPlayersFragment(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_players, container, false);

        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            addPlayer(getArguments().getParcelable("player"));
        }
        initView();

        return view;
    }

    private void initView() {
        enableSwipeToDeleteAndUndo();
        if (playersAdapter == null) {
            playersAdapter = new PlayersAdapter();
            rvPlayers.setLayoutManager(new LinearLayoutManager(context));
            rvPlayers.setAdapter(playersAdapter);

            PublishSubjectHolder.getInstance().players.subscribe(new Observer<Player>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Player player) {
                    addPlayer(player);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        } else {
            rvPlayers.setLayoutManager(new LinearLayoutManager(context));
            rvPlayers.setAdapter(playersAdapter);
        }
    }

    @OnClick(R.id.fabAddPlayer)
    void onAddPlayerClicked() {
        ((AppCompatActivity) context).getSupportActionBar().setTitle("Создай игрока");
        final Screens.CreatePlayerScreen createPlayerScreen;
        createPlayerScreen = new Screens.CreatePlayerScreen();
        MainActivity.playersCreateView = createPlayerScreen;
        MainActivity.currentScreen = createPlayerScreen.getScreenKey();
        router.navigateTo(createPlayerScreen);
    }

    @Override
    public List<Player> getPlayersString() {
        return playersAdapter.getPlayers();
    }

    @Override
    public void addPlayer(Player player) {
        if (playersAdapter.addPlayer(player)) {
            Toast.makeText(context, player.getName() + " успешно добавлен!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Не удалось добавить игрока!", Toast.LENGTH_SHORT).show();
        }
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(context) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                final Player item = playersAdapter.getPlayersWithoutSelect().get(position);

                playersAdapter.removeItem(position);


                Snackbar snackbar = Snackbar
                        .make(getView(), "Игрок удалён.", Snackbar.LENGTH_LONG);
                snackbar.setAction("Отмена", view -> {

                    playersAdapter.restoreItem(item, position);
                    rvPlayers.scrollToPosition(position);
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rvPlayers);
    }
}
