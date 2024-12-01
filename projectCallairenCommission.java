
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class projectCallairenCommission {
    static ArrayList<String> semuadata = new ArrayList<>(); 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Selamat datang di Callairen Commission!");
        System.out.print("Masukkan nickname: ");
        String nickname = scanner.nextLine();
        System.out.print("Masukkan nomor telepon: ");
        String noTelp = scanner.nextLine();
        System.out.print("Masukkan email: ");
        String email = scanner.nextLine();

        //ingfo admin 
        if (nickname.equalsIgnoreCase("admin") && noTelp.equals("699116") && email.equalsIgnoreCase("ren@gmail.com")) {
            System.out.println("\n--- Data Semua Pesanan Pembeli ---");
            if (semuadata.isEmpty()) {
                System.out.println("Belum ada pesanan yang tersimpan.");
            } else {
                for (String pesanan : semuadata) {
                    System.out.println(pesanan);
                }
            }
            return;
        }

       
        int totalHarga = 0;
        ArrayList<String> detailPesanan = new ArrayList<>();
        boolean menambahPesanan = true;

        while (menambahPesanan) {
            try {
                
                System.out.println("\nKatalog Produk:");
                int[] pengaliKarakter = {100, 150, 200};
                System.out.println("Kamu mau order berapa karakter?");
                System.out.println("1. 1 karakter (100%)");
                System.out.println("2. 2 karakter (150%)");
                System.out.println("3. 3 karakter (200%)");
                int pilihanKarakter = scanner.nextInt();
                if (pilihanKarakter < 1 || pilihanKarakter > 3) {
                    System.out.println("Pilihan tidak valid!");
                    continue; 
                }

                //pakeetttt
                System.out.println("\nPilih paket:");
                String[] paket = {
                    "Headshot (FREE 2 ICON) 40K",
                    "Bust Up (FREE 3 ICON) 50K",
                    "Half Body (FREE 4 ICON) 75K",
                    "Thigh Up (FREE 5 ICON) 100K",
                    "Full Body (FREE 6 ICON) 150K",
                    "Chibi Half Body (FREE 1 ICON) 25K",
                    "Chibi Full Body (FREE 2 ICON) 35K",
                    "ICON (Buy 3 GET 1 FREE) 15K/pcs"
                };
                int[] hargaPaket = {40000, 50000, 75000, 100000, 150000, 25000, 35000, 15000};
                for (int i = 0; i < paket.length; i++) {
                    System.out.println((i + 1) + ". " + paket[i]);
                }
                System.out.print("Pilih paket: ");
                int pilihanPaket = scanner.nextInt();
                if (pilihanPaket < 1 || pilihanPaket > paket.length) {
                    System.out.println("Pilihan tidak valid!");
                    continue; 
                }
                int hargaDasar = hargaPaket[pilihanPaket - 1];
                int hargaAkhir = hargaDasar * pengaliKarakter[pilihanKarakter - 1] / 100;
                totalHarga += hargaAkhir;

               
                String pesanbaru = (detailPesanan.size() + 1) + ". (" + pilihanKarakter + " karakter)\n   " + 
                                            paket[pilihanPaket - 1] + " - " + hargaAkhir;
                detailPesanan.add(pesanbaru);

                
                System.out.println("\nApakah Anda ingin menambah orderan? (1. ya/ 2. tidak)");
                scanner.nextLine(); 
                String tambahOrder = scanner.nextLine();
                if (!tambahOrder.equalsIgnoreCase("1")) {
                    menambahPesanan = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid! Silakan masukkan angka yang benar.");
                scanner.nextLine(); 
            }
        }

        //metode bayar
        boolean metodePembayaranValid = false;
        int pilihanPembayaran = 0;

        while (!metodePembayaranValid) {
            try {
                System.out.println("\nBayarnya mau pakai aplikasi apa?");
                String[] metodePembayaran = {
                    "Gopay (+2K)", "OVO (+2K)", "Shopeepay (+2K)",
                    "Flip", "Transfer Bank - BCA", "Transfer Bank - Mandiri"
                };
                for (int i = 0; i < metodePembayaran.length; i++) {
                    System.out.println((i + 1) + ". " + metodePembayaran[i]);
                }
                System.out.print("Pilih metode pembayaran: ");
                pilihanPembayaran = scanner.nextInt();
                if (pilihanPembayaran < 1 || pilihanPembayaran > metodePembayaran.length) {
                    System.out.println("Pilihan tidak valid! Silakan coba lagi.");
                } else {
                    metodePembayaranValid = true; 
                }
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid! Silakan masukkan angka yang benar.");
                scanner.nextLine(); 
            }
        }

        
        if (pilihanPembayaran <= 3) {
            totalHarga += 2000;
        }

        
        LocalDateTime sekarang = LocalDateTime.now();
        LocalDateTime batasWaktuPembayaran = sekarang.plusHours(6);

        LocalDateTime deadlinePengerjaan = hitungDeadline(sekarang, detailPesanan.size());

    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String waktubeli = sekarang.format(formatter);
        String batasbayar = batasWaktuPembayaran.format(formatter);
        String deadlinekerja = deadlinePengerjaan.format(formatter);

        
        String ringkasanPesanan = "Nama: " + nickname + "\n" +
                                   "Nomor Telepon: " + noTelp + "\n" +
                                   "Email: " + email + "\n" +
                                   "Waktu Pembelian: " + waktubeli + "\n" +
                                   "Detail Pesanan:\n";
        for (String detail : detailPesanan) {
            ringkasanPesanan += detail + "\n";
        }
        ringkasanPesanan += "Total Harga Akhir: Rp.  " + totalHarga + "\n" +
                            "Deadline Pengerjaan: " + deadlinekerja + "\n";
        semuadata.add(ringkasanPesanan);

        //part terakhir
        System.out.println("\n======================== NOTA PEMBELIAN ========================");
        System.out.println(ringkasanPesanan);
        System.out.println("Konfirmasi pembayaran sebelum: " + batasbayar);
        System.out.println("================================================================");
        System.out.println("\nMakasih ya kak udah jajan disini!");
    }

    
    public static LocalDateTime hitungDeadline(LocalDateTime tanggalMulai, int jumlahItem) {
        return tanggalMulai.plusDays(jumlahItem * 3); // Tambahkan 3 hari per item
    }
}


