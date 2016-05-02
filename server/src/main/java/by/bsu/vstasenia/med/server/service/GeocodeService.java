package by.bsu.vstasenia.med.server.service;

import by.bsu.vstasenia.med.server.Const;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.ComponentFilter;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

public class GeocodeService {

    private static final GeoApiContext context = new GeoApiContext().setApiKey(Const.GOOGLE_API_KEY);

    public static AddressLocation geocode(String address) {
        GeocodingResult[] results = new GeocodingResult[0];
        try {
            results = GeocodingApi.geocode(context, address).language("ru").region("by").components(ComponentFilter.country("BY")).await();
        } catch (Exception e) {
            System.out.println("Geocoding error!");
            e.printStackTrace();
        }
        if (results.length > 0) {
            String formattedAddress = results[0].formattedAddress;
            LatLng location = results[0].geometry.location;
            System.out.println("Geocoding succeed: " + address + " -> " + location + ": " + formattedAddress);
            return new AddressLocation(formattedAddress, location);
        } else {
            System.out.println("Geocoding NOT succeed: " + address);
            return null;
        }
    }

    public static class AddressLocation {
        private String address;
        private LatLng location;

        public AddressLocation() {
        }

        public AddressLocation(String address, LatLng location) {
            this.address = address;
            this.location = location;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public LatLng getLocation() {
            return location;
        }

        public void setLocation(LatLng location) {
            this.location = location;
        }
    }

}
