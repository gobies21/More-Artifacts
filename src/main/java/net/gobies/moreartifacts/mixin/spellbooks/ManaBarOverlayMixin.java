package net.gobies.moreartifacts.mixin.spellbooks;

import io.redspace.ironsspellbooks.gui.overlays.ManaBarOverlay;
import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;

import java.util.Objects;

@Mixin(ManaBarOverlay.class)
public class ManaBarOverlayMixin {

    @Final
    @Shadow(remap = false)
    static int TEXT_COLOR;

    @Unique
    private static final ResourceLocation SOUL_MANA = new ResourceLocation(MoreArtifacts.MOD_ID, "textures/gui/spellbooks/mana_icons.png");

    @Unique
    private static final int SOUL_TEXT_COLOR = Objects.requireNonNullElse(ChatFormatting.LIGHT_PURPLE.getColor(), 0xFF55FF);

    @Redirect(
            method = "render",
            at = @At(
                    value = "FIELD",
                    target = "Lio/redspace/ironsspellbooks/gui/overlays/ManaBarOverlay;TEXTURE:Lnet/minecraft/resources/ResourceLocation;",
                    opcode = Opcodes.GETSTATIC
            ),
            remap = false
    )
    private ResourceLocation convertTexture() {
        if (moreArtifacts$hasSoulMana()) {
            return SOUL_MANA;
        }
        return ManaBarOverlay.TEXTURE;
    }

    @Redirect(
            method = "render",
            at = @At(
                    value = "FIELD",
                    target = "Lio/redspace/ironsspellbooks/gui/overlays/ManaBarOverlay;TEXT_COLOR:I",
                    opcode = Opcodes.GETSTATIC
            ),
            remap = false
    )
    private int covertText() {
        if (moreArtifacts$hasSoulMana()) {
            return SOUL_TEXT_COLOR;
        }
        return TEXT_COLOR;
    }

    @Unique
    private boolean moreArtifacts$hasSoulMana() {
        var player = Minecraft.getInstance().player;
        return CommonConfig.IRONS_SPELLBOOKS_MANA_COMPAT.get() && player != null && SoulUtil.hasSoul(player, SoulType.ENVY);
    }
}