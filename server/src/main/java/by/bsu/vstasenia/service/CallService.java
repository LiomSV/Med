package by.bsu.vstasenia.service;

import by.bsu.vstasenia.Const;
import by.bsu.vstasenia.Data;
import by.bsu.vstasenia.db.DataBase;
import by.bsu.vstasenia.entity.Call;
import by.bsu.vstasenia.model.MCall;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.ComponentFilter;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static by.bsu.vstasenia.StringResponse.ERROR;
import static by.bsu.vstasenia.StringResponse.OK;

/**
 *  Created by vstasenia on 4/20/16.
 */
@Path("/call")
public class CallService {

    @Context
    org.jboss.resteasy.spi.HttpResponse response;

    @POST
    @Path("/add")
    @Produces("text/plain")
    public String add(@FormParam("firstname") String firstname,
                      @FormParam("fathername") String fathername,
                      @FormParam("lastname") String lastname,
                      @FormParam("phoneNumber") String phoneNumber,
                      @FormParam("address") String address,
                      @FormParam("reason") String reason,
                      @FormParam("comment") String comment) {
        response.getOutputHeaders().putSingle("Access-Control-Allow-Origin", "*");
        if (address == null || address.isEmpty())
            return ERROR;
        try {
            GeoApiContext context = new GeoApiContext().setApiKey(Const.GOOGLE_API_KEY);
            GeocodingResult[] results = GeocodingApi.geocode(context, address).language("ru").region("by").components(ComponentFilter.country("BY")).await();
            if (results.length > 0) {
                DataBase.addCall(new Call(firstname, fathername, lastname, phoneNumber, address, reason, comment, results[0]));
                for (AddressComponent comp : results[ 0].addressComponents) {
                    System.out.println(comp.shortName + "; " + comp.longName + "; " + Arrays.toString(comp.types));
                }
                return OK;
            } else {
                return ERROR;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    @POST
    @Path("/add_coor")
    @Produces("text/plain")
    public String addWithCoordinates(@FormParam("firstname") String firstname,
                      @FormParam("fathername") String fathername,
                      @FormParam("lastname") String lastname,
                      @FormParam("phoneNumber") String phoneNumber,
                      @FormParam("reason") String reason,
                      @FormParam("comment") String comment,
                      @FormParam("latitude") Double latitude,
                      @FormParam("longitude") Double longitude) {
        response.getOutputHeaders().putSingle("Access-Control-Allow-Origin", "*");
        if (latitude == null || longitude == null)
            return ERROR;
        DataBase.addOtherCall(new MCall(null, firstname, fathername, lastname, phoneNumber, "no address", reason, comment, new LatLng(latitude, longitude)));
        return OK;
    }

    @GET
    @Path("/getall")
    @Produces("application/json")
    public List<MCall> getAllCalls() {
        List<MCall> result = new ArrayList<>();
        for (Call call : DataBase.getCalls()) {
            result.add(new MCall(call.getId(), call.getFirstname(), call.getFathername(), call.getLastname(), call.getPhoneNumber(), call.getGeo().formattedAddress, call.getReason(), call.getComment(), call.getGeo().geometry.location));
        }
        result.addAll(DataBase.getOtherCalls());
        response.getOutputHeaders().putSingle("Access-Control-Allow-Origin", "*");
        return result;
    }

}
