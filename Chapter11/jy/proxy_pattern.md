## 프록시 패턴

특정 객체로의 접근을 제어하는 대리인(특정 객체를 대변하는 객체)를 제공.

- 원격 프록시는 프록시 패턴을 구현할 때 자주 사용됨

![](https://velog.velcdn.com/images/jiyeong/post/2599875c-245d-4e73-bb34-35e3c0f81262/image.png)
이미지 출처 : [CodeProject](https://www.codeproject.com/Articles/472799/Proxy-Design-Pattern-in-Java-2)

<<인터페이스>> subject : proxy와 RealSubject 모두 Subject 인터페이스를 구현함. 이러면 어떤 클라이언트에서든 프록시를 주제와 똑같은 식으로 다룰 수 있음.

RealSubject : 진짜 작업을 대부분 처리하는 객체.  Proxy는 그 객체로의 접근을 제어함.
<- RealSubject의 인스턴스를 생성하거나, 그 객체의 생성 과정에 관여하는 경우가 많음.
Proxy : 진짜 작업을 처리하는 객체의 레퍼런스가 들어있음. 진짜 객체가 필요하면 그 레퍼런스를 사용해서 요청 전달.


#### 모니터링 코드 만들기

```
package headfirst.designpatterns.proxy.gumballmonitor;
 
public class GumballMonitor {
	GumballMachine machine;
 
	public GumballMonitor(GumballMachine machine) {
		this.machine = machine;
	}
 
	public void report() {
		System.out.println("Gumball Machine: " + machine.getLocation());
		System.out.println("Current inventory: " + machine.getCount() + " gumballs");
		System.out.println("Current state: " + machine.getState());
	}
}

```
#### 모니터링 기능 테스트

```
package headfirst.designpatterns.proxy.gumballmonitor;

public class GumballMachineTestDrive {

	public static void main(String[] args) {
		int count = 0;

        if (args.length < 2) {
            System.out.println("GumballMachine <name> <inventory>");
            System.exit(1);
        }

        try {
        	count = Integer.parseInt(args[1]);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		GumballMachine gumballMachine = new GumballMachine(args[0], count);

		GumballMonitor monitor = new GumballMonitor(gumballMachine);

 
		System.out.println(gumballMachine);

		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();
		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();

		System.out.println(gumballMachine);

		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();
		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();

		System.out.println(gumballMachine);

		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();
		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();

		System.out.println(gumballMachine);

		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();
		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();

		System.out.println(gumballMachine);

		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();
		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();

		System.out.println(gumballMachine);

		monitor.report();
	}
}

```
-프록시는 진짜 객체를 대신하는 역할을 맡음. 여기선 뽑기 기계 객체 역할을 맡음. 
- 프록시는 다른 객체의 **대리인** 역할.
ex) 뽑기 기계에서 프록시가 원격 객체의 접근을 제어하고 있다고 생각하면 된다, 클라이언트인 모니터링 객체와 원격 객체가 직접 데이터를 주고 받을 수 없으므로 프록시에서 접근을 제어해 줘야 한다. (원격 프록시가 접근을 제어해서 네트워크 관련 사항을 챙겨줌). 

- 원격 프록시를 써서 원격 객체로의 접근 제어
- 가상 프록시(virtual proxy)를 써서 생성하기 힘든 자원으로의 접근 제어
- 보호 프록시(protection proxy)를 써서 접근 권한이 필요한 자원으로의 접근 제어



### 원격 프록시의 역할

원격 객체의 **로컬 대변자 역할**
원격 객체란 다른 자바 가상 머신의 힙에서 살고 있는 객체(=다른 주소 공간에서 돌아가고 있는 객체)를 뜻함.
로콜 대변자란 로컬 대변자의 어떤 메소드를 호출하면, 다른 원격 객체에게 그 메소드 호출을 전달해 주는 객체를 지칭함.

클라이언트 객체는 원격 객체의 메소드 호출을 하는 것처럼 행동함.
하지만 실제로는 로컬 힙에 들어있는 '프록시' 객체의 메소드를 호출함.
네트워크 통신과 관련된 저수준 작업은 이 프록시 객체에서 처리해줌.


#### 모니터링 코드에 원격 프록시 추가하기

Duck d = <다른 힙에 있는 객체>

변수 d가 어떤 객체를 참조하든, 그 객체는 선언문이 들어있는 코드와 같은 힙 공간에 있어야만 합니다. 바로 그 부분에서 자바의 원격 메소드 호출(RMI, Remote Method Invocation)이 쓰임. RMI를 사용하면 원격 JVM에 있는 객체를 찾아서 그 메소드 호출 가능.

### 원격 메소드의 기초

로컬 객체의 메소드를 호출하면, 그 요청을 원격 객체에 전달해 주는 시스템을 디자인해야 한다고 가정. 일단 통신을 처리해 주는 보조 객체 필요!
보조 객체를 사용하면 클라이언트는 로컬 객체의 메소드만 호출하면 됨. 클라이언트 보조 객체(client helper)의 메소드를 호출하는 것.
클라이언트는 그 보조 객체가 실제 서비스를 제공한다고 생각함.
그러면 클라이언트 보조 객체가 그 요청을 원격 객체에게 전달함.
클라이언트 보조 객체가 클라이언트에서 호출하고자 하는 메소드가 들어있는 객체인 척 하기 때문!

하지만 클라이언트 보조 객체는 진짜 원격 서비스가 아님. 클라이언트에서 요구하는 실제 메소드 로직이 그 안에 들어있는 것이 아니기 때문! 클라이언트 보조 객체는 서버에 연락을 취하고, 메소드 호출에 관한 정보(메소드 이름, 인자 등)를 전달하고, 서버로부터 리턴되는 정보를 기다림.

서버는 서비스 보조 객체(service helper)가 있어서, Socket 연결로 클라이언트 보조 객체로부터 요청을 받아 오고, 호출 정보를 해석해서 진짜 서비스 객체에 있는 진짜 메소드를 호출함. 따라서 서비스 객체는 그 메소드 호출이 원격 클라이언트가 아닌 로컬 객체로부터 들어온다고 생각. 
서비스 보조 객체는 서비스로부터 리턴값을 받아서 Socket의 출력 스트림으로 클라이언트 보조 객체에게 전송함. 클라이언트 보조 객체는 그 정보를 해석해서 클라이언트 객체에 리턴. 

### 메소드 호출 과정 알아보기
1. 클라이언트 객체에서 클라이언트 보조 객체의 doBigThing()을 호출
2. 클라이언트 보조 객체는 메소드 호출 정보(인자, 메소드 이름 등)를 잘 포장해서 네트워크로 서비스 보조 객체에게 전달
3. 서비스 보조 객체는 클라이언트 보조 객체로부터 받은 정보를 해석해서 어떤 객체의 어떤 메소드를 호출할 지 알아낸 다음 진짜 서비스 객체의 메소드 호출
4. 서비스 객체의 메소드 실행이 끝나면 서비스 보조 객체에 어떤 결과 리턴
6. 서비스 보조 객체는 호출 결과로 리턴된 정보를 포장해서 네트워크로 클라이언트 보조 객체에게 전달
6. 클라이언트 보조 객체는 리턴된 정보를 해석해서 클라이언트 객체에게 리턴. 클라이언트 객체는 메소드 호출이 어디로 전달되었는지, 어디에서 리턴되었는지 전혀 알 수가 없음.

### 자바 RMI의 개요

RMI는 우리 대신 클라이언트와 서비스 보조 객체를 만들어 준다.
보조 객체에는 원격 서비스와 똑같은 메소드가 들어있음. RMI를 사용하면 네트워킹 및 입출력 관련 코드를 직접 작성하지 않아도 된다. 클라이언트는 그냥 같은 로컬 JVM에 있는 메소드를 호출하듯이 원격 메소드(진짜 서비스 객체에 있는 메소드)를 호출할 수 있음. 또한 클라이언트가 원격 객체를 찾아서 접근할 때 쓸 수 있는 룩업 서비스도 RMI에서 제공.
RMI와 로컬 메소드 호출의 차이점
클라이언트는 로컬 메소드 호출과 똑같은 식으로 메소드를 호출하지만, 실제로는 클라이언트 보조 객체가 네트워크 호출을 전송해야 하므로 네트워킹 및 입출력 기능이 반드시 필요하다.
네트워킹이나 입출력 기능을 쓸 때는 문제가 발생할 위험이 따르므로 클라이언트에서 항상 예상치 못한 상황을 대비하고 있어야 한다.

RMI 용어
RMI에서 클라이언트 보조 객체는 스텁(Stub), 서비스 보조 객체는 스켈레톤(skeleton)이라고 함.

### 원격 서비스 만들기

#### 1단계: 원격 인터페이스 만들기
원격 인터페이스는 클라이언트가 원격으로 호출할 메소드를 정의.
클라이언트에서 이 인터페이스를 서비스의 클래스 형식으로 사용함.
스텁과 실제 서비스에 이 인터페이스 구현!

01. **java.rmi.Remote를 확장.**
Remote는 표식용(marker) 인터페이스인데 메소드가 없음. 하지만 RMI에서 remote는 특별한 의미를 가지므로 반드시 확장해야함. 인터페이스를 확장해서 다른 인터페이스를 만들 수 있다는 사실을 알아두자.

```
public interface MyRemote extends Remote
//이 인터페이스에서 호출을 지원함
```

02. **모든 메소드를 RemoteException을 던지도록 선언. **
클라이언트는 서비스 원격 인터페이스 형식으로 선언해서 사용함. 
즉, 클라이언트는 원격 인터페이스를 구현하는 스텁의 메소드를 호출.
그런데 스텁이 각종 입출력 작업을 처리할 때 네트워크 등에 안 좋을 일이 일어날 수 있으므로 클라이언트는 원격 예외를 처리하거나 선언해줘야 함.
인터페이스를 정의할 때 모든 메소드에서 예외를 선언했다면, 인터페이스 형식의 레퍼런스에 관한 메소드를 호출하는 코드에서 반드시 그 예외를 처리하거나 선언해야 함.

```
import.java.rmi.*
//Remote 인터페이스는 java.rmi에 들어있음

public interface MyRemote extends Remote{
	public String sayHello() throws RemoteException;
    //모든 원격 메소드 호출은 위험이 따르는 것ㅇ로 간주해야 함.
    //모든 메소드에서 RemoteException을 선언해야 클라이언트에서 예외 상황 발생 대비 가능
}
```

03.** 원격 메소드의 인자와 리턴값은 반드시 원시 형식(primitive) 또는 Serialuzable 형식(자바 직렬화 참고)으로 선언. **원격 메소드의 인자는 모두 네트워크로 전달되어야 하며, 직렬화로 포장됨. 리턴값도 마찬가지.
원시 형식이나 String 또는 API에서 많이 쓰이는 일반적인 형식(배열, 컬렉션 등)을 사용한다면 괜찮음. 혹시 직접 만든 형식 전달 시, 클래스를 만들 때 Serializable 인터페이스도 구현해야 함.

```
public String sayHello() throws RemoteException;
//String 서버에서 클라이언트로 다시 전송해야 하므로 이 리턴값을 직렬화 할 수 있어야 함. 모든 인자 및 리턴값이 직렬화로 포장되어 전송.
```

#### 2단계 : 서비스 구현 클래스 만들기
실제 작업을 처리하는 클래스로 원격 메소드를 실제로 구현한 코드가 들어있는 부분이다. 나중에 클라이언트에서 이 객체에 있는 메소드를 호출함.

01. **서비스 클래스에 원격 인터페이스 구현.**
클라이언트가 인터페이스의 메소드를 호출.

```
public class MyRemoteImpl extends UnicatRemote implements MyRemote{
	public String sayHello(){
    //인터페이스가 들어있는 모든 메소드를 구현했는지 컴파일러에서 체크해줌. 여기에는 sayHello() 하나밖에 없음!
    	return "Server says, 'Hey'";
    }
}
```

02. UnicastRemoteObject를 확장. 
원격 서비스 객체 역할을 하려면 객체에 '원격 객체'기능을 추가해야 함.
가장 간단한 방법은 (java.rmi.server 패키지에 들어있는) UnicastRemoteObject를 확장해서, 슈퍼클래스에서 제공하는 기능으로 처리.

```
public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote{
	private static final long serialVersionUID = 1L;
    //UnicastRemoteObject는 serializable을 구현하므로 serialVersionUID 필드가 필요
}
```

03.** RemoteException을 선언하는 생성자 구현.**
슈퍼클래스 UnicastRemoteObject에는 생성자가 RemoteException을 던진다는 문제가 있음. 이 문제를 해결하려면 서비스를 구현하는 클래스에 RemoteException을 선언하는 생성자를 만들어야 한다. 어떤 클래스가 생성될 때 그 슈퍼클래스의 생성자도 반드시 호출되므로 슈퍼클래스 생성자가 어떤 예외를 던진다면 서브클래스의 생성자도 예외를 선언해야 한다.

```
public MyRemoteImpl() throws RemoteException{
//생성자에 별다른 코드를 받을 필요는 없음.
//이 생성자에서도 예외를 선언하려고 코드를 그냥 만든 거임.
}
```

04. **서비스를 RMI 레지스트리에 등록.**
원격 서비스를 원격 클라이언트에서 쓸 수 있게 만들어줘야 함.
인스턴스를 만든 다음 RMI 레지스트리에 등록하면 된다(이 클래스가 실행될 때 RMI 레지스트리가 돌아가고 있어야 함). 서비스를 구현한 객체를 등록하면 RMI 시스템은 레지스트리에 스텁만 등록함. 클라이언트는 스텁만 필요.
서비스를 등록할 때 java.rmi.Naming 클래스에 있는 rebind() 정적 메소드를 사용.

```
try{
	MyRemote service = new MyRemoteImpl();
    Naming.rebind("RemoteHello", service);
    //서비스를 등록할 때는 이름을 지정해 줘야 함.
   	//클라이언트는 그 이름으로 레지스트리를 검색. 
    //rebind() 메소드로 서비스 객체를 결합하면 RMI는 서비스에 해당하는 스텁을 레지스트리에 추가.
} catch(Exception e){}
```

3단계 : RMI 레즈스트리(rmiregistry) 실행
rmiregistry는 전화번호부와 비슷하다고 보면 됨. 클라이언트는 이 레지스트리로부터 프록시(스텁, 클라이언트 보조 객체)를 받아감.

**터미널을 새로 띄워서 rmiregistry를 실행**함. 클래스에 접근할 수 있는 디렉토리에서 실행해야 함. classes 디렉토리에서 실행하면 간단하게 처리 가능.

4단계 : 원격 서비스 실행하기
서비스 객체를 실행해야 한다. 서비스를 구현한 클래스에서 서비스의 인스턴스를 만들고 그 인스턴스를 RMI 레지스트리에 등록. 이러면 그 서비스를 클라이언트에서 사용 가능.

**다른 터미널을 열고 서비스를 실행함. **
이 작업은 원격 서비스를 구현한 클래스의 main() 메소드로 실행할 수 있지만, 별도의 클래스로부터 실행할 수도 있음. 이 예제에서 서비스를 구현한 클래스의 main() 메소드에서 바로 객체 인스턴스를 만들고 RMI 레지스트리에 등록.

#### 서버에 필요한 코드 살펴보기

클라이언트는 어떻게 스텁 객체를 가져올 수 있을까?
클라이언트는 스텁 객체(프록시)를 가져와야 함. 거기에 있는 메소드를 호출해야하기 때문! 그리고 이때 RMI 레지스트리가 활약한다.
클라이언트는 룩업(lookup)으로 스텁 객체를 요청함. 
이름을 건네주면서 그 이름에 맞는 스텁 객체를 요구한다.
스텁 객체를 룩업하고 가져오는 데 필요한 코드 살펴보기

```
MyRemote service = (MyRemote) Naming.lookuo("rmi://127.0.0.1/RemoteHello");
```

- MyRemote : 클라이언트는 항상 서비스를 원격 인터페이스 형식으로 지정. 원격 서비스를 구현한 클래스의 이름은 전혀 몰라도 됨.

- (MyRemote) : 리턴된 스텁은 인터페이스 형식으로 커스텀해야 한다. lookup() 메소드는 항상 Object 형식의 객체를 리턴하기 때문.

- .lookup = lookup()은 Naming 클래스에 들어있는 정적 메소드.

- rmi://127.0.0.1/RemoteHello : 서비스가 돌아가고 있는 시스템의 호스트 이름 또는 IP 주소

- RemoteHello : 서비스를 등록할 때 사용한 이름을 적어줘야 함.

#### 작동 방식

1. 클라이언트에서 RMI 레지스트리를 룩업
```
Naming.lookuo("rmi://127.0.0.1/RemoteHello");
```

2. RMI 레지스트리에서 스텁 객체를 리턴합니다. 스텁 객체는 lookup() 메소드의 리턴 값으로 전달되며, RMI에서는 그 스텁을 자동으로 역직렬화합니다. 
이때(rmic에서 생성해 준) 스텁 클래스는 반드시 클라이언트에만 있어야 함. 그 클래스가 없으면 역직렬화를 할 수 없음.

3. 클라이언트는 스텁의 메소드를 호출. 스텁이 진짜 서비스 객체라고 생각함.

#### 클라이언트 코드 살펴보기

RMI를 사용할 때 흔히 하는 실수
1. 원격 서비스를 돌리기 전에 rmiregistry를 실행하지 않는다.
Naming.rebind()를 호출해서 서비스를 등록할 때 RMI 레지스트리가 돌아가고 있어야 함.

2. 인자와 리턴 형식을 직렬화할 수 없게 만든다.
실행하기 전까지는 직렬화가 되지 않았다는 사실을 알 수 없음. 컴파이러에서ㅡㄴ 잡아낼 수 없는 문제이기 때문.

#### 뽑기 기계용 원격 프록시 고민하기

![](https://velog.velcdn.com/images/jiyeong/post/fcd50645-c6f2-45ab-8689-2fcebcd11e68/image.png)

출처 : 한국 오라클

#### GumballMachine 클래스를 원격 서비스로 바꾸기
```
package headfirst.designpatterns.proxy.gumball;

import java.rmi.*;
 
public interface GumballMachineRemote extends Remote {
	public int getCount() throws RemoteException;
	public String getLocation() throws RemoteException;
	public State getState() throws RemoteException;
}

```
```
package headfirst.designpatterns.proxy.gumball;

import java.io.*;
  
public interface State extends Serializable {
	public void insertQuarter();
	public void ejectQuarter();
	public void turnCrank();
	public void dispense();
}

```

```
package headfirst.designpatterns.proxy.gumball;

public class NoQuarterState implements State {
	private static final long serialVersionUID = 2L;
    transient GumballMachine gumballMachine;
 
    public NoQuarterState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }
 
	public void insertQuarter() {
		System.out.println("You inserted a quarter");
		gumballMachine.setState(gumballMachine.getHasQuarterState());
	}
 
	public void ejectQuarter() {
		System.out.println("You haven't inserted a quarter");
	}
 
	public void turnCrank() {
		System.out.println("You turned, but there's no quarter");
	 }
 
	public void dispense() {
		System.out.println("You need to pay first");
	} 
 
	public String toString() {
		return "waiting for quarter";
	}
}

```
```
package headfirst.designpatterns.proxy.gumball;

import java.rmi.*;
import java.rmi.server.*;
 
public class GumballMachine
		extends UnicastRemoteObject implements GumballMachineRemote 
{
	private static final long serialVersionUID = 2L;
	State soldOutState;
	State noQuarterState;
	State hasQuarterState;
	State soldState;
	State winnerState;
 
	State state = soldOutState;
	int count = 0;
 	String location;

	public GumballMachine(String location, int numberGumballs) throws RemoteException {
		soldOutState = new SoldOutState(this);
		noQuarterState = new NoQuarterState(this);
		hasQuarterState = new HasQuarterState(this);
		soldState = new SoldState(this);
		winnerState = new WinnerState(this);

		this.count = numberGumballs;
 		if (numberGumballs > 0) {
			state = noQuarterState;
		} 
		this.location = location;
	}
 
 
	public void insertQuarter() {
		state.insertQuarter();
	}
 
	public void ejectQuarter() {
		state.ejectQuarter();
	}
 
	public void turnCrank() {
		state.turnCrank();
		state.dispense();
	}

	void setState(State state) {
		this.state = state;
	}
 
	void releaseBall() {
		System.out.println("A gumball comes rolling out the slot...");
		if (count != 0) {
			count = count - 1;
		}
	}

	public void refill(int count) {
		this.count = count;
		state = noQuarterState;
	}
 
	public int getCount() {
		return count;
	}
 
    public State getState() {
        return state;
    }
 
    public String getLocation() {
        return location;
    }
  
    public State getSoldOutState() {
        return soldOutState;
    }

    public State getNoQuarterState() {
        return noQuarterState;
    }

    public State getHasQuarterState() {
        return hasQuarterState;
    }

    public State getSoldState() {
        return soldState;
    }

    public State getWinnerState() {
        return winnerState;
    }
 
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("\nMighty Gumball, Inc.");
		result.append("\nJava-enabled Standing Gumball Model #2014");
		result.append("\nInventory: " + count + " gumball");
		if (count != 1) {
			result.append("s");
		}
		result.append("\n");
		result.append("Machine is " + state + "\n");
		return result.toString();
	}
}

```
1. GumballMachine의 운격 인터페이스를 만듦.
이 인터페이스는 원격 클라이언트에서 호출할 수 있는 메소드 정의
2. 인터페이스의 모든 리턴 형식을 직렬화할 수 있는 지 확인
3. 구상 클래스에서 인터페이스를 구현함.

#### RMI 레지스트리 등록

```
package headfirst.designpatterns.proxy.gumball;
import java.rmi.*;

public class GumballMachineTestDrive {
 
	public static void main(String[] args) {
		GumballMachineRemote gumballMachine = null;
		int count;

		if (args.length < 2) {
			System.out.println("GumballMachine <name> <inventory>");
 			System.exit(1);
		}

		try {
			count = Integer.parseInt(args[1]);

			gumballMachine = 
				new GumballMachine(args[0], count);
			Naming.rebind("//" + args[0] + "/gumballmachine", gumballMachine);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

```

#### GumballMonitor 클라이언트 코드 고치기

```
package headfirst.designpatterns.proxy.gumball;

import java.rmi.*;
 
public class GumballMonitor {
	GumballMachineRemote machine;
 
	public GumballMonitor(GumballMachineRemote machine) {
		this.machine = machine;
	}
 
	public void report() {
		try {
			System.out.println("Gumball Machine: " + machine.getLocation());
			System.out.println("Current inventory: " + machine.getCount() + " gumballs");
			System.out.println("Current state: " + machine.getState());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}

```
#### 새로운 머니터링 기능 테스트

```
package headfirst.designpatterns.proxy.gumball;

import java.rmi.*;
 
public class GumballMonitorTestDrive {
 
	public static void main(String[] args) {
		String[] location = {"rmi://santafe.mightygumball.com/gumballmachine",
		                     "rmi://boulder.mightygumball.com/gumballmachine",
		                     "rmi://austin.mightygumball.com/gumballmachine"}; 
		
		if (args.length >= 0)
        {
            location = new String[1];
            location[0] = "rmi://" + args[0] + "/gumballmachine";
        }
		
		GumballMonitor[] monitor = new GumballMonitor[location.length];
		
		
		for (int i=0;i < location.length; i++) {
			try {
           		GumballMachineRemote machine = 
						(GumballMachineRemote) Naming.lookup(location[i]);
           		monitor[i] = new GumballMonitor(machine);
				System.out.println(monitor[i]);
        	} catch (Exception e) {
            	e.printStackTrace();
        	}
		}
 
		for (int i=0; i < monitor.length; i++) {
			monitor[i].report();
		}
	}
}

```
#### 데모 버전 돌려보기
프록시에 있는 메소드 호출 시 네트워크로 메소드 호출이 전달됨

#### 프록시가 원격으로 일을 처리할 때까지

1. CEO가 모니터링을 시작하면, GumballMonitor는 우선 뽑기 기계 원격 객체의 프록시를 가져온 다음 getState()와 getCount(), getLocation() 호출

2. 프록시의 getState()가 호출되면 프록시는 그 호출을 원격 서비스로 전달함. 스켈레톤은 그 요청을 받아서 뽑기 기계에게 전달.

3. GumballMachine은 스켈레톤에게 상태를 리턴함. 그러면 스켈레톤은 리턴값을 직렬화한 다음 네트워크로 프록시에게 전달. 프록시는 리턴값을 역직렬화해서 GumballMonitor에게 리턴

### 원격 프록시와 가상 프록시 비교

#### 원격 프록시

다른 JVM에 들어있는 객체의 대리인에 해당하는 로컬 객체로 프록시의 메소드를 호출하면 그 호출이 네트워크로 전달되어 결국 원격 객체의 메소드가 호출됨. 그리고 그 결과는 다시 프록시를 거쳐 클라이언트에게 전달된다.

#### 가상 프록시
생성하는 데 많은 비용이 드는 객체를 대신함. 진짜 객체가 필요한 상황이 오기 전까지 객체의 생성을 미루는 기능 제공. 객체 생성 전이나 객체 생성 도중에 객체를 대신하기도 한다. 객체 생성이 끝나면 그냥 RealSubject에 직접 요청 전달.

#### 앨범 커버 가상 프록시 설계

가상 프록시는 네트워크로 연결되어 있는 많은 비용이 드는 객체를 숨기는 용도로 사용됨.

1. ImageProxy는 ImageIcon을 생성하고 네트워크 URL로부터 이미지를 불러옴
2. 이미지를 가져오는 동안 메세지 표시
3. 이미지 로딩이 끝나면 paintIcon(), getWidth(), getHeight()를 비롯한 모든 메소드 호출을 이미지 아이콘 객체에 넘김.
4. 새로운 이미지 요청이 들어오면 프록시를 새로 만들고 위의 과정 처음부터 반복


#### ImageProxy 코드 만들기
```
package headfirst.designpatterns.proxy.virtualproxy;

import java.net.*;
import java.awt.*;
import javax.swing.*;

class ImageProxy implements Icon {
	volatile ImageIcon imageIcon;
	final URL imageURL;
	Thread retrievalThread;
	boolean retrieving = false;
     
	public ImageProxy(URL url) { imageURL = url; }
     
	public int getIconWidth() {
		if (imageIcon != null) {
            return imageIcon.getIconWidth();
        } else {
			return 800;
		}
	}
 
	public int getIconHeight() {
		if (imageIcon != null) {
            return imageIcon.getIconHeight();
        } else {
			return 600;
		}
	}
	
	synchronized void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}
     
	public void paintIcon(final Component c, Graphics  g, int x,  int y) {
		if (imageIcon != null) {
			imageIcon.paintIcon(c, g, x, y);
		} else {
			g.drawString("Loading album cover, please wait...", x+300, y+190);
			if (!retrieving) {
				retrieving = true;
				
				retrievalThread = new Thread(new Runnable() {
					public void run() {
						try {
							setImageIcon(new ImageIcon(imageURL, "Album Cover"));
							c.repaint();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
				retrievalThread = new Thread(() -> {
						try {
							setImageIcon(new ImageIcon(imageURL, "Album Cover"));
							c.repaint();
						} catch (Exception e) {
							e.printStackTrace();
						}
				});
				retrievalThread.start();
				
			}
		}
	}
}

```
#### 앨범 커버 뷰어 테스트

테스트해 봐야 하는 내용
- 메뉴로 다른 앨범 커버 불러오기. 이미지 로딩이 완료될 때까지 프록시에서 불러오는 중이라는 메시지를 보여 주는지 확인
- 불러오는 중이라는 메시지가 화면에 표시된 상태에서 위녿우 크기를 조절해 보기. 프록시에서 이미지를 로딩하고 있을 때도 스윙 윈도우가 멈추지 않는 지 확인.
- ImageProxyTestDrive에 좋아하는 앨범을 추가해보기
```
package headfirst.designpatterns.proxy.virtualproxy;

import java.net.*;
import javax.swing.*;
import java.util.*;

public class ImageProxyTestDrive {
	ImageComponent imageComponent;
	JFrame frame = new JFrame("Album Cover Viewer");
	JMenuBar menuBar;
	JMenu menu;
	Hashtable<String, String> albums = new Hashtable<String, String>();

	public static void main (String[] args) throws Exception {
		ImageProxyTestDrive testDrive = new ImageProxyTestDrive();
	}

	public ImageProxyTestDrive() throws Exception {
		albums.put("Buddha Bar","http://images.amazon.com/images/P/B00009XBYK.01.LZZZZZZZ.jpg");
		albums.put("Ima","http://images.amazon.com/images/P/B000005IRM.01.LZZZZZZZ.jpg");
		albums.put("Karma","http://images.amazon.com/images/P/B000005DCB.01.LZZZZZZZ.gif");
		albums.put("MCMXC a.D.","http://images.amazon.com/images/P/B000002URV.01.LZZZZZZZ.jpg");
		albums.put("Northern Exposure","http://images.amazon.com/images/P/B000003SFN.01.LZZZZZZZ.jpg");
		albums.put("Selected Ambient Works, Vol. 2","http://images.amazon.com/images/P/B000002MNZ.01.LZZZZZZZ.jpg");

		URL initialURL = new URL((String)albums.get("Selected Ambient Works, Vol. 2"));
		menuBar = new JMenuBar();
		menu = new JMenu("Favorite Albums");
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);

		for (Enumeration<String> e = albums.keys(); e.hasMoreElements();) {
			String name = (String)e.nextElement();
			JMenuItem menuItem = new JMenuItem(name);
			menu.add(menuItem); 
			menuItem.addActionListener(event -> {
				imageComponent.setIcon(new ImageProxy(getAlbumUrl(event.getActionCommand())));
				frame.repaint();
			});
		}

		// set up frame and menus

		Icon icon = new ImageProxy(initialURL);
		imageComponent = new ImageComponent(icon);
		frame.getContentPane().add(imageComponent);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,600);
		frame.setVisible(true);

	}

	URL getAlbumUrl(String name) {
		try {
			return new URL((String)albums.get(name));
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
}

```

#### 앨범 커버를 불러올 때까지

1. 화면에 이미지를 표시하는 ImageProxy를 만들었음. paintcon()메소드가 호출되면 ImageProxy에서 이미지를 가져오고 ImageIcon 객체를 생성하는 스레드 시작

2. 잠시 후, 이미지가 리턴되고 ImageIcon의 인스턴스를 만드는 작업 완료

3. ImageIcon 생성이 완료된 후 paintIcon()이 호출되면 프록시에서 그 호출을 곧바로 ImageIcon 객체에게 넘김

클라이언트에서 진짜 객체가 아닌 프록시를 사용하도록 만드는 방법 :
가장 흔히 쓰이는 기법은 진짜 객체의 인스턴스를 생성해서 리턴하는 팩토리 사용. 이 작업은 팩토리 메소드 내에서 진행되므로 객체를 프록시로 감싼 다음에 리턴 가능. 그러면 클라이언트는 진짜 객체를 쓰고 있는지, 프록시 객체를 쓰는 지 전혀 알지 못 함.

전에 가져왔던 이미지를 캐시에 저장해두는 ImageProxy와 비슷한 객체를 구현하기 :
캐싱 프록시는 기존에 생성했던 객체들을 캐시에 저장해뒀다가, 요청이 들어왔을 때  캐시에 저장되어 있는 객체 리턴 가능. 

프록시 패턴은 주제(Subject)를 대신하는 패턴으로 원치 않는 접근으로부터 보호해 주거나 커다란 객체를 로딩하는 동안 GUI가 멈추는 것을 방지해 주거나 주제가 원격 시스템에서 돌아가고 있다는 사실을 숨겨 주는 일을 함. 

#### 보호 프록시 만들기
p.503

자바의 java.lang.reflect 패키지 안에 프록시 기능이 내장되어 있으며, 즉석에서 하나 이상의 인터페이스를 구현하고, 지정한 클래스에 메소드 호출을 전달하는 프록시 클래스를 만들 수 있음.진짜 프록시 클래스인** 동적 프록시(dynamic proxy)**는 실행 중에 생성됨.

자바에서 Proxy 클래스가 Subject 인터페이스 전체를 구현함.
Proxy 객체의 모든 메소드 호출을 전달 받는 InvocationHandler을 제공. InvocationHandle에서 RealSubject 객체에 있는 메소드로의 접근 제어.

자바에서 Proxy 클래스를 생성해주므로 Proxy 클래스에게 무슨  일을 해야 하는지 알려 줄 방법 필요. InvocationHandle에 넣어서 프록시에 호출되는 모든 메소드에 응답하기.
Proxy에서 메소드 호출을 받으면 항상 InvocationHandle에 진짜 작업을 부탁한다고 생각.

#### 객체 마을 데이팅 서비스
```
package headfirst.designpatterns.proxy.javaproxy;

public interface Person {
 
	String getName();
	String getGender();
	String getInterests();
	int getGeekRating();
 
    void setName(String name);
    void setGender(String gender);
    void setInterests(String interests);
    void setGeekRating(int rating); 
 
}

```
#### Person 인터페이스 코드 만들기
```
package headfirst.designpatterns.proxy.javaproxy;

public class PersonImpl implements Person {
	String name;
	String gender;
	String interests;
	int rating;
	int ratingCount = 0;
  
	public String getName() {
		return name;	
	} 
  
	public String getGender() {
		return gender;
	}
  
	public String getInterests() {
		return interests;
	}
   
	public int getGeekRating() {
		if (ratingCount == 0) return 0;
		return (rating/ratingCount);
	}
  
 
	public void setName(String name) {
		this.name = name;
	}
 
	public void setGender(String gender) {
		this.gender = gender;
	} 
  
	public void setInterests(String interests) {
		this.interests = interests;
	} 
  
	public void setGeekRating(int rating) {
		this.rating += rating;	
		ratingCount++;
	}

}

```
Person 인터페이스에게 클라이언트에서 아무 메소드나 마음대로 호출할 수 있도록 만들지 못 하게 보호 프록시(Protection Proxy) 사용하기.

보호 프록시는 접근 권한을 바탕으로 객체로의 접근을 제어하는 프록시이다. 

#### Person 인터페이스용 동적 프록시 만들기
1. 2개의 InvocationHandler 만들기
InvocationHandler는 프록시의 행동을 구현.  프록시 클래스와 객체를 만드는 일은 자바에서 알아서 해 주기에 프록시의 메소드가 호출되었을 때 할 일을 지정해 주는 핸들러만 만들면 됨.
<<인터페이스>>InvocationHandler - invoke()

1) 프록시의 setGreekRating() 메소드가 호출된 경우
2) 프록시는 InvocationHandler의 invoke() 호출
3) 핸들러에서 주어진 요청을 어떻게 처리할 지 결정한 다음 상황에 따라 RealSubject에 그 요청을 전달할 수 있음.

