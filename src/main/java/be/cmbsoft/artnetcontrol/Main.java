package be.cmbsoft.artnetcontrol;

public class Main {
    public static void main(String[] args) {
        ArtNetThread thread = new ArtNetThread();
        thread.setTransformer(new NoopTransformer());
        thread.start();
    }
}