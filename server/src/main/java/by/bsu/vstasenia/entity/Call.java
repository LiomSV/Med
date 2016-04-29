package by.bsu.vstasenia.entity;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

/**
 *  Created by vstasenia on 4/20/16.
 */
public class Call {

    private Integer id;
    private String firstname;
    private String fathername;
    private String lastname;
    private String phoneNumber;
    private String initAddress;
    private String reason;
    private String comment;
    private GeocodingResult geo;

    public Call() {
    }

    public Call(String firstname, String fathername, String lastname, String phoneNumber, String initAddress, String reason, String comment, GeocodingResult geo) {
        this.firstname = firstname;
        this.fathername = fathername;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.initAddress = initAddress;
        this.reason = reason;
        this.comment = comment;
        this.geo = geo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInitAddress() {
        return initAddress;
    }

    public void setInitAddress(String initAddress) {
        this.initAddress = initAddress;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public GeocodingResult getGeo() {
        return geo;
    }

    public void setGeo(GeocodingResult geo) {
        this.geo = geo;
    }
}
