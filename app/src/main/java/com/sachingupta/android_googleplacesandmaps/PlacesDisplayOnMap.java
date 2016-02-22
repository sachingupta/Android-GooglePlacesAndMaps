package com.sachingupta.android_googleplacesandmaps;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;
import java.util.List;

public class PlacesDisplayOnMap extends AsyncTask<Object, Integer, List<Place>> {

    JSONObject placesJsonResponse;
    GoogleMap googleMap;

    @Override
    protected List<Place> doInBackground(Object... inputObj) {

        List<Place> googlePlacesList = null;
        PlacesJsonParser placeJsonParser = new PlacesJsonParser();

        try {
            googleMap = (GoogleMap) inputObj[0];
            placesJsonResponse = new JSONObject((String) inputObj[1]);
            googlePlacesList = placeJsonParser.parse(placesJsonResponse);
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
        return googlePlacesList;
    }

    @Override
    protected void onPostExecute(List<Place> placeList) {
        googleMap.clear();
        if(placeList.size() > 0) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(placeList.get(0).latLng));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        }
        for (int i = 0; i < placeList.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            Place place = placeList.get(i);
            String placeName = place.name;
            String vicinity = place.vicinity;
            LatLng latLng = place.latLng;
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : " + vicinity);
            googleMap.addMarker(markerOptions);
        }
    }
}
