
# 팩토리 패턴 이해하기:star:

</br>
</br>

## :factory: 요약
1. 간단한 팩토리 / 팩토리 메소드 / 추상 팩토리 메소드
2. 의존성 뒤집기 - 추상화 된 것에 의존하게 만들고 구상 클래스에 의존하지 않게 만든다
3. 어떤 서브클래스를 선택했느냐에 따라 구현이 달라진다.

</br>

## ✅ 문제 
- 객체를 생성하는 'new'의 문제 : new는 인터페이스가 아니라 실제 클래스를 생성
- OCP 에 어긋남: 객체의 확정 개방적으로, 객체의 수정은 폐쇄적
	1. 생성할 객체가 늘어나면 코드 수정 필요
	2. 클래스가 많아지거나 변경되면 클라이언트 측 변경이 많아짐


</br>
=> 인터페이스를 바탕으로 만들면 여러 변화에 대응할 수 있다. (다형성 덕분)
</br>
새로운 구상 형식을 써서 확장 해야 할 때는 어떻게 해서든 다시 열 수 있게 만들어야 한다.
</br>
<- <u>바뀌는 부분을 찾아내서 바뀌지 않는 부분과 분리해야 한다는 원칙 적용</u>


</br>
</br>

## ✅ 구현 
예제) 피자를 주문하는 예제
</br>
orderPizza 메소드 : 피자 종류를 고르고 그에 맞게 피자를 만드는 코드 추가
</br>

```java

Pizza orderPizza(String type) {

    Pizza pizza;


    **if(type.equals("cheese")) pizza = new CheesePizza();**

    **else if(type.equals("greek")) pizza = new GreekPizza();**

    **else if(type.equals("pepperoni")) pizza = new PepperoniPizza();**       



    pizza.prepare(); 

    pizza.bake();

    pizza.cut();

    pizza.box();

    return pizza;

} 

```
</br>

* if ~ else if  부분은 계속 바뀐다! 피자 종류를 추가한다든지, 잘 나가지 않아서 삭제한다든지! </p>
=> 캡슐화가 필요하다! 그리고새로 만들 객체에는 **팩토리(Factory)** 라는 이름을 붙이기로 한다. 

</br>

* 간단한 피자 팩토리를 만든다  : 객체 생성 코드를 orderPizza 메소드에서 꺼내기 -> 피자 만드는 일만 처리 </br> 

## ✅ 팩토리 
: 객체 생성을 처리하는 클래스 

</br>


```java

public class SimplePizzaFactory {

	public Pizza createPizza(String type) { // 이런 경우에는 static메소드로 선언하는 경우가 종종 있음.

		Pizza pizza = null;

		if (pizza.equals("cheese"))
			pizza = new CheesePizza();

		if (pizza.equals("pepper"))
			pizza = new PepperoniPizza();

		if (pizza.equals("clam"))
			pizza = new ClamPizza();

		if (pizza.equals("veggie"))
			pizza = new VeggiePizza();

		return pizza;

	}

}

```

</br>
바뀐 PizzaStore 클래스 </br>
: 이 클래스 생성자에 팩토리 객체가 전달, orderPizza() 메소드는 팩토리로 피자 객체를 만든다.

```java

public class PizzaStore {

	SimplePizzaFactory simplePizzaFactory;

	public PizzaStore(SimplePizzaFactory simplePizzaFactory) {

		this.simplePizzaFactory = simplePizzaFactory;

	}

	public Pizza orderPizza(String type) {

		Pizza pizza;

		pizza = simplePizzaFactory.createPizza(type);

		pizza.prepare();

		pizza.bake();

		pizza.cut();

		pizza.box();

		return pizza;

	}

}

```
</br>

## ✅ 간단한 팩토리
디자인 패턴이라기보다는 프로그래밍에서 자주 쓰이는 관용구에 가깝다.
</br>
</br>

## ✅ 팩토리 메소드 패턴
>
> 객체를 생성하기 위해 인터페이스를 만든다.
> 어떤 클래스의 인스턴스를 만들지 서브클래스에서 결정하도록 한다.
> 팩토리 메소드를 이용하면 인스턴스를 만드는 일을 서브 클래스로 미룰 수 있다.


</br>

## ❗ 피자 가게 사업 확장 
    => 여러 지역별로 각각의 다른 스타일의 피자를 만들어야 한다! (문제발생)

* 문제
</br>
각 팩토리를 가진 피자가게 체인점들이 서로의 구현방식이 달라지는 일이 발생 할 수도 있게 됨 
</br>
PizzaStore가 각각 있다보니 굽는 방식이 달라진다거나 피자를 자르는 단계를 빼먹거나 하는...
</br>

모든 프렌차이즈 분점에서 PizzaStore 코드를 사용하여 진행 한다. 간단한 팩토리를 사용한 SimplePizzaFactory를 빼고 세가지 서로 다른 팩토리 
</p> (NYPizzaFactory, ChicagoPizzaFactory, CaliforniaPizzaFactory) 를 만들고 PizzaStore에서 팩토리를 사용하도록 하자 
</br>



