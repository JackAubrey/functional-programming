### Optional
A null reference can be a ranger if not handled with care.  
Of a value or object is null any operation on that null reference causes null pointer exception and i would say it is the most occurred.  
Java sc8 introduced a new class called "Optional" (java.util.Optional<T>) that is inspired from ideas of Haskell and Scala.  
Take a look to "FP01DangerousNull" code in "section7" package to see in detail.

#### Optional is
- a box which wraps a value in it
- it consumes 16 bytes
- it's a separate object so don't think no new object will be created when we wrap a value in an optional
- are immutable we can't change the value once they are created

**One thing is clear that creating optional everywhere can cost performance** because each time it creates a new object.  
So don't replace null with optional where you really don't need it

![image info](./imgs/Screenshot_20240722_163339.png "Optional")

#### Set value on Optional
- Optional.of(....) when we have a value (NOT NULL) to set
- Optional.empty() when we have no data
- Optional.ofNullable(...) when we have a value to set, and we are not sure if it is null.

**Beware:**  Optional.of(null) will rise an exception.  
If always better use Optional.ofNullable(...)
- Optional.ofNullable(null) = Optional.empty()
- Optional.ofNullable("my value) = Optional.of("my value")

#### Retrieve value from Optional
- is would be better never use ".get()" because is a risky method.  
  If we use it on "empty" optional object we'll obtain an exception.
- **orElse(other)** return the optional value if set, otherwise return the "other" value we provided
- **orElseGet(supplier)** return the optional value if set, otherwise return the "other" value supplied by the supplier
- **orElseThrow(Exception Supplier)** return the optional value if set, otherwise rise the exception supplied by the exception supplier

#### Optional operations - the most interesting methods
- **map (mapperFunction):** if the optional value is present map it to another value wrapped in an Optional and returned back
- **filter (predicate):** if the optional value is present it use the predicate to test the condition.  
  If the condition is satisfied it returns the same option again otherwise returns an empty optional.
- **flatMap (mapperFunction):** is similar to map but the mapping function is one whose result is already an optional.  
  FlatMap does not wrap the returned value of this mapper in an optional **because the value returned by this mapper is already an optional.**
- **ifPresent (consumer):** if the optional value is present ifPresent executes a consumer on that value
- **ifPresentOrElse (consumer, runnable):** if the optional value is present ifPresentOrElse executes a consumer on that value otherwise will execute the provided runnable
- **stream:** if the value is present it returns a sequential stream containing only that value otherwise it returns an empty stream.
- **or (supplier of optional):** it takes a supplier, and it returns the same optional if the optional has a value.  
  If optional is empty then it's applying new optional returned by the supplier function.  
  **Be careful** to do not return a null value from the supplier.
  If a value is present, returns an Optional describing the value, otherwise returns an Optional produced by the supplying function.
- **orElse (other):** If a value is present, returns the value, otherwise returns other
- **orElseGet (supplier of other):** If a value is present, returns the value, otherwise returns the result produced by the supplying function.
- **equals (object):** it's for checking some other object is equal to the optional or not. So what would be the criteria for that.  
  The other object is considered equal if:
    - it is also an optional
    - and either if both the optionals are empty
    - or both contains equal value
- **hashcode** method returns hash code value