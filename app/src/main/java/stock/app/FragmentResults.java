package stock.app;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
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
import org.patriques.output.digitalcurrencies.data.SimpelDigitalCurrencyData;

import java.io.Serializable;
import java.util.ArrayList;
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
            if(!getRetainInstance()){
                data = ((MainActivity) Objects.requireNonNull(getActivity())).fetcher.getResult(); //haetaan data fetcheristä
            }

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
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        buttonGraph = view.findViewById(R.id.buttonShowGraph);
        buttonBack = view.findViewById(R.id.buttonBackToSearch);
        shortCurrencyName = view.findViewById(R.id.textViewCurNameShort);
        textViewPrice = view.findViewById(R.id.textViewPrice);
        longCurrencyName = view.findViewById(R.id.textViewCurNameLong);

        buttonGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Graph view
                setRetainInstance(true);
                int length = Math.min(data.getDigitalData().size(), 289);
                double[] priceList = new double[length];
                String[] timeList = new String[length];
                for(int i = 0; i < length; i++){
                    priceList[i] = data.getDigitalData().get(length - i - 1).getPriceA();
                    timeList[i] = data.getDigitalData().get(length - i - 1).getDateTime().toString();
                }
                Intent intent = new Intent(getActivity(),FragmentGraph.class);
                intent.putExtra("priceData", priceList);
                intent.putExtra("timeData", timeList);
                startActivity(intent);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRetainInstance(false);
                ((MainActivity)getActivity()).setViewPager(MainActivity.FRAGMENT_SEARCH);
            }
        });
        return view;
    }
}
