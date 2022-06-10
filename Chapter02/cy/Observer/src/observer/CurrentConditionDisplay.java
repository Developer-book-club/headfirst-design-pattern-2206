package observer;

import observer.DisplayElement;
import observer.Observer;
import subject.WeatherData;

import java.text.MessageFormat;

public class CurrentConditionDisplay implements Observer, DisplayElement {
    private float temperature;
    private float humidity;
    private WeatherData weatherData;
    public CurrentConditionDisplay(WeatherData weatherData){
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }
    @Override
    public void display() {
        System.out.println(MessageFormat.format("현재 상태: 온도 {0} F, 습도 {1} %",temperature, humidity));
    }
    @Override
    public void update() {
        this.temperature = weatherData.getTemperature();
        this.humidity = weatherData.getHumidity();
        display();
    }
}
