# Prototype Pattern

> Original 객체를 새로운 객체에 복사하여 필요에 따라 수정하는 메커니즘을 제공하는 패턴
> 
> (프로토타입 : 실제 제품을 만들기에 앞서 테스트를 위한 샘플 제품)

어떤 클래스의 인스턴스를 만들 때 자원과 시간이 많이 들거나 복잡하다면 프로토타입 패턴을 사용한다.

기존의 객체를 응용하여 새로운 객체를 만든다.

- 장점
  - 클라이언트는 새로운 인스턴스를 만드는 과정을 몰라도 된다.
  - 클라이언트는 구체적인 형식을 몰라도 객체를 생성할 수 있다.
  - 상황에 따라서 객체를 새로 생성하는 것보다 객체를 복사하는 것이 더 효율적일 수 있다.
  
- 활용법
    - 시스템에서 복잡한 클래스 계층구조에 파묻혀 있는 다양한 형식의 객체 인스턴스를 새로 만들어야 할 때 유용

- 단점
    - 때때로 객체의 복사본을 만드는 일이 매우 복잡
