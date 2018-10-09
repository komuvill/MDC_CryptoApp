package stock.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements FragmentSearch.switchStateInterface {

    private SectionsStatePagerAdapter adapter;
    private CustomPager viewPager;
    public static final int FRAGMENT_MENU = 0;
    public static final int FRAGMENT_SEARCH = 1;
    public static final int FRAGMENT_RESULTS = 2;
    public static final int FRAGMENT_GRAPH = 3;
    public static final int FRAGMENT_CONVERTER = 4;
    public CryptoFetcher cFetcher;
    public CurrencyConverter converter;
    public StockFetcher sFetcher;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentMenu(), "FragmentMenu"); //First fragment added to the list will be the first one to be inflated
        adapter.addFragment(new FragmentSearch(), "FragmentSearch");
        adapter.addFragment(new FragmentResults(), "FragmentResults");
        adapter.addFragment(new FragmentConverter(), "FragmentConverter");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        runOnUiThread(() -> viewPager.setCurrentItem(fragmentNumber));
    }

    @Override
    public void onRequestSent(boolean state) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("STATE", state);
        adapter.getItem(2).setArguments(bundle);
    }
}
