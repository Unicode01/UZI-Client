package io.fishermen.fpsdisplay.settings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.timechanger.ClickListener;

import java.awt.*;
import java.util.List;

public class Hitbox extends GuiSettings{
    public static CommandSettings Mor;
    public static ClickListener Show;
    private static MovingObjectPosition mv;
    public Hitbox(){
        super("HitBox","",c4.combat2,0,-1);
        Mor = new CommandSettings("Hitbox", 0.0D, 0.0D, 20D, 0.1D);
        Show = new ClickListener("Show new hitbox", false);
        this.avav(Mor);
        this.avav(Show);
    }
    @SubscribeEvent
    public void update(TickEvent.PlayerTickEvent E) {
        if(!this.getStat()){
            return;
        }
        gmo(1.0F);
    }

    @SubscribeEvent
    public void m(MouseEvent e) {
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()) return;
        if (e.button == 0 && e.buttonstate && mv != null) {
            mc.objectMouseOver = mv;
        }
    }

    @SubscribeEvent
    public void ef(TickEvent.RenderTickEvent ev) {
        if(!this.getStat()){
            return;
        }
        // autoclick event
        if(!GuiSettings.isPlayerInGame()) return;

        if (Mouse.isButtonDown(0)){
            if (mv != null) {
                mc.objectMouseOver = mv;
            }
        }
    }

    @SubscribeEvent
    public void r1(RenderWorldLastEvent e) {
        if(!this.getStat()){
            return;
        }
        if (Show.i() && GuiSettings.isPlayerInGame()) {
            for (Entity en : mc.theWorld.loadedEntityList) {
                if (en != mc.thePlayer && en instanceof EntityLivingBase && ((EntityLivingBase) en).deathTime == 0 && !(en instanceof EntityArmorStand) && !en.isInvisible()) {
                    this.rh(en, Color.WHITE);
                }
            }

        }
    }

    public static double exp(Entity en) {
        return Mor.g3tV4l4u3();
    }

    public static void gmo(float partialTicks) {
        if (mc.getRenderViewEntity() != null && mc.theWorld != null) {
            mc.pointedEntity = null;
            Entity pE = null;
            double d0 = 3.0D;
            mv = mc.getRenderViewEntity().rayTrace(d0, partialTicks);
            double d2 = d0;
            Vec3 vec3 = mc.getRenderViewEntity().getPositionEyes(partialTicks);
            if (mv != null) {
                d2 = mv.hitVec.distanceTo(vec3);
            }

            Vec3 vec4 = mc.getRenderViewEntity().getLook(partialTicks);
            Vec3 vec5 = vec3.addVector(vec4.xCoord * d0, vec4.yCoord * d0, vec4.zCoord * d0);
            Vec3 vec6 = null;
            float f1 = 1.0F;
            List list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.getRenderViewEntity(), mc.getRenderViewEntity().getEntityBoundingBox().addCoord(vec4.xCoord * d0, vec4.yCoord * d0, vec4.zCoord * d0).expand(f1, f1, f1));
            double d3 = d2;

            for (Object o : list) {
                Entity entity = (Entity) o;
                if (entity.canBeCollidedWith()) {
                    float ex = (float) ((double) entity.getCollisionBorderSize() * exp(entity));
                    AxisAlignedBB ax = entity.getEntityBoundingBox().expand(ex, ex, ex);
                    MovingObjectPosition mop = ax.calculateIntercept(vec3, vec5);
                    if (ax.isVecInside(vec3)) {
                        if (0.0D < d3 || d3 == 0.0D) {
                            pE = entity;
                            vec6 = mop == null ? vec3 : mop.hitVec;
                            d3 = 0.0D;
                        }
                    } else if (mop != null) {
                        double d4 = vec3.distanceTo(mop.hitVec);
                        if (d4 < d3 || d3 == 0.0D) {
                            if (entity == mc.getRenderViewEntity().ridingEntity && !entity.canRiderInteract()) {
                                if (d3 == 0.0D) {
                                    pE = entity;
                                    vec6 = mop.hitVec;
                                }
                            } else {
                                pE = entity;
                                vec6 = mop.hitVec;
                                d3 = d4;
                            }
                        }
                    }
                }
            }

            if (pE != null && (d3 < d2 || mv == null)) {
                mv = new MovingObjectPosition(pE, vec6);
                if (pE instanceof EntityLivingBase || pE instanceof EntityItemFrame) {
                    mc.pointedEntity = pE;
                }
            }
        }

    }

    private void rh(Entity e, Color c) {
        if (e instanceof EntityLivingBase) {
            double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double) getTimer().renderPartialTicks - mc.getRenderManager().viewerPosX;
            double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double) getTimer().renderPartialTicks - mc.getRenderManager().viewerPosY;
            double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double) getTimer().renderPartialTicks - mc.getRenderManager().viewerPosZ;
            float ex = (float)((double)e.getCollisionBorderSize() * Mor.g3tV4l4u3());
            AxisAlignedBB bbox = e.getEntityBoundingBox().expand(ex, ex, ex);
            AxisAlignedBB axis = new AxisAlignedBB(bbox.minX - e.posX + x, bbox.minY - e.posY + y, bbox.minZ - e.posZ + z, bbox.maxX - e.posX + x, bbox.maxY - e.posY + y, bbox.maxZ - e.posZ + z);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glLineWidth(2.0F);
            GL11.glColor3d(c.getRed(), c.getGreen(), c.getBlue());
            RenderGlobal.drawSelectionBoundingBox(axis);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
        }
    }
    public static net.minecraft.util.Timer getTimer() {
        return ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "timer", "field_71428_T");
    }
}
