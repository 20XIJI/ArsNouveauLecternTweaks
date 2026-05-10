# Ars Nouveau Lectern Tweaks

[English version](./README.en.md)

一个适用于 Minecraft 1.21.1 的 NeoForge 客户端附属模组（依赖 Ars Nouveau）。

它会禁用存储讲台（Storage Lectern）搜索框的自动聚焦，避免打开界面后打字被搜索框劫持。

## 功能

- 禁用存储讲台搜索框自动聚焦（默认开启）。
- 在搜索框未聚焦时，保留按物品栏键（默认 `E` 键）关闭界面的行为。
- 提供客户端配置项：`lectern_tweaks.disableAutoFocus`。

## 兼容性

- Minecraft：`1.21.1`
- NeoForge：`21.1.x`
- Ars Nouveau：`5.11.x`
- Java：`21`

## 下载

可从 **GitHub Releases** 下载构建好的 jar：

- 最新版本：`https://github.com/20XIJI/ArsNouveauLecternTweaks/releases/latest`

Release 资产由 GitHub Actions 在打标签后自动构建并上传。

## 安装

1. 安装 Minecraft `1.21.1` 对应的 NeoForge。
2. 安装适配 Minecraft `1.21.1` 的 Ars Nouveau（“同版本”是指 Minecraft 版本一致，不是要求模组版本号相同）。
3. 将 `arsnouveaulecterntweaks-<version>.jar` 放入 `mods/` 文件夹。
4. 启动游戏。

## 配置

客户端配置文件会生成在：

- `config/arsnouveaulecterntweaks-client.toml`

关键配置：

- `lectern_tweaks.disableAutoFocus = true`（默认值）

## 从源码构建

Windows：

```powershell
.\gradlew.bat clean build
```

Linux/macOS：

```bash
./gradlew clean build
```

构建产物：

- `build/libs/arsnouveaulecterntweaks-<mod_version>.jar`
- `build/libs/arsnouveaulecterntweaks-<mod_version>-sources.jar`

## 版本管理

- `gradle.properties` 中的 `mod_version` 采用 SemVer：`MAJOR.MINOR.PATCH`。
- `gradle.properties` 中的 `minecraft_version` 表示目标 MC 版本。
- Git 标签建议格式：`v<mod_version>-mc<minecraft_version>`
- 示例：`v1.0.0-mc1.21.1`

## 发布流程

1. 更新 `gradle.properties` 里的 `mod_version`。
2. 更新 `CHANGELOG.md`。
3. 提交并推送代码。
4. 创建并推送标签：

```bash
git tag v1.0.1-mc1.21.1
git push origin v1.0.1-mc1.21.1
```

5. GitHub Actions 会自动构建并将 jar 上传到 Release 资产中。
