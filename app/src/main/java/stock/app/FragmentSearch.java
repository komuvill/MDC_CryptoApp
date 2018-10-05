package stock.app;


import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
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
import android.widget.Toast;

import org.patriques.output.digitalcurrencies.IntraDay;

import java.util.Map;

public class FragmentSearch extends Fragment implements CryptoFetcher.ResultsCallback {

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

        buttonSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String symbol = editText.getText().toString();
                sendRequest(symbol);
            }
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

    private void sendRequest(String str) {
        ((MainActivityTemp)getActivity()).fetcher = (CryptoFetcher) new CryptoFetcher(this, str).execute();
    }

    @Override
    public void onRequestDone(boolean result) {
        if(!result) {
            Activity activity = this.getActivity();
            Toast.makeText(activity,"Search failed",Toast.LENGTH_SHORT).show();
            Looper.prepare();
        }
        else {
            ((MainActivityTemp)getActivity()).setViewPager(MainActivityTemp.FRAGMENT_RESULTS);
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(),0);
        }
    }
}
