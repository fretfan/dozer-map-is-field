import java.time.LocalDate

data class DestinationCustomStructure(
    var nested: NestedDestination? = null,
    var one: String? = null,
    var three: String? = null,
    var theDate: LocalDate? = null,
    var five: String? = null,
    var listOfText: List<String> = mutableListOf()
)
