package com.nh55.arsnouveaulecterntweaks;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    public static final ModConfigSpec CLIENT_SPEC;

    public static ModConfigSpec.BooleanValue DISABLE_AUTO_FOCUS;
    public static ModConfigSpec.IntValue ROW_COUNT_COLLAPSED;
    public static ModConfigSpec.IntValue ROW_COUNT_EXPANDED;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("Ars Nouveau Lectern Tweaks Configuration")
               .push("lectern_tweaks");

        DISABLE_AUTO_FOCUS = builder
                .comment(
                    "Disable auto-focus on the Storage Lectern search box.",
                    "When enabled, you must click the search box to type in it.",
                    "This prevents conflicts with JEI/REI search boxes.",
                    "Default: true"
                )
                .define("disableAutoFocus", true);

        ROW_COUNT_COLLAPSED = builder
                .comment(
                    "Number of item rows displayed when the crafting grid is collapsed.",
                    "Vanilla default: 3. Higher values show more storage rows.",
                    "Range: 3-4, Default: 4"
                )
                .defineInRange("rowCountCollapsed", 4, 3, 4);

        ROW_COUNT_EXPANDED = builder
                .comment(
                    "Number of item rows displayed when the crafting grid is expanded.",
                    "Vanilla default: 7. Higher values show more storage rows.",
                    "Range: 3-7, Default: 7"
                )
                .defineInRange("rowCountExpanded", 7, 3, 7);

        builder.pop();

        CLIENT_SPEC = builder.build();
    }
}
