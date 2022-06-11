public class CurrentConditionsDisplay implements Observer, DisplayElement{
    // Observer - WeatherData 객체로부터 변경 사항을 받으려면 구현해야 함.
    // DisplayElement - API 구조상 모든 디스플레이 항목에서 DisplayElement를 구현하기로 했기에 이 인터페이스를 구현함.

    private float temperature;
    private float humidity;
    private WeatherData weatherData;

    public CurrentConditionsDisplay(WeatherData weatherData){
        //생성자에 weatherData라는 주제가 전달되며, 그 객체를 써서 디스플레이를 옵저버에 등록함.
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    public void update(){
        this.temperature = weatherData.getTemperature();
        this.humidity = weatherData.getHumidity();
        //Subjcet의 게터 메소드 사용
        display();
        //update가 호출되면 온도와 습도를 저장하고 display()를 호출
    }

    public void display(){
        System.out.println("현재 상태: 온도" + temperature + 'F, 습도' + humidity + "%");
        //display() 메소드는 가장 최근의 온도와 습도를 출력
    }
}
