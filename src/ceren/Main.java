import java.util.ArrayList;
import java.util.Random;

public class Main {
    public enum Yonler {
        güney(30),
        dogu(20),
        batı(10),
        kuzey(0);

        private int fiyat;

        public int fiyatSoyle() {
            return this.fiyat;
        }

        private Yonler(int f) {
            this.fiyat = f;
        }

        public static Yonler rastgeleOdaYonGetir() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];

        }

    }

    public enum Tipler {
        klasik(0),
        bungalov(50),
        AgacEv(90);
        private int fiyat;

        private Tipler(int f) {
            this.fiyat = f;
        }

        public static Tipler rastgeleOdaTipGetir() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];

        }

        public int fiyatSoyle() {
            return this.fiyat;
        }
    }

    public enum OdaDurumu {
        dolu,
        bos,
        bakımda,
        kirli;

        public static OdaDurumu rastgeleOdaDurumuGetir() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];

        }
    }

    public enum OdaTuru {
        tekKisilikSingle(100),
        ciftKisilikDouble(200),
        doubleSuit(500);

        private int fiyat;

        private OdaTuru(int f) {
            this.fiyat = f;
        }

        public static OdaTuru rastgeleOdaGetir() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];

        }

        public int fiyatSoyle() {
            return this.fiyat;
        }
    }

    public static class Otel {
        private int kasa = 0;
        private String otelAdi;
        private ArrayList<Oda> odalar = new ArrayList<>();
        private ArrayList<Calisan> calisanlar = new ArrayList<>();


        private Otel(String otelAdi, int odaSayisi) {
            this.otelAdi = otelAdi;
            otelDagit(odaSayisi);
            calisanOlustur();
        }

        public int getKasa() {
            return kasa;
        }

        public void setKasa(int kasa) {
            this.kasa += kasa;
        }

        public void calisanOlustur() {
            //ArrayList calısanlarımın isimlerini ve maaslarını oluşturdum.
            calisanlar.add(new Calisan("Pelin Sönmez", 2200));
            calisanlar.add(new Calisan("Helin Yeğit", 2200));
            calisanlar.add(new Calisan("Beyza Çakır", 2200));
            calisanlar.add(new Calisan("Esra Güngör", 2200));
        }

        public int odaSayisiKac() {
            return odalar.size();
        }

        public void otelDagit(int odaSayisi) {
            //for döngüsüyle oda sayimizi ve numaralarımizi belirledim.
            //ratgele oda oluşturdum daha önceden oluşturduğum methodları çağırarak.
            for (int i = 1; i <= odaSayisi; i++) {
                Oda rastgeleOda = new Oda(Yonler.rastgeleOdaYonGetir(), Tipler.rastgeleOdaTipGetir(), OdaDurumu.rastgeleOdaDurumuGetir(), OdaTuru.rastgeleOdaGetir(), i);
                odalar.add(rastgeleOda);
                if (rastgeleOda.odaDurumuNe() == OdaDurumu.dolu) {
                    setKasa(rastgeleOda.toplamTutar());
                    rastgeleOda.setSahip(new Musteri("Bilinmiyor", 0));
                }
            }
        }

        public Oda odaGetir(OdaTuru tur, Yonler yon, Tipler tip) {
            //İstenilen özelliklere göre bulunan odayı geri döndürüyor
            Oda bulunanOda = null;
            tur = (tur == null) ? OdaTuru.rastgeleOdaGetir() : tur;
            yon = (yon == null) ? Yonler.rastgeleOdaYonGetir() : yon;
            tip = (tip == null) ? Tipler.rastgeleOdaTipGetir() : tip;

            for (Oda oda : odalar) {
                if (oda.odaTuruNe() == tur && oda.odaTipinNe() == tip && oda.odaYonunNe() == yon && oda.odaBosMu()) {
                    bulunanOda = oda;
                }
            }
            return bulunanOda;
        }

        public void odaVer(Musteri musteri, Oda oda) {
//            müşteriye oda ataması
//            Odaya sahip ataması
//            Odanın durumunu dolu olarak değiştirilmesi ve odanın fiyatı kadar kasaya para aktarılma işlemeleri yapılıyor
            musteri.setOda(oda);
            oda.setSahip(musteri);
            this.setKasa(oda.toplamTutar());
            oda.setOdaDurumu(OdaDurumu.dolu);
        }

        public void odaCik(Musteri musteri, Oda oda) {
            //oda sahibi boş ve oda durumu boş
            //ODanın durumu kirli olarak değiştiriliyor.
            musteri.setOda(null);
            oda.setSahip(null);
            oda.setOdaDurumu(OdaDurumu.kirli);
        }

        public int doluOdaSayisi() {
            //Dolu oda sayilarını 1 arttırarak döndürüyor.
            int sayi = 0;
            for (Oda oda : odalar) {
                if (oda.getOdaDurumu() == OdaDurumu.dolu) {
                    sayi++;
                }
            }
            return sayi;
        }

        public String dolulukOrani() {
            return otelAdi + " : Otelimiz %" + ((doluOdaSayisi() * 100) / odaSayisiKac()) + " oranında doludur.";

        }

        public void odaRaporla(OdaTuru tur, Yonler yon, Tipler tip, OdaDurumu durum) {
            //İstenilen özelliklere göre odaları listeliyor.
            tur = (tur == null) ? OdaTuru.rastgeleOdaGetir() : tur;
            yon = (yon == null) ? Yonler.rastgeleOdaYonGetir() : yon;
            tip = (tip == null) ? Tipler.rastgeleOdaTipGetir() : tip;
            durum = (durum == null) ? OdaDurumu.rastgeleOdaDurumuGetir() : durum;

            for (Oda oda : odalar) {
                if (oda.odaTuruNe() == tur && oda.odaTipinNe() == tip && oda.odaYonunNe() == yon && oda.odaDurumuNe() == durum) {
                    System.out.println(oda.getOdaNumarasi() + " Nolu oda " + oda.toplamTutar() + " Tl'dir.");
                    System.out.println("\t Cephe : " + oda.odaYonunNe());
                    System.out.println("\t Tipi : " + oda.odaTipinNe());
                    System.out.println("\t Durumu : " + oda.odaDurumuNe());
                    System.out.println("\t Sahibi : " + (oda.sahibinKim() == null ? "yok" : oda.sahibinKim().adSoyadNe()));
                }
            }
        }

        public void maasVer() {
            //Çalışana maas veriliyor ve kasadan para düşülüyor.
            for (Calisan calisan : calisanlar) {
                if (this.kasa >= calisan.getMaas()) {
                    calisan.maasAl(calisan.getMaas());
                    this.kasa -= calisan.getMaas();
                }

            }

        }
    }

    public static class Oda {
        private OdaTuru odaTuru;
        private Yonler odaYonu;
        private Tipler odaTipi;
        private OdaDurumu odaDurumu;
        private Musteri sahip;
        private int odaNumarasi;

        public Oda(Yonler odaYonu, Tipler odaTipi, OdaDurumu odaDurumu, OdaTuru odaTur, int odaNo) {
            this.odaTipi = odaTipi;
            this.odaYonu = odaYonu;
            this.odaDurumu = odaDurumu;
            this.odaTuru = odaTur;
            this.odaNumarasi = odaNo;
            this.sahip = null;
        }

        public Yonler odaYonunNe() {
            return odaYonu;
        }

        public void setOdaYonu(Yonler odaYönleri) {
            this.odaYonu = odaYönleri;
        }

        public Tipler odaTipinNe() {
            return odaTipi;
        }

        public void setOdaTipleri(Tipler odaTipi) {
            this.odaTipi = odaTipi;
        }

        public OdaDurumu odaDurumuNe() {
            return odaDurumu;
        }

        public OdaDurumu getOdaDurumu() {
            return odaDurumu;
        }

        public void setOdaDurumu(OdaDurumu odaDurumu) {
            this.odaDurumu = odaDurumu;
        }

        public OdaTuru odaTuruNe() {
            return odaTuru;
        }

        public void setOdaTuru(OdaTuru odaTuru) {
            this.odaTuru = odaTuru;
        }

        public int odaNumarasiKac() {
            return odaNumarasi;
        }

        public int getOdaNumarasi() {
            return odaNumarasi;
        }

        public void setOdaNumarasi(int odaNumarasi) {
            this.odaNumarasi = odaNumarasi;
        }

        public boolean odaBosMu() {
            if (odaDurumu == OdaDurumu.bos) {
                return true;
            }
            return false;
        }

        public int toplamTutar() {
            return odaTipi.fiyatSoyle() + odaYonu.fiyatSoyle() + odaTuru.fiyatSoyle();
        }

        public Musteri sahibinKim() {
            return sahip;
        }

        public void setSahip(Musteri musteri) {
            this.sahip = musteri;
        }
    }

    public static class Insan {
        private String adSoyad;
        private int bakiye;

        public Insan(String adSoyad, int bakiye) {
            this.adSoyad = adSoyad;
            this.bakiye = bakiye;
        }

        public String adSoyadNe() {
            return adSoyad;
        }

        public boolean paranYetiyorMu(int miktar) {
            return bakiye >= miktar;
        }

        public boolean paraHarca(int miktar) {
            if (paranYetiyorMu(miktar)) {
                bakiye -= miktar;
                return true;
            } else {
                return false;
            }
        }

        public void adSoyadDegis(String adSoyad) {
            this.adSoyad = adSoyad;
        }

        public void paraKazan(int miktar) {
            this.bakiye += miktar;
        }
    }

    public static class Calisan extends Insan {
        private int maas;
        private int cuzdan = 0;

        public Calisan(String adSoyad, int bakiye, int maas) {
            super(adSoyad, bakiye);
            this.maas = maas;
        }

        public Calisan(String adSoyad, int maas) {
            super(adSoyad, 0);
            this.maas = maas;
        }

        public int getMaas() {
            return maas;
        }

        public void maasAl(int cuzdan) {
            this.cuzdan += cuzdan;
        }

        public int getCuzdan() {
            return cuzdan;
        }

        public void odaTemizle(Oda oda) {
            //Oda durumu kirliden boş durumuna getiriliyor.
            if (oda.getOdaDurumu() == OdaDurumu.kirli) {
                oda.setOdaDurumu(OdaDurumu.bos);
            }
        }

        public void odaOnar(Oda oda) {
            //Oda durumu bakımdadan  boş durumuna getiriliyor.
            if (oda.getOdaDurumu() == OdaDurumu.bakımda) {
                oda.setOdaDurumu(OdaDurumu.bos);
            }
        }

    }

    public static class Musteri extends Insan {
        private Oda oda = null;

        public Musteri(String adSoyad, int bakiye) {
            super(adSoyad, bakiye);
        }

        public Oda getOda() {
            return oda;
        }

        public void setOda(Oda oda) {
            this.oda = oda;
        }

        public void odaTut(Otel otel, OdaTuru tur, Yonler yon, Tipler tip) {
            //toplam tutara parası yetiyorsa oda tutuluyor.
            Oda oda = otel.odaGetir(tur, yon, tip);
            if (oda != null) {
                if (paraHarca(oda.toplamTutar())) {
                    otel.odaVer(this, oda);
                    System.out.println("Oda tutuldu oda numaranız : " + oda.odaNumarasiKac());
                } else {
                    System.out.println("Malesef paranız yetersiz ");
                }
            } else {
                System.out.println("Aradığınız kriterlere göre oda bulunamamıştır.");
            }
        }

        public void oteldenAyril(Otel otel) {
            otel.odaCik(this, oda);
        }
    }

    public static void main(String[] args) {
        Otel rixosOtel = new Otel("rixos", 100);
        Musteri cerenOZTURK = new Musteri("Ceren Öztürk", 25000);
        Musteri esraGUNGOR = new Musteri("Esra GÜNGÖR", 2000);

        System.out.println("dolu oda sayısı : " + rixosOtel.doluOdaSayisi());
        System.out.println("otel kasa durumu : " + rixosOtel.getKasa());

        cerenOZTURK.odaTut(rixosOtel, OdaTuru.doubleSuit, Yonler.güney, Tipler.AgacEv);
        esraGUNGOR.odaTut(rixosOtel, null, Yonler.güney, null);

        System.out.println("dolu oda sayısı : " + rixosOtel.doluOdaSayisi());
        System.out.println("otel kasa durumu : " + rixosOtel.getKasa());

        System.out.println("------------------------------------------");
        rixosOtel.odaRaporla(null, null, null, null);
        rixosOtel.maasVer();
    }
}