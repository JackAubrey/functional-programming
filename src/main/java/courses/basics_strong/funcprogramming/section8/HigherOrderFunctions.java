package courses.basics_strong.funcprogramming.section8;

import net.datafaker.Faker;

import java.util.Random;

public class HigherOrderFunctions {
    public static void main(String[] args) {
        // suppose we have to create a factory that produces and configures items and then supplies the items
        // So! We have three interfaces:
        //  - first "IProducer" that produces the product
        //  - second "IConfigurator" which configures item
        //  - third "IFactory" which does the process of generating or the item and then configuring the item
        //
        // now we are going to create the Higher Order Functions here called "createFactory(IProducer<T> p, IConfigurator<T> c)"
        //
        //  1 - IProducer will produce a double number using the Math.random
        //  2 - IConfigurator configure the number taken from IProducer in order to return an Integer
        //  3 - the invocation on "createFactory" method return an IFactory<Integer> reference.
        IFactory<Integer> intFactory = createFactory( () -> new Random().nextDouble(1, 100), Double::intValue);
        //  4 - to get the product we have to invoke the create method on it.
        //      This is the implementation that we are returning for IFactory will get invoked using the two function passed on "createFactory" method.
        System.out.println("Int Factory result: "+intFactory.create());

        // 1 - IProducer will produce a Faker
        // 2 - IConfigurator will produce a String with a random name and surname
        IFactory<String> nameFactory = createFactory( () -> new Faker(), (f) -> f.name().firstName()+ " "+ f.name().lastName());
        System.out.println("Name Factory result: "+nameFactory.create());
    }

    static <T, R> IFactory<R> createFactory(IProducer<T> producer, IConfigurator<T, R> configurator)  {

        // IFactory doesn't take anything in input
        //      T create()
        // but return something
        //
        // here we are using lambda to create the implementation of IFactory
        // this code is the same in old style:
        //
        //      return new IFactory<T, R>() {
        //            @Override
        //            public R create() {
        //                T product = producer.produce();
        //                return configurator.configure(product);
        //            }
        //        };
        //
        // this is the body of function returned
        return () -> {
            T product = producer.produce();
            return configurator.configure(product);
        };
    }
}


@FunctionalInterface
interface IProducer<T> {
    T produce();
}

@FunctionalInterface
interface IConfigurator<T, R> {
    R configure(T t);
}

@FunctionalInterface
interface IFactory<R> {
    R create();
}