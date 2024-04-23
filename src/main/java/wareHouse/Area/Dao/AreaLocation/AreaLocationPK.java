package wareHouse.Area.Dao.AreaLocation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaLocationPK {
    public String locationId;
    public String branch;

    public AreaLocationPK() {
    }

    public AreaLocationPK(String locationId, String branch) {
        this.locationId = locationId;
        this.branch = branch;
    }

    public int hashCode() {
        int result;
        result = branch.hashCode() + locationId.hashCode();
        return result;
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof AreaLocationPK))
            return false;

        final AreaLocationPK pk = (AreaLocationPK) other;

        if (!pk.branch.equals(branch))
            return false;
        if (!pk.locationId.equals(locationId))
            return false;
        return true;
    }
    
}
