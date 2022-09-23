package be.cmbsoft.artnetcontrol;

import static processing.core.PApplet.*;

public class RGBWTransformer implements Transformer {
    public static int getWhite(int red, int green, int blue) {
        int max = max(red, green, blue);
        int min = min(red, green, blue);
        int delta = max - min;
        int luminosity = (max + min) / 2;
        int saturation = delta == 0 ? 0 : delta / (255 - abs(2 * luminosity - 255));
        int white = 0;
        if (saturation < 25) {
            white = (byte) (luminosity * (255 - saturation * 10));
        }
        return white;
    }

    @Override
    public byte[] transform(byte[] input) {
        int pixels = input.length / 3;
        byte[] output = new byte[pixels * 4];
        for (int i = 0; i < pixels; i++) {
            int inputIdx = 3 * i;
            int outputIdx = 4 * i;
            output[outputIdx] = input[inputIdx];
            output[outputIdx + 1] = input[inputIdx + 2];
            output[outputIdx + 2] = input[inputIdx + 1];
            output[outputIdx + 3] = (byte) getWhite(input[inputIdx], input[inputIdx + 1], input[inputIdx + 2]);
        }
        return output;
    }
}
