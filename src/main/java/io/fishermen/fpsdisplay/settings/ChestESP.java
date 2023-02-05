package io.fishermen.fpsdisplay.settings;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pw.cinque.timechanger.ClickListener;
import pw.cinque.CommandSettings.CommandSettings;

import java.awt.*;
import java.util.Iterator;

public class ChestESP extends GuiSettings{
    public static CommandSettings a;
    public static CommandSettings b;
    public static CommandSettings c;
    public static ClickListener d;
    public ChestESP(){
        super(GuiSettings.a(new char[]{'C','h','e','s','t','E','S','P'}),"",c4.render,0,-1);
        a = new CommandSettings("Red",0.0D, 0.0D, 255.0D, 1.0D);
        b = new CommandSettings("Green", 0.0D, 0.0D, 255.0D, 1.0D);
        c = new CommandSettings("Blue", 255.0D, 0.0D, 255.0D, 1.0D);
        d = new ClickListener("Rainbow", false);
        this.avav(a);
        this.avav(b);
        this.avav(c);
        this.avav(d);
    }
    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent ev) {
        if(!this.getStat()){
            return;
        }
        if (GuiSettings.isPlayerInGame()) {
            int rgb = d.i() ? a(6000, -270) : (new Color((int)a.g3tV4l4u3(), (int)b.g3tV4l4u3(), (int)c.g3tV4l4u3())).getRGB();
            Iterator var3 = mc.theWorld.loadedTileEntityList.iterator();

            while(true) {
                TileEntity te;
                do {
                    if (!var3.hasNext()) {
                        return;
                    }

                    te = (TileEntity)var3.next();
                } while(!(te instanceof TileEntityChest) && !(te instanceof TileEntityEnderChest));
                GuiSettings.HUD.re(te.getPos(), rgb, true);
            }
        }
    }
    public static int a(final int s, final int o) {
        float h = (System.currentTimeMillis() + (long)o) % (long)s;
        return Color.getHSBColor(h /= (float)s, 1.0f, 1.0f).getRGB();
    }
}
