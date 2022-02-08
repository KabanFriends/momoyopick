package io.github.kabanfriends.momoyopick;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MomoyoPick implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	public static final ResourceLocation MOMOYO_SONG = new ResourceLocation("momoyopick:momoyo");
	public static SoundEvent MOMOYO_SONG_EVENT = new SoundEvent(MOMOYO_SONG);

	public static boolean isMomoyo;

	@Override
	public void onInitialize() {
		Registry.register(Registry.SOUND_EVENT, MOMOYO_SONG, MOMOYO_SONG_EVENT);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				if (client.player.getMainHandItem() != null && client.player.getOffhandItem() != null) {
					if ((client.player.getMainHandItem().getItem() == Items.GOLDEN_PICKAXE && client.player.getOffhandItem().getItem() == Items.GOLDEN_SHOVEL)
							|| (client.player.getMainHandItem().getItem() == Items.GOLDEN_SHOVEL && client.player.getOffhandItem().getItem() == Items.GOLDEN_PICKAXE)) {
						if (!isMomoyo) {
							isMomoyo = true;
							client.player.playNotifySound(MOMOYO_SONG_EVENT, SoundSource.RECORDS, 1.0f, 1.0f);
						}
					}else {
						if (isMomoyo) {
							isMomoyo = false;
							client.getSoundManager().stop(MOMOYO_SONG, SoundSource.RECORDS);
						}
					}
				}
			}
		});
	}
}
