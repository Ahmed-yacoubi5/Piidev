package com.recrutement.controllers;

import com.recrutement.models.cv;
import com.recrutement.services.CvServices;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.util.Callback;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Desktop;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.collections.ObservableList;
public class AfficheCV {

    @FXML
    public Button btnRechercher;
    @FXML
    public TextField txtRecherche;
    @FXML
    public Button btnTrier;
    @FXML
    public TableView<cv> tableViewCVs;
    @FXML
    private TableColumn<cv, Integer> colId;
    @FXML
    private TableColumn<cv, String> colNom;
    @FXML
    private TableColumn<cv, String> colPrenom;
    @FXML
    private TableColumn<cv, String> colDateNaissance;
    @FXML
    private TableColumn<cv, String> colAdresse;
    @FXML
    private TableColumn<cv, String> colEmail;
    @FXML
    private TableColumn<cv, Integer> colTelephone;
    @FXML
    private TableColumn<cv, Image> colImage;  // Colonne d'image de type Image
    @FXML
    public Button btnModifier;
    @FXML
    public Button btnSupprimer;
    @FXML
    public Button btnRetour;
    @FXML
    private ImageView imageView;

    private CvServices cvService = new CvServices();
    private List<cv> listCVs = new ArrayList<>();  // Liste des CVs

    @FXML
    public void initialize() {
        // Liaison des colonnes avec les propriétés de CV
        colNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        colPrenom.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        colAdresse.setCellValueFactory(cellData -> cellData.getValue().adresseProperty());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        // Pour les colonnes avec IntegerProperty (telles que colId et colTelephone),
        // nous devons les convertir en ObservableValue<Integer>
        colTelephone.setCellValueFactory(cellData -> cellData.getValue().telephoneProperty().asObject());  // asObject() convertit IntegerProperty en ObservableValue<Integer>

        // Configurer la colonne d'image pour afficher l'image à partir du chemin
        colImage.setCellValueFactory(cellData -> {
            // On suppose que vous avez une propriété 'imagePath' de type StringProperty dans votre modèle 'cv'
            String imagePath = cellData.getValue().getImage();  // Assurez-vous que cette méthode retourne un chemin valide
            if (imagePath != null && !imagePath.isEmpty()) {
                Image image = new Image("file:" + imagePath);  // Créer une Image à partir du chemin
                return new SimpleObjectProperty<>(image);  // Retourner un SimpleObjectProperty<Image> (ObservableValue<Image>)
            } else {
                return new SimpleObjectProperty<>(null);  // Si l'image n'existe pas, retourner une valeur nulle
            }
        });

        // Configurer le rendu de l'image dans la table
        colImage.setCellFactory(new Callback<TableColumn<cv, Image>, TableCell<cv, Image>>() {
            @Override
            public TableCell<cv, Image> call(TableColumn<cv, Image> param) {
                return new TableCell<cv, Image>() {
                    private final ImageView imageView = new ImageView();

                    @Override
                    protected void updateItem(Image image, boolean empty) {
                        super.updateItem(image, empty);
                        if (!empty && image != null) {
                            imageView.setImage(image);
                            imageView.setFitWidth(70);  // Ajuster la largeur
                            imageView.setFitHeight(70); // Ajuster la hauteur
                            setGraphic(imageView);
                        } else {
                            setGraphic(null);  // Si l'image est vide ou nulle, ne rien afficher
                        }
                    }
                };
            }
        });

        // Charger les données depuis la base de données
        listCVs = cvService.getAllCVs();
        tableViewCVs.setItems(FXCollections.observableArrayList(listCVs));
    }

    // Setter pour les CVs si vous voulez les passer directement depuis un autre contrôleur
    public void setCvs(List<cv> cvs) {
        this.listCVs = cvs;
        tableViewCVs.setItems(FXCollections.observableArrayList(cvs));
    }

