package com.ejrgeek.festafimdeano.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ejrgeek.festafimdeano.R;
import com.ejrgeek.festafimdeano.constant.FimDeAnoConstants;
import com.ejrgeek.festafimdeano.data.SecurityPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;
    private static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.textHoje = findViewById(R.id.text_hoje);
        this.mViewHolder.textDiasRestantes = findViewById(R.id.text_dias_restantes);
        this.mViewHolder.botaoConfirmar = findViewById(R.id.botao_confirmar);

        this.mViewHolder.botaoConfirmar.setOnClickListener(this);

        //Datas
        this.mViewHolder.textHoje.setText(SIMPLE_DATE_FORMAT.format(Calendar.getInstance().getTime()));

        String faltaDias = String.format("%s %s", String.valueOf(this.getDaysLeft()), this.getString(R.string.dias));

        this.mViewHolder.textDiasRestantes.setText(faltaDias);

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.verifyPresence();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.botao_confirmar){

            String presenca = this.mSecurityPreferences.getStoredString(FimDeAnoConstants.PRESENCE_KEY);

            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(FimDeAnoConstants.PRESENCE_KEY, presenca);
            startActivity(intent);
        }
    }

    private void verifyPresence(){
        // Não confirmado, sim, não
        String presenca = this.mSecurityPreferences.getStoredString(FimDeAnoConstants.PRESENCE_KEY);

        if (presenca.equals(FimDeAnoConstants.CONFIRMATION_NO)){
            this.mViewHolder.botaoConfirmar.setText(R.string.nao);
        }else if (presenca.equals(FimDeAnoConstants.CONFIRMATION_YES)){
            this.mViewHolder.botaoConfirmar.setText(R.string.sim);
        }else{
            this.mViewHolder.botaoConfirmar.setText(R.string.nao_confirmado);
        }
    }

    private int getDaysLeft(){
        //Data de hoje
        Calendar calendarHoje = Calendar.getInstance();
        int hoje = calendarHoje.get(Calendar.DAY_OF_YEAR);

        //Dia maximo do ano
        Calendar calendarUltimoDia = Calendar.getInstance();
        int ultimoDia = calendarUltimoDia.getActualMaximum(Calendar.DAY_OF_YEAR);

        return ultimoDia - hoje;
    }

    private static class ViewHolder{
        TextView textHoje, textDiasRestantes;
        Button botaoConfirmar;
    }

}
