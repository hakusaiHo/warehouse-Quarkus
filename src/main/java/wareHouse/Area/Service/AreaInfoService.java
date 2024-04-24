package wareHouse.Area.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import wareHouse.Area.Dao.AreaInfo.AreaInfo;
import wareHouse.Area.Dao.AreaInfo.AreaInfoPK;
import wareHouse.Area.Dao.AreaLocation.AreaLocation;
import wareHouse.Area.Dao.ItemArea.ItemArea;

@ApplicationScoped
public class AreaInfoService {

    @Inject
    EntityManager em;

    @Inject
    ItemAreaService itemAreaService;

    @Inject
    AreaLocationService areaLocationService;

    @Transactional
    public Response deleteAreaInfoList(List<AreaInfoPK> deleteRequest) {
        if (deleteRequest == null || deleteRequest.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<AreaInfoPK> notDeleteList = new ArrayList<>();

        for (AreaInfoPK pk : deleteRequest) {
            List<ItemArea> itemAreaList = itemAreaService.checkItemAreaList(pk.branch, pk.area);
            List<AreaLocation> areaLocationList = areaLocationService.getAreaLocationList(pk.branch, pk.area, null,
                    null);

            if (!itemAreaList.isEmpty() || !areaLocationList.isEmpty()) {
                notDeleteList.add(pk);
            }
        }
        if (!notDeleteList.isEmpty()) {
            String notDeleteListMsg = notDeleteList.stream()
                    .map(pk -> "分支廠：" + pk.getBranch() + "，儲區：" + pk.getArea())
                    .collect(Collectors.joining("; "));
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("此儲區已設定為偏好保存區域，或有對照的儲位資料：" + notDeleteListMsg + "，請先移除設定，再進行刪除動作。").build();
        }

        @SuppressWarnings("unused")
        int deletedAreaInfoCount = 0;
        for (AreaInfoPK pk : deleteRequest) {
            boolean areaInfoIsDeleted = AreaInfo.deleteById(pk);
            if (areaInfoIsDeleted) {
                deletedAreaInfoCount++;
            }
        }
        return Response.status(Response.Status.OK).build();
    }

    public List<AreaInfo> getAreaInfoList(String branch, String area, String areaName) {
        StringBuilder jpql = new StringBuilder("SELECT ai FROM AreaInfo ai WHERE 1 = 1");
        Map<String, Object> parameters = new HashMap<>();

        if (branch != null && !branch.trim().isEmpty()) {
            jpql.append(" AND ai.branch = :branch");
            parameters.put("branch", branch);
        }
        if (area != null && !area.trim().isEmpty()) {
            jpql.append(" AND ai.area = :area");
            parameters.put("area", area);
        }
        if (areaName != null && !areaName.trim().isEmpty()) {
            jpql.append(" AND ai.areaName LIKE :areaName OR ai.area LIKE :areaName");
            parameters.put("areaName", "%" + areaName + "%");
        }
        TypedQuery<AreaInfo> query = em.createQuery(jpql.toString(), AreaInfo.class);
        parameters.forEach(query::setParameter);

        return query.getResultList();
    }

    public List<AreaInfo> getAreaInfoListByBranch(String branch) {
        String jpql = "SELECT ai FROM AreaInfo ai WHERE ai.branch = :branch";
        TypedQuery<AreaInfo> query = em.createQuery(jpql, AreaInfo.class);
        query.setParameter("branch", branch);
        return query.getResultList();
    }
}
