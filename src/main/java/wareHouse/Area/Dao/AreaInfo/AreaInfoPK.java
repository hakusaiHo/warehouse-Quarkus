package wareHouse.Area.Dao.AreaInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaInfoPK {

    public String branch;
    public String area;

    public AreaInfoPK() {
    }

    public AreaInfoPK(String branch, String area) {
        this.branch = branch;
        this.area = area;
    };

    public int hashCode() {
        int result;
        result = branch.hashCode() + area.hashCode();
        return result;
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof AreaInfoPK))
            return false;

        final AreaInfoPK pk = (AreaInfoPK) other;

        if (!pk.branch.equals(branch))
            return false;
        if (!pk.area.equals(area))
            return false;
        return true;
    }
}
