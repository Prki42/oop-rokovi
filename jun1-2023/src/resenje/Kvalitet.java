package resenje;

public enum Kvalitet {
    LOS(1), OSREDNJI(2), VRHUNSKI(3);
    private int celobrojnaVred;

    Kvalitet(int kvalitet) {
        if (kvalitet < 1 || kvalitet > 3) {
            throw new IllegalArgumentException("Kvalitet mora biti izmedju 1 i 3.");
        }
        this.celobrojnaVred = kvalitet;
    }

    public static Kvalitet createFromInt(int kvalitet) {
        try {
            return Kvalitet.values()[kvalitet - 1];
        }
        catch (Exception _) {
            throw new IllegalArgumentException("Kvalitet mora biti izmedju 1 i 3.");
        }
    }

    public int getCelobrojnaVred() {
        return celobrojnaVred;
    }

    @Override
    public String toString() {
        return (switch (this) {
            case LOS -> "LOS";
            case OSREDNJI -> "OSREDNJI";
            case VRHUNSKI -> "VRHUNSKI";
        });
    }
}
