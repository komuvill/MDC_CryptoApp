package stock.app;

import android.os.AsyncTask;
import android.util.Log;

import org.patriques.AlphaVantageConnector;
import org.patriques.DigitalCurrencies;
import org.patriques.input.digitalcurrencies.Market;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.digitalcurrencies.IntraDay;
import org.patriques.output.digitalcurrencies.data.SimpelDigitalCurrencyData;

import java.util.List;
import java.util.Map;

public class cryptoFetcher extends AsyncTask {

    String TAG = "API-KUTSUJEN TESTAUSTA";
    String apiKey = "3475H17";
    String selectedCurrency = "";
    int timeout = 3000;
    AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
    DigitalCurrencies digitalCurrencies = new DigitalCurrencies(apiConnector);
    private Map<String, String> metaData;
    private SimpelDigitalCurrencyData latestEntry;
    private boolean isFinished = false;

    fetcherInterface listener = null;

    public cryptoFetcher(){

    }

    public void setListener(fetcherInterface listener){
        this.listener = listener;
    }

    public void setSelectedCurrency(String currency){
        this.selectedCurrency = currency;
    }

    //TODO GET TO onPostExecute AND CALL setViewPager method

    /*
        Paras olis ehkä muuttaa tämä luokka normaaliksi Threadiksi, koska FragmentResult:ia ei voi
        asettaa näkyväksi ennen, kuin query on suoritettu ja tiedot ovat saatavilla. En tiiä.
     */
    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            IntraDay response = digitalCurrencies.intraDay(selectedCurrency, Market.EUR);
            metaData = response.getMetaData();
            System.out.println("Information: " + metaData.get("1. Information"));
            System.out.println("Digital Currency Code: " + metaData.get("2. Digital Currency Code"));
            List<SimpelDigitalCurrencyData> digitalData = response.getDigitalData();
            latestEntry = digitalData.get(0);
            Log.d(TAG, "date:       " + latestEntry.getDateTime());
            Log.d(TAG, "price A:    " + latestEntry.getPriceA());
            Log.d(TAG, "price B:    " + latestEntry.getPriceB());
            Log.d(TAG, "volume:     " + latestEntry.getVolume());
            Log.d(TAG, "market cap: " + latestEntry.getMarketCap());
            isFinished = true;
            listener.finished(true);
        } catch (AlphaVantageException e) {
            System.out.println(e.getMessage());
        }
        return objects;
    }

    public boolean getFinishedStatus(){
        return isFinished;
    }

    public String getCurCode(){
        isFinished = false;
        return metaData.get("2. Digital Currency Code");
    }

    public String getCurrentPrice(){
        return String.valueOf(latestEntry.getPriceA());
    }

    public interface fetcherInterface{
        void finished(boolean result);
    }
}
