package stock.app;


import android.content.Intent;
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

import java.util.Objects;

public class FragmentResults extends Fragment {

    private Button buttonGraph;
    private Button buttonBack;
    private TextView shortName;
    private TextView textViewPrice;
    private TextView longName;
    private boolean switchState;
    private String TAG = "FragmentResults";
    private org.patriques.output.digitalcurrencies.IntraDay cryptoData;
    private org.patriques.output.timeseries.IntraDay stockData;

    /*
        Tällä hetkellä ulos saadaan tunnus ja hinta
     */

    //Called when the fragment becomes visible
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(menuVisible){
            if(!getRetainInstance()){
                switchState = getArguments().getBoolean("STATE");
                if(switchState){
                    stockData = ((MainActivity) Objects.requireNonNull(getActivity())).sFetcher.getResult();
                    shortName.setText(stockData.getMetaData().get("2. Symbol"));
                    textViewPrice.setText(String.format("%.2f", stockData.getStockData().get(0).getHigh()));
                }else{
                    cryptoData = ((MainActivity) Objects.requireNonNull(getActivity())).cFetcher.getResult();
                    shortName.setText(cryptoData.getMetaData().get("2. Digital Currency Code"));
                    longName.setText(cryptoData.getMetaData().get("3. Digital Currency Name"));
                    textViewPrice.setText(String.format("%.2f", cryptoData.getDigitalData().get(0).getPriceA()));
                }
            }
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
        shortName = view.findViewById(R.id.textViewCurNameShort);
        textViewPrice = view.findViewById(R.id.textViewPrice);
        longName = view.findViewById(R.id.textViewCurNameLong);

        buttonGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Graph view
                setRetainInstance(true);
                double[] priceList;
                String[] timeList;
                if(switchState){
                    priceList = new double[stockData.getStockData().size()];
                    timeList = new String[stockData.getStockData().size()];
                    for(int i = 0; i < stockData.getStockData().size(); i++){
                        priceList[i] = stockData.getStockData().get(stockData.getStockData().size() - i - 1).getHigh();
                        timeList[i] = stockData.getStockData().get(stockData.getStockData().size() - i - 1).getDateTime().toString();
                    }
                }else{
                    priceList = new double[cryptoData.getDigitalData().size()];
                    timeList = new String[cryptoData.getDigitalData().size()];
                    for(int i = 0; i < cryptoData.getDigitalData().size(); i++){
                        priceList[i] = cryptoData.getDigitalData().get(cryptoData.getDigitalData().size() - i - 1).getPriceA();
                        timeList[i] = cryptoData.getDigitalData().get(cryptoData.getDigitalData().size() - i - 1).getDateTime().toString();
                    }
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
