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

public class CryptoFetcher extends AsyncTask {

    String TAG = "API-KUTSUJEN TESTAUSTA";
    String apiKey = "3475H17";
    String selectedCurrency = "";
    int timeout = 3000;
    AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
    DigitalCurrencies digitalCurrencies = new DigitalCurrencies(apiConnector);
    private Map<String, String> metaData;
    private SimpelDigitalCurrencyData latestEntry;
    private IntraDay result;
    private ResultsCallback listener;

    public interface ResultsCallback {
        void onRequestDone(boolean result);
    }

    public CryptoFetcher(ResultsCallback listener, String currency){
        this.listener = listener;
        this.selectedCurrency = currency;
    }

    public IntraDay getResult() {
        return result;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            result = digitalCurrencies.intraDay(selectedCurrency, Market.EUR);
            listener.onRequestDone(true);
        } catch (AlphaVantageException e) {
            System.out.println(e.getMessage());
            listener.onRequestDone(false);
        }
        return objects;
    }
}
