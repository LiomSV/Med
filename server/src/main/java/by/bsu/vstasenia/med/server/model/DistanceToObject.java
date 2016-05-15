package by.bsu.vstasenia.med.server.model;

import by.bsu.vstasenia.med.server.entity.LocationObject;

public class DistanceToObject<T extends LocationObject> {

    private long meters;
    private T object;

    public DistanceToObject() {
    }

    public DistanceToObject(long meters, T object) {
        this.meters = meters;
        this.object = object;
    }

    public long getMeters() {
        return meters;
    }

    public void setMeters(long meters) {
        this.meters = meters;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
