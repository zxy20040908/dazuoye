package 大作业;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
    public Set<String> getTransferStations() {
        Map<String, Set<String>> stationLines = new HashMap<>();
        for (String line : map.keySet()) {
            for (String station : map.get(line).keySet()) {
                stationLines.putIfAbsent(station, new HashSet<>());
                stationLines.get(station).add(line);
            }
        }

        Set<String> transferStations = new HashSet<>();
        for (String station : stationLines.keySet()) {
            if (stationLines.get(station).size() > 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("<").append(station).append(", <");
                for (String line : stationLines.get(station)) {
                    sb.append(line).append(" 号线、");
                }
                sb.setLength(sb.length() - 1); // Remove the last comma
                sb.append(">>");
                transferStations.add(sb.toString());
            }
        }

        return transferStations;
    }
    public String getNearbyStations(String stationName) {
        for(Map.Entry<String, Map<String, Double>> entry : map.entrySet()){
            String line = entry.getKey();
            Map<String, Double> stations = entry.getValue();
            if (stations.containsKey(stationName)) {
                return line;
            }
        }
        return null; // Station not found in any line
//
//        Set<String> nearbyStations = new HashSet<>();
//        Map<String, Double> currentLine = map.get(stationName);
//        for (String line : currentLine.keySet()) {
//            for (String otherStation : map.get(line).keySet()) {
//                if (!otherStation.equals(stationName) && map.get(line).get(otherStation) <= distanceThreshold) {
//                    StringBuilder sb = new StringBuilder();
//                    sb.append("<<").append(otherStation).append("站，").append(line).append(" 号线，")
//                            .append(map.get(line).get(otherStation)).append(">>");
//                    nearbyStations.add(sb.toString());
//                }
//            }
//        }
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
        return this.map.values().toString();
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
        //System.out.println(subwayMap);
        System.out.println(subwayMap.findLinesByStation("中南路"));


    }
}


