
<h1>Command Pattern</h1>
메소드 호출을 캡슐화하기! <br>
커맨드 패턴을 사용하면 요청 내역을 객체로 캡슐화해서 객체를 서로 다른 요청 내역에 따라 매개변수화할 수 있음. <br>
-> 이러면 요청을 큐에 저장하거나 로그로 기록하거나 작업 취소 기능을 사용할 수 있음

- 일련의 행동을 특정 리시버와 연결함으로써 요청을 캡슐화한 것.
![](https://velog.velcdn.com/images/jiyeong/post/f333f1d8-ce2d-4024-9365-156de590620e/image.png)

<h3>Invoker Loading</h3>
1. 클라이언트에서 커맨드 객체 생성
2. setCommand()를 호출해서 인보커에 커맨드 객체 저장
3. 나중에 클라이언트에서 인보커에게 그 명령을 실행하라고 요청
* 참고 : 일단 어떤 명령을 인보커에 로딩한 다음 한 번만 작업을 처리하고 커맨드 객체를 지우도록 할 수도 있고, 저장해 둔 명령을 여러 번 수행하게 할 수도 있음.

1) Client > createCommandObject()
클라이언트는 커맨드 객체를 생성해야 함. 커맨드 객체는 리시버에 전달할 일련의 행동으로 구성됨.
- createCommandObject() : 커맨드 객체에는 행동과 리시버(Receiver)의 정보가 같이 들어 있음.

2) Command > execute() 
커맨드 객체에서 제공하는 메소드는 execute()  하나 뿐. 
이 메소드는 행동을 캡슐화하여, 리시버에게 있는 특정 행동을 처리함.
- setCommand() 

3) Invoker > setCommand() 
클라이언트는 인보커 객체의 setCommand() 메소드를 호출해 이때 커맨드 객체를 넘겨준다. 그 커맨드 객체는 나중에 쓰이기 전까지 인보커 객체에 보관됨.

- command > execute()
인보커에서 커맨드 객체의 execute() 메소드를 호출하면
- receiver > action1(), action2()
리시버에 있는 행동 메소드가 호출 됨

<h2>첫 번째 커맨드 객체 만들기</h2>

<h3>커맨드 인터페이스 구현</h3>
```
public interface Command{
	public void execute();
}
```

<h4>조명을 켤 때 필요한 커맨드 클래스 구현</h4>
```
public class LightOnCommand implements Command{
	Light light;
    
    public LightOnCommand(Light light){
    	this.light = light;
    }
    //생성자에 이 커맨드 객체로 제어할 특정 조명의 정보가 전달됨. 
    //그 객체는 light라는 인스턴스 변수에 저장이 되고, 
    //execute() 메소드가 호출되면 light 객체가 그 요청의 리시버가 됨.
    
    public void execute(){
    	light.on();
    }
}
```

<h3>커맨드 객체 사용하기</h3>
```
public class SimpleRemoteControl{
	Command slot;
    public SimpleRemoteControl(){}
    //커맨드를 저장할 슬롯이 1개가 있고, 이 슬롯으로 1개의 기기를 제어함
    
    public void setCommand(Command command){
    	slot = command;
    }
    //슬롯을 가지고 제어할 명령을 설정하는 메소드
    //리모컨 버튼의 기능을 바꾸고 싶다면 이 메소드를 사용해 바꿀 수 있음
    
    public void buttonWasPressed(){
    	slot.execute();
    }
    //버튼을 누르면 이 메소드가 호출됨
    //지금 슬롯에 연결된 커맨드 객체의 execute() 메소드만 호출 가능
}
```

<h3>리모컨을 사용할 때 필요한 간단한 테스트 클래스</h3>
```
public class RemoteControlTest{
//커맨드 페턴에서 클라이언트에 해당하는 부분
	public static void main(String[] args){
    	SimpleRemoteControl remote = new SimpleRemoteControl();
        //remote 변수가 인보커 역할을 함. 
        //필요한 작업을 요청할 때 사용할 커맨드 객체를 인자로 받음.
        Light light = new Light();
        //요청을 받아서 처리할 리시버인 Light 객체 생성
        LightOnCommand lightOn = new LightOnCommand(light);
        //커맨드 객체 생성. 이때 리시버 전달.
        
        remote.setCommand(lightOn); 
        //커맨드 객체를 인보커에 전달.
        remote.buttonWasPressed();
    }
}
```
### 커맨드 패턴 클래스 다이어그램 살펴보기

