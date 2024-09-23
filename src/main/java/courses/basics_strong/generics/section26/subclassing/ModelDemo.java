package courses.basics_strong.generics.section26.subclassing;

import courses.basics_strong.reactive.BasicExampleClass;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class ModelDemo extends BasicExampleClass {
    public static void main(String[] args) {
        Model<String, String> stringModel = new Model<>( generateId(), "This is my string data");
        Model<String, Integer> integerModel = new Model<>( generateId(), 10);
        // in this case the basic-model need to be parametrized with model id
        IBasicModel<String> stringBasicModel = new Model<>( generateId(), LocalTime.now());
        IModel<String, LocalDateTime> dataTimeModelInterface = new Model<>( generateId(), LocalDateTime.now());

        log("Model - String data is: "+stringModel);
        log("Model - Integer data is: "+integerModel);
        log("Interface Basic Model - DateTime data is: "+stringBasicModel);
        log("Interface Model - Time data is: "+dataTimeModelInterface);
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }
}
