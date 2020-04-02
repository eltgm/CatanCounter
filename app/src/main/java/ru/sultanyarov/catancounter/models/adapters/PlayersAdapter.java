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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.sultanyarov.catancounter.R;
import ru.sultanyarov.catancounter.models.Player;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {
    private final List<Player> players = new ArrayList<>(6);
    private final List<Integer> playersOrder = new ArrayList<>(6);
    private final boolean[] selectedPosition = new boolean[6];
    private Context context;

    public boolean addPlayer(Player player) {
        if (players.size() <= 5) {
            notifyDataSetChanged();
            return players.add(player);
        }

        return false;
    }

    public List<Player> getPlayers() {
        final List<Player> playersOrderedList = new ArrayList<>(players.size());
        for (Integer pos :
                playersOrder) {
            playersOrderedList.add(players.get(pos));
        }
        return playersOrderedList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_player_add, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(context, players.get(position));
        if (selectedPosition[position])
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryHighlight));
        else
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorSecondaryHighlight));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public void removeItem(int position) {
        players.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Player item, int position) {
        players.add(position, item);
        notifyItemInserted(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final View itemView;
        @BindView(R.id.buttonColorPick)
        Button buttonColor;
        @BindView(R.id.tvPlayerName)
        TextView tvPlayerName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemView.setOnClickListener(v -> {
                if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

                selectedPosition[getAdapterPosition()] = !selectedPosition[getAdapterPosition()];
                if (selectedPosition[getAdapterPosition()]) {
                    playersOrder.add(getAdapterPosition());
                } else {
                    playersOrder.remove(new Integer(getAdapterPosition()));
                }
                notifyDataSetChanged();
            });
            ButterKnife.bind(this, itemView);
        }

        void bind(Context context, Player player) {
            tvPlayerName.setText(player.getName());
            buttonColor.setBackground(new ColorDrawable(ContextCompat.getColor(context, player.getColor())));
        }
    }
}
