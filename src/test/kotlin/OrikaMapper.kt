import ma.glasnost.orika.CustomMapper
import ma.glasnost.orika.MapperFactory
import ma.glasnost.orika.MappingContext
import ma.glasnost.orika.constructor.ConstructorResolverStrategy
import ma.glasnost.orika.impl.ConfigurableMapper
import ma.glasnost.orika.impl.DefaultMapperFactory
import ma.glasnost.orika.metadata.ClassMap
import ma.glasnost.orika.metadata.Type
import java.lang.reflect.Constructor


class OrikaMapper : ConfigurableMapper() {
    override fun configure(factory: MapperFactory) {
        factory.classMap(Source3::class.java, Destination3::class.java)
            .field("text1", "nested.text11")
            .field("text2", "nested.text22")
            .field("text3", "threeRenamed")
            .byDefault()
            .register()

        factory.classMap(Source6::class.java, Destination6::class.java)
            .field("text1", "nested.text1")
            .byDefault() // if byDefault() called last, will not map "text1 >> text1" again
            .register()

        factory.classMap(Source6::class.java, Destination6B::class.java)
            .byDefault() // if byDefault() called first "text1 >> text1" will map
            .field("text1", "nested.text1") // "text1 >> nested.text1" will also map
            .register()

        factory.classMap(Source::class.java, DestinationCustomStructure::class.java)
            .byDefault() // will map 'bool1' and 'text1', but could define in mapAToB also
            .customize(object : CustomMapper<Source, DestinationCustomStructure>() {
                override fun mapAtoB(a: Source?, b: DestinationCustomStructure?, context: MappingContext?) {
                    if (a != null) {
                        b?.nested = NestedDestination2(a.number1, a.date1, a.dateTime1, a.list1)
                    }
                }
            })
            .register()


        // register custom type convertor
        val converterFactory = factory.converterFactory
        converterFactory.registerConverter(IntToBooleanConvertor())
        converterFactory.registerConverter(StringToBooleanConvertor())
        super.configure(factory)
    }

    override fun configureFactoryBuilder(factoryBuilder: DefaultMapperFactory.Builder) {
        factoryBuilder.mapNulls(false)
        factoryBuilder.constructorResolverStrategy(object : ConstructorResolverStrategy {
            /**
             * Fixes Orika's bug mapping nulls into destination, even when mapNulls is set to false.
             * Orika's default SimpleConstructorResolverStrategy picks constructor with most parameters first, not zero-arg constructor.
             * If class has some default value for field declared and only zero-arg constructor - default values work.
             * When using Lombok or Kotlin data class - constructors with all-args are generated.
             * Orika calls all-args constructor and sets null to values via constructor, overwriting default values.
             * Ideally developer should define in constructor, if inputValue == null, assign default value. Nobody does that, to avoid boilerplate.
             * You get a bug where Destination object with default values and multiple constructors gets nulls mapped even when Orika's mapNulls is set to false.
             *
             * To fix this: force Orika to always use zero-arg constructor.
             * Partially copy-paste from SimpleConstructorResolverStrategy.resolve() method
             */
            override fun <T : Any?, A : Any?, B : Any?> resolve(
                classMap: ClassMap<A, B>,
                sourceType: Type<T>?
            ): ConstructorResolverStrategy.ConstructorMapping<T> {
                val aToB = classMap.bType == sourceType
                val targetClass = if (aToB) classMap.bType else classMap.aType
                val constructors = targetClass.rawType.declaredConstructors as Array<Constructor<T>>
                constructors.forEach { aConstructor ->
                    val constructorParams = aConstructor.genericParameterTypes
                    if (isZeroArgConstructor(constructorParams)) {
                        val constructorMapping = ConstructorResolverStrategy.ConstructorMapping<T>()
                        constructorMapping.constructor = aConstructor
                    }
                }
                throw  RuntimeException("Zero-argument constructor not found! Declare one!")
            }
        })
        super.configureFactoryBuilder(factoryBuilder)
    }

    private fun isZeroArgConstructor(constructorParams: Array<java.lang.reflect.Type>) = constructorParams.isEmpty()
}

