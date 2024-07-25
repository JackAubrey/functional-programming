package courses.basics_strong.funcprogramming.section8.techniques;

import net.datafaker.Faker;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Technique01_Chaining_MyExperiment01 {
    public static void main(String[] args) {
        // we want to configure a user chaining some UserConfigurator.
        //
        // 1 - we declare a configurator in order to simply configure a user starting from its first and last name
        UserConfigurator<Faker, MyUser> userConfig = (f) -> new MyUser(f.name().firstName(), f.name().lastName());
        // 2 - now we declare a configurator in order to configure a User with authentication info
        UserConfigurator<MyUser, MyAuthUser> userAuthConfig = (u) -> new MyAuthUser(u, "AuthId:"+UUID.randomUUID());
        // 3 - and finally we declare a configurator in order to configure an Authenticated User with Login Info
        UserConfigurator<MyAuthUser, MyLoggedUser> userLoggedConfig = (uAuth) -> new MyLoggedUser(uAuth, "LogId:"+UUID.randomUUID(), LocalDate.now());

        // let's go to chain this 3
        MyLoggedUser loggedUser = userConfig
                .andThen(userAuthConfig)
                .andThen(userLoggedConfig)
                .configure(new Faker());
        System.out.println(loggedUser);
        // This is the result:
        //      MyLoggedUser [
        //          myAuthUser = MyAuthUser [
        //              myUser = MyUser [
        //                  firstName=Jake,
        //                  secondName=McCullough
        //              ],
        //              authCode=AuthId:c2a90bfa-9b84-422f-98f0-7cf6affe8a95
        //          ],
        //          loginId=LogId:972f1853-ae25-4774-b065-e8126dbe5624,
        //          loginDate=2024-07-25
        //      ]
    }
}

record MyUser(String firstName, String secondName) {}
record MyAuthUser(MyUser myUser, String authCode) {}
record MyLoggedUser(MyAuthUser myAuthUser, String loginId, LocalDate loginDate) {}

@FunctionalInterface
interface UserConfigurator<T, R> {
    R configure(T t);

    // this method wants an "UserConfigurator<R, V>" where:
    //  - R is the input and use the result of first function
    //  - V is the new result
    // it is a transformation!
    default <V> UserConfigurator<T, V> andThen(UserConfigurator<R, V> configurator) {
        Objects.requireNonNull(configurator);

        return (T t) -> configurator.configure(this.configure(t));
    }
}
