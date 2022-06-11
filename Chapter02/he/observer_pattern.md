
# 옵저버 패턴 이해하기:star:


* 옵저버 : 관찰자 
 > 어떤 주제(속성, 값) 객체가 변화하게 되면 그와 연관된 객체 들에게 변화를 알리는 패턴
 >> 주제객체 = 관찰 대상 , 그와 연관된 객체 = 옵저버 


### Subject / Objerver

 * Subject(주재객체, 관찰대상)  : 상태저장, 지베. Observerable 이라고도 부르며 이벤트가 발생하는 객체, 옵저버의 리스트를 가지고 있음, Observer의 등록/해제 메서드를 이용하여 SUbject를 등록 해제 할 수 있음
 
 * Observer(옵저버) : Subject의 이벤트를 관찰하는 대상, 의존적 성질.

=> 하나의 주제와 여러개의 옵저버가 연관된, 일대다 관계(one-to-many)관계 성립


</br>
</br>


### 느슨한 결합(Loose Coupling)

- 옵저버 패턴은 주제(데이터를 전달하는 객체) 와 옵저버(데이터를 받는 객체)가 느슨한 결합을 하고 있는 디자인 패턴
- 객체들이 느슨한 결합을 하고 있다는 것은, 객체들끼리 상호작용을 하지만 서로 '의존성'이 낮다는 것
- 즉, 객체 사이에 상호작용이 이루어지지만 직접적인 관계는 없는 상태 

=> 이유(장점) : </br>

**1. 주제가 옵저버에 대해서 아는것은 옵저버가 특정 인터페이스(Observer인터페이스)를 구현한다는 것 뿐이다.**
 - 옵저버의 구상클래스가 무엇인지, 옵저버가 무엇을 하는지 등에 대해 알 필요가 없다.
 
**2. 옵저버는 언제든지 새로 추가 할 수 있다.**
 - 주제는 Observer 인터페이스를 구현하는 객체의 목록에만 의존하기 때문에 언제든지 새로운 옵저버를 추가하거나 기존 옵저버를 제거하거나 등의 작업을 할 수 있다.
 
**3. 새로운 형식의 옵저버를 추가하려고 할 때도 주제를 전혀 변경할 필요가 없다.**
 - 새로운 클래스에서 Observer 인터페이스를 구현하고 옵저버롤 등록하기만 하면 된다.
 
**4. 주제와 옵저버는 서로 독릭적으로 재사용할 수 있다.**
 - 주제와 옵저버가 서로 단단하게 결합되어 있지 않기 때문에 다른 용도로 재사용할 수 있다.
 
**5. 주제나 옵저버가 바뀌더라도 서로한테 영향을 미치지 않는다.**
 - 둘이 서로 느슨하게 결합되어 있기 때문이다.

</br>
</br>

_____


</br>

## 기상 스테이션 구현 
: 유튜버 / 구독자 -> 동영상이 업로드 될때마다 구독자들에게 영상 제목 알려주기

## 유튜버와 구독자 인터페이스 선언 
  유튜버 인터페이스 : 구독자 추가, 제거, 영상 업로드
  구독자 인터페이스 : 유튜버가 올린 새 영상의 제목을 출력하는 update 기능

```java
 interface Youtube{
   fun subscribe(subscriber: Subscriber)
   fun unsunscribe(subscriber: Subscriber)
   fun uploadVide(title: String)
 } 
 
 interface Subscriber{
   fun update(msg: String)
 }
 
```

## Youtuber 인터페이스를 상속받는 Gogildong 클래스 생성
 : 구독자들을 list 현태로 저장, 영상 업로드 시 구독자들의 update 함수를 실행
 

```java
 class Gogildong: Youtuber{
   private val subscribers = mutableListOf<Subscriber>()
   
   override fun subscribe(subscriber: Subscriber){
     subscribers.add(subscriber)
   }
   
   override fun unsubscribe(subscriber: Subscriber){
     subscribers.remove(subscriber)
   }
   
   override fun uploadVideo(title: String){
     subscribers.forEach{it.update(title)}
   }
 }
 
```

## Subscriber 인터페이스를 상속받는 Doooly와 Doner 클래스 생성
 : update 함수를 override 하여 영상의 제목을 출력하도록 함


```java
 
 class Dooly: Subscriber{
   override fun update(msg: String){
     printIn("둘리 수신 ${msg}")
   }
 }  
 
  class Doner: Subscriber{
    override fun update(msg: String){
      printIn("도우너 수신 ${msg}")
    }
  }
 
```

## main 
: 구독, 구독취소, 영상 업로드 실행

```java

fun main(){
    val gildong = Gogildong()
    val dooly = Dooly()
    val doner = Doner()
    
    gildong.subscribe(dooly)
    gildong.subscribe(doner)
    
    gildong.uploadVideo(" 종로로 갈까요"
    
    gildong.unsubscribe(doner)
    
    gildong.uploadVideo("명동으로 갈까요")
      
 } 
 
```

## 결과
```
둘리 수신 종로로 갈까요
도우너 수신 종로로 갈까요
둘리 수신 명동으로 갈까요

```

예시 참조 
<https://angangmoddi.tistory.com/286>
