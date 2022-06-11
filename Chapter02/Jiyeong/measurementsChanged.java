public void measurementsChanged(){

    float temp = getTemperature();
    float humidity = getHumidity();
    float pressure = getPressure();

    //아래 부분들은 바뀔 수 있으므로 캡슐화 필요!
    currentConditionsDisplay.update(temp, humidity, pressure);
    statisticsDisplay.update(temp, humidity, pressure);
    forecastDisplay.update(temp, humidity, pressure);

}
