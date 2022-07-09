
## 싱글톤 패턴 이해하기 🌟

</br>

## 🎯 용도
> 스레드 풀, 캐시, 대화상자, 사용자 설정 혹은 레지스트리를 처리하는 객체, 로그 기록용 객체, 디바이스
> 드라이버 등 객체 중에 하나만 있으면 되는 경우 사용 (오히려 2개면 좋지 않다!)
</br>
</br>


## ✅ 고전적 싱글톤패턴 구현
> 생성자는 private 으로 설정하고, 객체를 부를 때에는 따로 static 함수를 사용한다. 
> 인스턴스가 있을 경우에는 그대로 그 인스턴스를 반환하고, 인스턴스가 없을 경우에는 생성하여 인스턴스를 반환하면 된다. 

</br>
</br>

```java

public class Singleton {
    private static Singleton uniqueInstance;

    private Singleton() {}
    
    public static Singleton getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton();
        }
        return uniqueInstance;
    }
}
```
</br>

## ✅ 게으른 인스턴스 생성(Lazy instantiation)
>uniqueInstance에 하나밖에 없는 인스턴스가 저장, uniqueInstance가 null 이어서아직 인스턴스가 만들어지지 않았다면 
>private로 선언된 생성자를 이용해서 Singleton객체를 만든다음 uniqueInstance 변수에 객체를 대입한다.
>이렇게 하면 인스턴스가 필요한 상황이 닥치기 전에는 아예 인스턴스를 생성하지않게되고 이런 방법을 게으른 인스턴스 생성(lazy instantiation)이라고 부른다.

</br>

## :exclamation: 게으른 인스턴스 생성의 문제 , 초콜릿 공장
대부분 초콜릿 공장에는 초콜릿 끓이는 장치(초콜릿 보일러)를 컴퓨터로 제어한다. 이 보일러에서는 초콜릿과 우유를 받아서 끓이고 초코바를 만드는 단계로 넘겨준다.
</br>
여기에 최신형 초콜릿 보일러를 제어하기 위한 클래스가 있다. 코드를 보면 잘못된 실수를 하지 않기 위해 여러가지 장치가 되어있음을 알 수있다.
</br>

```java
public class ChocolateBoiler {
    private boolean empty;
    private boolean boiled;
    
    public ChocolateBoiler() {
        empty = true; // 이 코드는 보일러가 비어있을 때만 동작한다
        boiled = false; 
    }
    
    public void fill() {
        if(isEmpty()) { // 보일러가 비어있을 때만 재료를 가득 채운다. 원료를 가득 채우고 나면 boiled 를 false 로 돌려놓는다.
            empty = false;
            boiled = false;
            // 보일러에 우유/초콜릿을 혼합한 재료를 집어넣음
        }
    }
    
    /**
    * 보일러에 들어있는 재료를 다음 단계로 넘긴다.
    */
    public void drain() {
        if(!isEmpty() && isBoiled()) {  // 보일러가 가득 차있고 다 끓여진 상태에서만 보일러에 들어있는 재료를 다음 단계로 넘긴다. 
        보일러를 다 비우고 나면 empty 플래그를 다시 true로 설정한다.
            // 끓인 재료를 다음 단계로 넘김
            emtpy = true;
        }
    }
    
    /**
    * 보일러에 들어있는 재료를 끓인다. 
    */
    public void boil() {
        if(!isEmpty() && !isBoiled()) { // 보일러가 가득 차있고 아직 끓지 않은 상태만 초콜릿과 우유가 혼합된 재료로 끓인다. 
        재료를 다 끓이면 boild를 true로 설정한다.
            emtpy = true;
        }
    }
    
    public boolean isEmpty() {
        return empty;
    }
    
    public boolean isBoiled() {
        return boiled;
    }
}
```
</br>

위의 코드는 괜찮아 보인다. 그러나 만약에 ChocolateBoiler의 인스턴스 여러개가 각기 생성되면 어떤 문제가 발생할까?  </p>

- 보일러에 들어있는 재료가 끓고 있는데 재료를 부어 버릴 수 있다. </p>
- 재료를 동시에 다음 단계로 넘겨서 다음 단계에 문제가 생길 수 있다.  </p>
=> 위의 문제를 해결하기 위해 ChocolateBoiler에 싱클톤 패턴을 적용 할 수 있다. </p>

</br>

