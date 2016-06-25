package sample;

import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.ArrayList;

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
    private Alert alert;
    private static int amount = 0;
    private ArrayList<VertexGraph> listVertex = new ArrayList<>();

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
                        amount ++;
                        Button button = new Button(Integer.toString(vertexGraph.getNumber()));
                        button.setLayoutX(x * 37);
                        button.setLayoutY(390 - y * 39);
                        pane.getChildren().add(button);
                        //System.out.println(vertexGraph.toString());
                    }
                    // и вот тут наконец-то начинается магия с созданием графов
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
                    if (x > amount || y > amount || x < 0 || y < 0) {
                       error("Не существет заданных вершин.");
                    }
                    if (x == y) {
                        error("Выберите различные вершины.");
                    }
                    else {
                        VertexGraph vertexGraph1 = listVertex.get(x - 1);
                        VertexGraph vertexGraph2 = listVertex.get(y - 1);
                        double length = getLength(vertexGraph1.getX(),vertexGraph1.getY(),vertexGraph2.getX(),vertexGraph2.getY());
                        EdgeGraph edgeGraph = new EdgeGraph(listVertex.get(x - 1),listVertex.get(y - 1), length);
                        Line line = new Line(vertexGraph1.getX()-5,vertexGraph1.getY() ,vertexGraph2.getX()-5,vertexGraph2.getY() );
                        Line line1 = new Line(vertexGraph2.getX()-5,vertexGraph2.getY(),vertexGraph2.getX() - 10,vertexGraph2.getY() + 5);
                        Line line2 = new Line(vertexGraph2.getX()-5,vertexGraph2.getY(),vertexGraph2.getX() - 10,vertexGraph2.getY() - 5);
                        pane.getChildren().add(line);
                        pane.getChildren().add(line1);
                        pane.getChildren().add(line2);

                    }
                    // и вот тут наконец-то начинается магия с соединением графов
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    error("Введено некорректное значение в одно из полей! Пожалуста, вводите только цифры.");
                }
            }
        }
    }

    public void error(String s) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Некорректный ввод");
        alert.setHeaderText(s);
        alert.showAndWait();
    }

    public double getLength (int x, int y, int x1, int y1) {
        double length = Math.sqrt((x1-x)^2+(y1-y)^2);
        return length;
    }

}
