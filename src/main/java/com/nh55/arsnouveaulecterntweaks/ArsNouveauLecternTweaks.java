package com.nh55.arsnouveaulecterntweaks;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ArsNouveauLecternTweaks.MOD_ID)
public class ArsNouveauLecternTweaks {
    public static final String MOD_ID = "arsnouveaulecterntweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public ArsNouveauLecternTweaks(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);
        LOGGER.info("Ars Nouveau Lectern Tweaks loaded!");
    }
}
