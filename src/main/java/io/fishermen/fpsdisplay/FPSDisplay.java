// Decompiled by ImCzf233

package io.fishermen.fpsdisplay;

import net.minecraft.util.AxisAlignedBB;
import java.util.List;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.MouseEvent;
import java.util.Random;
import pw.cinque.CommandSettings.CommandSettings;
import io.fishermen.fpsdisplay.settings.GuiSettings;

public class FPSDisplay extends GuiSettings
{
    public static boolean Start;
    public static String a;
    public static String ab;
    public static CommandSettings min;
    public static CommandSettings max;
    public Random r;
    
    public FPSDisplay() {
        super(GuiSettings.a(new char[] { 'R', 'e', 'a', 'c', 'h' }), "", c4.combat1, 0, -1);
        this.r = new Random();
        FPSDisplay.min = new CommandSettings(GuiSettings.a(new char[] { 'M', 'i', 'n', ' ', 'R', 'e', 'a', 'c', 'h' }), 3.8, 3.0, 6.0, 0.01);
        FPSDisplay.max = new CommandSettings(GuiSettings.a(new char[] { 'M', 'a', 'x', ' ', 'R', 'e', 'a', 'c', 'h' }), 4.1, 3.0, 6.0, 0.01);
        this.avav(FPSDisplay.min);
        this.avav(FPSDisplay.max);
    }

    @Override
    public void en(){
        Start = true;
    }
    @Override
    public void dd(){
        Start = false;
    }

    @SubscribeEvent
    public void a(final MouseEvent a) {
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        final double d2 = FPSDisplay.min.g3tV4l4u3() + this.r.nextDouble() * (FPSDisplay.max.g3tV4l4u3() - FPSDisplay.min.g3tV4l4u3());
        final Object[] objects = a(d2, 0.0, 0.0f);
        if (objects == null) {
            return;
        }
        FPSDisplay.mc.objectMouseOver = new MovingObjectPosition((Entity)objects[0], (Vec3)objects[1]);
        FPSDisplay.mc.pointedEntity = (Entity)objects[0];
    }
    
    public static Object[] a(final double d, final double expand, final float partialTicks) {
        final Entity var2 = FPSDisplay.mc.getRenderViewEntity();
        Entity entity = null;
        if (var2 == null || FPSDisplay.mc.theWorld == null) {
            return null;
        }
        FPSDisplay.mc.mcProfiler.startSection("pick");
        final Vec3 var3 = var2.getPositionEyes(0.0f);
        final Vec3 var4 = var2.getLook(0.0f);
        final Vec3 var5 = var3.addVector(var4.xCoord * d, var4.yCoord * d, var4.zCoord * d);
        Vec3 var6 = null;
        final float var7 = 1.0f;
        final List var8 = FPSDisplay.mc.theWorld.getEntitiesWithinAABBExcludingEntity(var2, var2.getEntityBoundingBox().addCoord(var4.xCoord * d, var4.yCoord * d, var4.zCoord * d).expand(1.0, 1.0, 1.0));
        double var9 = d;
        for (int var10 = 0; var10 < var8.size(); ++var10) {
            final Entity var11 = (Entity) var8.get(var10);
            if (var11.canBeCollidedWith()) {
                final float var12 = var11.getCollisionBorderSize();
                AxisAlignedBB var13 = var11.getEntityBoundingBox().expand((double)var12, (double)var12, (double)var12);
                var13 = var13.expand(expand, expand, expand);
                final MovingObjectPosition var14 = var13.calculateIntercept(var3, var5);
                if (var13.isVecInside(var3)) {
                    if (0.0 < var9 || var9 == 0.0) {
                        entity = var11;
                        var6 = ((var14 == null) ? var3 : var14.hitVec);
                        var9 = 0.0;
                    }
                }
                else if (var14 != null) {
                    final double var15 = var3.distanceTo(var14.hitVec);
                    if (var15 < var9 || var9 == 0.0) {
                        final boolean canRiderInteract = false;
                        if (var11 == var2.ridingEntity) {
                            if (var9 == 0.0) {
                                entity = var11;
                                var6 = var14.hitVec;
                            }
                        }
                        else {
                            entity = var11;
                            var6 = var14.hitVec;
                            var9 = var15;
                        }
                    }
                }
            }
        }
        if (var9 < d && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
            entity = null;
        }
        FPSDisplay.mc.mcProfiler.endSection();
        if (entity == null || var6 == null) {
            return null;
        }
        return new Object[] { entity, var6 };
    }
    
    static {
        FPSDisplay.a = new String(GuiSettings.a(new char[] { 'P', 'y', '3', 's', 's', ' ', '4', ' ', 'k', '3', 'y', '.', '.', '.' }));
        FPSDisplay.ab = new String(GuiSettings.a(new char[] { 'K', '3', 'y', ':' }));
    }
}
