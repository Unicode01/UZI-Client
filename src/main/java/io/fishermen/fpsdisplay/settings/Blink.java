package io.fishermen.fpsdisplay.settings;

import net.minecraft.network.Packet;
import pw.cinque.event.Connection;
import pw.cinque.timechanger.ClickListener;

import java.util.*;

public class Blink extends GuiSettings{
    public static boolean En;
    public static ClickListener LosePacket;
    private static ArrayList<Object> Packets = new ArrayList<Object>();
    public Blink(){
        super("Blink","",c4.Player,0,-1);
        this.avav(LosePacket = new ClickListener("LosePacket",true));
    }
    @Override
    public void dd(){
        En = false;
        for (Object P : Packets){
            mc.getNetHandler().addToSendQueue((Packet) P);
        }
        Packets.clear();
    }
    @Override
    public void en(){
        En = true;
    }
    public static boolean onPacket(Object packet, Connection.Side connection_side){
        if(connection_side == Connection.Side.OUT && En){
            if(LosePacket.i()){
                return false;
            }
            Packets.add(packet);
            return false;
        }
        return true;
    }
}
