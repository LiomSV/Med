package by.bsu.vstasenia.med.server.endpoint;

import by.bsu.vstasenia.med.server.db.DataBase;
import by.bsu.vstasenia.med.server.entity.Call;
import by.bsu.vstasenia.med.server.entity.Crew;
import by.bsu.vstasenia.med.server.model.*;
import com.google.maps.model.LatLng;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static by.bsu.vstasenia.med.server.StringResponse.OK;

@Path("/dev")
public class DevEndPoint extends BasicEndPoint {

    @GET
    @Path("/init_db")
    @Produces(MediaType.TEXT_PLAIN)
    public String initDB() {
        initCrews();
        initCalls();
        return OK;
    }

    private void initCrews() {
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
        DataBase.addCrew(crew);

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
        DataBase.addCrew(crew);

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
        DataBase.addCrew(crew);
    }

    private void initCalls() {
        Call call = new Call();
        call.setStatus(CallStatus.AWAITING);
        call.setType(CallType.URGENT);
        call.setInitAddress("Ольшевского 17 Минск");
        call.setAddress("ул. Ольшевского 17, Минск, Беларусь");
        call.setLocation(new LatLng(53.9141041, 27.5024981));
        call.setPhoneNumber("+375 21 489 0847");
        call.setFirstname("Иван");
        call.setFathername("Сидорович");
        call.setLastname("Потемкин");
        call.setSex(Sex.MALE);
        call.setBirthDate(LocalDate.of(1977, 7, 23));
        call.setReason("Носовое кровотечение");
        call.setComment("Во дворе ремонт, заезд затруднен.");
        DataBase.addCall(call);

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
        DataBase.addCall(call);

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
        DataBase.addCall(call);

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
        DataBase.addCall(call);

    }


}
