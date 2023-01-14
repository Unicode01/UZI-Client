package io.fishermen.fpsdisplay.settings;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.CommandSettings.CommandSettings;

import java.lang.reflect.Method;

public class AutoMLG extends GuiSettings {
    public static CommandSettings Delay;
    public AutoMLG(){
        super("AutoMLG","",c4.Player,0,-1);
        Delay = new CommandSettings("Delay",100.0, 1.0, 1000.0, 50.0);
        this.avav(Delay);
    }
    @SubscribeEvent
    public void onPre(TickEvent.PlayerTickEvent e) {
        if (mc.thePlayer.fallDistance > 4.0F && this.getSlotWaterBucket() != -1 && this.isMLGNeeded()) {
            mc.thePlayer.rotationPitch = 90.0F;
            this.swapToWaterBucket(this.getSlotWaterBucket());
        }

        if (mc.thePlayer.fallDistance > 4.0F && this.isMLGNeeded() && !mc.thePlayer.isOnLadder() && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - getDistanceToFall() - 1.0, mc.thePlayer.posZ);
            this.placeWater(pos, EnumFacing.UP);
            if (mc.thePlayer.getHeldItem().getItem() == Items.bucket) {
                Thread thr = new Thread(() -> {
                    try {
                        Thread.sleep((long) Delay.g3tV4l4u3());
                    } catch (Exception var2) {
                    }

                    rightClickMouse();
                });
                thr.start();
            }

            mc.thePlayer.fallDistance = 0.0F;
        }

    }
    public static void rightClickMouse() {
        try {
            String s = "func_147121_ag" ;
            Class c = mc.getClass();
            Method m = c.getDeclaredMethod(s);
            m.setAccessible(true);
            m.invoke(mc);
        } catch (Exception var4) {
        }

    }
    private void swapToWaterBucket(int blockSlot) {
        mc.thePlayer.inventory.currentItem = blockSlot;
        mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C09PacketHeldItemChange(blockSlot));
    }
    public static double getDistanceToFall() {
        double distance = 0.0;

        for(double i = mc.thePlayer.posY; i > 0.0; --i) {
            Block block = getBlock(new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ));
            if (block.getMaterial() != Material.air && block.isBlockNormalCube() && block.isCollidable()) {
                distance = i;
                break;
            }

            if (i < 0.0) {
                break;
            }
        }

        return mc.thePlayer.posY - distance - 1.0;
    }
    private int getSlotWaterBucket() {
        for(int i = 0; i < 8; ++i) {
            if (mc.thePlayer.inventory.mainInventory[i] != null && mc.thePlayer.inventory.mainInventory[i].getItem().getUnlocalizedName().contains("bucketWater")) {
                return i;
            }
        }

        return -1;
    }

    private void placeWater(BlockPos pos, EnumFacing facing) {
        ItemStack heldItem = mc.thePlayer.inventory.getCurrentItem();
        mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), pos, facing, new Vec3((double)pos.getX() + 0.5, (double)pos.getY() + 1.0, (double)pos.getZ() + 0.5));
        if (heldItem != null) {
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, heldItem);
            mc.entityRenderer.itemRenderer.resetEquippedProgress2();
        }

    }

    private boolean isMLGNeeded() {
        if (mc.playerController.getCurrentGameType() != WorldSettings.GameType.CREATIVE && mc.playerController.getCurrentGameType() != WorldSettings.GameType.SPECTATOR && !mc.thePlayer.capabilities.isFlying && !mc.thePlayer.capabilities.allowFlying) {
            double y = mc.thePlayer.posY;

            while(true) {
                if (y > 0.0) {
                    Block block = getBlock(new BlockPos(mc.thePlayer.posX, y, mc.thePlayer.posZ));
                    if (block.getMaterial() == Material.water) {
                        return false;
                    }

                    if (block.getMaterial() != Material.air) {
                        return true;
                    }

                    if (!(y < 0.0)) {
                        --y;
                        continue;
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }
    public static Block getBlock(BlockPos block) {
        return mc.theWorld.getBlockState(block).getBlock();
    }
}
