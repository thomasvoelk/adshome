package com.example.application.view.polling;

public class DetectionRequest {

    String unitName;
    String scenarioName;
    ExecutionPeriod period;

    public DetectionRequest(String unitName, String scenarioName, ExecutionPeriod period) {
        this.unitName = unitName;
        this.scenarioName = scenarioName;
        this.period = period;
    }

    @Override
    public String toString() {
        return "DetectionRequest{" +
                "unitName='" + unitName + '\'' +
                ", scenarioName='" + scenarioName + '\'' +
                ", period=" + period +
                '}';
    }
}
