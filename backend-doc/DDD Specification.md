# **DDD Specification**

## **领域对象Lombok规范**

* 项目Lombok配置`lombok.config`

  ```properties
  lombok.anyConstructor.addConstructorProperties=true
  lombok.addLombokGeneratedAnnotation = true
  lombok.equalsAndHashCode.callSuper = call
  lombok.copyableAnnotations += org.springframework.beans.factory.annotation.Qualifier
  ```

* 对于AggregateRoot

  ```java
  @Getter
  @Document(PROBLEM_COLLECTION)
  @TypeAlias(PROBLEM_COLLECTION)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public class Problem extends AggregateRoot {}
  ```

* 对于不可变对象

  ```java
  @Value
  @Builder
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public class AcceptedProblem implements Identified {}
  ```
  
* 对于可变对象

  ```java
  @Getter
  @Builder
  @EqualsAndHashCode
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public class ProblemSetting {}
  ```

## **聚合根校验与维护正确性规范**

* 聚合根具有私有correctAndValidate，调用设置的correctAndValidate并处理其他逻辑，校验并满足对象正确性，在初始化和变更设置时调用
* 聚合根setting具有公有correctAndValidate，校验并满足对象正确性
* VO（不可变对象）具有correct和validate，分别满足正确情况和校验
* 可变对象具有correct和validate，分别满足正确情况和校验

## **分包规范**

```shell
org.ricky
       ├─common 支撑域
       │  ├─xxx
       │  └─xxx
       └─core 核心域
           ├─xxx
           ├─xxx
           │  ├─alter 写服务层
           │  │  └─dto
           │  │      ├─command 命令
           │  │      └─response 响应
           │  ├─domain 领域层
           │  │  ├─xxx
           │  │  ├─xxx
           │  │  │  └─xxx
           │  │  └─xxx
           │  │      └─xxx
           │  ├─eventhandler 事件处理器层
           │  ├─fetch 读服务层
           │  │  └─dto
           │  │      ├─query
           │  │      └─response
           │  └─infrastructure 基础设施层
           └─xxx
               └─domain
```

## **持久层规范**

### 关于BaseRepository及其子类的约定

1. 后缀为byXXX的方法，不会做checkUserShip检查，在没找到资源时将抛出异常
2. 后缀为byXxxOptional的方法，不会做checkUserShip检查，在没找到资源时返回empty()
3. 后缀为byXxxAndCheckUserShip的方法，会做checkUserShip检查，在没找到资源时将抛出异常