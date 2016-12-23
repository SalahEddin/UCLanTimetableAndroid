package uclancyprusguide.inspirecenter.org.uclantimetable.views;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.IntentUtils;

import java.util.Locale;

public class FragmentContact
        extends Fragment
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static final String TAG = "uclan-cy";

    public static final LatLng UCLAN_CY_LAT_LNG = new LatLng(35.008511d, 33.696940d);

    private GoogleMap googleMap = null;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

    private TextView uclanCyDistance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_contact, null);

        final MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.activity_uclancy_map);
        mapFragment.getMapAsync(this);

        uclanCyDistance = (TextView) view.findViewById(R.id.fragment_uclancy_distance_l1);
        final Button uclanCyCall = (Button) view.findViewById(R.id.fragment_uclancy_call);
        final Button uclanCyStudentSupportCall = (Button) view.findViewById(R.id.fragment_uclancy_student_support_call);
        final Button uclanCyEmail = (Button) view.findViewById(R.id.fragment_uclancy_email);
        final Button uclanCyFacebook = (Button) view.findViewById(R.id.facebook_button);
        final Button uclanCyTwitter = (Button) view.findViewById(R.id.twitter_button);
        final Button uclanCyInstagram = (Button) view.findViewById(R.id.instagram_button);
        final Button uclanCyNavigate = (Button) view.findViewById(R.id.fragment_uclancy_navigate);

        // call button
        {
            final Intent dialUclanCyIntent = new Intent(Intent.ACTION_DIAL);

            uclanCyCall.setEnabled(IntentUtils.isIntentAvailable(getActivity(), dialUclanCyIntent));
            uclanCyCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialUclanCyIntent.setData(Uri.parse("tel: +35724694000"));
                    startActivity(dialUclanCyIntent);
                }
            });
            uclanCyStudentSupportCall.setEnabled(IntentUtils.isIntentAvailable(getActivity(), dialUclanCyIntent));
            uclanCyStudentSupportCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialUclanCyIntent.setData(Uri.parse("tel: +35724694026"));
                    startActivity(dialUclanCyIntent);
                }
            });
        }
        // mail
        {
            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"info@uclancyprus.ac.cy"});
            uclanCyEmail.setEnabled(IntentUtils.isIntentAvailable(getActivity(), intent));
            uclanCyEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intent);
                }
            });
        }
        // social media
        {
            final Intent intent = new Intent(Intent.ACTION_VIEW);

            uclanCyFacebook.setEnabled(IntentUtils.isIntentAvailable(getActivity(), intent));
            uclanCyFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://www.facebook.com/uclancyprus";
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            });

            uclanCyTwitter.setEnabled(IntentUtils.isIntentAvailable(getActivity(), intent));
            uclanCyTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "https://twitter.com/uclancyprus";
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            });

            uclanCyInstagram.setEnabled(IntentUtils.isIntentAvailable(getActivity(), intent));
            uclanCyInstagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "https://www.instagram.com/uclancyprus/";
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            });
        }

        {
            final String directionsUri = "http://maps.google.com/maps?daddr=35.008511,33.696940";
            final Intent directionsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(directionsUri));
            // check if the intent can be handled
            uclanCyNavigate.setEnabled(IntentUtils.isIntentAvailable(getActivity(), directionsIntent));
            uclanCyNavigate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(directionsIntent);
                }
            });
        }

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        return view;
    }

    public static final int PERMISSIONS_REQUEST_CODE_ACCESS_AND_CHANGE_WIFI = 42;

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(UCLAN_CY_LAT_LNG, 15));
        final Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.uclan_cy_white_round), 96, 96, true);
        final BitmapDescriptor uclanCyBitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
        googleMap.addMarker(new MarkerOptions().position(UCLAN_CY_LAT_LNG).title(getString(R.string.uclancy_address)).icon(uclanCyBitmapDescriptor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE_ACCESS_AND_CHANGE_WIFI);
        } else {
            googleMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (googleMap != null) googleMap.setMyLocationEnabled(true);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE_ACCESS_AND_CHANGE_WIFI);
            return;
        }
        final Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastLocation != null) {
            updateDistance(lastLocation);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "connection suspended: " + i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "connection failed: " + connectionResult);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        updateDistance(location);
    }

    protected void startLocationUpdates() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    void updateDistance(final Location location) {
        final float[] distance = new float[3];
        Location.distanceBetween(location.getLatitude(), location.getLongitude(), FragmentContact.UCLAN_CY_LAT_LNG.latitude, FragmentContact.UCLAN_CY_LAT_LNG.longitude, distance);
        uclanCyDistance.setText(String.format(Locale.US, "%.1f", distance[0] / 1000f));
    }
}