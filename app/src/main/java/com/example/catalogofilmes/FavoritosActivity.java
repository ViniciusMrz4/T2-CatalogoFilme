package com.example.catalogofilmes;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.*;
import java.util.ArrayList;
public class FavoritosActivity extends AppCompatActivity {
    ListView listViewFavoritos;
    ArrayList<Filme> listaFavoritos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        listViewFavoritos = findViewById(R.id.listViewFavoritos);
        listaFavoritos = (ArrayList<Filme>) getIntent().getSerializableExtra("favoritos");
        if (listaFavoritos == null || listaFavoritos.isEmpty()) {
            Toast.makeText(this, "Nenhum favorito ainda!", Toast.LENGTH_SHORT).show();
            listaFavoritos = new ArrayList<>();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                getTitulos(listaFavoritos)
        );
        listViewFavoritos.setAdapter(adapter);
    }
    private ArrayList<String> getTitulos(ArrayList<Filme> filmes) {
        ArrayList<String> titulos = new ArrayList<>();
        for (Filme f : filmes) {
            titulos.add(f.getTitulo());
        }
        return titulos;
    }
}