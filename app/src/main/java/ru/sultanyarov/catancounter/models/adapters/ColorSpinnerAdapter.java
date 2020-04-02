package ru.sultanyarov.catancounter.models.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import ru.sultanyarov.catancounter.R;

public class ColorSpinnerAdapter extends ArrayAdapter<Integer> {
    private final Integer[] colors;
    private final Context context;

    public ColorSpinnerAdapter(@NonNull Context context, @NonNull Integer[] objects) {
        super(context, R.layout.item_spinner_color, objects);
        this.colors = objects;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {

        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView,
                               ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_spinner_color, null);
        }

        Button color = convertView.findViewById(R.id.buttonColor);

        color.setBackground(new ColorDrawable(ContextCompat.getColor(context, colors[position])));
        return convertView;
    }
}
