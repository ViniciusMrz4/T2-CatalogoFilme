package com.example.catalogofilmes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    EditText edtTitulo;
    Button btnBuscar, btnFavoritar;
    ImageView imgPoster;
    TextView txtSinopse;
    ArrayList<Filme> favoritos = new ArrayList<>();
    String tituloAtual = "", sinopseAtual = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtTitulo = findViewById(R.id.edtTitulo);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnFavoritar = findViewById(R.id.btnFavoritar);
        imgPoster = findViewById(R.id.imgPoster);
        txtSinopse = findViewById(R.id.txtSinopse);
        btnBuscar.setOnClickListener(v -> buscarFilme());
        btnFavoritar.setOnClickListener(v -> adicionarFavorito());
    }
    private void buscarFilme() {
        String titulo = edtTitulo.getText().toString().trim();
        if (titulo.isEmpty()) {
            Toast.makeText(this, "Digite um título primeiro!", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            try {
                String urlStr = "https://www.omdbapi.com/?t=" + URLEncoder.encode(titulo, "UTF-8") + "&apikey=564727fa";
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String linha;
                while ((linha = br.readLine()) != null) sb.append(linha);
                JSONObject json = new JSONObject(sb.toString());
                if (json.getString("Response").equals("True")) {
                    tituloAtual = json.optString("Title");
                    sinopseAtual = json.optString("Plot");
                    String posterUrl = json.optString("Poster");
                    runOnUiThread(() -> {
                        txtSinopse.setText(sinopseAtual);
                        Picasso.get().load(posterUrl).into(imgPoster);
                    });
                } else {
                    runOnUiThread(() -> {
                        txtSinopse.setText("");
                        imgPoster.setImageDrawable(null);
                        Toast.makeText(this, "Filme não encontrado!", Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Erro na busca!", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
    private void adicionarFavorito() {
        if (tituloAtual.isEmpty()) {
            Toast.makeText(this, "Busque um filme antes!", Toast.LENGTH_SHORT).show();
            return;
        }
        favoritos.add(new Filme(tituloAtual, sinopseAtual));
        Toast.makeText(this, "Filme adicionado aos favoritos!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_favoritos) {
            Intent intent = new Intent(this, FavoritosActivity.class);
            intent.putExtra("favoritos", favoritos);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.menu_sobre) {
            startActivity(new Intent(this, SobreActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
