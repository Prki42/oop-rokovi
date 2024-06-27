public enum Kategorija {
    A1(0), P2(1), P3(2), P13(3);

    private final int code;

    Kategorija(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return switch (this) {
            case A1 -> "zaposleni";
            case P2 -> "srednjoskolci";
            case P3 -> "studenti";
            case P13 -> "penzioneri";
        };
    }

    public static Kategorija izBroja(int i) {
        return switch (i) {
            case 0 -> A1;
            case 1 -> P2;
            case 2 -> P3;
            case 3 -> P13;
            default -> throw new IllegalArgumentException("Mora biti broj izmedju 0 i 3");
        };
    }
}
