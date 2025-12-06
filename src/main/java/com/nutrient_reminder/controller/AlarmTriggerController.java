package com.nutrient_reminder.controller;

import com.nutrient_reminder.service.AlarmSchedulerService;
import com.nutrient_reminder.model.Nutrient;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import java.util.List;

public class AlarmTriggerController {

    @FXML private Label timeLabel;
    @FXML private VBox pillListContainer;
    @FXML private Button offButton;
    @FXML private Button snoozeButton;

    private final AlarmSchedulerService service = AlarmSchedulerService.getInstance();
    private List<Nutrient> currentAlarmGroup;

    @FXML
    public void initialize() {
        offButton.setOnMouseEntered(this::onButtonHoverEnter);
        offButton.setOnMouseExited(this::onButtonHoverExit);
        offButton.setOnMousePressed(this::onButtonPress);
        offButton.setOnMouseReleased(this::onButtonRelease);

        snoozeButton.setOnMouseEntered(this::onButtonHoverEnter);
        snoozeButton.setOnMouseExited(this::onButtonHoverExit);
        snoozeButton.setOnMousePressed(this::onButtonPress);
        snoozeButton.setOnMouseReleased(this::onButtonRelease);
    }

    // 알람 그룹 리스트를 받아와서 UI에 표시
    public void setAlarmGroupInfo(List<Nutrient> alarmGroup) {
        if (alarmGroup.isEmpty()) return;

        this.currentAlarmGroup = alarmGroup;

        // 그룹의 시간 (첫 번째 알람의 시간으로 대표)
        timeLabel.setText(alarmGroup.get(0).getTime());

        // VBox에 모든 약 이름 동적으로 추가
        pillListContainer.getChildren().clear();
        for (Nutrient alarm : alarmGroup) {
            Label nameLabel = new Label(alarm.getName());
            nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333333;");
            pillListContainer.getChildren().add(nameLabel);
        }

        // 버튼 텍스트 변경
        offButton.setText("모두 끄기 (" + alarmGroup.size() + "개)");
        snoozeButton.setText("모두 30분 뒤");
    }

    @FXML
    private void handleOff() {
        if (currentAlarmGroup == null) return;

        // 그룹 내 모든 알람의 상태를 COMPLETED로 변경 요청
        for (Nutrient alarm : currentAlarmGroup) {
            service.updateAlarmStatus(alarm.getId(), "COMPLETED");
        }
        System.out.println("알람 그룹 모두 끄기 요청 완료. 개수: " + currentAlarmGroup.size());
        closePopup();
    }

    @FXML
    private void handleSnooze() {
        if (currentAlarmGroup == null) return;

        //그룹 내 모든 알람에 대해 SNOOZED 요청
        for (Nutrient alarm : currentAlarmGroup) {
            service.updateAlarmStatus(alarm.getId(), "SNOOZED");
        }
        System.out.println("알람 그룹 모두 스누즈 요청 완료. 개수: " + currentAlarmGroup.size());
        closePopup();
    }

    private void closePopup() {
        // timeLabel은 FXML에서 VBox의 자식으로 정의되어 있으므로 안전하게 Stage를 찾을 수 있습니다.
        Stage stage = (Stage) timeLabel.getScene().getWindow();
        stage.close();
    }


    // 마우스 이벤트 핸들러 (기존 유지)
    @FXML
    private void onButtonHoverEnter(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: #567889; -fx-background-radius: 5; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 18.0px;");
        button.setScaleX(1.02);
        button.setScaleY(1.02);
    }

    @FXML
    private void onButtonHoverExit(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: #D0E8F2; -fx-background-radius: 5; -fx-text-fill: #567889; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 18.0px;");
        button.setScaleX(1.0);
        button.setScaleY(1.0);
    }

    @FXML
    private void onButtonPress(MouseEvent event) {
        Node node = (Node) event.getSource();
        node.setScaleX(0.98);
        node.setScaleY(0.98);
    }

    @FXML
    private void onButtonRelease(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: #D0E8F2; -fx-background-radius: 5; -fx-text-fill: #567889; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 18.0px;");
        button.setScaleX(1.0);
        button.setScaleY(1.0);
    }
}