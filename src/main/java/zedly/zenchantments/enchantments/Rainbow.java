package zedly.zenchantments.enchantments;

import org.bukkit.Material;
import org.bukkit.entity.Sheep;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;
import zedly.zenchantments.Zenchantment;
import zedly.zenchantments.Storage;
import zedly.zenchantments.Utilities;
import zedly.zenchantments.Hand;
import zedly.zenchantments.Tool;

import static org.bukkit.Material.*;
import static org.bukkit.block.BlockFace.DOWN;
import static zedly.zenchantments.Tool.SHEAR;

public class Rainbow extends Zenchantment {

	public static final int ID = 47;

	@Override
	public Builder<Rainbow> defaults() {
		return new Builder<>(Rainbow::new, ID)
			.maxLevel(1)
			.loreName("Rainbow")
			.probability(0)
			.enchantable(new Tool[]{SHEAR})
			.conflicting(new Class[]{})
			.description("Drops random flowers and wool colors when used")
			.cooldown(0)
			.power(-1.0)
			.handUse(Hand.BOTH);
	}

	@Override
	public boolean onBlockBreak(BlockBreakEvent event, int level, boolean usedHand) {
		Material dropMaterial;
		if (Storage.COMPATIBILITY_ADAPTER.SmallFlowers().contains(event.getBlock().getType())) {
			dropMaterial = Storage.COMPATIBILITY_ADAPTER.SmallFlowers().getRandom();
		} else if (Storage.COMPATIBILITY_ADAPTER.LargeFlowers().contains(event.getBlock().getType())) {
			dropMaterial = Storage.COMPATIBILITY_ADAPTER.LargeFlowers().getRandom();
		} else {
			return false;
		}
		event.setCancelled(true);
		if (Storage.COMPATIBILITY_ADAPTER.LargeFlowers().contains(event.getBlock().getRelative(DOWN).getType())) {
			event.getBlock().getRelative(DOWN).setType(AIR);
		}
		event.getBlock().setType(AIR);
		Utilities.damageTool(event.getPlayer(), 1, usedHand);
		event.getPlayer().getWorld().dropItem(Utilities.getCenter(event.getBlock()), new ItemStack(dropMaterial, 1));
		return true;
	}

	@Override
	public boolean onShear(PlayerShearEntityEvent event, int level, boolean usedHand) {
		Sheep sheep = (Sheep) event.getEntity();
		if (!sheep.isSheared()) {
			int count = Storage.rnd.nextInt(3) + 1;
			Utilities.damageTool(event.getPlayer(), 1, usedHand);
			event.setCancelled(true);
			sheep.setSheared(true);
			event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(),
				new ItemStack(Storage.COMPATIBILITY_ADAPTER.Wools().getRandom(), count));
		}
		return true;
	}
}
