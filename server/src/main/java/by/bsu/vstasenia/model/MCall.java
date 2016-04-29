package by.bsu.vstasenia.model;

import com.google.maps.model.LatLng;

/**
 *  Created by vstasenia on 4/20/16.
 */
public class MCall {

    private Integer id;
    private String firstname;
    private String fathername;
    private String lastname;
    private String phoneNumber;
    private String address;
    private String reason;
    private String comment;
    private LatLng location;


    public MCall() {
    }

    public MCall(Integer id, String firstname, String fathername, String lastname, String phoneNumber, String address, String reason, String comment, LatLng location) {
        this.id = id;
        this.firstname = firstname;
        this.fathername = fathername;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.reason = reason;
        this.comment = comment;
        this.location = location;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
