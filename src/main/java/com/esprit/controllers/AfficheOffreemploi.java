package com.esprit.controllers;

import com.esprit.models.offreemploi;
import com.esprit.models.statut;
import com.esprit.services.OffreEmploiServices;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AfficheOffreemploi {
    public PieChart statChart;
    @FXML
    private Button btnRetour;
    @FXML
    private Button btnTrier;
    @FXML
    private Button btnRechercher;
    @FXML
    private TextField txtRecherche;
    @FXML
    private TableView<offreemploi> tableViewOffres;
    @FXML
    private TableColumn<offreemploi, Integer> colId;
    @FXML
    private TableColumn<offreemploi, String> colTitre;
    @FXML
    private TableColumn<offreemploi, String> colDescription;
    @FXML
    private TableColumn<offreemploi, String> colAdresse;
    @FXML
    private TableColumn<offreemploi, String> colDatePublication;
    @FXML
    private TableColumn<offreemploi, String> colStatut;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnExportPDF;

    private OffreEmploiServices offreService = new OffreEmploiServices();
    private List<offreemploi> listOffres = new ArrayList<>();

    @FXML
    public void initialize() {
        // Initialiser les colonnes du TableView
        colTitre.setCellValueFactory(cellData -> cellData.getValue().titreProperty());
        colDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        colAdresse.setCellValueFactory(cellData -> cellData.getValue().adresseProperty());
        colDatePublication.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate_publication().toString()));

        // For 'statut', we will convert it to String (i.e., DISPONIBLE or INDISPONIBLE)
        colStatut.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatut().toString()));

        // Charger les données depuis le service
        listOffres = offreService.getAllOffres();
        tableViewOffres.setItems(FXCollections.observableArrayList(listOffres));
    }
    public void setOffres(List<offreemploi> offres) {
        this.listOffres = offres;
        tableViewOffres.setItems(FXCollections.observableArrayList(offres));
    }

    @FXML
    void handleRetour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Menu Principal");
        stage.show();
    }

    @FXML
    void handleTrier(ActionEvent event) {
        listOffres.sort((o1, o2) -> o1.getTitre().compareTo(o2.getTitre()));
        tableViewOffres.setItems(FXCollections.observableArrayList(listOffres));
    }

    @FXML
    void handleRechercher(ActionEvent event) {
        String searchTerm = txtRecherche.getText().toLowerCase();
        List<offreemploi> filteredOffres = new ArrayList<>();

        if (!searchTerm.isEmpty()) {
            for (offreemploi offre : listOffres) {
                if (offre.getTitre().toLowerCase().contains(searchTerm)) {
                    filteredOffres.add(offre);
                }
            }
        } else {
            filteredOffres = listOffres;
        }

        tableViewOffres.setItems(FXCollections.observableArrayList(filteredOffres));
    }

    @FXML
    void handleSupprimer(ActionEvent event) {
        offreemploi selectedOffre = tableViewOffres.getSelectionModel().getSelectedItem();
        if (selectedOffre == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner une offre à supprimer.");
            alert.show();
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setContentText("Voulez-vous vraiment supprimer cette offre ?");
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                offreService.supprimer(selectedOffre);
                listOffres.remove(selectedOffre);
                tableViewOffres.setItems(FXCollections.observableArrayList(listOffres));
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Succès");
                success.setContentText("Offre supprimée avec succès !");
                success.show();
            }
        });
    }

    @FXML
    void handleModifier(ActionEvent event) throws IOException {
        offreemploi selectedOffre = tableViewOffres.getSelectionModel().getSelectedItem();
        if (selectedOffre == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner une offre à modifier.");
            alert.show();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifieroffreemploi.fxml"));
        Parent root = loader.load();

        ModifierOffreEmploiController controller = loader.getController();
        controller.setOffreToModify(selectedOffre);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Modifier Offre d'Emploi");

        // Rafraîchir la TableView après la fermeture de la fenêtre de modification
        stage.setOnHidden(e -> {
            listOffres = offreService.getAllOffres(); // Recharger les données depuis la base de données
            tableViewOffres.setItems(FXCollections.observableArrayList(listOffres)); // Mettre à jour la TableView
        });

        stage.show();
    }

    @FXML
    void handleExportToExcel(ActionEvent event) {
        // Créer un nouveau classeur Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Offres d'Emploi");

        // Créer une ligne pour les en-têtes
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Titre");
        headerRow.createCell(2).setCellValue("Description");
        headerRow.createCell(3).setCellValue("Date de Publication");
        headerRow.createCell(4).setCellValue("Statut");

        // Remplir les données
        ObservableList<offreemploi> offres = tableViewOffres.getItems();
        for (int i = 0; i < offres.size(); i++) {
            Row row = sheet.createRow(i + 1);
            offreemploi offre = offres.get(i);
            row.createCell(0).setCellValue(offre.getId());
            row.createCell(1).setCellValue(offre.getTitre());
            row.createCell(2).setCellValue(offre.getDescription());
            row.createCell(3).setCellValue(offre.getDate_publication().toString());
            row.createCell(4).setCellValue(offre.getStatut().toString());
        }

        // Ajuster la largeur des colonnes
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        // Sauvegarder le fichier Excel
        File file = new File("OffresEmploi.xlsx");
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
            alert.setContentText("Les données ont été exportées avec succès vers OffresEmploi.xlsx");
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

    @FXML
    void handleAjouterFavoris(ActionEvent event) {
        offreemploi selectedOffre = tableViewOffres.getSelectionModel().getSelectedItem();
        if (selectedOffre == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner une offre à ajouter aux favoris.");
            alert.show();
            return;
        }

        // Ajouter l'offre aux favoris
        offreService.ajouterFavoris(selectedOffre);

        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Succès");
        success.setContentText("Offre ajoutée aux favoris avec succès !");
        success.show();
    }

    @FXML
    void handleAfficherFavoris(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheFavoris.fxml"));
        Parent root = loader.load();

        // Passer le service au contrôleur AfficheFavorisController
        AfficheFavorisController controller = loader.getController();
        controller.setOffreService(offreService); // Assurez-vous que cette méthode existe dans AfficheFavorisController

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void handleAjouter(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutOffreemploi.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void handleExportPDF(ActionEvent event) {
        showNotImplementedAlert("Exporter en PDF");
    }

    @FXML
    void handleStatistiquesParStatut(ActionEvent event) {
        // Récupérer les données d'offres
        List<offreemploi> offres = offreService.getAllOffres();
        
        // Compter les offres par statut
        int disponibles = 0;
        int indisponibles = 0;
        
        for (offreemploi offre : offres) {
            if (offre.getStatut() == statut.DISPONIBLE) {
                disponibles++;
            } else {
                indisponibles++;
            }
        }
        
        // Créer les données pour le graphique PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Disponibles", disponibles),
            new PieChart.Data("Indisponibles", indisponibles)
        );
        
        // Mettre à jour le graphique
        statChart.setData(pieChartData);
        statChart.setTitle("Offres par statut");
        statChart.setLegendVisible(true);
        statChart.setVisible(true);
        
        // Animation pour les tranches du PieChart (optional)
        pieChartData.forEach(data -> {
            data.getNode().setOnMouseEntered(e -> {
                data.getNode().setStyle("-fx-pie-color: derive(" + data.getNode().getStyle() + ", 20%);");
            });
            data.getNode().setOnMouseExited(e -> {
                data.getNode().setStyle("");
            });
        });
    }

    private void showNotImplementedAlert(String action) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fonctionnalité non implémentée");
        alert.setHeaderText(null);
        alert.setContentText("La fonctionnalité \"" + action + "\" n'a pas encore été implémentée.");
        alert.showAndWait();
    }
}