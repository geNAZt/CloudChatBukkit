package net.cubespace.PluginMessages;

import com.iKeirNez.PluginMessageApiPlus.PacketWriter;
import com.iKeirNez.PluginMessageApiPlus.StandardPacket;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class PermissionRequest extends StandardPacket {
    public PermissionRequest() {}

    @Override
    protected void handle(DataInputStream dataInputStream) throws IOException {

    }

    @Override
    protected PacketWriter write() throws IOException {
        return new PacketWriter(this);
    }
}
