public interface Observer {
    public void update();
    //기상 정보가 변경되었을 때 옵저버에게 전달되는 상태값들.
    //이 인터페이스는 모든 옵저버 클래스에서 구현되어야 함. 그래서 모든 옵저버들은 update() 메소드를 구현해야 한다.
}