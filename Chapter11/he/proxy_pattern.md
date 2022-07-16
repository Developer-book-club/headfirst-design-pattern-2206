# 프록시(proxy) 패턴 이해하기 :star2:
 
 </br>
 </br>
 
 
## 🎯 의도 
> 프록시 : 대리인 , 즉 무언가를 대신 처리하는 의미 -> 비서 </p>
> 사장님한테 물어보기 전에 비서에게 물어보세요! </p>
> 어떤 객체를 사용하고자 할때 객체를 직접적으로 참조하는 것이 아니라 해당 객체를 대행하는 객체를 통해 대상 객체에 접근하는 방식 </p>
> 이를 통해 해당 객체가 메모리에 존재하지 않아도 기본적 정보를 참조하거나 설정 할 수 있으며 </p>
> 또한 실제 객체의 기능이 반드시 필요한 시점까지 객체의 생성을 미룰 수 있다. </p>
 
 </br>
 </br>
 


## ✅ RMI

<img width="637" alt="RMI이해" src="https://user-images.githubusercontent.com/98209409/179329763-562baf63-b500-4bff-8e5a-1c3d64101794.png">

출처: https://bb-dochi.tistory.com/84
 
</br>
 
> 클라이언트 객체는 원격 객체의 메소드 호출한다고 생각하고 작업 처리한다 </p>
> 하지만 실제로는 로컬 힙에 들어있는 프록시 객체(클라이언트 보조 객체)의 메소드를 호출하고 있다. </p>
> 프록시는 메소드 호출정보(인자, 메소드 이름 등)를 잘 포장해서 네트워크로 서비스 보조 객체에게 전달한다.</p>
> 서버 쪽에선 서비스 보조 객체가 요청을 받고 해석해 실제 서비스 객체에 있는 메소드 호출, 리턴값(포장된 결과)을 전송한다. </p>

</br>

자바RMI
> 클라이언트 서비스 보조 객체 ( = RMI 스텁 ) + 서비스 보조 객체 ( = RMI 스켈레톤) 을 만들어줌 </p>
> 사용시 네트워킹 및 입출력 관련 코드를 직접 작성하지 않아도 됨 </p>
> 클라이언트에서 원격 객체에 접근할 수 있는 룩업 서비스 제공 </p>
> 클라이언트 입장에선 로컬 메소드 호출과 동일 방식으로 사용하면 되지만, 실제론 네트워킹 및 입출력 기능이 활용되고 있으므롤 예외처리 중요 </p>

</br>
</br>

## ✅ 원격 서비스 만들기 

</br>

### 1단계 : 원격 인터페이스 만들기
> 클라이언트에서 원격으로 호출할 수 있는 메소드 정의  </p>
> 클라이언트에서 이 인터페이스를 서비스 클래스 형식으로 사용 -> 스텁과 실제 서비스에 이 인터페이스 구현해야 함  </p>
</br>

>> 1. java.rmi.Remote를 반드시 확장하여 원격 인터페이스 정의. </p>
>> Remote는 표식용(marker) 인터페이스나 메소드가 없지만 RMI에서는 특별!  </p>
>> 2. 모든 메소드를 RemoteException을 던지도록 선언 - 네트워킹, 입출력 작업 중 생길 수 있는 예외 처리를 위함 </p>
>> 3. 원격 메소드의 인자와 리턴값은 반드시 원시 형식(primitive) 또는 Serializable 형식으로 선언 </p>
>> 메소드의 인자들은 네트워크를 통해 전달되어 직렬화를 통해 포장될 것 </p>
>> 즉 원시 형식이 아닌 직접 만든 형식을 사용한다면 클래스를 만들 때 Serialize 인터페이스 구현 </p>

</br>

### 2단계 : 서비스 구현 클래스 생성
> 실제 작업을 하는 클래스, 나중에 클라이언트에서 이 객체에 있는 메소드 호출 ex) GumballMachine </p>
> 1번의 인터페이스를 구현해야 함 </p>
</br>

