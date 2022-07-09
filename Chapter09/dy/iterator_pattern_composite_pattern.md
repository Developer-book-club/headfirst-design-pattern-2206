# ✏️ 반복자 패턴 Iterator Pattern

**반복자 패턴** : 컬렉션의 구현 방법을 노출하지 않으면서 집합체 내의 모든 항목에 접근하는 방법을 제공함.

- 반복자 패턴을 사용하면 각 항목에 일일이 접근할 수 있게 해주는 기능을 집합체가 아닌 반복자 객체가 책임진다는 장점이 있다.
- 반복자 객체와 집합체의 기능이 분리되면 집합체 인터페이스와 구현이 간단해지고, 각자에게 중요한 일만 처리할 수 있게 된다.
- 컬렉션 객체 안에 들어있는 모든 항목에 접근하는 방식이 통일되어 있으면, 종류에 관계없이 모든 집합체에 사용할 수 있는 다형적인 코드를 만들 수 있다.

![반복자패턴](https://upload.wikimedia.org/wikipedia/commons/thumb/1/13/Iterator_UML_class_diagram.svg/1000px-Iterator_UML_class_diagram.svg.png)

## 📒 단일 역할 원칙 Single Responsibility Principle
> 어떤 클래스가 바뀌는 이유는 하나뿐이어야 한다.    
> 즉, 클래스는 단 핸개의 책임을 가져야 한다.  

집합체에서 내부 컬렉션 관련 기능과 반복자용 메소드 관련 기능을 전부 구현한다면,  
1. 컬렉션이 어떤 이유로 바뀌게 되면 그 클래스도 바뀌어야 한다.
2. 반복자 관련 기능이 바뀌었을 때도 클래스가 바뀌어야 한다.

어떤 클래스에서 맡고 있는 모든 역할은 나중에 코드 변화를 불러올 수 있다. 역할이 2개 이상 있으면 바뀔 수 있는 부분이 2개 이상이 된다.  

### 응집도 cohension

한 클래스 또는 모듈이 특정 목적이나 역할을 얼마나 일관되게 지원하는지를 나타내는 척도.
- 응집도가 높다는 것: 서로 연관된 기능이 묶여있다는 것
- 응집도가 낮다는 것: 서로 상관 없는 기능들이 묶여있다는 것

SRP를 잘 지키는 클래스는 2개 이상의 역할을 맡고 있는 클래스에 비해 응집도가 높고, 관리하기 쉽다.

## ✏️ 컴포지트 패턴 Composite Pattern

**컴포지트 패턴** : 객체를 트리구조로 구성해서 부분-전체 계층구조를 구현한다. 컴포지트 패턴을 사용하면 클라이언트에서 개별 객체와 복합 객체를 똑같은 방법으로 다룰 수 있다.

- 객체의 구성과 개별 객체를 노드로 가지는 트리 형태의 객체 구조를 만들 수 있다.
- 복합 객체와 개별 객체를 대상으로 똑같은 작업을 적용할 수 있다. 복합 객체와 개별 객체를 구분할 필요가 거의 없어진다.

![컴포지트패턴](https://upload.wikimedia.org/wikipedia/commons/thumb/5/5a/Composite_UML_class_diagram_%28fixed%29.svg/960px-Composite_UML_class_diagram_%28fixed%29.svg.png)

![컴포지트패턴-메뉴](https://mblogthumb-phinf.pstatic.net/MjAxNzA1MTZfMTA5/MDAxNDk0OTM5NTEyMTA2.a74SG4k25nukqG6boytFM4YLMVp4Yhr64mJbSSZeY0wg.L0nyxXvvgVmWJ6MtLXwmGf5bpZdA_ssf5yXFJmcnuMQg.PNG.cestlavie_01/3.png?type=w800)

#### 부분-전체 계층 구조 part-whole hierarchy

부분들이 계층을 이루고 있지만 모든 부분을 묶어서 전체로 다룰 수 있는 구조

![부분-전체 계층 구조](https://t1.daumcdn.net/cfile/tistory/9923A84E5C84B5203A)
