public interface Subject {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    //위 두 메소드들은 Observer을 인자로 받음. 각각 옵저버를 등록하거나 제거하는 역할.
    public void notifyObservers();
    //주제의 상태가 변경되었을 때 모든 옵저버에게 변경 내용을 알리려고 호출되는 메소드.
}

