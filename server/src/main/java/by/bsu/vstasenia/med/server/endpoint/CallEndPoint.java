package by.bsu.vstasenia.med.server.endpoint;

import by.bsu.vstasenia.med.server.db.DataBase;
import by.bsu.vstasenia.med.server.entity.Call;
import by.bsu.vstasenia.med.server.entity.Crew;
import by.bsu.vstasenia.med.server.model.CallStatus;
import by.bsu.vstasenia.med.server.model.CallType;
import by.bsu.vstasenia.med.server.model.CrewStatus;
import by.bsu.vstasenia.med.server.model.DistanceToObject;
import by.bsu.vstasenia.med.server.service.DistanceService;
import by.bsu.vstasenia.med.server.service.GeocodeService;

import javax.ws.rs.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static by.bsu.vstasenia.med.server.StringResponse.ERROR;
import static by.bsu.vstasenia.med.server.StringResponse.OK;

@Path("/call")
public class CallEndPoint extends BasicEndPoint {

    /* TODO:
        call?id                         GET     $$$
        call/add                        POST    $$$
        call/add_passing                POST
        call/assign                     POST    $$$
        call/update                     POST
        call/remove?id                  POST
        call/restore?id                 POST
        call/list?[type]                GET     $
        сall/history?[offset]&[limit]   GET
        call/advice_crew                POST
     */

    @GET
    @Produces("application/json")
    public Call getCall(@QueryParam("id") int id) {
        addACAO();
        return DataBase.getCall(id);
    }



    @POST
    @Path("/add")
    @Produces("text/plain")
    public String add(@FormParam("firstname") String firstname,
                      @FormParam("fathername") String fathername,
                      @FormParam("lastname") String lastname,
                      @FormParam("birthDate") String birthDateStr,
                      @FormParam("phoneNumber") String phoneNumber,
                      @FormParam("address") String address,
                      @FormParam("reason") String reason,
                      @FormParam("type") Integer typeOrd,
                      @FormParam("comment") String comment) {
        addACAO();
        if (address == null || address.isEmpty())
            return ERROR;
        Call call = new Call();
        call.setStatus(CallStatus.AWAITING);
        call.setType(typeOrd == null ? CallType.URGENT : CallType.values()[typeOrd]);
        call.setInitAddress(address);
        call.setPhoneNumber(phoneNumber);
        call.setFirstname(firstname);
        call.setFathername(fathername);
        call.setLastname(lastname);
        // call.setSex(sex);
        if (birthDateStr != null) {
            call.setBirthDate(LocalDate.parse(birthDateStr));
        }
        call.setReason(reason);
        call.setComment(comment);
        call.setAwaitingStartedAt(LocalDateTime.now());

        GeocodeService.AddressLocation addLoc = GeocodeService.geocode(address);
        if (addLoc != null) {
            call.setAddress(addLoc.getAddress());
            call.setLocation(addLoc.getLocation());
        }
        DataBase.addCall(call);
        return call.getId().toString();
    }

    @POST
    @Path("/assign")
    @Produces("text/plain")
    public String assign(@FormParam("callId") int callId,
                         @FormParam("crewId") int crewId) {
        addACAO();
        Call call = DataBase.getCall(callId);
        Crew crew = DataBase.getCrew(crewId);

        call.setStatus(CallStatus.MOVING_TO);
        call.setMovingStartedAt(LocalDateTime.now());
        if (call.getCrewId() != null) {
            Crew replacedCrew = DataBase.getCrew(call.getCrewId());
            replacedCrew.setStatus(CrewStatus.FREE);
            replacedCrew.setCallId(null);
        }
        call.setCrewId(crew.getId());

        crew.setStatus(CrewStatus.MOVING);
        if (crew.getCallId() != null) {
            Call replacedCall = DataBase.getCall(crew.getCallId());
            replacedCall.setStatus(CallStatus.AWAITING);
            replacedCall.setCrewId(null);
        }
        crew.setCallId(call.getId());

        return OK;
    }

    @GET
    @Path("/list")
    @Produces("application/json")
    public List<Call> list() {
        addACAO();
        return DataBase.getAllActiveCalls();
    }

    @POST
    @Path("/advice_crew")
    @Produces("application/json")
    public List<DistanceToObject<Crew>> adviceCrew(@FormParam("callId") int callId) {
        addACAO();
        Call call = DataBase.getCall(callId);
        if (call == null) {
            return null;
        }
        return DistanceService.findClosestCrews(call, DataBase.getAllFreeCrews());
    }

}
