# 상태(state) 패턴 이해하기 :star2:

</br>
</br>

## 🎯 의도
> 스테이트 패턴에서는 필요한 기능을 인터페이스로 만들고, </p>
> 모든 상태를 캡슐화시켜서 해당 인터페이스를 구현하도록 만든다.</p>
> 스테이트 패턴을 사용하면 각 상태별 기능의 동작을 명확하게 분리 할 수 있다.


</br>
</br>

## ✅ 뽑기 기계
> 뽑기 기계는 동전없음, 동전있음, 알맹이 매진, 알맹이 판매 의 4가지 상태를 가질 수 있다.</p>
> 동전 투입, 동전 반환, 손잡이 돌리기, 알맹이 배출 의 4가지 행동을 가지고 있다. </p>
> 행동 실행 시 상태가 변한다.

</br>
</br>

<img width="853" alt="뽑기 기계 작동 원리" src="https://user-images.githubusercontent.com/98209409/178126574-f8d2151d-4d6f-4442-9ef4-fa20cef8666b.png">

출처 : https://bb-dochi.tistory.com/83

</br>
</br>

```java

public class GumballMachine{
    final static int SOLD_OUT = 0; //매진
    final static int NO_QUARTER = 1; //동전없음
    final static int HAS_QUARTER = 2; //동전있음
    final static int SOLD = 3; // 판매
    
    int state = SOLD_OUT;
    int count = 0;
    
    ..
    public GumballMachine(int count) {
      this.count = count;
      if(count > 0) {
      state = NO_QUARTER;
      }  
    }
    
    //동전삽입
    public void insertQuarter() {
        if ( state == HAS_QUARTER ) { 
        
        // 출력 : 동전은 한 개만
        System.out.println('동전은 한 개만 넣어주세요');  
        
        }else if ( state == NO_QUARTER ) {
            state = HAS_QUARTER;            
            // 출력 : 동전 받음        
       }         
       else if ( state == SOLD_OUT ) { // 출력 : 매진 }        
       else if ( state == SOLD ) { // 출력 : 껌볼 나오는 중 }    
   }     
   public void ejectQuater() { // 동전 반환시에 해야할 일 }    
   public void turnCrank() { // 손잡이 돌릴 때 해야할 일 }    
   public void dispense() { // 껌볼 내줄 때 해야할 일 }     
   // 기타 메소드
    
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

## ✅ 정의
> 상태패턴을 사용하면 객체 내부 상태가 바뀜에 따라서 객체의 행동을 바꿀 수 있다. </p>
> 마치 객체의 클래스가 바뀌는 것과 같은 결과를 얻을 수 있다.</p>
</br>
> => 각 상태마다 클래스를 분리하여 정의하고, 구성 관계를 통해 여러 상태 객체를 바꿔가면서 사용함으로써 </p>
클라이언트는 Context의 메소드를 호출 하는 것만으로도 상태 관련 작업을 처리 할 수 있다. </p>
> => if, switch 문과 같은 분기문을 패턴을 이용해 캡슐화, 분리 한다고 생각하면 된다. 

</br>
</br>

## ✅ 구조
<img width="703" alt="스테이트 패턴 구조" src="https://user-images.githubusercontent.com/98209409/178125764-f85c0005-8953-4648-b0cb-9cc0ab89812d.png">

출처 : https://bb-dochi.tistory.com/83

</br>

전략(스트래터지) 패턴과 동일한 구조를 가진다.

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

## ✅ 뽑기기계 부분 UML
<img width="652" alt="해당 상태 패턴 구조" src="https://user-images.githubusercontent.com/98209409/178125292-032a1971-0f65-457b-b608-64bc5fc2f50c.png">


출처 : https://secretroute.tistory.com/entry/Head-First-Design-Pattern-%EC%A0%9C10%EC%9E%A5-State-%ED%8C%A8%ED%84%B4

</br>

 : 공통 state 인터페이스를 사용하고 모든 상태를 캡슐화 통해서 관리한다.

</br>
</br>

## ✅ State Interface

```java

//State 인터페이스 
public interface State {
    public void insertQuarter();
    public void ejectQuarter();
    public void turnCrank();
    public void dispense();
}

```
</br>

## ✅ GumballMachine

```java

//Context 클래스
public class GumballMachine {
    //상수 대신 상태 클래스를 사용
    State soldOutState;
    State noQuarterState;
    State hasQuarterState;
    State soldState;
    
    State state = soldoutState;
    int count = 0;
    
