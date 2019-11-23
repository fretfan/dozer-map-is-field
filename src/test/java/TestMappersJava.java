import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public class TestMappersJava {

//    var mapper: OrikaMapper = OrikaMapper()
//    var testDate: LocalDate = LocalDate.of(2019, 2, 1)
//    var testDateTime: LocalDateTime = LocalDateTime.of(testDate, LocalTime.of(12, 11))
//    val text1: String = "one"
//    val list1: List<String> = listOf("one", "two")

    OrikaMapper mapper = new OrikaMapper();
    LocalDate testDate = LocalDate.of(2019, 2, 1);
    LocalDateTime testDateTime = LocalDateTime.of(testDate, LocalTime.of(12, 11));

    @Test
    void name() {
        SourceJava source = new SourceJava();
        source.bool1 = true;
        source.text1 = "alo";
        source.number1 = BigDecimal.ONE;
        source.date1 = testDate;
        source.dateTime1 = testDateTime;
        source.list1 = Arrays.asList("one", "two");

        DestinationJava result = mapper.map(source, DestinationJava.class);
        Assertions.assertEquals(result.bool1, source.bool1);
        Assertions.assertEquals(result.text1, source.text1);
        Assertions.assertEquals(result.number1, source.number1);
        Assertions.assertEquals(result.date1, source.date1);
        Assertions.assertEquals(result.dateTime1, source.dateTime1);
        Assertions.assertEquals(result.list1, source.list1);
        Assertions.assertEquals(result.text2, "alo");

    }
}