    // Action pour le bouton retour
    @FXML
    void handleRetour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Action pour le bouton modifier
    @FXML
    void handleModifier(ActionEvent event) throws IOException {
        cv selectedCV = tableViewCVs.getSelectionModel().getSelectedItem();
        if (selectedCV == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner un CV à modifier.");
            alert.show();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCV.fxml"));
        Parent root = loader.load();

        ModifierCVController controller = loader.getController();
        controller.setCV(selectedCV);
        controller.setTableViewCV(tableViewCVs);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Modifier CV");
        stage.show();
    }

    // Action pour le bouton trier
    @FXML
    void handleTrier(ActionEvent event) {
        // Trier les CVs par nom
        listCVs.sort((cv1, cv2) -> cv1.getNom().compareTo(cv2.getNom()));
        tableViewCVs.setItems(FXCollections.observableArrayList(listCVs));
    }

    // Action pour le bouton rechercher
    @FXML
    void handleRechercher(ActionEvent event) {
        String searchTerm = txtRecherche.getText().toLowerCase();
        List<cv> filteredCVs = new ArrayList<>();

        if (!searchTerm.isEmpty()) {
            for (cv cv : listCVs) {
                if (cv.getNom().toLowerCase().contains(searchTerm)) {
                    filteredCVs.add(cv);
                }
            }
        } else {
            filteredCVs = listCVs;
        }

        tableViewCVs.setItems(FXCollections.observableArrayList(filteredCVs));
    }
    @FXML
    private void handleExportToExcel(ActionEvent event) {
        // Créer un nouveau classeur Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("CVs");

        // Créer une ligne pour les en-têtes
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nom");
        headerRow.createCell(2).setCellValue("Prénom");
        headerRow.createCell(3).setCellValue("Date de Naissance");
        headerRow.createCell(4).setCellValue("Adresse");
        headerRow.createCell(5).setCellValue("Email");
        headerRow.createCell(6).setCellValue("Téléphone");
        headerRow.createCell(7).setCellValue("Image");

        // Remplir les données
        ObservableList<cv> cvs = tableViewCVs.getItems();
        for (int i = 0; i < cvs.size(); i++) {
            Row row = sheet.createRow(i + 1);
            cv cv = cvs.get(i);
            row.createCell(0).setCellValue(cv.getIdCv());
            row.createCell(1).setCellValue(cv.getNom());
            row.createCell(2).setCellValue(cv.getPrenom());
            row.createCell(3).setCellValue(cv.getDateDeNaissance().toString()); // Assurez-vous que cette méthode existe
            row.createCell(4).setCellValue(cv.getAdresse());
            row.createCell(5).setCellValue(cv.getEmail());
            row.createCell(6).setCellValue(cv.getTelephone());
            row.createCell(7).setCellValue(cv.getImage()); // Chemin de l'image
        }

        // Ajuster la largeur des colonnes
        for (int i = 0; i < 8; i++) {
            sheet.autoSizeColumn(i);
        }

        // Sauvegarder le fichier Excel
        File file = new File("CVs.xlsx");
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);

            // Ouvrir le fichier Excel après l'exportation
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (file.exists()) {
                    desktop.open(file); // Ouvrir le fichier avec l'application par défaut
                }
            }

            // Afficher un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Export réussi");
            alert.setHeaderText(null);
            alert.setContentText("Les données ont été exportées avec succès vers CVs.xlsx");
            alert.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de l'exportation : " + e.getMessage());
            alert.show();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // Action pour le bouton supprimer
    @FXML
    void handleSupprimer(ActionEvent event) {
        cv selectedCV = tableViewCVs.getSelectionModel().getSelectedItem();
        if (selectedCV == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner un CV à supprimer.");
            alert.show();
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setContentText("Voulez-vous vraiment supprimer ce CV ?");
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                cvService.supprimer(selectedCV);
                listCVs.remove(selectedCV);
                tableViewCVs.setItems(FXCollections.observableArrayList(listCVs));
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Succès");
                success.setContentText("CV supprimé avec succès !");
                success.show();
            }
        });
    }


    // Action pour le bouton ajouter
    @FXML
    void handleAjouter(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutCV.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