>> 1. 서비스 클래스에 원격 인터페이스 구현 <- 클라이언트가 인터페이스의 메소드를 호출할 것임 </p>
>> 2. UnicastRemoteObject를 확장 -> 원격 서비스 객체 역할을 하려면 객체에 '원격 객체'기능을 추가해야함 </p>
>> 가장 간단한 방법이 java.rmi.server 패키지에 있는 UnicastRemoteObject를 확장해서 슈퍼 클래스 제공하는 기능으로 처리하기 위함 </p>
>> 3. RemoteException을 선언하는 생성자 구현 </p>
>> 어떤 클래스가 생성될 때 그 슈퍼클래스의 생성자도 반드시 호출되므로 슈퍼클래스 생성자가 어떤 예외를 던진다면 </p>
>> 서브 클래스 생성자도 그 예외를 선언해야 한다. ex) public MyRemoteImpl() throws RemoteException{} </p>
>> 4. 서비스를 RMI 레지스트리에 등록 </p>
>> java.rmi.Naming에 있는 rebind()메소드를 통해 등록 </p>
>> RMI 레지스트리를 통해 서비스를 검색할 때 여기서 등록한 이름으로 사용 </p>

</br>

### 3단계 : RMI 레지스트리 (rmiregistry) 실행하기
> 전화번호부와 비슷, 클라이언트는 이 레지스트리로부터 프록시(스텁, 클라이언트 보조 객체)를 받아 감 </p>
> rmiregistry 명령어 새 터미널 창에서 실행 </p>

</br>

### 4단계 : 원격 서비스 실행
> 서비스를 구현한 클래스에서 서비스 인스터스를 만들고 그 인스턴스를 RMI 레지스트리에 등록 => 클라이어트에서 사용 가능해짐 </p>
> 스텁과 스켈레톤은 보이지 않는 곳에서 동적으로 생성된다. </p>
> java MyServiceImp1 다른 터미널 열어 실행 </p>

</br>

### 원격 서비스 시작
> 서비스를 구현한 클래스의 인스턴스를 만들고 RMI 레지스트리에 등록
> 등록 후엔 클라이언트에서 사용 가능

</br>
</br>

## ✅ 원격 프록시

</br>
> 원격 객체(다른 JVM에 들어있는 객체)에 대한 접근을 제어 할 수 있다. </p>
> 원격 프록시의 메소드를 호출하게 되면 네트워크를 통해 전달되어 원격 객체의 메소드가 호출되며 이러한 결과는 다시 클라이언트에게 전달됩니다.

</br>

<img width="700" alt="원격프록시" src="https://user-images.githubusercontent.com/98209409/179258892-473661e3-55a8-4916-afed-8262280e9a39.png">

출처 : https://plposer.tistory.com/31    </p>

</br>
</br>


## ✅ 예제 : GumballMachine 클래스를 원격 서비스로 바꾸기 

1. GumballMachine용 원격 인터페이스 정의 </p>
> 원격 클라이언트에서 호출 할 수 있는 메소드 정의 </p>
</br>

```java

import java.rmi.*; // 빼먹지 말자!

//원격 인터페이스
public interface GumballMachineRemote extends Remote{
    public int getCount() throws RemoteException;
    public String getLocation() throws RemoteException;
    public State getState() throws RemoteException;
    //모든 리턴 형식은 원시 형식 또는 Serializable이어야 한다.
    // 지원해야 하는 메소드 모두 RemoteException을 던질 수 있다.
}

 ```
</br> 

2. 인터페이스의 모든 리턴 형식을 직렬화 할 수 있는지 확인 </p>
> State 형식은 직렬화가 안되므로 수정 - > Serializable 인터페이스를 확장해 해결 </p>
> State 클래스에 GumballMachine에 대한 레퍼런스가 있는데 이를 포함해 직렬화 하는것은 바람직 하지 않음 </p>
> -> 각 State에 있는 GumballMachine 인스턴스 변수에 transient 키워드 추가 </p>
> -> 해결시, 해당 필드는 직렬화 되지 않으나 직렬화해서 받은 뒤 호출하면 문제가 발생 할 수도 있다. </p>

</br>

```java

import java.io.*; //Serializable은 java.io 패키지에 들어있다

public interface State extends Serializable{ //아무 메소드도 없는 Serializable 인터페이스를 확장
                                             // 이러면 State의 서브 클래스를 직렬화해서 네트워크 전송 가능
  public void insertQuarter();
  public void ejectQuarter();
  public void turnCrank();
  public void dispense();
  
}


public class NoQuarterState implements State{

    private static final long serialVersionUID = 2L;
    //transient 키워드로 해당 필드는 직렬화 되지 않도록 함
    transient GumballMachine gumballMachine;
    
    //기타 메소드..
}

```

</br> 

3. 구상 클래스에서 인터페이스를 구현 
> GumballMachine이 서비스 역할과 네트워트 요청을 처리 할 수 있도록 수정  </p>

