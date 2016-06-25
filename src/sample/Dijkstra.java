package sample;

import java.util.ArrayList;

/**
 * Created by Julia on 25.06.2016.
 */
public class Dijkstra {
    private final static double inf = 1000000;
    private ArrayList<Double> vLen = new ArrayList<>();// Длина путей
    private ArrayList<Integer> visitedTops = new ArrayList<>();//Посещенные вершины
    private ArrayList<Integer> realWay = new ArrayList<>();//Предыдущие значения вершин
    private double length;

    public double getLength() {
        return length;
    }

    public Dijkstra(int nSize) {
        for (int i = 0; i < nSize; i++) {
            vLen.add(inf);
            realWay.add(-1);
            visitedTops.add(0);
        }
    }

    public ArrayList<Integer> dijkstraAlgoritm(int start, int end, int nSize, ArrayList<EdgeGraph> edgeGraphs) {
        vLen.set(start, 0.0);
        int CurrV = start; //Текущий элемент
        visitedTops.set(CurrV, 1);
        while (visitedTops.get(end) != 1) //Цикл выполняется до тех пор, пока не посетим конечную вершину
        {
            double minLen = inf;
            for (int v = 0; v < edgeGraphs.size(); v++) {
                if (edgeGraphs.get(v).getVertexGraphStart().getNum()-1 == CurrV) {
                    if (vLen.get(edgeGraphs.get(v).getVertexGraphEnd().getNum()-1) > vLen.get(edgeGraphs.get(v).getVertexGraphStart().getNum()-1) + edgeGraphs.get(v).getLength()) {
                        vLen.set(edgeGraphs.get(v).getVertexGraphEnd().getNum()-1, vLen.get(edgeGraphs.get(v).getVertexGraphStart().getNum()-1) + edgeGraphs.get(v).getLength());
                        realWay.set(edgeGraphs.get(v).getVertexGraphEnd().getNum()-1,edgeGraphs.get(v).getVertexGraphStart().getNum()-1);
                    }
                }
            }
            for (int i = 0; i < nSize; i++) {
                if ((vLen.get(i) < minLen) && (visitedTops.get(i) == 0)) {
                    minLen = vLen.get(i);
                }
            }
            for (int j = 0; j < nSize; j++) {
                if ((visitedTops.get(j) == 0) && (vLen.get(j) == minLen)) {
                    CurrV = j;
                }
            }
            visitedTops.set(CurrV,1);
        }
        //Восстанавлием кратчайший путь
        if (vLen.get(end) == inf)
        {
            realWay.clear();
            realWay.add(-1);
        }
        else
        {
            length = vLen.get(end);
            ArrayList<Integer> path = new ArrayList<>();
            for (int cur = end; cur != -1; cur = realWay.get(cur))
                path.add(cur);
            realWay.clear();
            for (int cur = path.size() - 1; cur > -1; cur--)
                realWay.add(path.get(cur));
        }
        return realWay;
    }
}
