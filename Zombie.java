public class Zombie extends Musuh implements BisaLoot {
        public Zombie() {
        super("Zombie Moshing", 150);
    }

@Override
public void suaraKhas() {
    System.out.println(this.namaMusuh + ": HIYAKKKK NYAMNYAM");
}

@Override
public void serangPemain() {
    System.out.println(this.namaMusuh + " menendang dan menggigit! Player -20 HP");
}

@Override
    public void jatuhkanItem() {
        System.out.println(this.namaMusuh + " zombie menjatuhkan Daging Busuk!");
    }
}

