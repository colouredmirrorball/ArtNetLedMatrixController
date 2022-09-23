package be.cmbsoft.artnetcontrol;

import com.illposed.osc.OSCBadDataEvent;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPacketEvent;
import com.illposed.osc.OSCPacketListener;
import com.illposed.osc.transport.OSCPortIn;

import java.io.IOException;

public class MatrixController {
    private final ArtNetThread thread;

    public MatrixController() throws IOException {
        OSCPortIn port = new OSCPortIn(6497);
        thread = new ArtNetThread();
        thread.setTransformer(new RGBWTransformer());
        thread.start();
        port.addPacketListener(new OSCPacketListener() {
            @Override
            public void handlePacket(OSCPacketEvent event) {
                OSCMessage message = (OSCMessage) event.getPacket();
                System.out.println(message.getAddress());
                CharSequence argumentTypeTags = message.getInfo().getArgumentTypeTags();
                for (int i = 0; i < argumentTypeTags.length(); i++) {
                    processMessage(message.getAddress(), argumentTypeTags.charAt(i), message.getArguments().get(i));
                }
            }

            @Override
            public void handleBadData(OSCBadDataEvent event) {
                System.out.println("BAD DATA!!1!!");
                System.err.println(event.getException());
            }
        });


    }

    public static void main(String[] args) throws IOException {
        new MatrixController();
    }

    private void processMessage(String address, char typetag, Object data) {
        switch (typetag) {
            case 'i':
                Integer valueInteger = (Integer) data;
                break;
            case 's':
                String valueString = (String) data;
                break;
            case 'f':
                Float valueFloat = (Float) data;
                break;
        }
    }
}