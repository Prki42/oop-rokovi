public class Arheolog implements Comparable<Arheolog> {
    private String imePrezime;
    private int kvalifikacija;
    private boolean licenciran;

    public Arheolog(String imePrezime, int kvalifikacija) {
        this.imePrezime = imePrezime;
        if (kvalifikacija < 1 || kvalifikacija > 10) {
            throw new IllegalArgumentException("Kvalifikacija mora biti u opsegu [1, 10]");
        }
        this.kvalifikacija = kvalifikacija;
        this.licenciran = true;
    }

    public String getImePrezime() {
        return imePrezime;
    }

    public int getKvalifikacija() {
        return kvalifikacija;
    }

    public boolean isLicenciran() {
        return licenciran;
    }

    public void oduzmiLicencu() {
        licenciran = false;
    }

    @Override
    public String toString() {
        return imePrezime;
    }

    @Override
    public int compareTo(Arheolog a) {
        return imePrezime.compareTo(a.imePrezime);
    }
}
