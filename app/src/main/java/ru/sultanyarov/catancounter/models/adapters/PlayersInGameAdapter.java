package ru.sultanyarov.catancounter.models.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.sultanyarov.catancounter.R;
import ru.sultanyarov.catancounter.models.Player;

public class PlayersInGameAdapter extends RecyclerView.Adapter<PlayersInGameAdapter.ViewHolder> {
    private final int pointsToWin;
    private final boolean[] winingCandidate = new boolean[6];
    private List<Player> players;
    private Context context;
    private int selectedPlayer = 0;

    public PlayersInGameAdapter(int pointsToWin) {
        this.pointsToWin = pointsToWin;
    }

    public void addPlayers(List<Player> players) {
        notifyDataSetChanged();
        this.players = players;
    }

    public Player getWinner() {
        return players.get(selectedPlayer);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_player, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(context, players.get(position));
        if (selectedPlayer == position)
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryHighlight));
        else
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorSecondaryHighlight));

        if (winingCandidate[position]) {
            holder.tvPlayerPoints.setTextColor(context.getResources().getColor(R.color.red));
        } else {
            holder.tvPlayerPoints.setTextColor(context.getResources().getColor(R.color.colorText));
        }
    }

    public void nextPlayer() {
        if (selectedPlayer < players.size() - 1) {
            selectedPlayer++;
        } else {
            selectedPlayer = 0;
        }
        notifyDataSetChanged();
    }

    public boolean changePoints(int count) {
        final Player player = players.get(selectedPlayer);
        int points = player.getPoints() + count;
        if (points < 2) {
            points = 2;
        }
        winingCandidate[selectedPlayer] = points >= pointsToWin - 3;

        player.setPoints(points);
        notifyItemChanged(selectedPlayer);

        return points >= pointsToWin;
    }

    public void prevPlayer() {
        if (selectedPlayer == 0) {
            selectedPlayer = players.size() - 1;
        } else {
            selectedPlayer--;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.buttonColor)
        Button buttonColor;
        @BindView(R.id.tvPlayerName)
        TextView tvPlayerName;
        @BindView(R.id.tvPlayerPoints)
        TextView tvPlayerPoints;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Context context, Player player) {
            tvPlayerName.setText(player.getName());
            buttonColor.setBackground(new ColorDrawable(ContextCompat.getColor(context, player.getColor())));
            tvPlayerPoints.setText(String.valueOf(player.getPoints()));
        }
    }
}
