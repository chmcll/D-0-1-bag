package main.java.blog;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class read2 {
    public static void main(String[] args) throws IOException
    {

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入文件名：");
        String fileName=scanner.nextLine();
        String rootPath="E:\\idea_workspace\\data\\";
        //BufferedReader是可以按行读取文件
        FileInputStream inputStream = new FileInputStream(rootPath+fileName+".txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String str = null;
//            读取文件中的一行数据

//            存放物品价值和质量的列表
        ArrayList<String> profitList = new ArrayList<>();
        ArrayList<String> weightList = new ArrayList<>();
        while((str = bufferedReader.readLine()) != null)
        {
            if(str.contains("The profit of")){
                profitList.add(bufferedReader.readLine());
            }
            if(str.contains("The weight of")){
                weightList.add(bufferedReader.readLine());
            }
        }
//            System.out.println("======================这是每一组所有的物品价值======================");
//            for (String s : profitList) {
//                System.out.println(s);
//            }
//            System.out.println("================================这是每一组物品的重量================================");
//            for (String s : weightList) {
//                System.out.println(s);
//            }

        ArrayList<Integer> integers = new ArrayList<>();
//            遍历物品重量，将其分割出来
        System.out.println("输出所有物品价值：");
        int profits[][] = new int[12][4000];
        int i=0,j=0;
        for (String s : profitList) {
            j=0;
            String replace = s.replace(".", "");
            String[] split = replace.split(",");
            for (String s1 : split) {
//                    字符型转整形
                profits[i][j] =  Integer.parseInt(s1);
                System.out.println("输出第"+i+"组第"+j+"个物品的价值："+profits[i][j]);
                j++;
            }
            i++;
//            System.out.println("输出第一组第五个物品重量： "+profits[0][5]);
        }

//            遍历物品重量，将其分割出来
        System.out.println("\n输出所有物品重量：\n");
        int weights[][] = new int[12][4000];
        int m=0,n;
        for (String s : weightList) {
            n=0;
            String replace = s.replace(".", "");
            String[] split = replace.split(",");
            for (String s1 : split){
//                    字符型转整形
                weights[m][n] = Integer.parseInt(s1);
                System.out.println("输出第"+m+"组第"+n+"个物品的重量："+weights[m][n]);
                n++;
            }
            m++;
        }
//        绘制散点图
        System.out.println("请输入所要绘制的散点图的组数： ");
        int group=scanner.nextInt();
        drawScatter(profits[group-1],weights[group-1]);
        //close
        inputStream.close();
        bufferedReader.close();

    }

    /**
     * 画散点图
     * @throws IOException
     */
    private static   void drawScatter(int[] profitList,int[] weightList) throws IOException {
        //设置散点图数据集
        //设置第一个
        XYSeries firefox = new XYSeries("背包问题");
        for (int i = 0,j=0; i < profitList.length; i++,j++) {
            firefox.add(weightList[i],profitList[j]);
        }

        //添加到数据集
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(firefox);


        //实现简单的散点图，设置基本的数据
        JFreeChart freeChart = ChartFactory.createScatterPlot(
                "数据散点图",// 图表标题
                "重量",//x轴方向数据标签
                "价值",//y轴方向数据标签
                dataset,//数据集，即要显示在图表上的数据
                PlotOrientation.VERTICAL,//设置方向
                true,//是否显示图例
                true,//是否显示提示
                false//是否生成URL连接
        );

        //以面板显示
        ChartPanel chartPanel = new ChartPanel(freeChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 400));

        //创建一个主窗口来显示面板
        JFrame frame = new JFrame("散点图");
        frame.setLocation(500, 200);
        frame.setSize(1000, 800);

        //将主窗口的内容面板设置为图表面板
        frame.setContentPane(chartPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}