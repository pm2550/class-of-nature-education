package com.program.server.domain;

public class sensor {
    public double temperature;
    public double humidity;

    public double getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return "sensor{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                '}';
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
