package stock.app;

import android.os.AsyncTask;
import android.util.Log;

import org.patriques.AlphaVantageConnector;
import org.patriques.TimeSeries;
import org.patriques.input.timeseries.Interval;
import org.patriques.input.timeseries.OutputSize;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.timeseries.IntraDay;

public class StockFetcher extends AsyncTask {

    String TAG = "StockFetcher";
    String apiKey = "3475H17";
    String selectedStock = "";
    int timeout = 3000;
    AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
    private TimeSeries stockTimeSeries = new TimeSeries(apiConnector);
    private IntraDay result;
    private ResultsCallback listener;

    public interface ResultsCallback {
        void onRequestDone(boolean result);
    }

    public StockFetcher(ResultsCallback listener, String stock){
        this.listener = listener;
        this.selectedStock = stock;
    }

    public IntraDay getResult() {
        return result;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try{
            result = stockTimeSeries.intraDay(selectedStock, Interval.FIVE_MIN, OutputSize.COMPACT);
            listener.onRequestDone(true);
        }catch(AlphaVantageException e) {
            Log.d(TAG, "Vituixmän: " + e.getMessage());
            listener.onRequestDone(false);
        }
        return objects;
    }
}