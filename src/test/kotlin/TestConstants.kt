import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object TestConstants {
    var testDate: LocalDate = LocalDate.of(2019, 2, 1)
    var testDateTime: LocalDateTime = LocalDateTime.of(testDate, LocalTime.of(12, 11))
    val testBool1: Boolean = true
    val testNumber1: BigDecimal = BigDecimal(42)
    val testText1: String = "one"
    val testText2: String = "two"
    val testText3: String = "three"
    val testList1: List<String> = listOf("one", "two")
}