import java.util.Random;

public class Posuda extends Artefakt {

    public Posuda(String oznaka, String materijal, PraistorijskoDoba doba) {
        super(oznaka, materijal, doba);
    }

    @Override
    public boolean iskopajArtefakt(int kvalifikacijaArheologa) {
        return (new Random()).nextInt(100) < kvalifikacijaArheologa*9;
    }
}
