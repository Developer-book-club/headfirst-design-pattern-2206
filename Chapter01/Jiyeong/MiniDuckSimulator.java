package headfirst.designpatterns.Jiyeong;

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
