package net.cubespace.PluginMessages;

import com.iKeirNez.PluginMessageApiPlus.PacketWriter;
import com.iKeirNez.PluginMessageApiPlus.StandardPacket;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class PermissionResponse extends StandardPacket {
    private String permission;
    private int mode;

    public PermissionResponse() {}

    public PermissionResponse(String permission, int mode) {
        this.permission = permission;
        this.mode = mode;
    }

    public String getPermission() {
        return permission;
    }

    public int getMode() {
        return mode;
    }

    @Override
    public void handle(DataInputStream dataInputStream) throws IOException {
        permission = dataInputStream.readUTF();
        mode = dataInputStream.readInt();
    }

    @Override
    public PacketWriter write() throws IOException {
        PacketWriter packetWriter = new PacketWriter(this);
        packetWriter.writeUTF(permission);
        packetWriter.writeInt(mode);

        return packetWriter;
    }
}
