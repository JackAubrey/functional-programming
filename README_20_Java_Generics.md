## Java Generics
**What are Generics?** Generics are mechanism for type checking at compile time.  
**Why we should use?** In application there may occur any compile time error while writing the code.

Using Generics we can reuse the code.  
**NOTE: Generics are not available for static attributes. This is because only single instance can be inferred with the appropriated type.**   
We can make also an interface Generic and extends or implement it.  

**See examples on "courses.basics_strong.generics.section26" package and sub packages.**

### Subclassing
At the same time, we can extend a generic class and implement a generic interface which is extending another generic.  
Some important points about extending generic:
- A generic interface can be implemented by a non-general class only if you define the particular class or interfaces as the type parameter.
  This is still true when a non-generic class extends a generic class or interface.  

      // A non-generic class or interface can extends a generic one
      // but as you can see it must define the type
      public interface NonGenericInterface extends GenericInterface<Integer> {
      }

      public class NonGenericClass implements GenericInterface<String> {
      }

      public class NonGenericClass extends GenericClass<NonGenericInterface> {
      }

- A Generic class/interface can extend/implements a non-generic class/interface.

      public interface GenericInterface<T> extends NonGenericInterface {
      }

      public class GenericClass<T> implements NonGenericInterface {
      }

      public class GenericClass2<T> extends NonGenericClass {
      }

### Sub-Typing
Like we are able to do with normal types, for example we can assign an Integer to a Number because Integer is a child of Number, we can also do the same with Generics.  
But beware we can not do this:
      
      // Not permitted
      List<Number> list = new ArrayList<Integer>();

This not permitted because ArrayList<Integer> is not a subtype of List<Number>.  
SubTyping is permitted only when paren-child relationship is preserved.

      // This is generic class
      interface Pair<K, V> {
         K getKey();
         V getValue();
      }

      // This is a generic that extends our previous Generic
      class SingleItem<E> implements Pair<E, E> {
      }

      // we can do this
      Pair<Integer, Integer> single = new Single<>(5);

### Generic Methods
We can make generic also the methods in a non-generic class using the diamond brackets before the returning method signature.  
We can also use generics constructors giving the diamond brackets before the constructor name.

      // This class is NOT Generic
      class NonGenericClass {

         // generic constructor
         public <T> NonGenericClass(List<T> list) {
         }

         // generic method examples

         public <T> String concat(T data) {
            return "concat with : "+data;
         }
   
         public static <T, R> String concat(T t, R r) {
            return "concat "+t+" with : "+r;
         }
   
         public <T,R> R map(T t) {
            return .....code to make this transformation!!!
         }
      }

### Bounded Type Parameters
A type parameter can be any reference type but there may be many cases when we want to bound the types that we that can be used as type arguments in a generic class.  
Suppose if a generic class is having operations of numeric type only, then there is no need to have any other type except numeric type as argument.  
In order to have a Generic Bounded Type we need to "extends" a type. In diamond brackets we are going to specify the letter used as generic and which kind of type it extends.

    // Bounded Type
    class MyCalculator<T extends Number> {
      //... methods
    }

**NOTE-1: we can not use a bounded type final class like String, Integer and so on!!  
This because when a type variable or a wildcard declares an upper bound that is final, the parametrization is not generic at all because it accepts one and only one type at runtime: the one that is final.**  

**NOTE-2: bounding a generic we are doing something like a filter.  
Lot of operations that we will are able to do depends on which type od date we are extends.**

    // Bounded Type
    class MyCalculator<T extends Number> {
      
      // Here we are limited by the Number.
      // We can use only its methods.
      // For this reason WE CAN NOT DO THIS
      T sum(T t1, T t2) {
          return t1+t2; //<<< ERROR
      }
      
      // But we can do this
      void consume(T t) {
        System.out.println(t);
      }
      
      // and this
      long toLong(T t) {
          return t.longValue();
      }
    }

