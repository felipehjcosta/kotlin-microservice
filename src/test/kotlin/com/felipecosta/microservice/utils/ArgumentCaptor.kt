package com.felipecosta.microservice.utils

import org.mockito.ArgumentCaptor

inline fun <reified T : Any> argumentCaptor() = ArgumentCaptor.forClass(T::class.java)
//inline fun <reified T : Any> capture(captor: ArgumentCaptor<T>): T = captor.capture() ?: createInstance<T>()
//inline fun <reified T : Any> capture(noinline consumer: (T) -> Unit): T {
//    var times = 0
//    return Matchers.argThat { if (++times == 1) consumer.invoke(this); true }
//}