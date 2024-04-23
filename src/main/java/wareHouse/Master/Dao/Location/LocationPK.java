package wareHouse.Master.Dao.Location;

public class LocationPK {
    public String locationId;
    public String branch;

    public LocationPK() {
    }

    public LocationPK(String locationId, String branch) {
        this.locationId = locationId;
        this.branch = branch;
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof LocationPK)) {
            return false;
        }
        final LocationPK pk = (LocationPK) other;
        if (!pk.branch.equals(branch)) {
            return false;
        }
        if (!pk.locationId.equals(locationId)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return branch.hashCode() + locationId.hashCode();
    }
}
