package luckyowlstudios.mods.luckysshowcase.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import luckyowlstudios.mods.luckysshowcase.LuckysShowcase;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(CallbackInfo info) {
        LuckysShowcase.logger.info("Hello from " + LuckysShowcase.MOD_ID);
    }
}
