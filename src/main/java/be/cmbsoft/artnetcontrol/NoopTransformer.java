package be.cmbsoft.artnetcontrol;

public class NoopTransformer implements Transformer {
    @Override
    public byte[] transform(byte[] input) {
        return input;
    }
}
