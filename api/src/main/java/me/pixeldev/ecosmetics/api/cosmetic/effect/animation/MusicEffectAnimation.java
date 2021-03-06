package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.NoteColor;

import java.lang.ref.WeakReference;

import static me.pixeldev.ecosmetics.api.util.MathUtils.*;

public class MusicEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	public MusicEffectAnimation(WeakReference<Player> ownerReference,
															CosmeticSpectators spectators) {
		super(ownerReference, spectators, ParticleEffect.NOTE, 5);
	}

	@Override
	protected void runAnimation(Player player) {
		for (int i = 0; i < 6; i++) {
			NoteColor noteColor = NoteColor.random();
			Location location = player.getLocation().add(
				randomDouble(-1.5, 1.5), randomDouble(0, 2.5), randomDouble(-1.5, 1.5)
			);

			ParticleBuilder particlePacket = new ParticleBuilder(effect, location)
				.setParticleData(noteColor);

			spectators.consumeAsPlayers(spectator -> particlePacket.display(player));
		}
	}

}