#### 호출 핸들러 만들기

```
package headfirst.designpatterns.proxy.javaproxy;
 
import java.lang.reflect.*;
 
public class OwnerInvocationHandler implements InvocationHandler { 
	Person person;
 
	public OwnerInvocationHandler(Person person) {
		this.person = person;
	}
 
	public Object invoke(Object proxy, Method method, Object[] args) 
			throws IllegalAccessException {
  
		try {
			if (method.getName().startsWith("get")) {
				return method.invoke(person, args);
   			} else if (method.getName().equals("setGeekRating")) {
				throw new IllegalAccessException();
			} else if (method.getName().startsWith("set")) {
				return method.invoke(person, args);
			} 
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } 
		return null;
	}
}

```

2. 동적 프록시 생성 코드 만들기
프록시 클래스를 생성하고 그 인스턴스를 만드는 코드 필요. 

Person 객체를 인자로 받고 본인용 프록시를 만드는 메소드 만들기.
메소드 호출을 OwnerInvocationHandler에게 넘겨주는 프록시.

**데이팅 서비스 코드 테스트**
```
package headfirst.designpatterns.proxy.javaproxy;

import java.lang.reflect.*;
import java.util.*;

public class MatchMakingTestDrive {
	HashMap<String, Person> datingDB = new HashMap<String, Person>();
 	
	public static void main(String[] args) {
		MatchMakingTestDrive test = new MatchMakingTestDrive();
		test.drive();
	}
 
	public MatchMakingTestDrive() {
		initializeDatabase();
	}

	public void drive() {
		Person joe = getPersonFromDatabase("Joe Javabean"); 
		Person ownerProxy = getOwnerProxy(joe);
		System.out.println("Name is " + ownerProxy.getName());
		ownerProxy.setInterests("bowling, Go");
		System.out.println("Interests set from owner proxy");
		try {
			ownerProxy.setGeekRating(10);
		} catch (Exception e) {
			System.out.println("Can't set rating from owner proxy");
		}
		System.out.println("Rating is " + ownerProxy.getGeekRating());

		Person nonOwnerProxy = getNonOwnerProxy(joe);
		System.out.println("Name is " + nonOwnerProxy.getName());
		try {
			nonOwnerProxy.setInterests("bowling, Go");
		} catch (Exception e) {
			System.out.println("Can't set interests from non owner proxy");
		}
		nonOwnerProxy.setGeekRating(3);
		System.out.println("Rating set from non owner proxy");
		System.out.println("Rating is " + nonOwnerProxy.getGeekRating());
	}

	Person getOwnerProxy(Person person) {
 		
        return (Person) Proxy.newProxyInstance( 
            	person.getClass().getClassLoader(),
            	person.getClass().getInterfaces(),
                new OwnerInvocationHandler(person));
	}

	Person getNonOwnerProxy(Person person) {
		
        return (Person) Proxy.newProxyInstance(
            	person.getClass().getClassLoader(),
            	person.getClass().getInterfaces(),
                new NonOwnerInvocationHandler(person));
	}

	Person getPersonFromDatabase(String name) {
		return (Person)datingDB.get(name);
	}

	void initializeDatabase() {
		Person joe = new PersonImpl();
		joe.setName("Joe Javabean");
		joe.setInterests("cars, computers, music");
		joe.setGeekRating(7);
		datingDB.put(joe.getName(), joe);

		Person kelly = new PersonImpl();
		kelly.setName("Kelly Klosure");
		kelly.setInterests("ebay, movies, music");
		kelly.setGeekRating(6);
		datingDB.put(kelly.getName(), kelly);
	}
}

```

