# Flyweight Pattern

> 동일하거나 유사한 객체들 사이에 가능한 많은 데이터를 서로 공유하여 사용하도록 하여 메모리 사용량을 최소화하는 소프트웨어 디자인 패턴

어떤 클래스의 인스턴스 하나로 여러 개의 '가상 인스턴스'를 제공하고 싶다면 플라이웨이트 패턴을 사용한다.

즉 인스턴스를 new 연산자로 쓸데없이 메모리 낭비를 줄이는 방식 

- 장점
    - 실행 시에 객체 인스턴스의 개수를 줄여 메모리를 절약 가능
    - 여러 ‘가상’ 객체의 상태를 한곳에 모아 둘 수 있음.


- 활용법
    - 어떤 클래스의 인스턴스가 아주 많이 필요하지만 모두 똑같은 방식으로 제어해야 할 때 유용하게 사용


- 단점
    - 일단 이 패턴을 써서 구현해 놓으면 특정 인스턴스만 다른 인스턴스와 다르게 행동하게 할 수 없다.
    
``` java
import java.util.HashMap;
import java.util.Map;

// Flyweight 인터페이스
interface Shape {
    void draw();
}

// ConcreteFlyweight 클래스
class Circle implements Shape {
    private String color;

    public Circle(String color) {
        this.color = color;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a circle with color: " + color);
    }
}

// FlyweightFactory 클래스
class ShapeFactory {
    private static final Map<String, Shape> circleMap = new HashMap<>();

    // 캐시된 동일한 색상의 원 객체 반환 또는 생성 후 반환
     public static Shape getCircle(String color) {
        Circle circle = (Circle)circleMap.get(color);

         if(circle == null){
            circle = new Circle(color);
            circleMap.put(color, circle);
            System.out.println("Creating a new circle with color: " + color);
         } else{
             System.out.println("Reusing an existing circle with color: " + color);
         }

        return circle;
     }
}

public class Main {
   private static final String[] colors =
      { "Red", "Green", "Blue", "Yellow", "Black" };

   public static void main(String[] args) {
      for(int i = 0; i < 10; ++i) {
         Circle circle =
            (Circle)ShapeFactory.getCircle(getRandomColor());
         circle.draw();
      }
   }

   private static String getRandomColor() {
      return colors[(int)(Math.random()*colors.length)];
    }
```
