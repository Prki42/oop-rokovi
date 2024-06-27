import java.util.Random;

public class Figurina extends Artefakt {
    public Figurina(String oznaka, String materijal, PraistorijskoDoba doba) {
        super(oznaka, materijal, doba);
    }

    @Override
    public boolean iskopajArtefakt(int kvalifikacijaArheologa) {
        return (new Random()).nextInt(100) < kvalifikacijaArheologa*7;

    }
}
