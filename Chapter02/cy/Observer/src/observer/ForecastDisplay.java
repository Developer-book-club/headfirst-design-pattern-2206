package observer;

import subject.WeatherData;

import java.util.Arrays;

public class ForecastDisplay implements Observer, DisplayElement {
    private WeatherData weatherData;
    public ForecastDisplay(WeatherData weatherData){
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }
    @Override
    public void display() {
        String[] results = {"날씨가 좋아지고 있다", "쌀쌀하며 비가 올 것 같다", "지금과 비슷할 것 같다"};
        int randomIndex = (int) Math.floor(Math.random()*3);
        System.out.println("기상 예보: "+Arrays.asList(results).get(randomIndex));
    }
    @Override
    public void update() {
        display();
    }
}
