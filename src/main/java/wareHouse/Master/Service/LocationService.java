package wareHouse.Master.Service;

import java.util.Collections;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import wareHouse.Master.Dao.Location.Location;
import wareHouse.Master.Dao.Location.LocationPK;

@ApplicationScoped
public class LocationService {

    public List<Location> getLocationList(String branch, String locationId) {
        LocationPK pk = new LocationPK();
        pk.locationId = locationId;
        pk.branch = branch;
        Location foundList = Location.findById(pk);
        if (foundList != null) {
            return List.of(foundList);
        } else {
            return Collections.emptyList();
        }
    }
}
