package courses.basics_strong.funcprogramming.section8.techniques;

import net.datafaker.Faker;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Technique01_Chaining_MyExperiment02 {
    public static void main(String[] args) {
        Faker faker = new Faker();

        // we want to configure a user chaining some UserConfigurator.
        //
        // 1 - we declare a configurator in order to simply configure a user starting from its first and last name
        UserConfiguratorAdvanced<Faker, MyUserBean> userConfig = (f) -> new MyUserBean(f.name().firstName(), f.name().lastName());
        // 2 - we declare a configurator in order to fill the rest with user attribute
        //      look on this step no transformation occur
        UserConfiguratorAdvanced<MyUserBean, MyUserBean> userSecondConfig = (u) -> MyUserBean.of(u)
                .withFullName(faker.name().fullName())
                .withTitle(faker.name().title())
                .build();
        // 3 - now we declare a configurator in order to configure a User with authentication info
        UserConfiguratorAdvanced<MyUserBean, MyAuthUserBean> userAuthConfig = (u) -> new MyAuthUserBean(u, "AuthId:"+UUID.randomUUID());
        // 4 - and finally we declare a configurator in order to configure an Authenticated User with Login Info
        UserConfiguratorAdvanced<MyAuthUserBean, MyLoggedUserBean> userLoggedConfig = (uAuth) -> new MyLoggedUserBean(uAuth, "LogId:"+UUID.randomUUID(), LocalDate.now());

        // let's go to chain this 3
        MyLoggedUserBean loggedUser = userConfig // will configure a user using the Faker instance passed on "configure" method
                .andThen(userSecondConfig) // will complete to configure the user
                .andThen(userAuthConfig) // will configure an authenticate user starting from the received user
                .andThen(userLoggedConfig) // will configure a logged user starting from the received authenticate
                .configure(faker);
        System.out.println(loggedUser);
        // This is the result:
        //      MyLoggedUserBean[
        //          myAuthUser=MyAuthUserBean[
        //              myUser=MyUserBean{
        //                  firstName='Charles',
        //                  secondName='Kub',
        //                  fullName='Florentino Quitzon',
        //                  title='Internal Brand Manager'
        //              },
        //              authCode=AuthId:8a519f7c-a1ea-4189-9d68-fc1f2a901c77
        //          ],
        //          loginId=LogId:17ee8198-50b6-4590-8a1e-75013f1f7717, loginDate=2024-07-25
        //      ]
    }

    static class MyUserBean {
        private String firstName;
        private String secondName;
        private String fullName;
        private String title;

        public MyUserBean(String firstName, String secondName) {
            this.firstName = firstName;
            this.secondName = secondName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getSecondName() {
            return secondName;
        }

        public String getFullName() {
            return fullName;
        }

        public String getTitle() {
            return title;
        }

        MyUserBean setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        MyUserBean setTitle(String title) {
            this.title = title;
            return this;
        }

        @Override
        public String toString() {
            return "MyUserBean{" +
                    "firstName='" + firstName + '\'' +
                    ", secondName='" + secondName + '\'' +
                    ", fullName='" + fullName + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }

        public static Builder of(MyUserBean user) {
            return new Builder(user.getFirstName(), user.getSecondName());
        }

        static class Builder {
            private String firstName;
            private String secondName;
            private String fullName;
            private String title;

            public Builder(String firstName, String secondName) {
                this.firstName = firstName;
                this.secondName = secondName;
            }

            public Builder withFullName(String fullName) {
                this.fullName = fullName;
                return this;
            }

            public Builder withTitle(String title) {
                this.title = title;
                return this;
            }

            public MyUserBean build() {
                return new MyUserBean(this.firstName, this.secondName)
                        .setTitle(this.title)
                        .setFullName(this.fullName);
            }
        }
    }
    record MyAuthUserBean(MyUserBean myUser, String authCode) {}
    record MyLoggedUserBean(MyAuthUserBean myAuthUser, String loginId, LocalDate loginDate) {}

    @FunctionalInterface
    interface UserConfiguratorAdvanced<T, R> {
        R configure(T t);

        // this method wants an "UserConfigurator<R, V>" where:
        //  - R is the input and use the result of first function
        //  - V is the new result
        // it is a transformation!
        default <V> UserConfiguratorAdvanced<T, V> andThen(UserConfiguratorAdvanced<R, V> configurator) {
            Objects.requireNonNull(configurator);

            return (T t) -> configurator.configure(this.configure(t));
        }


    }
}
