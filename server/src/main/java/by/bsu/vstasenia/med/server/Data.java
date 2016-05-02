package by.bsu.vstasenia.med.server;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *  Created by vstasenia on 4/19/16.
 */
@Path("/data")
public class Data {

    @GET
    @Path("/{addr}")
    public String get(@PathParam("addr") String addr) throws Exception {
        System.out.println("get(): addr=" + addr);
        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyB_Sc0dGO6oDWND1FwMMI1RTDexCq8ULbk");
        GeocodingResult[] results =  GeocodingApi.geocode(context,
                "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
        System.out.println(results[0].formattedAddress);
        results =  GeocodingApi.geocode(context, addr).await();
        System.out.println(results[0].formattedAddress);
        System.out.println(results[0].geometry.location);
        return "GET!!!";
    }

}
