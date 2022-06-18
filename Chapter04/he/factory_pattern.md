
# 팩토리 패턴 이해하기:star:


## ✅ 팩토리 메소드 패턴 : 
>
> 객체를 생성하기 위해 인터페이스를 만든다.
> 어떤 클래스의 인스턴스를 만들지 서브클래스에서 결정하도록 한다.
> 팩토리 메소드를 이용하면 인스턴스를 만드는 일을 서브 클래스로 미룰 수 있다.
> 

</br>

## ✅ 추상 팩토리 패턴 목적:
>
> 구체적인 클래스를 명시하지 않고 관련된 혹은 의존적인 객체들을 생성 할 수 있는 인터페이스 제공
> 

문제 
- 객체를 생성하는 'new'의 문제 : new는 인터페이스가 아니라 실제 클래스를 생성
- - OCP 에 어긋남: not closed for modification
</p>    1. 생성할 객체가 늘어나면 코드 수정 필요
</p>    2. 클래스가 많아지거나 변경되면 클라이언트 측 변경이 많아짐

=> 인터페이스를 바탕으로 만들면 여러 변화에 대응할 수 있다. (다형성 덕분)
새로운 구상 형식을 써서 확장 해야 할 때는 어떻게 해서든 다시 열 수 있게 만들어야 한다.<- 바뀌는 부분을 찾아내서 바뀌지 않는 부분과 분리해야 한다는 원칙 적용


</br>
</br>

## ✅ 팩토리 메소드 패턴 구현 
예제) 피자를 주문하는 예제
뉴욕과 시카고 지역에 매장을 추가하게 되었다. 각 지역별로 피자를 만드는 방식이 조금씩 차이가 난다. 같은 채식피자라도 지역별로 다른 방식으로 처리해야 한다. 
따라서 지역별로 피자를 만드는 방법을 관리해야 하기 때문에 지역별 PizzaFactory를 만든다. (SimplePizzaFactory를 지역을 기준으로 둘로 쪼갠다)

뉴욕 지역을 위한 피자 인스턴스를 관리하는 클래스 이름을 NYPizzaFactory로 지었다.

```java
NYPizzaFactory nyFactory = new NYPizzaFactory();
PizzaStore nyStore = new PizzaStore(nyFactory);
nyStore.order("Veggie");

```
Line 1: 뉴욕 스타일 피자 인스턴스 생성을 담당하는 NYPizzaFactory 클래스를 인스턴스화 한다.
Line 2: PizzaStore의 생성자 nyFactory를 주입한다. 이제부터 nyStore는 nyFactory에서 제공하는 방식으로만 피자를 생성 할 수 있게 된다.
Line 3: Veggie type의 피자를 주문한다.

</br>

위와 마찬가지로 시카고 스타일 피자 생성방법을 매장에 부여한다.

```java
ChicagoPizzaFactory chicagoFactory = new ChicagoPizzaFactory();
PizzaStore chicagoStore = new PizzaStore(chicagoFactory);
chicagoStore.order("Veggie");


```
뉴욕과 시카고 지역을 위한 피자 제작 방식을 Factory 클래스를 사용하여 분리했다.

</br>

지역별 피자 인스턴스 생성에 대헤 잘 대처했지만 코드에 한가지 문제점이 있다.
각 매장에 너무 높은 자유도를 부여한것인지 pizza를 두번 굽거나, 피자는 자르지 않는 일이 발생했다. 
그래서 모든 매장에서 피자를 처리하는 활동(주문, 굽기, 자르기 등)을 하나로 통일 하려고 한다.

해결을 위해 먼저 PizzaStore를 추상 클래스로 변경한다. (이제 PizzaStore 클래스를 인스턴스화 할 수 없게 되었다.)

</br>

1. Creator 클래스인 PizzaStore를 만든다. 이 클래스는 createPizza 라는 팩토리 메소드를 실행해서 pizza를 생성한다.

```java

public abstract class PizzaStore {
    public Pizza orderPizza(String type) {
        Pizza pizza = createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.box();

        return pizza;
    }

    abstract Pizza createPizza(String type);
}

```
</br>

2.PizzaStore를 확장해 각 지점을 만들고 지점마다 피자를 만드는 것을 책임지도록 createPizza 팩토리 메소드를 오버라이딩 한다.

```java
public class NYPizzaStore extends PizzaStore {
    @Override
    Pizza createPizza(String item) {
        if ("cheese".equals(item)) {
            return new NYStyleCheesePizza();
        } else if ("veggie".equals(item)) {
            return new NYStyleVeggiePizza();
        } else if ("clam".equals(item)) {
            return new NYStyleClamPizza();
        } else {
            return null;
        }
    }
}

```

```
public class ChicagoPizzaStore extends PizzaStore {
    @Override
    Pizza createPizza(String item) {
        if ("cheese".equals(item)) {
            return new ChicagoStyleCheesePizza();
        } else if ("veggie".equals(item)) {
            return new ChicagoStyleVeggiePizza();
        } else if ("clam".equals(item)) {
            return new ChicagoStyleClamPizza();
        } else {
            return null;
        }
    }
}

```
</br>

3.product 클래스인 Pizza를 만들고 이 클래스를 확장해 구상 클래스를 만들어보기.

```java
public abstract class Pizza {
    String name;
    String dough;
    String sauce;

    void prepare() {
        System.out.println("preparing~~ " + name);
    }

    void bake() {
        System.out.println("baking~~");
    }

    void box() {
        System.out.println("boxing~~");
    }
    
    public String getName() {
        return name;
    }
}


```
</br>

4. Product 클래스인 Pizza를 확장해 3가지 시카고 피자를 만들었다.

```java
public class ChicagoStyleCheesePizza extends Pizza {
    public ChicagoStyleCheesePizza() {
        name = "ChicagoStyleCheesePizza";
    }
}

```

```java
public class ChicagoStyleClamPizza extends Pizza {
    public ChicagoStyleClamPizza() {
        name = "ChicagoStyleClamPizza";
    }
}

```

```java
public class ChicagoStyleVeggiePizza extends Pizza {
    public ChicagoStyleVeggiePizza() {
        name = "ChicagoStyleVeggiePizza";
    }
}

```
</br>

5. main 메소드에서 예제 실행
```java
PizzaStore nyStore = new NYPizzaStore();
PizzaStore chicagoStore = new ChicagoPizzaStore();

Pizza nyCheesePizza = nyStore.orderPizza("cheese");
Pizza nyClamPizza = nyStore.orderPizza("clam");

Pizza chicagoCheesePizza = chicagoStore.orderPizza("cheese");
Pizza chicagoClamPizza = chicagoStore.orderPizza("clam");

```

```java
preparing~~ NYStyleCheesePizza
baking~~
boxing~~
preparing~~ NYStyleClamPizza
baking~~
boxing~~
preparing~~ ChicagoStyleCheesePizza
baking~~
boxing~~
preparing~~ ChicagoStyleClamPizza
baking~~
boxing~~

```

지점과 주어진 타임(cheeze, clam)에 따라 다른 피자를 만드는 걸 볼 수 있다.
