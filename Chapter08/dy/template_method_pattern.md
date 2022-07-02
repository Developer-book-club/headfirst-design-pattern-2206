# ✏️ 템플릿 메소드 패턴 Template Method Pattern

**템플릿 메소드 패턴** : 알고리즘의 골격을 정의한다. 알고리즘의 일부 단계를 서브클래스에서 구현할 수 있으며, 알고리즘의 구조는 그대로 유지하면서 알고리즘의 특정 단계를 서브클래스에서 재정의할 수도 있다.

![template method pattern](https://upload.wikimedia.org/wikipedia/commons/thumb/5/52/Template_Method_UML.svg/600px-Template_Method_UML.svg.png)

- 알고리즘의 틀(템플릿)을 만들고, 그 클래스에서 알고리즘을 독점하여 작업을 처리한다.
- 템플릿의 서브클래스에서 코드를 재사용할 수 잇다.
- 알고리즘이 한 군데 모여 있으므로 한 부분만 고치면 된다.
- 템플릿 클래스에 알고리즘 지식이 집중되어 있으며 일부 구현만 서브클래스에 의존한다.

## ☕️🫖 커피와 홍차를 만들 때 공통된 제조법을 뽑아 추상화하기

#### 1. 먼저, 커피를 만드는 방법과 홍차를 만들 때 겹치는 제조법을 추려본다.

1. 물을 끓인다. → 베이스 클래스에 추상화
2. 뜨거운 물을 사용해서 커피 또는 찻잎을 우려낸다. → 커피 or 찻잎의 차이
3. 만들어진 음료를 컵에 따른다. → 베이스 클래스에 추상화
4. 각 음료에 맞는 첨가물을 추가한다. → 설탕/우유 or 레몬의 차이

#### 2. 위의 4 스텝의 제조법을 메소드로 표현해본다.

```java
void prepareRecipe() {
  boilWater();
  brew();
  pourInCup();
  addCondiments();
}
```
`prepareRecipe()`가 템플릿 메소드다.  
카페인 음료를 만드는 알고리즘 템플릿 역할을 한다.

#### 3. CaffeinBeverage 슈퍼 클래스에서 제조법 사용하기

CaffeinBeverage는 추상 클래스다.

```java
public abstract class CaffeinBeverage {

  final void prepareRecipe() {
    boilWater();
    brew();
    pourInCup();
    addCondiments();
  }

  abstract void brew();

  abstract void addCondiments();

  void boilWater() {
    System.out.println("물 끓이는 중");
  }

  void pourInCup() {
    System.out.println("컵에 따르는 중");
  }
} 
```

💡 `prepareRecipe()`가 **final**인 이유  

- 서브 클래스가 이 메소드를 아무렇게나 오버라이드 하지 못하게 하기 위함.


커피와 차 클래스는 CaffeinBeverage를 `extends`하고, `brew()`, `addCondiments()`를 정의하여 사용한다.  
고로, `boilWater()`, `pourInCup()`는 베이스 클래스에 일반화 되었고,  
`brew()`, `addCondiments()`는 서브클래스에 의존한다.  

> 템플릿 메소드는 알고리즘의 각 단계를 정의하며, 서브클래스에서 일부 단계를 구현할 수 있도록 유도한다.