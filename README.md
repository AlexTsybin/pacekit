# PaceKit

[![License: Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Kotlin/JVM](https://img.shields.io/badge/Kotlin-JVM-7F52FF)](https://kotlinlang.org/)

PaceKit is a lightweight, type-safe Kotlin/JVM library for running pace, speed, distance conversions, and estimated finish time calculations.

Designed for Android and other Kotlin/JVM applications, it uses Kotlin value classes and `kotlin.time.Duration` to make calculations explicit and reduce unit-related mistakes. The library itself does not depend on the Android SDK or third-party runtime libraries beyond the Kotlin standard library.

## Features

- Type-safe `Distance`, `Pace`, and `Speed` value classes.
- Metric and imperial units, with explicit factory methods and conversion properties.
- Average pace and speed calculations from distance and elapsed time.
- Estimated finish times at a constant pace, with optional additional time for stops or delays.
- Input validation and support for fractional values.

## Installation

PaceKit is in early development. Version `0.1.0` is not yet available on Maven Central.

To try it locally, clone the repository and publish the library to Maven Local:

```bash
git clone https://github.com/AlexTsybin/pacekit.git
cd pacekit
./gradlew :pacekit:publishToMavenLocal
```

In your consumer project's `settings.gradle.kts`, add the following inside the existing dependency repositories block (`dependencyResolutionManagement.repositories`):

```kotlin
mavenLocal {
    content {
        includeModule("io.github.alextsybin", "pacekit")
    }
}
```

Add the dependency to your module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("io.github.alextsybin:pacekit:0.1.0")
}
```

PaceKit is compiled with Kotlin 2.2.10 and targets JVM 11 bytecode. Building the repository also requires an environment compatible with its Gradle wrapper and Android demo module.

## Usage

### Calculate average pace

```kotlin
import io.github.alextsybin.pacekit.PaceCalculator
import io.github.alextsybin.pacekit.model.Distance
import kotlin.time.Duration.Companion.minutes

val pace = PaceCalculator.calculatePace(
    distance = Distance.kilometers(10.0),
    duration = 35.minutes
)

println(pace.secondsPerKilometer) // 210.0
println(pace.minutesPerKilometer) // 3.5
```

Pace is expressed in decimal units: `3.5` minutes per kilometer means 3 minutes and 30 seconds per kilometer, not 3 minutes and 50 seconds.

### Calculate average speed

```kotlin
import io.github.alextsybin.pacekit.PaceCalculator
import io.github.alextsybin.pacekit.model.Distance
import kotlin.time.Duration.Companion.seconds

val speed = PaceCalculator.calculateSpeed(
    distance = Distance.kilometers(1.0),
    duration = 200.seconds
)

println(speed.metersPerSecond)   // 5.0
println(speed.kilometersPerHour)  // 18.0
println(speed.milesPerHour)       // Approximately 11.18
```

### Estimate finish time

```kotlin
import io.github.alextsybin.pacekit.PaceCalculator
import io.github.alextsybin.pacekit.model.Distance
import io.github.alextsybin.pacekit.model.Pace
import kotlin.time.Duration.Companion.minutes

val distance = Distance.kilometers(10.0)
val pace = Pace.minutesPerKilometer(3.5)

val finishTime = PaceCalculator.calculateFinishTime(distance, pace)
println(finishTime) // 35m

val totalTime = PaceCalculator.calculateFinishTime(
    distance = distance,
    pace = pace,
    extraTime = 2.minutes
)
println(totalTime) // 37m
```

Finish time is a mathematical estimate based on a constant pace, not a race performance prediction.

### Convert units

```kotlin
import io.github.alextsybin.pacekit.model.Distance
import io.github.alextsybin.pacekit.model.Pace
import io.github.alextsybin.pacekit.model.Speed

val distance = Distance.miles(1.0)
println(distance.meters)     // 1609.344
println(distance.kilometers) // 1.609344

val pace = Pace.minutesPerKilometer(4.0)
println(pace.secondsPerKilometer) // 240.0

val speed = Speed.kilometersPerHour(18.0)
println(speed.metersPerSecond) // 5.0
```

## API overview

| Type | Supported units |
| --- | --- |
| `Distance` | Meters, kilometers, miles |
| `Pace` | Seconds per kilometer, minutes per kilometer, seconds per mile |
| `Speed` | Meters per second, kilometers per hour, miles per hour |
| `Duration` | Kotlin standard library time units |

Distance and speed values must be finite and non-negative in their stored representation. Pace must be finite and strictly positive. Average pace requires a positive distance; average pace and speed require a finite, positive duration. Additional finish time must be finite and non-negative. Invalid calculated values or inputs may result in `IllegalArgumentException`.

## Documentation

The public API is documented with KDoc and includes usage samples. Generate the HTML API reference locally:

```bash
./gradlew :pacekit:dokkaGenerate
```

Open `pacekit/build/dokka/html/index.html` in your browser.

## Development

Run the library's unit tests, ABI validation, and documentation generation:

```bash
./gradlew :pacekit:test :pacekit:checkLegacyAbi :pacekit:dokkaGenerate
```

The repository contains two modules:

- `:pacekit` — the standalone Kotlin/JVM library.
- `:app` — the Android demo module.

The library uses Kotlin explicit API mode and ABI validation to track changes to its public binary interface.

## Contributing

Bug reports, feature suggestions, and pull requests are welcome. For substantial API changes, please open an issue to discuss the proposed change first.

## License

PaceKit is licensed under the [Apache License 2.0](LICENSE).
