package io.fishermen.fpsdisplay.settings;

import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.event.Connection;

public class FastBreak extends GuiSettings {
    private static boolean bzs = false;
    private static float bzx = 0.0F;
    public static BlockPos blockPos;
    public static EnumFacing facing;
    public static CommandSettings speed = new CommandSettings("Speed", 1.4f, 1.0f, 2.0f, 0.1f);
    public static CommandSettings dmg = new CommandSettings("Damage", 0.8f, 0.1f, 1.0f, 0.1f);

    public FastBreak(){
        super("FastBreak","",c4.Player,0,-1);
        this.avav(speed);
        this.avav(dmg);
    }

    public static void onPacket(Object object, Connection.Side connection_side) {
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if (object instanceof C07PacketPlayerDigging && !mc.playerController.extendedReach() && mc.playerController != null) {
            C07PacketPlayerDigging c07PacketPlayerDigging = (C07PacketPlayerDigging) object;
            if (c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
                bzs = true;
                blockPos = c07PacketPlayerDigging.getPosition();
                facing = c07PacketPlayerDigging.getFacing();
                bzx = 0;
            } else if (c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK || c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                bzs = false;
                blockPos = null;
                facing = null;
            }
        }
    }
    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if (getCurBlockDamageMP() >= (float) speed.g3tV4l4u3()) {
            setCurBlockDamageMP(1);
            boolean item = mc.thePlayer.getCurrentEquippedItem() == null;
            mc.thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 20, item ? 1 : 0));
        }
        if (mc.playerController.extendedReach()) {
            setBlockHitDelay(0);
        } else {
            if (bzs) {
                Block block = mc.theWorld.getBlockState(blockPos).getBlock();
                bzx = (float) bzx + (float) (block.getPlayerRelativeBlockHardness(mc.thePlayer, mc.theWorld, blockPos) * (float) speed.g3tV4l4u3());
                if (bzx >= 1.0F) {
                    mc.theWorld.setBlockState(blockPos, Blocks.air.getDefaultState(), 11);
                    mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, facing));
                    bzx = 0.0F;
                    bzs = false;
                }
            }
        }
    }
    public float getCurBlockDamageMP(){
        return ObfuscationReflectionHelper.getPrivateValue(PlayerControllerMP.class,mc.playerController,"field_78770_f");
    }
    public void setCurBlockDamageMP(float va){
        ObfuscationReflectionHelper.setPrivateValue(PlayerControllerMP.class,mc.playerController,va,"field_78770_f");
    }
    public void setBlockHitDelay(int va){
        ObfuscationReflectionHelper.setPrivateValue(PlayerControllerMP.class,mc.playerController,va,"field_78781_i");
    }
}
