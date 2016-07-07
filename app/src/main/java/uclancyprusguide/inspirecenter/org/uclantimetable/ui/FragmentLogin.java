package uclancyprusguide.inspirecenter.org.uclantimetable.ui;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;


/**
 * Login in Fragment, can login via UCLan or Google account
 */
public class FragmentLogin extends Fragment {

    private View vi;
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
        Button forgotButton = (Button) view.findViewById(R.id.button_forgot_login);
        Button googleLoginButton = (Button) view.findViewById(R.id.button_google_login);

        loginButton.setOnClickListener((v) -> {
            if (mailText.getText().toString().matches("")) {
                generateEmptySubmittedAlert("oops", "Please enter your e-mail in the box above", "ok");
            } else if (mailPass.getText().toString().matches("")) {
                generateEmptySubmittedAlert("oops", "Please enter your password in the box above", "ok");
            } else if (checkLogin(
                    mailText.getText() + getString(R.string.uclan_mail_suffix), mailPass.getText().toString())) {

                String subtitle = "News"; // on actionbar
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                int fragmentId = prefs.getInt(getString(R.string.activity_after_login), ActivityHome.FRAGMENT_ID_NEWS);
                Fragment frag = new FragmentNews();
                final FragmentManager fragmentManager = getFragmentManager();

                switch (fragmentId) {
                    case ActivityHome.FRAGMENT_ID_TIMETABLE:
                        frag = new FragmentTimetable();
                        subtitle = "Timetable";
                        break;
                    case ActivityHome.FRAGMENT_ID_TIMETABLE_EXAMS:
                        subtitle = "Exams";
                        frag = new FragmentExams();
                        break;
                    case ActivityHome.FRAGMENT_ID_TIMETABLE_NOTIFICATIONS:
                        subtitle = "Notifications";
                        frag = new FragmentTimetableNotifications();
                        break;
                    default:
                        break;
                }

                ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subtitle);
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
                fragmentManager.executePendingTransactions();


                // dismissSoftKeyboard();


            } else {
                AlertDialog alertDialog = new AlertDialog.Builder((AppCompatActivity) getActivity()).create();
                alertDialog.setTitle("Login Problem");
                alertDialog.setMessage("Username or Password are Incorrect");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Dismiss",
                        (dialog, which) -> {
                            dialog.dismiss();
                        });
                alertDialog.show();
            }
        });
        // forgot button clicked
        forgotButton.setOnClickListener((v) -> {
            if (mailText.getText().toString().matches("")) {
                generateEmptySubmittedAlert("oops", "Please enter your e-mail in the box above", "ok");
            } else {
                generateEmptySubmittedAlert("E-mail sent", "An email with instructions was sent to your UCLan email", "dismiss");
            }
        });
        // Google login button clicked
        googleLoginButton.setOnClickListener((v) -> {
            if (mailText.getText().toString().matches("")) {
                generateEmptySubmittedAlert("oops", "Please enter your e-mail in the box above", "ok");
            } else {
                generateEmptySubmittedAlert("Success", "An email with instructions was sent to your UCLan email", "ok");
            }
        });
        return view;
    }

    private void dismissSoftKeyboard() {
        // Check if no view has focus, then hide keyboard todo resolve
        InputMethodManager imm = (InputMethodManager) vi.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    private void generateEmptySubmittedAlert(String Title, String Description, String DissmissButtonText) {
        AlertDialog alertDialog = new AlertDialog.Builder((AppCompatActivity) getActivity()).create();
        alertDialog.setTitle(Title);
        alertDialog.setMessage(Description);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, DissmissButtonText,
                (dialog, which) -> {
                    dialog.dismiss();
                });
        alertDialog.show();
    }

    private boolean checkLogin(String user, String pass) {
        // TODO: 07/07/16 check user credentials
        return user.equals("a" + getString(R.string.uclan_mail_suffix)) && pass.equals("a");
    }
}
