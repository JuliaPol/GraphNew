package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
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
    private TextArea textArea;
    private Alert alert;
    private static int amountV = 0;
    private static int amountE = 0;
    private ArrayList<VertexGraph> listVertex = new ArrayList<>();
    private ArrayList<EdgeGraph> listEdge = new ArrayList<>();

    @FXML
    public void handleAddVertexGraph() {
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
                        Button button = new Button(Integer.toString(vertexGraph.getNumber()));
                        button.setLayoutX(x * 37);
                        button.setLayoutY(390 - y * 39);
                        pane.getChildren().add(button);
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
                        VertexGraph vertexGraph1 = listVertex.get(x - 1);
                        VertexGraph vertexGraph2 = listVertex.get(y - 1);
                        double length = getLength(vertexGraph1.getX(), vertexGraph1.getY(), vertexGraph2.getX(), vertexGraph2.getY());
                        EdgeGraph edgeGraph = new EdgeGraph(listVertex.get(x - 1), listVertex.get(y - 1), length);
                        amountE++;
                        listEdge.add(edgeGraph);
                        double horizontalComponent = vertexGraph2.getX() - vertexGraph1.getX();
                        double verticalComponent = vertexGraph2.getY() - vertexGraph1.getY();
                        double realLength = Math.sqrt(horizontalComponent * horizontalComponent +
                                verticalComponent * verticalComponent);
                        double angle = Math.acos(horizontalComponent / realLength); // Угол грани
                        if (verticalComponent >= 0) angle = Math.PI * 2 - angle;
                        Line line = new Line(vertexGraph1.getX() - 5, vertexGraph1.getY(),
                                vertexGraph2.getX() - 5, vertexGraph2.getY());
                        Line line1 = new Line(vertexGraph2.getX() - 5, vertexGraph2.getY(),
                                vertexGraph2.getX() + Math.sin(angle - Math.PI / 3) * 10 - 5,
                                vertexGraph2.getY() + Math.cos(angle - Math.PI / 3) * 10);
                        Line line2 = new Line(vertexGraph2.getX() - 5, vertexGraph2.getY(),
                                vertexGraph2.getX() + Math.sin(angle - Math.PI + Math.PI / 3) * 10 - 5,
                                vertexGraph2.getY() + Math.cos(angle - Math.PI + Math.PI / 3) * 10);
                        pane.getChildren().add(line);
                        pane.getChildren().add(line1);
                        pane.getChildren().add(line2);

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


}
