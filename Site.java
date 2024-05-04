package 大作业;

public class Site {
    private int num;
    private String name;
    private Double dis;
    private int line;
    public Site(Double dis, String name, int line, int num) {
        this.num = num;
        this.dis = dis;
        this.name = name;
        this.line = line;
    }

    public Site(Double dis, String name, int line) {
        this.dis = dis;
        this.name = name;
        this.line = line;
    }

    public Site(String name) {
        this.name = name;
    }

    public Double getdis() {
        return dis;
    }

    public void setdis(Double dis) {
        this.dis = dis;
    }

    public int getline() {
        return line;
    }

    public void setline(Integer line) {
        this.line = line;
    }

    public int getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Site) {
            Site s = (Site) obj;
            if (s.getName().equals(this.getName())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}