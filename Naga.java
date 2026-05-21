public class Naga extends Musuh implements BisaTerbang, BisaLoot {
    public Naga() {
        super("Naga Durin", 500);
    }

@Override
public void suaraKhas() {
    System.out.println(this.namaMusuh + ": BRRRAHHH YESSIR");
}

@Override
public void serangPemain() {
    System.out.println(this.namaMusuh + " menyemburkan nafas api dari udara! Player -50 HP");
}

@Override
public void lepasLandas() {
    System.out.println(this.namaMusuh + " terbang tinggi! Sulit diserang.");
}

@Override
public void seranganUdara() {
    System.out.println(this.namaMusuh + " mmenyemburkan badai api! Pemain -80 HP.");
}

@Override
public void jatuhkanItem() {
    System.out.println(this.namaMusuh + " Menjatuhkan bola Api.");
}
}