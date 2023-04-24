/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ViewControl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yousef
 */
public class MainMenuController implements Initializable {

    @FXML
    private Button btnMeals;
    @FXML
    private Button btnDrank;
    @FXML
    private Button btnExit;
    @FXML
    private TextField numMeals;
    @FXML
    private TextField numDranks;
    @FXML
    private Pane mealID;
    @FXML
    private TextField mealName;
    @FXML
    private TextField mealCost;
    @FXML
    private ComboBox<String> mealType;
    @FXML
    private TextField search;
    @FXML
    TableView<Meals> tableM;
    @FXML
    private TableColumn<Meals, Integer> tbNum;
    @FXML
    private TableColumn<Meals, String> tbName;
    @FXML
    private TableColumn<Meals, String> tbType;
    @FXML
    private TableColumn<Meals, Double> tbCost;
    @FXML
    private Button addMeal;
    @FXML
    private Button upeateMeal;
    @FXML
    private Button deleteMeal;
    @FXML
    private Button resetMeal;
    @FXML
    private Label DoneM;
    @FXML
    private Pane drankID;
    @FXML
    private TextField drankName;
    @FXML
    private TextField drankCost;
    @FXML
    private ComboBox<String> drankType;
    @FXML
    private TextField searchDrank;

    @FXML
    TableView<Drinks> tableD;
    @FXML
    private TableColumn<Drinks, Integer> tbNumD;
    @FXML
    private TableColumn<Drinks, String> tbNameD;
    @FXML
    private TableColumn<Drinks, String> tbTypeD;
    @FXML
    private TableColumn<Drinks, Double> tbCostD;
    @FXML
    private Button addDrank;
    @FXML
    private Button updateDrank;
    @FXML
    private Button deleteDrank;
    @FXML
    private Button resetDrank;
    @FXML
    private Label DoneM1;
    @FXML
    private TextField btnMealID;
    @FXML
    private TextField btnDrankID;
    @FXML
    private Text textMeal;
    @FXML
    private Text textDrank;

    ObservableList<Meals> listM2;
    ObservableList<Drinks> listD2;

    int index = -1;
    int indexD = -1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbNum.setCellValueFactory(new PropertyValueFactory<Meals, Integer>("id"));
        tbName.setCellValueFactory(new PropertyValueFactory<Meals, String>("name"));
        tbType.setCellValueFactory(new PropertyValueFactory<Meals, String>("type"));
        tbCost.setCellValueFactory(new PropertyValueFactory<Meals, Double>("cost"));
        listM2 = ConnectionDB.getMeals();
        tableM.setItems(listM2);
        tbNumD.setCellValueFactory(new PropertyValueFactory<Drinks, Integer>("id"));
        tbNameD.setCellValueFactory(new PropertyValueFactory<Drinks, String>("name"));
        tbTypeD.setCellValueFactory(new PropertyValueFactory<Drinks, String>("type"));
        tbCostD.setCellValueFactory(new PropertyValueFactory<Drinks, Double>("cost"));
        listD2 = ConnectionDB.getDrinks();
        tableD.setItems(listD2);
        try {
            numDranks.setText(ConnectionDB.count("numD", "dranks") + "");
            numMeals.setText(ConnectionDB.count("numM", "meals") + "");
        } catch (SQLException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList listM = FXCollections.observableArrayList("Fish", "Meat");
        ObservableList listD = FXCollections.observableArrayList("Natural", "Artificial");
        drankType.getItems().addAll(listD);
        mealType.getItems().addAll(listM);

    }