![](https://velog.velcdn.com/images/jiyeong/post/793bfbd3-cfc9-4400-a5a8-368b6f3ff4a5/image.png)


Client : 클라이언트는 ConcreteCommand를 생성하고 Receiver를 설정함.

Invoker : 인보커에는 명령이 들어있으며, execute() 메소드를 호출함으로써 커맨드 객체에게 특정 작업을 수행해 달라는 요구를 하게 된다.

<<인터페이스>> Command : Command는 모든 커맨드 객체에서 구현해야 하는 인터페이스로 모든 명령은 execute() 메소드 호출로 수행되며, 이 메소드 리시버에 특정 작업을 처리하라는 지시를 전달함. 이 인터페이스를 보면 undo() 메소드도 들어있음.

Receiver : 리시버는 요구 사항을 수행할 때 어떤 일을 처리해야 하는 지 알고 있는 객체

ConcreteCommand : 특정 행동과 리시버를 연결해 줌. 인보커에서 execute() 호출로 요청하면 ConcreteCommand 객체에서 리시버에 있는 메소드를 호출해서 그 작업을 처리함

execute() 메소드에서는 리시버에 있는 메소드를 호출해서 요청된 작업 수행

#### 리모컨 코드 만들기

```
public class RemoteControl{
//이 리모컨 코드는 7개의 on, off 명령을 처리할 수 있음. 각 명령은 배열에 저장.
	Command[] onCommands;
    Command[] offCommands;
}

	public RemoteControl(){
    	onCommands = new Command[7];
        offCommands = new Command[7];
        //생성자는 각 on, off 배열의 인스턴스를 만들고 초기화하기만 하면 됨.
        Command noCommand = new NoCommand();
        for (int i = 0; i < 7; i++) {
        	onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
    }
        
  	public void setCommand(int slot, Command onCommand, Command offCommand) {
    //각 커맨드 객체는 나중에 사용하기 편하게 아래 두 배열에 저장
    	onCommand[slot] = onCommand;
        offCommand[slot] = offCommand;
    }
    
    //사용자가 온오프 버튼을 누르면 리모컨 하드웨어에서 각 버튼에 대응하는 메소드 호출
  	public void onButtonWasPushed(int slot){
    	onCommands[slot].execute();
    }
    
     public void offButtonWasPushed(int slot){
    	offCommands[slot].execute();
    }
    
    public Stirng toString(){
    	StringBuffer stringBuff = new StringBuffer();
        stringBuff.append("\n-----리모컨-----\n");
        for(int i = 0; i < onCommands.length; i++){
        	stringBuff.append("[slot" + i + "]" + onCommands[i].getClass().getName()+" "+offCommands[].getClass().getName() + "\n");
            //toString()을 오버라이드해서 슬롯별 명령을 출력하도록 고침. 리모컨 테스트시 이 코드 사용
        }
        return stringBuff.toString();
    }
  }
```

<h3>커맨드 클래스 만들기</h3>

```
public class LightOffCommand implements Command{
	Light light;
    
    public LightOffCommand(Light light){
    	this.light = light;
    }
    
    public void execute(){
    	light.off();
        //LightOffCommand는 리시버를 off() 메소드와 결합시킨다는 점을 제외하면 
        //LightOnCommand와 똑같은 방식으로 작동
    }
}
```

오디오를 켜고 끌 때 사용할 커맨드 클래스

```
public class StereoOnWithCDCommand implements Command{
	Stereo stereo;
    
    public StereoOnWithCDCommand(Stereo stereo{
    	this.stereo = stereo;
    }
    //제어할 오디오의 인스턴스를 전달 받음. 
    //그 인스턴스는 stereo라는 지역 인스턴스 변수에 저장
    
    public void execute(){
    	stereo.on();
        stereo.setCD();
        stereo.setVolume(11);
    }
}
```

<h3>리모컨 테스트</h3>

```
public class RemoteLoader{
	public static void main(String args[]){
    	RemoteControl remoteControl = new RemoteControl();
        
        Light livingRoomLight = new Light("Living Room");
        Light kitchenLight = new Light("Kitchen");
        CeilingFan ceilingFan = new CeilingFan("Living Room");
        GarageDoor garageDoor = new GarageDoor("Garage");
        Stereo stereo = new Stereo("Living Room");
        
        //조명용 커맨드 객체
        LightOnComand livingRoomLightOn = new LightOnComand(livingRoomLight);
        LightOffComand livingRoomLightOff = new LightOffComand(livingRoomLight);
         LightOnComand kitchenLightOn = new LightOnComand(kitchenLight);
        LightOffComand lkitchenLightOff = new LightOffComand(kitchenLight);
        
        //선풍기 온오프 커맨드 객체
        CeilingFanOnCommand ceilingFanOn = new CeilingFanOnCommand(ceilingFan);
        CeilingFanOffCommand ceilingFanOff = new CeilingFanOffCommand(ceilingFan);
        
        //차고 개폐 커맨드 객체
        GarageDoorUpCommand garageDoorUp = new GarageDoorUpCommand(garageDoor);
    	GarageDoorDownCommand garageDoorUp = new GarageDoorDownCommand(garageDoor);
        
        //오디오 온오프 커맨드 객체
        StereoOnWithCDCOmmand stereoOnWithCD = new StereoOnWithCDCOmmand(stereo);
       StereoOffWithCDCOmmand stereoOffWithCD = new StereoOffWithCDCOmmand(stereo);
       
       //리모컨 슬롯에 커맨드 로드
       remoteControl.setCommand(0, livingRoomLightOn, livingRoomLightOff);
       remoteControl.setCommand(1, kitchenLightOn, kitchenLightOff);
       remoteControl.setCommand(2, ceilingFanOn, ceilingFanOff);
       remoteControl.setCommand(3, stereoOnWithCD, stereoOffWithCD);
       
       System.out.println(remoteControl);
       //toString()메소드로 리코먼 슬롯의 정보 출력
       
       remoteControl.onButtonWasPushed(0);
       remoteControl.offButtonWasPushed(0);
       remoteControl.onButtonWasPushed(1);
       remoteControl.offButtonWasPushed(1);
       remoteControl.onButtonWasPushed(2);
       remoteControl.offButtonWasPushed(2);
       remoteControl.onButtonWasPushed(3);
       remoteControl.offButtonWasPushed(3);
    }
}
```

<h3>NoCommand 객체</h3>
일종의 널 객체로 딱히 리턴할 객체도 없고 클라이언트가 null을 처리하지 않고 싶을 때 활용하면 좋음. 

4~6번 슬롯에 명령이 리모컨에 할당되지 않은 부분에 이 객체를 넣어 execute()가 호출되어도 문제가 생기지 않도록 함.

```
public void onButtonWasPushed(int slot){
	if(onCommands[slot] != null){
    	onCommands[slot].execute();
    }
}
```

NoCommand 객체 구현

```
public void NoCommand implements Command{
	public void execute(){}
}
```

RemoteControl 생성자에서 모든 슬롯에 기본 커맨드 객체로 NoCommand 객체 넣기 -> 모든 슬롯에 커맨드 객체가 들어있음

```
Command noCommand = new NoCommand();
for(int i = 0; i < 7; i++){
	onCommand[i] = noCommand;
    offCommand[i] = noCommand;
}
```

### 작업 취소 기능 추가하기

undo() 메소드 추가하기

```
public interface Command{
	public void execute();
    public void undo();
}
```

LightOnCommand부터 undo() 메소드 추가해보기

```
public class LightOnCommand implements Command{
	Light light;
    
    public LightOnCommand(Light light){
    	this.light = light;
    }
    
    public void execute(){
    	light.on();
    }
    
    public void undo(){
    	light.off();
    }
}
```

LightOffCommand


```
public class LightOffCommand implements Command{
	Light light;
    
    public LightOffCommand(Light light){
    	this.light = light;
    }
    
    public void execute(){
    	light.on();
    }
    
    public void undo(){
    	light.off();
    }
}
```
RemoteControl에 undo() 추가
```
public class RemoteControlWithUndo{
	Command[] onCommands;
    Command[] offCommands;
    Command undoCommand;
    
    public RemoteControlWithUndo(){
    	onCommands = new Command[7];
        offCommands = new Command[7];
        
        Command noCommand = new NoCommand();
        for(int i=0; i<7; i++){
        	onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
        undoCommand = noCommand;
    }
    
    public void setCommand(int slot, Command onCommand, Command offCommand){
    onCommand[slot] = onCommand;
    offCommand[slot] = offCommand;
    }
    
    //1) 사용자가 버튼을 누르면 해당 커맨드 객체의 execute() 호출
    //2) 그 객체의 레퍼런스를 undoCommand 인스턴스 변수에 저장
    public void onButtonWasPushed(int slot){
    	onCommand[slot].execute();
        undoCommand = onCommands[slot];
    }
    
        public void offButtonWasPushed(int slot){
    	offCommand[slot].execute();
        undoCommand = offCommands[slot];
    }
    
    //사용자가 undo 버튼을 누르면 undoCommand에 저장된 커맨드 객체 호출
    public void undoButtonWasPushed(){
    	undoCommand.udno();
    }
    
    public String toString(){
    	//toString 코드
    }
}
```

### 작업 취소 기능 테스트

```
public class RemoteLoader{
	public static void main(String args[]){
    	RemoteControlWithUndo remoteControl = new RemoteControlWithUndo();
        
        Light livingRoomLight = new Light("Living Room");
        
        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
        
        //조명 커맨드 객체를 0번 슬롯에 추가
        remoteControl.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        
        //불을 켰다가 끈 다음 작업 취소
        remoteControl.onButtonWasPushed(0);
        remoteControl.offButtonWasPushed(0);
        remoteControl.undoButtonWasPushed();
        remoteControl.offButtonWasPushed(0);
        remoteControl.onButtonWasPushed(0);
        remoteControl.undoButtonWasPushed();
    }
}
```

<h4>작업 취소 기능을 구현할 때 상태를 사용하는 방법(예시 : 선풍기)</h4>

```
public class CeilingFan{
	public static final int HIGH = 3;
    public static final int MEDIUM = 2;
    public static final int LOW = 1;
	public static final int OFF = 3;
    String location;
    int spped;
    
    //CeilingFan 클래스는 선풍기 속도를 나타내는 상태 저장
    
    public CeilingFan(String location){
    	this.location = location;
        speed = OFF:
    }
    
    //이 메소드들로 선풍기 속도 설정
    public void high(){
    	speed = HIGH:
    }
    
    public void medium(){
    	speed = MEDIUM:
    }
    
    public void low(){
    	speed = LOW:
    }
    
    public void off(){
    	speed = OFF:
    }//선풍기 끄기
    
    public int getSpeed(){
    	return speed;
    }//선풍기 현재 속도 구하기
}
```

선풍기 명령어에 작업 취소 기능 추가

```
public class CeilingFanHighCommand implements Command{
	CeilingFan ceilingFan;
    int prevSpeed; //상태 지역 변수로 선풍기의 속도 저장
    
    public CeilingFanHighCommand(CeilingFan ceilingFan){
    	this.ceilingFan = ceilingFan;
    }
    
    //선풍기 속도 변경 전 작업 취소를 대비해 이전 속도를 저장
    public void execute(){
    	prevSpeed = ceilingFan.getSpeed();
        ceilingFan.high();
    }
    
    //작업 취소 시 선풍기 속도를 이전으로 되돌림
    public void undo(){
    	if(prevSpeed == CeilingFan.HIGH){
        	ceilingFan.high();
        } else if(prevSpeed == CeilingFan.MEDIUM){
        	ceilingFan.medium();
        } else if(prevSpeed == CeilingFan.LOW){
        	ceilingFan.low();
        } else if(prevSpeed == CeilingFan.OFF){
        	ceilingFan.off();
        }
    }
}
```

<h4>선풍기 테스트 코드</h4>

```
public class RemoteLoader{
	public static void main(String[] args){
    	RemoteControlWithUndo remoteControl = new RemoteControlWithUndo();
        
        CeilingFan ceilingFan = new CeilingFan("Living Room");
        
        //커맨드 객체 인스턴스 생성
        CeilingFanMediumCommand ceilingFanMedium = new CeilingFanMediumCommand(ceilingFan);
        CeilingFanHighCommand ceilingFanHigh = new CeilingFanHighCommand(ceilingFan);
        CeilingFanOffCommand ceilingFanOff= new CeilingFanOffCommand(ceilingFan);
        
        remoteControl.setCommand(0, ceilingFanMedium, ceilingFanOff);
        emoteControl.setCommand(1, ceilingFanHigh, ceilingFanOff);
        
        remoteControl.onButtonWasPushed(0); 
        //선풍기 속도를 medium으로 설정
        remoteControl.offButtonWasPushed(0); 
        //선풍기 끔
        remoteControl.undoButtonWasPushed(0); 
        //작업 취소
        
        remoteControl.onButtonWasPushed(1); 
        //선풍기 속도를 high으로 설정
        remoteControl.undoButtonWasPushed(0); 
        //다시 선풍기 속도를 medium으로 설정
    }
}
```

여러 동작 한 번에 처리하기 - 새로운 커맨드 생성

```
public class MacroCommand implements Command{
	Command[] commands;
    
    public MacroCommand(Command[] commands){
    	this.commands = commands;
        //Command 배열을 받아서 MacroCommand 안에 저장
    }
    
    public void execute(){ 
    	for(int i = 0; i < commands.length; i++){
        	commands[i].execute();
            //매크로 실행 시 각 커맨드를 순서대로 실행
        }
    }
}
```
<h3>매크로 커맨드 사용하기</h3>

01. 매크로에 넣을 일련의 커맨드 생성

```
Light light = new Light("Living Room");
TV tv = new TV("Living Room");
Stereo stereo = new Stereo("Living Room");
Hottub hottub = new Hottub("Living Room");

//각 장치를 제어할 ON 명령을 만듦
LightOnCommand lightOn = new LightOnCommand(light);
StereoOnCommand stereoOn = new StereoOnCommand(stereo);
TVOnCommand tvOn = new TVOnCommand(tv);
HottubOnCommand hottubOn = new HottubOnCommand(hottub);
```

02. On 커맨드와 Off 커맨드용 배열을 하나씩 만들고 필요한 커맨드 넣기
```
Command[] partyOn = {lightOn, stereoOn, tvOn, hottubOn};
Command[] partyOff = {lightOff, stereoOff, tvOff, hottubOff};

MacroCommand partyOnMacro = new MacroCommand(partyOn);
MacroCommand partyOffMacro = new MacroCommand(partyOff);

03. MacroCommand 객체를 버튼에 할당
```
remoteControl.setCommand(0, partOnMacro, partyOffMacro);
```

04. 작동해보기

### QNA로 알아보기
Q1. 항상 리시버가 필요한가? 커맨드 객체에서 execute()를 구현하면 안 되는 이유
A1. 리시버 행동 호출 시 더미 커맨드 객체 생성 -> 이럴 경우 인보커와 리시버를 분리하기가 힘들고, 리시버로 커맨드 매개변수화 하기 어려움

Q2. 작업 취소시 히스토리 기능 구현 - undo 버튼 여러 번 누르기
A2. 마지막으로 실행한 커맨드의 레퍼런스 저장 전 실행한 커맨드 자체를 스택에 넣으면 된다. 사용자가 UNDO 버튼을 누를 때마다 인보커에서 스택 맨 위에 있는 항목을 꺼내 undo() 메소드 호출

Q3. 파티 모드를 구현할 때 다른 커맨드 객체의 execute() 호출해도 됨?
A3. 가능은 하나 해당 command에 파티 모드 코드를 직접 넣어야 할 수도 있어, 차라리 Macro Command 사용을 추천함. 

<h3>커맨드 패턴 활용</h3>

커맨드로 컴퓨테이션의 한 부분(리시버와 일련의 행동)을 패키지로 묶어서 일급 객체로 전달 가능 <br>
-> 클라이언트 애플리케이션에서 커맨드 객체를 생성한 뒤 오랜 시간이 지나도 그 컴퓨테이션 호출 가능, 다른 스레드에서도 호출 가능
<br>
이런 점을 활용해 커맨드 패턴을 스케줄러나 스레드풀, 작업 큐와 같은 다양한 작업에 적용 가능

작업 처라 스레드에선 큐에서 커맨드를 하나씩 제거하면서 커맨드의 execute() 메소드를 호출함 -> 메소드 실행이 끝나면 다시 큐에서 새로운 커맨드 객체를 가져감.

작업 큐 클래스는 계산 작업을 하는 객체들과 완전리 분리되어 있음.<br>
한 스레드가 한동안 금융 관련 계산을 하다가 잠시 후에 네트워크로 뭔가 내려받을 수도 있음. 작업 큐 객체는 전혀 신경 쓸 필요 없음!<br>
큐에 커맨드 패턴을 구현하는 객체를 넣으면 그 객체를 처리하는 스레드가 생기고, 자동으로 execute() 메소드 호출

### 실전에 적용하기 

2장에서 만든 ActionListener 형태의 옵저버는 다양하며, 이는 인터페이스이자 Command 인터페이스이기도 하며, AngelListener와 DevilListener 클래스는 구상 Command 클래스.

implements Action Listener 해주기!

<h4>커맨드 패턴 더 활용하기</h4>

로그 기록은 어떤 명령을 실행하면서 디스크에 실행 히스토리를 기록하고, 애플리케이션이 다운되면 커맨드 객체를 다시 로딩해 execute() 메소드를 자동으로 순서대로 실행하는 방식으로 작동

<h3>핵심 정리</h3>
- 커맨드 패턴을 사용하면 요청하는 객체와 요청을 수행하는 객체 분리 가능
- 이렇게 분리하는 과정의 중심에는 커맨드 객체가 있으며, 이 객체가 행동이 들어있는 리시버를 캡슐화
- 이렇게 분리하는 과정의 중심에는 커맨드 객체가 있으며, 이 객체가 행동이 들어있는 리시버를 캡슐화
- 인보커는 무언가를 요청할 때 커맨드 객체의 execute() 메소드를 호출 -> execute() 메소드는 리시버에 있는 행동을 호출
- 커맨드는 인보커를 매개변수화할 수 있음 -> 실행 중에 동적으로 매개변수화를 설정 가능
- execute() 메소드가 마지막으로 호출되기 전의 상태로 되돌리는 작업 취소 메소드를 구현하면 커맨드 패턴으로 작업 취소 기능을 구현 가능
- 매크로 커맨드는 커맨드를 확장해서 여러 개의 커맨드 패턴을 한 번에 호출할 수 있게 해주는 가장 간편한 방법. 매크로 커맨드로도 어렵지 않게 작업 취소 기능 구현 가능
- 요청을 스스로 처리하는 '스마트' 커맨드 객체
- 커맨드 패턴을 활용해 로그 및 트랜잭션 시스템 구현 가능

<h4>낱말 퀴즈</h4>
9. Command : 뭘 해야하는지, 리시버가 누구인지 아는 객체
12. Decoupled(분리) : 인보커와 리시버는 서로 분리되어 있음
17. Undo : 커맨드 패턴으로 할 수 있는 또 다른 일

3. Request : 커맨드에서는 요청을 캡슐화 함
8 & 10 Receiver : 일을 해내는 방법을 아는 객체이자 요청을 처리함
11. Execute : 모든 커맨드에서 이 메소드 제공
14. Binds : 커맨드는 일련의 행동과 리시버를 연결해 줌.
