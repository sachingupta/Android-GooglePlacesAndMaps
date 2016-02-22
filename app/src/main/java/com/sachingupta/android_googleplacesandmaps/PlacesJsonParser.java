package com.sachingupta.android_googleplacesandmaps;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlacesJsonParser {

    public List<Place> parse(JSONObject jsonObject) {
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }

    private List<Place> getPlaces(JSONArray jsonArray) {
        int placesCount = jsonArray.length();
        List<Place> placesList = new ArrayList<Place>();

        for (int i = 0; i < placesCount; i++) {
            try {
                Place place = getPlace((JSONObject) jsonArray.get(i));
                if(place != null) {
                    placesList.add(place);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placesList;
    }

    private Place getPlace(JSONObject googlePlaceJson) {
        Place place = null;
        String latitude = "";
        String longitude = "";

        try {
            if (!googlePlaceJson.isNull("name")) {
                place = new Place();
                place.name = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("vicinity")) {
                place.vicinity = googlePlaceJson.getString("vicinity");
            }
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            place.latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
            place.reference = googlePlaceJson.getString("reference");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return place;
    }
}