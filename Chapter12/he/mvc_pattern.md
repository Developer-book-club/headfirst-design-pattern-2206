# 복합패턴 : mvc 패턴 이해하기 :star2:

</br>
</br>

## 🎯 MVC
> Model - View - Controller (MVC) 3가지 형태로 역할을 나누어 개발하는 방법론 </p>
> Controller를 조작하면 Controller는 Model을 통해서 데이터를 가져오고 그 정보를 바탕으로 시각적인 표현을 담당하는 View를 제어해서 사용자에게 전달한다. </p>
> UI와 비즈니스 로직이 분리되어 한쪽 모듈 수정시 서로 영향이 적다 </p>

</br>
</br>

## ✅ 구조
![mvc 패턴](https://user-images.githubusercontent.com/98209409/180592607-6d8e686d-235f-417a-9dbb-fed05f438ae3.png)

출처: https://plposer.tistory.com/33

 1. 컨트롤러가 사용자로부터 입력을 받으며 입력받은 내용이 모델에게 어떤 의미가 있는지 파악한다.
 2. 컨트롤러가 모델에게 상태를 바꿔달라고 요청한다. 
 3. 모델은 뷰에게 상태가 변경되었음을 알린다.
 4. 뷰가 디스플레이를 갱신하고 사용자는 변경된 뷰를 확인할 수 있다.

</br>
</br>

## ✅ Model
> 어플리케이션이 '무엇'을 할것인지 정의하는 부분. 즉 DB와 연동하여 사용자가 입력한 데이터나 사용자에게 출력할 데이터를 다룬다. </p>
> 애플리케이션의 모든 정보와 데이터, 상태 및 어플리케이션 로직이 들어있다. </p>
> 뷰와 컨트롤러에서 모델의 상태를 조작하거나 가져오기 위한 인터페이스를 제공하고 모델에서 자신의 상태 변화에 대해서 옵저버들에게 알려주긴 하지만
> 기본적으로 모델은 뷰와 컨트롤러에 별 관심이 없다. </p>

</br>
</br>

## ✅ View
> 사용자에게 시각적으로 보여주는 부분 UI(사용자 인터페이스) </p>
> 일반적으로 화면에 표시하기 위해 필요한 상태 및 데이터를 모델에서 직접 가져온다. </p>

</br>
</br>

## ✅ Controller
> 사용자가 보는 페이지와 데이터처리 사이에서 중간 제어자 역할을 한다
> Model이 데이터를 '어떻게' 처리 할지 알려주는 역할을 한다. 사용자에 의해 클라이언트가 보낸 데이터가 있으면 모델을 호출하기 전에 적절히 해석 및 가공을 하고 모델을 호출한다. </p>
> 그 다음 모델이 수행을 완료하면 그 결과를 가지고 View에게 전달하는 역할을 한다.

</br>
</br>

## :bulb: 알아보기
> 1.사용자는 뷰에만 접촉 할 수 있다. </p>
> : 뷰는 모델을 보여주는 창이다. 사용자가 뷰에서 뭔가 사면 뷰는 무슨 일이 일어났는지 컨트롤러에게 알려주고, 그러면 컨트롤러가 상황에 맞게 작업을 처리한다. </p>
</br>

> 2.컨트롤러가 모델에게 상태를 변경하라고 요청한다. </p>
> : 컨트롤러는 사용자의 행동을 받아서 해석, 모델을 어떤 식으로 조작해야 하는지 결정한다. </p>
</br>

> 3.컨트롤러가 뷰를 변경해 달라고 요청 할 수도 있다. </p>
> : 컨트롤러는 뷰로부터 어떤 행동을 받았을 때, 그 행동의 결과로 뷰에게 뭔가를 바꿔 달라고 할 수도 있다. </p>
>  예를 들어, 컨트롤러는 인터페이스에 있는 어떤 버튼이나 메뉴를 활성화하거나 비활성화 할수 있다. </p>
</br>

> 4. 상태가 변경되면 모델이 뷰에게 그 사실을 알린다. </p>
> : 사용자가 한 행동이나 내부적 변화 등으로 모델에서 뭔가가 바뀌면 모델은 뷰에게 상태가 변경되었다고 알린다. </p>
</br>

> 5. 뷰가 모델에게 상태를 요청한다. </p>
> : 뷰는 화면에 표시할 상태를 모델로부터 직접 가져온다. </p>
> 예를들어, 모델이 뷰에게 새로운 곡이 재생되었다고 알려주면 뷰는 모델에게 곡 제목을 요청하고 그것을 받아 화면에 표시한다. </p>

</br>
</br>

## :key: MVC에 사용되는 패턴
</br>

### :bookmark: 모델 - 옵저버 패턴 
: 모델은 옵저버 패턴을 써서 상태가 변경되었을 때 그 모델과 연관된 객체들에게 연락한다. 옵저버 패턴을 사용하면 모델을 뷰와 컨트롤러로부터 완전히 독립 시킬 수 있다. </p>
한 모델에서 서로 다른 뷰를 사용할 수도 있고, 심지어 여러개의 뷰를 동시에 사용하는 것도 가능하다. </p>

<img width="704" alt="모델의 옵저버 패턴" src="https://user-images.githubusercontent.com/98209409/180632041-1a359a8e-4fe8-451f-9c49-9ab62454e3f6.png">

</br>

### :bookmark: 뷰 - 컴포지트 패턴
: 디스플레이는 여러 단계로 겹쳐있는 윈도우, 패널 버튼, 텍스트 레이블 등으로 구성된다. </p>
각 디스플레이 항목은 복합 객체(윈도우 등)나 잎(버튼)이 될 수 있다. 컨트롤러가 뷰에게 화면을 갱신해 달라고 요청하면 최상위 뷰 구성 요소에게만 화면을 갱신하라고 얘기하면 된다. </p>
나머지는 컴포지트 패턴이 알아서 처리해 준다. </p>


<img width="667" alt="뷰의 컴포지트 패턴" src="https://user-images.githubusercontent.com/98209409/180632033-c0eacb48-b405-4659-95c4-6d018f9754e8.png">


</br>

### :bookmark: 컨트롤러 - 전략 패턴
: 뷰와 컨트롤러는 고전적 전략패턴으로 구현되어 있다. 뷰 객체를 여러 전략을 써서 설정할 수 있다.  </p>
뷰는 애플리케이션의 겉모습에만 신경 쓰고, 인터페이스의 행동을 결정하는 일은 모두 컨트롤러에게 맡긴다. 전략 패턴을 사용하면 뷰를 모델로부터 분리하는 데에도 도움이 된다.  </p>
사용자가 요청한 내역을 처리하려고 모델과 얘기하는 일을 컨트롤러가 맡고 있다. 뷰는 그 방법을 전혀 알지 못한다.  </p>

<img width="600" alt="뷰와 컨트롤러 전략패턴" src="https://user-images.githubusercontent.com/98209409/180632012-c20020ad-2ebb-4dbc-b613-1916cf10781c.png">

출처 : https://thefif19wlsvy.tistory.com/49

</br>
</br>

## ✅ 예제
</br>
예제 출처 : https://www.crocus.co.kr/1539
</br>

### MVC main class
: model, view, controller를 모두 생성한다. updateView를 통해 옵저버 패턴을 보이고있다.
</br>

```java

package MVCPattern;

public class MVCMain {

  public static void main(String[] args) {
    CharacterModel model = new CharacterModel("Crocus", 20, 3);		
    CharacterView view = new CharacterView();		
    CharacterController controller = new CharacterController(model, view);
    controller.updateView();

    controller.setCharacterLevel(controller.getCharacterLevel() + 1);
    controller.updateView();
    
    controller.setCharacterLife(controller.getCharacterLife() + 10);
    controller.updateView();
  }

}
```
</br>

### CharacterModel 클래스 
: Model은 모든 상태를 가지고 관리해야 한다.

```java

package MVCPattern;

public class CharacterModel {
  private String name;
  private int level;
  private int life;
  
  public CharacterModel(String name, int level, int life) {
    this.name = name;
    this.level = level;
    this.life = life;
  }
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  
  public int getLevel() {
    return level;
  }
  public void setLevel(int level) {
    this.level = level;
  }
  
  public int getLife() {
    return life;
  }
  public void setLife(int life) {
    this.life = life;
  }
}
```

</br>

### CharacterView 클래스 
: UI 관련된 기능을 가지고 있다.

```java

package MVCPattern;

public class CharacterView {
  // State query
  public void printView(CharacterModel model) {
    System.out.println("Name :: " + model.getName());
    System.out.println("Level :: " + model.getLevel());
    System.out.println("Life :: " + model.getLife());
  }
}

```
</br>

### CharacterController 클래스 
: 위의 MVC Structure에서 나타난 기능들을 포함하고 있다.

```java
package MVCPattern;

public class CharacterController {
  private CharacterModel model;
  private CharacterView view;
  
  public CharacterController(CharacterModel model, CharacterView view) {
    this.model = model;
    this.view = view;
  }

  // State change
  public void setCharacterName(String name) {
    model.setName(name);
  }	
  public String getCharacterName() {
    return model.getName();
  }
  
  // State change
  public void setCharacterLevel(int level) {
    model.setLevel(level);
  }
  public int getCharacterLevel() {
    return model.getLevel();
  }

  // State change
  public void setCharacterLife(int life) {
    model.setLife(life);
  }
  public int getCharacterLife() {
    return model.getLife();
  }
  
  // View selection(Rendering)
  public void updateView() {
    view.printView(model);
  }
}

```

</br>
</br>

## ✅ MVC 의 장점
- 기능별로 코드를 분리하여 하나의 파일에 코드가 모이는 것을 방지하여 가독성과 코드의 재사용이 증가한다. </p>
- 각 구성요소들을 독립시켜 협업 할 때 맡은 부분 개발에만 집중 할 수 있어 개발 효율성을 높여준다 - 분업 가능 </p>
- 유지 보수성과 확장성이 보장된다.

</br>
</br>

## ✅ MVC 의 한게
- Model과 View는 서로의 정보를 갖고 있지 않는 독립적인 상태라고 하지만 Model과 View사이에는 Controller를 통해 소통을 이루기에 의존성이 완전 분리될 수 없다.</p>
- 복잡 대규모 프로그램으 ㅣ경우 다수의 View와 Model이 Controller 통해 연결되므로 컨트롤러가 불필요하게 커지는 현상 발생하기도 한다.</p>
- 이러한 현상을 Massive - View - Controller 현상이라고도 함. 이를 보안하기위해 MVVM, MVP 등의 다양한 패턴이 생겨났다.

참조 : https://velog.io/@seongwon97/MVC-%ED%8C%A8%ED%84%B4%EC%9D%B4%EB%9E%80

</br>
</br>

## ✅ Q&A
Q1. 정말로 컴포지트 패턴이 쓰였는가?
> A. GUI 패키지가 워낙 복잡해 그 내부 구조를 한눈에 파악하기 힘들지만 MVC가 처음 만들어질 무렵에는 GUI를 만들 때 직접 건드려야 할 부분이 지금보다 훨씬 많았고 </p>
> 그 시절에는 컴포지트 패턴이 MVC 의 일부분임을 분명히 알 수 있었다.

Q2. 컨트롤러에서 애플리케이션 로직을 구현하는 경우는 없는가?
> A. 없다. 컨트롤러는 뷰를 대상으로 하는 행동만 구현한다. </p>
> 컨트롤러에서 모델의 어떤 메소드를 호출해야 할지 결정하려고 어느 정도 간단한 작업을 처리할 수 있지만 그렇다고 그런 부분은 애플리케이션 로직이라고 할 수 없다.

Q3. MVC 패턴을 설명할때 컨트롤러를 뷰와 모델 사이의 중재자로 설명하는 경우를 봤는데 혹시 컨트롤러가 중재자 패턴을 구현한 건가?
> A. 중재자 패턴에서의 중재자는 객체 사이의 상호작용을 캡슐화해서 두 객체 사이의 연결을 느슨하게 만드는 역할을 한다. 그러니 컨트롤러가 어느 정도 중재자 역할 한다고 볼 수있다.</p>
> 하지만 뷰에서 모델의 상태를 알아내는 작업은 해야 하므로 뷰에도 모델의 레퍼런스가 들어있다. </p>
> 만약 컨트롤러가 진정한 중재자라면 모델의 상태를 알아낼 때도 반드시 컨트롤러를 거치도록 해야한다.

Q4. 뷰가 2개 이상있으면 컨트롤러도 2개 이상이어야 하나?
> A. 하나의 뷰에 하나의 컨트롤러가 일반적이지만 하나의 컨트롤러 클래스에서 여러개 뷰를 관리하는 것도 가능하다.

</br>
</br>