```java
public class ChocolateBoiler {
    private boolean empty;
    private boolean boiled;
    private ChocolateBoiler uniqueInstance;
    
    private ChocolateBoiler() {
        empty = true; // 이 코드는 보일러가 비어있을 때만 동작한다
        boiled = false; 
    }
    
    public static ChocolateBoiler getInstance() {
        if(uniqueInstance == null) {
            uniqueInstance = new ChocolateBoiler();
        }
        
        return uniqueInstance;
    }
    
    public void fill() {
        if(isEmpty()) { // 보일러가 비어있을 때만 재료를 가득 채운다. 원료를 가득 채우고 나면 boiled 를 false 로 돌려놓는다.
            empty = false;
            boiled = false;
            // 보일러에 우유/초콜릿을 혼합한 재료를 집어넣음
        }
    }
    
    public void drain() {
        if(!isEmpty() && isBoiled()) {
            // 끓인 재료를 다음 단계로 넘김
            emtpy = true;
        }
    }
    
    public void boil() {
        if(!isEmpty() && !isBoiled()) {
            emtpy = true;
        }
    }
    
    public boolean isEmpty() {
        return empty;
    }
    
    public boolean isBoiled() {
        return boiled;
    }
}
```
</br>

아까 앞에서 보았던 것과 같은 싱글톤 패턴을 적용하면 하나의 인스턴스만 생성하게 강제 할 수 있다.

</br>

## 📝 정의
> 싱글톤 패턴은 해당 클래스의 인스턴스가 하나만 만들어지고, 어디서든지 그 인스턴스에 접근 할 수 있도록 하기 위한 패턴

</br>

- 반드시 생성은 클래스 자신을 통해 하도록 하여, 다른 어떤 클래스에서도 자신의 인스턴스를 추가로 만들지 못하도록 한다.
- 싱글톤은 '게으르게' 생성할 수 있다. 클래스의 객체가 자원을 많이 잡아먹는 경우에 유용하다.

## :exclamation: 문제 발생
위의 코드를 가지고 ChocolateBoiler 를 실제로 동작시켜 보았다. 그런데 fill() 메소드에서 아직 초콜릿이 끓고 있는데 재료를 집어넣어버렸다.</p> 
싱글턴 패턴이 제대로 구현되지 않은 건가? 사용자가 멀티스레드를 사용하도록 초콜릿 보일러 컨트롤러를 최적화 시킨거 말고는 없다고 한다.

문제 해결을 위해 두 개의 JVM(java virtual machine) 에서 다음과 같은 코드를 실행시켜본다고 가정해보자
</br>

```java

public static ChocolateBoiler getInstance() {
    if(uniqueInstance == null) {
        uniqueInstance = new ChocolateBoiler();
    }
    
    return uniqueInstance;
}

```
</br>
만약에 1번 쓰레드에서 getInstance 를 수행할 때, 2번 스레드에서도 동시에 getInstance 를 하면
</br>

```java

if(getInstanc == null)

```
</br>
이 부분에서 두 개의 스레드가 동시에 저 라인을 통과해버린다. 두 개의 스레드에서는 각각 uniqueInstance 이 초기화되기 전이라서, null 이기 때문이다. </p>
그렇기 때문에 동시에 두 개의 인스턴스가 생성되게 되고, 초콜릿이 끓고 있는데 재료를 집어넣는 것과 같은 일이 발생할 수 있다.

</br>
어떻게 해야 할까? 일단 동기화를 하는 방법을 생각해 볼 수 있다.
</br>

## ✅ 멀티스레딩 문제 해결
### genInstance()를 동기화

```java
public class ChocolateBoiler {
    private boolean empty;
    private boolean boiled;
    private ChocolateBoiler uniqueInstance;
    
    private ChocolateBoiler() {
        empty = true;
        boiled = false; 
    }
    
    public static synchronized ChocolateBoiler getInstance() { // getInstance 를 동기화한다.
        if(uniqueInstance == null) {
            uniqueInstance = new ChocolateBoiler();
        }
        
        return uniqueInstance;
    }
}
```

</br>
이렇게 간단하게 문제를 해결할 수 있다. 코드는 정확히 동작한다. </p>
그러나, 조금 생각해 보면 이렇게 동기화를 하기가 아깝다는 느낌이 들 수 있다. 동기화가 필요한 시점은 이 메소드가 시작되는 때 뿐이다.
</br>
바꿔 말하자면, 일단 uniqueInstance 변수에 ChocolateBoiler 가 대입된 이후에는 굳이 이 메소드를 동기화된 상태로 유지할 필요가 없는 것이다. </p>
uniqueInstance 를 초기화 한 이후에 해당 메소드를 동기화하는 것은 불필요한 오버헤드만 증가시킬 뿐이다.
</br>

