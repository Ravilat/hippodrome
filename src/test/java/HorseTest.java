
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class HorseTest {

    @Test
    public void constructorFirstParamNullTest() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1.0));
    }

    @Test
    public void constructorFirstParamNullExceptionMessageTest() {
        String textException = "";
        try {
            new Horse(null, 1.0);
        } catch (IllegalArgumentException e) {
            textException = e.getMessage();
        }
        assertEquals("Name cannot be null.", textException);
    }

    @ParameterizedTest
    @MethodSource("whiteSpaceParameters")
    public void constructorFirstParamIsBlankTest(String s) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(s, 1.0));
    }

    @ParameterizedTest
    @MethodSource("whiteSpaceParameters")
    void constructorFirstParamIsBlankExceptionMessageTest(String s) {
        String textException = "";
        try {
            new Horse(s, 1.0);
        } catch (IllegalArgumentException e) {
            textException = e.getMessage();
        }
        assertEquals("Name cannot be blank.", textException);
    }

    static Stream<String> whiteSpaceParameters() {
        return Stream.of("", " ", "\t", "\n", "\u000B", "\f", "\r", "\u001C", "\u001D", "\u001F", "\u001E");
    }

    @Test
    public void constructorSecondParamIsNegativeTest() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Zorka", -1.0));
    }

    @Test
    public void constructorSecondParamIsNegativeExceptionMessageTest() {
        String textException = "";
        try {
            new Horse("Zorka", -1.0);
        } catch (IllegalArgumentException e) {
            textException = e.getMessage();
        }
        assertEquals("Speed cannot be negative.", textException);
    }

    @Test
    public void constructorThirdParamIsNegativeTest() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Zorka", 1.0, -1.0));
    }

    @Test
    public void constructorThirdParamIsNegativeExceptionMessageTest() {
        String textException = "";
        try {
            new Horse("Zorka", 1.0, -1.0);
        } catch (IllegalArgumentException e) {
            textException = e.getMessage();
        }
        assertEquals("Distance cannot be negative.", textException);
    }

    @Test
    public void getNameTest() {
        String expected = "MyName";
        Horse horse = new Horse(expected, 1.0);
        assertEquals(expected, horse.getName());
    }

    @Test
    public void getSpeedTest() {
        Double expected = 1.5;
        Horse horse = new Horse("MyName", expected);
        assertEquals(expected, horse.getSpeed());
    }

    @Test
    public void getDistanceTest() {
        Double expected = 1.5;
        Horse horse = new Horse("MyName", 1.0, expected);
        assertEquals(expected, horse.getDistance());
    }

    @Test
    public void getDistanceZeroTest() {
        Horse horse = new Horse("MyName", 1.0);
        assertEquals(0.0, horse.getDistance());
    }

    @Test
    public void moveCheckForStaticMethodExecuteTest() {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("MyName", 1.0);
            horse.move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2d, 0.9d));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.3, 0.2, 0.8, 0.5})
    public void moveCheckDistanceTest(Double random) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            double distance = 5.0;
            double speed = 2.0;
            Horse horse = new Horse("MyName", speed, distance);
            horse.move();
            distance = distance + speed * random;
            assertEquals(distance, horse.getDistance());
        }
    }


}

