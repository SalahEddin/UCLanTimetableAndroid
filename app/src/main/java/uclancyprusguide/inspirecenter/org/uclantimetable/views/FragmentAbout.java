package uclancyprusguide.inspirecenter.org.uclantimetable.views;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;

public class FragmentAbout extends Fragment {

    public FragmentAbout() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View vi = inflater.inflate(R.layout.fragment_about, container, false);

        final ImageView uclanImg = (ImageView) vi.findViewById(R.id.uclan_logo_about);
        uclanImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String url = "http://www.uclancyprus.ac.cy/en/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                getActivity().startActivity(i);
            }
        });

        final ImageView inspireImg = (ImageView) vi.findViewById(R.id.inspire_logo_about);
        inspireImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String url = "http://inspirecenter.org/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                getActivity().startActivity(i);
            }
        });

        return vi;
    }

}
