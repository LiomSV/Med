package by.bsu.vstasenia.med.mobile;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static final String UPDATE_TIMER_NAME = "updateTimer";
    private Location currentLocation = null;
    private Timer timer;
    private int statusOrd = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: 4/26/16 permissions denied
            ((TextView) findViewById(R.id.textCoord)).setText("Coord: no permission.");
        } else {
            LocationListener listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (isNewLocationBetter(location)) {
                        updateLocation(location);
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                }

                @Override
                public void onProviderEnabled(String s) {
                }

                @Override
                public void onProviderDisabled(String s) {
                }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);

            timer = new Timer(UPDATE_TIMER_NAME);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                        update();
                }
            }, 0, 3000);
        }
    }

    public void onAddCall(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = getUrl("rest/call/add_coor");
        StringRequest request = new StringRequest(Request.Method.POST, url, new Listener<String>() {
            @Override
            public void onResponse(String response) {
                ((TextView) findViewById(R.id.textMain)).setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((TextView) findViewById(R.id.textMain)).setText("Ops... error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("lastname", "Pushkin");
                if (currentLocation != null) {
                    params.put("latitude", Double.toString(currentLocation.getLatitude()));
                    params.put("longitude", Double.toString(currentLocation.getLongitude()));
                }
                return params;
            }
        };

        queue.add(request);
    }

    public void onFree(View view) {
        statusOrd = 0;
    }

    public void onMoving(View view) {
        statusOrd = 1;
    }

    public void onTreatment(View view) {
        statusOrd = 2;
    }

    public void onTransporting(View view) {
        statusOrd = 3;
    }

    public void onBreak(View view) {
        statusOrd = 4;
    }

    private boolean isNewLocationBetter(Location newLoc) {
        return currentLocation == null || (newLoc.getTime() - currentLocation.getTime() > 1000);
    }

    private void updateLocation(Location newLoc) {
        this.currentLocation = newLoc;
        TextView textCoord = (TextView) findViewById(R.id.textCoord);
        textCoord.setText(String.format("Coord: %s; %s", currentLocation.getLatitude(), currentLocation.getLongitude()));
    }

    private String getUrl(String suffix) {
        return ((EditText) findViewById(R.id.fldPrefix)).getText() + suffix;
    }

    private void update() {
        String numerStr = ((EditText) findViewById(R.id.fldNumber)).getText().toString();
        if (numerStr != null && !numerStr.isEmpty()) {
            String url = getUrl("rest/crew/update_status");
            Map<String, String> params = new HashMap<>();
            params.put("crewId", numerStr);
            params.put("status", Integer.toString(statusOrd));
            if (currentLocation != null) {
                params.put("lat", Double.toString(currentLocation.getLatitude()));
                params.put("lng", Double.toString(currentLocation.getLongitude()));
            }
            Request<JSONObject> request = new CustomRequest(Request.Method.POST, url, params, new Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    ((TextView) findViewById(R.id.textMain)).setText(response.toString());
                    try {
                        String status = response.getString("status");
                        if (status.equals("FREE")) {
                            ((TextView) findViewById(R.id.textStatus)).setText("Статус: свободен");
                            ((Button) findViewById(R.id.btnMoving)).setEnabled(true);
                            ((Button) findViewById(R.id.btnTreatment)).setEnabled(false);
                            ((Button) findViewById(R.id.btnTransportation)).setEnabled(false);
                            ((Button) findViewById(R.id.btnFree)).setEnabled(false);
                        } else if (status.equals("MOVING")) {
                            ((TextView) findViewById(R.id.textStatus)).setText("Статус: в пути");
                            ((Button) findViewById(R.id.btnMoving)).setEnabled(false);
                            ((Button) findViewById(R.id.btnTreatment)).setEnabled(true);
                            ((Button) findViewById(R.id.btnTransportation)).setEnabled(false);
                            ((Button) findViewById(R.id.btnFree)).setEnabled(false);
                        } else if (status.equals("TREATMENT")) {
                            ((TextView) findViewById(R.id.textStatus)).setText("Статус: на месте");
                            ((Button) findViewById(R.id.btnMoving)).setEnabled(false);
                            ((Button) findViewById(R.id.btnTreatment)).setEnabled(false);
                            ((Button) findViewById(R.id.btnTransportation)).setEnabled(true);
                            ((Button) findViewById(R.id.btnFree)).setEnabled(true);
                        } else if (status.equals("TRANSPORTING")) {
                            ((TextView) findViewById(R.id.textStatus)).setText("Статус: транспортировка");
                            ((Button) findViewById(R.id.btnMoving)).setEnabled(false);
                            ((Button) findViewById(R.id.btnTreatment)).setEnabled(false);
                            ((Button) findViewById(R.id.btnTransportation)).setEnabled(false);
                            ((Button) findViewById(R.id.btnFree)).setEnabled(true);
                        } else if (status.equals("BREAK")) {
                            ((TextView) findViewById(R.id.textStatus)).setText("Статус: занят");
                            ((Button) findViewById(R.id.btnMoving)).setEnabled(false);
                            ((Button) findViewById(R.id.btnTreatment)).setEnabled(false);
                            ((Button) findViewById(R.id.btnTransportation)).setEnabled(false);
                            ((Button) findViewById(R.id.btnFree)).setEnabled(true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ((TextView) findViewById(R.id.textMain)).setText("Ops... error");
                }
            });

            Volley.newRequestQueue(MainActivity.this).add(request);
        }
    }
}
