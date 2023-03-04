package io.fishermen.fpsdisplay.settings;

import io.fishermen.fpsdisplay.RotationUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.event.Connection;
import pw.cinque.timechanger.ClickListener;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Scaffold2 extends GuiSettings{
    private final List<Block> invalidBlocks = Arrays.asList(Blocks.enchanting_table, Blocks.furnace, Blocks.carpet, Blocks.crafting_table, Blocks.trapped_chest, (Block)Blocks.chest, Blocks.dispenser, Blocks.air, (Block)Blocks.water, (Block)Blocks.lava,
            (Block)Blocks.flowing_water, (Block)Blocks.flowing_lava, (Block)Blocks.sand, Blocks.snow_layer, Blocks.torch, Blocks.anvil, Blocks.jukebox, Blocks.stone_button, Blocks.wooden_button, Blocks.lever,
            Blocks.noteblock, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.wooden_pressure_plate, Blocks.heavy_weighted_pressure_plate, (Block)Blocks.stone_slab, (Block)Blocks.wooden_slab, (Block)Blocks.stone_slab2, (Block)Blocks.red_mushroom, (Block)Blocks.brown_mushroom,
            (Block)Blocks.yellow_flower, (Block)Blocks.red_flower, Blocks.anvil, Blocks.glass_pane, (Block)Blocks.stained_glass_pane, Blocks.iron_bars, (Block)Blocks.cactus, Blocks.ladder, Blocks.web);

    private final List<Block> validBlocks = Arrays.asList(Blocks.air, (Block)Blocks.water, (Block)Blocks.flowing_water, (Block)Blocks.lava, (Block)Blocks.flowing_lava);

    private final BlockPos[] blockPositions = new BlockPos[] { new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1) };

    private final EnumFacing[] facings = new EnumFacing[] { EnumFacing.EAST, EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.NORTH };

    private final Random rng = new Random();

    private float[] angles = new float[2];

    private boolean rotating;
    private static boolean ena;

    private int slot;
    private static boolean FirstChange;
    private static boolean StartChange;
    private static boolean NeedFix;
    public Scaffold2() {
        super(GuiSettings.a(new char[]{'S', 'c', 'a', 'f', 'f', 'o', 'l', 'd', '2'}), "", c4.movement, 0, -1);
    }

    @Override
    public void en(){
        ena = true;
    }
    @Override
    public void dd(){
        ena = false;
    }
    public static boolean onPacket(Object object, Connection.Side connection_side) {
        if(ena){
            if(connection_side == Connection.Side.OUT){
                if(object instanceof C03PacketPlayer){
                    if(StartChange && !FirstChange){
                        if(object instanceof C03PacketPlayer.C05PacketPlayerLook){
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer(mc.thePlayer.onGround));
                            return false;
                        }
                        if(object instanceof C03PacketPlayer.C06PacketPlayerPosLook){
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,mc.thePlayer.posY,mc.thePlayer.posZ,mc.thePlayer.onGround));
                            return false;
                        }
                    }
                    if(FirstChange){
                        if(object instanceof C03PacketPlayer.C06PacketPlayerPosLook){
                            ObfuscationReflectionHelper.setPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object,getYaw(),"yaw","field_149476_e");
                            ObfuscationReflectionHelper.setPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object, 84F,"pitch","field_149473_f");
                            ObfuscationReflectionHelper.setPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object, true,"rotating","field_149481_i");
                            FirstChange = false;
                            NeedFix = true;
                            return true;
                        }
                        if(object instanceof C03PacketPlayer.C05PacketPlayerLook){
                            ObfuscationReflectionHelper.setPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object,getYaw(),"yaw","field_149476_e");
                            ObfuscationReflectionHelper.setPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object, 84F,"pitch","field_149473_f");
                            ObfuscationReflectionHelper.setPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object, true,"rotating","field_149481_i");
                            FirstChange = false;
                            NeedFix = true;
                            return true;
                        }
                        if(object instanceof C03PacketPlayer.C04PacketPlayerPosition){
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX,mc.thePlayer.posY,mc.thePlayer.posZ,getYaw(),84F,mc.thePlayer.onGround));
                            FirstChange = false;
                            NeedFix = true;
                            return true;
                        }
                    }
                }
            }
        }
        if(ena && connection_side == Connection.Side.OUT && object instanceof C03PacketPlayer && NeedFix && !FirstChange && !StartChange){
            if(object instanceof C03PacketPlayer.C04PacketPlayerPosition){
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX,mc.thePlayer.posY,mc.thePlayer.posZ,mc.thePlayer.rotationYaw,mc.thePlayer.rotationPitch,mc.thePlayer.onGround));
                NeedFix = false;
                return true;
            }
        }
        return true;
    }
    public static float getYaw() {
        float yaw = 0;

        if (mc.gameSettings.keyBindForward.isKeyDown())
            yaw = mc.thePlayer.rotationYaw + 180;
        if (mc.gameSettings.keyBindLeft.isKeyDown())
            yaw = mc.thePlayer.rotationYaw + 90;
        if (mc.gameSettings.keyBindRight.isKeyDown())
            yaw = mc.thePlayer.rotationYaw - 90;
        if (mc.gameSettings.keyBindBack.isKeyDown())
            yaw = mc.thePlayer.rotationYaw;

        if (mc.gameSettings.keyBindForward.isKeyDown() && mc.gameSettings.keyBindLeft.isKeyDown())
            yaw = mc.thePlayer.rotationYaw + 90 + 45;
        if (mc.gameSettings.keyBindForward.isKeyDown() && mc.gameSettings.keyBindRight.isKeyDown())
            yaw = mc.thePlayer.rotationYaw - 90 - 45;

        if (mc.gameSettings.keyBindBack.isKeyDown() && mc.gameSettings.keyBindLeft.isKeyDown())
            yaw = mc.thePlayer.rotationYaw + 90 - 45;
        if (mc.gameSettings.keyBindBack.isKeyDown() && mc.gameSettings.keyBindRight.isKeyDown())
            yaw = mc.thePlayer.rotationYaw - 90 + 45;

        if (!isMoving())
            yaw = mc.thePlayer.rotationYaw + 180;

        return yaw;
    }

    @SubscribeEvent
    public void a(final TickEvent.RenderTickEvent a){
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if (mc.thePlayer.onGround
                && !Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed()
                && !isCollidable(Minecraft.getMinecraft().theWorld
                .getBlockState(new BlockPos(
                        Minecraft.getMinecraft().thePlayer.getPositionVector().add(new Vec3(0, -0.5, 0))))
                .getBlock())){
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), true);
            FirstChange=true;
            StartChange=true;
        }
        else if (!Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode())){
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), false);
            StartChange=false;
            FirstChange=false;
        }

        mc.thePlayer.cameraYaw = 0.105F;
        //mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(360 - mc.thePlayer.rotationYaw,79.44F,false));
        //mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(0.105F,90F,false));
        EntityPlayerSP player = mc.thePlayer;
        WorldClient world = mc.theWorld;
        double yDif = 1.0D;
        Scaffold2.BlockData data = null;
        double posY;
        for (posY = player.posY - 1.0D; posY > 0.0D; posY--) {
            Scaffold2.BlockData newData = null;
            if(mc.gameSettings.keyBindForward.isKeyDown()){
                if (mc.thePlayer.getHorizontalFacing() == EnumFacing.SOUTH) {
                    newData = getBlockData(new BlockPos(player.posX, posY, player.posZ - 0.26));
                }else
                if (mc.thePlayer.getHorizontalFacing() == EnumFacing.NORTH) {
                    newData = getBlockData(new BlockPos(player.posX, posY, player.posZ + 0.26));
                }else
                if (mc.thePlayer.getHorizontalFacing() == EnumFacing.EAST) {
                    newData = getBlockData(new BlockPos(player.posX - 0.26, posY, player.posZ));
                }else
                if (mc.thePlayer.getHorizontalFacing() == EnumFacing.WEST) {
                    newData = getBlockData(new BlockPos(player.posX + 0.26, posY, player.posZ));
                }
            }
            if(mc.gameSettings.keyBindBack.isKeyDown()){
                if (mc.thePlayer.getHorizontalFacing() == EnumFacing.NORTH) {
                    newData = getBlockData(new BlockPos(player.posX, posY, player.posZ - 0.26));
                }else
                if (mc.thePlayer.getHorizontalFacing() == EnumFacing.SOUTH) {
                    newData = getBlockData(new BlockPos(player.posX, posY, player.posZ + 0.26));
                }else
                if (mc.thePlayer.getHorizontalFacing() == EnumFacing.WEST) {
                    newData = getBlockData(new BlockPos(player.posX - 0.26, posY, player.posZ));
                }else
                if (mc.thePlayer.getHorizontalFacing() == EnumFacing.EAST) {
                    newData = getBlockData(new BlockPos(player.posX + 0.26, posY, player.posZ));
                }
            }
            if (newData != null) {
                yDif = player.posY - posY;
                if (yDif <= 3.0D) {
                    data = newData;
                    break;
                }
            }
        }
        int slot = -1;
        int blockCount = 0;
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = player.inventory.getStackInSlot(i);
            if (itemStack != null) {
                int stackSize = itemStack.stackSize;
                if (isValidItem(itemStack.getItem()) && stackSize > blockCount) {
                    blockCount = stackSize;
                    slot = i;
                }
            }
        }
        if (data != null && slot != -1) {
            BlockPos pos = data.pos;
            Block block = world.getBlockState(pos.offset(data.face)).getBlock();
            /*
            if(mc.thePlayer.getHorizontalFacing() == EnumFacing.EAST){data.face = EnumFacing.WEST;}
            if(mc.thePlayer.getHorizontalFacing() == EnumFacing.WEST){data.face = EnumFacing.EAST;}
            if(mc.thePlayer.getHorizontalFacing() == EnumFacing.SOUTH){data.face = EnumFacing.NORTH;}
            if(mc.thePlayer.getHorizontalFacing() == EnumFacing.NORTH){data.face = EnumFacing.SOUTH;}
            */
            Vec3 hitVec = getVec3(data);
            if (!this.validBlocks.contains(block) || isBlockUnder(yDif))
                return;
            int last;
            ///a.player.setRotationYawHead(360 - mc.thePlayer.rotationYaw);
            last = player.inventory.currentItem;
            player.inventory.currentItem = slot;

            if((mc.thePlayer.getHorizontalFacing() == EnumFacing.SOUTH && data.face == EnumFacing.WEST) || (mc.thePlayer.getHorizontalFacing() == EnumFacing.SOUTH && data.face == EnumFacing.EAST) || (mc.thePlayer.getHorizontalFacing() == EnumFacing.NORTH && data.face == EnumFacing.WEST) || (mc.thePlayer.getHorizontalFacing() == EnumFacing.NORTH && data.face == EnumFacing.EAST)){
                return;
                ///if(isCollidable(Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(Minecraft.getMinecraft().thePlayer.getPositionVector().add(new Vec3(0, -0.5, 0)))).getBlock()))
                /// return;
            }
            if (mc.playerController.onPlayerRightClick(player, world, player.getCurrentEquippedItem(), pos, data.face, hitVec)){
                //if (swing.getObject()) {
                //   player.swingItem();
                // }else {
                mc.thePlayer.swingItem();
                mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());}
            player.inventory.currentItem = last;
        }
    }

    //}
    public static boolean isCollidable(Block block) {
        return block != Blocks.air && block != Blocks.carrots && block != Blocks.deadbush
                && block != Blocks.double_plant && block != Blocks.flowing_lava && block != Blocks.flowing_water
                && block != Blocks.lava && block != Blocks.melon_stem && block != Blocks.nether_wart
                && block != Blocks.potatoes && block != Blocks.pumpkin_stem && block != Blocks.red_flower
                && block != Blocks.red_mushroom && block != Blocks.redstone_torch && block != Blocks.tallgrass
                && block != Blocks.torch && block != Blocks.unlit_redstone_torch && block != Blocks.yellow_flower
                && block != Blocks.vine && block != Blocks.water && block != Blocks.web && block != Blocks.wheat;
    }
    private boolean isBlockUnder(double yOffset) {
        EntityPlayerSP player = mc.thePlayer;
        return !this.validBlocks.contains(mc.theWorld.getBlockState(new BlockPos(player.posX, player.posY - yOffset, player.posZ)).getBlock());
    }

    private Vec3 getVec3(Scaffold2.BlockData data) {
        BlockPos pos = data.pos;
        EnumFacing face = data.face;
        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 0.5D;
        double z = pos.getZ() + 0.5D;
        x += face.getFrontOffsetX() / 2.0D;
        z += face.getFrontOffsetZ() / 2.0D;
        y += face.getFrontOffsetY() / 2.0D;
        if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
            x += randomNumber(0.3D, -0.3D);
            z += randomNumber(0.3D, -0.3D);
        } else {
            y += randomNumber(0.49D, 0.5D);
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST)
            z += randomNumber(0.3D, -0.3D);
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH)
            x += randomNumber(0.3D, -0.3D);
        return new Vec3(x, y, z);
    }
    private Vec3 getVec3_(Scaffold2.BlockData2 data) {
        BlockPos pos = data.pos;
        EnumFacing face = data.face;
        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 0.5D;
        double z = pos.getZ() + 0.5D;
        x += face.getFrontOffsetX() / 2.0D;
        z += face.getFrontOffsetZ() / 2.0D;
        y += face.getFrontOffsetY() / 2.0D;
        if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
            x += randomNumber(0.3D, -0.3D);
            z += randomNumber(0.3D, -0.3D);
        } else {
            y += randomNumber(0.49D, 0.5D);
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST)
            z += randomNumber(0.3D, -0.3D);
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH)
            x += randomNumber(0.3D, -0.3D);
        return new Vec3(x, y, z);
    }

    private double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }

    private Scaffold2.BlockData getBlockData(BlockPos pos) {
        BlockPos[] blockPositions = this.blockPositions;
        EnumFacing[] facings = this.facings;
        WorldClient world = mc.theWorld;
        BlockPos posBelow = new BlockPos(0, -1, 0);
        List<Block> validBlocks = this.validBlocks;
        if (!validBlocks.contains(world.getBlockState(pos.add((Vec3i)posBelow)).getBlock()))
            return new Scaffold2.BlockData(pos.add((Vec3i)posBelow), EnumFacing.UP);
        for (int i = 0, blockPositionsLength = blockPositions.length; i < blockPositionsLength; i++) {
            BlockPos blockPos = pos.add((Vec3i)blockPositions[i]);
            if (!validBlocks.contains(world.getBlockState(blockPos).getBlock()))
                return new Scaffold2.BlockData(blockPos, facings[i]);
            for (int i1 = 0; i1 < blockPositionsLength; i1++) {
                BlockPos blockPos1 = pos.add((Vec3i)blockPositions[i1]);
                BlockPos blockPos2 = blockPos.add((Vec3i)blockPositions[i1]);
                if (!validBlocks.contains(world.getBlockState(blockPos1).getBlock()))
                    return new Scaffold2.BlockData(blockPos1, facings[i1]);
                if (!validBlocks.contains(world.getBlockState(blockPos2).getBlock()))
                    return new Scaffold2.BlockData(blockPos2, facings[i1]);
            }
        }
        return null;
    }
    private Scaffold2.BlockData2 getBlockData2(BlockPos pos) {
        BlockPos[] blockPositions = this.blockPositions;
        EnumFacing[] facings = this.facings;
        WorldClient world = mc.theWorld;
        BlockPos posBelow = new BlockPos(0, -1, 0);
        List<Block> validBlocks = this.validBlocks;
        if (!validBlocks.contains(world.getBlockState(pos.add((Vec3i)posBelow)).getBlock()))
            return new Scaffold2.BlockData2(pos.add((Vec3i)posBelow), EnumFacing.UP);
        for (int i = 0, blockPositionsLength = blockPositions.length; i < blockPositionsLength; i++) {
            BlockPos blockPos = pos.add((Vec3i)blockPositions[i]);
            if (!validBlocks.contains(world.getBlockState(blockPos).getBlock()))
                return new Scaffold2.BlockData2(blockPos, facings[i]);
            for (int i1 = 0; i1 < blockPositionsLength; i1++) {
                BlockPos blockPos1 = pos.add((Vec3i)blockPositions[i1]);
                BlockPos blockPos2 = blockPos.add((Vec3i)blockPositions[i1]);
                if (!validBlocks.contains(world.getBlockState(blockPos1).getBlock()))
                    return new Scaffold2.BlockData2(blockPos1, facings[i1]);
                if (!validBlocks.contains(world.getBlockState(blockPos2).getBlock()))
                    return new Scaffold2.BlockData2(blockPos2, facings[i1]);
            }
        }
        return null;
    }
    private boolean isValidItem(Item item) {
        if (item instanceof ItemBlock) {
            ItemBlock iBlock = (ItemBlock)item;
            Block block = iBlock.getBlock();
            return !this.invalidBlocks.contains(block);
        }
        return false;
    }

    private static class BlockData {
        public final BlockPos pos;

        public final EnumFacing face;

        private BlockData(BlockPos pos, EnumFacing face) {
            this.pos = pos;
            this.face = face;
        }
    }
    private static class BlockData2 {
        public BlockPos pos;

        public EnumFacing face;

        private BlockData2(BlockPos pos, EnumFacing face) {
            this.pos = pos;
            this.face = face;
        }
    }
}
