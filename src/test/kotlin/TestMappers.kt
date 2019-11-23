import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
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
    fun itMapsAllFields() {
        val source = Source(true, "alo", BigDecimal(42), testDate, testDateTime, listOf("one", "two"))
        val result = mapper.map(source, Destination::class.java)

        assertEquals(source.bool1, result.bool1)
        assertEquals(source.text1, result.text1)
        assertEquals(source.number1, result.number1)
        assertEquals(source.date1, result.date1)
        assertEquals(source.dateTime1, result.dateTime1)
        assertEquals(source.list1, result.list1)
    }

    @Test
    fun itDoesNotMapNullsIntoDestination() {
        val source = Source2()
        val result = mapper.map(source, Destination2::class.java)

        assertEquals(text1, result.text1)
        assertEquals(list1, result.list1)
    }

    @Test
    fun itMapsIntoDestinationWithRenamedFields() {
        val source = Source3(text1, text2, text3)
        val result = mapper.map(source, Destination3::class.java)

        assertEquals(text1, result.nested?.text11)
        assertEquals(text2, result.nested?.text22)
        assertEquals(text3, result.threeRenamed)
    }

    @Test
    fun itMapsBooleanFieldsStartingWithIs() {
        val source = Source4( true)
        val result = mapper.map(source, Destination4::class.java)

        assertEquals(true, result.isBool1)
        assertEquals(false, result.isBool2)
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

data class Source4(
    var isBool1: Boolean? = null,
    var isBool2: Boolean? = null
)

data class Destination4(
    var isBool1: Boolean? = null,
    var isBool2: Boolean? = false
)



