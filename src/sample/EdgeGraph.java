package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Julia on 25.06.2016.
 */
public class EdgeGraph implements Comparable<EdgeGraph> {
    private VertexGraph vertexGraphStart;
    private VertexGraph vertexGraphEnd;
    private double length;

    public EdgeGraph() {
        vertexGraphStart = null;
        vertexGraphEnd = null;
        length = 0;
    }

    public EdgeGraph(VertexGraph vertex1, VertexGraph vertex2, double length1) {
        vertexGraphStart = vertex1;
        vertexGraphEnd = vertex2;
        length = length1;
    }

    public VertexGraph getVertexGraphStart() {
        return vertexGraphStart;
    }

    public void setVertexGraphStart(VertexGraph vertexGraphStart) {
        this.vertexGraphStart = vertexGraphStart;
    }

    public VertexGraph getVertexGraphEnd() {
        return vertexGraphEnd;
    }

    public void setVertexGraphEnd(VertexGraph vertexGraphEnd) {
        this.vertexGraphEnd = vertexGraphEnd;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public int compareTo(EdgeGraph o) {
        int result;
        if (this.getVertexGraphStart().getNum() > (o.getVertexGraphStart().getNum())) {
            result = 1;
        } else if (this.getVertexGraphStart().getNum() == (o.getVertexGraphStart().getNum())) {
            result = 0;
        } else {
            result = -1;
        }
        if (result == 0) {
            if (this.getVertexGraphEnd().getNum() > (o.getVertexGraphEnd().getNum())) {
                result = 1;
            } else {
                result = -1;
            }
        }
        return result;
    }


    static class EdgeCompare implements Comparator<EdgeGraph> {

        public int compare(EdgeGraph e1, EdgeGraph e2) {
            return e1.compareTo(e2);
        }
    }
}


