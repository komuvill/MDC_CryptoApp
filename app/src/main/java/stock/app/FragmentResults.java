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
    private TextView description;
    private TextView priceDescription;
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
                    longName.setText("Stock Symbol");
                    priceDescription.setText("Current Price ($)");
                    textViewPrice.setText(String.format("%.2f", stockData.getStockData().get(0).getHigh()));
                    description.setText("Complete data for newest entry at: " + stockData.getStockData().get(0).getDateTime().toString() +"\n");
                    description.append("Open: " + stockData.getStockData().get(0).getOpen() + "\n");
                    description.append("High: " + stockData.getStockData().get(0).getHigh() + "\n");
                    description.append("Low: " + stockData.getStockData().get(0).getLow() + "\n");
                    description.append("Close: " + stockData.getStockData().get(0).getClose() + "\n");
                    description.append("Volume: " + stockData.getStockData().get(0).getVolume() + "\n");
                }else{
                    cryptoData = ((MainActivity) Objects.requireNonNull(getActivity())).cFetcher.getResult();
                    priceDescription.setText("Current Price (EUR)");
                    shortName.setText(cryptoData.getMetaData().get("2. Digital Currency Code"));
                    longName.setText(cryptoData.getMetaData().get("3. Digital Currency Name"));
                    textViewPrice.setText(String.format("%.2f", cryptoData.getDigitalData().get(0).getPriceA()));
                    description.setText("Complete data for newest entry at: " + cryptoData.getDigitalData().get(0).getDateTime().toString() + "\n");
                    description.append("Price A: " + cryptoData.getDigitalData().get(0).getPriceA() + "\n");
                    description.append("Price B: " +  cryptoData.getDigitalData().get(0).getPriceB() + "\n");
                    description.append("Volume: " +  cryptoData.getDigitalData().get(0).getVolume() + "\n");
                    description.append("Market Cap: " +  cryptoData.getDigitalData().get(0).getMarketCap() + "\n");
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
        description = view.findViewById(R.id.textViewDescription);
        priceDescription = view.findViewById(R.id.textViewPriceDescription);

        buttonGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Graph view
                setRetainInstance(true);
                int length = Math.min(cryptoData.getDigitalData().size(), 289);
                double[] priceList = new double[length];
                String[] timeList = new String[length];
                if(switchState){
                    for(int i = 0; i < length; i++) {
                        priceList[i] = stockData.getStockData().get(length - i - 1).getHigh();
                        timeList[i] = stockData.getStockData().get(length - i - 1).getDateTime().toString();
                    }
                }else{
                    for(int i = 0; i < length; i++) {
                        priceList[i] = cryptoData.getDigitalData().get(length - i - 1).getPriceA();
                        timeList[i] = cryptoData.getDigitalData().get(length - i - 1).getDateTime().toString();
                    }
                }
                Intent intent = new Intent(getActivity(),ActivityGraph.class);
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
