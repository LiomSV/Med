package by.bsu.vstasenia.db;

import by.bsu.vstasenia.entity.Call;
import by.bsu.vstasenia.model.MCall;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by vstasenia on 4/20/16.
 */
public class DataBase {

    private static DataBase instance = new DataBase();

    private List<Call> calls = new ArrayList<>();
    private List<MCall> otherCalls = new ArrayList<>();

    private DataBase() {
    }

    public static void addCall(Call call) {
        call.setId(instance.calls.size());
        instance.calls.add(call);
        System.out.println("Added new call: id=" + call.getId() + "; " + call.getGeo().geometry.location + "; " + call.getGeo().formattedAddress);
    }

    public static List<Call> getCalls() {
        return new ArrayList<>(instance.calls);
    }

    public static void addOtherCall(MCall call) {
        call.setId(1000 + instance.otherCalls.size());
        instance.otherCalls.add(call);
        System.out.println("Added new other call: id=" + call.getId() + "; " + call.getLocation() + ";");
    }

    public static List<MCall> getOtherCalls() {
        return new ArrayList<>(instance.otherCalls);
    }

}
