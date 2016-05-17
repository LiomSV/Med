package by.bsu.vstasenia.med.server.db;

import by.bsu.vstasenia.med.server.entity.Call;
import by.bsu.vstasenia.med.server.entity.Crew;
import by.bsu.vstasenia.med.server.model.*;
import com.google.maps.model.LatLng;

import java.time.LocalDate;
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

    public static List<Crew> getAllFreeCrews() {
        List<Crew> result = instance.crews.values().stream().filter(o -> o.getStatus() == CrewStatus.FREE).sorted((o1, o2) -> o1.getId() - o2.getId()).collect(Collectors.toList());
        System.out.println("Database.getAllCrews(): resultSize=" + result.size());
        return result;
    }

    public static void init() {
        initCrews();
        initCalls();
    }

    private static void initCrews() {
        instance.crews = new HashMap<>();

        List<String> members = new ArrayList<>();
        members.add("Коробов И.С.");
        members.add("Синицев А.П.");
        members.add("Артищева Е.К.");
        Crew crew = new Crew();
        crew.setStatus(CrewStatus.FREE);
        crew.setType(CrewType.DOCTOR);
        crew.setDriver("Терещенко А.В.");
        crew.setMembers(members);
        crew.setCarNumber("7489 АВ 7");
        crew.setLocation(new LatLng(53.909094, 27.490041));
        addCrew(crew);

        members = new ArrayList<>();
        members.add("Ильес А.К.");
        members.add("Протопов Т.В.");
        crew = new Crew();
        crew.setStatus(CrewStatus.FREE);
        crew.setType(CrewType.DOCTOR);
        crew.setDriver("Бобр Е.И.");
        crew.setMembers(members);
        crew.setCarNumber("1553 СВ 7");
        crew.setLocation(new LatLng(53.868068, 27.601392));
        addCrew(crew);

        members = new ArrayList<>();
        members.add("Курипова Л.Т.");
        members.add("Гребенков Д.В.");
        crew = new Crew();
        crew.setStatus(CrewStatus.FREE);
        crew.setType(CrewType.PARAMEDIC);
        crew.setDriver("Семенцов П.К.");
        crew.setMembers(members);
        crew.setCarNumber("9871 IО 7");
        crew.setLocation(new LatLng(53.9162540, 27.543196));
        addCrew(crew);
    }

    private static void initCalls() {
        instance.calls = new ArrayList<>();

        Call call = new Call();
        call.setStatus(CallStatus.AWAITING);
        call.setType(CallType.URGENT);
        call.setInitAddress("Чигладзе 10 Минск");
        call.setAddress("ул. Чигладзе 10, Минск, Беларусь");
        call.setLocation(new LatLng(53.9258279, 27.4991843));
        call.setPhoneNumber("+375 21 489 0847");
        call.setFirstname("Иван");
        call.setFathername("Сидорович");
        call.setLastname("Потемкин");
        call.setSex(Sex.MALE);
        call.setBirthDate(LocalDate.of(1977, 7, 23));
        call.setReason("Носовое кровотечение");
        call.setComment("Во дворе ремонт, заезд затруднен.");
        addCall(call);

        call = new Call();
        call.setStatus(CallStatus.AWAITING);
        call.setType(CallType.URGENT);
        call.setInitAddress("улица Гамарника 35 Минск");
        call.setAddress("ул. Гамарника 35, Минск, Беларусь");
        call.setLocation(new LatLng(53.9546943, 27.6016098));
        call.setPhoneNumber("+375 21 347 6579");
        call.setFirstname("Алексей");
        call.setFathername("Романович");
        call.setLastname("Русецкий");
        call.setSex(Sex.MALE);
        call.setBirthDate(LocalDate.of(1984, 3, 11));
        call.setReason("Боль в животе");
        call.setComment("");
        addCall(call);

        call = new Call();
        call.setStatus(CallStatus.AWAITING);
        call.setType(CallType.IMMEDIATE);
        call.setInitAddress("улица Филимонова 74 Минск");
        call.setAddress("ул. Филимонова 74, Минск, Беларусь");
        call.setLocation(new LatLng(53.9332807, 27.6376714));
        call.setPhoneNumber("741 14 98");
        call.setFirstname("Елена");
        call.setFathername("");
        call.setLastname("");
        call.setSex(Sex.FEMALE);
        call.setBirthDate(LocalDate.of(1989, 11, 19));
        call.setReason("Потеря сознания");
        call.setComment("");
        addCall(call);

        call = new Call();
        call.setStatus(CallStatus.AWAITING);
        call.setType(CallType.EMERGENT);
        call.setInitAddress("Янки Брыля 24 Минск");
        call.setAddress("ул. Янки Брыля 24, Минск, Беларусь");
        call.setLocation(new LatLng(53.878705, 27.483481));
        call.setPhoneNumber("+375 74 121 96 57");
        call.setFirstname("Антон");
        call.setFathername("Антонович");
        call.setLastname("Морозов");
        call.setSex(Sex.MALE);
        call.setBirthDate(LocalDate.of(1991, 6, 4));
        call.setReason("Ножевое ранение");
        call.setComment("");
        addCall(call);

    }

}
