package stock.app;

import android.os.AsyncTask;
import android.util.Log;

import org.patriques.AlphaVantageConnector;
import org.patriques.ForeignExchange;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.exchange.CurrencyExchange;
import org.patriques.output.exchange.data.CurrencyExchangeData;

import java.util.Map;

public class CurrencyConverter extends AsyncTask {

    final String TAG = "CurrencyConverter";
    String apiKey = "3475H17";
    int timeout = 3000;
    AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
    ForeignExchange foreignExchange = new ForeignExchange(apiConnector);
    private ResultsCallback listener;
    private CurrencyExchangeData result;
    protected String from, to;

    public interface ResultsCallback {
        void onRequestDone(boolean result);
    }

    public CurrencyConverter(ResultsCallback listener, String fromCur, String toCur){
        this.listener = listener;
        this.from = fromCur;
        this.to = toCur;
    }

    public CurrencyExchangeData getResult(){
        return result;
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        try{
            CurrencyExchange exchange = foreignExchange.currencyExchangeRate(from, to);
            result = exchange.getData();
            listener.onRequestDone(true);
        }catch(AlphaVantageException e){
            Log.d(TAG, "Something's fucky: " + e.getMessage());
            listener.onRequestDone(false);
        }
        return objects;
    }
}
