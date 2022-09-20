package be.cmbsoft.artnetcontrol;

import ch.bildspur.artnet.ArtNetClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArtNetThread extends Thread {

    private Transformer transformer;

    @Override
    public void run() {
        List<ArtNetInput> inputs = new ArrayList<>();
        Map<ArtNetInput, ArtNetOutput> inputOutputMap = new HashMap<>();
        String targetIp = "127.0.0.1";
        for (int i = 0; i < 16; i++) {
            ArtNetInput input = new ArtNetInput(0, i);
            inputs.add(input);
            inputOutputMap.put(input, new ArtNetOutput(targetIp, 0, i));
        }
        ArtNetClient client = new ArtNetClient();
        client.start();
        boolean running = true;
        while (running) {
            for (ArtNetInput input : inputs) {
                byte[] dmxData = client.getInputBuffer().getDmxData((short) input.subnet(), (short) input.universe());
                byte[] outputData = transformData(dmxData);
                ArtNetOutput output = inputOutputMap.get(input);
                client.unicastDmx(output.ip(), output.subnet(), output.universe(), outputData);
            }
        }

    }

    private byte[] transformData(byte[] dmxData) {
        return transformer.transform(dmxData);
    }

    public void setTransformer(Transformer transformer) {
        this.transformer = transformer;
    }
}
