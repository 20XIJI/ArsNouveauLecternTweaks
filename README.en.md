# Ars Nouveau Lectern Tweaks

[简体中文说明](./README.md)

Ever opened an Ars Nouveau Storage Lectern and found your keystrokes hijacked by the search box? This mod is the fix.

It disables the auto-focus on the Storage Lectern search box, so your cursor no longer jumps into it every time you open the screen. A small tweak that makes a big difference in daily use.

## Features

- **Disable auto-focus**: The search box no longer grabs your input when you open the Storage Lectern (enabled by default).
- **Quick close**: Press your inventory key (default `E`) to close the screen when the search box is not focused.
- **Toggle on/off**: Change the behavior at any time via the client config file.

## Requirements

This mod depends on **Ars Nouveau** (a magic-themed mod). The following environment is needed:

| Component | Version |
|-----------|---------|
| Minecraft | `1.21.1` |
| Ars Nouveau | `5.11.x` |

## Download

Download the latest release from **GitHub Releases**:

- <https://github.com/20XIJI/ArsNouveauLecternTweaks/releases/latest>

## Configuration

After launching the game once, the config file is generated at:

```
config/arsnouveaulecterntweaks-client.toml
```

It should look like this:

```
[lectern_tweaks]
    disableAutoFocus = true
```

Change `true` to `false` to restore the original auto-focus behavior, then restart the game.

---

The sections below are intended for mod developers and modpack authors.

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
- `minecraft_version` in `gradle.properties` tracks the target Minecraft version.
- Git tags should use: `v<mod_version>-mc<minecraft_version>`
- Example: `v1.0.0-mc1.21.1`

## Release Flow

1. Update `mod_version` in `gradle.properties`.
2. Update `CHANGELOG.md`.
3. Commit and push.
4. Create and push a Git tag:

```bash
git tag v1.0.1-mc1.21.1
git push origin v1.0.1-mc1.21.1
```

5. GitHub Actions builds the release and uploads the artifacts.

