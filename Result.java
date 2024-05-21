
import java.util.ArrayList;
import java.util.List;

public class Result {

    private Site start;

    private Site end;

    private Double distance = 0.0D;

    private List<Site> passStations = new ArrayList<>();

    public Site getStar() {
        return start;
    }

    public void setStar(Site start) {
        this.start = start;
    }

    public Site getEnd() {
        return end;
    }

    public void setEnd(Site end) {
        this.end = end;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public List<Site> getPassStations() {
        return passStations;
    }

    public void setPassStations(List<Site> passStations) {
        this.passStations = passStations;
    }

    public Result(Site star, Site end, Double distance) {
        this.start = star;
        this.end = end;
        this.distance = distance;
    }

    public Result() {

    }

    @Override
    public String toString() {
        return "Result{" + "start=" + start + ", end=" + end + ", distance=" + distance + ", passStations="
                + passStations + '}';
    }
}
