package io.fishermen.fpsdisplay.settings;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.keystrokes.render.Gui;
import pw.cinque.timechanger.ClickListener;

import java.awt.*;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.List;

public class Xray extends GuiSettings{
    private java.util.Timer t;
    private List<BlockPos> ren;
    private final long per = 200L;
    public static CommandSettings Range;
    public static ClickListener Iron;
    public static ClickListener Gold;
    public static ClickListener Diamond;
    public static ClickListener Emerald;
    public static ClickListener Lapis;
    public static ClickListener Redstone;
    public static ClickListener Coal;
    public static ClickListener Spawner;
    public Xray(){
        super(GuiSettings.a(new char[]{'X','r','a','y'}),"",c4.render,0,-1);
        Xray.Range = new CommandSettings(GuiSettings.a(new char[]{'R','a','n','g','e'}),20.0D, 5.0D, 50.0D, 1.0D);
        Xray.Iron = new ClickListener(GuiSettings.a(new char[]{'I','r','o','n'}),true);
        Xray.Gold = new ClickListener(GuiSettings.a(new char[]{'G','o','l','d'}),true);
        Xray.Diamond = new ClickListener(GuiSettings.a(new char[]{'D','i','a','m','o','n','d'}),true);
        Xray.Emerald = new ClickListener(GuiSettings.a(new char[]{'E','m','e','r','a','l','d'}),true);
        Xray.Lapis = new ClickListener(GuiSettings.a(new char[]{'L','a','p','i','s'}),true);
        Xray.Redstone = new ClickListener(GuiSettings.a(new char[]{'R','e','d','s','t','o','n','e'}),true);
        Xray.Coal = new ClickListener(GuiSettings.a(new char[]{'C','o','c','l'}),true);
        Xray.Spawner = new ClickListener(GuiSettings.a(new char[]{'S','p','a','w','n','e','r'}),true);
        this.avav(Range);
        this.avav(Iron);
        this.avav(Gold);
        this.avav(Diamond);
        this.avav(Emerald);
        this.avav(Lapis);
        this.avav(Redstone);
        this.avav(Coal);
        this.avav(Spawner);
    }
    @Override
    public void en() {
        this.ren = new ArrayList<>();
        (this.t = new java.util.Timer()).scheduleAtFixedRate(this.ta(), 0L, 200L);
    }

    public void onDisable() {
        if (this.t != null) {
            this.t.cancel();
            this.t.purge();
            this.t = null;
        }

    }

    private TimerTask ta() {
        return new TimerTask() {
            public void run() {
                Xray.this.ren.clear();
                int ra = (int)Xray.Range.g3tV4l4u3();

                for(int y = ra; y >= -ra; --y) {
                    for(int x = -ra; x <= ra; ++x) {
                        for(int z = -ra; z <= ra; ++z) {
                            if (GuiSettings.isPlayerInGame()) {
                                BlockPos p = new BlockPos(mc.thePlayer.posX + (double)x, mc.thePlayer.posY + (double)y, mc.thePlayer.posZ + (double)z);
                                Block bl = mc.theWorld.getBlockState(p).getBlock();
                                if ((Xray.Iron.i() && bl.equals(Blocks.iron_ore)) || (Xray.Gold.i() && bl.equals(Blocks.gold_ore)) || (Xray.Diamond.i() && bl.equals(Blocks.diamond_ore)) || (Xray.Emerald.i() && bl.equals(Blocks.emerald_ore)) || (Xray.Lapis.i() && bl.equals(Blocks.lapis_ore)) || (Xray.Redstone.i() && bl.equals(Blocks.redstone_ore)) || (Xray.Coal.i() && bl.equals(Blocks.coal_ore)) || (Xray.Spawner.i() && bl.equals(Blocks.mob_spawner))) {
                                    Xray.this.ren.add(p);
                                }
                            }
                        }
                    }
                }

            }
        };
    }

    @SubscribeEvent
    public void orl(RenderWorldLastEvent ev) {
        if(!this.getStat()){
            return;
        }
        if (GuiSettings.isPlayerInGame() && !this.ren.isEmpty()) {
            List<BlockPos> tRen = new ArrayList<>(this.ren);

            for (BlockPos p : tRen) {
                this.dr(p);
            }
        }

    }

    private void dr(BlockPos p) {
        int[] rgb = this.c(mc.theWorld.getBlockState(p).getBlock());
        if (rgb[0] + rgb[1] + rgb[2] != 0) {
            GuiSettings.HUD.re(p, (new Color(rgb[0], rgb[1], rgb[2])).getRGB(), true);
        }

    }

    private int[] c(Block b) {
        int red = 0;
        int green = 0;
        int blue = 0;
        if (b.equals(Blocks.iron_ore)) {
            red = 255;
            green = 255;
            blue = 255;
        } else if (b.equals(Blocks.gold_ore)) {
            red = 255;
            green = 255;
        } else if (b.equals(Blocks.diamond_ore)) {
            green = 220;
            blue = 255;
        } else if (b.equals(Blocks.emerald_ore)) {
            red = 35;
            green = 255;
        } else if (b.equals(Blocks.lapis_ore)) {
            green = 50;
            blue = 255;
        } else if (b.equals(Blocks.redstone_ore)) {
            red = 255;
        } else if (b.equals(Blocks.mob_spawner)) {
            red = 30;
            blue = 135;
        }else if(b.equals(Blocks.coal_ore)){
            red = 1;
            green = 1;
            blue = 1;
        }

        return new int[]{red, green, blue};
    }
}
