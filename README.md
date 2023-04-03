# Spring-Data-JPA-Practice

Use JPA practice for Springboot. 
readed book is Springboot learning by code.

# Spring Data JPA의 소개
## JPA (Java Persistence API)
- java 언어를 통해서 데이터베이스와 같은 영속 계층을 처리하고자 하는 스펙

## ORM과 JPA
- ORM (Object Relational Mapping)
- ORM은 객체지향 패러다임을 관계현 데이터베이스에 보존하는 기술
- 패러다임 입장에서는 객체지향 패러다임을 관계형 패러다임으로 Mapping 해주는 개념
- 객체지향의 구조가 관계형 데이터베이스와 유사하다는 점에서 시작됨.
- ORM이 좀 더 상위 개념이 되고 JPA는 java라는 언어의 국한된 개념

>클래스를 테이블과 비유하고 인스턴스를 Row와 비유함
여기서의 차이가 '객체'라는 단어가 '데이터 + 행위(메서드)' 라는 의미라면 'Row'는 '데이터'만을 의미한다는 점이 다르다.
데이터베이스에서는 '개체(entity)'라는 용어를 사용

- JPA는 단순한 스펙이기 때문에 해당 스펙을 구현하는 구현체마다 회사의 이름이나 프레임워크의 이름이 다른데 그 중 가장 유명한 것이 Hibernate

## Spring Data JPA와 JPA
- 스프링 부트 JPA는 Hibernate 라는 구현체를 이용 함.
- Hibernate는 오픈소스로 ORM을 지원하는 프레임워크
>Hibernate는 단독으로 프로젝트에 적용이 가능한 독립된 프레임워크이기 때문에 부트가 아닌 스프링만을 이용한다고 해도 연동하여 JPA를 사용 가능 함.

### _**Spring Data JPA <-> Hibernate <-> JDBC <-> DB**_
위와 같은구성을 이용

---

## Entity Class와 JpaRepository
```
@Entity
@Table(name = "tbl_memo")
@ToString
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;
}

```

### @Entity
엔티티 클래스는 Spring Data JPA에서 반드시 @Entity 어노테이션을 추가해야 합니다.
@Entity는 해당 클래스가 엔티티를 위한 클래스이며, 해당 클래스의 인스턴스들이 JPA로 관리되는 엔티티 객체라는 것을 의미합니다.

또한 @Entity가 붙은 클래스는 옵션에 따라서 자동으로 테이블을 생성할 수도 있습니다. 이 경우 @Entity가 있는 클래스의 멤버 변수에 따라서 자동으로 칼럼들도 생성됩니다.

### @Table
@Entity 어노테이션과 같이 사용할 수 있는 어노테이션으로 말 그대로 데이터베이스상에서 엔티티 클래스를 어떠한 테이블로 생성할 것인지에 대한 정보를 담기 위한 어노테이션입니다.

예를 들어 @Table(name = "t_memo")와 같이 지정하는 경우에는 생성되는 테이블 이름이 't_memo' 테이블로 생성 됩니다. 단순히 테이블의 이름뿐만 아니라 인덱스 등을 생성하는 설정도 가능합니다.

### @Id와 @GeneratedValue
@Entity가 붙은 클래스는 Primary Key에 해당하는 특정 필드를 @Id로 지정해야만 함.
@Id가 사용자가 입력하는 값을 사용하는 경우가 아니면 자동으로 생성되는 번호를 사용하기 위해서 @GeneratedValue라는 어노테이션을 활용해야 하는데 
@GeneratedValue(strategy = GenerationType.IDENTITY) 부분은 PK가 자동으로 생성하고자 할 때 사용합니다 (키 생성 전략이라고 한다고도 함. 전략 패턴이라 그런가...?)

