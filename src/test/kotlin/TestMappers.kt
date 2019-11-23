import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TestMappers {

    var mapper: OrikaMapper = OrikaMapper()
    var testDate: LocalDate = LocalDate.of(2019, 2, 1)
    var testDateTime: LocalDateTime = LocalDateTime.of(testDate, LocalTime.of(12, 11))
    val text1: String = "one"
    val text2: String = "two"
    val text3: String = "three"
    val list1: List<String> = listOf("one", "two")

    @Test
    internal fun itMapsAllFields() {
        val s = Source123()
//        var dest = Destination123()
//        val res = mapper.map(s, Destination123::class.java)

        val source = Source(true, "alo", BigDecimal(42), testDate, testDateTime, listOf("one", "two"))
        val result = mapper.map(source, Destination::class.java)

        Assertions.assertEquals(source.bool1, result.bool1)
        Assertions.assertEquals(source.text1, result.text1)
        Assertions.assertEquals(source.number1, result.number1)
        Assertions.assertEquals(source.date1, result.date1)
        Assertions.assertEquals(source.dateTime1, result.dateTime1)
        Assertions.assertEquals(source.list1, result.list1)
    }

//        @Test
    internal fun itDoesNotMapNullsIntoDestination() {
        val source = Source2()
        val result = mapper.map(source, Destination2::class.java)

        Assertions.assertEquals(text1, result.text1)
        Assertions.assertEquals(list1, result.list1)
    }

    @Test
    internal fun itMapsIntoDestinationWithRenamedFields() {
        val source = Source3(text1, text2, text3)
        val result = mapper.map(source, Destination3::class.java)

        Assertions.assertEquals(text1, result.nested?.text11)
        Assertions.assertEquals(text2, result.nested?.text22)
        Assertions.assertEquals(text3, result.threeRenamed)
    }

    // todo test mapping with custom boolean converter
    // todo test mapping with custom structure

}


data class Source(
    var bool1: Boolean? = null,
    var text1: String? = null,
    var number1: BigDecimal? = null,
    var date1: LocalDate? = null,
    var dateTime1: LocalDateTime? = null,
    var list1: List<String>? = null
)

data class Destination(
    var bool1: Boolean? = null,
    var text1: String? = null,
    var number1: BigDecimal? = null,
    var date1: LocalDate? = null,
    var dateTime1: LocalDateTime? = null,
    var list1: List<String>? = null,
    var text2: String? = "tere"
)

data class Source2(
    var text1: String? = null,
    var list1: List<String>? = null
)

data class Destination2(
    var text1: String? = "one",
    var list1: List<String>? = mutableListOf("one", "two")
)

data class Source3(
    var text1: String? = null,
    var text2: String? = null,
    var text3: String? = null
)

data class Destination3(
    var nested: NestedDestination? = null,
    var threeRenamed: String? = null
)

data class NestedDestination(
    var text11: String? = null,
    var text22: String? = null
)




