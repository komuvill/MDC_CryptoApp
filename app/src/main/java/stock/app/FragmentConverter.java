package stock.app;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.patriques.output.exchange.data.CurrencyExchangeData;

import java.util.Objects;

public class FragmentConverter extends Fragment implements CurrencyConverter.ResultsCallback {
    private final String TAG = "Converter Fragment";

    private EditText fromCurrency;
    private String from;
    private EditText toCurrency;
    private String to;
    private EditText convertedAmount;
    private double amount;
    private double exchangeRate;
    private double conversion;
    private TextView conversionResults;
    private Button convertButton;
    private Button backButton;
    private CurrencyExchangeData exchangeData;

    public FragmentConverter() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        Log.d(TAG, "Started.");
        fromCurrency = view.findViewById(R.id.editTextFromCurrency);
        toCurrency = view.findViewById(R.id.editTextToCurrency);
        convertedAmount = view.findViewById(R.id.editTextConvertedAmount);
        conversionResults = view.findViewById(R.id.textViewConversionResult);

        convertButton = view.findViewById(R.id.buttonConvert);
        convertButton.setOnClickListener(v -> {
            from = fromCurrency.getText().toString();
            to = toCurrency.getText().toString();
            ((MainActivity)getActivity()).converter = (CurrencyConverter) new CurrencyConverter(this,
                    from, to).execute();
            amount = Double.parseDouble(convertedAmount.getText().toString());
        });
        backButton = view.findViewById(R.id.buttonBack);
        backButton.setOnClickListener(v -> {
            ((MainActivity)getActivity()).setViewPager(MainActivity.FRAGMENT_MENU);
        });

        return view;
    }

    @Override
    public void onRequestDone(boolean result) {
        if(!result){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            exchangeData = ((MainActivity) Objects.requireNonNull(getActivity())).converter.getResult();
            exchangeRate = exchangeData.getExchangeRate();
            conversion = amount * exchangeRate;
            String parsedString = amount + " " + from + " = " + conversion + " " + to;
            conversionResults.setText(parsedString);

        }
    }
}
