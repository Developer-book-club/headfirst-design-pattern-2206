public class WeatherData implements Subject{
    //인스턴스 변수 선언
    private List<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData(){
        observers = new ArrayList<Observer>();
    }

    //Subject 인터페이스를 구현하는 부분
    public void registerObserver(Observer o){//옵저버가 둥록 요청 시 목록 맨 뒤에 추가하기만 하면 됨
        observers.add(o);
    }

    public void removeObserver(Observer o){//옵저버가 탈퇴 요청 시 목록에서 빼기만 하면 됨
        observers.remove(o);
    }

    public void notifyObserver(){
        for(Observer observer : observers){
            observer.update();
        }
    }
    /* 중요한 부분 - 모든 옵저버에게 상태 변화를 알려줌*/
    // 모두 Observer 인터페이스를 구현하는 update() 메소드가 있는 객체들이므로 상태 변화를 쉽게 알려줄 수 있음.

    public void measurementsChanged(){
        //가상 스테이션으로부터 갱신된 측정 값을 받으면 옵저버들에게 알림.
        notifyObserver();
    }

    public void setMeasurements(float temperature, float humidity, float pressure){
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }
}
