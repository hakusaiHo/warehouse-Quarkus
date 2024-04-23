package wareHouse.Vehicle.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@ApplicationScoped
public class VehicleService {

    @Inject
    EntityManager em;

    public boolean existsByVehicleId(String vehicleId) {
        Query query = em.createQuery("SELECT count(vm) FROM VehicleMaster vm WHERE vm.vehicleId = :vehicleId");
        query.setParameter("vehicleId", vehicleId);
        long count = (long) query.getSingleResult();
        return count > 0;
    }
}