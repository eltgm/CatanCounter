package ru.sultanyarov.catancounter.models;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity(indices = {@Index(value = {"name"},
        unique = true)})
public class Player {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int color;
    private int points;

}
