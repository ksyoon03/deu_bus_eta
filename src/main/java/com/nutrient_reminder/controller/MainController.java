package com.nutrient_reminder.controller;

import com.nutrient_reminder.service.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Label userNameLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Button mainTabButton;

    @FXML
    private Button recommendTabButton;

    @FXML
    private Button addButton;

    @FXML
    private VBox alarmListContainer;

    @FXML
    public void initialize() {
        // 사용자 이름 설정
        String currentId = UserSession.getUserId();
        if (currentId != null) {
            userNameLabel.setText("'" + currentId + "' 님");
        }

        System.out.println("메인 화면이 초기화되었습니다.");

        // 테스트 용
        addAlarmToUI("11월 06일", "08:00", "섭취중인 약", "08:00");
        addAlarmToUI("11월 07일", "13:00", "비타민 C", "13:00");
    }

    // 알림박스 메소드
    public void addAlarmToUI(String dateText, String timeText, String pillName, String subTime) {
        VBox alarmBox = new VBox(10); // spacing 생성자 활용
        alarmBox.setStyle("-fx-background-color: #EAF6FA; -fx-background-radius: 15;");
        alarmBox.setPadding(new Insets(15, 20, 15, 20));

        Label dateLabel = new Label(dateText);
        dateLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #555555; -fx-font-size: 14px;");

        HBox contentBox = new HBox(50); // spacing 생성자 활용
        contentBox.setAlignment(Pos.CENTER);

        Label mainTimeLabel = new Label(timeText);
        mainTimeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        Label pillLabel = new Label(pillName);
        pillLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #555555;");

        contentBox.getChildren().addAll(mainTimeLabel, pillLabel);

        Label bottomBar = new Label(subTime);
        bottomBar.setMaxWidth(Double.MAX_VALUE);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(5, 0, 5, 0));
        bottomBar.setStyle("-fx-background-color: #D0E8F2; -fx-background-radius: 10; -fx-text-fill: #667880; -fx-font-weight: bold; -fx-font-style: italic;");

        alarmBox.getChildren().addAll(dateLabel, contentBox, bottomBar);

        if (alarmListContainer != null) {
            alarmListContainer.getChildren().add(alarmBox);
        }
    }

    // 로그아웃
    @FXML
    private void handleLogout() {
        System.out.println("로그아웃 버튼 클릭됨");
        try {
            UserSession.clear();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/nutrient_reminder/view/login-view.fxml")
            );
            Parent root = loader.load();

            // 수정 - 현재 Scene의 내용(Root)만 교체
            Scene currentScene = userNameLabel.getScene();
            currentScene.setRoot(root);

            Stage stage = (Stage) currentScene.getWindow();
            stage.setTitle("로그인"); // 타이틀 설정 추가
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 영양제 추천 탭 이동
    @FXML
    private void handleRecommendTab() {
        System.out.println("영양제 추천 탭 클릭됨");
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/nutrient_reminder/view/nutrient-check.fxml")
            );
            Parent root = loader.load();

            Scene currentScene = recommendTabButton.getScene();
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("영양제 추천 화면으로 이동 실패");
        }
    }

    // 추가 팝업
    @FXML
    private void handleAdd() {
        System.out.println("추가(+) 버튼 클릭됨");
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/nutrient_reminder/view/alarmAddPopup.fxml")
            );
            Parent root = loader.load();
            Stage popupStage = new Stage();
            popupStage.initOwner(userNameLabel.getScene().getWindow());
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.setTitle("알람 추가");
            popupStage.setScene(new Scene(root));
            popupStage.setResizable(false);
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 마우스 들어오면 ( 작아지기 )
    @FXML
    private void onHoverEnter(MouseEvent event) {
        Node node = (Node) event.getSource();
        node.setScaleX(0.98);
        node.setScaleY(0.98);
    }

    // 마우스 나가면 ( 원래대로 )
    @FXML
    private void onHoverExit(MouseEvent event) {
        Node node = (Node) event.getSource();
        node.setScaleX(1.0);
        node.setScaleY(1.0);
    }
}