
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SubwaySystem {
    public static String[] lineNames1;
    public static String[] lineNames2;
    public static String[] lineNames3;
    public static String[] lineNames4;
    public static String[] lineNames5;
    public static String[] lineNames6;
    public static String[] lineNames7;
    public static String[] lineNames8;
    public static String[] lineNames9;
    public static String[] lineNames10;
    public static String[] lineNames11;
    public static String[] lineNames12;

    static String lineName;
    public static String[] siteName = { "1号线", "2号线", "3号线", "4号线", "5号线", "6号线", "7号线", "8号线", "阳逻线", "11号线", "16号线", "19号线" };
    static List<Paths> route = new ArrayList<>();

    public static String getLineName() {
        return lineName;
    }

    public static void SearchLineByStation() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入需要查询的站点名称：");
        String name = sc.nextLine();
        Map<String, String[]> lineMap = new HashMap<>();
        lineMap.put("1号线", lineNames1);
        lineMap.put("2号线", lineNames2);
        lineMap.put("3号线", lineNames3);
        lineMap.put("4号线", lineNames4);
        lineMap.put("5号线", lineNames5);
        lineMap.put("6号线", lineNames6);
        lineMap.put("7号线", lineNames7);
        lineMap.put("8号线", lineNames8);
        lineMap.put("阳逻线", lineNames9);
        lineMap.put("11号线", lineNames10);
        lineMap.put("16号线", lineNames11);
        lineMap.put("19号线", lineNames12);

        for (Map.Entry<String, String[]> entry : lineMap.entrySet()) {
            String lineName = entry.getKey();
            String[] stationNames = entry.getValue();

            for (String stationName : stationNames) {
                if (stationName.equals(name)) {
                    System.out.println(name + "经过" + lineName);
                    break;
                }
            }
        }
    }

    // 新增的方法，查找并打印所有中转站
    public static void findAndPrintTransferStations() {
        Map<String, Set<String>> stationLinesMap = new HashMap<>();
        Map<String, String[]> lineMap = new HashMap<>();
        lineMap.put("1号线", lineNames1);
        lineMap.put("2号线", lineNames2);
        lineMap.put("3号线", lineNames3);
        lineMap.put("4号线", lineNames4);
        lineMap.put("5号线", lineNames5);
        lineMap.put("6号线", lineNames6);
        lineMap.put("7号线", lineNames7);
        lineMap.put("8号线", lineNames8);
        lineMap.put("阳逻线", lineNames9);
        lineMap.put("11号线", lineNames10);
        lineMap.put("16号线", lineNames11);
        lineMap.put("19号线", lineNames12);

        // 填充 stationLinesMap
        for (Map.Entry<String, String[]> entry : lineMap.entrySet()) {
            String lineName = entry.getKey();
            String[] stationNames = entry.getValue();

            for (String stationName : stationNames) {
                stationLinesMap.putIfAbsent(stationName, new HashSet<>());
                stationLinesMap.get(stationName).add(lineName);
            }
        }

        // 查找并打印中转站
        System.out.println("中转站：");
        for (Map.Entry<String, Set<String>> entry : stationLinesMap.entrySet()) {
            String stationName = entry.getKey();
            Set<String> lines = entry.getValue();
            if (lines.size() > 1) {
                System.out.println(stationName + "经过的线路：" + lines);
            }
        }
    }

    public static void main(String[] args) {
        SubwaySystem sub = new SubwaySystem();
        initial.init(sub);

        Frame f = new Frame("武汉地铁模拟系统-智建2201周潇怡");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setBounds(400, 200, 800, 600);
        f.setLayout(new FlowLayout());

        Button bu = new Button("searchLineByStation");
        bu.setBounds(50, 50, 200, 100); // x, y, width, height
        f.add(bu);

        bu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchLineByStation();
            }
        });

        Button bu2 = new Button("CountDistanceAndPriceBetweenStation");
        bu2.setBounds(80, 100, 200, 30); // x, y, width, height
        f.add(bu2);
        bu2.addActionListener(e -> countPrice());

        // 添加按钮以查找并打印中转站
        Button bu3 = new Button("FindAndPrintTransferStations");
        bu3.setBounds(110, 150, 200, 30); // x, y, width, height
        f.add(bu3);
        bu3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findAndPrintTransferStations();
            }
        });

        // 显示窗体
        f.setVisible(true);

        for (int i = 0; i < 12; i++) {
            Paths r = new Paths(i);
            route.add(r);
        }

        BufferedReader br = null;
        String str = null;
        int flag = 0;
        try {
            br = new BufferedReader(new FileReader("F:\\IntelliJ IDEA 2024.1\\intellij project\\learningproject\\src\\大作业\\subway2.txt"));
            str = "";
            while ((str = br.readLine()) != null) {
                if (str.charAt(0) != '-') {
                    String[] arr = str.split("\\s+");
                    Double dis = Double.valueOf(arr[1].toString());
                    Site a = new Site(dis, arr[0], flag);
                    route.get(flag).getList().add(a);
                }
                if (str.charAt(0) == '-')
                    flag += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static void countPrice() {
        number();
        Scanner input = new Scanner(System.in);
        System.out.println("请输入起点：");
        String start = input.nextLine();
        System.out.println("请输入终点：");
        String end = input.nextLine();
        printRoute(start, end);
        System.out.println("");
        System.out.println(start + "到" + end + "的总距离是" + getDis(start, end) + "千米");
        System.out.println("普通票的票价是" + countFare(start, end) + "元");
        double temp_fare = (double) (Math.round(countFare(start, end) * 0.9f * 10) / 10.0);
        System.out.println("武汉通的票价是" + temp_fare + "元");
    }

    public static void number() {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < route.get(i).getList().size(); j++) {
                route.get(i).getList().get(j).setNum(j);
            }
        }
    }

    public static List<Integer> getline(String name) {
        List<Integer> lines = new ArrayList<>();
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < route.get(j).getList().size(); i++) {
                if (route.get(j).getList().get(i).getName().equals(name))
                    lines.add(j);
            }
        }
        return lines;
    }

    public static Map<Site, Result> resultMap = new HashMap<>();
    public static List<Site> analysisList = new ArrayList<>();

    public static List<Site> getLinkStations(Site station) {
        List<Site> linkedStaions = new ArrayList<>();
        int line = station.getline();
        if (station.getNum() != 0) {
            linkedStaions.add(route.get(line).getList().get(station.getNum() - 1));
        }
        if (station.getNum() != route.get(line).getList().size() - 1) {
            linkedStaions.add(route.get(line).getList().get(station.getNum() + 1));
        }
        for (int j = 0; j < route.get(station.getline()).getList().size(); j++) {
            if (route.get(station.getline()).getList().get(j).getName().equals(station.getName())
                    && station.getline() != route.get(station.getline()).getList().get(j).getline())
                linkedStaions.add(route.get(station.getline()).getList().get(j));
        }
        return linkedStaions;
    }

    public static void printRoute(String start, String end) {
        Site startStation = new Site(0.0, start, 0, 0);
        Site endStation = new Site(0.0, end, 0, 0);
        analysisList.clear();
        resultMap.clear();

        for (int line : getline(start)) {
            for (Site site : route.get(line).getList()) {
                if (site.getName().equals(start)) {
                    analysisList.add(site);
                    resultMap.put(site, new Result(site, null, 0.0));
                }
            }
        }

        while (!analysisList.isEmpty()) {
            double minDis = Double.MAX_VALUE;
            Site minStation = null;
            for (Site site : analysisList) {
                if (resultMap.get(site).getDistance() < minDis) {
                    minDis = resultMap.get(site).getDistance();
                    minStation = site;
                }
            }

            if (minStation == null || minStation.getName().equals(end)) {
                endStation = minStation;
                break;
            }

            analysisList.remove(minStation);
            List<Site> linkedStations = getLinkStations(minStation);
            for (Site site : linkedStations) {
                double dis = resultMap.get(minStation).getDistance() + site.getdis();
                if (resultMap.containsKey(site)) {
                    if (resultMap.get(site).getDistance() > dis) {
                        resultMap.get(site).setDistance(dis);
                        List<Site> passStations = new ArrayList<>(resultMap.get(minStation).getPassStations());
                        passStations.add(site);
                        resultMap.get(site).setPassStations(passStations);
                    }
                } else {
                    Result newResult = new Result(minStation, site, dis);
                    List<Site> passStations = new ArrayList<>(resultMap.get(minStation).getPassStations());
                    passStations.add(site);
                    newResult.setPassStations(passStations);
                    resultMap.put(site, newResult);
                    analysisList.add(site);
                }
            }
        }

        List<Site> finalList = resultMap.get(endStation).getPassStations();
        for (int k = 0; k < finalList.size(); k++) {
            System.out.print(finalList.get(k).getName() + " ");
            if (k != 0 && !finalList.get(k).getName().equals(finalList.get(k - 1).getName())) {
                System.out.print(finalList.get(k).getline() + "号线 ");
            }
        }
        System.out.println();
    }

    public static double getDis(String start, String end) {
        Site startStation = new Site(0.0, start, 0, 0);
        Site endStation = new Site(0.0, end, 0, 0);
        analysisList.clear();
        resultMap.clear();

        for (int line : getline(start)) {
            for (Site site : route.get(line).getList()) {
                if (site.getName().equals(start)) {
                    analysisList.add(site);
                    resultMap.put(site, new Result(site, null, 0.0));
                }
            }
        }

        while (!analysisList.isEmpty()) {
            double minDis = Double.MAX_VALUE;
            Site minStation = null;
            for (Site site : analysisList) {
                if (resultMap.get(site).getDistance() < minDis) {
                    minDis = resultMap.get(site).getDistance();
                    minStation = site;
                }
            }

            if (minStation == null || minStation.getName().equals(end)) {
                endStation = minStation;
                break;
            }

            analysisList.remove(minStation);
            List<Site> linkedStations = getLinkStations(minStation);
            for (Site site : linkedStations) {
                double dis = resultMap.get(minStation).getDistance() + site.getdis();
                if (resultMap.containsKey(site)) {
                    if (resultMap.get(site).getDistance() > dis) {
                        resultMap.get(site).setDistance(dis);
                        List<Site> passStations = new ArrayList<>(resultMap.get(minStation).getPassStations());
                        passStations.add(site);
                        resultMap.get(site).setPassStations(passStations);
                    }
                } else {
                    Result newResult = new Result(minStation, site, dis);
                    List<Site> passStations = new ArrayList<>(resultMap.get(minStation).getPassStations());
                    passStations.add(site);
                    newResult.setPassStations(passStations);
                    resultMap.put(site, newResult);
                    analysisList.add(site);
                }
            }
        }

        return resultMap.get(endStation).getDistance();
    }

    public static double countFare(String start, String end) {
        double dis = getDis(start, end);
        if (dis < 4)
            return 2.0;
        if (dis < 12)
            return 3.0;
        if (dis < 24)
            return 4.0;
        return 5.0;
    }
}
