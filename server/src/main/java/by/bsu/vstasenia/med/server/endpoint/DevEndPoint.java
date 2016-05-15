package by.bsu.vstasenia.med.server.endpoint;

import by.bsu.vstasenia.med.server.db.DataBase;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static by.bsu.vstasenia.med.server.StringResponse.OK;

@Path("/dev")
public class DevEndPoint extends BasicEndPoint {

    @GET
    @Path("/init_db")
    @Produces(MediaType.TEXT_PLAIN)
    public String initDB() {
        DataBase.init();
        return OK;
    }




}
