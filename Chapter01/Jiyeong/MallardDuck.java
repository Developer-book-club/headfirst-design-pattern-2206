package headfirst.designpatterns.Jiyeong;

public class MallardDuck extends Duck{

    public MallardDuck(){
        quackBehavior = new Quack(); //꽥꽥거리는 행동은 quack 객체에 위임됨.
        flyBehavior = new FlyWithWings();
    }

    public void display(){
        System.out.println("저는 물오리입니다.");
    }
}
