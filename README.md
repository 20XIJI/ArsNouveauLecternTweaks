# Ars Nouveau Lectern Tweaks

[English version](./README.en.md)

你是否遇到过这样的情况：打开 Ars Nouveau 的存储讲台后，一打字就被吸入搜索框，无法正常操作界面？

这个模组正是为此而生。它禁用了存储讲台搜索框的自动聚焦，让每次打开界面时光标不再自动跳入搜索框，操作更自然。

## 功能

- **禁用自动聚焦**：打开存储讲台后，搜索框不会自动选中，打字不会直接进入搜索框（默认开启）。
- **快速关闭**：搜索框未选中时，按物品栏键（默认 `E`）可直接关闭界面。
- **自由开关**：通过客户端配置文件可开启或关闭本功能。

## 前置要求

本模组依赖 **Ars Nouveau**（一款魔法主题模组），需要以下环境：

| 项目 | 版本 |
|------|------|
| Minecraft | `1.21.1` |
| NeoForge | `21.1.x` |
| Ars Nouveau | `5.11.x` |
| Java | `21` |

> 提示：NeoForge 可从 https://neoforged.net 下载。Ars Nouveau 可在 CurseForge 或 Modrinth 上找到。

## 下载

从 **GitHub Releases** 获取最新模组文件（`.jar`）：

- <https://github.com/20XIJI/ArsNouveauLecternTweaks/releases/latest>

## 安装步骤

1. **安装游戏平台**：为 Minecraft `1.21.1` 安装对应版本的 NeoForge（安装方式请参考 NeoForge 官网教程）。
2. **安装 Ars Nouveau**：下载适配 Minecraft `1.21.1` 的 Ars Nouveau 模组（只需 Minecraft 版本一致即可，不要求 Ars Nouveau 版本号精确匹配）。
3. **放入模组**：将 `arsnouveaulecterntweaks-<版本号>.jar` 拖入游戏目录下的 `mods/` 文件夹中。
4. **启动游戏**：确认模组加载成功后即可生效。

## 配置

启动一次游戏后，配置文件会自动生成在：

```
config/arsnouveaulecterntweaks-client.toml
```

打开后可以看到以下内容：

```
[lectern_tweaks]
    disableAutoFocus = true
```

将 `true` 改为 `false` 可恢复自动聚焦的原始行为，重启游戏后生效。

---

以下内容面向模组开发者及模组包作者。

## 从源码构建

Windows：

```powershell
.\gradlew.bat clean build
```

Linux/macOS：

```bash
./gradlew clean build
```

构建产物位于：

- `build/libs/arsnouveaulecterntweaks-<mod_version>.jar`
- `build/libs/arsnouveaulecterntweaks-<mod_version>-sources.jar`

## 版本管理

- `gradle.properties` 中的 `mod_version` 遵循语义化版本控制（SemVer）：`MAJOR.MINOR.PATCH`。
- `gradle.properties` 中的 `minecraft_version` 表示对应的 Minecraft 版本。
- Git 标签建议格式：`v<mod_version>-mc<minecraft_version>`
- 示例：`v1.0.0-mc1.21.1`

## 发布流程

1. 更新 `gradle.properties` 中的 `mod_version`。
2. 更新 `CHANGELOG.md`。
3. 提交并推送代码。
4. 创建并推送 Git 标签：

```bash
git tag v1.0.1-mc1.21.1
git push origin v1.0.1-mc1.21.1
```

5. GitHub Actions 将自动构建并将产物上传至 Release。
