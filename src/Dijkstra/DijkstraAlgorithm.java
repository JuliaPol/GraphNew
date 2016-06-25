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
        for(int i = 0; i < nSize; i++) vLen.addElement(inf);       // Длина путей

        Vector<Integer> realWay = new Vector<Integer>(nSize); // Предыдущие значения вершин
        for(int i = 0; i < nSize; i++) vLen.addElement(-1);

        Vector<Integer> u = new Vector<Integer>(nSize); //Вектор посещенных вершин
        for(int i = 0; i < nSize; i++) vLen.addElement(0);

        vLen.setElementAt(start,0);
        int CurrV=start; //Текущий элемент
        u.setElementAt(CurrV,1);

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
                        vLen.setElementAt(vLen.elementAt(arrOfLen.elementAt(v).source) + arrOfLen.elementAt(v).length,arrOfLen.elementAt(v).dest);
                        realWay.setElementAt(arrOfLen.elementAt(v).source,arrOfLen.elementAt(v).dest);
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
            u.setElementAt(1,CurrV);
        }
        //Восстанавлием кратчайший путь
        if (vLen.elementAt(end) == inf)
        {
            realWay.clear();
            realWay.addElement(-1);
        }
        else
        {
            Vector<Integer> path = new Vector<>();
            for (int cur = end; cur != -1; cur = realWay.elementAt(cur))
                path.addElement(cur);
            realWay.clear();
            for (int cur = path.size() - 1; cur > -1; cur--)
                realWay.addElement(path.elementAt(cur));
        }
        return realWay;
    }

}
