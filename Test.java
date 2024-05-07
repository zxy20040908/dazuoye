package 大作业;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Test {
    private static String[] lineNames1;
    private static String[] lineNames2;
    private static String[] lineNames3;
    private static String[] lineNames4;
    private static String[] lineNames5;
    private static String[] lineNames6;
    private static String[] lineNames7;
    private static String[] lineNames8;
    private static String[] lineNames9;
    static String lineName;

    public static String getLineName() {
        return lineName;
    }

    public static void init() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("F:\\IntelliJ IDEA 2024.1\\intellij project\\learningproject\\src\\subway1.txt"));
            String Line1 = br.readLine();
            lineNames1 = Line1.split("---");
            String Line2 = br.readLine();
            lineNames2 = Line2.split("---");
            String Line3 = br.readLine();
            lineNames3 = Line3.split("---");
            String Line4 = br.readLine();
            lineNames4 = Line4.split("---");
            String Line5 = br.readLine();
            lineNames5 = Line5.split("---");
            String Line6 = br.readLine();
            lineNames6 = Line6.split("---");
            String Line7 = br.readLine();
            lineNames7 = Line7.split("---");
            String Line8 = br.readLine();
            lineNames8 = Line8.split("---");
            String Line9 = br.readLine();
            lineNames9 = Line9.split("---");
            br.close();
        } catch (FileNotFoundException e1) {
            System.out.println("can not find subway.txt!");
        } catch (IOException e2) {
            System.out.println("IO流异常!");
        }

    }

    public static void task3_1() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入需要查询的站点名称：");
        String name = sc.nextLine();
        for (int i = 0; i < lineNames1.length; i++) {
            if (lineNames1[i].equals(name))
                System.out.println(name + "经过1号线");
        }
        for (int i = 0; i < lineNames2.length; i++) {
            if (lineNames2[i].equals(name))
                System.out.println(name + "经过2号线");
        }
        for (int i = 0; i < lineNames3.length; i++) {
            if (lineNames3[i].equals(name))
                System.out.println(name + "经过3号线");
        }
        for (int i = 0; i < lineNames4.length; i++) {
            if (lineNames4[i].equals(name))
                System.out.println(name + "经过4号线");
        }
        for (int i = 0; i < lineNames5.length; i++) {
            if (lineNames5[i].equals(name))
                System.out.println(name + "经过6号线");
        }
        for (int i = 0; i < lineNames6.length; i++) {
            if (lineNames6[i].equals(name))
                System.out.println(name + "经过7号线");
        }
        for (int i = 0; i < lineNames7.length; i++) {
            if (lineNames7[i].equals(name))
                System.out.println(name + "经过8号线");
        }
        for (int i = 0; i < lineNames8.length; i++) {
            if (lineNames8[i].equals(name))
                System.out.println(name + "经过阳逻线");
        }
        for (int i = 0; i < lineNames9.length; i++) {
            if (lineNames9[i].equals(name))
                System.out.println(name + "经过11号线东段");
        }
    }

    public static String[] siteName = { "1号线", "2号线", "3号线", "4号线","5号线", "6号线", "7号线", "8号线", "阳逻线", "11号线","16号线","19号线" };
    static List<Paths> route = new ArrayList();

    public static void main(String[] args) {
        init();
        Frame f = new Frame("武汉地铁模拟系统-数媒1701李一泓");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setBounds(400, 200, 400, 300);
        f.setLayout(new FlowLayout());
        Button bu = new Button("Inquire By Site Name");
        f.add(bu);

        bu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                task3_1();
            }
        });

        Button bu2 = new Button("Inquire By Start&End Site Name And Get Price");
        f.add(bu2);
        bu2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                main_0();
            }
        });
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
            br = new BufferedReader(new FileReader("F:\\IntelliJ IDEA 2024.1\\intellij project\\learningproject\\src\\subway2.txt"));
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
                    br = null;
                }
            }
        }
    }

    public static void main_0() {
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
        System.out.println("注：1日票18元/张，3日票45元/张，7日票90元/张");
    }

    public static void number() {
        for (int i = 0; i < 9; i++) {
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
        for (int j = 0; j < 9; j++) {
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

    private static int countFare(String startSite, String endSite) {
        if (getDis(startSite, endSite) <= 9)
            return 2;
        if (getDis(startSite, endSite) > 9 && getDis(startSite, endSite) <= 14)
            return 3;
        if (getDis(startSite, endSite) > 14 && getDis(startSite, endSite) <= 21)
            return 4;
        if (getDis(startSite, endSite) > 21 && getDis(startSite, endSite) <= 30)
            return 5;
        if (getDis(startSite, endSite) > 30 && getDis(startSite, endSite) <= 41)
            return 6;
        if (getDis(startSite, endSite) > 41 && getDis(startSite, endSite) <= 53)
            return 7;
        return 0;
    }

    private static Result calculate(Site star, Site end) {
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < route.get(j).getList().size(); i++) {
                if (star.equals(route.get(j).getList().get(i)))
                    star = route.get(j).getList().get(i);
            }
        }
        for (int j = 0; j < 9; j++) {
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
                for (int j = 0; j < 9; j++) {
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
        for (int j = 0; j < 9; j++) {
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
            for (int j = 0; j < 9; j++) {
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
}
