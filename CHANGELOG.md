# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.5.0] - 2026-05-29

### Changed

- Improved Wayward Lantern performance.

### Fixed

- Fixed Cat sitting model.
- Fixed issues with tamed Wolf AI.
- Fixed crash with Caverns and Chasms Rats.
- Fixed pets sometimes changing variants when using Pet Beds.
- Fixed issues with Scorched Guns mobs.
- Fixed race condition with Wayward Lantern teleportation.

## [1.4.0] - 2026-05-26

### Added

- Ocelots are now tamable like they once were in Vanilla, using raw fishes (anything in the `#minecraft:cat_food` tag).
- Added a config option to disable pet teleportation.
- Added a config option to stop pets from attacking when their health dips below a specific threshold (default 20%).

### Changed

- Added collar tags to `#c:enchantables`.
- Adjusted loot table weights.

### Fixed

- Fixed Feather on a Stick held model not being cast.

## [1.3.1] - 2026-05-16

### Fixed

- Fixed baby animals being unkillable.
- Fixed shifting not bypassing swing through pets.

## [1.3.0] - 2026-05-14

### Added

- Added `command_blacklist` tag for entities that shouldn't use the trinary command system.

### Fixed

- Fixed invalid data crash on loot generation.

## [1.2.0] - 2026-05-14

### Fixed

- Fixed buggy Shield behaviour.
- Fixed various behaviour regressions from Domestication Innovation.

## [1.1.0] - 2026-05-14

### Fixed

- Fixed missing Command Drum recipe.
- Pet Beds now follow the vanilla dye order in the creative tab.
- Fixed Pet Beds not acting as Villager workstations on Fabric.
- Fixed missing tags for some curses.

## [1.0.1] - 2026-05-13

### Fixed

- Renamed "disk jockey" to "disc jockey".

## [1.0.0] - 2026-05-13

- Initial release.