public class Slika extends Fajl {
    private int sirina;
    private int visina;
    private boolean uBoji;

    public Slika(String naziv, int sirina, int visina, boolean uBoji) {
        super(naziv);
        this.sirina = sirina;
        this.visina = visina;
        this.uBoji = uBoji;
    }

    public int getSirina() {
        return sirina;
    }

    public int getVisina() {
        return visina;
    }

    public boolean isuBoji() {
        return uBoji;
    }

    public void setSirina(int sirina) {
        this.sirina = sirina;
    }

    public void setVisina(int visina) {
        this.visina = visina;
    }

    public void setuBoji(boolean uBoji) {
        this.uBoji = uBoji;
    }

    @Override
    public int velicina() {
        int x = visina * sirina;
        if (uBoji) {
            x = x * 3;
        }
        return x;
    }

    public static Slika fromString(String s) {
        String[] params = s.split(" ");
        String naziv = params[0];
        int sirina, visina, uBojiInt;
        try {
            sirina = Integer.parseInt(params[1]);
            visina = Integer.parseInt(params[2]);
            uBojiInt = Integer.parseInt(params[3]);
        } catch (NumberFormatException _) {
            throw new IllegalArgumentException("Parametri za sliku nisu brojcane vrednosti");
        }
        if (uBojiInt != 0 && uBojiInt != 1) {
            throw new IllegalArgumentException("Parametar za boju mora biti 0 ili 1");
        }
        boolean uBoji = uBojiInt == 1;
        return new Slika(naziv, sirina, visina, uBoji);
    }
}
