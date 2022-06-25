# 5. 싱글턴 패턴

스레드 풀, 캐시, 대화상자, 사용자 설정, 레지스트리 설정을 처리하는 객체, 로그 기록용 객체, 디바이스 드라이버 등 설계상 유일해야 하는 특정 클래스에게 객체 인스턴스 하나만 만들어지도록 하는 패턴

> 싱글턴 패턴은 클래스 인스턴스를 하나만 만들고, 그 인스턴스로의 전역 접근을 제공한다

<br>

## 1) 고전적 싱글턴 구현

```java
public class Singleton {
    private static Singleton uniqueInstance;

    private Singleton() {}

    public static Singleton getInstance() {
        if(uniqueInstance == null){
            //게으른 인스턴스 생성(lazy instantiation)
            //인스턴스가 필요하기 전까지는 인스턴스를 생성하지 않는다
            uniqueInstance = new Singleton();
        }
        return uniqueInstance;
    }
}
```


-> 멀티 스레드 환경에서 하나 이상의 객체가 생성될 가능성이 있다!

<br>

💡 synchronized 키워드를 추가함으로써 멀티스레딩 문제를 해결해 보자

```java
public class Singleton {
    private static Singleton uniqueInstance;

    private Singleton() {}

    public static synchronized Singleton getInstance() {
        if(uniqueInstance == null){
            //게으른 인스턴스 생성(lazy instantiation)
            //인스턴스가 필요하기 전까지는 인스턴스를 생성하지 않는다
            uniqueInstance = new Singleton();
        }
        return uniqueInstance;
  
```

-> 동기화는 메소드가 시작되는 때만 필요한데, uniqueInstance에 Singleton 인스턴스가 대입된 후에도 동기화 상태를 유지하면 불필요한 오버헤드만 증가시킬 뿐! 

<br>

💡 방법 1. 속도 문제가 중요하지 않다면 그냥 두기 : 성능 100배 저하

💡 방법 2. 인스턴스를 처음부터 만들어 두자

```java
public class Singleton {
    private static Singleton uniqueInstance = new Singleton();

    private Singleton(){}

    public static Singleton getInstance() {
        return uniqueInstance;
    }
}
```

💡 방법 3. DCL(Double-Checked Locking)으로 getInstance()에서의 동기화 부분 줄이기

인스턴스가 생성되어 있는지 확인한 다음 생성되어 있지 않을 때만 동기화할 수 있다.

```java
public class Singleton {
    //volatile 키워드를 사용하면 멀티스레딩을 쓰더라도 uniqueInstance 변수가 Singleton 인스턴스로 초기화되는 과정이 올바르게 진행된다
    //자바 1.4 이전 버전 사용 불가
    private volatile static Singleton uniqueInstance;
    private Singleton() {}

    public static synchronized Singleton getInstance() {
        if(uniqueInstance == null){
            synchronized (Singleton.class){
                if(uniqueInstance == null){
                    uniqueInstance = new Singleton();
                }
            }
        }
        return uniqueInstance;
}

```

## 2) enum으로 싱글턴 생성

고전적 싱글턴보다 좋은 방식 -> 싱글턴이 필요할 때면 enum(열거 타입)을 쓰자. 

열거 타입을 이용하면 선언한 상수 외 다른 객체는 존재하지 않음을 자바가 보장해주며 동기화 문제, 클래스 로딩 문제, 리플렉션, 직렬화와 역직렬화 문제 등을 해결할 수 있다.

* 클래스 로더마다 다른 네임스페이스를 정의하기에 클래스 로더가 2개 이상이면 각 클래스 로더마다 클래스를 로딩할 수 있다. -> 여러 개의 인스턴스 생성
* 클래스는 직렬화(implements Serializable)를 사용하는 순간 더이상 싱글턴이 아니다.

* 직렬화: 자바가 객체를 바이트 스트림으로 인코딩(직렬화)하고, 바이트 스트림으로부터 다시 객체를 재구성(역직렬화)하는 매커니즘

<br>

```java
public enum Singleton {
    UNIQUE_INSTANCE;
}

public class SingletonClient {
    public static void main(String[] args){
        Singleton singleton = Singleton.UNIQUE_INSTANCE;
    }
}
```


