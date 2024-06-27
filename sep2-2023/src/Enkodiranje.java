public enum Enkodiranje {
    ASCII, UTF8, UTF16;

    public int velicinaEnkodiranjaKaraktera() {
        return switch (this) {
            case ASCII -> 1;
            case UTF8 -> 2;
            case UTF16 -> 4;
        };
    }
}
