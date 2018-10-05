package stock.app;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentMenu extends Fragment {

    private Button buttonSearch;
    private Button buttonConverter;
    private Button buttonQuit;
    private String TAG = "FragmentMenu";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        Log.d(TAG, "Started.");
        buttonSearch = view.findViewById(R.id.buttonSearchMenu);
        buttonConverter = view.findViewById(R.id.buttonConverter);
        buttonQuit = view.findViewById(R.id.buttonQuit);

        buttonSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ((MainActivity)getActivity()).setViewPager(MainActivity.FRAGMENT_SEARCH);
            }
        });

        buttonConverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setViewPager(MainActivity.FRAGMENT_CONVERTER);
            }
        });

        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO quit the app
                System.exit(1);
            }
        });
        return view;
    }
}
