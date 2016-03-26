package jason.tongji.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jason on 2016/3/26.
 */
public class EchartsDataScript {
    private String id;
    private String theme = "shine";
    private ArrayList<String> colors;
    private ArrayList<String> legends;

    private String xAxis_type = "category";
    private boolean xAxis_boundaryGap = false;
    private ArrayList<String> xAxis_data;
    private String xAxis_color = "#d4d4d4";

    private String yAxis_type = "value";
    private ArrayList<String> yAxis_data;
    private String yAxis_color = "#d4d4d4";

    private ArrayList<String> series_names;
    private ArrayList<String> series_types;
    private ArrayList<ArrayList<String>> series_data;

    public EchartsDataScript() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public void setLegends(ArrayList<String> legends) {
        this.legends = legends;
    }

    public void setxAxis_type(String xAxis_type) {
        this.xAxis_type = xAxis_type;
    }

    public void setxAxis_boundaryGap(boolean xAxis_boundaryGap) {
        this.xAxis_boundaryGap = xAxis_boundaryGap;
    }

    public void setxAxis_data(ArrayList<String> xAxis_data) {
        this.xAxis_data = xAxis_data;
    }

    public void setxAxis_color(String xAxis_color) {
        this.xAxis_color = xAxis_color;
    }

    public void setyAxis_type(String yAxis_type) {
        this.yAxis_type = yAxis_type;
    }

    public void setyAxis_color(String yAxis_color) {
        this.yAxis_color = yAxis_color;
    }

    public void setyAxis_data(ArrayList<String> yAxis_data) {
        this.yAxis_data = yAxis_data;
    }

    public void setSeries_data(ArrayList<ArrayList<String>> series_data) {
        this.series_data = series_data;
    }

    public void setSeries_names(ArrayList<String> series_names) {
        this.series_names = series_names;
    }

    public void setSeries_types(ArrayList<String> series_types) {
        this.series_types = series_types;
    }

    public void writeJS(String path) {
        try {
            File file = new File(path);
            System.out.println(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write("var myChart = echarts.init(document.getElementById('"+id+"'), '"+theme+"');\n");
            bw.newLine();
            bw.write("var option = {\n");
            //write colors
            bw.write("color: ['");
            for (int i=0;i<colors.size();i++) {
                bw.write(colors.get(i));
                if (i==colors.size()-1) {
                    bw.write("'],");
                } else {
                    bw.write("','");
                }
            }
            bw.newLine();
            //write tooltips
            bw.write("tooltip : {trigger: 'axis'},\n");
            //write legends
            bw.write("legend: {data:['");
            for (int i=0;i<legends.size();i++) {
                bw.write(legends.get(i));
                if (i==legends.size()-1) {
                    bw.write("']},");
                } else {
                    bw.write("','");
                }
            }
            bw.newLine();
            //write xAxis
            bw.write("xAxis : [\n");
            bw.write("\t{\n");
            bw.write("\ttype : '"+xAxis_type+"',\n");
            bw.write("\tboundaryGap : "+xAxis_boundaryGap+",\n");
            //write xAxis data
            bw.write("\tdata : [\"");
            for (int i=0;i<xAxis_data.size();i++) {
                bw.write(xAxis_data.get(i));
                if (i==xAxis_data.size()-1) {
                    bw.write("\"],");
                } else {
                    bw.write("\",\"");
                }
            }
            bw.newLine();
            bw.write("\taxisLine: {lineStyle : {color: '" + yAxis_color + "'}}\n");
            bw.write("\t}");
            bw.write("\n],\n");
            //write yAxis
            bw.write("yAxis : [\n");
            bw.write("\t{\n");
            bw.write("\ttype : '"+yAxis_type+"',\n");
            bw.write("\taxisLine: {lineStyle : {color: '"+yAxis_color+"'}}\n");
            bw.write("\t}\n");
            bw.write("],\n");
            //write series
            bw.write("series : [\n");
            for (int i=0;i<series_data.size();i++) {
                bw.write("\t{\n");
                bw.write("\t\tname:'"+series_names.get(i)+"',\n");
                bw.write("\t\ttype:'"+series_types.get(i)+"',\n");
                bw.write("\t\tdata:[");
                for (int j=0;j<series_data.get(i).size();j++) {
                    bw.write(series_data.get(i).get(j));
                    if (j==series_data.get(i).size()-1) {
                        bw.write("]");
                    } else {
                        bw.write(",");
                    }
                }
                if (i==series_data.size()-1) {
                    bw.write("\n\t}\n");
                } else {
                    bw.write("\n},\n");
                }
            }
            bw.write("]\n");
            bw.write("};\n");
            bw.write("myChart.setOption(option);");
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
