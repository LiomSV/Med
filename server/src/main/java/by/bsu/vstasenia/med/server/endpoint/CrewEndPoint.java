package by.bsu.vstasenia.med.server.endpoint;

import by.bsu.vstasenia.med.server.db.DataBase;
import by.bsu.vstasenia.med.server.entity.Crew;
import by.bsu.vstasenia.med.server.model.CrewStatus;
import by.bsu.vstasenia.med.server.model.CrewType;
import com.google.maps.model.LatLng;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static by.bsu.vstasenia.med.server.StringResponse.ERROR;
import static by.bsu.vstasenia.med.server.StringResponse.OK;

@Path("/crew")
public class CrewEndPoint extends BasicEndPoint {

    /*
        crew/add                        POST        $$$
        crew/remove?id                  POST        $$$
        crew/list                       GET         $$$
        // crew/assign_call                POST
        crew/register                   POST
        crew/check                      POST
        crew/update_status              POST        $$$
     */

    @POST
    @Path("/add")
    @Produces(MediaType.TEXT_PLAIN)
    public String add(@FormParam("type") Integer typeOrd,
                             @FormParam("driver") String driver,
                             @FormParam("member") List<String> members,
                             @FormParam("carNumber") String carNumber) {
        addACAO();
        if (driver == null || driver.isEmpty()) {
            return ERROR;
        }
        if (members == null || members.size() == 0) {
            return ERROR;
        }
        if (carNumber == null || carNumber.isEmpty()) {
            return ERROR;
        }
        Crew crew = new Crew();
        crew.setStatus(CrewStatus.FREE);
        crew.setType(typeOrd == null ? CrewType.PARAMEDIC : CrewType.values()[typeOrd]);
        crew.setDriver(driver);
        crew.setMembers(new ArrayList<>(members));
        crew.setCarNumber(carNumber);
        return Integer.toString(DataBase.addCrew(crew));
    }

    @POST
    @Path("/remove")
    @Produces(MediaType.TEXT_PLAIN)
    public String removeCrew(@FormParam("id") Integer id) {
        addACAO();
        if (id == null || id < 0) {
            return ERROR;
        }
        DataBase.removeCrew(id);
        return OK;
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Crew> list() {
        addACAO();
        return DataBase.getAllCrews();
    }

    @POST
    @Path("/update_status")
    @Produces(MediaType.APPLICATION_JSON)
    public Crew updateStatus(@FormParam("crewId") int crewId,
                             @FormParam("status") int statusOrd,
                             @FormParam("lat") double lat,
                             @FormParam("lng") double lng) {
        addACAO();
        Crew crew = DataBase.getCrew(crewId);
        crew.setStatus(CrewStatus.values()[statusOrd]);
        crew.setLocation(new LatLng(lat, lng));
        return crew;
    }

}
