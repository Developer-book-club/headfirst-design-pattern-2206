package observer;

import subject.WeatherData;

import java.text.MessageFormat;

public class StatisticsDisplay implements Observer, DisplayElement {
    private float averageTemperature;
    private float minTemperature;
    private float maxTemperature;
    private WeatherData weatherData;
    public StatisticsDisplay(WeatherData weatherData){
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }
    @Override
    public void display() {
        System.out.println(MessageFormat.format("평균/최고/최저 온도 = {0}/{1}/{2}",averageTemperature, maxTemperature, minTemperature));
    }
    @Override
    public void update() {
        this.averageTemperature = weatherData.getTemperature();
        this.maxTemperature = weatherData.getTemperature()+1;
        this.minTemperature = weatherData.getTemperature()-1;
        display();
    }
}
