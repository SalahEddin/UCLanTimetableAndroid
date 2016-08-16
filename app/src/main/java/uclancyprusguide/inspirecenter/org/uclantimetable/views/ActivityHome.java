package uclancyprusguide.inspirecenter.org.uclantimetable.views;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.interfaces.NotificationsCallbackInterface;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.Notification;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.User;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.Misc;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableData;

public class ActivityHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NotificationsCallbackInterface {

    public static final String TAG = "uclan-cy";

    public static final int FRAGMENT_ID_NEWS = 0x010;
    public static final int FRAGMENT_ID_CONTACT = 0x030;
    public static final int FRAGMENT_ID_TIMETABLE = 0x040;
    public static final int FRAGMENT_ID_TIMETABLE_EXAMS = 0x050;
    public static final int FRAGMENT_ID_TIMETABLE_NOTIFICATIONS = 0x060;
    public static final int FRAGMENT_ID_LOGIN = 0x070;
    public static final int FRAGMENT_ID_ATTENDANCE = 0x080;
    public static final int FRAGMENT_ID_ROOM = 0x090;
    public static final int FRAGMENT_ID_ABOUT = 0x100;

    public static final String SELECTED_FRAGMENT_KEY = "selected-fragment";
    public static final String SELECTED_DATE_KEY = "selected-date";
    public static final String SELECTED_ROOM_KEY = "selected-room";

    final FragmentManager fragmentManager = getFragmentManager();
    NavigationView navigationView;

    private int selectedFragment = 0;

    private FragmentNews fragmentNews = new FragmentNews();
    private FragmentContact fragmentContact = new FragmentContact();
    private FragmentTimetable fragmentTimetable = new FragmentTimetable();
    private FragmentExams fragmentExams = new FragmentExams();
    private FragmentTimetableNotifications fragmentTimetableNotifications = new FragmentTimetableNotifications();
    private FragmentLogin fragmentLogin = new FragmentLogin();
    private FragmentAttendance fragmentAttendance = new FragmentAttendance();
    private FragmentRooms fragmentRooms = new FragmentRooms();
    private FragmentAbout fragmentAbout = new FragmentAbout();

