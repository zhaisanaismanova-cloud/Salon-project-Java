# Salon - Mini Project (Java)

Java console application that manages beauty salons.
Practises object-oriented constructs.

## What it does
- Keeps all salons in a class extent and prints them
- Saves the extent to a file (`salons.dat`) and loads it back (extent persistency, Java serialization)
- Validates the data (empty names, null values, opening hours where closing is before opening, removing the last service)
- Calculates the working hours of a salon from its opening hours (derived attribute)

## Implemented constructs
- Extent and extent persistency
- Complex attribute (`OpeningHours`)
- Optional attribute (`description`)
- Multi-valued attribute (`services`)
- Class attribute (`brandName`)
- Derived attribute (`getWorkingHours()`)
- Class methods
- Override (`toString()`)
- Overload (`addService`)

## Files
- `Main.java` - examples showing how each construct is used
- `Salon.java` - main class
- `OpeningHours.java` - class used as a complex attribute

## How to run
    javac *.java
    java Main

Requires Java 8 or newer.
