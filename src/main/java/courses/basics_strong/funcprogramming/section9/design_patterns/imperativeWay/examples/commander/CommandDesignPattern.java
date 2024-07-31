package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.commander;

public class CommandDesignPattern {
    public static void main(String[] args) {
        AirConditioner acRoomOne = new AirConditioner("AC Room One");
        AirConditioner acRoomTwo = new AirConditioner("AC Room Two");

        AirConditionerAutomateRemote remote = new AirConditionerAutomateRemote();
        remote.setCommand(CommandFactory.turnOn(acRoomOne));
        remote.pressButton();
        remote.setCommand(CommandFactory.turnOn(acRoomTwo));
        remote.pressButton();
        remote.setCommand(CommandFactory.increaseTemperature(acRoomOne));
        remote.pressButton();
    }
}
