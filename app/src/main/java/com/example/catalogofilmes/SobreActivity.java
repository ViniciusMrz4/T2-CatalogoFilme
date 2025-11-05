package com.example.catalogofilmes;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class SobreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        TextView txtSobre = findViewById(R.id.txtSobre);
        txtSobre.setText(
                "■ Catálogo de Filmes\n\n" +
                        "Aplicativo desenvolvido como projeto acadêmico.\n" +
                        "Permite buscar filmes na API OMDb, ver pôster e sinopse,\n" +
                        "e adicionar títulos aos favoritos.\n\n" +
                        "Desenvolvido por: \n" +
                        "Vinícius Marin Rodrigues - 206976 \n" +
                        "Ano: 2025"
        );
    }
}