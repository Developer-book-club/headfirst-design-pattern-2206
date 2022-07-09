## Iterator Pattern
컬렉션의 구현 방법을 노출하지 않으면서 집합체 내의 모든 항목에 접근하는 방법
- 내부 구현 방법을 외부로 노출하지 않으면서 집합체에 있는 모든 항목에 일일이 접근 가능
- 각 항목에 일일이 접근 가능한 기능은 반복자 객체가 책임 진다
- 집합체 인터페이스와 구현이 간단해짐

![](https://velog.velcdn.com/images/jiyeong/post/f3317108-6938-4102-b040-59a71dd1c4c7/image.png)
[반복자 패턴 이미지 출처](https://reactiveprogramming.io/blog/en/design-patterns/iterator)

p.373
<<인터페이스>>Aggregate > createIterator() : 공통된 인터페이스가 있으면 클라이언트는 매우 편리하게 작업 처리 가능. 클라이언트와 객체 컬렉션의 구현이 분리됨.
<br>
<<인터페이스>>Iterator : 모든 반복자가 구현해야 하는 인터페이스 제공. <br>컬렉션에 들어있는 원소에 돌아가면서 접근할 수 있게 해주는 메소드를 제공. <br> 여기서는 java.util.Iteratorㄹ르 사용. 
<br>
ConcreteAggregate : 객체 컬렉션이 들어있으며, 그 안에 들어있는 컬렉션을 Iterator로 리턴하는 메소드 구현. 모든 ConcreteAggregate는 그 안에 있는 객체 컬렉션을 대상으로 돌아가면서 반복 작업을 처리할 수 있게 해주는 ConcreteIterator이 인스턴스를 만들 수 있어야 함.
<br>ConcreteIterator: 반복 작업 중에 현재 위치를 관리하는 일을 맡음




메뉴 항목 살펴보기
```
public class MenuItem{
	String name;
   	String description;
    boolean vegetarian;
    double price;
    
    public MenuItem(String name,String description, boolean vegetarian, double price) {
    //MenuItem을 초기화할 땐 생성자에 이 값들을 모두 매개변수로 전달해야 함.
    this.name = name;
    this.description = description;
    this.vegetarian = vegetarian;
    this.price = price;
    }
    
    //이 게터 메소드를 사용해 메뉴 항목의 각 필드에 접근 가능
    public String getName(){
    	return name;
    }
    
    public String getDescription(){
    	return description;
    }
    
    public double getPrice(){
    	reuturn price;
    }
    
    public boolean isVegetarian(){
    	return vegetarian;
    }
}
```
#### 자바 종업원이 자격 요건

- printMeunu() : 메뉴에 있는 모든 항목 출력
- printBreakfastMenu() : 아침 식사 항목만 출력
- printLunchMenu() : 점심 식사 항목만 출력
- printVegetarianMenu() : 채식주의자용 메뉴 항목만 출력
- isItemVegetarian() : 해당 항목이 채식주의자용이면 true 리턴, 그렇지 않으면 false 리턴

### 반복을 캡슐화하기

01. breakfastItems의 각 항목에 순환문을 돌릴 때는 ArrayList의 size()와 get() 메소드 사용

```
for(int i=0; i < breakfastItems.size(); i++){
	MenuItem menuItem = breakfastItems.get(i);
}
```

02. lunchItems에 순환문을 돌릴 때는 배열의 length 필드와 배열 첨자 사용

```
for(int i = 0; i < lunchItems.length; i++){
	MenuItem menuItem = lunchItems[i];
}
```

03. 객체 컬렉션의 반복 작업 처리 방법을 캡슐화한 Iterator라는 객체를 만들기

```
Iterator iterator = breakfastItems.createIterator();

while(iterator.hasNext()){
	MenuItem menuItem = iterator.next();
}
```

04. 배열에 적용
```
Iterator iterator = lunchMenu.createIterator();

while(iterator.hasNext()){
	MenuItem menuItem = iterator.next();
}

```

### 반복자 패턴 알아보기
반복자 패턴은 Iterator 인터페이스에 의존함.

*컬렉션은 객체를 모아 놓은 곳에 불가함. 다양한 자료구조에 컬렉션을 보관할 수 있는데 어떤 자료구조를 사용하든 컬렉션은 컬렉션임! 그래서 집합체(aggregate)라고도 불림.

#### 객체 마을 식당 메뉴에 반복자 추가

DinerMenu 클래스에 반복자 추가 전 Iterator 인터페이스 정의
```
public interface Iterator{
	boolean hasNext();
    MenuItem next();
}
```

DinerMenu 클래스에 사용할 구상 Iterator 클래스 생성

```
public class DinerMenuIterator implements Iterator{
//Iterator 인터페이스 구현
	MenuItem[] items;
    int position = 0;
    //position은 반복 작업이 처리되고 있는 위치 저장
    
    public DinerMenuIterator(MenuItem[] items){
    //생성자는 반복 작업을 수행할 멘 항목 배열을 인자로 받아들임
    	this.items = items;
    }
    
    public MenuItem next(){
    //next() 메소드는 배열의 다음 원소를 리턴하고 position 변수 값을 1 증가
    	MenuItem menuItem = items[position];
        position = position + 1;
        return menuItem;
    }
    
    public boolean hasNext(){
    //hasNext() 메소드는 배열에 있는 모든 원소를 돌았는 지 확인 후 돌아야 할 원소가 있을 때 true 리턴
    	if(position >= items.length; || items[position] == null){
        return false;
        } else {
        	return true;
        }
    }
}
```

#### 객체 마을 식당 메뉴에서 반복자 사용하기

```
public class DinerMenu{
	static final int MAX_ITEMS = 6;
    int numberOfItems = 0;
    MenuItem[] menuItems;
    
    public Iterator createIterator(){
    	return new DinerMenuIterator(menuItems);
    }
    //createIterator() 메소드 : menuItems의 배열을 가지고 DinerMenuIterator를 생성한 다음 클라이언트에게 리턴
    //Iterator 인터페이스 리턴. 
}
```

#### 종업원 코드에 반복자 적용하기

```
public class Waitress{
	PancakeHouseMenu pancakeHouseMenu;
    DinerMenu dinerMenu;
    
    public Waitress(PancakeHouseMenu pancakeHouseMenu, DinerMenu dinerMenu){
    	this.pancakeHouseMenu = pancakeHouseMenu;
        this.dinerMenu = dinerMenu;
    }
    
    public void printMenu(){
    	//2개의 반복자 생성
        Iterator pancakeIterator = pancakeHouseMenu.createIterator();
        Iterator dinerIterator = dinerMenu.createIterator();
        
        //반복자를 가지고 오버로드된 printMenu() 메소드 호출
        printMenu(pancakeIterator);
        printMenu(dinerIterator);
    }
    
    //오버로드된 printMenu() 메소드는 반복자를 써서 모든 메뉴 항목에 접근해 그 내용을 출력
    public void printMenu(Iterator iterator){
    	while(iterator.hasNext()) {
        	MenuItem menuItem = iterator.next();
            //이름, 가격, 설명을 출력
        }
    }
}
```

종업원 코드 테스트

```
public class MenuTestDrive{
	public static void main(String[] args){
    	PancakeHouseMenu pancakeHouseMenu = new PancakeHouseMenu();
        
        DinerMenu dinerMenu = new DinerMenu();
        
        Waitress waitress = new Waitress(pancakeHouseMenu, drinerMenu); //종업원 생성, 두 메뉴를 인자로 전달해야 함.        
      	
        waitress.printMenu();
    }
}
```

### 반복자 패턴의 특징 알아보기
PancakeHouseIterator와 DinerMenuIterator를 받은 다음 getIterator() 메소드만 추가

- 메뉴 구현법이 캡슐화되어 있어 종업원은 메뉴에서 메뉴 항목의 컬렉션을 어떤 식으로 저장하는 지 알 수가 없음.
- 반복자만 구현하다면 다형성을 활용해서 어떤 컬렉션이든 1개의 순환문으로 처리 가능
- 종업원은 인터페이스(반복자)만 알면 됨

#### java.util.Iterator 적용하기
menuItem ArrayList의 iterator() 메소드 호출
```
public Iterator<MenuItem> createIterator(){
	return menuItems.iterator();
}
```

```
import java.util.Iterator;

public class DinerMenuIterator implements Iterator<MenuItem>{
	MenuItem[] items;
    int position = 0;
    
    public DinerMenuIterator(MenuItem[] items){
    	this.items = items;
    }
    
    public MenuItem next(){}
    
    public boolean hasNext(){}
    
    public void remove(){
    	throw new UnsupportedOperationException
    }
}
```
```
public interface Menu{
	public Iterator<MenuItem> createIterator();
    //클라이언트에서 메뉴에 들어있는 항목의 반복자를 획득할 수 있게 해주는 인터페이스
}
```
```
import java.util.Iterator;

public class Waitress{
	Menu pancakeHouseMenu;
    Menu dinerMenu;
    
    public Waitress(Menu pancakeHouseMenu, Menu dinerMenu){
    	this.pancakeHouseMenu = pancakeHouseMenu;
        this.dinerMenu = dinerMenu;
    }
    
    public void printMenu(){
    	Iterator<MenuItem> pancakeIterator = pancakeHouseMenu.createIterator();
        Iterator<MenuItem> dinerIterator = dinerMenu.createIterator();
        printMenu(pancakeIterator);
        printMenu(dinerIterator);
    }
    
    private void printMenu(Iterator iterator){
    	while(iterator.hasNext()){
        	MenuItem menuItem = iterator.next();
        }
    }
}
```
## 단일 역할의 원칙

집합체에서 내부 컬렉션 관련 기능과 반복자용 메소드 관련 기능을 전부 구현한다면 메소드 개수가 늘어나서 효율에 안 좋은 이유 : <br>
클래스에서 원래 그 클래스의 역할(집합체 관리) 외에도 다른 역할(반복자 메소드)를 처리할 때 2가지 이유로 클래스가 바뀔 수 있음<br>
1. 컬렉션이 어떤 이유로 바뀌게 되면 그 클래스도 바뀌어야 함.
2. 반복자 관련 기능이 바뀌었을 때도 클래스가 바뀌어야 함.

<br>
**new!**변경과 관련된 디자인 원칙
<br>
어떤 클래스가 바뀌는 이유는 하나뿐이어야 한다.

#### 향상된 for 순환문 사용하기
ArrayList도 Iterable이므로 자바의 향상된 for 순환문 사용 가능.
배열은 자바 컬렉션이 아니라서 Iteratable 인터페이스를 구현하지 않음.

```
public void printMenu(Iterable<MenuItem> iterable){
	for(MenuItem menuItem : iterable){
    	//menuItem 출력
        printMenu(lunchItems); // 컴파일 에러! 배열은 Iterable이 아님
    }
}
```

#### 객체 마을 메뉴 
HashTable도 Iterator를 지원하는 자바 컬렉션.

```
public class CafeMenu implements Menu{
//Menu를 구현함으로써 waitress도 다른 메뉴와 같은 방식으로 사용 가능
	Map<String, MenuItem> menuItems = new HashMap<String, MenuItem>(); 
    //Hasttable은 다양한 값을 저장할 때 많이 쓰이는 자료 구조
    
    public CafeMenu(){
 		//메뉴 항목 생성자에서 초기화   
    }
    
    public void addItem(String name, String description, boolean vegetarian, double price){//새 menuItem을 생성하고, menuItem 해시테이블에 추가
    	MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
        menuItems.put(name, menuItem); // menuItem 객체가 값으로 저장됨 & 항목 이름이 키로 쓰임
    }
    
~~    public Map<String, MenuItem> getMenuItems(){
    	return menuItems;
    }~~
    //menuItems 구현을 waitress에 모두 공개할 필요 없음
    
    public Iterator<MenuItem> createIterator(){
    	return menuItems.values().iterator();
    }
    //createIterator()을 구현 -> HashMap 전체를 대상으로 반복자를 리턴하는 것이 아니라 값을 대상으로 반복자 리턴
}
```
HashMap은 키와 값을 모두 지원함. 값(MenuItem)을 대상으로 하는 반복자 가져오기는 조금 수월한 편!
menuItems.values() : HashMap의 값들을 가져옴. HashMap에 있는 모든 객체의 컬렉션.
menuItems.values().iterator() : 컬렉션에서 java.util.Iterator 유형의 객체를 리턴하는 iterator() 메소드 지원.

#### 종업원 코드에 카페 메뉴 추가


```
public class Waitress{
	PancakeHouseMenu pancakeHouseMenu;
    DinerMenu dinerMenu;
    Menu cafeMenu;
    //CafeMenu 객체도 다른 메뉴와 함께 waitress 클래스의 생성자에 전달됨. 그 객체는 인스턴스 변수로 저장됨. 
    
    public Waitress(PancakeHouseMenu pancakeHouseMenu, DinerMenu dinerMenu, Menu cafeMenu){
    	this.pancakeHouseMenu = pancakeHouseMenu;
        this.dinerMenu = dinerMenu;
        this.cafeMenu = cafeMenu;
    }
    
    public void printMenu(){
    	//2개의 반복자 생성
        Iterator<MenuItem> pancakeIterator = pancakeHouseMenu.createIterator();
        Iterator<MenuItem> dinerIterator = dinerMenu.createIterator();
        Iterator<MenuItem> cafeIterator = cafeMenu.createIterator();
        
       
        //반복자를 가지고 오버로드된 printMenu() 메소드 호출
        printMenu(pancakeIterator);
        printMenu(dinerIterator);
         printMenu(cafeIterator);
    }
    
    //오버로드된 printMenu() 메소드는 반복자를 써서 모든 메뉴 항목에 접근해 그 내용을 출력
    public void printMenu(Iterator iterator){
    	while(iterator.hasNext()) {
        	MenuItem menuItem = iterator.next();
            //이름, 가격, 설명을 출력
        }
    }
}
```
#### 통합 식당 코드 테스트
```
public class MenuTestDrive{
	public static void main(String[] args){
    	PancakeHouseMenu pancakeHouseMenu = new PancakeHouseMenu();
        DinerMenu dinerMenu = new DinerMenu();
        CafeMenu cafeMenu = new CafeMenu();
        
        Waitress waitress = new Waitress(pancakeHouseMenu, dinerMenu, cafeMenu); //종업원 생성, 두 메뉴를 인자로 전달해야 함.        
      	
        waitress.printMenu();
    }
}
```

반복자와 컬렉션 p.387
<<인터페이스>>iterable : collection 인터페이스에서 Iterable 인터페이스를 구현
<<인터페이스>>collection 
_주의!
HashMapm은 반복자를 간접적으로 지원! 값들을 대상으로 반복 작업을 하고 싶다면 HashMap에서 values를 가져온 다음 그 반복자를 받아와야 함!
컬렉션과 반복자를 사용하면 모든 컬렉션 객체에서 자신을 대상으로 하는 반복자를 리턴할 줄 앎._
ArrayList의 iterator() 메소드 호출 시 그 ArrayList용 구상 클래가 리턴됨!

메뉴들을 ArrayList로 묶어서 각 메뉴를 대상으로 반복 작업을 수행하기
```
public class Waitress{
	List<Menu> menus;
    
    public Waitress(List<Menu> menus){
    	this.menus = menus;
        //이제 각 메뉴를 따로 받지 않고 목록으로 받아 옴
    }
    
    public void printMenu(){
    	Iterator<Menu> menuIterator = menus.iterator();
        //각 메뉴에 반복 작업 수행. 각 메뉴의 반복자를 오버로드된 printMenu() 메소드에 넘겨줌
        while(menuIterator.hasNext()){
        	Menu menu = menuIterator.next();
            printMenu(menu.createIterator());
        }
    }
    
    void printMenu(Iterator<MenuItem> iterator){
    	while(iterator.hasNext()){
        	MenuItem menuItem = iterator.next();
        }
    }
}
```

## Composite Pattern
객체를 트리구조로 구성해서 **부분-전체 계층구조**를 구현함. 컴포지트 패턴 사용 시 클라이언트에서 개별 객체와 복합 객체를 똑같은 방법으로 다룰 수 있음. 

![](https://velog.velcdn.com/images/jiyeong/post/54339c45-b38e-4193-9b4d-57e576c77d96/image.png)

[이미지 출처](https://www.conradbock.org/relation4.html)

- 컴포지터 패턴 사용 시 객체의 구성과 개별 객체를 노드로 가지는 트리 형태의 객체 구조로 만들 수 있음
- 이런 복합 구조(composite structure)를 사용 시 복합 객체와 개별 객체를 대상으로 똑같은 작업 적용 가능.
- 즉, 복합 객체와 개별 객체 구분할 필요x

![](https://velog.velcdn.com/images/jiyeong/post/77ccda12-602c-4bab-a2c5-75e40c878b68/image.png)


출처 : research gate

클라이언트 : component 인터페이스를 사용해 복합 객체 내의 객체 조작 가능

Component : 복합 객체 내에 들어있는 모든 객체의 인터페이스 정의. 복합 노드와 잎에 관한 메소드까지 정의. add(), remove(), getChild()와 몇 가지 작업의 기본 행동을 정의할 수 있음.

Composite : Leaf와 관련된 기능도 구현해야함. 그런 기능들이 복합 객체에 별 쓸모가 없으면 예외를 던져도 됨. 자식이 있는 구성 요소의 행동을 정의하고 자식 구성 요소를 저장하는 역할을 맡음.

Leaf : 자식이 없으며, 그 안에 들어있는 원소의 행동 정의. Composite에서 지원하는 기능을 구현하면 됨.

복합 객체(composite)에는 구성 요소(component)가 들어있음. 
구성 요소는 두 종류로 나뉨. 하나는 복합 객체이고 다른 하나는 잎이다.
구성 요소는 재귀 구조로, 복합 객체에는 자식들이 들어있으며, 그 자식들은 복합 객체일 수도 있고 아니면 그냥 잎일 수도 있음.

### 메뉴 구성 요소 구현

모든 구성 요소에 MenuComponent 인터페이스를 구현해야만 함. 
하지만 잎과 노드는 각각 역할이 다르므로 모든 메소드에 알맞는 기본 메소드 구현은 불가능. 그래서 자기 역할에 맞지 않는 상황을 기준으로 예외를 던지는 코드를 기본 구현으로 제공하지 않음.

```
public abstract class MenuComponent{
//MenuComponent에서는 모든 메소드를 기본적으로 구현해 둠
//UnsupportedOperationException() : 자기 역할에 맞지 않는 메소드는 오버라이드하지 않고 기본 구현은 그대로 사용

	public void add(MenuComponent menuComponent){
    	throw new UnsupportedOperationException();
    }
    
    public void remove(MenuComponent menuComponent){
    	throw new UnsupportedOperationException();
    }
    
    public MenuComponent getChild(int i){
    	throw new UnsupportedOperationException();
    }
    
    public String getName(){
   		throw new UnsupportedOperationException();
    }
    
     public String getDescription(){
   		throw new UnsupportedOperationException();
    }
    
     public String getPrice(){
   		throw new UnsupportedOperationException();
    }
    
     public String isVegetarian(){
   		throw new UnsupportedOperationException();
    }
    
     public void print(){
   		throw new UnsupportedOperationException();
    }
}
```

#### 메뉴 항목 구현

```
public class MenuItem extends MenuComponent{
	String name;
    String description;
    boolean vegetarian;
    double price;
    
    public MenuItem(String name, String description, boolean vegetarian, double price){
    	this.name = name;
        this.description = description;
        this.vegetarian = vegetarian;
        this.price = price;
    }
    
    public String getName(){
    	return name;
    }
    
    public String getDescription(){
    	return description;
    }
    
    public double getPrice(){
    	return price;
    }
    
    public boolean isVegetarian(){
    	return vegetarian;
    }
    
    public void print(){
    if(isVegetarian()){
    	System.out.println("v");
    }
    }
}
```

#### 메뉴 구현하기

```
public class Menu extends MenuComponent{
	List<MenuComponent> menuComponents = new ArrayList<MenuComponent>();
    String name;
    String description;
    
    public Menu(String name, String description){
    //메뉴마다 이름과 설명을 붙임
    	this.name = name;
        this.description = description;
    }
    
    public void add(MenuComponent menuComponent){
    	menuComponents.add(menuComponent);
    }
    
    public void remove(MenuComponent menuComponent){
    	menuComponents.remove(menuComponent);
    }
    
    public MenuComponent getChild(int i){
    	return menuComponents.get(i);
    }
    
    public String getName(){
    	return name;
    }
    
    public String getDescription(){
    	return description;
    }
    
    public void print(){
    //프린트
    	for(MenuComponent menuComponent : menuComponents){
        menuComponent.print();}
    }
}
```

#### 종업원 코드에 컴포지터 적용하기

public class Waitress{
	MenuComponent allMenus;
    
    public Waitress(MenuComponent allMenus){
    //다른 메뉴를 포함하고 있는 최상위 메뉴를 구성 요소로 넘겨 주기
    	this.allMenus = allMenus;
    }
    
    public void printMenu(){
    //메뉴 전체의 계층 구조를 출력하고 싶다면 그냥 최상위 메뉴 출력
    	allMenus.print();
    }
}

#### 메뉴 코드 테스트

```
public class MenuTestDrive{
	public static void main(String args[]){
    	MenuComponent pancakeHouseMenu = new Menu("팬케이크 하우스 메뉴","아침메뉴");
        MenuComponent dinerMenu = new Menu("객체마을 식당 메뉴","점심메뉴");
        MenuComponent cafeMenu = new Menu("카페 메뉴","저녁메뉴");
        MenuComponent dessertMenu = new Menu("디저트 메뉴","디저트를 즐겨보세요");
        
        MenuComponent allMenus = new Menu("전체 메뉴","전체 메뉴");
        
        allMenus.add(pancakeHouseMenu);
        allMenus.add(dinerMenu);
        allMenus.add(cafeMenu);
        
        dinerMenu.add(new MenuItem(
        	"파스타"
            "마리나라 소스 스파게티, 효모빵도 드립니다.",
            true,
          	3.89));
            
       	dinerMenu.add(dessertMenu);
        
        dessertMenu.add(new MenuItem(
        	"애플 파이"
            "바삭바삭한 크러스트에 바닐라 아이스크림이 얹혀 있는 애플 파이.",
            true,
          	1.59
        ));
        
        Waitress waitress = new Waitress(allMenus);
        
        waitress.printMenu();
    }
}
```

한 클래스에서 한 역할만 맡아야 하는 컴포지터 패턴에서 한 클래스에 2가지 역할을 넣고 있는데, 계층 구조를 관리하는 일과 메뉴 관련 작업을 처리해야하지 않나?
<br>컴포지트 패턴에서는 단일 역할 원칙을 깨는 대신 투명성을 확보하는 패턴.
**투명성(transparency)**이란 Component 인터페이스에 자식들을 관리하는 기능과 잎으로써의 기능을 전부 넣어서 클라이언트가 복합 객체와 잎을 똑같은 방식으로 처리할 수 있도록 만들 수 있음. 어떤 원소가 복합 객체인지 잎인지가 클라이언트에게는 투명하게 보임.

### Composite Pattern 더 알아보기
- 계층구조를 가진 객체 컬렉션에서 그 객체를 모든 똑같은 방식으로 다루고 싶을 때 쓰이는 패턴
- 계층구조란 최상위 구성 요소와 다른 구성 요소를 포함하고 있는 복합 객체(composite object)로 이뤄져있고, 다른 구성 요소를 포함하고 있지 않는 구성 요소는 잎 객체(leaf object)라고 함.
- 복합 객체와 잎 객체에 똑같은 메소드를 호출하는 것으로 복합 객체가 명령을 받았을 때 그 안에 다른 구성 요소에게도 같은 명령을 내림.
- 투명하게 작동하려면 복합 객체 내에 있는 모든 객체의 인터페이스가 똑같아야 함. 그렇지 않으면 클라이언트가 각 객체의 인터페이스에 신경을 써야 해서 처음에 달성하려고 했던 목표에서 벗어나게 된다. 
- 메소드 처리는 아무것도 하지 않거나 null 또는 false를 상황에 맞게 리턴하거나 예외를 던지면 됨. 예외를 던진다면, 클라이언트에서 예외 상황을 적절히 처리할 준비를 해야 함.
- 메소드 구조를 잘 조절해서 기본 구현으로 그럴듯한 행동을 하게 만든다면 클라이언트가 어떤 형식의 객체를 다룰 지 몰라도, 어떤 메소드를 호출하면 안 되는 지 알 수 있음. 
- 이러한 문제는 서로 다른 객체에 서로 다른 인터페이스를 요구해서 엉뚱한 메소드가 호출되지 않게 하는 안전한 버전의 컴포지트 패턴. 이렇게 하려면 객체를 올바르게 캐스팅 할 수 있도록, 메소드를 호출하기 전 형식을 매번 확인해야 함.
- 복합 객체와 잎 객체는 트리구조로 이뤄져있고, 뿌리는 최상위 복합 객체로, 그 자식들은 모두 복합 객체나 잎으로 이뤄짐.
- 트리 내에서 돌아다니기 편하도록 자식에게 부모 노드의 포인터를 넣어 자식한테 부모의 레퍼런스가 있을 수 있음. 그리고 자식의 레퍼런스를 지워야 할 때도 반드시 부모한테 자식을 지우라고 해야 하는데, 레퍼런스를 만들어 두면 더 쉽게 삭제 가능.
- 구현할 때 고려해야할 점으로 자식의 순서도 고려해야 함. 어떤 복합 객체에서 자식을 특별한 순서에 맞게 저장해야 한다면 자식을 추가하거나 제거할 때 더 복잡한 관리 방법 사용 필요. 계층 구조를 돌아다니는 데 있어서도 더 많은 주의 필요.
- 캐시와 관련해서 복합 구조가 너무 복잡하거나, 복합 객체 전체를 도는데 너무 많은 자원이 필요하다면 복합 노드를 캐싱해 두면 도움이 됨. 예를 들어, 복합 객체에 있는 모든 자식이 어떤 계산을 하고, 그 계산을 반복 작업한다면 계산 결과를 임시로 저장하는 캐시를 만들어서 속도 향상 가능.
- 컴포지트 패턴의 ㅈ방점은 클라이언트를 단순화시킬 수 있다는 점. 클라이언트들은 복합 객체를 사용하고 있는 지, 잎 객체를 사용하고 있는 지를 신경 쓰지 않아도 됨. 올바른 객체에 관한 메소드를 호출하고 있는 지 확인하려고 지저분하게 if 문을 쓰지 않아도 된디. 메소드 하나만 호출하면 전체 구조를 대상으로 반복 작업을 처리할 수도 있음.

## Summary
- 객체 지향 원칙 new! 어떤 클래스가 바뀌는 이유는 하나뿐이어야 한다.
- **반복자 패턴 ** : 컬렉션의 구현 방법을 노출하지 않으면서 집합체 내의 모든 항목에 접근하는 방법 제공
- **컴포지트 패턴** : 객체를 트리구조로 구성해서 부분-전체 계층 구조 구현. 컴포지트 패턴을 사용하면 클라이언트에서 개별 객체와 복합 객체를 똑같은 방법으로 다룰 수 있음.
- 반복자를 사용하면 내부 구조를 드러내지 않으면서 클라이언트가 컬렉션 안에 들어있는 모든 원소에 접근하도록 할 수 있음.
- 반복자 패턴을 사용하면 집합체를 대상으로 하는 반복 작업을 별도의 객체로 캡슐화 할 수 있음
- 반복자 패턴을 사용하면 컬렉션에 있는 모든 데이터를 대상으로 반복 작업을 하는 역할을 컬렉션에서 분리할 수 있음
- 반복자 패턴을 쓰면 반복 작업에 똑같은 인터페이스를 적용할 수 있으므로 집합체에 있는 개체를 활용하는 코드를 만들 때 다형성을 활용할 수 있음
- 한 클래스에는 될 수 있으면 한 가지 역할만 부여하는 것이 좋음
- 컴포지트 패턴은 개별 객체와 복합 객체를 모두 담아 둘 수 있는 구조 제공
- 컴포지트 패턴 사용 시 클라이언트가 개별 객체와 복합 객체를 똑같은 방법으로 다룰 수 있음
- 복합 구조에 들어있는 것을 구성 요소라고 부름. 구성 요소에는 복합 객체와 잎 객체가 있다.
- 컴포지트 패턴을 적용 시 여러가지 장단점 고려 필요. 상황에 따라 투명성과 안전성 사이에서 적절한 균형을 찾아야 함.
