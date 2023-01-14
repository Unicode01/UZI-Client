package io.fishermen.fpsdisplay.settings;

import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pw.cinque.event.Connection;

import java.lang.reflect.Method;

public class AutoFish extends GuiSettings{
    public static boolean en;
    public AutoFish(){
        super("AutoFish","",c4.Player,0,-1);
    }

    public static void onPacket(Object object, Connection.Side connection_side) {
        S29PacketSoundEffect packet;
        if (object instanceof S29PacketSoundEffect && (packet = (S29PacketSoundEffect)object).getSoundName().equalsIgnoreCase("entity.bobber.splash")) {
            new Thread("Fish"){

                @Override
                public void run() {
                    try {
                        rightClickMouse();
                        Thread.sleep(300);
                        rightClickMouse();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }.start();
        }
    }

    @Override
    public void en(){
        en=true;
    }
    @Override
    public void dd(){
        en = true;
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
}
