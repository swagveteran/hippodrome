import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HorseTest {

    private Horse horse;

    @BeforeEach
    void setUp() {
        horse = new Horse("test", 10.0, 5.0);
    }

    @Test
    void constructorHorseIfNameIsNull(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 10.0, 5.0));

        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\t", "\n", "\r\n", "   "})
    void constructorHorseIsBlank(String blankName){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(blankName, 10.0, 5.0));

        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void constructorHorseNegativeSpeed(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse("test", -10.0, 5.0));

        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void constructorHorseNegativeDistance(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse("test", 10.0, -5.0));

        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getNameTest(){
        assertEquals("test", horse.getName());
    }

    @Test
    void getSpeedTest(){
        assertEquals(10.0, horse.getSpeed());
    }

    @Test
    void getDistanceTest() {
        assertEquals(5.0, horse.getDistance());
    }

    @Test
    void constructorHorseTwoParameters(){
        Horse horseTwo = new Horse("test", 10.0);
        assertEquals(0.0, horseTwo.getDistance());
    }

    @Test
    void moveGetRandomWithCorrectParams() {
        try (MockedStatic<Horse> horseMock = Mockito.mockStatic(Horse.class)) {
            horse.move();

            horseMock.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.5, 0.9})
    void moveUpdateDistanceCorrect(double mockedRandomValue){
        Horse horse = new Horse("test", 10.0, 5.0);
        double expectedDistance = horse.getDistance() + horse.getSpeed() * mockedRandomValue;

        try (MockedStatic<Horse> horseMock = Mockito.mockStatic(Horse.class)) {
            horseMock.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(mockedRandomValue);

            horse.move();

            assertEquals(expectedDistance, horse.getDistance());
            horseMock.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }
}