```java

import java.rmi.*;    // 임포트
import java.rmi.server.*;

public class GumballMachine 
   extends UnicastRemoteObject implements GumballMachineRemote 
   //GumballMachine 클래스를 UnicastRemoteObject의 서브 클래스로 만들어야 원격 서비스 역할을 할 수 있다.
{
    private static final long serialVersionUID = 2L;
    //기타 인스턴스 변수 ..
   
    public GumballMachine(String location, int numberGumballs) throws RemoteException{
        //슈퍼클래스에서 RemoteException 를 던질 수 있기 때문에
        //해당 생성자에서도 던질 수 있어야 한다.
        
        //생성자 코드
    }
    
    public int getCount() {
      return count;
    }
    
    public int getState() {
      return state;
    }

    public int getLocat() {
      return location;
    }
    
    //기타 메소드 ..
    
```

</br>
</br>

4. RMI 레지스트리 등록
</br>

```java

public class GumballMachineTestDrive(){

public static void main(String[] args){

...

  try { // 뽑기 기계의 인스턴스를 만드는 부분을 try/catch 블록으로 감싸야 한다. 생성자가 예외를 던질 수도 있음
    count = Integer.parseInt(args[1]);
    
    gumballMachine = new GumballMachine(args[0], count);
    Naming.rebind("//" + args[0] + "/gumballmachine", gumballMachine);
    //Naming.rebind 메소드를 호출, GumballMachine 스텁을 gumballmachine 이라는 이름으로 등록한다.
  }catch (Exception e ){  
    e.printStackTrace();
  }
 }
}

```
</br>

5. 테스트 실행 </p>
> 1. rmiregistry 실행 </p>
> 2. java GumballMachineTestDrive austin.mightgumball.com 100 </p>

</br>
</br>

6. GumballMonitor 클라이언트 코드 고치기

</br>

```java

import java.rmi.*; //RemoteException 클래스 사용하므로 RMI 패키지 임포트

public class GumballMonitor{
  GumballMachineRemote machine;
  // 이제 GumballMachine 구상클래스 대신 원격 인터페이스 사용
  
  public GumballMonitor(GumballMachineRemote machine){
    this.machine = machine;
  
  }
  
  public void report(){
     try{
       system.out.printlm("뽑기 기계 위치:" + machine.getLocation());
       system.out.printlm("현재 재고 상태:" + machine.getCount() + "gumballs" );
       system.out.printlm("뽑기 상태:" + machine.getState());
     }catch (RemoteException e) {
       e.printStackTrace(); //이제 메소드를 네트워크로 호출하므로 RemoteException이 던져질 때 잡아낼 수 있어야 한다.
     }
  }
}

```

## ✅ 가상 프록시
> 생성하기 힘든 자원에 대해 접근을 제어 할 수 있다. </p>
> 실제 객체의 사용 시점을 제어 할 수 있다. 클라이언트가 처음 요청 및 접근 할 때만 실체 객체가 생성되며 </p>
> 이후는 프록시를 참조하여 실제 객체를 대신 할 수 있다. </p>
> 객체 생성이 완료 되면 그냥 RealSubject 요청을 직접 전달 </p>

</br>

<img width="700" alt="가상프록시" src="https://user-images.githubusercontent.com/98209409/179261011-19c9002a-4849-41b2-acb6-9ce1a3969dec.png">


</br>
</br>

## ✅ 보호 프록시 
> 접근 권한이 필요한 자원에 대한 접근을 제어 할 수 있다. </p>
> java.lang.reflect 패키지에 프록시 기능이 내장되어있다. </p>
> 이 패키지를 통해 즉석에서 한 개 이상의 인터페이스를 구현하고 메소드 호출을 지정한 클래스로 전달하는 프록시를 생성가능 </p>
> 실제 프록시 클래스는 실행중에 생성되므로 이런 기술을 동적 프록시(dynamic proxy)라고 함 </p>

</br>

```java

//PersonBean 객체를 인자로 받고, 프록시를 리턴함
PersonBean getOwnerProxy(PersonBean person){
    return (PersonBean) Proxy.newProxyInstance(
        person.getClass().getClassLoader(),
        person.getClass().getInterfaces(),
        new OwnerInvocationHandler(person));
    //newProxyInstance()로 프록시를 생성
    //프록시에서 구현해야하는 클래스로더, 인터페이스를 인자로 전달
    //호출핸들러도 전달, 핸들러의 인자로 person을 전달
}

```

</br>
</br>

## ✅ 그 외 여러 프록시
> 방화벽 프록시, 스마트 레퍼런스 프록시, 캐싱 프록시, 동기화 프록시, 복잡도 숨김 프록시, 지연 복사 프록시


</br>
</br>
