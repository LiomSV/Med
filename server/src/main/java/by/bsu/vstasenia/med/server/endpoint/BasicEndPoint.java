package by.bsu.vstasenia.med.server.endpoint;


import javax.ws.rs.core.Context;

public class BasicEndPoint {

    @Context
    protected org.jboss.resteasy.spi.HttpResponse response;

    protected void addACAO() {
        response.getOutputHeaders().putSingle("Access-Control-Allow-Origin", "*");
    }

}
