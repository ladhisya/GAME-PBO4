import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class ArenaPertarunganDinamis {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        ArrayList<Musuh> gelombangMonster = new ArrayList<>();
        gelombangMonster.add(new Slime());
        gelombangMonster.add(new Naga());
        gelombangMonster.add(new Slime());
        gelombangMonster.add(new Zombie());

        System.out.println("=====================================");
        System.out.println(" ARENA RPG: GELOMBANG MONSTER ");
        System.out.println("=====================================\n");
        System.out.println("AWAS! Sekelompok monster menghadang Anda!");

        boolean isBermain = true;

        while (isBermain && !gelombangMonster.isEmpty()) {

            System.out.println("\n--- STATUS MONSTER ---");

            for (int i = 0; i < gelombangMonster.size(); i++) {
                Musuh monster = gelombangMonster.get(i);

                System.out.println(
                    (i + 1) + ". " + monster.namaMusuh +
                    " (HP: " + monster.healthPoint + ")"
                );
            }

            System.out.println("\n----------------------");
            System.out.println("8. [SAVE GAME] Simpan progres pertarungan");
            System.out.println("9. [LOAD GAME] Muat progres sebelumnya");
            System.out.println("0. Kabur dari pertarungan");
            System.out.print("\nPilih target monster: ");

            try {
                int pilihanTarget = input.nextInt();

                if (pilihanTarget == 0) {

                    System.out.println("Anda lari terbirit-birit...");
                    isBermain = false;
                    continue;

                } else if (pilihanTarget == 8) {

                    try (ObjectOutputStream oos = new ObjectOutputStream(
                            new FileOutputStream("savegame_rpg.dat"))) {

                        oos.writeObject(gelombangMonster);

                        System.out.println(
                            ">>> BERHASIL: Game telah disimpan! <<<"
                        );

                    } catch (IOException e) {

                        System.out.println(
                            "GAGAL: Terjadi kesalahan saat menyimpan game: "
                            + e.getMessage()
                        );
                    }

                    continue;

                } else if (pilihanTarget == 9) {

                    try (ObjectInputStream ois = new ObjectInputStream(
                            new FileInputStream("savegame_rpg.dat"))) {

                        gelombangMonster =
                            (ArrayList<Musuh>) ois.readObject();

                        System.out.println(
                            ">>> BERHASIL: Game telah dimuat! <<<"
                        );

                    } catch (FileNotFoundException e) {

                        System.out.println(
                            "GAGAL: Tidak ada file save yang ditemukan."
                        );

                    } catch (IOException | ClassNotFoundException e) {

                        System.out.println(
                            "GAGAL: Terjadi kesalahan saat memuat game: "
                            + e.getMessage()
                        );
                    }

                    continue;
                }

                if (pilihanTarget < 1 ||
                    pilihanTarget > gelombangMonster.size()) {

                    System.out.println("Pilihan tidak valid!");
                    continue;
                }

                int indeksMonster = pilihanTarget - 1;
                Musuh target = gelombangMonster.get(indeksMonster);

                System.out.print(
                    "Masukkan kekuatan serangan Anda (10 - 100): "
                );

                int power = input.nextInt();

                if (power < 10 || power > 100) {

                    throw new SeranganTidakValidException(
                        "Kekuatan serangan harus di antara 10 sampai 100!"
                    );
                }

                System.out.println("\n>>> HASIL SERANGAN ANDA <<<");

                target.terimaDamage(power);

                if (target.healthPoint <= 0) {

                    System.out.println(
                        target.namaMusuh + " hancur menjadi debu!"
                    );

                    if (target instanceof BisaLoot) {

                        BisaLoot loot = (BisaLoot) target;
                        loot.jatuhkanItem();
                    }

                    gelombangMonster.remove(indeksMonster);
                }

            } catch (Exception e) {

                System.out.println(
                    "Terjadi kesalahan input, silahkan coba lagi."
                );

                input.nextLine();
                continue;
            }

            if (gelombangMonster.isEmpty()) {

                System.out.println(
                    "\nSELAMAT! Semua monster telah dibersihkan dari arena!"
                );

                break;
            }

            System.out.println("\n<<< GILIRAN MONSTER MEMBALAS >>>");

            for (int i = 0; i < gelombangMonster.size(); i++) {

                Musuh monsterAktif = gelombangMonster.get(i);

                monsterAktif.suaraKhas();

                if (monsterAktif instanceof BisaTerbang) {

                    System.out.println(
                        "[PERINGATAN! SERANGAN UDARA TERDETEKSI]"
                    );

                    BisaTerbang monsterTerbang =
                        (BisaTerbang) monsterAktif;

                    monsterTerbang.lepasLandas();
                    monsterTerbang.seranganUdara();

                } else {

                    monsterAktif.serangPemain();
                }

                System.out.println("\n--------------------------------");
            }
        }

        boolean semuaMati = true;

        for (int i = 0; i < gelombangMonster.size(); i++) {

            if (gelombangMonster.get(i).healthPoint > 0) {

                semuaMati = false;
                break;
            }
        }

        if (semuaMati) {

            System.out.println(
                "\nSELAMAT! Anda telah menyapu bersih gelombang monster ini!"
            );
        }

        input.close();

        System.out.println("\nPermainan Berakhir!");
    }
}