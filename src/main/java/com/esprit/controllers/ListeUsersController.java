package com.esprit.controllers;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.esprit.models.User;
import com.esprit.utils.DataBaseConnection ;
import com.esprit.services.ServiceUser;
 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ListeUsersController implements Initializable {

    private Connection connection = DataBaseConnection.getInstance().getConnection();
    private ServiceUser userService = new ServiceUser();

    @FXML
    private Button btn1;
    @FXML
    private TableColumn<User, Integer> id;

    @FXML
    private TableColumn<User, String> firstName;
    @FXML
    private TableColumn<User, String> lastName;
    boolean ok;
    @FXML
    private TableColumn<User, String> email;
    @FXML
    private TableColumn<User, String> status;
    @FXML
    private TableColumn<User, String> phoneNumber;
   

    @FXML
    private TableView<User> users;
 
   
 @FXML
 private TextField filtre ;
    @FXML
    private Button btn11;

    
    ObservableList<User> listeB = FXCollections.observableArrayList();
    @FXML
    public void show(){
        refreshTable();
    }

    private void refreshTable() {
        // Clear existing items
        listeB.clear();
        
        // Get all users from service
        try {
            ServiceUser service = new ServiceUser();
            listeB.addAll(service.afficherTous());
            
            // Update table
            users.setItems(listeB);
            users.refresh();
            
            if (listeB.isEmpty()) {
                System.out.println("No users found in the database");
            } else {
                System.out.println("Loaded " + listeB.size() + " users");
            }
        } catch (Exception e) {
            System.err.println("Error loading users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSearch(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String searchText = filtre.getText().trim();
            if (searchText.isEmpty()) {
                users.setItems(listeB);
            } else {
                ObservableList<User> filteredList = FXCollections.observableArrayList();
                boolean productFound = false;
                for (User b : listeB) {
                    // search for name or description
                    if ((b.getFirstName().toLowerCase().contains(searchText.toLowerCase())) 
                            || (b.getLastName().toLowerCase().contains(searchText.toLowerCase()))) {
                        filteredList.add(b);
                        productFound = true;
                    }
                }
                if (!productFound) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle(" Article non trouv�");
                    alert.setHeaderText("Aucun Article ne correspond � votre recherche");
                    alert.setContentText("Veuillez essayer une autre recherche.");
                    alert.showAndWait();
                }
                users.setItems(filteredList);
            }
        }
    }

    @FXML
    void supp(ActionEvent event) {
    	  User selectedLN =  users.getSelectionModel().getSelectedItem();
          if (selectedLN == null) {
         // Afficher un message d'erreur
         Alert alert = new Alert(AlertType.ERROR);
         alert.setTitle("Erreur");
         alert.setHeaderText("Impossible de supprimer la User ");
         alert.setContentText("Veuillez sélectionner une User à supprimer !");
         alert.showAndWait();}
          else {
        	
        	  Alert confirmation = new Alert(AlertType.CONFIRMATION, "�tes-vous s�r de vouloir supprimer cet utilisateur ?", ButtonType.YES, ButtonType.NO);
        	    confirmation.setHeaderText(null);
        	    confirmation.showAndWait().ifPresent(response -> {
        	        if (response == ButtonType.YES) {
        	            // Si l'utilisateur confirme, appeler la fonction de suppression
        	        	  ServiceUser sc =new ServiceUser();
        	        	  sc.supprimer(selectedLN.getId());

        	            // Afficher une alerte de r�ussite apr�s la suppression
        	            Alert success = new Alert(AlertType.INFORMATION, "L'utilisateur a �t� supprim� avec succ�s !");
        	            success.setHeaderText(null);
        	            success.showAndWait();
        	            show();
        	            users.refresh();
        	        }
        	    });
          }

    }
    @FXML
    public void Acceuil(ActionEvent event) {
        try {
            // Fermer la fenêtre actuelle
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            // Charger AcceuilAdmin.fxml avec le bon chemin
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo4/AcceuilAdmin.fxml"));
            Parent root = loader.load();

            // Afficher la nouvelle scène
            Scene scene = new Scene(root);
            Stage stage2 = new Stage();
            stage2.setScene(scene);
            stage2.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void bloq(ActionEvent event) {
        User selectedLN = users.getSelectionModel().getSelectedItem();
        if (selectedLN == null) {
            // Afficher un message d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de bloquer l'utilisateur");
            alert.setContentText("Veuillez s�lectionner un utilisateur � bloquer !");
            alert.showAndWait();
        } else {
            Alert confirmation = new Alert(AlertType.CONFIRMATION, "�tes-vous s�r de vouloir bloquer cet utilisateur ?", ButtonType.YES, ButtonType.NO);
            confirmation.setHeaderText(null);
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    // Si l'utilisateur confirme, appeler la fonction de blocage
                    ServiceUser sc = new ServiceUser();
                    sc.bloquer(selectedLN.getId());

                    // Afficher une alerte de r�ussite apr�s le blocage
                    Alert success = new Alert(AlertType.INFORMATION, "L'utilisateur a �t� bloqu� avec succ�s !");
                    success.setHeaderText(null);
                    success.showAndWait();
 users.refresh();
                								}
                					});
       
        }
             
          
    }


    @FXML
    void Modifier(ActionEvent event) {
     
     User selectedUser = users.getSelectionModel().getSelectedItem();
if (selectedUser == null) {
    // Afficher un message d'erreur
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Erreur");
    alert.setHeaderText("Impossible de modifier");
    alert.setContentText("Veuillez s�lectionner un utilisateur pour modifier !");
    alert.showAndWait();
} else {
    // Show an input dialog to get the new user details.
    Dialog<User> dialog = new Dialog<>();
    dialog.setTitle("Modifier un utilisateur");
    dialog.setHeaderText("Modifier les champs");

    // Set the default value of the input fields to the current user details.
    TextField firstNameField = new TextField(selectedUser.getFirstName());
    TextField lastNameField = new TextField(selectedUser.getLastName());
    TextField phoneNumberField = new TextField(selectedUser.getPhoneNumber());

    // Add the input fields to the dialog pane.
    GridPane grid = new GridPane();
    grid.add(new Label("Nom:"), 1, 1);
    grid.add(firstNameField, 2, 1);
    grid.add(new Label("Nom de famille:"), 1, 2);
    grid.add(lastNameField, 2, 2);
    grid.add(new Label("Num�ro de t�l�phone:"), 1, 3);
    grid.add(phoneNumberField, 2, 3);
    dialog.getDialogPane().setContent(grid);

    // Add buttons to the dialog pane.
    ButtonType modifierButtonType = new ButtonType("Modifier", ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(modifierButtonType, ButtonType.CANCEL);

    // Convert the result to a user object when the modify button is clicked.
    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == modifierButtonType) {
            return new User(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    phoneNumberField.getText()
            );
        }
        return null;
    });

    Optional<User> result = dialog.showAndWait();
    if (result.isPresent()) {
        // Update the selected user with the new values.
        selectedUser.setFirstName(result.get().getFirstName());
        selectedUser.setLastName(result.get().getLastName());
        selectedUser.setPhoneNumber(result.get().getPhoneNumber());

        // Call the service method to update the user in the database.
        ServiceUser bs = new ServiceUser();
        bs.Update(selectedUser, selectedUser.getId());

        // Show a confirmation alert.
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Succ�s");
        alert.setHeaderText("Utilisateur a �t� modifi� avec succ�s");
        alert.setContentText("Les modifications ont �t� enregistr�es.");
        alert.showAndWait();
    }

}
users.refresh();
 
    }

    @FXML
    void desable(ActionEvent event) {
     
    	    	  User selectedLN =  users.getSelectionModel().getSelectedItem();
    	          if (selectedLN == null) {
    	         // Afficher un message d'erreur
    	         Alert alert = new Alert(AlertType.ERROR);
    	         alert.setTitle("Erreur");
    	         alert.setHeaderText("Impossible de desable la User ");
    	         alert.setContentText("Veuillez sélectionner une User à desactiver !");
    	         alert.showAndWait();}
    	          else {
    	        	
    	        	  Alert confirmation = new Alert(AlertType.CONFIRMATION, "�tes-vous s�r de vouloir desactiver cet utilisateur ?", ButtonType.YES, ButtonType.NO);
    	        	    confirmation.setHeaderText(null);
    	        	    confirmation.showAndWait().ifPresent(response -> {
    	        	        if (response == ButtonType.YES) {
    	        	            // Si l'utilisateur confirme, appeler la fonction de suppression
    	        	        	  ServiceUser sc =new ServiceUser();
    	        	        	  sc.modifier(selectedLN, selectedLN.getId());

    	        	            // Afficher une alerte de r�ussite apr�s la suppression
    	        	            Alert success = new Alert(AlertType.INFORMATION, "L'utilisateur a �t� bloquuer avec succ�s !");
    	        	            success.setHeaderText(null);
    	        	            success.showAndWait();
    	        	            show();
    	        	            users.refresh();    	        	        }
    	        	    });
    	          }
    }
 
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize table columns
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        // Load data
        refreshTable();
        
        users.getStyleClass().add("my-tableview");
        users.setRowFactory(tv -> new TableRow<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    pseudoClassStateChanged(BLOQUE_PSEUDO_CLASS, false);
                    getStyleClass().remove("bloque"); // remove bloque style class
                } else {
                    try {
                        PreparedStatement ps = connection.prepareStatement("SELECT bloque FROM user WHERE id = ?");
                        ps.setInt(1, item.getId());
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            int x = rs.getInt("bloque");
                            if (x == 1) {
                                pseudoClassStateChanged(BLOQUE_PSEUDO_CLASS, true);
                                getStyleClass().add("bloque"); // add bloque style class
                            } else {
                                pseudoClassStateChanged(BLOQUE_PSEUDO_CLASS, false);
                                getStyleClass().remove("bloque"); // remove bloque style class
                            }
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        show();
    }
    private static final PseudoClass BLOQUE_PSEUDO_CLASS = PseudoClass.getPseudoClass("bloque");

}

