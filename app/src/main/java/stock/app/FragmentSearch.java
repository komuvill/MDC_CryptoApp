package stock.app;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

public class FragmentSearch extends Fragment {

    private Button buttonSearch;
    private Button buttonBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        buttonSearch = view.findViewById(R.id.buttonSearch);
        buttonBack = view.findViewById(R.id.buttonBackToMenu);
        Log.d("SEARCH", "ON FRAGMENT SEARCH");

        buttonSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //TODO Send query to the API
                ((MainActivityTemp)getActivity()).setViewPager(MainActivityTemp.FRAGMENT_RESULTS);
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(),0);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivityTemp)getActivity()).setViewPager(MainActivityTemp.FRAGMENT_MENU);
            }
        });

        return view;
    }
}
