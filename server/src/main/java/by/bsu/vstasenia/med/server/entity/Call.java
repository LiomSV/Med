package by.bsu.vstasenia.med.server.entity;

import by.bsu.vstasenia.med.server.model.CallStatus;
import by.bsu.vstasenia.med.server.model.CallType;
import by.bsu.vstasenia.med.server.model.Sex;
import com.google.maps.model.LatLng;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Call {

    private Integer id;
    private CallStatus status;
    private CallType type;
    private String initAddress;
    private String address;
    private LatLng location;
    private String phoneNumber;
    private String firstname;
    private String fathername;
    private String lastname;
    private Sex sex;
    private LocalDate birthDate;
    private String reason;
    private String comment;
    private LocalDateTime awaitingStartedAt;
    private LocalDateTime movingStartedAt;
    private LocalDateTime treatmentStartedAt;
    private LocalDateTime transportationStartedAt;
    private LocalDateTime finishedAt;
    private Crew crew;

    public Call() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CallStatus getStatus() {
        return status;
    }

    public void setStatus(CallStatus status) {
        this.status = status;
    }

    public CallType getType() {
        return type;
    }

    public void setType(CallType type) {
        this.type = type;
    }

    public String getInitAddress() {
        return initAddress;
    }

    public void setInitAddress(String initAddress) {
        this.initAddress = initAddress;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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

    public LocalDateTime getAwaitingStartedAt() {
        return awaitingStartedAt;
    }

    public void setAwaitingStartedAt(LocalDateTime awaitingStartedAt) {
        this.awaitingStartedAt = awaitingStartedAt;
    }

    public LocalDateTime getMovingStartedAt() {
        return movingStartedAt;
    }

    public void setMovingStartedAt(LocalDateTime movingStartedAt) {
        this.movingStartedAt = movingStartedAt;
    }

    public LocalDateTime getTreatmentStartedAt() {
        return treatmentStartedAt;
    }

    public void setTreatmentStartedAt(LocalDateTime treatmentStartedAt) {
        this.treatmentStartedAt = treatmentStartedAt;
    }

    public LocalDateTime getTransportationStartedAt() {
        return transportationStartedAt;
    }

    public void setTransportationStartedAt(LocalDateTime transportationStartedAt) {
        this.transportationStartedAt = transportationStartedAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Crew getCrew() {
        return crew;
    }

    public void setCrew(Crew crew) {
        this.crew = crew;
    }

    @Override
    public String toString() {
        return "Call{" +
                "id=" + id +
                ", status=" + status +
                ", type=" + type +
                ", initAddress='" + initAddress + '\'' +
                ", address='" + address + '\'' +
                ", location=" + location +
                ", firstname='" + firstname + '\'' +
                ", fathername='" + fathername + '\'' +
                ", lastname='" + lastname + '\'' +
                ", sex=" + sex +
                ", birthDate=" + birthDate +
                ", reason='" + reason + '\'' +
                ", comment='" + comment + '\'' +
                ", awaitingStartedAt=" + awaitingStartedAt +
                ", movingStartedAt=" + movingStartedAt +
                ", treatmentStartedAt=" + treatmentStartedAt +
                ", transportationStartedAt=" + transportationStartedAt +
                ", finishedAt=" + finishedAt +
                ", crew=" + crew +
                '}';
    }
}
