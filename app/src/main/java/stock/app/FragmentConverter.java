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

public class FragmentConverter extends Fragment {
    private final String TAG = "Converter Fragment";

    EditText fromCurrency;
    EditText toCurrency;
    EditText convertedAmount;
    TextView conversionResults;
    Button convertButton;
    Button backButton;


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

        });
        backButton = view.findViewById(R.id.buttonBack);
        backButton.setOnClickListener(v -> {
            ((MainActivity)getActivity()).setViewPager(MainActivity.FRAGMENT_MENU);
        });

        return view;
    }
}
