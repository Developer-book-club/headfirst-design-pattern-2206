# 🌉 브리지 패턴 Bridge Pattern

**브리지 패턴**: 구현과 추상화 부분까지 변경하고자 할 때 사용

![브리지 패턴](https://www.dofactory.com/images/diagrams/net/bridge.gif)

### 🌉 브리지 패턴의 장점
- 구현과 인터페이스를 완전히 결합하지 않았기 때문에 구현과 추상화 부분을 분리할 수 있다.
- 추상화된 부분과 실제 구현 부분을 독립적으로 확장할 수 있다.
- 추상화 부분을 구현한 구상 클래스가 바뀌어도 클라이언트에는 영향을 끼치지 않는다.

### 🌉 브리지 패턴의 단점
- 디자인이 복잡해진다.

### 🌉 브리지 패턴의 활용법
- 여러 플랫폼에서 사용해야 하는 그래픽스와 윈도우 처리 시스템
- 인터페이스와 실제 구현할 부분을 서로 다른 방식으로 변경해야 할 때

### 🕹 만능 리모컨 만들기 예제
- 리모컨 자체는 똑같이 추상화 부분을 바탕으로 한다.
- TV모델마다 엄청나게 많은 구현 코드를 사용해야 한다.

# ✏️ 🏗 빌더 패턴 Builder Pattern

**빌더 패턴**: 제품을 여러 단계로 나눠서 만들도록 제품 생산 단계를 캡슐화하고 싶을 때 사용

![빌더 패턴](https://upload.wikimedia.org/wikipedia/commons/thumb/f/f3/Builder_UML_class_diagram.svg/1400px-Builder_UML_class_diagram.svg.png)

### 🏗 빌더 패턴의 장점
- 복합 객체 생성 과정을 캡슐화한다.
- 여러 단계와 다양한 절차를 거쳐 객체를 만들 수 있다.
- 제품의 내부 구조를 클라이언트로부터 보호할 수 있다.
- 클라이언트는 추상 인터페이스만 볼 수 있기에 제품을 구현한 코들르 쉽게 바꿀 수 있다.

### 🏗 빌더 패턴의 단점
- 복합 객체 구조를 구축하는 용도로 많이 쓰인다.
- 팩토리를 사용할 때보다 객체를 만들 때 클라이언트에 관해 더 많이 알아야 한다.

# ⛓ 책임 연쇄 패턴 Chain of Responsibility Pattern

**책임 연쇄 패턴**: 1개의 요청을 2개 이상의 객체에서 처리해야 할 때 사용
![책임 연쇄 패턴](https://k0102575.github.io/assets/image/2020-02-23-chain-of-responsibility-pattern/diagram.jpeg)

### ⛓ 책임 연쇄 패턴의 장점
- 요청을 보낸 쪽과 받는 쪽을 분리할 수 있다.
- 객체는 사슬의 구조를 몰라도 되고, 그 사슬에 들어있는 다른 객체의 직접적인 레퍼런스를 가질 필요도 없으므로 객체를 단순하게 만들 수 있다.
- 사슬에 들어가는 객체를 바꾸거나 순서를 바꿈으로써 역할을 동적으로 추가하거나 제거할 수 있다.

### ⛓ 책임 연쇄 패턴의 활용법
- 윈도우 시스템에서 마우스 클릭과 키보드 이벤트를 처리할 때

### ⛓ 책임 연쇄 패턴의 단점
- 요청이 반드시 수행된다는 보장이 없다. 사슬 끝까지 갔는데도 처리되지 않을 수 있다. (이는 장점이 될 수도 있다.)
- 실행 시에 과정을 살펴보거나 디버깅하기 힘들다.

# 🥊 플라이웨이트 패턴 Flyweight Pattern

**플라이웨이트 패턴**: 인스턴스 하나로 여러 개의 `가상 인스턴스`를 제공하고 싶을 때 사용

![플라이웨이트 패턴](https://blog.kakaocdn.net/dn/HsGjp/btrjk0nm6Hj/kgKRLCqciXqcti1vnmAlfK/img.jpg)

### 🥊 플라이웨이트 패턴의 장점
- 실행 시에 객체 인스턴스의 개수를 줄여서 메모리를 절약할 수 있다.
- 여러 `가상`객체의 상태를 한 곳에 모아 둘 수 있다.

### 🥊 플라이웨이트 패턴의 활용법
- 어떤 클래스의 인스턴스가 아주 많이 필요하지만 모두 똑같은 방식으로 제어해야할 때

### 🥊 플라이웨이트 패턴의 단점
- 특정 인스턴스만 다른 인스턴스와 다르게 행동할 수 없다.