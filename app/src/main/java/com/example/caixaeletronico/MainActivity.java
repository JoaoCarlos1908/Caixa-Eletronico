package com.example.caixaeletronico;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView text_notas[] = new TextView[7];
    private Button btn_sacar, btn_reset, btn_apagar,btn_cancelar;
    private Button[] btn_teclado = new Button[10];
    private TextView edit_valor;
    private String valor = "";
    private int[] quantNotas = new int[7];
    private int[] valorNotas = {200, 100, 50, 20, 10, 5, 2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // inicializa componentes logo aqui
        iniciarComponentes();

        // listener para ajustar insets (apenas layout, não lógica do app)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        for (int x = 0; x < 10; x++) {
            int finalx = x;
            btn_teclado[x].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    valor = valor + Integer.toString(finalx);
                    edit_valor.setText("R$" + valor);
                }
            });
        }

        // agora sim: listener do botão
        btn_sacar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valor.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Digite um valor válido!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int valorInt = Integer.parseInt(valor);
                    calcularNotas(valorInt);

                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Valor inválido!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int x = 0; x < 7; x++){
                    text_notas[x].setVisibility(View.GONE);
                }
                edit_valor.setVisibility(View.VISIBLE);
                edit_valor.setText("R$");
                valor = "";
                edit_valor.setText("Informe o valor do saque.");
            }
        });

        btn_apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder(valor);
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                valor = sb.toString();
                edit_valor.setText("R$" + valor);
            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void iniciarComponentes() {
        text_notas[0] = findViewById(R.id.nota200);
        text_notas[1] = findViewById(R.id.nota100);
        text_notas[2] = findViewById(R.id.nota50);
        text_notas[3] = findViewById(R.id.nota20);
        text_notas[4] = findViewById(R.id.nota10);
        text_notas[5] = findViewById(R.id.nota5);
        text_notas[6] = findViewById(R.id.nota2);

        btn_sacar = findViewById(R.id.btn_confirmar);
        btn_reset = findViewById(R.id.btn_reset);
        btn_apagar = findViewById(R.id.btn_apagar);
        btn_cancelar = findViewById(R.id.btn_cancelar);
        edit_valor = findViewById(R.id.edit_valor);
        edit_valor.setText("R$");

        for(int x = 0; x < 7; x++){
            text_notas[x].setVisibility(View.GONE);
        }
        btn_teclado[0] = findViewById(R.id.btn_0);
        btn_teclado[1] = findViewById(R.id.btn_01);
        btn_teclado[2] = findViewById(R.id.btn_02);
        btn_teclado[3] = findViewById(R.id.btn_03);
        btn_teclado[4] = findViewById(R.id.btn_04);
        btn_teclado[5] = findViewById(R.id.btn_05);
        btn_teclado[6] = findViewById(R.id.btn_06);
        btn_teclado[7] = findViewById(R.id.btn_07);
        btn_teclado[8] = findViewById(R.id.btn_08);
        btn_teclado[9] = findViewById(R.id.btn_09);
    }

    public void calcularNotas(int valor) {
        int contValor = valor;

        // zera os TextViews antes de começar
        for (int i = 0; i < text_notas.length; i++) {
            text_notas[i].setText("R$ " + valorNotas[i] + ": 0 und.");
            quantNotas[i] = 0;
        }

        for (int i = 0; i < valorNotas.length; i++) {
            // Pula a nota de 5 se o restante for 6 ou 8
            if (valorNotas[i] == 5 && (contValor == 6 || contValor == 8)) {
                continue;
            }

            quantNotas[i] = contValor / valorNotas[i]; // pega o máximo possível da nota atual
            contValor = contValor % valorNotas[i];     // calcula o restante
            text_notas[i].setText("R$ " + valorNotas[i] + ": " + quantNotas[i] + " und.");
        }

        if (contValor != 0) {
            edit_valor.setText("Sem moedas.");
        }

        for(int x = 0; x < 7; x++){
            text_notas[x].setVisibility(View.VISIBLE);
        }
        edit_valor.setVisibility(View.GONE);
    }

}
