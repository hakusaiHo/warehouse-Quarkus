package wareHouse.Vehicle.Service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import wareHouse.Vehicle.Dao.VehicleType.VehicleType;

@ApplicationScoped
public class VehicleTypeService {

    @Inject
    EntityManager em;

    public List<VehicleType> findVehicleTypeList(String vehicleTypeId, String vehicleTypeSpec) {

        if (vehicleTypeId != null && !vehicleTypeId.trim().isEmpty()) {
            return em
                    .createQuery("SELECT vt FROM VehicleType vt WHERE vt.vehicleTypeId = :vehicleTypeId",
                            VehicleType.class)
                    .setParameter("vehicleTypeId", vehicleTypeId)
                    .getResultList();
        } else if (vehicleTypeSpec != null && !vehicleTypeSpec.trim().isEmpty()) {
            return em
                    .createQuery("SELECT vt FROM VehicleType vt WHERE vt.vehicleTypeSpec LIKE :vehicleTypeSpec",
                            VehicleType.class)
                    .setParameter("vehicleTypeSpec", "%" + vehicleTypeSpec + "%")
                    .getResultList();
        } else {
            return em.createQuery("SELECT vt FROM VehicleType vt", VehicleType.class)
                    .getResultList();
        }
    }

}