3. 적절한 프록시로 Person 객체 감싸기
Person 객체를 사용하는 객체는 고객 자신의 객체, 아니면 다른 고객의 객체 둘 중 하나. 어떤 경우든 해당 Person에 따라서 적절한 프록시 생성.


### 실전! 여러 프록시 구경

**방화벽 프록시**(Firewall Prxoy) : 일련의 네트워크 자원으로의 접근을 제어함으로써 주제를 나쁜 클라이언트로부터 보호

**스마트 레퍼런스 프록시**(Smart Reference Proxy)는 주제가 참조될 때마다 추가 행동 제공. 객체의 레퍼런스 개수를 세는 등

**캐싱 프록시(**Cashing Proxy) : 비용이 많이 드는 작업의 결과 임시 저장. 여러 클라이언트에서 결과를 공유하게 해 줌으로써 계산 시간과 네트워크 지연을 줄여주는 효과도 있음

**동기화 프록시**(Synchronization Proxy) : 여러 스레드에서 주제에 접근할 때 안전하게 작업 처리

**복잡도 숨김 프록시**(Complextiy Hiding Proxy) : 복잡한 클래스의 집합으로의 접근 제어, 그 복잡도를 숨겨 줌. 퍼사드 프록시(Facade Proxy)라고도 함. 이 프록시와 퍼사드 패턴의 차이점은 프록시는 접근을 제어하지만 퍼사드 패턴은 대체 인터페이스만 제공.

