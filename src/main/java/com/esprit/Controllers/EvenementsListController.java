package com.esprit.Controllers;

import com.esprit.models.Evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import com.esprit.PDFExporter;
import com.esprit.services.IService;
import com.esprit.services.ServiceEvenement;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tray.animations.AnimationType;
import tray.notification.NotificationType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class EvenementsListController implements Initializable {

    @FXML
    private GridPane evenementsListContainer;

    @FXML
    private Pane content_area;

    @FXML
    private TextField evenementsearchInput;

    @FXML
    private Button stockBtn;


    @FXML
    private HBox qrCodeImgModel;


    @FXML
    private ComboBox<String> couponCombobox;
    private int sortValue = -1;
    private String selectedOption = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        qrCodeImgModel.setVisible(false);

        this.setEvenementGridPaneList();
    }


    @FXML
    void sort__Byevent(MouseEvent event) {
        Evenement.setSearchValue(null);
        if (!stockBtn.getStyleClass().contains("sort__stockBtn-active")) {
            stockBtn.getStyleClass().add("sort__stockBtn-active");
            sortValue = 1;
        } else {
            stockBtn.getStyleClass().remove("sort__stockBtn-active");
            sortValue = -1;
        }
        GridPane productsListContainer = (GridPane) content_area.lookup("#evenementsListContainer");
        productsListContainer.getChildren().clear();
        this.setEvenementGridPaneList();
    }

    @FXML
    void searchEvenement(KeyEvent event) throws IOException {
        Evenement.setSearchValue(((TextField) event.getSource()).getText());

        GridPane evenementsListContainer = (GridPane) content_area.lookup("#evenementsListContainer");
        evenementsListContainer.getChildren().clear();
        this.setEvenementGridPaneList();
    }
    @FXML
    void excelBtn(MouseEvent event) throws IOException, SQLException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Evenement");
        IService serviceEvenement = new ServiceEvenement();
        List<Evenement> evenementList = serviceEvenement.afficher();
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Nom");
        headerRow.createCell(1).setCellValue("Déscription");
        headerRow.createCell(2).setCellValue("Lieu");
        headerRow.createCell(3).setCellValue("Prix");
        int rowNum = 1;
        for (Evenement evenement : evenementList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(evenement.getNom());
            row.createCell(1).setCellValue(evenement.getDescription());
            row.createCell(2).setCellValue(evenement.getLieu());
            row.createCell(3).setCellValue(evenement.getPrix());
        }
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier Excel");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers Excel", "*.xlsx"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try (FileOutputStream outputStream = new FileOutputStream(selectedFile)) {
                workbook.write(outputStream);
            }
        }
    }

    @FXML
    private void open_addEvenement(MouseEvent event) throws IOException {
        Evenement.actionTest = 0;
        Parent fxml = FXMLLoader.load(getClass().getResource("/FXML/AddEvenement.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);
    }




    private void setEvenementGridPaneList() {
        IService evenementService = new ServiceEvenement();

        List<Evenement> evenements = null;
        if (Evenement.getSearchValue() == null) {
            if (sortValue == 1) {
                evenements = evenementService.sortEvent(1);
            } else {
                try {
                    evenements = evenementService.afficher();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        } else {
            evenements = ServiceEvenement.searchEvenement(Evenement.getSearchValue());
        }
        int column = 0;
        int row = 1;
        try {
            for (Evenement evenement : evenements) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/OneEvenementListCard.fxml"));
                HBox oneEvenementCard = fxmlLoader.load();
                OneEvenementListCardController evenementCardController = fxmlLoader.getController();
                evenementCardController.setEvenementData(evenement);
                if (column == 1) {
                    column = 0;
                    ++row;
                }
                evenementsListContainer.add(oneEvenementCard, column++, row);
                GridPane.setMargin(oneEvenementCard, new Insets(0, 10, 25, 10));
                oneEvenementCard.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.09), 25, 0.1, 0, 0);");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void close_QrCodeModel(MouseEvent event) {
        qrCodeImgModel.setVisible(false);
    }



}
