package stock.app;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class FragmentResults extends Fragment {

    private Button buttonGraph;
    private Button buttonBack;
    private TextView shortCurrencyName;
    private TextView textViewPrice;
    private String TAG = "FragmentResults";

    /*
        Tällä hetkellä ulos saadaan tunnus ja hinta
     */


    //Called when the fragment becomes visible
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(menuVisible){
            setShortCurrencyName();
            setTextViewPrice();
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

        buttonGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Graph view
                ((MainActivityTemp)getActivity()).setViewPager(MainActivityTemp.FRAGMENT_GRAPH);
                //Set the orientation to landscape
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivityTemp)getActivity()).setViewPager(MainActivityTemp.FRAGMENT_SEARCH);
            }
        });
        return view;
    }

    public void setShortCurrencyName(){
        shortCurrencyName.setText(((MainActivityTemp) Objects.requireNonNull(getActivity())).fetcher.getCurCode());
    }

    public void setTextViewPrice(){
        textViewPrice.setText(((MainActivityTemp)getActivity()).fetcher.getCurrentPrice());
    }
}
