package Dijkstra;

import java.util.Vector;

/**
 * Created by FlipBook TP300LD on 24.06.2016.
 */

public class DijkstraAlgorithm {



    protected Vector<Integer> AlgDijkstra(int start, int end, Integer nSize, Vector<VertexDijkstra> arrOfLen)
    {
        int inf = 2147483647;
//        int [] n = new int[nSize];
        Vector<Integer> vLen = new Vector<Integer>(nSize);
        for(int i = 0; i < nSize; i++) vLen.addElement(inf);
            //,Inf);    //(nSize);        //, Inf); // Длина путей
        Vector<Integer> realWay = new Vector<Integer>(nSize);//, -1); // Предыдущие значения вершин
        for(int i = 0; i < nSize; i++) vLen.addElement(-1);
        Vector<Integer> u = new Vector<Integer>(nSize);//),0); //Вектор посещенных вершин
        for(int i = 0; i < nSize; i++) vLen.addElement(0);
        vLen.add(start,0);
        int CurrV=start; //Текущий элемент
        u.add(CurrV,1);
        // Алгоритм

        while(u.elementAt(end)!=1) //Цикл выполняется до тех пор, пока не посетим конечную вершину
        {
            int minLen=inf;
            for (int v = 0; v < arrOfLen.size(); v++)
            {
                if(arrOfLen.elementAt(v).source == CurrV)
                {
                    if (vLen.elementAt(arrOfLen.elementAt(v).dest) > vLen.elementAt(arrOfLen.elementAt(v).source) + arrOfLen.elementAt(v).length)
                    {
                        vLen.elementAt(arrOfLen.elementAt(v).dest) = vLen.elementAt(arrOfLen.elementAt(v).source) + arrOfLen.elementAt(v).length;
                        realWay.elementAt(arrOfLen.elementAt(v).dest) = arrOfLen.elementAt(v).source;
                    }
                }
            }
            for (int i = 0; i< nSize; i++)
            {
                if ((vLen.elementAt(i) < minLen)&&(u.elementAt(i)==0))
                {
                    minLen = vLen.elementAt(i);
                }
            }
            for(int j=0;j< nSize;j++)
            {
                if((u.elementAt(j)==0)&&(vLen.elementAt(j)==minLen))
                {
                    CurrV=j;
                }
            }
            u.elementAt(CurrV) = 1;
        }
        //Восстанавлием кратчайший путь
        if (vLen.elementAt(end) == inf)
        {
            realWay.clear();
//            realWay.append(-1);
            realWay.addElement(-1);
        }
        else
        {
            Vector<Integer> path = new Vector<>();
            for (int cur = end; cur != -1; cur = realWay.elementAt(cur))
                path.addElement(cur);
//                path.append(cur);
            realWay.clear();
            for (int cur = path.size() - 1; cur > -1; cur--)
                realWay.addElement(path.elementAt(cur));
//                realWay.append(path.at(cur));
        }
        return realWay;
    }

}
