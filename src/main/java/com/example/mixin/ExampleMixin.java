package net.fabricmc.example.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class ExampleMixin {

    // إلغاء تأثير الاختفاء للاعبين في شاشتك
    @Inject(method = "isInvisible", at = @At("HEAD"), cancellable = true)
    private void cancelPlayerInvisibility(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player) {
            Player player = (Player) (Object) this;
            if (player.hasEffect(MobEffects.INVISIBILITY)) {
                cir.setReturnValue(false);
            }
        }
    }

    // إجبار اللعبة على رسم هالة مضيئة (Glowing) حول اللاعب المخفي
    @Inject(method = "hasGlowingOutline", at = @At("HEAD"), cancellable = true)
    private void forceGlowingOnInvisiblePlayers(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player) {
            Player player = (Player) (Object) this;
            if (player.hasEffect(MobEffects.INVISIBILITY)) {
                cir.setReturnValue(true);
            }
        }
    }
}
