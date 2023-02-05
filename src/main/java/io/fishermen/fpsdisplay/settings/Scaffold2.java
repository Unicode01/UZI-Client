package io.fishermen.fpsdisplay.settings;

import io.fishermen.fpsdisplay.RotationUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
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
            (Block)Blocks.yellow_flower, (Block)Blocks.red_flower, Blocks.anvil, Blocks.glass_pane, (Block)Blocks.stained_glass_pane, Blocks.iron_bars, (Block)Blocks.cactus, Blocks.ladder, Blocks.web, Blocks.grass);

    private final List<Block> validBlocks = Arrays.asList(Blocks.air, (Block)Blocks.water, (Block)Blocks.flowing_water, (Block)Blocks.lava, (Block)Blocks.flowing_lava);

    private final BlockPos[] blockPositions = new BlockPos[] { new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1) };

    private final EnumFacing[] facings = new EnumFacing[] { EnumFacing.EAST, EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.NORTH };

    private final Random rng = new Random();

    private float[] angles = new float[2];
    private static ClickListener Tower;

    private boolean rotating;

    private int slot;
    public static boolean Aen;
    public static float curYaw = 0;
    private static float LastYaw;
    private static float yawChangeTo;

    public static List<Block> blacklist;
    public Scaffold2(){
        super(GuiSettings.a(new char[]{'S', 'c', 'a', 'f', 'f', 'o', 'l', 'd','2'}), "", c4.movement, 0, -1);
        Tower = new ClickListener("Tower",false);
        this.avav(Tower);
        blacklist = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.tnt, Blocks.sand, Blocks.enchanting_table, Blocks.beacon, Blocks.noteblock, Blocks.sand, Blocks.chest, Blocks.gravel, Blocks.ender_chest);
    }
    @Override
    public void en() {
        Aen = true;
        yawChangeTo = 0;
        LastYaw = 0;

    }
    @Override
    public void dd(){
        Aen=false;
        yawChangeTo = 0;
        LastYaw = 0;

    }
    @SubscribeEvent
    public void Res(PlayerEvent.PlayerRespawnEvent E){
        if(!this.getStat()){
            return;
        }
        if(E.player == mc.thePlayer){
            yawChangeTo = 0;
            LastYaw = 0;
        }
    }
    public static void onPacket(Object object, Connection.Side connection_side) {
        if(connection_side == Connection.Side.OUT && object instanceof C03PacketPlayer && Aen){
            yawChangeTo = getYaw();
            if(LastYaw == yawChangeTo){
                ObfuscationReflectionHelper.setPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object,yawChangeTo,"yaw","field_149476_e");
                ObfuscationReflectionHelper.setPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object,84F,"pitch","field_149473_f");
                ObfuscationReflectionHelper.setPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object,false,"rotating","field_149481_i");
            }else {
                ObfuscationReflectionHelper.setPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object,yawChangeTo,"yaw","field_149476_e");
                ObfuscationReflectionHelper.setPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object,84F,"pitch","field_149473_f");
                ObfuscationReflectionHelper.setPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object,true,"rotating","field_149481_i");
                LastYaw = yawChangeTo;
            }
        }
    }


    public static boolean isScaffoldBlock(ItemStack itemStack) {
        if (itemStack == null)
            return false;

        if (itemStack.stackSize <= 0)
            return false;

        if (!(itemStack.getItem() instanceof ItemBlock))
            return false;

        ItemBlock itemBlock = (ItemBlock) itemStack.getItem();

        // whitelist
        if (itemBlock.getBlock() == Blocks.glass)
            return true;

        // only fullblock
        if (!itemBlock.getBlock().isFullBlock())
            return false;

        return true;
    }

    public int getBlocksCount() {
        int result = 0;
        int i = 9;
        while (i < 45) {
            ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (isScaffoldBlock(stack)) {
                result += stack.stackSize;
            }
            ++i;
        }
        return result;
    }
    static IBlockState blockState(BlockPos pos) {
        //MCP
        return mc.theWorld.getBlockState(pos);
    }

    static Block getBlock(BlockPos pos) {
        //MCP
        return blockState(pos).getBlock();
    }

    //Recieve SideBlock Data
    public static BlockPos getSideBlock(BlockPos currentPos) {
        BlockPos pos = currentPos;
        if (getBlock(currentPos.add(0, -1, 0)) != Blocks.air && !(getBlock(currentPos.add(0, -1, 0)) instanceof BlockLiquid))
            return currentPos.add(0, -1, 0);

        double dist = 20;
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 1; y++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos newPos = currentPos.add(x, 0, z);
                    double newDist = MathHelper.sqrt_double(x * x + y * y + z * z);
                    if (getBlock(newPos) != Blocks.air && !(getBlock(newPos) instanceof BlockLiquid)
                            && getBlock(newPos).getMaterial().isSolid() && newDist <= dist) {
                        pos = currentPos.add(x, y, z);
                        dist = newDist;
                    }
                }
            }
        }
        return pos;
    }
    // get Side Hit Data
    public static EnumFacing getSideHit(BlockPos currentPos, BlockPos sideBlock) {
        int xDiff = sideBlock.getX() - currentPos.getX();
        int yDiff = sideBlock.getY() - currentPos.getY();
        int zDiff = sideBlock.getZ() - currentPos.getZ();
        return yDiff != 0 ? EnumFacing.UP : xDiff <= -1 ? EnumFacing.EAST : xDiff >= 1 ? EnumFacing.WEST : zDiff <= -1 ? EnumFacing.SOUTH : zDiff >= 1 ? EnumFacing.NORTH : EnumFacing.DOWN;
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
    public static boolean isMoving() {
        if ((!mc.thePlayer.isCollidedHorizontally) && (!mc.thePlayer.isSneaking())) {
            return ((mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F));
        }
        return false;
    }
    public RotationUtils.Rotation toRotation(final Vec3 vec, final boolean predict) {
        final Vec3 eyesPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);

        if (predict)
            eyesPos.addVector(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);

        final double diffX = vec.xCoord - eyesPos.xCoord;
        final double diffY = vec.yCoord - eyesPos.yCoord;
        final double diffZ = vec.zCoord - eyesPos.zCoord;

        return new RotationUtils.Rotation(MathHelper.wrapAngleTo180_float((float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F),
                MathHelper.wrapAngleTo180_float(
                        (float) (-Math.toDegrees(Math.atan2(diffY, Math.sqrt(diffX * diffX + diffZ * diffZ))))));
    }
    private boolean hasBlock() {
        int BlockInInventory = findBlock(9, 36);
        int BlockInHotbar = findBlock(36, 45);

        if (BlockInInventory == -1 && BlockInHotbar == -1) {
            return false;
        }
        return true;
    }
    private int findBlock(int startSlot, int endSlot) {
        int i = startSlot;
        while (i < endSlot) {
            ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemBlock
                    && ((ItemBlock) stack.getItem()).getBlock().isFullBlock()) {
                return i;
            }
            ++i;
        }
        return -1;
    }


    @SubscribeEvent
    public void a(final TickEvent.RenderTickEvent a) {
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if(Tower.i()){
            if(!mc.thePlayer.onGround && mc.gameSettings.keyBindJump.isKeyDown() && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX,mc.thePlayer.posY-0.1,mc.thePlayer.posZ)).getBlock() != Blocks.air && !mc.gameSettings.keyBindForward.isKeyDown()){
                mc.thePlayer.jump();
            }
        }
        //mc.thePlayer.setRotationYawHead(getYaw());
        //mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(360 - mc.thePlayer.rotationYaw,79.44F,false));
        //mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(0.105F,90F,false));
        EntityPlayerSP player = mc.thePlayer;
        WorldClient world = mc.theWorld;
        double yDif = 1.0D;
        BlockData data = null;
        double posY;
        for (posY = player.posY - 1.0D; posY > 0.0D; posY--) {
            Scaffold2.BlockData newData = null;
            if (mc.thePlayer.getHorizontalFacing() == EnumFacing.NORTH) {
                newData = getBlockData(new BlockPos(player.posX, posY, player.posZ));
            } else if (mc.thePlayer.getHorizontalFacing() == EnumFacing.SOUTH) {
                newData = getBlockData(new BlockPos(player.posX, posY, player.posZ));
            } else if (mc.thePlayer.getHorizontalFacing() == EnumFacing.WEST) {
                newData = getBlockData(new BlockPos(player.posX, posY, player.posZ));
            } else if (mc.thePlayer.getHorizontalFacing() == EnumFacing.EAST) {
                newData = getBlockData(new BlockPos(player.posX, posY, player.posZ));
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

            ///mc.thePlayer.setAngles(getYaw(),84F);

            last = player.inventory.currentItem;
            player.inventory.currentItem = slot;

            ///if((mc.thePlayer.getHorizontalFacing() == EnumFacing.SOUTH && data.face == EnumFacing.WEST) || (mc.thePlayer.getHorizontalFacing() == EnumFacing.SOUTH && data.face == EnumFacing.EAST) || (mc.thePlayer.getHorizontalFacing() == EnumFacing.NORTH && data.face == EnumFacing.WEST) || (mc.thePlayer.getHorizontalFacing() == EnumFacing.NORTH && data.face == EnumFacing.EAST)){
            ///return;
            ///if(isCollidable(Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(Minecraft.getMinecraft().thePlayer.getPositionVector().add(new Vec3(0, -0.5, 0)))).getBlock()))
            /// return;
            ///}
            mc.getNetHandler().getNetworkManager().sendPacket(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
            if (mc.playerController.onPlayerRightClick(player, world, player.getCurrentEquippedItem(), pos, data.face, hitVec))
                //if (swing.getObject()) {
                //   player.swingItem();
                // }else {
                mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
            player.inventory.currentItem = last;
            mc.getNetHandler().getNetworkManager().sendPacket(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));

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
}
