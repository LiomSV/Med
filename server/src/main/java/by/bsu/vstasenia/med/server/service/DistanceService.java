package by.bsu.vstasenia.med.server.service;

import by.bsu.vstasenia.med.server.Const;
import by.bsu.vstasenia.med.server.entity.Call;
import by.bsu.vstasenia.med.server.entity.Crew;
import by.bsu.vstasenia.med.server.entity.LocationObject;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.*;

import java.util.ArrayList;
import java.util.List;

public class DistanceService {

    private static final GeoApiContext context = new GeoApiContext().setApiKey(Const.GOOGLE_API_KEY);

    public static List<Crew> findClosestCrews(Call origin, List<Crew> destinations) {
        LatLng[] destLocations = new LatLng[destinations.size()];
        int i = 0;
        for (LocationObject destination : destinations) {
            destLocations[i++] = destination.getLocation();
        }
        try {
            DistanceMatrix distanceMatrix = DistanceMatrixApi.newRequest(context).origins(origin.getLocation()).destinations(destLocations).mode(TravelMode.DRIVING).units(Unit.METRIC).await();
            if (distanceMatrix.rows.length != 1) {
                throw new IllegalStateException("One origin, but rows.length != 1");
            }
            DistanceMatrixElement[] elements = distanceMatrix.rows[0].elements;
            List<IndexMeters> sortList = new ArrayList<>();
            for (i = 0; i < elements.length; i++) {
                sortList.add(new IndexMeters(i, elements[i].distance.inMeters));
            }
            sortList.sort((o1, o2) -> (o1.meters - o2.meters) == 0 ? o1.index - o2.index : Long.compare(o1.meters, o2.meters));

            List<Crew> result = new ArrayList<>();
            for (IndexMeters im : sortList) {
                result.add(destinations.get(im.index));
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static class IndexMeters {
        int index;
        long meters;

        public IndexMeters() {
        }

        public IndexMeters(int index, long meters) {
            this.index = index;
            this.meters = meters;
        }
    }

}
