package courses.basics_strong.funcprogramming.section7;

import java.util.Optional;

public class FP01DangerousNull {
    public static void main(String[] args) {
        // This example will rise an NullPointerException because the SoundCard is null;
        try {
            String version = new Computer()
                    .setSoundCard(null) // we intentionally set SoundCard with null
                    .getSoundCard()
                    .setUsb(new USB())
                    .getUsb().getVersion(); // NullPointerException because the SoundCard is null

            System.out.println("Version = "+version); // we'll never see this log message
        } catch (NullPointerException e) {
            System.out.println("Yes! We caught the null pointer");
        }

        String version = new ComputerOptional()
                .setSoundCardOptional(null) // we intentionally set SoundCard with null
                .getSoundCardOptional()
                .orElseGet( () -> new SoundCardOptional()) // since the sound-card is null (empty) this statement will use this supplier
                .setUsbOptional(new USBOptional())
                .getUsbOptional()
                .orElseGet( () -> new USBOptional())
                .getVersion();

        System.out.println("Version = "+version);
    }
}

class Computer{
    SoundCard soundCard; // NOTE is null

    public Computer setSoundCard(SoundCard soundCard) {
        this.soundCard = soundCard;
        return this;
    }

    public SoundCard getSoundCard() {
        return soundCard;
    }
}

class SoundCard {
    USB usb = new USB();

    public SoundCard setUsb(USB usb) {
        this.usb = usb;
        return this;
    }

    public USB getUsb() {
        return usb;
    }

}

class USB {
    public String getVersion() {
        return "1.0";
    }
}

class ComputerOptional {
    Optional<SoundCardOptional> soundCardOptional;

    // we ensure Optional result using its "ofNullable" that returns "empty" if the input is null
    public ComputerOptional setSoundCardOptional(SoundCardOptional soundCardOptional) {
        this.soundCardOptional = Optional.ofNullable(soundCardOptional);
        return this;
    }

    public Optional<SoundCardOptional> getSoundCardOptional() {
        return soundCardOptional;
    }
}

class SoundCardOptional {
    Optional<USBOptional> usbOptional;

    public SoundCardOptional setUsbOptional(USBOptional usbOptional) {
        this.usbOptional = Optional.ofNullable(usbOptional);

        return this;
    }

    public Optional<USBOptional> getUsbOptional() {
        return usbOptional;
    }
}

class USBOptional {
    public String getVersion() {
        return "2.0";
    }
}