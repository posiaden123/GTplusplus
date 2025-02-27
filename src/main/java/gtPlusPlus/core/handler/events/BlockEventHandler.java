package gtPlusPlus.core.handler.events;

import static gregtech.api.enums.Mods.Thaumcraft;
import static gtPlusPlus.core.lib.CORE.ConfigSwitches.chanceToDropDrainedShard;
import static gtPlusPlus.core.lib.CORE.ConfigSwitches.chanceToDropFluoriteOre;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.item.ModItems;
import gtPlusPlus.core.material.nuclear.FLUORIDES;
import gtPlusPlus.core.util.math.MathUtils;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.core.util.minecraft.PlayerUtils;

public class BlockEventHandler {

    public static ArrayList<ItemStack> oreLimestone;
    public static ArrayList<ItemStack> blockLimestone;
    public static ItemStack fluoriteOre;

    public static void init() {
        // Set Variables for Fluorite Block handling
        Logger.INFO("Setting some Variables for the block break event handler.");
        BlockEventHandler.oreLimestone = OreDictionary.getOres("oreLimestone");
        BlockEventHandler.blockLimestone = OreDictionary.getOres("limestone");
        BlockEventHandler.fluoriteOre = FLUORIDES.FLUORITE.getOre(1);
    }

    @SubscribeEvent
    public void onBlockLeftClicked(final PlayerInteractEvent event) {
        /*
         * if (event.action != PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) return; ItemStack heldItem =
         * event.entityPlayer.getHeldItem(); Block block = event.world.getBlock(event.x, event.y, event.z); // If the
         * block clicked was Stone, the player was holding an Iron Pickaxe and a random integer from 0 (inclusive) to 2
         * (exclusive) is 0 (50% chance) if (block == Blocks.stone && heldItem != null && heldItem.getItem() ==
         * Items.iron_pickaxe && random.nextInt(2) == 0) { ForgeDirection direction =
         * ForgeDirection.getOrientation(event.face); // Convert the numeric face to a ForgeDirection int fireX =
         * event.x + direction.offsetX, fireY = event.y + direction.offsetY, fireZ = event.z + direction.offsetZ; //
         * Offset the block's coordinates according to the direction if (event.world.isAirBlock(fireX, fireY, fireZ)) {
         * // If the block at the new coordinates is Air event.world.setBlock(fireX, fireY, fireZ, Blocks.fire); //
         * Replace it with Fire event.useBlock = Event.Result.DENY; // Prevent the Fire from being extinguished (also
         * prevents Block#onBlockClicked from being called) } }
         */
    }

    @SubscribeEvent
    public void onEntityDrop(final LivingDropsEvent event) {
        /*
         * if (event.entityLiving instanceof EntityPig && event.source instanceof EntityDamageSource) { // getEntity
         * will return the Entity that caused the damage,even for indirect damage sources like arrows/fireballs //
         * (where it will return the Entity that shot the projectile rather than the projectile itself) Entity
         * sourceEntity = event.source.getEntity(); ItemStack heldItem = sourceEntity instanceof EntityLiving ?
         * ((EntityLiving) sourceEntity).getHeldItem() : sourceEntity instanceof EntityPlayer ? ((EntityPlayer)
         * sourceEntity).getHeldItem() : null; if (heldItem != null && heldItem.getItem() == Items.iron_pickaxe) {
         * System.out.println("EntityPig drops event"); event.drops.clear(); event.entityLiving.dropItem(Items.diamond,
         * 64); } }
         */
    }

    @SubscribeEvent
    public void onBlockBreak(final BlockEvent.BreakEvent event) {}

