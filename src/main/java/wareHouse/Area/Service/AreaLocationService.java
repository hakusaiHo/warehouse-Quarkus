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
import jakarta.ws.rs.core.Response;
import wareHouse.Area.Dao.AreaLocation.AreaLocation;
import wareHouse.Area.Dao.AreaLocation.AreaLocationPK;

@ApplicationScoped
public class AreaLocationService {

    @Inject
    EntityManager em;

    public Response deleteAreaLocationList(List<AreaLocationPK> deleteRequest) {
        if (deleteRequest == null || deleteRequest.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<AreaLocationPK> notFoundList = new ArrayList<>();
        @SuppressWarnings("unused")
        int deletedCount = 0;

        for (AreaLocationPK pk : deleteRequest) {
            AreaLocation foundList = em.find(AreaLocation.class, pk);
            if (foundList == null) {
                notFoundList.add(pk);
            } else {
                em.remove(foundList);
                deletedCount++;
            }
        }

        if (!notFoundList.isEmpty()) {
            @SuppressWarnings("unused")
            String notFoundMsg = notFoundList.stream()
                    .map(pk -> "分支廠：" + pk.getBranch() + "，儲位：" + pk.getLocationId())
                    .collect(Collectors.joining("; "));
            return Response.status(Response.Status.PARTIAL_CONTENT).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    public List<AreaLocation> getAreaLocationList(String branch, String area, String location,
            String locationDesc) {
        StringBuilder jpql = new StringBuilder("SELECT ai FROM AreaLocation ai WHERE 1 = 1");
        Map<String, Object> parameters = new HashMap<>();

        if (branch != null && !branch.trim().isEmpty()) {
            jpql.append(" AND ai.branch = :branch");
            parameters.put("branch", branch);
        }
        if (area != null && !area.trim().isEmpty()) {
            jpql.append(" AND ai.area = :area");
            parameters.put("area", area);
        }
        if (location != null && !location.trim().isEmpty()) {
            jpql.append(" AND ai.location = :location");
            parameters.put("location", location);
        }
        if (locationDesc != null && !locationDesc.trim().isEmpty()) {
            jpql.append(" AND ai.locationDesc LIKE :locationDesc OR ai.location LIKE :locationDesc");
            parameters.put("locationDesc", "%" + locationDesc + "%");
        }
        TypedQuery<AreaLocation> query = em.createQuery(jpql.toString(), AreaLocation.class);
        parameters.forEach(query::setParameter);

        return query.getResultList();
    }
}
