<h1>Decorator Pattern</h1>
객체에 추가 요소를 동적으로 더할 수 있다. <br>
데코레이터를 사용하면 서브클래스를 만들 때보다 훨씬 유연하게 기능을 확장할 수 있다.

<h2>구성의 장점</h2>
- 실행 중에 동적으로 행동 설정 가능
- 객체에 여러 임무를 새로 추가할 수 있음
- 기존 코드를 건드리지 않고 코드 수정에 따른 버그나 의도하지 않은 부작용들을 고칠 수 있음

<h2>OCP(Open-Closed Principle)</h2>
클래스는 확장에는 열려 있으나 변경에는 닫혀 있음

<h3>주문 시스템에 데코레이터 패턴 적용하기</h3>
1. Dark Roast 객체
   Beverage로부터 상속받으므로 음료 가격을 계산하는 메소드를 지님

2. 고객이 모카를 주문했으므로 Mocha 객체를 만들고 그 객체로 DarkRoast를 감쌈
- Mocha 객체는 데코레이터임. 객체의 형식은 객체가 장식하고 있는 객체를 반영하는데, Beverage가 이를 반영함. 여기서 반영한다는 뜻은 같은 형식을 갖는다는 뜻!
- Mocha에도 cost() 메소드가 있고, 이를 감싸는 것도 Beverage 객체로 간주할 수 있음. 왜냐하면 Beverage의 서브클래스 형식이기 때문.

3. 고객이 휘핑크림도 추가했으므로 Whip 데코레이터를 만들어 Mocha를 감쌈
- Whip도 데코레이터라서 DarkRoast의 형식을 반영해 Coast() 메소드를 가짐
- Mocha와 Whip에 싸여 있는 DarkRoast는 여전히 Beverage 객체이므로 cost() 메소드 호출을 비롯해, DarkRoast에 관한 일이라면 무엇이든 할 수 있음.

4. 가격 호출을 위해서는 가장 바깥쪽 데코레이터인 Whip의 cost()를 호출 -> Whip은 그 객체가 장식하고 있는 객체에게 가격 계산 위임<br>
   -> 가격이 구해진 후 계산된 가격에 휘핑크림의 가격을 더한 결과값을 리턴

<h3>Summary</h3>
- 데코레이터의 슈퍼클래스는 자신이 장식하고 있는 객체의 슈퍼클래스와 같다
- 한 객체를 여러 개의 데코레이터로 감쌀 수 있다
- 데코레이터는 자신이 감싸고 있는 객체와 같은 슈퍼클래스를 가지고 있기에 원래 객체가 들어갈 자리에 데코레이터 객체를 넣어도 상관이 없다
- <i>데코레이터는 자신이 장식하고 있는 객체에게 어떤 행동을 위임하는 일 말고도 추가 작업을 수행할 수 있다</i>
- 객체는 언제든지 감쌀 수 있으므로 실행 중에 필요한 데코레이터를 마음대로 적용할 수 있다

<h3>커피 주문 시스템 코드 만들기</h3>

```
public abstract class Beverage{
    String description = "제목 없음";
    //Beverage는 추상 클래스이며, getDescription()과 cost()라는 2개의 메소드를 가짐
    
    public String getDescirption(){
    //getDescirption은 이미 구현되어 있지만 cost()는 서브클래스에서 구현해야 함
    return description;
    }
    
    public abstract double Csot();
}
```

```
public abstract class CondimentDecorator extends Beverage{
    Beverage beverage;
    public abstract String getDescription();
}
//Beverage 객체가 들어갈 자리에 들어갈 수 있어야하므로 Beverage 클래스를 확장함
//각 데코레이터가 감쌀 음료를 나타내는 Beverage 객체를 여기에서 지정함. 음료를 지정할 땐 데코레이터에서 어떤 음료를 감쌀 수 있도록 Beverage 슈퍼클래스 유형을 사용함.
//모든 첨가물 데코레이터에 getDescription() 메소드를 새로 구현하도록 만들 계획으로 추상메소드로 선엄함.
```


