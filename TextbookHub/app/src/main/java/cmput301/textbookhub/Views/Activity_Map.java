package cmput301.textbookhub.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cmput301.textbookhub.BaseApplication;
import cmput301.textbookhub.Controllers.ActivityController;
import cmput301.textbookhub.Controllers.ActivityControllerFactory;
import cmput301.textbookhub.Controllers.AppUserController;
import cmput301.textbookhub.GPSTracker;
import cmput301.textbookhub.Models.DataHelper;
import cmput301.textbookhub.R;
import cmput301.textbookhub.Tools;

/**
 * Created by Fred on 2016/4/3.
 */
public class Activity_Map extends AppCompatActivity implements BaseView{

    public static final String ACTIVITY_KEY_LAT = "LAT";
    public static final String ACTIVITY_KEY_LONG = "LONG";
    public static final String ACTIVITY_TYPE = "TYPE";
    public static final String ACTIVITY_VIEW_ONLY = "VIEW";
    public static final String ACTIVITY_SET_LOC = "SET";

    private MapView mapView;

    private Double lat = 53.631611;
    private Double lon = -113.323975;
    GPSTracker gps;
    Button btn_finish, btn_search;
    EditText et_address;
    LinearLayout layout_search_address;

    ActivityController activityController;
    AppUserController userController;

    private Context context;
    private MarkerOptions marker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        this.activityController = ActivityControllerFactory.getControllerForView(ActivityControllerFactory.FactoryCatalog.ACTIVITY_MAP, this);
        this.context = this;
        this.userController = AppUserController.getInstance();
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View view = getLayoutInflater().inflate(R.layout.actionbar_buttonbar_ok, null);
        final ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        getSupportActionBar().setCustomView(view, layoutParams);
        Toolbar parent = (Toolbar) view.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        if(getIntent().hasExtra(ACTIVITY_KEY_LAT)){
            this.lat = getIntent().getDoubleExtra(ACTIVITY_KEY_LAT, lat);
            this.lon = getIntent().getDoubleExtra(ACTIVITY_KEY_LONG, lon);
        }else{
            Log.i("ERROR--->", "NO VALID LAT LON PASSED IN");
        }

        btn_finish = (Button) view.findViewById(R.id.button_ok);
        btn_search = (Button) findViewById(R.id.button_search_address);
        et_address = (EditText) findViewById(R.id.et_address);
        layout_search_address = (LinearLayout) findViewById(R.id.search_address_layout);
        if(getIntent().hasExtra(ACTIVITY_TYPE) && getIntent().getStringExtra(ACTIVITY_TYPE).equals(ACTIVITY_SET_LOC)){
            this.layout_search_address.setVisibility(View.VISIBLE);
        }else{
            this.layout_search_address.setVisibility(View.GONE);
        }
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Tools.isStringValid(et_address.getText().toString())) {
                    DataHelper.GetLocationFromAddressTask t = new DataHelper.GetLocationFromAddressTask();
                    t.execute(et_address.getText().toString());
                    boolean result;
                    try {
                        result = getLatLong(t.get());
                    }catch(Exception e){
                        result = false;
                    }
                    if (!result) {
                        activityController.displayNotificationDialog(context, getResources().getString(R.string.error), getResources().getString(R.string.invalid_address));
                        saveCurrLocation();
                    }
                    updateView();
                } else {
                    activityController.displayNotificationDialog(context, getResources().getString(R.string.error), getResources().getString(R.string.invalid_address));
                }
            }
        });
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                i.putExtra(ACTIVITY_KEY_LAT, lat);
                i.putExtra(ACTIVITY_KEY_LONG, lon);
                setResult(RESULT_OK, i);
                finish();
            }
        });
        marker = new MarkerOptions()
                .position(new LatLng(lat, lon))
                .title("Trade Location")
                .snippet("Trade this book here");
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.addMarker(marker);
                //LatLngBounds latLngBounds = new LatLngBounds.Builder().include(new LatLng(lat, lon)).build();
                mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder().target(new LatLng(lat, lon)).zoom(12).build()
                ));

            }
        });


    }

    private void saveCurrLocation(){
        if(gps.canGetLocation()){
            this.lat = gps.getLatitude();
            this.lon = gps.getLongitude();
        }else {
            Log.i("GPS FAILURE", "CANNOT GET LOC");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        BaseApplication.activityResumed();
        gps = new GPSTracker(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        gps.stopUsingGPS();
        BaseApplication.activityPaused();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }



    @Override
    public void updateView() {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.removeMarker(marker.getMarker());
                marker = new MarkerOptions()
                        .position(new LatLng(lat, lon))
                        .title("Trade Location")
                        .snippet("Trade this book here");
                mapboxMap.addMarker(marker);
                mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder().target(new LatLng(lat, lon)).zoom(12).build()
                ));
            }
        });
    }

    public boolean getLatLong(JSONObject jsonObject) {

        try {
            lon = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

        } catch (JSONException e) {
            return false;
        }
        return true;
    }
}
