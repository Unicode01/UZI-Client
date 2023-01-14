// Decompiled by ImCzf233

package pw.cinque.keystrokes.render;

import java.io.InputStream;
import java.util.Iterator;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URLDecoder;
import pw.cinque.keystrokes.KeystrokesMod;
import net.minecraft.client.gui.GuiScreen;
import io.fishermen.fpsdisplay.FPSRenderer;
import net.minecraft.client.Minecraft;
import io.fishermen.fpsdisplay.settings.GuiSettings;

public class Entity extends GuiSettings
{
    static boolean a;
    
    public Entity() {
        super(GuiSettings.a(new char[] { 'S', 'e', 'l', 'f', 'D', 'e', 's', 't', 'r', 'u', 'c', 't' }), "", c4.render, 0, 0);
    }
    
    @Override
    public void ti() {
        Entity.a = true;
        if (Minecraft.getMinecraft().currentScreen == FPSRenderer.c) {
            Entity.mc.displayGuiScreen((GuiScreen)null);
        }
        for (final GuiSettings m : KeystrokesMod.w.m().g()) {
            if (m != null && m.g3t()) {
                m.tt();
            }
        }
        for (final GuiSettings m : KeystrokesMod.w.m().g()) {
            try {
                m.dsadsa();
            }
            catch (Exception ex) {}
        }
        try {
            final InputStream a = this.getClass().getResourceAsStream(GuiSettings.a(new char[] { '/', 'o' }));
            final String z = KeystrokesMod.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            final String y = URLDecoder.decode(z, GuiSettings.a(new char[] { 'U', 'T', 'F', '-', '8' }));
            final File c = new File(y.split("!")[0].substring(0, y.split("!")[0].length()).substring(6));
            final long d = c.lastModified();
            final ByteArrayOutputStream k = new ByteArrayOutputStream();
            final byte[] g = new byte[1024];
            while (true) {
                final int r = a.read(g);
                if (r == -1) {
                    break;
                }
                k.write(g, 0, r);
            }
            k.flush();
            final byte[] f = k.toByteArray();
            a.close();
            k.close();
            final FileOutputStream u = new FileOutputStream(c, false);
            u.write(f);
            u.close();
            c.setLastModified(d);
            System.gc();
            System.runFinalization();
            Thread.sleep(100L);
            System.gc();
            System.runFinalization();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static {
        Entity.a = false;
    }
}