**NOTE-3: we can bound a generic type of Class, of method or interface.  
Also we can extend as bounded type multiple interface**

    // Bounded type on method
    <K, V> Pair<K, V> pair(K key, V value) {
      return new Pair<>(key, value);
    }

    // Bounding multiple interface
    void <T extends IBound, IBox> doSome(T t) {
    }

Note about multiple bounded interfaces: in that case T must be an object that will have to implements both interfaces.

    class CBound implements IBound, IBox {
    }

    //
    doSome(new CBound());

### Upper and Lower Bounding note
**Taken from SonarLint note:**
Expressions at an input position, such as arguments passed to a method, can have a more specific type than the type expected by the method, which is called covariance. Expressions at an output position, such as a variable that receives the return result from a method, can have a more general type than the method’s return type, which is called contravariance. This can be traced back to the Liskov substitution principle.  
In Java, type parameters of a generic type are invariant by default due to their potential occurrence in both input and output positions at the same time. A classic example of this is the methods T get() (output position) and add(T element) (input position) in interface java.util.List. We could construct cases with invalid typing in List if T were not invariant.  
Wildcards can be employed to achieve covariance or contravariance in situations where the type parameter appears in one position only:
- <? extends Foo> for covariance (input positions)
- <? super Foo> for contravariance (output positions)
However, covariance is ineffective for the return type of method since it is not an input position. Making it contravariant also has no effect since it is the receiver of the return value which must be contravariant (use-site variance in Java). Consequently, a return type containing wildcards is generally a mistake.  

- Upper bound Wildcard − ? extends Type.
- Lower bound Wildcard − ? super Type.
- Unbounded Wildcard − ?

Classify the type of parameters passed to a method as in and out parameter.  

- **in variable** − An in variable provides data to the code.  
  For example, copy(src, destination).
  Here src acts as in variable being data to be copied.
- **out variable** − An out variable holds data updated by the code.  
  For example, copy(src, destination).  
  Here destination acts as in variable having copied data.

**Guidelines for Wildcards.**  
- **Upper bound wildcard** − If a variable is of in category, use extends keyword with wildcard.
- **Lower bound wildcard** − If a variable is of out category, use super keyword with wildcard.
- **Unbounded wildcard** − If a variable can be accessed using Object class method then use an unbound wildcard.
- **No wildcard** − If code is accessing variable in both in and out category then do not use wildcards.

### Wild Cards & Upper Bound Wild Card
The question mark symbol "?" is called a wild card in generics.  
When we us it without any kind of "extension" it is called "unbounded" wildcard.  
It represents unknown type when we do not know, and we can use it: as the type of parameter, a field or local variable or sometimes as a return type.  

There are many difference between Generic Type and Wild Card:

- Type parameter is just for generic classes, generic methods, and generic constructors only.  
  We can only parameterize a generic parameter to tell the compiler that is the type I am going to provide or the type to use to replace the generic.
- Use question mark for limiting what types you can legally invoke a method with at compile time.
- You cannot use question mark as direct replacement of object that you are passing straight to method or as return type.

    
      // you can not do this.
      // produce an error
      ? getSome() {}

      // neither this
      // produce an error
      void doSome(? d) {}

      // but you can do this
      void doSome(List<?> l) {}

      // NOTE: You would be tempted to do this, but it is not recommended and would produce:
      //  - a worning
      //  - a SonarLint lint
      List<?> getSome() {}

We can specify which kind of types can be used with wildcard using "extends".  
**Using the "extends" means the wild-card can be any type of extended value or subtype.  
THIS IS CALLED Upper Bound Wild Card**
    
      // limit the wildcard
      // in this case only the children of Number can be passed to "print" method
      <E extends Number> void print(E value)

### Lower Bounded WildCards
Unlike the "Upper Bounded" where we use a question mark that extends other, in "Lower Bounded" we use "super".  
This means, using the "Lower Bounded" that only parents of specified type can be used.  


    // Upper Bounded
    // In this case we can only pass Number or its children like Integer and so on
    <T extends Number> void doSome(T data) {}

    // LOWER BOUNDED
    // In this case we can only pass Integer or its parent (a Number in this particular case)
    <T super Integer> void doSome(T data) {}

