package resenje;

public enum MuzickiZanr {
    POP, ROK, REP;

    public static <T>  MuzickiZanr odrediZanr(T arg) {
        if (arg instanceof Integer) {
            return switch ((Integer) arg) {
                case 0 -> POP;
                case 1 -> ROK;
                case 2 -> REP;
                default -> throw new IllegalArgumentException();
            };
        } else if (arg instanceof String) {
            return switch ((String) arg) {
                case "pop" -> POP;
                case "rok" -> ROK;
                case "rep" -> REP;
                default -> throw new IllegalArgumentException();
            };
        }
        throw new IllegalArgumentException();
    }

    public static String prevediNaEngleski(MuzickiZanr zanr) {
        return switch (zanr) {
            case POP -> "POP";
            case ROK -> "ROCK";
            case REP -> "RAP";
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case POP -> "POP";
            case ROK -> "ROK";
            case REP -> "REP";
        };
    }
}
