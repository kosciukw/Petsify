package com.kosciukw.services.internal.session.time

internal actual fun currentEpochSeconds(): Long = System.currentTimeMillis() / 1000