## ✅ 더 효율적인 방법은?
### 방법1) getInstance() 의 속도가 그리 중요하지 않다면 그냥 둔다
</br>
 getInstance() 메소드가 애플리케이션에 큰 부담을 주지 않는다면 그냥 놔둬도 된다. </p>
 다만, 메소드를 동기화하면 성능이 100 배 정도 저하된다는 것은 기억해두어야 한다. </p>
 만약에 getInstance() 가 병목으로 작용한다면 다른 방법을 생각 해야 한다.
 </br>
 
 ### 방법2) 인스턴스를 필요할 때 생성하지 말고, 처음부터 만들어버린다
 </br>

```java
public class ChocolateBoiler {
    private boolean empty;
    private boolean boiled;
    private static ChocolateBoiler uniqueInstance = new ChocolateBoiler(); // static 변수에서 싱글톤 인스턴스를 생성한다. 
    //이렇게 하면 스레드-세이프하다.
    
    private ChocolateBoiler() {
        empty = true;
        boiled = false; 
    }
    
    public static ChocolateBoiler getInstance() {
        return uniqueInstance;
    }
}
```
이렇게 하면 JVM 에서 유일한 인스턴스를 생성해 준다. 생성되기 전에는, 어떤 스레드로 uniqueInstance 정적 변수에 접근할 수 없다.
</br>

### 방법3) DLC(Double-checking Locking) 을 써서 getInstance() 에서 동기화되는 부분을 줄인다
</br>
DCL 를 사용하면 일단 인스턴스가 생성되어 있는지를 확인한 다음, 생성되어 있지 않았을 때만 동기화를 할 수 있다. </p>
이렇게 하면 처음에만 동기화를 하고 나중에는 동기화를 하지 않아도 된다.
</br>

```java
public class ChocolateBoiler {
    private boolean empty;
    private boolean boiled;
    private volatile ChocolateBoiler uniqueInstance; // 반드시 volatile 로 변수를 선언해야 한다.
    
    private ChocolateBoiler() {
        empty = true;
        boiled = false; 
    }
    
    public static ChocolateBoiler getInstance() {
        if (uniqueInstance == null) { // 인스턴스가 있는지 확인하고, 없으면 동기화 된 블록으로 진입한다.
            synchronized (ChocolateBoiler.class) {
                if (uniqueInstance == null) { // 블록으로 들어온 후에도 다시 변수가 null 인지 체크한다.
                    uniqueInstance = new ChocolateBoiler();
                }
            }
        }
        return uniqueInstance;
    }
}

```
주의해야 할 점은 DCL 는 자바 1.4 이전 버전에서는 사용할 수 없다는 것이다. 자바 1.4 이전의 JVM 에서는 volatile 키워드를 사용하더라도 동기화가 잘 되지 않는 것이 많다.
</br>

## ✅ 싱글톤의 서브클래스
싱글턴은 서브클래스를 만들 수 없다. 왜냐면 생성자가 private 으로 선언되어 있기 때문이다. 만약에 생성자를 public 으로 고치게 된다면,</p>
다른 클래스에서 인스턴스를 만들 수 있기 때문에 더 이상 싱글턴이라고 볼수 없다.
</br>
생성자를 고친다고 하더라도 싱글턴은 정적 변수를 바탕으로 구현하기 때문에, 서브클래스를 만들면 모든 서브클래스가 똑같은 인스턴스 변수를 공유하게 되는 문제가 있다. </p>
이를 막기 위해서는 베이스 클래스에서 레지스트리 같은 걸 구현해 놓아야 한다.
</br>
그러나 가장 우선적으로 고려해야 할 점은, 싱글턴을 확장해서 무엇을 할 것인지이다. </p>
싱글턴의 서브클래스를 만들어야 할 상황이 왔다는 것은 애플리케이션에 싱글턴을 꽤 자주 사용하고 있다는 신호일 수 있다. </p>
그런 상황이라면, 전반적인 디자인을 다시 고려해보는 게 좋다. </p>
싱글턴은 제한된 용도로 특수한 상황에서 사용하기 위해 만들어진 디자인 패턴이기 때문이다.



