# Ars Nouveau Lectern Tweaks

A small NeoForge client-side addon for Ars Nouveau on Minecraft 1.21.1.

It disables the auto-focus behavior of the Storage Lectern search box, so typing does not get hijacked when you open the screen.

## Features

- Disable auto-focus on Storage Lectern search input (enabled by default).
- Keep `Inventory` key close behavior when search is not focused.
- Configurable client option: `lectern_tweaks.disableAutoFocus`.

## Compatibility

- Minecraft: `1.21.1`
- NeoForge: `21.1.x`
- Ars Nouveau: `5.11.x`
- Java: `21`

## Download

Download compiled jars from **GitHub Releases**:

- Latest: `https://github.com/20XIJI/ArsNouveauLecternTweaks/releases/latest`

Release assets are built from tags and uploaded automatically by GitHub Actions.

## Installation

1. Install NeoForge for Minecraft `1.21.1`.
2. Install Ars Nouveau for the same MC version.
3. Put `arsnouveaulecterntweaks-<version>.jar` into your `mods/` folder.
4. Start the game.

## Configuration

The client config is generated at:

- `config/arsnouveaulecterntweaks-client.toml`

Key:

- `lectern_tweaks.disableAutoFocus = true` (default)

## Build From Source

Windows:

```powershell
.\gradlew.bat clean build
```

Linux/macOS:

```bash
./gradlew clean build
```

Build output:

- `build/libs/arsnouveaulecterntweaks-<mod_version>.jar`
- `build/libs/arsnouveaulecterntweaks-<mod_version>-sources.jar`

## Versioning

- `mod_version` in `gradle.properties` follows SemVer: `MAJOR.MINOR.PATCH`.
- `minecraft_version` in `gradle.properties` tracks the target MC version.
- Git tags should use: `v<mod_version>-mc<minecraft_version>`
  - Example: `v1.0.0-mc1.21.1`

## Release Flow

1. Update `mod_version` in `gradle.properties`.
2. Update `CHANGELOG.md`.
3. Commit and push.
4. Create and push tag:

```bash
git tag v1.0.1-mc1.21.1
git push origin v1.0.1-mc1.21.1
```

5. GitHub Actions builds and publishes jars to Release assets.
