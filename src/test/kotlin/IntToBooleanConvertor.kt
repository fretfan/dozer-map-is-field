import ma.glasnost.orika.MappingContext
import ma.glasnost.orika.converter.BidirectionalConverter
import ma.glasnost.orika.metadata.Type

class IntToBooleanConvertor : BidirectionalConverter<Int, Boolean>() {
    override fun convertFrom(source: Boolean?, destinationType: Type<Int>?, mappingContext: MappingContext?): Int {
        return if (source == true) 1 else 0
    }

    override fun convertTo(source: Int?, destinationType: Type<Boolean>?, mappingContext: MappingContext?): Boolean {
        return source == 1
    }
}