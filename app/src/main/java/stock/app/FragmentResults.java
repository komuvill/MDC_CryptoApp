package stock.app;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.patriques.output.digitalcurrencies.IntraDay;

import java.util.Objects;

public class FragmentResults extends Fragment {

    private Button buttonGraph;
    private Button buttonBack;
    private TextView shortCurrencyName;
    private TextView textViewPrice;
    private TextView longCurrencyName;
    private String TAG = "FragmentResults";
    private IntraDay data;

    /*
        Tällä hetkellä ulos saadaan tunnus ja hinta
     */


    //Called when the fragment becomes visible
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(menuVisible){
            data = ((MainActivity) Objects.requireNonNull(getActivity())).fetcher.getResult(); //haetaan data fetcheristä

            shortCurrencyName.setText(data.getMetaData().get("2. Digital Currency Code"));
            longCurrencyName.setText(data.getMetaData().get("3. Digital Currency Name"));
            textViewPrice.setText(String.format("%.2f", data.getDigitalData().get(0).getPriceA()));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        Log.d(TAG, "Started.");
        buttonGraph = view.findViewById(R.id.buttonShowGraph);
        buttonBack = view.findViewById(R.id.buttonBackToSearch);
        shortCurrencyName = view.findViewById(R.id.textViewCurNameShort);
        textViewPrice = view.findViewById(R.id.textViewPrice);
        longCurrencyName = view.findViewById(R.id.textViewCurNameLong);

        buttonGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Graph view
                ((MainActivity)getActivity()).setViewPager(MainActivity.FRAGMENT_GRAPH);
                //Set the orientation to landscape
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setViewPager(MainActivity.FRAGMENT_SEARCH);
            }
        });
        return view;
    }

    /*
    //this is a problem
    public void setShortCurrencyName(){
        shortCurrencyName.setText(((MainActivity) Objects.requireNonNull(getActivity())).fetcher.getCurCode());
    }

    public void setTextViewPrice(){
        textViewPrice.setText(((MainActivity)getActivity()).fetcher.getCurrentPrice());
    } */
}
