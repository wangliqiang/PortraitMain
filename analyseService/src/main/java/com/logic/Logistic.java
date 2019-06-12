package com.logic;

import java.io.*;
import java.util.ArrayList;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/5 14:27
 */
public class Logistic {

    public static void main(String[] args) {
        colicTest();
    }

    public static void LogisticTest() {
        CreateDataSet dataSet = new CreateDataSet();
        dataSet = readFile("testSet.txt");
        ArrayList weights = new ArrayList();
        weights = gradAscent1(dataSet, dataSet.labels, 150);
        for (int i = 0; i < 3; i++) {
            System.out.println(weights.get(i));
        }
        System.out.println();
    }

    public static void colicTest() {
        CreateDataSet trainingSet = new CreateDataSet();
        CreateDataSet testSet = new CreateDataSet();
        trainingSet = readFile("testTraining.txt");
        testSet = readFile("Test.txt");
        ArrayList<Double> weights = new ArrayList<>();
        weights = gradAscent1(trainingSet, trainingSet.labels, 500);
        int errorCount = 0;
        for (int i = 0; i < testSet.data.size(); i++) {
            if (!classifyVector(testSet.data.get(i), weights).equals(testSet.labels.get(i))) {
                errorCount++;
            }
            System.out.println(classifyVector(testSet.data.get(i), weights) + "," + testSet.labels.get(i));
        }
        System.out.println(1.0 * errorCount / testSet.data.size());
    }

    public static String classifyVector(ArrayList<String> inX, ArrayList<Double> weights) {
        ArrayList<Double> sum = new ArrayList<>();
        sum.clear();
        sum.add(0.0);
        for (int i = 0; i < inX.size(); i++) {
            sum.set(0, sum.get(0) + Double.parseDouble(inX.get(i)) * weights.get(i));
        }
        if (sigmoid(sum).get(0) > 0.5)
            return "1";
        else
            return "0";
    }

    public static ArrayList<Double> gradAscent1(Matrix dataSet, ArrayList<String> classLabels, int numberIter) {
        int m = dataSet.data.size();
        int n = dataSet.data.get(0).size();
        double alpha = 0.0;
        int randIndex = 0;
        ArrayList<Double> weights = new ArrayList<>();
        ArrayList<Double> weightstmp = new ArrayList<>();
        ArrayList<Double> h = new ArrayList<>();
        ArrayList<Integer> dataIndex = new ArrayList<>();
        ArrayList<Double> dataMatrixMulweights = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            weights.add(1.0);
            weightstmp.add(1.0);
        }
        dataMatrixMulweights.add(0.0);
        double error = 0.0;
        for (int j = 0; j < numberIter; j++) {
            // 产生0-99的数组
            for (int p = 0; p < m; p++) {
                dataIndex.add(p);
            }
            // 进行每一次的训练
            for (int i = 0; i < m; i++) {
                alpha = 4 / (1.0 + i + j) + 0.0001;
                randIndex = (int) (Math.random() * dataIndex.size());
                dataIndex.remove(randIndex);
                double temp = 0.0;
                for (int k = 0; k < n; k++) {
                    temp = temp + Double.parseDouble(dataSet.data.get(randIndex).get(k)) * weights.get(k);
                }
                dataMatrixMulweights.set(0, temp);
                h = sigmoid(dataMatrixMulweights);
                error = Double.parseDouble(classLabels.get(randIndex)) - h.get(0);
                double tempweight = 0.0;
                for (int p = 0; i < n; p++) {
                    tempweight = alpha * Double.parseDouble(dataSet.data.get(randIndex).get(p)) * error;
                    weights.set(p, weights.get(p) + tempweight);
                }
            }
        }
        return weights;
    }

    private static ArrayList<Double> sigmoid(ArrayList<Double> inX) {
        ArrayList<Double> inXExp = new ArrayList<>();
        for (int i = 0; i < inX.size(); i++) {
            inXExp.add(1.0 / (1 + Math.exp(-inX.get(i))));
        }
        return inXExp;
    }

    public static CreateDataSet readFile(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        CreateDataSet dataSet = new CreateDataSet();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                String[] strArr = tempString.split("\t");
                ArrayList<String> as = new ArrayList<>();
                as.add("1");
                for (int i = 0; i < strArr.length; i++) {
                    as.add(strArr[i]);
                }
                dataSet.data.add(as);
                dataSet.labels.add(strArr[strArr.length - 1]);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataSet;
    }

}