<h3>음료 코드 코드 구현하기</h3>

<h4>에스프레소</h4>

```
public class Espresso extends Beverage{
//Beverage 클래스를 확장

    public Espresso(){
       description = "에스프레소";
    }
    //클래스 생성자 부분에서 description 변수 값을 설정함. description 인스턴스 변수는 Beverage로부터 상속 받음.
    
    public double cost(){
      return 1.99;
    }
    //가격만 리턴해주기
}
```

<h4>하우스 블렌드 커피</h4>
```
public class HouseBlend extends Beverage{
    public HouseBlend(){
    description = "하우스 블렌드 커피";
    }

    public double cost(){
    return .89;
    }
}
```

<h4>다크 로스트</h4>
```
public class DarkRoast extends Beverage{
    public DarkRoast(){
    description = "다크 로스트 커피";
    }

    public double cost(){
    return .99;
    }
}
```

<h4>디카페인</h4>
```
public class Decaf extends Beverage{
    public Decaf(){
        description = "디카페인 커피";
    }

    public double cost(){
        return .79;
    }
}
```

<h3>첨가물 코드 구현하기</h3>
Mocha 인스턴스에는 Beverage의 레퍼런스가 들어있음.
1. 감싸고자 하는 음료를 저장하는 인스턴스 변수
2. 인스턴스 변수를 감싸고자 하는 객체로 설정하는 생성자(데코레이터의 생성자에 감싸고자 하는 음료 객체를 전달하는 방식을 사용함)

```
public class Mocha extends CondimentDecorator{
//CondimentDecorator에서 Beveragem를 확장함

    public Mocha(Beverage beverage){
      this.beverage = beverage;
    }

    public String getDescription(){
      return beverage.getDescription() + ", 모카";
    }
    
    public double cost(){
      return beverage.cost() + .20;
    }
}
```

<h4>커피 주문 시스템 코드 테스트</h4>
```
public class StarbuzzCoffee {

    public static void main(String args[]){
    //아무것도 넣지 않은 에스프레소를 주문하고 그 음료의 설명과 가격 출력
    Beverage beverage = new Espresso();
    System.out.println(beverage.getDescription() + "$" + beverage.cost());
    
    Beverage beverage2 = new DarkRoast();
    beverage2 = new Mocha(beverage2); //모카로 감쌈
    beverage2 = new Mocha(beverage2); //모카 샷 추가
    beverage2 = new Whip(beverage2); //휘핑 올리기
    System.out.println(beverage2.getDescription() + "$" + beverage2.cost());
    
    Beverage beverage3 = new HouseBlend();
    beverage3 = new Soy(beverage3); //두유 감쌈
    beverage3 = new Mocha(beverage3); //모카 샷 추가
    beverage3 = new Whip(beverage3); //휘핑 올리기
    System.out.println(beverage3.getDescription() + "$" + beverage3.cost());
    }
}
```

<h4>커피 사이즈별 가격 코드 테스트</h4>
```
public abstract class CondimentDecorator extends Beverage{
public Beverage beverage;
public abstract String getDescription();

    //데코레이터에 음료 사이즈를 리턴하는 getSize() 메소드 추가
    public Size getSize(){
      return beverage.getSize();
    }
}

public class Soy extends CondimentDecorator{
public Soy(Beverage beverage){
this.beverage = beverage;
}
}

public String getDescription(){
return beverage.getDescription() + ", 두유";
}

public double cost(){
double cost = beverage.cost();

    if(beverage.getSize() == Size.TALL) {
       cost += .10;
    } else if (beverage.getSize() == Size.GRANDE){
       cost += .15;
    } else if (beverage.getSize() == Size.VENTI){
       cost += .20;
    }
    return cost;
}
}
```

