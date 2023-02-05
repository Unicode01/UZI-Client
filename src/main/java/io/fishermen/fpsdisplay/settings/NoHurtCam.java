package io.fishermen.fpsdisplay.settings;

import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.event.Connection;

public class NoHurtCam extends GuiSettings{
    private static boolean en;
    private boolean CancelTick;
    public NoHurtCam(){
        super("NoHurtCam","",c4.render,0,-1);
    }
    @Override
    public void en(){
        en = true;
    }
    @Override
    public void dd(){
        en = false;
    }
    public static boolean onPacket(Object object, Connection.Side connection_side) {
        if (connection_side == Connection.Side.IN && object instanceof S19PacketEntityStatus && en){
            HurtTick = true;
            return !(((S19PacketEntityStatus) object).getEntity(mc.theWorld) == mc.thePlayer);
        }
        return true;
    }
    @SubscribeEvent
    public void a(TickEvent.PlayerTickEvent E){
        if(!this.getStat()){
            return;
        }
        if(CancelTick){
            CancelTick=false;
            HurtTick=false;
        }
        if(HurtTick){
            CancelTick = true;
        }
    }
}