    // to update notification count
    private MenuItem notificationNav;
    // holds ids of pages that requires logging in
    private static final Integer secureFragmentsArray[] = {
            R.id.nav_timetable,
            R.id.nav_exams,
            R.id.nav_notifications,
            R.id.nav_attendance,
            R.id.nav_room_timetable
    };

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        // hide logout by default and show login
        navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
        User user = Misc.loadUser(this);
        if (user != null) {
            // user is logged in, hide unavailable stuff
            switchUserView(user.getACCOUNT_TYPE_ID());
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            // load notifications count
            TimetableData.LoadNotifications(user.getUSER_ID(), this, this);
            // put name on the drawer
            navigationView.getMenu().findItem(R.id.drawer_title).setTitle(user.getFULLNAME());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (selectedFragment == 0) {
            selectedFragment = PreferenceManager.getDefaultSharedPreferences(this).getInt(SELECTED_FRAGMENT_KEY, FRAGMENT_ID_LOGIN);
            selectFragment();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt(SELECTED_FRAGMENT_KEY, selectedFragment).apply();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt(getString(R.string.selected_room), selectedFragment).apply();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt(getString(R.string.selected_date), selectedFragment).apply();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // get id of clicked item
        int id = item.getItemId();
        // check if user is logged in
        User user = Misc.loadUser(this);
        Boolean isUserLoggedIn = user != null;

        // Handle navigation view item clicks here.

        // if logged in, show logout
        if (id == R.id.nav_logout && isUserLoggedIn) {
            Misc.logoutUser(this);
            // hide logout and secure items
            navigationView.getMenu().findItem(R.id.nav_room_timetable).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_exams).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_attendance).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            selectedFragment = FRAGMENT_ID_LOGIN;
            // remove name
            navigationView.getMenu().findItem(R.id.drawer_title).setTitle("My Portal");
            selectFragment();
        } else if (id == R.id.nav_login) {
            selectedFragment = FRAGMENT_ID_LOGIN;
            selectFragment();
        } else if (id == R.id.nav_news) {
            selectedFragment = FRAGMENT_ID_NEWS;
            selectFragment();
        } else if (id == R.id.nav_contact) {
            selectedFragment = FRAGMENT_ID_CONTACT;
            selectFragment();
        }
        // if user is not logged in and page is in the secure list
        else if (!isUserLoggedIn && Arrays.asList(secureFragmentsArray).contains(id)) {
            int returnFragment;
            switch (id) {
                case R.id.nav_notifications:
                    returnFragment = FRAGMENT_ID_TIMETABLE_NOTIFICATIONS;
                    break;
                case R.id.nav_exams:
                    returnFragment = FRAGMENT_ID_TIMETABLE_EXAMS;
                    break;
                case R.id.nav_room_timetable:
                    returnFragment = FRAGMENT_ID_ROOM;
                    break;
                case R.id.nav_attendance:
                    returnFragment = FRAGMENT_ID_ATTENDANCE;
                    break;
                default:
                    returnFragment = FRAGMENT_ID_TIMETABLE;
                    break;
            }
            // write the intended fragment Id to be redirected after login
            prefs.edit().putInt(getString(R.string.activity_after_login), returnFragment).apply();
            selectedFragment = FRAGMENT_ID_LOGIN;
            selectFragment();

        } else if (id == R.id.nav_timetable) {
            // student timetable fragment
            selectedFragment = FRAGMENT_ID_TIMETABLE;
            selectFragment();
        } else if (id == R.id.nav_exams) {
            // upcoming exams
            selectedFragment = FRAGMENT_ID_TIMETABLE_EXAMS;
            selectFragment();
        } else if (id == R.id.nav_notifications) {
            // timetable notification
            selectedFragment = FRAGMENT_ID_TIMETABLE_NOTIFICATIONS;
            selectFragment();
        } else if (id == R.id.nav_attendance) {
            // timetable notification
            selectedFragment = FRAGMENT_ID_ATTENDANCE;
            selectFragment();
        } else if (id == R.id.nav_room_timetable) {
            selectedFragment = FRAGMENT_ID_ROOM;
            selectFragment();
        } else if (id == R.id.nav_about) {
            selectedFragment = FRAGMENT_ID_ABOUT;
            selectFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void selectFragment() {
        if (selectedFragment == FRAGMENT_ID_LOGIN) {
            toolbar.setSubtitle(getString(R.string.Login));
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentLogin).commit();
            fragmentManager.executePendingTransactions();
        } else if (selectedFragment == FRAGMENT_ID_NEWS) {
            // Handle the news action
            toolbar.setSubtitle(getString(R.string.News));
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentNews).commit();
            fragmentManager.executePendingTransactions();
        } else if (selectedFragment == FRAGMENT_ID_CONTACT) {
            toolbar.setSubtitle(getString(R.string.Contact));
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentContact).commit();
            fragmentManager.executePendingTransactions();
        } else if (selectedFragment == FRAGMENT_ID_TIMETABLE) {
            // final Drawable overflowIcon = ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_insert_invitation_black_48dp);
            toolbar.setSubtitle("Timetable");
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentTimetable).commit();
            fragmentManager.executePendingTransactions();
        } else if (selectedFragment == FRAGMENT_ID_TIMETABLE_EXAMS) {
            toolbar.setSubtitle("Upcoming Exams");
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentExams).commit();
            fragmentManager.executePendingTransactions();
        } else if (selectedFragment == FRAGMENT_ID_TIMETABLE_NOTIFICATIONS) {
            toolbar.setSubtitle("Notifications");
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentTimetableNotifications).commit();
            fragmentManager.executePendingTransactions();
        } else if (selectedFragment == FRAGMENT_ID_ROOM) {
            toolbar.setSubtitle("Rooms");
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentRooms).commit();
            fragmentManager.executePendingTransactions();
        } else if (selectedFragment == FRAGMENT_ID_ATTENDANCE) {
            toolbar.setSubtitle("Attendance");
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentAttendance).commit();
            fragmentManager.executePendingTransactions();
        } else if (selectedFragment == FRAGMENT_ID_ABOUT) {
            toolbar.setSubtitle("About us");
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentAbout).commit();
            fragmentManager.executePendingTransactions();
        } else {
            Log.e(TAG, "Unknown selected fragment: " + selectedFragment);
        }
    }

    public void switchUserView(String userType) {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_timetable).setVisible(true);
        notificationNav = nav_Menu.findItem(R.id.nav_notifications);
        notificationNav.setVisible(true);

        if (userType.equals("5")) {
            // student
            nav_Menu.findItem(R.id.nav_exams).setVisible(true);
            nav_Menu.findItem(R.id.nav_attendance).setVisible(true);

        } else {
            nav_Menu.findItem(R.id.nav_room_timetable).setVisible(true);
        }
        // make logout visible, and login invisible
        nav_Menu.findItem(R.id.nav_logout).setVisible(true);
        nav_Menu.findItem(R.id.nav_login).setVisible(false);
    }

    @Override
    public void onAllDownloaded(List<Notification> notifications) {
        final int notiCount = notifications.size();
        if (notiCount > 0) {
            TextView actionView = (TextView) MenuItemCompat.getActionView(notificationNav);
            actionView.setText(String.valueOf(notiCount));
            actionView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            actionView.setGravity(Gravity.CENTER);
        }
    }

    @Override
    public void onSingleDownloaded(Notification notifications) {

    }

    @Override
    public void onStatusChanged() {
        // ignored
    }
}