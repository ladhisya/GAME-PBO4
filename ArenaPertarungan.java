import java.util.Scanner;

public class ArenaPertarungan {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Musuh[] gelombangMonster = new Musuh[4];
        gelombangMonster[0] = new Slime();
        gelombangMonster[1] = new Naga();
        gelombangMonster[2] = new Slime();
        gelombangMonster[3] = new Zombie();

        System.out.println("=====================================");
        System.out.println(" ARENA RPG: GELOMBANG MONSTER ");
        System.out.println("=====================================\n");
        System.out.println("AWAS! Sekelompok monster menghadang Anda!");

        boolean isBermain = true;

        while (isBermain) {
            System.out.println("\n--- STATUS MONSTER ---");

            for (int i = 0; i < gelombangMonster.length; i++) {
                if (gelombangMonster[i].healthPoint > 0) {
                    System.out.println((i + 1) + ". " + gelombangMonster[i].namaMusuh + " (HP: " + gelombangMonster[i].healthPoint + ")");
                } else {
                    System.out.println((i + 1) + ". " + gelombangMonster[i].namaMusuh + " (TEWAS)");
                }
            }

            System.out.println("5. Kabur dari pertarungan");
            System.out.print("\nPilih target monster yang ingin diserang (1/2/3/4) atau 5 untuk kabur: ");

            try {
                int pilihanTarget = input.nextInt();

                if (pilihanTarget == 5) {
                    System.out.println("Anda lari terbirit-birit dari arena...");
                    isBermain = false;
                    continue;
                }

                if (pilihanTarget < 1 || pilihanTarget > 4) {
                    System.out.println("Pilihan tidak valid! Anda membuang giliran.");
                    continue;      
                }

                int indeksMonster = pilihanTarget - 1;

                if (gelombangMonster[indeksMonster].healthPoint <= 0) {
                    throw new TargetMatiException("Tindakan ilegal: Anda tidak bisa menyerang monster yang sudah mati!");
                }

                System.out.println("Masukkan kekuatan serangan Anda (10 - 100): ");
                int power = input.nextInt();

                if (power < 10 || power > 100) {
                    throw new SeranganTidakValidException("Kekuatan serangan harus di antara 10 sampai 100!");
                }

                System.out.println("\n>>> HASIL SERANGAN ANDA <<<");
                gelombangMonster[indeksMonster].terimaDamage(power);

                if (gelombangMonster[indeksMonster].healthPoint <= 0) {
                    System.out.println(gelombangMonster[indeksMonster].namaMusuh + " berhasil dikalahkan!");
                    
                    if (gelombangMonster[indeksMonster] instanceof BisaLoot) {
                        BisaLoot monsterLoot = (BisaLoot) gelombangMonster[indeksMonster];
                        monsterLoot.jatuhkanItem();
                    }
                }

                System.out.println("\n<<< GILIRAN MONSTER MEMBALAS >>>");
                for (int i = 0; i < gelombangMonster.length; i++) {
                    if (gelombangMonster[i].healthPoint > 0) {
                        Musuh monsterAktif = gelombangMonster[i];
                        monsterAktif.suaraKhas();

                        if (monsterAktif instanceof BisaTerbang) {
                            System.out.println("[PERINGATAN! SERANGAN UDARA TERDETEKSI]");
                            BisaTerbang monsterTerbang = (BisaTerbang) monsterAktif;
                            monsterTerbang.lepasLandas();
                            monsterTerbang.seranganUdara();
                        } else { 
                            monsterAktif.serangPemain();
                        }
                    }
                }
            } 
            catch (java.util.InputMismatchException e) {
                System.out.println("ERROR INPUT: Anda harus memasukkan ANGKA!");
                input.nextLine(); 
            } 
            catch (TargetMatiException e) {
                System.out.println("KESALAHAN GAME: " + e.getMessage());
            } 
        
            catch (SeranganTidakValidException e) {
                System.out.println("KESALAHAN GAME: " + e.getMessage());
            }
            catch (Exception e) {
                System.out.println("Terjadi kesalahan sistem: " + e.getMessage());
            }

            System.out.println("\n--------------------------------");
            
            boolean semuaMati = true;
            for (int i = 0; i < gelombangMonster.length; i++) {
                if (gelombangMonster[i].healthPoint > 0) {
                    semuaMati = false;
                    break;
                }
            }
            
            if (semuaMati) {
                System.out.println("\nSELAMAT! Anda telah menyapu bersih gelombang monster ini!");
                isBermain = false;
            }
        } 

        input.close(); 
        System.out.println("\nPermainan Berakhir!");
    } 
}