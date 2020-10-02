package zedly.zenchantments.enchantments;

import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import zedly.zenchantments.Zenchantment;
import zedly.zenchantments.Hand;
import zedly.zenchantments.Tool;

import static zedly.zenchantments.Tool.ROD;

public class ShortCast extends Zenchantment {

	public static final int ID = 51;

	@Override
	public Builder<ShortCast> defaults() {
		return new Builder<>(ShortCast::new, ID)
			.maxLevel(2)
			.loreName("Short Cast")
			.probability(0)
			.enchantable(new Tool[]{ROD})
			.conflicting(new Class[]{LongCast.class})
			.description("Launches fishing hooks closer in when casting")
			.cooldown(0)
			.power(1.0)
			.handUse(Hand.RIGHT);
	}

	@Override
	public boolean onProjectileLaunch(ProjectileLaunchEvent event, int level, boolean usedHand) {
		if (event.getEntity().getType() == EntityType.FISHING_HOOK) {
			event.getEntity()
			   .setVelocity(event.getEntity().getVelocity().normalize().multiply((.8f / (level * power))));
		}
		return true;
	}
}
