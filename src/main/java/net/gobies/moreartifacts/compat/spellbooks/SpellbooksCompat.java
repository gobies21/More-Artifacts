package net.gobies.moreartifacts.compat.spellbooks;

import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class SpellbooksCompat {

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new SpellbooksCompat());
    }

    public static float getMana(Player player) {
        return MagicData.getPlayerMagicData(player).getMana();
    }

    public static void setMana(Player player, float amount) {
        MagicData.getPlayerMagicData(player).setMana(amount);
    }

    public static float getMaxMana(Player player) {
        var maxMana = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "max_mana"));

        if (maxMana != null && player.getAttribute(maxMana) != null) {
            return (float) player.getAttributeValue(maxMana);
        }
        return 0;
    }

    public static void syncPlayerMana(int manaAmount) {
        ClientMagicData.setMana(manaAmount);
    }

    @SubscribeEvent
    public void onSpellCast(SpellOnCastEvent event) {
        if (SoulUtil.hasSoul(event.getEntity(), SoulType.ENVY) && CommonConfig.IRONS_SPELLBOOKS_MANA_COMPAT.get()) {
            float costReduction = CommonConfig.ENVY_SPELLBOOKS_SPELL_COST_REDUCTION.get().floatValue();
            event.setManaCost((int) (event.getManaCost() * (1.0 - costReduction)));
        }
    }

    @SubscribeEvent
    public void onSpellDamage(SpellDamageEvent event) {
        if (event.getSpellDamageSource().getEntity() instanceof Player player) {
            if (!ModLoadedUtil.isApothecaryLoaded()) {
                if (CurioHandler.isCurioEquipped(player, MAItems.EnvySoul.get())) {
                    ItemStack stack = CurioHandler.getEquippedCurio(player, MAItems.EnvySoul.get());
                    int stage = SoulUtil.getSoulStage(stack);
                    if (stage >= 1 && stage <= 4) {
                        event.setAmount(event.getAmount() * (1.0f + (stage * CommonConfig.ENVY_MAGIC_DAMAGE.get().floatValue())));
                    }
                } else if (SoulUtil.hasSoul(player, SoulType.ENVY)) {
                    event.setAmount(event.getAmount() * (1.0f + (5 * CommonConfig.ENVY_MAGIC_DAMAGE.get().floatValue())));
                }
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.EnvyRing.get())) {
                SoulUtil.applySinful(player, 210, 2);
            }
        }
    }
}