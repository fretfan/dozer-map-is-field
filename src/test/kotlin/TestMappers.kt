import TestConstants.testBool1
import TestConstants.testDate
import TestConstants.testDateTime
import TestConstants.testList1
import TestConstants.testNumber1
import TestConstants.testText1
import TestConstants.testText2
import TestConstants.testText3
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

class TestMappers {

    var mapper: OrikaMapper = OrikaMapper()

    @Test
    fun itMapsAllFields() {
        val source = Source(testBool1, testText1, testNumber1, testDate, testDateTime, testList1)
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
        val source = Source()
        val result = mapper.map(source, Destination2::class.java)

        assertEquals(testBool1, result.bool1)
        assertEquals(testText1, result.text1)
        assertEquals(testNumber1, result.number1)
        assertEquals(testDate, result.date1)
        assertEquals(testDateTime, result.dateTime1)
        assertEquals(testList1, result.list1)
    }

    @Test
    fun itMapsIntoDestinationWithRenamedFields() {
        val source = Source3(testText1, testText2, testText3)
        val result = mapper.map(source, Destination3::class.java)

        assertEquals(testText1, result.nested?.text11)
        assertEquals(testText2, result.nested?.text22)
        assertEquals(testText3, result.threeRenamed)
    }

    @Test
    fun itMapsBooleanFieldsStartingWithIs() {
        val source = Source4(true)
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
    var list1: List<String>? = null
)

data class Destination2(
    var bool1: Boolean? = testBool1,
    var text1: String? = testText1,
    var number1: BigDecimal? = testNumber1,
    var date1: LocalDate? = testDate,
    var dateTime1: LocalDateTime? = testDateTime,
    var list1: List<String>? = testList1
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



