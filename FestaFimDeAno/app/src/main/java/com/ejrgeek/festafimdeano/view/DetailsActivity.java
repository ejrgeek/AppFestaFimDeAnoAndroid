package com.ejrgeek.festafimdeano.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.ejrgeek.festafimdeano.R;
import com.ejrgeek.festafimdeano.constant.FimDeAnoConstants;
import com.ejrgeek.festafimdeano.data.SecurityPreferences;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.checkBoxParticipar = findViewById(R.id.checkbox_participar);

        this.mViewHolder.checkBoxParticipar.setOnClickListener(this);

        this.loadDataFromMain();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.checkbox_participar){
            if (this.mViewHolder.checkBoxParticipar.isChecked()){
                // Salvar a presença
                this.mSecurityPreferences.storeString(FimDeAnoConstants.PRESENCE_KEY, FimDeAnoConstants.CONFIRMATION_YES);
            }else{
                // Salvar ausencia
                this.mSecurityPreferences.storeString(FimDeAnoConstants.PRESENCE_KEY, FimDeAnoConstants.CONFIRMATION_NO);
            }
        }
    }

    private void loadDataFromMain(){
        Bundle extra = getIntent().getExtras();
        if (extra != null){
            String presenca = extra.getString(FimDeAnoConstants.PRESENCE_KEY);
            if (presenca != null && presenca.equals(FimDeAnoConstants.CONFIRMATION_YES)){
              this.mViewHolder.checkBoxParticipar.setChecked(true);
            } else {
                this.mViewHolder.checkBoxParticipar.setChecked(false);
            }
        }
    }

    private static class ViewHolder{
        CheckBox checkBoxParticipar;
    }

}