    // Used to handle Thaumcraft Shards when TC is not installed.
    @SubscribeEvent
    public void harvestDrops(final BlockEvent.HarvestDropsEvent event) {
        try {

            if (event != null && event.harvester != null && event.harvester.worldObj != null) {
                if (!event.harvester.worldObj.isRemote) {

                    EntityPlayer p = event.harvester;

                    if (PlayerUtils.isRealPlayer(p)) {
                        // Spawns Fluorite from Lime Stone
                        if (chanceToDropFluoriteOre != 0) {
                            if (!oreLimestone.isEmpty() || !blockLimestone.isEmpty()) {

                                ArrayList<Block> mBlockTypes = new ArrayList<Block>();
                                if (!oreLimestone.isEmpty()) {
                                    for (int i = 0; i < oreLimestone.size(); i++) {
                                        if (ItemUtils.getModId(oreLimestone.get(i)) != null
                                                && !ItemUtils.getModId(oreLimestone.get(i)).toLowerCase()
                                                        .contains("biomesoplenty")) {
                                            if (!mBlockTypes
                                                    .contains(Block.getBlockFromItem(oreLimestone.get(i).getItem()))) {
                                                mBlockTypes.add(Block.getBlockFromItem(oreLimestone.get(i).getItem()));
                                            }
                                        }
                                    }
                                }
                                if (!blockLimestone.isEmpty()) {
                                    for (int i = 0; i < blockLimestone.size(); i++) {
                                        if (ItemUtils.getModId(blockLimestone.get(i)) != null
                                                && !ItemUtils.getModId(blockLimestone.get(i)).toLowerCase()
                                                        .contains("biomesoplenty")) {
                                            if (!mBlockTypes.contains(
                                                    Block.getBlockFromItem(blockLimestone.get(i).getItem()))) {
                                                mBlockTypes
                                                        .add(Block.getBlockFromItem(blockLimestone.get(i).getItem()));
                                            }
                                        }
                                    }
                                }

                                Logger.WARNING("Found Limestone in OreDict.");
                                if (!mBlockTypes.isEmpty()) {
                                    Logger.WARNING("1a | " + event.block.getUnlocalizedName());
                                    for (final Block temp : mBlockTypes) {
                                        Logger.WARNING("2a - " + temp.getUnlocalizedName());
                                        if (event.block == temp) {
                                            Logger.WARNING("3a - found " + temp.getUnlocalizedName());
                                            if (MathUtils.randInt(1, chanceToDropFluoriteOre) == 1) {
                                                Logger.WARNING("4a");
                                                event.drops.clear();
                                                event.drops.add(fluoriteOre.copy());
                                            }
                                        }
                                    }
                                }
                            }

                            if (event.block.getUnlocalizedName().toLowerCase().contains("limestone")) {
                                Logger.WARNING("1c");
                                if (MathUtils.randInt(1, chanceToDropFluoriteOre) == 1) {
                                    Logger.WARNING("2c");
                                    event.drops.clear();
                                    event.drops.add(fluoriteOre.copy());
                                }
                            }

                            if (event.block == Blocks.sandstone) {
                                if (MathUtils.randInt(1, chanceToDropFluoriteOre * 20) == 1) {
                                    event.drops.clear();
                                    event.drops.add(fluoriteOre.copy());
                                }
                            }
                        }
                    }

                    // Do things that can occur for fake players and real players

                    // Spawn Dull Shards (Can spawn from Tree Logs, Grass or Stone. Stone going to
                    // be the most common source.)
                    if (((event.block == Blocks.stone) || (event.block == Blocks.sandstone)
                            || (event.block == Blocks.log)
                            || (event.block == Blocks.log2)
                            || (event.block == Blocks.grass)) && !Thaumcraft.isModLoaded()
                            && (chanceToDropDrainedShard != 0)) {
                        // small chance for one to spawn per stone mined. 1 per 3 stacks~
                        if (MathUtils.randInt(1, chanceToDropDrainedShard) == 1) {
                            // Let's sort out a lucky charm for the player.
                            final int FancyChance = MathUtils.randInt(1, 4);
                            if (MathUtils.randInt(1, 100) < 90) {
                                event.drops.add(new ItemStack(ModItems.shardDull));
                            }
                            // Make a Fire Shard
                            else if (FancyChance == 1) {
                                event.drops.add(new ItemStack(ModItems.shardIgnis));
                            }
                            // Make a Water Shard.
                            else if (FancyChance == 2) {
                                event.drops.add(new ItemStack(ModItems.shardAqua));
                            }
                            // Make an Earth Shard.
                            else if (FancyChance == 3) {
                                event.drops.add(new ItemStack(ModItems.shardTerra));
                            }
                            // Make an Air Shard.
                            else if (FancyChance == 4) {
                                event.drops.add(new ItemStack(ModItems.shardAer));
                            }
                        } else {
                            Logger.WARNING("invalid chance");
                        }
                    }
                }
            }
        } catch (Throwable r) {
            Logger.INFO("Block Event Handler Failed. Please Report this to Alkalus.");
            r.printStackTrace();
        }
    }

    @SubscribeEvent
    public void logsHarvest(final BlockEvent.HarvestDropsEvent event) {
        /*
         * if (event.block instanceof BlockLog) { //
         * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/
         * modification-development/2444501-harvestdropevent-changing-drops-of-vanilla- blocks //
         * Utils.sendServerMessage("Logs! Harvester: %s Drops: %s", event.harvester != null ?
         * event.harvester.getCommandSenderName() : "<none>",
         * event.drops.stream().map(ItemStack.toString()).collect(Collectors. joining(", "))); if (event.harvester !=
         * null) { ItemStack heldItem = event.harvester.getHeldItem(); if (heldItem == null ||
         * heldItem.getItem().getHarvestLevel(heldItem, "axe") < 1) { event.drops.clear();
         * Utils.sendServerMessage("Harvester had wrong tool, clearing drops"); } else {
         * Utils.sendServerMessage("Harvester had correct tool, not clearing drops"); } } else { event.drops.clear();
         * Utils.sendServerMessage("No harvester, clearing drops"); } }
         */
    }
}
