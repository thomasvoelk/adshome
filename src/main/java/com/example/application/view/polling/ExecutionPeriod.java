package com.example.application.view.polling;

public class ExecutionPeriod {

    long from;
    long to;

    public ExecutionPeriod(long from, long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "ExecutionPeriod{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
