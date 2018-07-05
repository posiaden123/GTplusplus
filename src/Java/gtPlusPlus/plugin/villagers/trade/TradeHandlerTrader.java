package gtPlusPlus.plugin.villagers.trade;

import java.util.Collections;
import java.util.Random;

import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.material.ELEMENT;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class TradeHandlerTrader extends TradeHandlerBase {

	public TradeHandlerTrader() {
		Logger.INFO("Created Trade Manager for 'Trader' villager profession type.");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random) {
		
		Logger.INFO("Trying to manipulate trade for villager.");
		
		//if (villager.getProfession() == 7736) {
			recipeList.add(new MerchantRecipe(ItemUtils.getItemStackOfAmountFromOreDict("logWood", 32), ELEMENT.getInstance().IRON.getOre(1)));
			recipeList.add(new MerchantRecipe(ItemUtils.getItemStackOfAmountFromOreDict("dustRawMeat", 32), ELEMENT.getInstance().COPPER.getOre(1)));
			recipeList.add(new MerchantRecipe(ItemUtils.getSimpleStack(Blocks.obsidian, 6), ELEMENT.getInstance().TIN.getOre(1)));
			recipeList.add(new MerchantRecipe(ItemUtils.getSimpleStack(Blocks.glowstone, 32), ELEMENT.getInstance().SILICON.getOre(1)));
			recipeList.add(new MerchantRecipe(ItemUtils.getSimpleStack(Blocks.piston, 32), ELEMENT.getInstance().ALUMINIUM.getOre(1)));
			Collections.shuffle(recipeList);	
		//}
	}

}