**지연 복사 프록시**(Copy-On-Write Proxy) : 클라이언트에서 필요로 할 때까지 객체가 복사되는 것을 지연시킴으로써 객체의 복사를 제어함. 변형된 가상 프록시.

## 핵심 정리

- 프록시 패턴을 사용하면 어던 객체의 대리인을 내세워서 클라이언트의 접근 제어 가능. 접근을 관리하는 방법은 여러 가지.
- 원격 프록시는 클라이언트와 원격 객체 사이의 데이터 전달 관리.
- 가상 프록시는 인스턴스를 만드는 데 많은 비용이 드는 객체로의 접근 제어.
- 보호 프록시는 호출하는 쪽의 권한에 따라서 객체에 있는 메소드로의 접근 제어.
- 그 외에도 캐싱 프록시, 동기화 프록시, 방화벽 프록시, 지연 복사 프록시와 같이 다양한 변형된 프록시 패턴 존재.
- 프록시 패턴의 구조는 데코레이터 패턴의 구조와 비슷하지만 그 용도는 다름.
- 데코레이터 패턴은 객체에 행동을 추가하지만 프록시 패턴은 접근 제어.
- 자바에 내장된 프록시 지원 기능을 사용하면 동적 프록시 클래스를 만들어서 원하는 핸들러에서 호출을 처리하도록 할 수 있음.
- 다른 래퍼를 쓸 때와 마찬가지로 프록시를 쓰면 디자인에 포함되는 클래스와 객체의 수가 늘어남.
