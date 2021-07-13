package com.example.proyectopoo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class loginStore extends AppCompatActivity {
    private Button singUpProductActivity;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_store);

        Bundle takeObject = getIntent().getExtras();

        Store store = null;

        if(takeObject != null){
            store = (Store) takeObject.getSerializable("Store");
        }
        info = (TextView)findViewById(R.id.textViewStore);
        singUpProductActivity = (Button)findViewById(R.id.signUpProductActivity);

        Store finalStore = store;

        info.setText(finalStore.getId());

        singUpProductActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ProductViewStrore.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("Store", finalStore);
                intent.putExtras(bundle);

                startActivity(intent);
                finish();
            }
        });

    }
    public void Back(View view){
        Intent back = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(back);
        finish();
    }
}