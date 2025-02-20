import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HippodromeTest {

    @Test
    public void constructorListIsNullTest() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void constructorListIsNullExceptionMessageTest() {
        String textException = "";
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            textException = e.getMessage();
        }
        assertEquals("Horses cannot be null.", textException);
    }

    @Test
    public void constructorListIsEmptyTest() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(Collections.emptyList()));
    }

    @Test
    public void constructorListIsEmptyTestExceptionMessageTest() {
        String textException = "";
        try {
            new Hippodrome(Collections.emptyList());
        } catch (IllegalArgumentException e) {
            textException = e.getMessage();
        }
        assertEquals("Horses cannot be empty.", textException);
    }

    @Test
    public void getHorsesCheckListTest() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse#" + i, i, i + 2));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    public void moveTest() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse horse : horses) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    public void getWinnerTest(){
        Horse horse1 = new Horse("Burka", 1.0, 1.5);
        Horse horse2 = new Horse("Sivka", 1.0, 2.5);
        List<Horse> horses = new ArrayList<>();
        horses.add(horse1);
        horses.add(horse2);
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horse2, hippodrome.getWinner());
    }

}
