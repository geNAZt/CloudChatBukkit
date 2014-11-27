package net.cubespace.PluginMessages;

import com.iKeirNez.PluginMessageApiPlus.PacketWriter;
import com.iKeirNez.PluginMessageApiPlus.StandardPacket;

import java.io.DataInputStream;
import java.io.IOException;

public class OutputMessage extends StandardPacket {
    private boolean output;

    public OutputMessage() {}

    public OutputMessage(boolean output) {
        this.output = output;
    }

    public boolean isOutput() {
        return output;
    }

    @Override
    public void handle(DataInputStream dataInputStream) throws IOException {
        this.output = dataInputStream.readBoolean();
    }

    @Override
    public PacketWriter write() throws IOException {
        PacketWriter packetWriter = new PacketWriter(this);
        packetWriter.writeBoolean(output);
        return packetWriter;
    }
}
