<h1>디자인 패턴 세계로 떠나기</h1>

<h2>Strategy Pattern</h2>
    알고리즘군을 정의하고 캡슐화해서 각각의 알고리즘군을 수정해서 쓸 수 있게 해줌.<br>
    전략패턴을 사용하면 클라이언트로부터 알고리즘을 분리해 독립적으로 변경 가능.

<h3>Duck의 행동을 상속할 때 단점이 될 수 있는 요소들</h3>
    A. 서브클래스에서 코드가 중복된다 <br>
    B. 실행 시에 특징을 바꾸기 힘들다. <br>
    D. 모든 오리의 행동을 아릭 힘들다. <br>
    F. 코드가 변경되면 다른 오리들에게 원치 않은 영향을 끼칠 수 있다. <br>

<h3>문제 해결 방법</h3>
    달라지는 부분을 찾아서 나머지 코드에 영향을 주지 않도록 <st rong>'캡슐화'</strong>하기!<br>
    -> 코드 변경시 의도치 않게 발생할 일을 줄이면서 시스템의 유연성을 향상시킬 수 있음!

<h3>상위 형식에 맞춰 프로그래밍하기</h3>
    실행 시에 쓰이는 객체가 코드에 고정되지 않도록 상위 형식(supertype)에 맞춰 프로그래밍하여 <strong>다형성</strong>을 활용해야 함!<br>
    
<h3>캡슐화된 행동 살펴보기</h3>
    <h4>구성(Composition)</h4>
    두 클래스를 합치는 것을 칭하며, 오리 클래스에서는 행동을 상속받는 대신 올바른 행동 객체로 구성되어 행동을 부여받음.

<h3>객체지향</h3>
<h4>객체지향 기초</h4>
    - 추상화 <br>
    - 캡슐화 <br>
    - 다형성 <br>
    - 상속 <br>

<h4>객제지향 원칙</h4>
    - 바뀌는 부분은 <strong>캡슐화</strong>함 <br>
    - 상속보다는 <strong>구성</strong>을 활용 <br>
    - 구현보다는 <strong>인터페이스에 맞춰</strong> 개발 <br>

<h4>객체지향 패턴 - 전략 패턴</h4>

<h2>낱말 퀴즈로 보는 Summary</h2>
- 패턴은 유연한 애플리케이션을 만드는 데 도움이 됨. <br>
- 전략 패턴을 쓰면 코드를 brain할 수 있음. <br>
- 상속보다는 <strong>구성</strong>이 나음. <br>
- 자바 IO, 네트워킹, 사운드 APIs <br>
- 패턴은 대부분 객체 지향 원칙을 따름. <br>
- 고수준 라이브러리를 프레임워크라고 부름. <br>
- 구현보다는 <strong>인터페이스</strong>에 맞춰 프로그래밍.<br>
- 바뀔 수 있는 부분은 <strong>캡슐화</strong>해야 함.

```
public abstract class Duck {
    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;
    //행동 인터페이스 형식의 레퍼런스 변수 2개를 선언함.
    //같은 패키지에 속하는 모든 서브클래스에서 이 변수를 상속 받음.

    public Duck(){}

    public abstract void display();

    //아래 두 행동 클래스에 위임함.
    public void performFly(){
        flyBehavior.fly();
    }

    public void performDuck(){
        quackBehavior.quack();
    }

    public void swim(){
        System.out.println("모든 오리는 물에 뜹니다. 가짜 오리도 뜨죠.");
    }

    //아래의 두 메소드를 호출하면 언제든지 오리의 행동을 즉석에서 바꿀 수 있음.
    public void setFlyBehavior(FlyBehavior fb){
        flyBehavior = fb;
    }

    public void setQuackBehavior(QuackBehavior qb){
        quackBehavior = qb;
    }
}

```


나는 행동과 꽥꽥거리는 행동의 인터페이스

```
public interface FlyBehavior {
    //모든 나는 행동의 클래스에서 구현
    public void fly();
}

public interface QuackBehavior {
    public void quack();
}
```

나는 행동 인터페이스를 위임받는 클래스들

```
public class FlyWithWings implements FlyBehavior{
    public void fly(){
        System.out.println("날고 있어요!");
    }
}

public class FlyNoWay implements FlyBehavior{
    public void fly(){
        System.out.println("저는 못 날아요!");
    }
}

public class FlyRocketPowered implements FlyBehavior{
    public void fly() {
        System.out.println("로켓 추진으로 날아갑니다.");
    }
}
```

꽥꽥거리는 행동 인터페이스를 위임받는 클래스들

```
public class Quack implements QuackBehavior{
    public void quack(){
        System.out.println("꽥");
    }
}

public class Squeak implements QuackBehavior{
    public void quack() {
        System.out.println("삑");
    }
}

```

특징이 다른 오리들

```
public class MallardDuck extends Duck{

    public MallardDuck(){
        quackBehavior = new Quack(); //꽥꽥거리는 행동은 quack 객체에 위임됨.
        flyBehavior = new FlyWithWings();
    }

    public void display(){
        System.out.println("저는 물오리입니다.");
    }
}

public class ModelDuck extends Duck {
    public ModelDuck(){
        flyBehavior = new FlyNoWay();
        quackBehavior = new Quack();
    }

    public void display(){
        System.out.println("저는 모형 오리입니다.");
    }
}

public class MuteQuack implements QuackBehavior{
    public void quack() {
        System.out.println("<<조용>>");
    }
}
```

테스트 해보기
```
public class MiniDuckSimulator {
    public static void main(String[] args){
        Duck mallard = new MallardDuck();
        mallard.performDuck();
        mallard.performFly();

        Duck model = new ModelDuck();
        model.performFly();
        model.setFlyBehavior(new FlyRocketPowered()); //상속받은 세터 메소드가 호출됨.
        model.performFly();
    }
}
```

공부한 책 : 헤드퍼스트 디자인패턴
