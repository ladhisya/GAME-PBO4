public class Slime extends Musuh implements BisaLoot {
    public Slime() {
        super("Slime Api", 50);
    }

@Override
public void suaraKhas() {
    System.out.println(this.namaMusuh + ": BLUUUB BLUUUB SKRRRR");
}

@Override
public void serangPemain() {
    System.out.println(this.namaMusuh + " melompat dan menyiram cairan lava! Player -15 HP");
}

@Override
    public void jatuhkanItem() {
        System.out.println(this.namaMusuh + " Slime menjatuhkan lava kristal!");
    }

}
