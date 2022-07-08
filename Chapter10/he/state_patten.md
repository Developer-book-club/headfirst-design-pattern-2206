# 상태(state) 패턴 이해하기 :star2:

</br>
</br>

## 🎯 의도
> 스테이트 패턴에서는 필요한 기능을 인터페이스로 만들고, </p>
> 모든 상태를 캡슐화시켜서 해당 인터페이스를 구현하도록 만든다.</p>
> 스테이트 패턴을 사용하면 각 상태별 기능의 동작을 명확하게 분리 할 수 있다.

</br>
</br>

## ✅ 정의
> 상태패턴을 사용하면 객체 내부 상태가 바뀜에 따라서 객체의 행동을 바꿀 수 있다. </p>
> 마치 객체의 클래스가 바뀌는 것과 같은 결과를 얻을 수 있다.</p>

> => 각 상태마다 클래스를 분리하여 정의하고, 구성 관계를 통해 여러 상태 객체를 바꿔가면서 사용함으로써 </p>
클라이언트는 Context의 메소드를 호출 하는 것만으로도 상태 관련 작업을 처리 할 수 있다. </p>
> => if, switch 문과 같은 분기문을 패턴을 이용해 캡슐화, 분리 한다고 생각하면 된다. 

</br>
</br>

## ✅ 구조
<img width="589" alt="스테이트 패턴" src="https://user-images.githubusercontent.com/98209409/177044901-6497c778-6a7c-4e23-bbb2-18b5406b5ed2.png">

출처 : https://yiyj1030.tistory.com/404

</br>

전략 패턴과 동일한 구조를 가진다.

</br>
</br>

## ✅ context(상황), state(상태), concreteState(구체적 상태) 
> context </p>
> 여러가지 내부 상태를 가질 수 있는 클래스 </p>
> request가 호출되면 상태 객체에게 그 작업을 위임한다. </p>

> state </p>
> 모든 구상 상태 클래스에 대한 공통 인터페이스를 정의한다. </p>
> 모든 상태 클래스에서 이를 구현하기 때문에 바꿔가면서 사용할 수 있다. </p>

> concreteState </p>
> context로 부터 전달된 요청을 처리하는 구상 상태 클래스 </p>
> 각각의 구상 클래스들은 요청을 처리하는 방법을 자기 나름의 방식으로 구현한다.
> context에서 상태를 바꾸기만 하면 행동도 같이 바뀌게 된다. 

</br>
</br>

## ✅ 뽑기 기계
> 뽑기 기계는 동전없음, 동전있음, 알맹이 매진, 알맹이 판매 의 4가지 상태를 가질 수 있다.</p>
> 동전 투입, 동전 반환, 손잡이 돌리기, 알맹이 배출 의 4가지 행동을 가지고 있다. </p>
> 행동 실행 시 상태가 변한다.


```java

public class GumballMachine{
    final static int SOLD_OUT = 0;
    final static int NO_QUARTER = 1;
    final static int HAS_QUARTER = 2;
    final static int SOLD = 3;
    
    int state = SOLD_OUT;
    int count = 0;
    
    ..
    public GumballMachine(int count) {
      this.count = count;
      if(count > 0) {
      state = NO_QUARTER;
      }  
    }
    
}
 
```
</br>

-> 이 상황에서 각각의 행동을 구현하려면 행동에 해당하는 메소드에서 if문으로 state의 상태에 따라 행동을 분기해야 한다. </p>
-> 이럴 경우 상태 클래스가 추가 될때마다 모든 메소드에서 코드를 추가해야 하는 불편함이 있다! </p>
-> 바뀌는 부분은 캡슐화! 

```java
//계속해서 바뀌는 부분 

if(상태 == 동전있음) {
} else if(상태 == 동전없음) {
} else if(상태 == 알맹이 판매 {
} else if(상태 == 알맹이 매진) {
}

```
이를 분리 하려면 각 상태의 행동을 별도의 클래스에 집어넣고 모든 상태에서 각각 자기가 할 일을 구현하게 하면 된다. </p>


</br>
</br>

