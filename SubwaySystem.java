package 大作业;
import java.math.BigDecimal;
import java.util.*;
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

    public static String[] siteName = { "1号线", "2号线", "3号线", "4号线","5号线", "6号线", "7号线", "8号线", "阳逻线", "11号线","16号线","19号线" };
    static List<Paths> route = new ArrayList();
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


        // 窗体显示
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
        List<Integer> lines = new ArrayList();
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < route.get(j).getList().size(); i++) {
                if (route.get(j).getList().get(i).getName().equals(name) == true)
                    lines.add(j);
            }
        }
        return lines;
    }

    private static HashMap<Site, Result> resultMap = new HashMap<>();
    private static List<Site> analysisList = new ArrayList<>();

    private static List<Site> getLinkStations(Site station) {
        List<Site> linkedStaions = new ArrayList<Site>();
        for (int j = 0; j < 12; j++) {
            for (int i = 0; i < route.get(j).getList().size(); i++) {
                if (station.equals(route.get(j).getList().get(i))) {
                    if (i == 0) {
                        linkedStaions.add(route.get(j).getList().get(i + 1));
                    } else if (i == (route.get(j).getList().size() - 1)) {
                        linkedStaions.add(route.get(j).getList().get(i - 1));
                    } else {
                        linkedStaions.add(route.get(j).getList().get(i + 1));
                        linkedStaions.add(route.get(j).getList().get(i - 1));
                    }
                }
            }
        }
        return linkedStaions;
    }

    private static Site getNextStation() {
        Double min = Double.MAX_VALUE;
        Site rets = null;
        Set<Site> stations = resultMap.keySet();
        for (Site station : stations) {
            if (analysisList.contains(station)) {
                continue;
            }
            Result result = resultMap.get(station);
            result.setDistance(0.0D);
            if (result.getDistance() < min) {
                min = result.getDistance();
                rets = result.getEnd();
            }
        }
        return rets;
    }

    private static double doubleAdd(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    private static List<Site> getShortestRoute(String star, String end) {
        Result result = calculate(new Site(star), new Site(end));
        return result.getPassStations();
    }

    private static void printRoute(String start, String end) {
        List<Integer> index = new ArrayList();
        List<Site> way = getShortestRoute(start, end);

        System.out.print(start);
        for (int i = 0; i < way.size(); i++) {
            System.out.print("→" + way.get(i).getName());
        }
        System.out.print("");
    }

    private static double getDis(String star, String end) {
        List<Site> way = getShortestRoute(star, end);
        double distance = 0.0d;
        for (int i = 0; i < way.size() - 1; i++) {
            if (way.get(i).getNum() > way.get(i + 1).getNum())
                distance = distance + way.get(i + 1).getdis();
            else
                distance = distance + way.get(i).getdis();
        }
        distance = (double) (Math.round(distance * 1000) / 1000.0);
        return distance;
    }

    private static double countFare(String startSite, String endSite) {
        System.out.println("日票价格0元");
        if (getDis(startSite, endSite) <= 4)
            return 2;
        if (getDis(startSite, endSite) > 12 && getDis(startSite, endSite) <= 24)
            return Math.ceil(getDis(startSite, endSite)/6);
        if (getDis(startSite, endSite) > 24 && getDis(startSite, endSite) <= 40)
            return Math.ceil(getDis(startSite, endSite)/8);
        if (getDis(startSite, endSite) > 40 && getDis(startSite, endSite) <= 50)
            return Math.ceil(getDis(startSite, endSite)/10);
        if (getDis(startSite, endSite) > 50 )
            return Math.ceil(getDis(startSite, endSite)/20);
        return 0;
    }

    private static Result calculate(Site star, Site end) {
        for (int j = 0; j < 12; j++) {
            for (int i = 0; i < route.get(j).getList().size(); i++) {
                if (star.equals(route.get(j).getList().get(i)))
                    star = route.get(j).getList().get(i);
            }
        }
        for (int j = 0; j < 12; j++) {
            for (int i = 0; i < route.get(j).getList().size(); i++) {
                if (end.equals(route.get(j).getList().get(i)))
                    end = route.get(j).getList().get(i);
            }
        }
        if (!analysisList.contains(star)) {
            analysisList.add(star);
        }
        if (star.equals(end)) {
            Result result = new Result();
            result.setDistance(0.0D);
            result.setEnd(star);
            result.setStar(star);
            resultMap.put(star, result);
            return resultMap.get(star);
        }
        if (resultMap.isEmpty()) {
            List<Site> linkStations = getLinkStations(star);
            for (Site station : linkStations) {
                for (int j = 0; j < 12; j++) {
                    for (int i = 0; i < route.get(j).getList().size(); i++) {
                        if (station.equals(route.get(j).getList().get(i)))
                            station = route.get(j).getList().get(i);
                    }
                }
                Result result = new Result();
                result.setStar(star);
                result.setEnd(station);
                Double distance;
                if (star.getNum() < station.getNum())
                    distance = star.getdis();
                else
                    distance = station.getdis();
                result.setDistance(distance);
                result.getPassStations().add(station);
                resultMap.put(station, result);
            }
        }
        Site parent = getNextStation();
        for (int j = 0; j < 12; j++) {
            for (int i = 0; i < route.get(j).getList().size(); i++) {
                if (parent.equals(route.get(j).getList().get(i)))
                    parent = route.get(j).getList().get(i);
            }
        }
        if (parent == null) {
            Result result = new Result();
            result.setDistance(0.0D);
            result.setStar(star);
            result.setEnd(end);
            return resultMap.put(end, result);
        }
        if (parent.equals(end)) {
            return resultMap.get(parent);
        }
        List<Site> childLinkStations = getLinkStations(parent);
        for (Site child : childLinkStations) {
            for (int j = 0; j < 12; j++) {
                for (int i = 0; i < route.get(j).getList().size(); i++) {
                    if (child.equals(route.get(j).getList().get(i)))
                        child = route.get(j).getList().get(i);
                }
            }
            if (analysisList.contains(child)) {
                continue;
            }
            Double distance;
            if (parent.getNum() < child.getNum())
                distance = parent.getdis();
            else
                distance = child.getdis();
            if (parent.getName().equals(child.getName())) {
                distance = 0.0D;
            }
            Double parentDistance = resultMap.get(parent).getDistance();
            distance = doubleAdd(distance, parentDistance);
            List<Site> parentPassStations = resultMap.get(parent).getPassStations();
            Result childResult = resultMap.get(child);
            if (childResult != null) {
                if (childResult.getDistance() > distance) {
                    childResult.setDistance(distance);
                    childResult.getPassStations().clear();
                    childResult.getPassStations().addAll(parentPassStations);
                    childResult.getPassStations().add(child);
                }
            } else {
                childResult = new Result();
                childResult.setDistance(distance);
                childResult.setStar(star);
                childResult.setEnd(child);
                childResult.getPassStations().addAll(parentPassStations);
                childResult.getPassStations().add(child);
            }
            resultMap.put(child, childResult);
        }
        analysisList.add(parent);
        calculate(star, end);
        return resultMap.get(end);
    }

    public static String[] getLineNames10() {
        return lineNames10;
    }

    public static void setLineNames10(String[] lineNames10) {
        SubwaySystem.lineNames10 = lineNames10;
    }

    public static String[] getLineNames11() {
        return lineNames11;
    }

    public static void setLineNames11(String[] lineNames11) {
        SubwaySystem.lineNames11 = lineNames11;
    }

    public static String[] getLineNames12() {
        return lineNames12;
    }

    public static void setLineNames12(String[] lineNames12) {
        SubwaySystem.lineNames12 = lineNames12;
    }
}
