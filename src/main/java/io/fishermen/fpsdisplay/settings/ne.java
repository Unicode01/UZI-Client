// Decompiled by ImCzf233

package io.fishermen.fpsdisplay.settings;

import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ne extends GuiSettings
{
    public ne() {
        super(GuiSettings.a(new char[] { 'S', 'p', 'e', 'e', 'd' }), "", c4.movement, 0, -1);
    }
    
    @SubscribeEvent
    public void a(final TickEvent.PlayerTickEvent a) {
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if (ne.mc.thePlayer.moveForward == 0.0f && ne.mc.thePlayer.moveStrafing == 0.0f) {
            ne.mc.thePlayer.motionX = 0.0;
            ne.mc.thePlayer.motionZ = 0.0;
        }
        if (ne.mc.thePlayer.onGround) {
            this.s(1.45);
            ne.mc.thePlayer.motionY = 0.455;
        }
        else {
            final double n = ne.mc.thePlayer.motionX * ne.mc.thePlayer.motionX;
            this.s(Math.sqrt(n + ne.mc.thePlayer.motionZ * ne.mc.thePlayer.motionZ));
        }
    }
    
    public float gg() {
        float y = ne.mc.thePlayer.rotationYaw;
        final float f = ne.mc.thePlayer.moveForward;
        final float s = ne.mc.thePlayer.moveStrafing;
        y += ((f < 0.0f) ? 180 : 0);
        if (s < 0.0f) {
            y += ((f == 0.0f) ? 90.0f : ((f < 0.0f) ? -45.0f : 45.0f));
        }
        if (s > 0.0f) {
            y -= ((f == 0.0f) ? 90.0f : ((f < 0.0f) ? -45.0f : 45.0f));
        }
        return y * 0.017453292f;
    }
    
    public void s(final double s) {
        ne.mc.thePlayer.motionX = -MathHelper.sin(this.gg()) * s;
        ne.mc.thePlayer.motionZ = MathHelper.cos(this.gg()) * s;
    }
}
