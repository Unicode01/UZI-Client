package io.fishermen.fpsdisplay.settings;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.CommandSettings.CommandSettings;


public class Step extends GuiSettings
{
    public static CommandSettings Blocks;
    public static CommandSettings Mode;
    public double stepHightA;
    public Step() {
        super(GuiSettings.a(new char[] { 'S', 't', 'e', 'p' }), "", c4.movement, 0, -1);
        this.avav(Step.Mode= new CommandSettings(GuiSettings.a(new char[]{'M','o','d','e'}),2,1,2,1));
        this.avav(Step.Blocks= new CommandSettings(GuiSettings.a(new char[] { 'B', 'l', 'o', 'c', 'k' }), 0.5, 0.3, 2, 0.1));
    }
    @SubscribeEvent
    public void a(final TickEvent.PlayerTickEvent a) {
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if(Mode.g3tV4l4u3()==1){
        mc.thePlayer.stepHeight = (float) Step.Blocks.g3tV4l4u3();
        }
        else if(Mode.g3tV4l4u3()==2){
            mc.thePlayer.stepHeight= (float) stepHightA;
            if(mc.thePlayer.onGround && CouldStep() && mc.thePlayer.isCollidedHorizontally){
                mc.thePlayer.motionY=0.3681299D;
            }
        }
    }
    @Override
    public void dd(){
        mc.thePlayer.stepHeight= (float) stepHightA;
    }
    @Override
    public void en(){
        this.stepHightA= mc.thePlayer.stepHeight;
    }
    public boolean CouldStep(){
        double yaw = getDirection();
        double x = -Math.sin(yaw) * 0.32;
        double z = Math.cos(yaw) * 0.32;
        return mc.theWorld.getCollisionBoxes(mc.thePlayer.getEntityBoundingBox().offset(x, 1.001335979112147, z)).isEmpty();
    }
    public double getDirection(){
        float rotationYaw = mc.thePlayer.rotationYaw;
        if (mc.thePlayer.moveForward < 0f) {rotationYaw += 180f;}
        float forward = 1f;
        if (mc.thePlayer.moveForward < 0f) {
            forward = -0.5f;
        } else if (mc.thePlayer.moveForward > 0f) {
            forward = 0.5f;
        }
        if (mc.thePlayer.moveStrafing > 0f) {
            rotationYaw -= 90f * forward;
        }
        if (mc.thePlayer.moveStrafing < 0f) {
            rotationYaw += 90f * forward;
        }
        return Math.toRadians((double) rotationYaw);
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
