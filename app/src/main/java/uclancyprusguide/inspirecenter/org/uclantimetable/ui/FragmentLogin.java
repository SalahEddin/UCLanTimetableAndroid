package uclancyprusguide.inspirecenter.org.uclantimetable.ui;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;


/**
 * Login in Fragment, can login via UCLan or Google account
 */
public class FragmentLogin extends Fragment {

    public FragmentLogin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        EditText mailText = (EditText) view.findViewById(R.id.edit_mail);
        EditText mailPass = (EditText) view.findViewById(R.id.edit_pass);
        Button loginButton = (Button) view.findViewById(R.id.button_login);

        loginButton.setOnClickListener((v) -> {
            if (checkLogin()) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                int fragmentId = prefs.getInt(getString(R.string.activity_after_login), ActivityHome.FRAGMENT_ID_NEWS);
                Fragment frag = new FragmentNews();
                final FragmentManager fragmentManager = getFragmentManager();

                switch (fragmentId) {
                    case ActivityHome.FRAGMENT_ID_TIMETABLE:
                        frag= new FragmentTimetable();
                        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Timetable");
                        break;
                    case ActivityHome.FRAGMENT_ID_TIMETABLE_EXAMS:
                        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Exams");
                        frag = new FragmentExams();
                        break;
                    case ActivityHome.FRAGMENT_ID_TIMETABLE_NOTIFICATIONS:
                        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Notification");
                        frag = new FragmentTimetableNotifications();
                        break;
                    default:
                        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("News");
                        break;
                }

                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
                fragmentManager.executePendingTransactions();
            }
        });
        return view;
    }

    private boolean checkLogin() {
        return true;
    }
}
