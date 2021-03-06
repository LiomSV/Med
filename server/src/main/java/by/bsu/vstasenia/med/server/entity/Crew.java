package by.bsu.vstasenia.med.server.entity;

import by.bsu.vstasenia.med.server.model.CrewStatus;
import by.bsu.vstasenia.med.server.model.CrewType;

import java.util.List;

public class Crew extends LocationObject {

    private CrewStatus status;
    private CrewType type;
    private String driver;
    private List<String> members;
    private String carNumber;
    private Integer callId;

    public Crew() {
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

    public Integer getCallId() {
        return callId;
    }

    public void setCallId(Integer callId) {
        this.callId = callId;
    }

    @Override
    public String toString() {
        return "Crew{" +
                "id=" + super.getId() +
                ", status=" + status +
                ", type=" + type +
                ", driver='" + driver + '\'' +
                ", members=" + members +
                ", carNumber='" + carNumber + '\'' +
                ", location=" + super.getLocation() +
                ", callId=" + callId +
                '}';
    }
}
