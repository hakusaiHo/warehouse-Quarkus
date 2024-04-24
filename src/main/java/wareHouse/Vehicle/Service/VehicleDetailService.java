package wareHouse.Vehicle.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import wareHouse.Vehicle.Dao.VehicleDetail.VehicleDetail;
import wareHouse.Vehicle.Dao.VehicleDetail.VehicleDetailPK;
import wareHouse.Vehicle.Dao.VehicleMaster.VehicleMaster;
import wareHouse.Vehicle.Dao.VehicleMaster.VehicleMasterPK;
import wareHouse.Vehicle.Model.MoveQuantityRequest;
import wareHouse.Vehicle.Model.VehicleList;

@ApplicationScoped
public class VehicleDetailService {

    @Inject
    EntityManager em;

    @Transactional
    public List<VehicleList> getVehicleDetailList(String vehicleId, String itemNumber, String lotNumber) {
        StringBuilder sqlBuilder = new StringBuilder(
                "SELECT vd.vehicle_id, im.item_number, vd.lot_number, im.description1, " +
                        "im.allergen_mark, im.muslim_mark, vd.store_quantity, vd.store_uom " +
                        "FROM wmsmgmt_vehicle_detail vd " +
                        "JOIN wmsmgmt_item_master im on vd.item_id = im.item_id ");

        boolean Condition = false;
        if (vehicleId != null && !vehicleId.trim().isEmpty()) {
            sqlBuilder.append(Condition ? " AND" : " WHERE");
            sqlBuilder.append(" vd.vehicle_id = :vehicleId");
            Condition = true;
        }
        if (itemNumber != null && !itemNumber.trim().isEmpty()) {
            sqlBuilder.append(Condition ? " AND" : " WHERE");
            sqlBuilder.append(" im.item_number = :itemNumber");
            Condition = true;
        }
        if (lotNumber != null && !lotNumber.trim().isEmpty()) {
            sqlBuilder.append(Condition ? " AND" : " WHERE");
            sqlBuilder.append(" vd.lot_number = :lotNumber");
        }

        Query query = em.createNativeQuery(sqlBuilder.toString());

        if (vehicleId != null && !vehicleId.trim().isEmpty()) {
            query.setParameter("vehicleId", vehicleId);
        }
        if (itemNumber != null && !itemNumber.trim().isEmpty()) {
            query.setParameter("itemNumber", itemNumber);
        }
        if (lotNumber != null && !lotNumber.trim().isEmpty()) {
            query.setParameter("lotNumber", lotNumber);
        }

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        List<VehicleList> vehicleLists = new ArrayList<>();
        for (Object[] row : results) {
            VehicleList vehicleList = new VehicleList();
            vehicleList.setVehicleId((String) row[0]);
            vehicleList.setItemNumber(row[1] != null ? (String) row[1] : "");
            vehicleList.setLotNumber((String) row[2]);
            vehicleList.setDescription1(row[3] != null ? (String) row[3] : "");
            vehicleList.setAllergenMark(row[4] != null ? (Boolean) row[4] : false);
            vehicleList.setMuslimMark(row[5] != null ? (Boolean) row[5] : false);
            vehicleList.setStoreQuantity(row[6] != null ? (BigDecimal) row[6] : BigDecimal.ZERO);
            vehicleList.setStoreUom(row[7] != null ? (String) row[7] : null);
            vehicleLists.add(vehicleList);
        }
        return vehicleLists;
    }

    @Transactional
    public void moveVehicleDetailQuantity(MoveQuantityRequest request) {

        String targetVehicleId = request.getTargetVehicleId();
        VehicleMasterPK requestpk = new VehicleMasterPK();
        requestpk.setVehicleId(targetVehicleId);
        VehicleMaster targMaster = em.find(VehicleMaster.class, requestpk);
        if (targMaster == null) {
            throw new IllegalArgumentException("目標載具編號不存在");
        }
        for (MoveQuantityRequest.SourceDetail source : request.getSources()) {

            VehicleDetailPK pk = new VehicleDetailPK(source.getVehicleId(), source.getItemId(), source.getLotNumber());
            VehicleDetail sourceDetail = em.find(VehicleDetail.class, pk);

            BigDecimal quantityToMove = source.getQuantityToMove();
            if (sourceDetail == null) {
                throw new IllegalArgumentException("找不到來源載具明細: " + source.getVehicleId());
            }
            if (sourceDetail.getStoreQuantity().compareTo(quantityToMove) < 0
                    || quantityToMove.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("欲置換的載具明細調整數量不正確");
            }
            sourceDetail.setStoreQuantity(sourceDetail.getStoreQuantity().subtract(quantityToMove));
            em.merge(sourceDetail);
            if (sourceDetail.getStoreQuantity().compareTo(BigDecimal.ZERO) == 0) {
                em.remove(sourceDetail);
            }
            em.flush();
        }

        request.getSources().forEach(source -> {
            VehicleDetailPK targetpk = new VehicleDetailPK(request.getTargetVehicleId(), source.getItemId(),
                    source.getLotNumber());
            VehicleDetail targetDetail = em.find(VehicleDetail.class, targetpk);
            if (targetDetail == null) {
                targetDetail = new VehicleDetail();
                targetDetail.setVehicleId(request.getTargetVehicleId());
                targetDetail.setItemId(source.getItemId());
                targetDetail.setLotNumber(source.getLotNumber());
                targetDetail.setStoreQuantity(source.getQuantityToMove());
                targetDetail.setStoreUom(source.getStoreUom());
                targetDetail.modifiedDate = new Date();
                targetDetail.modifiedUser = 1L;
                em.persist(targetDetail);
            } else {
                targetDetail.setStoreQuantity(targetDetail.getStoreQuantity().add(source.getQuantityToMove()));
                em.merge(targetDetail);
            }
        });
    }
}