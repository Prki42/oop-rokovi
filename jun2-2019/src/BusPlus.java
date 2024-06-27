import java.util.Optional;

public abstract class BusPlus {
    private int id;
    private int zona;

    public BusPlus(int id, int zona) {
        this.id = id;
        if (zona != 1 && zona != 2) {
            throw new IllegalArgumentException("Zona mora biti 1 ili 2");
        }
        this.zona = zona;
    }

    public int getId() {
        return id;
    }

    public int getZona() {
        return zona;
    }

    @Override
    public String toString() {
        return "zona " + zona;
    }

    // Nepotrebno
    static public BusPlus parse(String s) {
        String[] params = s.split(",\\s+");
        if (params.length != 5) {
            throw new IllegalArgumentException("Invalid format: not enough parameters");
        }
        BusPlus ret;
        int id;
        try {
            id = Integer.parseInt(params[1]);
        } catch (NumberFormatException _) {
            throw new IllegalArgumentException("Invalid format: id must be an integer");
        }
        int zona;
        try {
            zona = Integer.parseInt(params[2]);
        } catch (NumberFormatException _) {
            throw new IllegalArgumentException("Invalid format: zona must be an integer");
        }
        boolean isPersonalizovana = false;
        if (params[0].equals("P")) {
            isPersonalizovana = true;
        } else if (!params[0].equals("N")) {
            throw new IllegalArgumentException("Invalid format: card indicator must be either P or N");
        }
        Kategorija kategorija = null;
        int kredit = 0;
        switch (params[3]) {
            case "P2": kategorija = Kategorija.P2; break;
            case "A1": kategorija = Kategorija.A1; break;
            case "P3": kategorija = Kategorija.P3; break;
            case "P13": kategorija = Kategorija.P13; break;
            default: {
                if (isPersonalizovana) {
                    throw new IllegalArgumentException("Invalid format: kategorija not valid");
                }
                try {
                    kredit = Integer.parseInt(params[3]);
                } catch (NumberFormatException _) {
                    throw new IllegalArgumentException("Invalid format: kredit must be an integer");
                }
            }
        }

        boolean imaDopunu = false;
        boolean jeOcitana = false;
        if (isPersonalizovana) {
            switch (params[4]) {
                case "da": imaDopunu = true; break;
                case "ne" : imaDopunu = false; break;
                default: throw new IllegalArgumentException("Invalid format: ");
            }
        } else {
            switch (params[4]) {
                case "+": jeOcitana = true; break;
                case "-": jeOcitana = false; break;
                default: throw new IllegalArgumentException("Invalid format: ");
            }
        }

        try {
            if (isPersonalizovana) {
                ret = new Personalizovana(id, zona, kategorija, imaDopunu);
            } else {
                ret = new Nepersonalizovana(id, zona, kredit, jeOcitana);
            }
            return ret;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid format: " + e.getMessage());
        }
    }
}
