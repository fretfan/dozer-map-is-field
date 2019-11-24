import ma.glasnost.orika.CustomConverter
import ma.glasnost.orika.MappingContext
import ma.glasnost.orika.metadata.Type

class StringToBooleanConvertor : CustomConverter<String, Boolean>() {
    override fun convert(
        source: String?,
        destinationType: Type<out Boolean>?,
        mappingContext: MappingContext?
    ): Boolean? {
        when (source) {
            null -> return null
            "Y" -> return true
            "N" -> return false
        }
        throw RuntimeException("Unsupported value: $source")
    }
}