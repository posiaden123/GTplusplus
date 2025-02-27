package gregtech.api.util;

import net.minecraftforge.fluids.FluidStack;

import gtPlusPlus.core.util.math.MathUtils;

public class ThermalFuel {

    public static void addThermalFuel(FluidStack aInput1, FluidStack aInput2, FluidStack aOutput1, int euValue,
            int aSpecialValue) {
        GTPP_Recipe x = new GTPP_Recipe(
                true,
                null,
                null,
                null,
                null,
                new FluidStack[] { aInput1, aInput2 },
                new FluidStack[] { aOutput1 },
                20, // 1 Tick
                euValue, // No Eu produced
                aSpecialValue // Magic Number
        );
        GTPP_Recipe.GTPP_Recipe_Map.sGeoThermalFuels.addRecipe(x);
    }

    public static void addSteamTurbineFuel(FluidStack aInput1) {
        GTPP_Recipe x = new GTPP_Recipe(
                true,
                null,
                null,
                null,
                null,
                new FluidStack[] { aInput1 },
                null,
                20, // 1 Tick
                MathUtils.findPercentageOfInt((aInput1.amount / 2), 95), // No Eu produced
                0 // Magic Number
        );
        GTPP_Recipe.GTPP_Recipe_Map.sSteamTurbineFuels.addRecipe(x);
    }
}
