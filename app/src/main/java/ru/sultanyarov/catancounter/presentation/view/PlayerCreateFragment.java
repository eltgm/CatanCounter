package ru.sultanyarov.catancounter.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.arellomobile.mvp.MvpAppCompatFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import ru.sultanyarov.catancounter.R;
import ru.sultanyarov.catancounter.di.App;
import ru.sultanyarov.catancounter.models.Player;
import ru.sultanyarov.catancounter.models.adapters.ColorSpinnerAdapter;
import ru.terrakok.cicerone.Router;


public class PlayerCreateFragment extends MvpAppCompatFragment implements PlayersCreateView {
    private final Integer[] colorsArray = {R.color.red, R.color.white, R.color.colorPrimaryDark, R.color.yellow,
            R.color.green, R.color.brown};
    @BindView(R.id.spinnerColor)
    Spinner colors;
    @BindView(R.id.etPlayerName)
    EditText etPlayerName;
    @Inject
    Router router;
    private int chosenColor;

    public PlayerCreateFragment() {
    }

    public static PlayerCreateFragment newInstance() {
        PlayerCreateFragment fragment = new PlayerCreateFragment();
        Bundle args = new Bundle();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.INSTANCE.getAppComponent().injectCreatePlayerFragment(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_player_create, container, false);

        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        ColorSpinnerAdapter colorSpinnerAdapter = new ColorSpinnerAdapter(getContext(), colorsArray);
        colors.setAdapter(colorSpinnerAdapter);
    }

    @Override
    public Player addPlayer() {
        final String name = etPlayerName.getText().toString();
        if (!name.isEmpty())
            return Player.builder()
                    .color(chosenColor)
                    .name(name)
                    .points(2)
                    .build();
        else {
            etPlayerName.requestFocus();
            etPlayerName.setError("Введите имя!");

            return null;
        }
    }

    @OnItemSelected(R.id.spinnerColor)
    void onItemSelected(int position) {
        chosenColor = colorsArray[position];
    }
}
