enum EngineType {
    ELECTRIC,
    GAS,
    HYBRID
}

interface Engine {
    void start();
    void stop();
    void onSpeedChange(int speed);
}

class GasEngine implements Engine {

    @Override
    public void start() {
        System.out.println("Gas Engine started");
    }

    @Override
    public void stop() {
        System.out.println("Gas Engine stopped");
    }

    @Override
    public void onSpeedChange(int speed) {
        System.out.println("Gas Engine running at speed: " + speed);
    }
}

class ElectricEngine implements Engine {

    @Override
    public void start() {
        System.out.println("Electric Engine started");
    }

    @Override
    public void stop() {
        System.out.println("Electric Engine stopped");
    }

    @Override
    public void onSpeedChange(int speed) {
        System.out.println("Electric Engine running at speed: " + speed);
    }
}

class HybridEngine implements Engine {

    private final Engine gasEngine = new GasEngine();
    private final Engine electricEngine = new ElectricEngine();

    @Override
    public void start() {
        System.out.println("Hybrid Engine started");
        electricEngine.start();
    }

    @Override
    public void stop() {
        System.out.println("Hybrid Engine stopped");
        electricEngine.stop();
        gasEngine.stop();
    }

    @Override
    public void onSpeedChange(int speed) {
        if (speed < 50) {
            electricEngine.onSpeedChange(speed);
        } else {
            gasEngine.onSpeedChange(speed);
        }
    }
}

class Car {

    private int speed;
    private Engine engine;

    public Car(Engine engine) {
        if (engine == null) {
            throw new IllegalArgumentException("Engine cannot be null");
        }
        this.engine = engine;
        this.speed = 0;
    }

    public void start() {
        speed = 0;
        engine.start();
    }

    public void stop() {
        speed = 0;
        engine.stop();
    }

    public void accelerate() {
        if (speed < 200) {
            speed += 20;
            speed = Math.min(speed, 200);
            engine.onSpeedChange(speed);
        }
    }

    public void brake() {
        if (speed > 0) {
            speed -= 20;
            speed = Math.max(speed, 0);
            engine.onSpeedChange(speed);
        }
    }

    public void setEngine(Engine engine) {
        if (engine == null) {
            throw new IllegalArgumentException("Engine cannot be null");
        }
        this.engine = engine;
    }

    public int getSpeed() {
        return speed;
    }
}

class CarFactory {

    public static Car createCar(EngineType type) {
        return new Car(createEngine(type));
    }

    public static void changeEngine(Car car, EngineType type) {
        if (car == null) {
            throw new IllegalArgumentException("Car cannot be null");
        }
        car.setEngine(createEngine(type));
    }

    private static Engine createEngine(EngineType type) {
        switch (type) {
            case GAS:
                return new GasEngine();
            case ELECTRIC:
                return new ElectricEngine();
            case HYBRID:
                return new HybridEngine();
            default:
                throw new IllegalArgumentException("Invalid engine type");
        }
    }
}

public class Main {
    public static void main(String[] args) {

        Car car = CarFactory.createCar(EngineType.ELECTRIC);

        car.start();
        car.accelerate();
        car.accelerate();

        System.out.println("---- Change to Hybrid ----");

        CarFactory.changeEngine(car, EngineType.HYBRID);

        car.accelerate(); 
        car.brake();      

        car.stop();
    }
}