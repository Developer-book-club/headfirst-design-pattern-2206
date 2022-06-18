# ✏️ 04장 팩토리 패턴 Factory Pattern

> 'new'연산자를 사용하면 구상 클래스의 인스턴스가 만들어진다.   
> 구상 클래스를 바탕으로 코딩하면 나중에 수정해야 할 가능성이 커지고 유연성이 떨어진다.   
> 다형성을 활용하면 시스템에서 일어날 수 있는 여러 변화에 대처할 수 있다. 구현해야 하는 클래스에 implements해서 사용할 수 있기 때문이다.  
> 우리는 인터페이스에 맞춰서 코딩하여 확장에는 열려있고, 변경에는 닫혀있는 코드를 쓰자.

**팩토리 메소드 패턴**  

팩토리 메소드 패턴은 객체를 생성할 때 필요한 인터페이스를 만든다.  
어떤 클래스의 인스턴스를 만들지는 서브클래스에서 결정한다.  
팩토리 메소드 패턴을 사용하면 클래스 인스턴스 만드는 일을 서브클래스에 맡기게 된다.  

- 팩토리 메소드 패턴으로 구상 형식 인스턴스를 만드는 작업을 캡슐화할 수 있다.
- Creator 추상 클래스에서 객체를 만드는 메소드(팩토리 메소드용 인터페이스)를 제공한다.
- Creator 추상 클래스에 구현되어 있는 다른 메소드는 팩토리 메소드에 의해 생산된 제품으로 필요한 작업을 처리한다.
- 실제 팩토리 메소드를 구현하고 제품(객체 인스턴스)을 만드는 일은 서브클래스에서만 할 수 있다.
- 사용하는 서브클래스에 따라 생산되는 객체 인스턴스가 결정된다.

## 🍕 객체 생성 부분을 캡슐화하여 객체 생성 팩토리 만들기

> 피자 가게 예제

```java
Pizza orderPizza(String type) {
  Pizza pizza;

  if (type.equals("cheese")) {
    pizza = new CheesePizza();
  } else if (type.equals("clam")) {
    pizza = new ClamPizza();
  } else if (type.equals("clam")) {
    pizza = new ClamPizza();
  } 

  pizza.prepare();
  pizza.bake();
  pizza.cut();
  pizza.box();
  return pizza;
}
```

- 인스턴스를 만드는 구상 클래스를 선택하는 부분은 피자 종류가 바뀔 때마다 코드를 계속 고쳐야한다. 그 부분만 따로 떼어서 캡슐화해보자

### 팩토리 

**팩토리**: 객체 생성을 처리하는 클래스
```java
public class SimplePizzaFactory {

  public Pizza createPizza(String type) {
    Pizza pizza = null;

    if (type.equals("cheese")) {
      pizza = new CheesePizza();
    } else if (type.equals("clam")) {
      pizza = new ClamPizza();
    } else if (type.equals("clam")) {
      pizza = new ClamPizza();
    } 
    return pizza;
  }
}
```

클라이언트 코드
```java
public class PizzaStore {
  SimplePizzaFactory factory;

  public PizzaStore(SimplePizzaFactory factory) {
    this.factory = factory;
  }

  public Pizza orderPizza(String type) {
    Pizza pizza;

    pizza = factory.createPizza(type);

    pizza.prepare();
    pizza.bake();
    pizza.cut();
    pizza.box();
    return pizza;
  }
}
```