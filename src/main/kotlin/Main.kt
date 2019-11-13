import com.github.dozermapper.core.DozerBeanMapperBuilder
import java.time.Instant
import java.time.LocalDate
import java.util.*


fun main(args: Array<String>) {
    println("starting")

//    mapWithOrikaSimple()
//    mapWithOrikaRenamedFields()
//    mapWithOrikaComplex()
//    mapWithDozerSimple()
    mapWithDozerRenamedFields()
}

fun mapWithOrikaSimple() {
    println("=========== Mapping with orika =================")
    val source = getSourceSimple()

    val mapper = OrikaMapper()
    val destObject = mapper.map(source, Destination::class.java)

    println("Source:      ${source}")
    println("Destination: ${destObject}")
    println()
}

fun mapWithOrikaComplex() {
    println("=========== mapping with orika to DestinationCustomStructure=================")
    val source = Source2()
    source.one = "one"
    source.two = true
    source.three = "three"
    source.five = 55
    source.listOfText = mutableListOf("one", "two")

    val mapper = OrikaMapper()
    val destObject2 = mapper.map(source, DestinationCustomStructure::class.java)
    println("Source:      ${source}")
    println("Destination: ${destObject2}")
    println()
}

fun mapWithOrikaRenamedFields() {
    println("=========== mapping with orika to DestinationRenamedFields=================")
    val source = Source2()
    source.one = "one"
    source.two = true
    source.three = "three"
    source.listOfText = mutableListOf("one", "two")

    val mapper = OrikaMapper()
    val destObject2 = mapper.map(source, DestinationRenamedFields::class.java)
    println("Source:      ${source}")
    println("Destination: ${destObject2}")
    println()
}

fun mapWithDozerSimple() {
    println("=========== mapping with Dozer =================")
    val source = getSourceSimple()

    val mapper = DozerBeanMapperBuilder.buildDefault()
    val destObject = mapper.map(source, Destination::class.java)
    println("Source:      ${source}")
    println("Destination: ${destObject}")
    println()
}

fun mapWithDozerRenamedFields() {
    println("=========== mapping with Dozer DestinationRenamedFields =================")
    val source = Source2()
    source.one = "one"
    source.two = true
    source.three = "three"
    source.listOfText = mutableListOf("one", "two")

    val mapper = DozerBeanMapperBuilder.buildDefault()

    val destObject = mapper.map(source, DestinationRenamedFields::class.java)
    println("Source:      ${source}")
    println("Destination: ${destObject}")
    println()
}

fun getSourceSimple(): Source {
    val source = Source()
    source.willMap1 = "will map"
    source.willMap2 = true
    source.willMap3 = "false"
    source.willMap4 = "true"
    source.willMap5 = 1 // will use custom IntToBooleanConvertor
    source.isWillNotMap1 = "will not map"
    source.isWillNotMap2 = true
    return source
}
