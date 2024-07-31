package courses.basics_strong.funcprogramming.section9.design_patterns;

import java.util.UUID;

public class PatternCommand {
    public static void main(String[] args) {
        // suppose we want control an air conditioners.
        // air conditioners have few actions on them like:
        //  - turning them off
        //  - turning them on
        //  - increasing the temperature
        //  - decreasing the temperature
        // there will be an invoker which will be the remote that can control the command for all the ACs

        AirConditioner acOne = new AirConditioner("Room One");
        AirConditioner acTwo = new AirConditioner("Room Two");

        AirConditionerRemote remote = new AirConditionerRemote();
        remote.setCommand( () -> acOne.turnOn() );
        remote.pressButton();
        remote.setCommand( () -> acTwo.turnOn() );
        remote.pressButton();
        remote.setCommand( () -> acOne.increaseTemperature() );
        remote.pressButton();
        remote.setCommand( () -> acOne.increaseTemperature() );
        remote.pressButton();
        remote.setCommand( () -> acOne.decreaseTemperature() );
        remote.pressButton();
        remote.setCommand( () -> acOne.turnOff() );
        remote.pressButton();
        remote.setCommand( () -> acTwo.turnOff() );
        remote.pressButton();
    }

    interface ICommandAC {
        void execute();
    }

    static class AirConditioner {
        private String name = null;

        public AirConditioner () {
            name = UUID.randomUUID().toString();
        }

        public AirConditioner (String name) {
            this.name = name;
        }

        public void turnOn() {
            System.out.println("Turning ON the Air Conditioner ["+name+"]");
        }

        public void turnOff() {
            System.out.println("Turning OFF the Air Conditioner ["+name+"]");
        }

        public void increaseTemperature() {
            System.out.println("Increasing temperature on ["+name+"]");
        }

        public void decreaseTemperature() {
            System.out.println("Decreasing temperature on ["+name+"]");
        }
    }

    static class AirConditionerRemote {
        ICommandAC command;

        void setCommand(ICommandAC command) {
            this.command = command;
        }

        void pressButton() {
            this.command.execute();
        }
    }
}