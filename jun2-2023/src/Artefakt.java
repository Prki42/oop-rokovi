public abstract class Artefakt {
    private String oznaka;
    private String materijal;
    private PraistorijskoDoba doba;

    private static int counter = 0;

    public Artefakt(String oznaka, String materijal, PraistorijskoDoba doba) {
        this.oznaka = counter + oznaka;
        this.materijal = materijal;
        this.doba = doba;
        counter++;
    }

    public abstract boolean iskopajArtefakt(int kvalifikacijaArheologa);

    @Override
    public String toString() {
        return oznaka + " | " + materijal + " | " + doba.toString();
    }

    public static Artefakt parse(String s) {
        String[] xs = s.split(",\\s+");
        if (xs.length != 3) {
            throw new IllegalArgumentException("Invalid format");
        }

        String oznaka = xs[0];
        PraistorijskoDoba doba = PraistorijskoDoba.odOznake(xs[1]);
        String materijal = xs[2];

        char c = s.charAt(0);
        return switch (c) {
            case 'O' -> new Orudje(oznaka, materijal, doba);
            case 'P' -> new Posuda(oznaka, materijal, doba);
            case 'F' -> new Figurina(oznaka, materijal, doba);
            default -> throw new IllegalArgumentException("Invalid format");
        };
    };
}
