package by.bsu.vstasenia.med.server.db;

import by.bsu.vstasenia.med.server.entity.Call;
import by.bsu.vstasenia.med.server.entity.Crew;
import by.bsu.vstasenia.med.server.model.CallStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataBase {

    private static DataBase instance = new DataBase();

    private List<Call> calls = new ArrayList<>();
    private Map<Integer, Crew> crews = new HashMap<>();

    private int crewIdSeq = 0;

    private DataBase() {
    }

    public static Call getCall(int id) {
        List<Call> filtered = instance.calls.stream().filter(call -> call.getId().equals(id)).collect(Collectors.toList());
        Call result = filtered.size() > 0 ? filtered.get(0) : null;
        System.out.println("Database.getCall(): id=" + id + "; result=" + result);
        return result;
    }

    public static void addCall(Call call) {
        call.setId(instance.calls.size());
        instance.calls.add(call);
        System.out.println("Database.addCall(): call=" + call);
    }

    public static List<Call> getAllActiveCalls() {
        List<Call> result = instance.calls.stream().filter(call -> call.getStatus() != CallStatus.FINISHED).collect(Collectors.toList());
        System.out.println("Database.getAllActiveCalls(): resultSize=" + result.size());
        return result;
    }

    public static List<Call> getAllFinishedCalls() {
        List<Call> result = instance.calls.stream().filter(call -> call.getStatus() == CallStatus.FINISHED).collect(Collectors.toList());
        System.out.println("Database.getAllFinishedCalls(): resultSize=" + result.size());
        return result;
    }

    public static Crew getCrew(int id) {
        Crew result = instance.crews.get(id);
        System.out.println("Database.getCrew(): id=" + id + "; result=" + result);
        return result;
    }

    public static int addCrew(Crew crew) {
        int id = instance.crewIdSeq++;
        crew.setId(id);
        instance.crews.put(id, crew);
        System.out.println("Database.addCrew(): crew=" + crew);
        return id;
    }

    public static void removeCrew(int id) {
        System.out.println("Database.removeCrew(): id=" + id);
        instance.crews.remove(id);
    }

    public static List<Crew> getAllCrews() {
        List<Crew> result = instance.crews.values().stream().sorted((o1, o2) -> o1.getId() - o2.getId()).collect(Collectors.toList());
        System.out.println("Database.getAllCrews(): resultSize=" + result.size());
        return result;
    }

}
