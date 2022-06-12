package headfirst.designpatterns.Jiyeong;


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
