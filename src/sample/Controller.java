package sample;

import com.sun.javafx.geom.Edge;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Collections;


public class Controller {
    @FXML
    private TextField graphX;
    @FXML
    private TextField graphY;
    @FXML
    private TextField vertexGraph1;
    @FXML
    private TextField vertexGraph2;
    @FXML
    private Pane pane;
    @FXML
    private TextField startVertex;
    @FXML
    private TextField endVertex;
    @FXML
    private TextField numDemVer;
    @FXML
    private TextArea textArea;
    private Alert alert;
    private static int amountV = 0;
    private static int amountE = 0;
    private ArrayList<VertexGraph> listVertex = new ArrayList<>();
    private ArrayList<EdgeGraph> listEdge = new ArrayList<>();

    @FXML
    public void handleAddVertexGraph() {
        setDefaultLine(1);
        if (graphX.getText() == null || graphX.getText().length() == 0) {
            error("Пожалуйста, заполните поле <X>");
        } else {
            if (graphY.getText() == null || graphY.getText().length() == 0) {
                error("Пожалуйста, заполните поле <Y>");
            } else {
                try {
                    int x = Integer.parseInt(graphX.getText());
                    int y = Integer.parseInt(graphY.getText());
                    if (x > 15 || x < 0 || y > 10 || y < 0) {
                        error("Граф может быть не отображен. Пожалуйста, введите координаты в соответствии заданной координатной осью.");
                    } else {
                        VertexGraph vertexGraph = new VertexGraph(x, y);
                        listVertex.add(vertexGraph);
                        amountV++;
                        Circle circle = new Circle(vertexGraph.getX(), vertexGraph.getY() + 10, 10);
                        circle.setFill(Color.AQUA);
                        Label label = new Label(Integer.toString(vertexGraph.getNum()));
                        label.setTextFill(Color.DARKBLUE);
                        label.setLayoutX(vertexGraph.getX() - 5);
                        label.setLayoutY(vertexGraph.getY() + 5);
                        pane.getChildren().add(circle);
                        pane.getChildren().add(label);
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    error("Введено некорректное значение в одно из полей! Пожалуста, вводите только цифры.");
                }
            }
        }
    }

    @FXML
    public void handleAddEdge() {
        setDefaultLine(1);
        if (vertexGraph1.getText() == null || vertexGraph1.getText().length() == 0) {
            error("Пожалуйста, заполните поле <Начальная>");
        } else {
            if (vertexGraph2.getText() == null || vertexGraph2.getText().length() == 0) {
                error("Пожалуйста, заполните поле <Конечная>");
            } else {
                try {
                    int x = Integer.parseInt(vertexGraph1.getText());
                    int y = Integer.parseInt(vertexGraph2.getText());
                    if (x > amountV || y > amountV || x <= 0 || y <= 0) {
                        error("Не существет заданных вершин.");
                    }
                    if (x == y) {
                        error("Выберите различные вершины.");
                    } else {
                        paintLine(x-1,y-1);
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    error("Введено некорректное значение в одно из полей! Пожалуста, вводите только цифры.");
                }
            }
        }
    }

    @FXML
    public void displayCoordinates() {
        textArea.clear();
        for (VertexGraph ver : listVertex) {
            textArea.appendText("Координаты графа № " + ver.getNum() + "\n" + " X: " + ver.getX() / 39 + " Y: " + (390 - ver.getY()) / 39 + "\n");
        }
    }

    @FXML
    public void displayEdge() {
        textArea.clear();
        int i = 0;
        for (EdgeGraph edge : listEdge) {
            textArea.appendText("Ребро № " + ++i + "\n" + edge.getVertexGraphStart().getNum() + " -> " + edge.getVertexGraphEnd().getNum() + "\n" + "Длина ребра: " + edge.getLength() + "\n");
        }
    }

    @FXML
    public void shortestPathProblem() {
        textArea.clear();
        setDefaultLine(1);
        if (startVertex.getText() == null || startVertex.getText().length() == 0) {
            error("Пожалуйста, заполните поле <Начальная вершина>");
        } else {
            if (endVertex.getText() == null || endVertex.getText().length() == 0) {
                error("Пожалуйста, заполните поле <Конечная вершина>");
            } else {
                try {
                    int x = Integer.parseInt(startVertex.getText());
                    int y = Integer.parseInt(endVertex.getText());
                    if (x > amountV || y > amountV || x <= 0 || y <= 0) {
                        error("Не существет заданных вершин.");
                    }
                    if (x == y) {
                        error("Выберите различные вершины.");
                    } else {
                        Dijkstra dijkstra = new Dijkstra(amountE);
                        ArrayList<EdgeGraph> listEdge1 = sortEdgeList(listEdge);
                        ArrayList<Integer> shortWay = dijkstra.dijkstraAlgoritm(x - 1, y - 1, amountE, listEdge1);
                        textArea.appendText("Кратчайший путь из " + x + " в " + y + ": " + "\n");
                        if (shortWay.get(0) == -1)
                            textArea.appendText("Не существует");
                        else {
                            for (int cur = 0; cur < shortWay.size() - 1; ++cur) {
                                textArea.appendText(Integer.toString(shortWay.get(cur) + 1) + " -> " + Integer.toString(shortWay.get(cur + 1) + 1) + "\n");
                                for (EdgeGraph edge : listEdge1) {
                                    if (((edge.getVertexGraphStart().getNum() == shortWay.get(cur) + 1)) && (edge.getVertexGraphEnd().getNum() == shortWay.get(cur + 1) + 1)) {
                                        pane.getChildren().remove(edge.line);
                                        edge.line.setStrokeWidth(3);
                                        pane.getChildren().add(edge.line);
                                    }
                                }
                            }
                            textArea.appendText("Стоимость пути: " + Double.toString(dijkstra.getLength()));
                        }
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    error("Введено некорректное значение в одно из полей! Пожалуста, вводите только цифры.");
                }
            }
        }
    }

    @FXML
    public void deleteGraph() {
        textArea.clear();
        startVertex.clear();
        endVertex.clear();
        vertexGraph2.clear();
        vertexGraph1.clear();
        graphX.clear();
        graphY.clear();
        pane.getChildren().clear();
        listEdge.clear();
        listVertex.clear();
        VertexGraph.setNumber(0);
    }

    @FXML
    public void demonstration() {
        if (numDemVer.getText() == null || numDemVer.getText().length() == 0) {
            error("Пожалуйста, заполните корректно поле <Количество вершин>");
        }
        else {
            try {
                int num = Integer.parseInt(numDemVer.getText());
                if (num > 10 || num < 3) error("Введите количество вершин в диапазоне от 3 до 10");
                else {
                    double angle = 2*Math.PI/num;
                    double a = 100.0;
                    double r = a / (2 * Math.sin(Math.PI/num)) /39;
                    double cx = 300/39;
                    double cy = 248/39;
                    for (int i = 0; i < num ; i++) {
                        VertexGraph vertexGraph = new VertexGraph((int)(cx + r * Math.cos(angle * i)),(int)(cy + r * Math.sin(angle * i)));
                        listVertex.add(vertexGraph);
                        amountV++;
                        Circle circle = new Circle(vertexGraph.getX(), vertexGraph.getY() + 10, 10);
                        circle.setFill(Color.AQUA);
                        Label label = new Label(Integer.toString(vertexGraph.getNum()));
                        label.setTextFill(Color.DARKBLUE);
                        label.setLayoutX(vertexGraph.getX() - 5);
                        label.setLayoutY(vertexGraph.getY() + 5);
                        pane.getChildren().add(circle);
                        pane.getChildren().add(label);
                    }

                    for(int i = 1; i < num; i++){ // connect one-to-one vertex
                        paintLine(i-1,i);
                    }
                    paintLine(num-1,0);
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                error("Введено некорректное значение в поле <Количество вершин>! Пожалуста, вводите только цифры.");
            }
        }
    }

    public void error(String s) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Некорректный ввод");
        alert.setHeaderText(s);
        alert.showAndWait();
    }

    public double getLength(int x, int y, int x1, int y1) {
        double X = Math.pow(x1 - x, 2);
        double Y = Math.pow(y1 - y, 2);
        double length = (Math.sqrt(X + Y)) / 39;
        return length;
    }

    public ArrayList<EdgeGraph> sortEdgeList(ArrayList<EdgeGraph> edgeGraphs) {
        ArrayList<EdgeGraph> newEdgeGraph;
        Collections.sort(edgeGraphs, new EdgeGraph.EdgeCompare());
        newEdgeGraph = edgeGraphs;
        return newEdgeGraph;
    }

    public void setDefaultLine(int width) {
        for (EdgeGraph edge : listEdge) {
            pane.getChildren().remove(edge.line);
            edge.line.setStrokeWidth(width);
            pane.getChildren().add(edge.line);
        }
    }

    public void paintLine(int first, int second){
        VertexGraph vertexGraph1 = listVertex.get(first);
        VertexGraph vertexGraph2 = listVertex.get(second);
        double length = getLength(vertexGraph1.getX(), vertexGraph1.getY(), vertexGraph2.getX(), vertexGraph2.getY());
        amountE++;
        double horizontalComponent = vertexGraph2.getX() - vertexGraph1.getX();
        double verticalComponent = vertexGraph2.getY() - vertexGraph1.getY();
        double realLength = Math.sqrt(horizontalComponent * horizontalComponent +
                verticalComponent * verticalComponent);
        double angle1 = Math.acos(horizontalComponent / realLength); // Угол грани
        if (verticalComponent >= 0) angle1 = Math.PI * 2 - angle1;
        double shiftX = 10 * Math.cos(angle1);
        double shiftY = 10 * Math.sin(angle1);
        EdgeGraph edgeGraph = new EdgeGraph(listVertex.get(first), listVertex.get(second), length,
                new Line(vertexGraph1.getX() + shiftX,
                        vertexGraph1.getY() - shiftY + 10,
                        vertexGraph2.getX() - shiftX,
                        vertexGraph2.getY() + shiftY + 10));
        listEdge.add(edgeGraph);
        Line line1 = new Line(vertexGraph2.getX() - shiftX, vertexGraph2.getY() + shiftY + 10,
                vertexGraph2.getX() + Math.sin(angle1 - Math.PI / 3) * 10 - shiftX,
                vertexGraph2.getY() + Math.cos(angle1 - Math.PI / 3) * 10 + shiftY + 10);
        Line line2 = new Line(vertexGraph2.getX() - shiftX, vertexGraph2.getY() + shiftY + 10,
                vertexGraph2.getX() + Math.sin(angle1 - Math.PI + Math.PI / 3) * 10 - shiftX,
                vertexGraph2.getY() + Math.cos(angle1 - Math.PI + Math.PI / 3) * 10 + shiftY + 10);
        edgeGraph.line.setFill(Color.GRAY);
        line1.setFill(Color.GRAY);
        line2.setFill(Color.GRAY);
        pane.getChildren().add(edgeGraph.line);
        pane.getChildren().add(line1);
        pane.getChildren().add(line2);
    }
}
