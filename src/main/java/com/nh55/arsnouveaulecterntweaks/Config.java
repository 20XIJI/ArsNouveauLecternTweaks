package com.nh55.arsnouveaulecterntweaks;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    public static final ModConfigSpec CLIENT_SPEC;

    public static ModConfigSpec.BooleanValue DISABLE_AUTO_FOCUS;

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

        builder.pop();

        CLIENT_SPEC = builder.build();
    }
}
