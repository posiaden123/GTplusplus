package gtPlusPlus.core.block.machine;

import static gregtech.api.enums.Mods.GTPlusPlus;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gtPlusPlus.GTplusplus;
import gtPlusPlus.core.creative.AddToCreativeTab;
import gtPlusPlus.core.tileentities.general.TileEntityHeliumGenerator;

public class HeliumGenerator extends BlockContainer {

    @SideOnly(Side.CLIENT)
    private IIcon textureTop;

    @SideOnly(Side.CLIENT)
    private IIcon textureBottom;

    @SideOnly(Side.CLIENT)
    private IIcon textureFront;

    public HeliumGenerator() {
        super(Material.wood);
        this.setBlockName("blockHeliumGenerator");
        this.setCreativeTab(AddToCreativeTab.tabMachines);
        GameRegistry.registerBlock(this, "blockHeliumGenerator");
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int ordinalSide, final int meta) {
        return ordinalSide == 1 ? this.textureTop
                : (ordinalSide == 0 ? this.textureBottom
                        : ((ordinalSide != 2) && (ordinalSide != 4) ? this.blockIcon : this.textureFront));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(GTPlusPlus.ID + ":" + "Chrono/" + "CyberPanel");
        this.textureTop = p_149651_1_.registerIcon(GTPlusPlus.ID + ":" + "Chrono/" + "CyberPanel");
        this.textureBottom = p_149651_1_.registerIcon(GTPlusPlus.ID + ":" + "Chrono/" + "CyberPanel");
        this.textureFront = p_149651_1_.registerIcon(GTPlusPlus.ID + ":" + "Chrono/" + "CyberPanel");
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player,
            final int side, final float lx, final float ly, final float lz) {
        if (world.isRemote) {
            return true;
        }

        final TileEntity te = world.getTileEntity(x, y, z);
        if ((te != null) && (te instanceof TileEntityHeliumGenerator)) { // TODO
            player.openGui(GTplusplus.instance, 1, world, x, y, z); // TODO
            return true;
        }
        return false;
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(final World world, final int p_149915_2_) {
        return new TileEntityHeliumGenerator();
    }
}
