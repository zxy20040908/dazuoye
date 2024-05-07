package 大作业;

import 大作业.SubwaySystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class initial {
    public static void init(SubwaySystem subwaySystem) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("F:\\IntelliJ IDEA 2024.1\\intellij project\\learningproject\\src\\subway1.txt"));
            List<String[]> lineNamesList = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lineNamesList.add(line.split("---"));
            }
            br.close();
            // 将每条线路的站点名称存储到对应的数组中
            subwaySystem.lineNames1 = lineNamesList.get(0);
            subwaySystem.lineNames2 = lineNamesList.get(1);
            subwaySystem.lineNames3 = lineNamesList.get(2);
            subwaySystem.lineNames4 = lineNamesList.get(3);
            subwaySystem.lineNames5 = lineNamesList.get(4);
            subwaySystem.lineNames6 = lineNamesList.get(5);
            subwaySystem.lineNames7 = lineNamesList.get(6);
            subwaySystem.lineNames8 = lineNamesList.get(7);
            subwaySystem.lineNames9 = lineNamesList.get(8);
            subwaySystem.lineNames10 = lineNamesList.get(9);
            subwaySystem.lineNames11 = lineNamesList.get(10);
            subwaySystem.lineNames12 = lineNamesList.get(11);
        } catch (FileNotFoundException e1) {
            System.out.println("找不到文件！");
        } catch (IOException e2) {
            System.out.println("IO流异常!");
        }
    }
}