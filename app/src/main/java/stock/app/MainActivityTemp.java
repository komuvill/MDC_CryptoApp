package stock.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivityTemp extends AppCompatActivity {

    private SectionsStatePagerAdapter sectionsStatePagerAdapter;
    private ViewPager viewPager;
    public static final int FRAGMENT_MENU = 0;
    public static final int FRAGMENT_SEARCH = 1;
    public static final int FRAGMENT_RESULTS = 2;
    public static final int FRAGMENT_GRAPH = 3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_temp);
        sectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentMenu(), "FragmentMenu"); //First fragment added to the list will be the first one to be inflated
        adapter.addFragment(new FragmentSearch(), "FragmentSearch");
        adapter.addFragment(new FragmentResults(), "FragmentResults");
        adapter.addFragment(new FragmentGraph(), "FragmentGraph");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        viewPager.setCurrentItem(fragmentNumber);
    }
}
