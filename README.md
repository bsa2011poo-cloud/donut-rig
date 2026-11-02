# Donut SMP Rig (Minecraft Java 1.21.11)

Client-side Fabric mod project. Requires Java 21, Fabric Loader 0.18.1+, and Fabric API for 1.21.11.

## Current prototype controls
- `R`: toggle Rig ON/OFF
- `O`: open the Rig menu
- `P`: copy the contents of the dispenser you're looking at into a local client-side preview snapshot and record its coordinates for the selected game/role
- Menu buttons switch Blackjack/Paper, HOST/VIEWER, and Rig ON/OFF

## Important limitations
This prototype only reads dispenser contents already available to the local client and copies them into local memory. It does not duplicate server items, change Donut SMP's RNG, or change what other players see. It does not yet implement private item-drop rendering suppression, lever coordinate capture, persistent configuration, or a finished item-preview panel. Those require additional implementation and in-game testing against the server/client build.

## Build
Run `gradle build` (or use a Gradle 8.14+ wrapper) from this directory. The remapped mod JAR will be in `build/libs/`.