    public GumballMachine(int numberGumballs) {
        soldOutState = new SoldOutState(this);
        noQuarterState = new NoQuarterState(this);
        hasQuarterState = new HasQuarterState(this);
        soldState = new SoldState(this);
        this.count = numberGumballs;
        if (numberGumballs > 0) {
            state = noQuarterState;
        }
    }
    
    public void insertQuarter() {
        // 조건문이 전부 사라지고 현재 상태 객체에 위임
        // 메서드 호출만
        state.insertQuarter();
    }
    
    public void ejectQuarter() {
        state.ejectQuarter();
    }
    
    public void turnCrank() {
        state.turnCrank();
        state.dispense();
    }
    
    // 해당 메소드를 사용해 다른 객체에서 상태 변경 가능
    void setState(State state) {
        this.state = state;
    }
    
    void releaseBall() {
        System.out.println("A gumball comes rolling out the slot...");
        if (count != 0) {
            count = count - 1;
        }
    }
    
    /*각종 GET, SET 함수*/
}

```
</br>

## ✅ HasQuaterState, NoQuaterState, SoldOutState, SoldState

```java
//ConcreteState 클래스들

public class HasQuaterState implements State {
    GumballMachine gumballMachine;
    
    public HasQuaterState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }
    
    public void insertQuarter() {
        System.out.println("동전은 한 개만 넣어주세요.");
    }
    
    public void ejectQuarter() {
        System.out.println("동전이 반환됩니다.");
        gumballMachine.setState(gumballMachine.getNoQuarterState());
    }
    
    public void turnCrank() {
        System.out.println("손잡이를 돌리셨습니다.");
        gumballMachine.setState(gumballMachine.getSoldState());
    }
    
    public void dispense() {
        System.out.println("알맹이가 나갈 수 없습니다.");
    }
}

//--------------NoQuaterState
public class NoQuarterState implements State{
  GumballMachine gumballMachine;

  public NoQuarterState(GumballMachine gumballMachine) {
      this.gumballMachine = gumballMachine;
  }

  @Override
  public void insertQuarter() {
      System.out.println("동전을 넣으셨습니다.");
      gumballMachine.setState(gumballMachine.getHasQuarterState());
  }
  
  //... dispense(){} 까지



//---------------SoldOutState

public class SoldOutState implements State{

  GumballMachine gumballMachine;

  public SoldOutState(GumballMachine gumballMachine) {
      this.gumballMachine = gumballMachine;
  }

  @Override
  public void insertQuarter() {
      System.out.println("동전을 넣으셨습니다.");
      gumballMachine.setState(gumballMachine.getHasQuarterState());
  }

  @Override
  public void ejectQuarter() {
      System.out.println("동전을 넣어주세요");
  }

  @Override
  public void tumCrank() {
      System.out.println("동전을 넣어주세요");
  }

  @Override
  public void dispense() {
      System.out.println("동전을 넣어주세요");
  }
}

//------- Sold State

     public class SoldState implements State {

        public void insertQuarter() {
            System.out.println("잠깐만 기다려 주세요. 알맹이가 나가고 있습니다.");
        }
      
      @Override
      public void ejectQuarter() {
          System.out.println("이미 알멩이를 뽑으셨습니다");
      }
        
    ...  dispense(){} 까지


```

</br>
</br>

## ✅ GumballMachineTest

```java
public class GumballMachineTest {
  public static void main(String[] args) {
      GumballMachine gumballMachine = new GumballMachine(5);

      System.out.println(gumballMachine);

      gumballMachine.insertQuarter();
      gumballMachine.tumCrank();

      System.out.println(gumballMachine);

      gumballMachine.insertQuarter();
      gumballMachine.tumCrank();
      gumballMachine.insertQuarter();
      gumballMachine.tumCrank();

      System.out.println(gumballMachine);

      gumballMachine.releaseBall();

      System.out.println(gumballMachine);
  }
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
- 각 상태를 변경헤는 닫혀있게 했고, GamballMachine클래스는 새로운 상태 클래스를 </p>
  추가하는 확장에는 열려 있도록 고침 - OCP
- 주식회사 왕뽑기에서 처음 제시했던 다이어그램에 훨씬 가까우면서 더 이애하기 좋은 </p>
  코드 베이스와 클래스 구조를 만듬 </p>

</br>
</br>

## ✅ 스테이트 패턴 VS 스트래티지 패턴

</br>

<img width="847" alt="상태패턴과 스트래티지 패턴 비교" src="https://user-images.githubusercontent.com/98209409/178125697-47173943-3776-4caf-88eb-c813695dedb2.png">

출처: https://bb-dochi.tistory.com/83

</br>
</br>
