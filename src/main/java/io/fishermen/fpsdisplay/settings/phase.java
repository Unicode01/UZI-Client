package io.fishermen.fpsdisplay.settings;


import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class phase extends GuiSettings {
    public phase(){
        super("Phase","",c4.Tests,0,-1);
    }



    @SubscribeEvent
    public void a(RenderWorldLastEvent e){
        if(!this.getStat()){
            return;
        }
        if(CaneReplace(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX-1,mc.thePlayer.posY,mc.thePlayer.posZ)).getBlock().getBlockState())){
            RenderBlock(new BlockPos(mc.thePlayer.posX-1,mc.thePlayer.posY,mc.thePlayer.posZ));
        }
        if(CaneReplace(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX-1,mc.thePlayer.posY,mc.thePlayer.posZ+1)).getBlock().getBlockState())){
            RenderBlock(new BlockPos(mc.thePlayer.posX-1,mc.thePlayer.posY,mc.thePlayer.posZ+1));
        }
        if(CaneReplace(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX-1,mc.thePlayer.posY,mc.thePlayer.posZ-1)).getBlock().getBlockState())){
            RenderBlock(new BlockPos(mc.thePlayer.posX-1,mc.thePlayer.posY,mc.thePlayer.posZ-1));
        }
        if(CaneReplace(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX+1,mc.thePlayer.posY,mc.thePlayer.posZ)).getBlock().getBlockState())){
            RenderBlock(new BlockPos(mc.thePlayer.posX+1,mc.thePlayer.posY,mc.thePlayer.posZ));
        }
        if(CaneReplace(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX+1,mc.thePlayer.posY,mc.thePlayer.posZ-1)).getBlock().getBlockState())){
            RenderBlock(new BlockPos(mc.thePlayer.posX+1,mc.thePlayer.posY,mc.thePlayer.posZ-1));
        }
        if(CaneReplace(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX+1,mc.thePlayer.posY,mc.thePlayer.posZ+1)).getBlock().getBlockState())){
            RenderBlock(new BlockPos(mc.thePlayer.posX+1,mc.thePlayer.posY,mc.thePlayer.posZ+1));
        }
        if(CaneReplace(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX,mc.thePlayer.posY,mc.thePlayer.posZ-1)).getBlock().getBlockState())){
            RenderBlock(new BlockPos(mc.thePlayer.posX,mc.thePlayer.posY,mc.thePlayer.posZ-1));
        }
        if(CaneReplace(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX,mc.thePlayer.posY,mc.thePlayer.posZ+1)).getBlock().getBlockState())){
            RenderBlock(new BlockPos(mc.thePlayer.posX,mc.thePlayer.posY,mc.thePlayer.posZ+1));
        }

        if(CaneReplace(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX-1,mc.thePlayer.posY+1,mc.thePlayer.posZ)).getBlock().getBlockState())){
            RenderBlock(new BlockPos(mc.thePlayer.posX-1,mc.thePlayer.posY,mc.thePlayer.posZ));
        }
        if(CaneReplace(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX-1,mc.thePlayer.posY+1,mc.thePlayer.posZ+1)).getBlock().getBlockState())){
            RenderBlock(new BlockPos(mc.thePlayer.posX-1,mc.thePlayer.posY,mc.thePlayer.posZ+1));
        }
        if(CaneReplace(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX-1,mc.thePlayer.posY+1,mc.thePlayer.posZ-1)).getBlock().getBlockState())){
            RenderBlock(new BlockPos(mc.thePlayer.posX-1,mc.thePlayer.posY,mc.thePlayer.posZ-1));
        }
        if(CaneReplace(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX+1,mc.thePlayer.posY+1,mc.thePlayer.posZ)).getBlock().getBlockState())){
            RenderBlock(new BlockPos(mc.thePlayer.posX+1,mc.thePlayer.posY,mc.thePlayer.posZ));
        }
        if(CaneReplace(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX+1,mc.thePlayer.posY+1,mc.thePlayer.posZ-1)).getBlock().getBlockState())){
            RenderBlock(new BlockPos(mc.thePlayer.posX+1,mc.thePlayer.posY,mc.thePlayer.posZ-1));
        }
        if(CaneReplace(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX+1,mc.thePlayer.posY+1,mc.thePlayer.posZ+1)).getBlock().getBlockState())){
            RenderBlock(new BlockPos(mc.thePlayer.posX+1,mc.thePlayer.posY,mc.thePlayer.posZ+1));
        }
        if(CaneReplace(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX,mc.thePlayer.posY+1,mc.thePlayer.posZ-1)).getBlock().getBlockState())){
            RenderBlock(new BlockPos(mc.thePlayer.posX,mc.thePlayer.posY,mc.thePlayer.posZ-1));
        }
        if(CaneReplace(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX,mc.thePlayer.posY+1,mc.thePlayer.posZ+1)).getBlock().getBlockState())){
            RenderBlock(new BlockPos(mc.thePlayer.posX,mc.thePlayer.posY,mc.thePlayer.posZ+1));
        }
    }
    public static boolean CaneReplace(BlockState Block){
        return Block.getBlock() != Blocks.air && Block.getBlock() != Blocks.water && Block.getBlock() != Blocks.lava && Block.getBlock() != Blocks.fire && Block.getBlock() != Blocks.grass && Block.getBlock() != Blocks.ladder;
    }
    public static void setAirBlock(BlockPos blockPos){
        mc.theWorld.setBlockState(blockPos, Blocks.air.getDefaultState(), 11);
    }
    public static void RenderBlock(BlockPos blockPos){
        setAirBlock(blockPos);
        HUD.re(blockPos, Color.RED.getRGB(),true);
    }
}
