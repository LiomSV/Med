package by.bsu.vstasenia.med.server.entity;

import com.google.maps.model.LatLng;

public class LocationObject {

    private Integer id;
    private LatLng location;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
