package com.nutrient_reminder.model;

import java.util.List;
import java.util.UUID;

public class Alarm {
    private String id;              // 알람 고유 ID (수정/삭제용)
    private String userId;          // 누구의 알람인지
    private String nutrientName;    // 영양제 이름
    private String time;            // 시간 (예: "08:30")
    private List<String> days;      // 요일 (예: ["월", "수", "금"])
    private boolean isTaken;        // 오늘 먹었는지 여부
    private String lastTakenDate;   // 마지막으로 먹은 날짜 (YYYY-MM-DD)

    // 기본 생성자 (Jackson 라이브러리가 JSON 읽을 때 필요함)
    public Alarm() {}

    // 우리가 쓸 생성자
    public Alarm(String userId, String nutrientName, String time, List<String> days) {
        this.id = UUID.randomUUID().toString(); // 랜덤한 고유 ID 자동 생성
        this.userId = userId;
        this.nutrientName = nutrientName;
        this.time = time;
        this.days = days;
        this.isTaken = false;
        this.lastTakenDate = "";
    }

    // Getter & Setter (필수)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getNutrientName() { return nutrientName; }
    public void setNutrientName(String nutrientName) { this.nutrientName = nutrientName; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public List<String> getDays() { return days; }
    public void setDays(List<String> days) { this.days = days; }
    public boolean isTaken() { return isTaken; }
    public void setTaken(boolean taken) { isTaken = taken; }
    public String getLastTakenDate() { return lastTakenDate; }
    public void setLastTakenDate(String lastTakenDate) { this.lastTakenDate = lastTakenDate; }
}