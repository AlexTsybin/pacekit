package io.github.alextsybin.pacekit.internal

internal const val METERS_PER_KILOMETER = 1000.0
internal const val METERS_PER_MILE = 1609.344

internal const val SECONDS_PER_MINUTE = 60.0
internal const val SECONDS_PER_HOUR = 3600.0

internal const val KILOMETERS_PER_MILE =
    METERS_PER_MILE / METERS_PER_KILOMETER

internal const val MPS_TO_KPH =
    SECONDS_PER_HOUR / METERS_PER_KILOMETER

internal const val MPH_TO_MPS =
    METERS_PER_MILE / SECONDS_PER_HOUR