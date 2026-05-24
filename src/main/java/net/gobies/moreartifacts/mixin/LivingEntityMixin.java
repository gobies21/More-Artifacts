package net.gobies.moreartifacts.mixin;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Shadow
    protected void hurtArmor(DamageSource damageSource, float damageAmount) {}

    @Inject(
            method = "getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F",
            at = @At("HEAD"),
            cancellable = true
    )
    private void setArmorPierce(DamageSource damageSource, float damageAmount, CallbackInfoReturnable<Float> cir) {
        LivingEntity target = (LivingEntity) (Object) this;
        Entity attacker = damageSource.getEntity();
        if (!(attacker instanceof Player player)) return;
        if (CurioHandler.isCurioEquipped(player, MAItems.GuardianThornNecklace.get())) {
            this.hurtArmor(damageSource, damageAmount);
            float armor = (float) target.getArmorValue();
            float toughness = (float) target.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
            int armorPierce = CommonConfig.GUARDIAN_THORN_NECKLACE_ARMOR_PIERCE.get();

            damageAmount = CombatRules.getDamageAfterAbsorb(damageAmount, Math.max(armor - armorPierce, 0f), toughness);

            cir.setReturnValue(damageAmount);
        }
    }
}