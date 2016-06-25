package sample;

/**
 * Created by Julia on 25.06.2016.
 */
public class EdgeGraph {
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



}
