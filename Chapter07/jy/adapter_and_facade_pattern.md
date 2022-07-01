## Adapter Pattern
- 서브시스템에 있는 일련의 인터페이스를 통합 인터페이스로 묶어줌
- 고수준 인터페이스도 정의하므로 서브시스템을 더 편리하게 사용 가능
- 특정 클래스 인터페이스를 클라이언트에서 요구하는 다른 인터페이스로 변환
- 하나의 인터페이스를 다른 인터페이스로 변환하는 용도로 사용
- 인터페이스가 호환되지 않아서 같이 쓸 수 없었던 클래스 사용 가능

![](https://velog.velcdn.com/images/jiyeong/post/a5f9f3e2-9e6d-413d-b843-7c3685d697e7/image.png)

Client : 타깃 인터페이스만 볼 수 있음
<br>
<<인터페이스>> Target : Adapter와 함께 어댑터에서 타깃 인터페이스 구현
<br>Adapter -> Adaptee
<br>어댑터는 어댑티로 구성, 모든 요청은 어댑티에 위임

- 어댑티를 새로 바뀐 인터페이스로 감쌀 때 객체 구성(composition) 사용 <br>-> 어댑티의 모든 서브클래스에 어댑터를 쓸 수 있음
- 클라이언트를 특정 구현이 아닌 인터페이스에 연결 <br>-> 서로 다른 백엔드 클래스로 변환시키는 여러 어댑터 사용 가능



### 어댑터 사용 방법 알아보기

#### 가금류 인터페이스
```
public ibterface Turkey{
	public void gobble(); 
    public void fly();
}
```

```
public class WildTurkey implements Turkey{
	public void gobble(){
    	System.out.printlm("골골");
    }
    
    public void fly(){
    	System.out.printlm("짧은 거리를 날고 있어요!");
    }
}
```

코드 자세히 들여보기

```
public class TurkeyAdapter implements Duck{
//우선 적응시킬 형식의 인터페이스를 구현해야 함. -> 클라이언트에서 원하는 인터페이스 구현
	Turkey turkey;
    
    public TurkeyAdapter(Turkey turkey){
    	this.turkey = turkey;
    }
    //기존 형식 객체의 레퍼런스 필요. 생성자에서 레퍼런스를 받아오는 작업
    
    public void quack(){
    	turkey.gobble();
    }
    //인터페이스에 들어있는 메소드 모두 구현 
    
    public void fly(){
    	for(int i=0; i<5; i++){
        	turkey.fly();
        }
    }
}
```

#### 오리 어댑터 테스트

```
public class DuckTestDrive{
	public static void main(String[] args){
    //오리 생성
    Duck duck = new MallardDuck();
    
    //칠면조 생성
    Turkey turkey = new WildTurkey();
    //Turkey 객체를 Turkey Adapter로 감싸서 Duck처럼 보이게 만듦
    Duck turkeyAdapter = new TurkeyAdapter(turkey);
    
    //칠면조 테스트
    System.out.println("칠면조가 말하길");
 	turkey.gobble();
    turkey.fly();
    
    //Duck 객체를 전달하는 testDuck() 메소드를 호출해서 Duck 객체 테스트
    System.out.println("\n오리가 말하길");
 	testDuck(duck);
    
    System.out.println("\n칠면조 어댑터가 말하길");
 	testDuck(turkeyAdapter);
    }
    
    //testDuck() 메소드 - Duck 객체를 받아서 quack()과 fly() 메소드 호출
    static void testDuck(Duck duck){
    	duck.quack();
        duck.fly();
    }
}
```

### 어댑터 패턴 알아보기

#### 클라이언트에서 어댑터를 사용하는 방법
1. 클라이언트에서 타깃 인터페이스로 메소드를 호출해서 어댑터에 요청을 보냄
2. 어댑터는 어댑티 인터페이스로 그 요청을 어댑티에 관한 (하나 이상의) 메소드 호출로 변환(* 클라이언트와 어댑터는 서로 분리되어 있음)
3. 클라이언트는 호출 결과를 받긴 하지만 중간에 어댑터가 있다는 사실을 모름

### 객체 어댑터와 클래스 어댑터

#### 객체 어댑터
- 구성으로 어댑티에 요청을 전달
- 어댑티 클래스와 그 서브클래스에 대해서도 어댑터 역할 가능

#### 클래스 어댑터
- 타깃과 어댑티 모두 서브클래스로 만들어서 사용
- *다중 상속을 사용해서 자바에서는 사용 불가*
- 특정 어댑터 클래스에서만 사용 가능
- 어댑터 전체를 다시 구현할 필요 없음
- 서브클래스이기 때문에 어댑티의 행동 오버라이드 가능

**<<인터페이스>>Enumeration**
- hasMoreElements() : 컬렉션에 또 다른 항목이 있는지 알려줌
- nextElement() : 컬렉션에 들어있는 다음 항목 리턴

**<<인터페이스>>Iterator**
- hasNext() : Enumeration 인터페이스의 hasMoreElements()와 유사 & 컬렉션에 있는 모든 항목을 살펴봤는지만 알려줌
- next() : 컬렉션의 다음 항목 리턴
- remove() : 컬렉션의 다음 항목 제거

#### Enumeration을 Iterator에 적응시키기

![](https://velog.velcdn.com/images/jiyeong/post/2b4b3eb9-d294-45b5-ad06-12e89491dc4f/image.png)

타깃 인터페이스 Iterator
: Iterator의 remove() 메소드는 Enumeration에 없음
위 메소드 구현 시 Unsupported OperationException 지원

->

어댑터 인터페이스 Enumeration

이렇게 일대일로 대응되지 않을 땐 어댑터를 완벽하게 적용 불가

EnumerationIterator 어댑터 코드

```
public class EnumerationIterator implements Iterator<Object>{
//Enumeration을 Iterator에 적응시킴
//어댑터는 Iterator에 인터페이스 구현

	Enumeration<?> enumeration;

	public EnumerationIterator(Enumeration<?> enumeration){
    //적응시켜야 하는 Enumeration 객체 
    //구성을 활용하고 있기에 인스턴스 변수에 저장
    	this.enumeration = enumeration;
    }
    
    public boolean hasNext(){
    //Iterator의 hasNext() 메소드는 Enumeration의 hasMoreElements 메소드로 연결
    	return enumeration.hasMoreElements();
    }
    
    public Object next(){
    //Iterator의 next() 메소드는 Enumeration의 nextElement() 메소드로 연결
    	return enumeration.nextElement();
    }
    
    public void remove(){
    //예외를 던짐
    	throw new UnsupportedOperationException();
    }
}
```

### 데코레이터 패턴과 어댑터 패턴의 차이점

#### 데코레이터 패턴
- 새로운 책임과 행동이 디자인에 추가됨

#### 어댑터 패턴
- 여러 클래스를 클라이언트에서 원하는 인터페이스에 맞게 뱐환
- 클라이언트 코드를 고치지 않고 새로운 라이브러리를 쓸 수 있음


## Facade Pattern
- 인터페이스를 단순하게 바꾸려고 인터페이스 변경함
- 클라이언트와 서브시스템이 서로 긴밀하게 연결되게 만들어 줌


### 퍼사드 작동 원리 알아보기

1. 홈시어터 시스템용 퍼사드 생성 - HomeTheaterFacade 클래스
2. 퍼사드 클래스는 홈시어터 구성 요소를 하나의 서브시스템으로 간주, watchMovie() 메소드는 서브시스템의 메소드를 호출해서 필요한 작업 처리
3. 클라이언트 코드는 서브시스템이 아닌 홈시어터 퍼사드에 있는 메소드 호출
4. 퍼사드를 쓰더라도 서브시스템에 여전히 직접 접근 가능.

Q&A

A1. 
- 퍼사드 클래스는 서브시스템 클래스를 캡슐화하지 않음
- 서브시스템의 기능을 사용할 수 있는 간단한 인터페이스 제공
- 클라이언트에서 특정 인터페이스가 필요하다면 서브시스템 클래스 사용
<br>-> 단순화된 인터페이스를 제공하면서도 클라이언트에서 필요로 하면 시스템 모든 기능 사용

A3. 특정 서브시스템에 대해 만들 수 있는 퍼사드의 개수에는 제한이 없음

A4. 퍼사드를 사용하면 클라이언트 구현과 서브시스템을 분리 가능

A5. 
- 어댑터 패턴은 하나 이상의 클래스 인터페이스를 클라이언트에서 필요로 하는 인터페이스로 변환
- 어댑터 패턴은 인터페이스를 변경해서 클라이언트에서 필요로 하는 인터페이스로 적응시키는 용도로 쓰임

- 퍼사드 패턴도 복잡한 인터페이스를 가진 클래스에 대한 퍼사드 생성 가능
- 퍼사드 패턴은 어떤 서브시스템에 대한 간단한 인터페이스 제공하는 용도
- 퍼사드 패턴은 인터페이스를 단순하게 만들고, 클라이언트와 구성 요소로 이뤄진 서브시스템을 분리하는 역할도 함

디자인 원칙 new! > 최조 지식 원칙(Principle of Least Knowledge)
객체 사이의 상호 작용은 될 수 있으면 가까울 때만 허용하기

#### 홈시어터 퍼사드 만들기

```
public class HomeTheaterFacade{
	Amplifier amp;
    Tuner tuner;
    StreamingPlayer player;
  	Projector projector;
    TheaterLights lights;
    Screen screen;
    PopcornPopper popper;
    //구성 부분으로 사용하고자 하는 서브시스템의 모든 구성 요소가 인스턴스 변수 형태로 저장

	public HomeTheaterFacade(Amplifier amp, Tuner tuner,
    StreamingPlayer player, Projector projector,
    TheaterLights lights, Screen screen, 
    PopcornPopper popper){
    
    	this.amp = amp;
        this.tuner = tuner;
        this.player = player;
        this.projector = projector;
        this.screen = screen;
        this.lights = lights;
        this.popper = popper;
    }
}
```

#### 단순화된 인터페이스 만들기
서브시스템의 구성 요소를 모두 합쳐 통합 인터페이스를 만들어야 함

```
public void watchMovie(String movie){
//각 작업은 서브시스템에 들어있는 구성 요소에 위임됨
	System.out.println("영화 볼 준비 중");
    popper.on();
    popper.pop();
    lights.dim(10);
    screen.down();
    projector.on();
    projector.wideScreenMode();
    amp.on();
    amp.setStreamingPlayer(player);
    amp.setSurroundSound();
   	amp.setVolume(5);
    plater.on();
    player.play(movie);
}

public void endMovie(){
	System.out.println("홈 시어터 끄는 중");
    popper.off();
    lights.on();
    screen.up();
    projector.off();
    amp.off();
    player.stop();
    plater.off();
}
```

#### 퍼사드 패턴으로 인터페이스 돌려보기

```
public class HomeTheaterTestDrive{
	public static void main(String[] args){
    	//구성 요소 초기화
        //보통 클라이언트에 퍼사드가 주어지므로 직접 구성 요소 생성x
        
        HomeTheaterFacade homeTheater = new HomeTheaterFacade(amp, tuner, player, projector, screen, lights, popper);
        //서브시스템에 들어가는 모든 구성 요소를 매개 변수로 전달해서 퍼사드 인스턴스를 만듦
        
        homeTheater.watchMovie("인디애나 존스: 레이더스");
        homeTheater.endMovie();
        //단순화된 인터페이스를 써서 영화 재생을 시작한 다음 홈시어터 끔
    }
}
```

#### 여러 객체와 가깝지 않게 지내면서 영향력 행사하기

- 객체 자체
- 메소드에 매개변수로 전달된 객체
- 메소드를 생성하거나 인스턴스를 만든 객체
- 객체에 속하는 구성 요소

x다른 메소드를 호출해서 리턴받은 객체의 메소드를 호출하는 일x
<br>'구성 요소'는 인스턴스 변수에 의해 참조되는 객체를 의미 -> 'A에는 B가 있다'라는 관계에 있는 객체를 생각해보기!
<br>

EX01. 원칙을 따르지 않은 경우
```
public float getTemp(){
//station으로부터 thermometer 객체를 받은 다음, 그 객체의 getThermometer() 메소드 직접 호출
	Thermometer thermometer = station.getThermometer();
    return thermometer.getTemperature();
}
```

EX02. 원칙을 따르는 경우
```
public float getTemp(){
	return station.getTemperature();
    //최소 지식 원칙을 적용해 thermometer에게 요청을 전달하는 메소드를 station 클래스에 추가 
    //-> 의존해야하는 클래스 개수 줄이기 가능
}
```

자동차를 나타내는 Car 클래스의** 최소 지식 원칙(a.k.a 데메테르의 법칙)** 예시

```
public class Car{
	Engine engine; //이 클래스의 구성 요소
    
    public Car(){}
    
    public void start(Key key){
    	Doors doors = new Doors(); //새로운 객체 생성
       	boolean authorized = key.turns(); //매개변수로 전달된 객체의 메소드 호출 가능
        if(authorized){
        engine.start(); //이 객체의 구성 요소를 대상으로 메소드 호출
        updateDashboardDisplay(); //객체 내에 있는 메소드 호출
        doors.lock(); //직접 생성하거나 인스턴스를 만든 객체의 메소드 호출
        }
    }
}
```

데메테르의 법칙의 장단점
- 장점 : 객체 사이의 의존성을 줄이고, 관리가 편해짐

- 단점 : 메소드 호출을 처리하는 래퍼 클래스를 더 만들어야 할 수도 있음

## Summary
- 기존 클래스를 사용하려고 하는데 인터페이스가 맞지 않을 때 어댑터 사용
- 큰 인터페이스와 여러 인터페이스를 단순하게 바꾸거나 통합해야할 때는 퍼사드
- 어댑터는 인터페이스를 클라이언트에서 원하는 인터페이스로 변환
- 퍼사드는 클라이언트를 복잡한 서브시스템과 분리함
- 어댑터를 구현할 때는 타깃 인터페이스의 크기와 구조에 따라 코딩해야 할 분량 결정
- 퍼사드 패턴에선 서브시스템으로 퍼사드를 만들고 진짜 작업은 서브클래스에 맡김
- 어댑터 패턴에선 객체 어댑터 패턴과 클래스 어댑터 패턴이 있음. 클래스 어댑터를 쓸 때 다중 상속 가능
- 한 서브시스템에 퍼사드를 여러 개 만들어도 됨
- 어댑터는 객체를 감사 인터페이스를 바꾸는 용도
- 데코레이터는 객체를 감싸 새로운 행동 추가
- 퍼사드는 일련의 객체를 감사서 단순하게 만듦

#### Hint!

- 데코레이터 : 인터페이스는 바꾸지 않고 책임(기능)만 추가
- 어댑터 : 하나의 인터페이스를 다른 인터페이스로 변환
- 퍼사드 : 인터페이스를 간단하게 변경

### Quiz! 그림을 보고 맞춰보세요

1. 
![](https://velog.velcdn.com/images/jiyeong/post/8b81d03c-9053-44e3-b117-29bfc05968b9/image.png)

2. 
![](https://velog.velcdn.com/images/jiyeong/post/21994676-c423-4cda-af48-f0cb3374fcb0/image.png)

3. 
![](https://velog.velcdn.com/images/jiyeong/post/e9c6d416-9149-40a5-be94-a3395e628206/image.png)

출처 : [Refactoring.guru](https://refactoring.guru/design-patterns)

### 마지막 퀴즈!

1. 인터페이스를 변환하는 것은?
2. 퍼사드 패턴은 고수준 패턴과 저수준 패턴을 __한다.
3. 최소 지식의 원칙의 장단점 서술!
4. 퍼사드 패턴의 장점으로 객체들을 __시킬 수 있다.
5. 어댑터 클라이언트는 ____ 인터페이스를 사용한다.
6. 어댑터와 데코레이터는 모든 객체를 _____한다.
