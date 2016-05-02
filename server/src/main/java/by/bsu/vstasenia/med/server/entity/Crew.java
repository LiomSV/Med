package by.bsu.vstasenia.med.server.entity;

import by.bsu.vstasenia.med.server.model.CrewStatus;
import by.bsu.vstasenia.med.server.model.CrewType;
import com.google.maps.model.LatLng;

import java.util.List;

public class Crew {

    private Integer id;
    private CrewStatus status;
    private CrewType type;
    private String driver;
    private List<String> members;
    private String carNumber;
    private LatLng location;
    private Call call;

    public Crew() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CrewStatus getStatus() {
        return status;
    }

    public void setStatus(CrewStatus status) {
        this.status = status;
    }

    public CrewType getType() {
        return type;
    }

    public void setType(CrewType type) {
        this.type = type;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    @Override
    public String toString() {
        return "Crew{" +
                "id=" + id +
                ", status=" + status +
                ", type=" + type +
                ", driver='" + driver + '\'' +
                ", members=" + members +
                ", carNumber='" + carNumber + '\'' +
                ", location=" + location +
                ", call=" + call +
                '}';
    }
}