    @FXML
    public void buttonEvent(ActionEvent event) throws IOException {
        String nameOfButton = ((Button) event.getSource()).getText();
        if (nameOfButton.equals("Meals")) {
            drankID.setVisible(false);
            mealID.setVisible(true);

        } else if (nameOfButton.equals("Dranks")) {
            mealID.setVisible(false);
            drankID.setVisible(true);
        } else if (nameOfButton.equals("Exit")) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    public void insertMeals() {
        int num = Integer.parseInt(btnMealID.getText());
        String name = mealName.getText();
        String type = (String) mealType.getSelectionModel().getSelectedItem();
        double cost = Double.parseDouble(mealCost.getText());
        listM2.add(new Meals(name, type, num, cost));
        if (!ConnectionDB.insert("meals", num, name, type, cost)) {
            textMeal.setText("Insert Done");
            textMeal.setVisible(true);
            numMeals.setText(Integer.parseInt(numMeals.getText()) + 1 + "");
        }

    }

    @FXML
    public void insertDrinks() {
        int num = Integer.parseInt(btnDrankID.getText());
        String name = drankName.getText();
        String type = (String) drankType.getSelectionModel().getSelectedItem();
        double cost = Double.parseDouble(drankCost.getText());
        listD2.add(new Drinks(name, type, num, cost));
        if (!ConnectionDB.insert("dranks", num, name, type, cost)) {
            textDrank.setText("Insert Done");
            textDrank.setVisible(true);
            numDranks.setText(Integer.parseInt(numDranks.getText()) + 1 + "");
        }
    }

    public void getSelected() {
        index = tableM.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        btnMealID.setText(tbNum.getCellData(index).toString());
        mealName.setText(tbName.getCellData(index).toString());
        mealType.getSelectionModel().select(tbType.getCellData(index));
        mealCost.setText(tbCost.getCellData(index).toString());
    }

    public void updateMeals() {
        int num = Integer.parseInt(btnMealID.getText());
        String name = mealName.getText();
        String type = (String) mealType.getSelectionModel().getSelectedItem();
        double cost = Double.parseDouble(mealCost.getText());
        if (ConnectionDB.update("Meals", "where numM='" + num + "'", name, type, cost)) {
            listM2.set(index, new Meals(name, type, num, cost));
            DoneM.setText("Done Update");
            DoneM.setVisible(true);
        }
    }

    public void getSelectedD() {
        indexD = tableD.getSelectionModel().getSelectedIndex();
        if (indexD <= -1) {
            return;
        }
        btnDrankID.setText(tbNumD.getCellData(indexD).toString());
        drankName.setText(tbNameD.getCellData(indexD).toString());
        drankType.getSelectionModel().select(tbTypeD.getCellData(indexD));
        drankCost.setText(tbCostD.getCellData(indexD).toString());
    }

    public void updateDrinks() {
        int num = Integer.parseInt(btnDrankID.getText());
        String name = drankName.getText();
        String type = (String) drankType.getSelectionModel().getSelectedItem();
        double cost = Double.parseDouble(drankCost.getText());
        if (ConnectionDB.update("Dranks", "where numD='" + num + "'", name, type, cost)) {
            listD2.set(indexD, new Drinks(name, type, num, cost));
            DoneM1.setText("Done Update");
            DoneM1.setVisible(true);
        }
    }

    public void clearM() {
        btnMealID.clear();
        mealName.clear();
        mealType.getSelectionModel().select(-1);
        mealCost.clear();
    }

    public void clearD() {
        btnDrankID.clear();
        drankName.clear();
        drankType.getSelectionModel().select(-1);
        drankCost.clear();
    }

    public void deleteM() {
        if (index <= -1) {
            return;
        }
        if (ConnectionDB.delete("Meals", "numM=" + tbNum.getCellData(index))) {
            DoneM.setText("Done Delete");
            DoneM.setVisible(true);
            numMeals.setText(Integer.parseInt(numMeals.getText()) - 1 + "");
            listM2.remove(index);
            index = -1;
            clearM();
        }
    }

    public void deleteD() {
        if (indexD <= -1) {
            return;
        }
        if (ConnectionDB.delete("Dranks", "numD=" + tbNumD.getCellData(indexD))) {
            DoneM1.setText("Done Delete");
            DoneM1.setVisible(true);
            numDranks.setText(Integer.parseInt(numDranks.getText()) - 1 + "");
            listD2.remove(indexD);
            indexD = -1;
            clearD();
        }
    }

    public void searchM() {
        search.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (search.textProperty().get().isEmpty()) {
                    tableM.setItems(listM2);
                    return;
                }
                ObservableList<Meals> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<Meals, ?>> cols = tableM.getColumns();
                for (int i = 0; i < listM2.size(); i++) {
                    for (int j = 0; j < cols.size(); j++) {
                        TableColumn<Meals, ?> col = cols.get(j);
                        String cellValue = col.getCellData(listM2.get(i)).toString();

                        cellValue = cellValue.toLowerCase();
                        if (cellValue.contains(search.getText().toLowerCase()) && cellValue.startsWith(search.getText().toLowerCase())) {
                            tableItems.add(listM2.get(i));
                            break;

                        }
                    }
                }
                tableM.setItems(tableItems);

            }

        });

    }

    public void searchD() {
        searchDrank.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (searchDrank.textProperty().get().isEmpty()) {
                    tableD.setItems(listD2);
                    return;
                }
                ObservableList<Drinks> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<Drinks, ?>> cols = tableD.getColumns();
                for (int i = 0; i < listD2.size(); i++) {
                    for (int j = 0; j < cols.size(); j++) {
                        TableColumn<Drinks, ?> col = cols.get(j);
                        String cellValue = col.getCellData(listD2.get(i)).toString();

                        cellValue = cellValue.toLowerCase();
                        if (cellValue.contains(searchDrank.getText().toLowerCase()) && cellValue.startsWith(searchDrank.getText().toLowerCase())) {
                            tableItems.add(listD2.get(i));
                            break;

                        }
                    }
                }
                tableD.setItems(tableItems);

            }

        });
    }
}
