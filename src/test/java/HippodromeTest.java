import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HippodromeTest {

    @Test
    void constructorHippodromeIfParameterAreNull(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null));

        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void constructorHippodromeIfListAreEmpty(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<>()));

        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorsesTest(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("horse" + i, i, i * 0.5));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());

    }

    @Test
    void hippodromeMoveTest(){
        List<Horse> mockHorses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mockHorses.add(Mockito.mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(mockHorses);

        hippodrome.move();

        for (Horse horseMock : mockHorses) {
            Mockito.verify(horseMock, Mockito.times(1)).move();
        }
    }

    @Test
    void getWinnerShouldReturnHorseWithMaxDistance() {
        Horse horse1 = new Horse("Horse1", 10.0, 100.0);
        Horse horse2 = new Horse("Horse2", 10.0, 150.0); // the largest distance
        Horse horse3 = new Horse("Horse3", 10.0, 120.0);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3));

        assertSame(horse2, hippodrome.getWinner(), "The method should return the horse with the maximum distance");
    }

}
