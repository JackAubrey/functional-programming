package courses.basics_strong.reactive.section16.model;

import courses.basics_strong.reactive.section16.interfaces.Observable;
import courses.basics_strong.reactive.section16.interfaces.SubjectLibrary;

/**
 * This is the concrete implementation of Observable interface
 */
public class EndUser implements Observable {
    private String name;

    public EndUser(String name, SubjectLibrary subject) {
        this.name = name;
        subject.subscribeObservable(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void update(String avail) {
        System.out.println("Hello "+name+"! We are glad to notify you that your book is now"+avail);
    }
}