```java

================================>

//변경된 코드
public class GumballMachine{
    //상수 대신 상태 클래스를 사용
    State soldOutState; 
    State noQuarterState;
    State hasQuarterState
    State soldState;
    
    State state = soldState;
    int count = 0;
    
    public GumballMachine(int numberGumballs){
    	soldOutState = new SoldOutState(this); 
    	noQuarterState = new NoQuarterState(this);
    	hasQuarterState = new HasQuaterState(this);
    	soldState = new SoldState(this);
        
        this.count = numberGumballs;
        if(numberGumballs > 0)
        	state = noQuarterState;
    }
    
    public void insertQuarter(){
    	//조건문이 전부 사라지고 현재 상태 객체에 위임
        state.insertQuarter();
    }
    
    public void ejectQuarter(){
        state.ejectQuarter();
    }
    
    public void turnCrank(){
        state.turnCrank();
        state.dispense();
    }
    
    ..
    
    //해당 메소드를 사용해 다른 객체에서 상태 변경 가능
   	void setState(State state){
    	this.state = state;
    }
    
    
    //..각 State 객체를 위한 getter 메소드 등등..
    
}

```

</br>
</br>

제어문을 통해 상태를 검사해 행동을 분기하는 부분이 없어졌다. </p>
그저 상태를 위한 인터페이스와 구상 클래스만 만들고 이를 구성으로 집어넣는다. </p>
각각의 행동이 호출되면 state 객체에게 행동을 위임한다.</p>
</br>

현재 뽑기기계의 상태에 맞는 상태 객체가 요청을 위임받아 처리하게 될 것이다.</p>
</br>
</br>

## ✅ 개선된 사항
- 각 상태의 행동을 별개의 클래스로 국지화 함 - 캡슐화 </p>
- 관리하기 힘든 골칫덩어리 if 선언문들을 없앰 - 유연성 </p>
- 각 상태를 변경에는 닫혀있게 했고, GamballMachine클래스는 새로운 상태 클래스를 </p>
  추가하는 확장에는 열려 있도록 고침 - OCP
- 주식회사 왕뽑기에서 처음 제시했던 다이어그램에 훨씬 가까우면서 더 이애하기 좋은 </p>
  코드 베이스와 클래스 구조를 만듬 </p>

</br>
</br>

## ✅ 스테이트 패턴 VS 스트래티지 패턴

</br>

1. 스테이트 패턴은 상태 객체에 일련의 행동이 캡슐화된다. </p>
  - 상황에 따라 Context 객체에서 여러 상태 객체 중 한 객체에게 모든 행동을 맡기게 된다.  </p>
    내부 상태에 따라 현재 상태를 나타내는 객체가 바뀌게 되고  결국 컨텍스트 객체의 행동도 자연스럽게 바뀌게 된다.  </p>
    클라이언트는 이러한 상태와 전환 과정을 거의 몰라도 된다. </p>
 
 </br> 
 
2. 스트래티지 패턴은 일반적으로 클라이언트에서 컨텍스트 객체한테 어떤 전략 객체를 사용할지를 지정해준다.</p>
  - 주로 실행시에 전략 객체를 변경할 수 있는 유연성을 제공한다.  보통 가장 적합한 전략 객체를 선택해서 사용하게 된다 </p>
 
 </br> 
 
3. 스트래티지 패턴은 서브클래스를 만드는 방법을 대신하여 유연성을 극대화하기 위한 용도로 쓰인다. </p>
  - 상속을 이용해서 클래스의 행동을 정의하다 보면 행동을 변경해야 할 때 마음 변경하기가 힘들다.  </p>
  - 결국 구성을 통해 행동을 정의하는 객체를 유연하게 바꿀 수 있다.</p>
  
 </br> 
 
4. 스테이트 패턴은 컨텍스트 객체에 수많은 조건문을 집어넣는 대신에 사용할 수 있는 패턴이라고 할 수 있다. </p>
  - 행동을 상태 객체 내에 캡슐화시키면 컨텍스트 내의 상태 객체를 바꾸는 것만으로도 컨텍스트 객체의 행동을 바꿀 수 있다. </p>



