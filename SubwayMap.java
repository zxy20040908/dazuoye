package 大作业;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
class StationInfo {
    String stationName;
    String lineName;
    int distance;

    public StationInfo(String stationName, String lineName, int distance) {
        this.stationName = stationName;
        this.lineName = lineName;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "<" + stationName + "，" + lineName + "号线，" + distance + ">";
    }
}
class TransferStationInfo {
    String stationName;
    Set<String> lines;

    public TransferStationInfo(String stationName) {
        this.stationName = stationName;
        this.lines = new HashSet<>();
    }

    public void addLine(String line) {
        lines.add(line);
    }

    @Override
    public String toString() {
        return "{" +
                 stationName +
                  lines +
                '}';
    }
}
public class SubwayMap {
    private Map<String, Map<String, Double>> map;
    public SubwayMap() {
        this.map = new LinkedHashMap<>();
    }

    public void addLine(String lineName) {
        map.put(lineName, new LinkedHashMap<>());
    }

    public void addStation(String lineName, String stationName, double distance) {
        map.get(lineName).put(stationName, distance);
    }

    public double getDistance(String lineName, String station1, String station2) {
        return map.get(lineName).get(station1) + map.get(lineName).get(station2);
    }
    public Map<String, TransferStationInfo> getTransferStations() {
        Map<String, TransferStationInfo> transferStations = new HashMap<>();
        for (String line : map.keySet()) {
            for (String station : map.get(line).keySet()) {
                transferStations.putIfAbsent(station, new TransferStationInfo(station));
                transferStations.get(station).addLine(line);
            }
        }

        // 只保留实际为换乘站的项
        return transferStations.entrySet().stream()
                .filter(entry -> entry.getValue().lines.size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    public Set<String> findNearbyStations(String stationName, int n) {
        Set<String> nearbyStations = new HashSet<>();
        for (String line : map.keySet()) {
            List<String> stations = new ArrayList<>(map.get(line).keySet());
            int index = stations.indexOf(stationName);
            if (index != -1) { // 如果找到了该站点
                // 查找前n个站点
                for (int i = Math.max(0, index - n); i < index; i++) {
                    nearbyStations.add(String.format("%s，%s号线，%d", stations.get(i), line, index - i));
                }
                // 查找后n个站点
                for (int i = index + 1; i <= Math.min(index + n, stations.size() - 1); i++) {
                    nearbyStations.add(String.format("%s，%s号线，%d", stations.get(i), line, i - index));
                }
            }
        }
        return nearbyStations;
    }



    public Set<String> findLinesByStation(String stationName) {
        Set<String> lines = new HashSet<>();
        for (Map.Entry<String, Map<String, Double>> entry :map.entrySet()) {
            String line = entry.getKey();
            Map<String, Double> stations = entry.getValue();
            if (stations.containsKey(stationName)) {
                lines.add(line);
            }
        }
        return lines;
    }








    @Override
    public String toString() {
        return this.map.toString();
    }


    public static void main(String[] args) {
        SubwayMap subwayMap = new SubwayMap();

        try (BufferedReader br = new BufferedReader(new FileReader("F:/subway.txt"))) {
            String line;
            String currentLine = null;
            while ((line = br.readLine()) != null) {
                if (line.contains("号线站点间距")) {
                    currentLine = line.split("号线站点间距")[0];
                    subwayMap.addLine(currentLine);
                } else if (line.contains("---") || line.contains("—")) {
                        String separator = line.contains("---") ? "---" : "—";
                    String[] parts = line.split(separator);
                    String station1 = parts[0].trim();

                    String station2 = parts[1].split("\t")[0].trim();
                    double distance = Double.parseDouble(parts[1].split("\t")[1].trim());
                    subwayMap.addStation(currentLine, station1, distance);
                    subwayMap.addStation(currentLine, station2, distance);
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(subwayMap.getTransferStations().values());
        Set<String> stations = subwayMap.findNearbyStations("华中科技大学", 1);
        System.out.println(stations);

    }
}


