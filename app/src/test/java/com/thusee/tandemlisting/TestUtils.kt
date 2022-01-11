package com.thusee.tandemlisting

object TestUtils {

    fun setProperty(instance: Any, name: String, param: Any?) {

        val field = instance.javaClass.getDeclaredField(name)
        field.isAccessible = true
        field.set(instance, param)
    }

    fun invokeMethod(instance: Any, methodName: String, vararg arguments: Any): Any? {

        val clazz = arrayOfNulls<Class<*>>(arguments.size)
        arguments.forEachIndexed { index, it ->
            when (it) {
                is Integer -> clazz[index] = Int::class.java
                else -> clazz[index] = it::class.java
            }
        }

        val method = instance.javaClass.getDeclaredMethod(methodName, *clazz)
        method.isAccessible = true
        return method.invoke(instance, *arguments)
    }

}