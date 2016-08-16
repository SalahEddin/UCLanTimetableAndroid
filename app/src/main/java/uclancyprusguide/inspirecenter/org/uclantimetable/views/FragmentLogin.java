package uclancyprusguide.inspirecenter.org.uclantimetable.views;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.EditText;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.User;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.Misc;
import uclancyprusguide.inspirecenter.org.uclantimetable.interfaces.UserCallbackInterface;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableData;


/**
 * Login in Fragment, can login via UCLan or Google account
 */
public class FragmentLogin extends Fragment implements UserCallbackInterface {

    private CheckBox rememberMeCheckBox;
    private String lastTypedName, lastTypedPass;
    private Context context;
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

        context = getActivity();
        rememberMeCheckBox = (CheckBox) view.findViewById(R.id.rememberMeCheckbox);
        final EditText mailText = (EditText) view.findViewById(R.id.edit_mail);
        final EditText mailPass = (EditText) view.findViewById(R.id.edit_pass);
        final Button loginButton = (Button) view.findViewById(R.id.button_login);
        final Button forgotButton = (Button) view.findViewById(R.id.button_forgot_login);
        final Button googleLoginButton = (Button) view.findViewById(R.id.button_google_login);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        mailText.setText(prefs.getString(FragmentLogin.this.getActivity().getString(R.string.username), ""));
        mailPass.setText(prefs.getString(FragmentLogin.this.getActivity().getString(R.string.password), ""));

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mailText.getText().toString().matches("")) {
                    FragmentLogin.this.generateEmptySubmittedAlert("oops", "Please enter your e-mail in the box above", "ok");
                } else if (mailPass.getText().toString().matches("")) {
                    FragmentLogin.this.generateEmptySubmittedAlert("oops", "Please enter your password in the box above", "ok");
                } else {
                    lastTypedName = mailText.getText().toString();
                    lastTypedPass = mailPass.getText().toString();
                    // check if @ is already typed
                    final String userName = (!mailText.getText().toString().toLowerCase().contains(getString(R.string.uclan_mail_suffix))) ? mailText.getText().toString() + getString(R.string.uclan_mail_suffix) : mailText.getText().toString();
                    FragmentLogin.this.checkLogin(userName, mailPass.getText().toString());

                    // Check if no view has focus:
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        });
        // forgot button clicked
        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mailText.getText().toString().matches("")) {
                    FragmentLogin.this.generateEmptySubmittedAlert("oops", "Please enter your e-mail in the box above", "ok");
                } else {
                    FragmentLogin.this.generateEmptySubmittedAlert("E-mail sent", "An email with instructions was sent to your UCLan email", "dismiss");
                }
            }
        });
        // Google login button clicked
        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mailText.getText().toString().matches("")) {
                    FragmentLogin.this.generateEmptySubmittedAlert("oops", "Please enter your e-mail in the box above", "ok");
                } else {
                    FragmentLogin.this.generateEmptySubmittedAlert("Success", "An email with instructions was sent to your UCLan email", "ok");
                }
            }
        });
        return view;
    }

    private void generateEmptySubmittedAlert(String Title, String Description, String DissmissButtonText) {
        AlertDialog alertDialog = new AlertDialog.Builder((AppCompatActivity) getActivity()).create();
        alertDialog.setTitle(Title);
        alertDialog.setMessage(Description);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, DissmissButtonText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void checkLogin(String user, String pass) {
        TimetableData.GetLoginData(user, pass, context, this);
    }

    @Override
    public void onDownloaded(User user) {

        if (user != null) {
            // save the login
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

            Misc.saveUser(context, user);

            if (rememberMeCheckBox.isChecked()) {
                prefs.edit().putString(context.getString(R.string.username), lastTypedName).apply();
                prefs.edit().putString(context.getString(R.string.password), lastTypedPass).apply();
            }
            // change drawer view
            ((ActivityHome) getActivity()).switchUserView(user.getACCOUNT_TYPE_ID());


            String subtitle = "News"; // on actionbar

            int fragmentId = prefs.getInt(FragmentLogin.this.getString(R.string.activity_after_login), ActivityHome.FRAGMENT_ID_NEWS);
            Fragment frag = new FragmentNews();
            final FragmentManager fragmentManager = FragmentLogin.this.getFragmentManager();

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

            ((AppCompatActivity) FragmentLogin.this.getActivity()).getSupportActionBar().setSubtitle(subtitle);
            fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
            fragmentManager.executePendingTransactions();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder((AppCompatActivity) FragmentLogin.this.getActivity()).create();
            alertDialog.setTitle("Login Problem");
            alertDialog.setMessage("Username or Password are Incorrect");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Dismiss",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Misc.loadUser(context) != null) {
            Fragment frag = new FragmentTimetable();
            ((AppCompatActivity) FragmentLogin.this.getActivity()).getSupportActionBar().setSubtitle("Timetable");
            final FragmentManager fragmentManager = FragmentLogin.this.getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
            fragmentManager.executePendingTransactions();
        }
    }
}
