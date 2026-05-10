# Release Checklist

## 1. Update metadata

1. Edit `gradle.properties`:
   - Bump `mod_version`
   - Keep `minecraft_version` accurate
2. Update `CHANGELOG.md`.

## 2. Verify local build

```bash
./gradlew clean build
```

Expected artifacts:

- `build/libs/arsnouveaulecterntweaks-<mod_version>.jar`
- `build/libs/arsnouveaulecterntweaks-<mod_version>-sources.jar`

## 3. Commit

```bash
git add .
git commit -m "release: v<mod_version> for mc<minecraft_version>"
git push
```

## 4. Tag and publish

```bash
git tag v<mod_version>-mc<minecraft_version>
git push origin v<mod_version>-mc<minecraft_version>
```

Tag push triggers `.github/workflows/release.yml` and uploads jars to GitHub Releases.

## 5. Post-release checks

1. Verify release page has jar assets.
2. Test jar in a clean modpack instance.
3. (Optional) Sync the same jar to CurseForge/Modrinth.