<h4>코드를 통해 배운 점</h4>
- 추상 구성 요소로 돌아가는 코드에 데코레이터 패턴을 적용해야만 제대로 된 결과를 얻을 수 있음.
  <h5>데코레이터가 적용된 예: 자바 I/O</h5>
  ZipInputStream > BufferedInputStream > FileInputStream > 읽어 들일 파일 텍스트
1. ZipInputStream <br> 구상 데코레이터로 zip 파일에서 데이터를 읽어 올 때 그 속에 들어있는 항목을 읽는 기능을 더해 준다.
2. BufferedInputStream <br> 구상 데코레이터로 FileInputStream에 입력을 미리 읽어서 더 빠르게 처리할 수 있게 해주는 버퍼링 기능을 더해 준다.
3. FileInputStream <br> 데코레이터로 장식할 예정으로 자바 I/O 라이브러리는 FileInputStream, StringBufferInputStream, ByteArrayInputStream 등 다양한 구성 요소를 제공한다. <br>
   이들 모두 바이트를 읽어 들이는 구성 요소 역할을 함.

* InputStream은 추상 데코레이터 클래스 역할을 하며, FileInputStream이 InputStream을 확장한 클래스.


<h4>자바 I/O 데코레이터 만들기</h4>
```
public class LowerCaseInputStream extends FilterInputStream{
//모든 InputStream의 추상 데코레이터인 FilterInputStream을 확장함

    public LowerCaseInputStream(InputStream in){
        super(in);
    }
    
    public int read() throws IOException{
        int c = in.read();
        return (c == -1 ? c: Character.toLowerCase((char)c));
    }
    
    //이제 2개의 read() 메소드를 구현해야 하는데 각각 byte 값, 혹은 byte 배열을 읽고 각 byte(문자를 나타냄)를 검사해서 대문자이면 소문자로 변환.
    
    public int read(byte[] b, int offset, int len) throws IOException {
        int result = in.read(b, offset, len);
        for(int i = offset; i < offset+result; i++){
        b[i] = (byte)Character.toLowerCase((char))b[i]);
        }
    return result;
    }
}
```

<h4>자바 I/O 데코레이터 테스트</h4>
```
public class InputTest{
    public static void main(String[] args) thrwos IOException{
    int c;

    try {
        InputStream in = new LowerCaseInputStream(
        new BufferedInputStream(
        new FileInputStream("test.txt")));
    
        while((c = in.read()) >= 0) {
        System.out.print((char)c); //스트림을 써서 파일 끝까지 문자를 하나씩 출력하면서 처리합니다.
        }

        in.close();
    } catch(IOException e) {
    e.printStackTrace();
    }
  }
}
```

<h2>핵심 정리</h2>
- 디자인의 유연성 면에서 보면 상속으로 확장하는 일을 별로 좋은 선택이 아님
- 기존 코드 수정 없이 행동을 확장해야 하는 상황도 있음
- 구성과 위임으로 실행 중에 새로운 행동을 추가할 수 있음
- 상속 대신 데코레이터 패턴으로 행동을 확장할 수 있음
- 데코레이터 패턴을 구상 구성 요소를 감싸 주는 데코레이터를 사용
- 데코레이터 클래스의 형식은 그 클래스가 감싸는 클래스 형식을 반영(상속이나 인터페이스 구현으로 자신이 감쌀 클래스와 같은 형식을 가짐)
- 데코레이터는 자기가 감싸고 있는 구성 요소의 메소드 호출한 결과에 새로운 기능을 더함으로써 행동을 확장
- 구성 요소를 감싸는 데코레이터 개수에는 제한이 없음
- 구성 요소의 클라이언트는 데코레이터의 존재를 알 수 없음. 클라이언트가 구성 요소의 구체적인 형식에 의존하는 경우는 예외!
- 데코레이터 패턴을 사용하면 자잘한 객체가 매우 많이 추가될 수 있어서 코드가 복잡해질 수 있다.
