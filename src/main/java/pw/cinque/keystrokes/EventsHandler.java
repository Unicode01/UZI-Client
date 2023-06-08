package pw.cinque.keystrokes;


import io.fishermen.fpsdisplay.settings.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.event.Connection;
import pw.cinque.event.EventPlayerPost;

public class EventsHandler {
    private boolean initialized = false;
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent clienttickevent) {
        if(Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null) {
            this.initialized = false;
        }else{
            EventsHandler eventshandler = this;
            try {
                if (!eventshandler.initialized) {
                    new Connection(this);
                    this.initialized = true;
                }
            }catch (RuntimeException runtimeexception){
                runtimeexception.printStackTrace();
            }
        }
    }
/*
    public boolean onPacket(Object object, Connection.Side connection_side) {
        boolean flag = true;
        for (Object o : HackManager.getHacks()) {
            Hack hack = (Hack) o;

            if (hack.isToggled() && Wrapper.INSTANCE.world() != null) {
                flag &= hack.onPacket(object, connection_side);
            }
        }

        return flag;
    }
*/

/*
    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent attackentityevent) {
        if (!Utils.nullCheck() && !GhostMode.enabled) {

            try {
                HackManager.onAttackEntity(attackentityevent);
            } catch (RuntimeException runtimeexception) {
                runtimeexception.printStackTrace();
                ChatUtils.error("RuntimeException: onAttackEntity");
                ChatUtils.error(runtimeexception.toString());
                Utils.copy(runtimeexception.toString());
            }

        }
    }
*/
@SubscribeEvent
public void onLivingUpdate(LivingUpdateEvent livingupdateevent) {
    if (Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().thePlayer != null) {
        EventHackManager.onLivingUpdate(livingupdateevent);
    }
}
    @SubscribeEvent
    public void onPlayerEventPost(EventPlayerPost eventplayerpost) {
       if (Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().thePlayer != null) {
           Minecraft.getMinecraft().thePlayer.sendChatMessage("A");
           EventHackManager.onPlayerEventPost(eventplayerpost);
    }
}
    public boolean onPacket(Object object, Connection.Side connection_side) {
        boolean flag = true;
        if (Minecraft.getMinecraft().theWorld != null) {
            PacketSniffer.onPacket(object,connection_side);
            NoFall.onPacket(object,connection_side);
            FastBreak.onPacket(object,connection_side);
            AutoFish.onPacket(object,connection_side);
            Wool.onPacket(object,connection_side);
            flag = Scaffold2.onPacket(object,connection_side);
            if(!flag){
                return false;
            }
            flag = Dada.onPacket(object,connection_side);
            if(!flag){
                return false;
            }
            flag = NewKillaura.onPacket(object,connection_side);
            if(!flag){
                return false;
            }
            flag = Teleport.onPacket(object,connection_side);
            if(!flag){
                return false;
            }
            flag = Criticals.onPacket(object,connection_side);
            if(!flag){
                return false;
            }
            flag = NoSlow.onPacket(object,connection_side);
            if(!flag){
                return false;
            }
            flag = Disabler.onPacket(object, connection_side);
            if(!flag){
                return false;
            }
            flag = Da.onPacket(object, connection_side);
            if(!flag){
                return false;
            }
            flag = Blink.onPacket(object,connection_side);
            if(!flag){
                return false;
            }
            flag = NoHurtCam.onPacket(object,connection_side);
            if(!flag){
                return false;
            }
            PingSpoof.onPacket(object,connection_side);
            return true;
        }
        return flag;
    }
}