DBMS의 종류에 따라서 auto increment를 해줌 키 생성 전략은 크게 다음과 같음
- AUTO (default) - JPA 구현체 (스프링 부트에서는 Hibernate)가 생성 방식을 결정
- IDENTITY - 사용하는 데이터베이스가 키 생성을 결정 MySQL 이나 MariDB의 경우 auto increment 방식을 이용
- SEQUENCE - 데이터베이스의 sequence를 이용해서 키를 생성. @SequenceGenerator와 같이 사용
- TABLE - 키 생성 전용 테이블을 생성해서 키 생성. @TableGenerator와 함께 사용

### @Column
만일 추가적인 필드(칼럼)가 필요한 경우에도 마찬가지로 어노테이션을 활용하여 생성하는데
@Column을 이용해서 다양한 속성을 지정할 수 있다. 주로 nullable, name, length 등을 이용해서 데이터베이스의 칼럼에 필요한 정보를 제공합니다.
속성 중에 columnDefinition을 이용하면 기본값을 지정할 수도 있습니다.

기존 코드에서 칼럼을 추가
```
@Entity
@Table(name = "tbl_memo")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;
}

```

#### @Getter, @Builder, @AllArgsConstructor, @NoArgsConstructor
- @Getter - 메서드를 생성함
- @Builder - @Builder를 이용해서 객체를 생성할 수 있게 처리 @Builder를 이용하기 위해서는 
@AllArgsConstructor, @NoArgsConstructor를 항상 같이 처리해야 컴파일 에러가 발생하지 않는다고함.

> @Column과 반대로 DB table에 칼럼으로 생성되지 않는 필드의 경우 @Transient를 적용합니다.
또한 @Column으로 기본 값을 지정하기 위해서 columnDefinition을 이용하기도 합니다.
```
@Column(columnDefintion = "varchar(255) default 'Yes'")
```

---

JPA와 Hibernate의 application.properties 설정
```
# 프로젝트 실행 시에 자동으로 DDL을 생성 할것인지를 결정하는 설정
spring.jpa.hibernate.ddl-auto=update
# 실제 JPA의 구현체인 Hibernate가 동작하면서 발생하는 SQL을 포맷팅해서 출력, 실행되는 SQL의 가독성을 높여 줌.
spring.jpa.properties.hibernate.format_sql=true
# JPA 처리 시에 발생하는 SQL을 보여줄 것인지를 결정. 
spring.jpa.show-sql=true
```

---

## JpaRepository Interface

Spring Data JPA에는 여러 종류의 인터페이스의 기능으 통해서 JPA관련 작업을 별도의 코드 없이 처리할 수 있게 지원합니다.

예를들어 CRUD 작업이나 페이징, 정렬 등의 처리도 인터페이스의 메서드를 호출하는 형태로 처리하는데 기능에 따라서 상속 구조로 추가적인 기능을 제공합니다.

> JpaRepository -> PagingAndSortRepository -> CrudRepository -> Repository
이런 형식의 상속 구조를 가짐.
일반적인 기능만을 사용할 때는 CrudRepository를 사용하는 것이 좋지만 특별한 경우가 아니라면 JpaRepository를 이용하는 것이 무난한 선택이라고 함.

```
public interface MemoRepository extends JpaRepository<Memo, Long> {
}
```

위와 같이 선언만 해주어도 모든 처리가 끝남...

JpaRepository를 사용할 때는 엔티티의 타입 정보(Memo 클래스 타입)와 @Id의 타입을 지정하게 됩니다. 이처럼 
Spring Data JPA는 인터페이스 선언만으로도 자동으로 스프링의 빈(bean)으로 등록됩니다. (스프링이 내부적으로 인터페이스 타입에 맞는 객체를 생성해서 빈으로 등록합니다.)

```
    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass() {
        System.out.println(memoRepository.getClass().getName());
    }
    
   실행 결과
   com.sun.proxy.$Proxy112
```
스프링 내부적으로 해당 클래스를 자동으로 생성하는데(AOP기능) 이때 클래스 이름을 확인하면 ...ProxyXX와 같이 확인 됨 동적 프록시 방식으로 만들어지기 때문.