## :dart: 프레임 워크 만들기 (피자가게와 피자 제작과정을 하나로 묶는!)
    => 피자를 만드는 활동 자체는 전부 PizzaStore 클래스에 국한시키면서 분점마다 고유의 스타일을 살릴 수 있는 방법은?
    createPizza() 메소드를 PizzaStore에 넣는다. 하지만 이번에는 그 메소드를 추상 메소드로 선언하고, 
    지역별 스타일에 맞게 PizzaStore의 서브클래스를 만든다.
</br>

```java

public abstract class PizzaStore { // PizzaStore는 추상 클래스!

	public Pizza orderPizza(String type) {

		Pizza pizza;

		Pizza pizza = createPizza(type); //팩토리 객체가 아닌 PizzaStore에 있는 createPizza를 호출 
		//서브클래스에서 결정하도록 함

		pizza.prepare();

		pizza.bake();

		pizza.cut();

		pizza.box();

		return pizza;

	}

	abstract Pizza createPizza(String type); // 팩토리 객체 대신 이 메소드 사용
	// Pizza 인스턴스를 만드는 일은 팩토리 역할을 하는 메소드에서 맡아 처리
}

```

* 이제 각 지점에 맞는 서브 클래스 만들기 (NYPizzaStore, ChicagoPizzaStore, CaliforniaPizzaStore) 피자의 스타일은 각 서브클래스에서 결정
</br>
PizzaStore에는 createPizza(), orderPizza() 메소드가 있음! 
모든 지점에서 주문 시스템에 따라 주문이 진행되어야 하며, 각 지점마다 달라질 수 있는 것은 피자 스타일 뿐이다.
</br>
그러므로, 각 서브클래스는 createPizza() 메소드를 오버라이드 하지만, orderPizza()메소드는 PizzaStore에서 정의한 내용 그대로 사용한다.
</br>
우리가 정의한 메소드를 고쳐 쓸 수 없게 하고 싶다면 orderPizza() 메소드를 final로 선언하면 된다.
</br>

## ✅ 팩토리 메소드 선언
: PizzaStore는 구상 클래스 인스턴스 만드는 일을 하나의 객체가 전부 처리하는 방식에서 일련의 서브 클래스가 처리하는 방식으로 바뀌었다. </p>
피자 객체 인스턴스를 만드는 일은 PizzaStore 의 서브 클래스 NYPizzaStore에 있는 createPizza() 메소드에서 처리

</br>
</br>

* NYPizzaStore : PizzaStore를 확장
</br>

```java

public class NYPizzaStore extends PizzaStore {

	@Override
	public Pizza createPizza(String type) { // createPizza는 PizzaStore에서 추상 메소드로 선언되었으므로 구상 클래스에서 반드시 구현!

		Pizza pizza = null;

		if (type.equals("cheese"))
			pizza = new NYStyleCheesePizza();

		if (type.equals("peper"))
			pizza = new NYStylePepperoniPizza();

		if (type.equals("clam"))
			pizza = new NYStyleClamPizza();

		if (type.equals("veggie"))
			pizza = new NYStyleVeggiePizza();

		return pizza;

	}

}

```

* ChicagoPizzaStore도 동일! 종류만 다름 

```java 

public class ChicagoPizzaStore extends PizzaStore {

	@Override
	public Pizza createPizza(String type) {

		Pizza pizza = null;

		if (type.equals("cheese"))
			pizza = new ChicagoStyleCheesePizza();

		if (type.equals("peper"))
			pizza = new ChicagoStylePepperoniPizza();

		if (type.equals("clam"))
			pizza = new ChicagoStyleClamPizza();

		if (type.equals("veggie"))
			pizza = new ChicagoStyleVeggiePizza();

		return pizza;

	}

}

```
</br>

## :interrobang: 간단한 팩토리와 팩토리 메소드 패턴의 차이를 모르겠어요! 
간단한 팩토리 : 스스로 결정한다 -> Cheese -> 같은 타입의 인스턴스가 나온다 </p>
팩토리 메소드 패턴 : 서브클래스가 결정한다. -> 서로 다른 클래스 -> Cheese -> 서로 다른 인스턴스가 나온다.

</br>
</br>


## ✅ 객체 의존성 
: 모든 피자 객체를 직접 생성해야 하므로, 이 PizzaStore는 모든 피자 객체에 직접 의존하게 된다. </P>
피자 클래스 구현이 변경되면 PizzaStore까지 고쳐야 할 수도 있다. </p>
피자 종류를 새로 추가하면 PizzaStore는 더 많은 피자 객체에 의존하게 된다.

! p.172 img 참조 
 
</br>

## :heavy_exclamation_mark: 디자인원칙
    => 추상화 된 것에 의존하도록 만들어라. 구상 클래스에 의존하도록 만들지 않도록 한다.

