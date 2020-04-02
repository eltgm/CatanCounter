package ru.sultanyarov.catancounter.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.sultanyarov.catancounter.R;


public class EndGameFragment extends MvpAppCompatFragment implements EndGameView {
    @BindView(R.id.etPointsToWin)
    EditText etPointsToWin;

    public EndGameFragment() {
    }

    public static EndGameFragment newInstance() {
        EndGameFragment fragment = new EndGameFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_end_game, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public int getPointsToWin() {
        final String pointsString = etPointsToWin.getText().toString();

        if (pointsString.isEmpty() || pointsString.length() < 2) {
            etPointsToWin.requestFocus();
            etPointsToWin.setError("Введите количество очков!");

            return 0;
        }

        return Integer.parseInt(pointsString);
    }
}
