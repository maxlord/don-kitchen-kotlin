package ru.ls.test

import uk.co.jemos.podam.api.AttributeMetadata
import uk.co.jemos.podam.api.DataProviderStrategy
import uk.co.jemos.podam.api.PodamFactory
import uk.co.jemos.podam.api.PodamFactoryImpl

class PojoFactory(private val factory: PodamFactory = PodamFactoryImpl()) {
    private val instanceStrategy = InstanceDataProviderStrategy(factory.strategy)

    init {
        factory.strategy = instanceStrategy
    }

    fun <T : Any> replaceWithInstance(from: Class<T>, instance: T): PojoFactory {
        return apply {
            instanceStrategy.addInstance(from, instance)
        }
    }

    inline fun <reified A : Any> replaceWithInstance(instance: A): PojoFactory {
        return apply {
            replaceWithInstance(A::class.java, instance)
        }
    }

    fun <T : Any> replace(from: Class<T>, to: Class<out T>): PojoFactory {
        return apply {
            factory.strategy.addOrReplaceSpecific(from, to)
        }
    }

    inline fun <reified A : Any, reified B : A> replace(): PojoFactory {
        return apply { replace(A::class.java, B::class.java) }
    }

    fun <T> create(type: Class<T>): T {
        return factory.manufacturePojoWithFullData(type) ?:
                throw RuntimeException("Podam failed to instantiate ${type.simpleName}")
    }

    inline fun <reified T : Any> create(): T {
        return create(T::class.java)
    }

    private class InstanceDataProviderStrategy(strategy: DataProviderStrategy) : DataProviderStrategy by strategy {

        private val instances = mutableMapOf<Class<*>, Any>()

        override fun getMemoizedObject(attributeMetadata: AttributeMetadata): Any? {
            return instances[attributeMetadata.pojoClass]
        }

        fun <T : Any> addInstance(type: Class<T>, instance: T) {
            instances[type] = instance
        }

    }
}