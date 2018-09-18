package stock.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText eText;
    Button fetchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eText = findViewById(R.id.editTextSymbol);
        fetchBtn = findViewById(R.id.buttonFetch);
        fetchBtn.setOnClickListener(v -> {
            String symbol = eText.getText().toString();
            new cryptoFetcher(symbol).execute();
        });
    }
}
