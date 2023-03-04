package io.fishermen.fpsdisplay.settings;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import pw.cinque.timechanger.ClickListener;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Scaffold extends GuiSettings {
    private static ClickListener Packet;
    private static ClickListener KeepY;
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

    private int slot;
    public Scaffold() {
        super(GuiSettings.a(new char[]{'S', 'c', 'a', 'f', 'f', 'o', 'l', 'd'}), "", c4.movement, 0, -1);
        Scaffold.Packet = new ClickListener("Send Sneak Packet", false);
        KeepY = new ClickListener("KeepY",true);
        this.avav(Scaffold.Packet);
        this.avav(KeepY);
    }
    @SubscribeEvent
    public void a(final TickEvent.RenderTickEvent a){
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        mc.thePlayer.cameraYaw = 0.105F;
        //mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(360 - mc.thePlayer.rotationYaw,79.44F,false));
        //mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(0.105F,90F,false));
        EntityPlayerSP player = mc.thePlayer;
        WorldClient world = mc.theWorld;
        double yDif = 1.0D;
        BlockData data = null;
        double posY;
        for (posY = player.posY - 1.0D; posY > 0.0D; posY--) {
            BlockData newData = null;
            if (mc.thePlayer.getHorizontalFacing() == EnumFacing.NORTH && mc.gameSettings.keyBindBack.isKeyDown()) {
                newData = getBlockData(new BlockPos(player.posX, posY, player.posZ - 0.26));
            }else
            if (mc.thePlayer.getHorizontalFacing() == EnumFacing.SOUTH && mc.gameSettings.keyBindBack.isKeyDown()) {
                newData = getBlockData(new BlockPos(player.posX, posY, player.posZ + 0.26));
            }else
            if (mc.thePlayer.getHorizontalFacing() == EnumFacing.WEST && mc.gameSettings.keyBindBack.isKeyDown()) {
                newData = getBlockData(new BlockPos(player.posX - 0.26, posY, player.posZ));
            }else
            if (mc.thePlayer.getHorizontalFacing() == EnumFacing.EAST && mc.gameSettings.keyBindBack.isKeyDown()) {
                newData = getBlockData(new BlockPos(player.posX + 0.26, posY, player.posZ));
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
            mc.thePlayer.rotationPitch = 84F;
            last = player.inventory.currentItem;
            player.inventory.currentItem = slot;

            if((mc.thePlayer.getHorizontalFacing() == EnumFacing.SOUTH && data.face == EnumFacing.WEST) || (mc.thePlayer.getHorizontalFacing() == EnumFacing.SOUTH && data.face == EnumFacing.EAST) || (mc.thePlayer.getHorizontalFacing() == EnumFacing.NORTH && data.face == EnumFacing.WEST) || (mc.thePlayer.getHorizontalFacing() == EnumFacing.NORTH && data.face == EnumFacing.EAST)){
                return;
                ///if(isCollidable(Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(Minecraft.getMinecraft().thePlayer.getPositionVector().add(new Vec3(0, -0.5, 0)))).getBlock()))
                   /// return;
            }
            if(Packet.i()){
                mc.getNetHandler().getNetworkManager().sendPacket(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
            }
                if (mc.playerController.onPlayerRightClick(player, world, player.getCurrentEquippedItem(), pos, data.face, hitVec)){
                    //if (swing.getObject()) {
                    //   player.swingItem();
                    // }else {
                    mc.thePlayer.swingItem();
                    mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());}
                player.inventory.currentItem = last;
            if(Packet.i()){
                mc.getNetHandler().getNetworkManager().sendPacket(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
            }
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

    private Vec3 getVec3(BlockData data) {
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
    private Vec3 getVec3_(BlockData2 data) {
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

    private BlockData getBlockData(BlockPos pos) {
        BlockPos[] blockPositions = this.blockPositions;
        EnumFacing[] facings = this.facings;
        WorldClient world = mc.theWorld;
        BlockPos posBelow = new BlockPos(0, -1, 0);
        List<Block> validBlocks = this.validBlocks;
        if (!validBlocks.contains(world.getBlockState(pos.add((Vec3i)posBelow)).getBlock()))
            return new BlockData(pos.add((Vec3i)posBelow), EnumFacing.UP);
        for (int i = 0, blockPositionsLength = blockPositions.length; i < blockPositionsLength; i++) {
            BlockPos blockPos = pos.add((Vec3i)blockPositions[i]);
            if (!validBlocks.contains(world.getBlockState(blockPos).getBlock()))
                return new BlockData(blockPos, facings[i]);
            for (int i1 = 0; i1 < blockPositionsLength; i1++) {
                BlockPos blockPos1 = pos.add((Vec3i)blockPositions[i1]);
                BlockPos blockPos2 = blockPos.add((Vec3i)blockPositions[i1]);
                if (!validBlocks.contains(world.getBlockState(blockPos1).getBlock()))
                    return new BlockData(blockPos1, facings[i1]);
                if (!validBlocks.contains(world.getBlockState(blockPos2).getBlock()))
                    return new BlockData(blockPos2, facings[i1]);
            }
        }
        return null;
    }
    private BlockData2 getBlockData2(BlockPos pos) {
        BlockPos[] blockPositions = this.blockPositions;
        EnumFacing[] facings = this.facings;
        WorldClient world = mc.theWorld;
        BlockPos posBelow = new BlockPos(0, -1, 0);
        List<Block> validBlocks = this.validBlocks;
        if (!validBlocks.contains(world.getBlockState(pos.add((Vec3i)posBelow)).getBlock()))
            return new BlockData2(pos.add((Vec3i)posBelow), EnumFacing.UP);
        for (int i = 0, blockPositionsLength = blockPositions.length; i < blockPositionsLength; i++) {
            BlockPos blockPos = pos.add((Vec3i)blockPositions[i]);
            if (!validBlocks.contains(world.getBlockState(blockPos).getBlock()))
                return new BlockData2(blockPos, facings[i]);
            for (int i1 = 0; i1 < blockPositionsLength; i1++) {
                BlockPos blockPos1 = pos.add((Vec3i)blockPositions[i1]);
                BlockPos blockPos2 = blockPos.add((Vec3i)blockPositions[i1]);
                if (!validBlocks.contains(world.getBlockState(blockPos1).getBlock()))
                    return new BlockData2(blockPos1, facings[i1]);
                if (!validBlocks.contains(world.getBlockState(blockPos2).getBlock()))
                    return new BlockData2(blockPos2, facings[i1]);
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
