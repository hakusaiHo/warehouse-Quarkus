package wareHouse.Area.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import wareHouse.Area.Dao.ItemArea.ItemArea;
import wareHouse.Area.Dao.ItemArea.ItemAreaPK;
import wareHouse.Area.Model.ItemAreaList;
import wareHouse.Master.Service.ItemService;

@ApplicationScoped
public class ItemAreaService {

    @Inject
    EntityManager em;

    @Inject
    ItemService itemService;

    @Transactional
    public Response deleteItemAreaList(List<ItemAreaList> deleteRequest) {

        if (deleteRequest == null || deleteRequest.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<String> notFoundList = new ArrayList<>();
        List<ItemAreaPK> foundList = new ArrayList<>();

        for (ItemAreaList request : deleteRequest) {
            Integer itemId = itemService.getItemIdByItemNumber(request.getItemNumber());
            if (itemId == null) {
                notFoundList.add(request.getItemNumber());
                continue;
            }
            ItemAreaPK pk = new ItemAreaPK(request.getBranch(), itemId);
            foundList.add(pk);
        }

        if (!notFoundList.isEmpty()) {
            String notFoundMsg = "找不到對應的料號: " + String.join(", ", notFoundList);
            return Response.status(Response.Status.BAD_REQUEST).entity(notFoundMsg).build();
        }

        for (ItemAreaPK pk : foundList) {
            ItemArea foundItemArea = em.find(ItemArea.class, pk);
            if (foundItemArea != null) {
                em.remove(foundItemArea);
            }
        }
        return Response.status(Response.Status.OK).build();
    }

    @Transactional
    public List<ItemAreaList> getItemAreaList(String branch, Integer itemId) {
        StringBuilder getList = new StringBuilder(
                "SELECT ia.branch, ia.item_id, ia.prefer_save_area, im.description1, im.item_number " +
                        "FROM wmsmgmt_item_area ia " +
                        "JOIN wmsmgmt_item_master im ON ia.item_id = im.item_id");

        List<String> conditions = new ArrayList<>();
        if (branch != null && !branch.trim().isEmpty()) {
            conditions.add("ia.branch = :branch");
        }
        if (itemId != null) {
            conditions.add("ia.item_id = :itemId");
        }
        if (!conditions.isEmpty()) {
            getList.append(" WHERE ").append(String.join(" AND ", conditions));
        }

        Query query = em.createNativeQuery(getList.toString());
        if (branch != null && !branch.trim().isEmpty()) {
            query.setParameter("branch", branch);
        }
        if (itemId != null) {
            query.setParameter("itemId", itemId);
        }

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        return results.stream().map(row -> {
            ItemAreaList itemAreaList = new ItemAreaList();
            itemAreaList.setBranch((String) row[0]);
            itemAreaList.setItemId(row[1] == null ? null : (Integer) row[1]);
            itemAreaList.setPreferSaveArea((String) row[2]);
            itemAreaList.setDescription1(row[3] == null ? "" : (String) row[3]);
            itemAreaList.setItemNumber((String) row[4]);
            return itemAreaList;
        }).collect(Collectors.toList());
    }

    public List<ItemArea> checkItemAreaList(String branch, String preferSaveArea) {
        PanacheQuery<ItemArea> query = ItemArea.find("branch =:branch and preferSaveArea =:preferSaveArea",
                Parameters.with("branch", branch).and("preferSaveArea", preferSaveArea));
        return query.list();
    }
}