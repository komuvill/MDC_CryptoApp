package stock.app;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class FragmentSearch extends Fragment implements cryptoFetcher.fetcherInterface {

    private Button buttonSearch;
    private Button buttonBack;
    private EditText editText;
    private String TAG = "FragmentSearch";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        Log.d(TAG, "Started.");
        buttonSearch = view.findViewById(R.id.buttonSearch);
        buttonBack = view.findViewById(R.id.buttonBackToMenu);
        editText = view.findViewById(R.id.editText);
        String symbol = editText.getText().toString();
        ((MainActivityTemp) Objects.requireNonNull(getActivity())).fetcher = (cryptoFetcher) new cryptoFetcher();
        ((MainActivityTemp)getActivity()).fetcher.setListener(this);
        buttonSearch.setOnClickListener(view1 -> {
            String symbol1 = editText.getText().toString();
            ((MainActivityTemp)getActivity()).fetcher.setSelectedCurrency(symbol1);
            ((MainActivityTemp)getActivity()).fetcher.execute();
            //Wait for the CryptoFetcher to finish work


            //Hide keyboard when moving to the next Fragment
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view1.getWindowToken(),0);
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Move back to menu screen
                ((MainActivityTemp)getActivity()).setViewPager(MainActivityTemp.FRAGMENT_MENU);
            }
        });

        return view;
    }

    @Override
    public void finished(boolean result) {
        if(result){
            ((MainActivityTemp)getActivity()).setViewPager(MainActivityTemp.FRAGMENT_RESULTS);
        } else {
            System.out.println("Piellee män");
        }
    }
}
