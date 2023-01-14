package pw.cinque.event;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import net.minecraft.client.Minecraft;
import pw.cinque.keystrokes.EventsHandler;

public class Connection extends ChannelDuplexHandler {

    private EventsHandler eventHandler;

    public Connection(EventsHandler eventHandler) {
        this.eventHandler = eventHandler;

        try {
            ChannelPipeline exception = Minecraft.getMinecraft().getNetHandler().getNetworkManager().channel().pipeline();
            exception.addBefore("packet_handler", "PacketHandler", this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        if (this.eventHandler.onPacket(packet, Side.IN)) {
            super.channelRead(ctx, packet);
        }
    }

    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        if (this.eventHandler.onPacket(packet, Side.OUT)) {
            super.write(ctx, packet, promise);
        }
    }

    public static enum Side {

        IN, OUT;
    }
}
