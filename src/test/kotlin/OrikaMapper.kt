import ma.glasnost.orika.MapperFactory
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

//        factory.classMap(Source2::class.java, DestinationCustomStructure::class.java)
//            .byDefault()
//            .customize(
//                object : CustomMapper<Source2, DestinationCustomStructure>() {
//                    override fun mapAtoB(
//                        a: Source2?,
//                        b: DestinationCustomStructure,
//                        context: MappingContext?
//                    ) {
//                        if (a != null) {
//                            b.nested = NestedDestination(a.one, a.two)
//                        }
//                    }
//                }
//            )
//        .register()


        // register custom type convertor
//        val converterFactory = factory.converterFactory
//        converterFactory.registerConverter(IntToBooleanConvertor())
        super.configure(factory)
    }

    override fun configureFactoryBuilder(factoryBuilder: DefaultMapperFactory.Builder) {
        factoryBuilder.mapNulls(false)
        factoryBuilder.constructorResolverStrategy(object : ConstructorResolverStrategy {
            // Orika's SimpleConstructorResolverStrategy picks constructor with most parameters first.
            // If class has some default value for field declared, it will be overwritten with value coming into constructor (usually null).
            // When mapNulls set to false, it will fail.
            // To fix it: force Orika to pick empty arguments constructor first.
            override fun <T : Any?, A : Any?, B : Any?> resolve(
                classMap: ClassMap<A, B>,
                sourceType: Type<T>?
            ): ConstructorResolverStrategy.ConstructorMapping<T> {
                val aToB = classMap.bType == sourceType
                val targetClass = if (aToB) classMap.bType else classMap.aType
                val constructors = targetClass.rawType.declaredConstructors as Array<Constructor<T>>
                constructors.forEach { aConstructor ->
                    val constructorParams = aConstructor.genericParameterTypes
                    if (constructorParams.isEmpty()) {
                        val constructorMapping = ConstructorResolverStrategy.ConstructorMapping<T>()
                        constructorMapping.constructor = aConstructor
                    }
                }
                throw  RuntimeException("Zero-argument constructor not found! Declare one!")
            }
        })
        super.configureFactoryBuilder(factoryBuilder)
    }
}

