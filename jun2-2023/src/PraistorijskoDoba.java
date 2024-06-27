public enum PraistorijskoDoba {
    KAMENO_DOBA, BRONZANO_DOBA, GVOZDENO_DOBA;

    public static PraistorijskoDoba odOznake(String oznaka) {
        return switch (oznaka) {
            case "KD" -> KAMENO_DOBA;
            case "BD" -> BRONZANO_DOBA;
            case "GD" -> GVOZDENO_DOBA;
            default -> throw new IllegalArgumentException(oznaka + " nije validno doba");
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case KAMENO_DOBA -> "KAMENO_DOBA";
            case BRONZANO_DOBA -> "BRONZANO_DOBA";
            case GVOZDENO_DOBA -> "GVOZDENO_DOBA";
        };
    }

}
