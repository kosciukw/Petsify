package com.kosciukw.services.internal.session.time

import kotlinx.cinterop.ExperimentalForeignApi
import platform.posix.time

@OptIn(ExperimentalForeignApi::class)
internal actual fun currentEpochSeconds(): Long = time(null).toLong()
