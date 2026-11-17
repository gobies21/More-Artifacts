package net.gobies.moreartifacts.client.renderer.effect;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class StormShockRenderer {

    private static final List<StormShockRenderer> EFFECTS = new ArrayList<>();
    private final int sourceId;
    private final int targetId;
    private final long startTime;

    private StormShockRenderer(Entity source, Entity target) {
        this.sourceId = source.getId();
        this.targetId = target.getId();
        this.startTime = Objects.requireNonNull(Minecraft.getInstance().level).getGameTime();
    }

    public static void spawn(Entity source, Entity target) {
        if (Minecraft.getInstance().level == null) return;
        EFFECTS.add(new StormShockRenderer(source, target));
    }

    @SubscribeEvent
    public static void render(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        long time = mc.level.getGameTime();

        EFFECTS.removeIf(effect -> time - effect.startTime > 8);

        if (EFFECTS.isEmpty()) return;

        PoseStack poseStack = event.getPoseStack();
        Vec3 camera = mc.gameRenderer.getMainCamera().getPosition();

        poseStack.pushPose();
        poseStack.translate(-camera.x, -camera.y, -camera.z);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        RenderSystem.disableCull();
        RenderSystem.depthMask(false);

        for (StormShockRenderer effect : List.copyOf(EFFECTS)) {
            Entity source = mc.level.getEntity(effect.sourceId);
            Entity target = mc.level.getEntity(effect.targetId);

            if (source == null || target == null) continue;

            Vec3 start = source.getPosition(event.getPartialTick()).add(0, source.getBbHeight() * 0.5D, 0);
            Vec3 end = target.getPosition(event.getPartialTick()).add(0, target.getBbHeight() * 0.5D, 0);
            float age = time - effect.startTime;
            float alpha = age > 3 ? 1.0F - (age - 3) / 5.0F : 1.0F;
            renderBolt(poseStack, start, end, alpha);
        }

        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        poseStack.popPose();
    }

    private static void renderBolt(PoseStack poseStack, Vec3 start, Vec3 end, float alpha) {
        RandomSource random = RandomSource.create();
        Vec3 previous = start;

        int segments = Math.max(8, (int) (start.distanceTo(end) * 4));
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        for (int i = 1; i <= segments; i++) {
            Vec3 next = start.lerp(end, i / (double) segments);

            if (i != segments) {
                next = next.add(
                        (random.nextDouble() - 0.5D) * 0.45D,
                        (random.nextDouble() - 0.5D) * 0.45D,
                        (random.nextDouble() - 0.5D) * 0.45D
                );
            }

            renderSegment(buffer, poseStack, previous, next, 0.055F, 0.015F, 0.525F, alpha);
            renderSegment(buffer, poseStack, previous, next, 0.025F, 0.8F, 0.95F, alpha);

            previous = next;
        }

        BufferUploader.drawWithShader(buffer.end());
    }

    private static void renderSegment(BufferBuilder buffer, PoseStack poseStack, Vec3 start, Vec3 end, float width, float red, float green, float alpha) {
        Vec3 direction = end.subtract(start).normalize();
        Vector3f look = Minecraft.getInstance().gameRenderer.getMainCamera().getLookVector();
        Vec3 camera = new Vec3(look.x, look.y, look.z);

        Vec3 side = direction.cross(camera).normalize();

        if (side.lengthSqr() < 0.0001D) {
            side = direction.cross(new Vec3(0, 1, 0)).normalize();
        }

        side = side.scale(width);

        Matrix4f matrix = poseStack.last().pose();
        buffer.vertex(matrix, (float) (start.x + side.x), (float) (start.y + side.y), (float) (start.z + side.z)).color(red, green, (float) 1.0, alpha).endVertex();
        buffer.vertex(matrix, (float) (start.x - side.x), (float) (start.y - side.y), (float) (start.z - side.z)).color(red, green, (float) 1.0, alpha).endVertex();
        buffer.vertex(matrix, (float) (end.x - side.x), (float) (end.y - side.y), (float) (end.z - side.z)).color(red, green, (float) 1.0, alpha).endVertex();
        buffer.vertex(matrix, (float) (end.x + side.x), (float) (end.y + side.y), (float) (end.z + side.z)).color(red, green, (float) 1.0, alpha).endVertex();
    }
}