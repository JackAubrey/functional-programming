package courses.basics_strong.funcprogramming.section8.techniques;

import courses.basics_strong.funcprogramming.section8.techniques.fi.ITask;

public class Technique03_Closure {
    public static void main(String[] args) {
        // we are going to use our own ITask functional interface.
        // the ITask abstract method definition is:
        //      void doTask()
        // it doesn't accept anything and return nothing.

        // before to declare the ITask reference, we prepare a variable.
        // the scope of thi variable is the same scope of the main method
        //
        // A brief refresh about "final" and "effectively final"
        //  - "final": is a variable that use the final modifier.
        //           final int x = 1;
        //  - "effectively final":  Is considered in this manner a primitive or object that after its initialization will never change.
        //                          In the case of objects, if we do not change the reference of an object, then it is effectively final
        //                          even if a change occurs in the state of the referenced object.
        int myValue = 11111;

        // now we are going to use it in our task block code
        ITask taskOk = () -> {
            // this is only possible if the value outside the block code context is one of:
            //      - final
            //      - effectively final
            System.out.println("Your value is: "+ myValue);
            System.out.println("Task Completed");
        };

        // we don't trigger the task method yet
        // before to do it, we need to analyze some scenarios

        // if we try to modify the value inside the functional interface block code
        ITask errorFinal01 = () -> {
            // The java compiler will print: "local variables referenced from a lambda expression must be final or effectively final".
            // myValue += 20; // <- uncomment to see the error
            System.out.println(myValue);
        };

        // also you are no longer able to modify our variable after its FIRST definition
        int updable = 2345; // this is its first definition. ACTUALLY IT IS CONSIDERED AS >> EFFECTIVELY FINAL <<
        updable = 456; // we are modifying our variable. This means is NO LONGER CONSIDERED AS >> EFFECTIVELY FINAL <<
        ITask errorFinal02 = () -> {
            // The java compiler will print: "local variables referenced from a lambda expression must be final or effectively final".
            // System.out.println(value); // <- uncomment to see the error
        };

        // it has also forbidden modify the value that we consider EFFECTIVELY FINAL after the lambda declaration:
        // myValue += 20; // <- if you uncomment this, you'll se an error on "taskOk" lambda body

        // this means:
        //  - the scope of "myValue" is the whole "main" method.

        // now trigger the taskOk but outside her
        printValue(taskOk);

        // after running this code you'll see as result:
        //
        //      Your value is: 11111
        //      Task Completed
        //
        // Ok it's fine, but let's think for a moment.
        //  -   The "task.doTask()" is placed in another method, another scope.
        //  -   Both the "task" reference and "myVal" are defined in our "main" method that has another scope.
        //  -   The "printValue" is executing the lambda associated to our reference that is placed in "main".
        //      Either "myVal" is not present in the scope of "printValue" method.
        // How is it possible?
        //
        // Simple:  JRE is keeping track of "myVal".
        //          When we pass "taskOk" to the "printValue" method, the value the variable "myVal" is also passed
        //          So where the value is coming from? It's coming from the closure.
        //
        // Wherever any lambda expression uses a free variable in the same scope
        // the value of that variable is saved
        // and whenever we call the lambda it goes along with that
        //
        // That's what a closure is!!
        // Closures are important because they control what is and isn't in the scope of a particular function
        // along with which variables are shared between sibling functions in the same containing scope.
    }

    private static void printValue(ITask task) {
        task.doTask();
    }
}
