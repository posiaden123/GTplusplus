package gtPlusPlus.core.handler.events;

import net.minecraft.entity.player.EntityPlayer;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerTickHandler {

    @SubscribeEvent
    public void onPlayerTick(net.minecraftforge.event.entity.player.PlayerOpenContainerEvent e) {
        if (e.entity instanceof EntityPlayer) {
            if (e.entityPlayer.openContainer != null) {}
        }
    }
}
