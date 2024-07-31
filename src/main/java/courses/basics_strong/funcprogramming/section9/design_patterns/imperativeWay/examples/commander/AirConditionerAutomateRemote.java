package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.commander;

// The Command Invoker
public class AirConditionerAutomateRemote {
    private ICommandAc command;

    public void setCommand(ICommandAc command) {
        this.command = command;
    }

    public void pressButton() {
        command.execute();
    }
}
