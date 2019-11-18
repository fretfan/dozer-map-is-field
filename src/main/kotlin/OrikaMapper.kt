import ma.glasnost.orika.CustomMapper
import ma.glasnost.orika.MapperFactory
import ma.glasnost.orika.MappingContext
import ma.glasnost.orika.impl.ConfigurableMapper
import ma.glasnost.orika.impl.DefaultMapperFactory


class OrikaMapper : ConfigurableMapper() {
    override fun configure(factory: MapperFactory) {
        factory.classMap(Source2::class.java, DestinationRenamedFields::class.java)
            .field("one", "nested.one")
            .field("two", "nested.two")
            .field("three", "threeRenamed")
            .byDefault()
            .register()

        factory.classMap(Source2::class.java, DestinationCustomStructure::class.java)
            .byDefault()
            .customize(
                object : CustomMapper<Source2, DestinationCustomStructure>() {
                    override fun mapAtoB(
                        a: Source2?,
                        b: DestinationCustomStructure,
                        context: MappingContext?
                    ) {
                        if (a != null) {
                            b.nested = NestedDestination(a.one, a.two)
                        }
                    }
                }
            )
        .register()


        // register custom type convertor
        val converterFactory = factory.converterFactory
        converterFactory.registerConverter(IntToBooleanConvertor())
    }

    override fun configureFactoryBuilder(factoryBuilder: DefaultMapperFactory.Builder) {
        factoryBuilder.mapNulls(false)
        super.configureFactoryBuilder(factoryBuilder)
    }

}

