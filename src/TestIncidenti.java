import java.io.*;
import java.text.*;
import java.util.*;

public class TestIncidenti
{
    public static ArrayList<Persona> Persone = new ArrayList<>();
    public static ArrayList<Automobile> Automobili = new ArrayList<>();
    public static ArrayList<Polizza> Polizze = new ArrayList<>();
    public static ArrayList<Incidente> Incidenti = new ArrayList<>();
    public static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public static Persona ControlloCodice(Scanner sc, int c)
    {
        while(true)
        {
            if(c==1)
            {
                System.out.println("Inserire codice fiscale del proprietario dell'auto: ");
            }
            else
            {
                System.out.println("Inserire codice fiscale dell'intestatario della polizza: ");
            }
            String codice = sc.next();
            for(Persona a : Persone)
            {
                if(a.getCodFiscale().equals(codice))
                {
                    return a;
                }
            }
            System.out.println("Codice fiscale inesistente");
            System.out.println("Azioni disponibili:");
            System.out.println("1 -> Aggiungere persona");
            System.out.println("2 -> Reinserire codice fiscale");
            System.out.println("3 -> Annulla inserimento");
            int i = sc.nextInt();
            switch (i) {
                case 1:
                    try {
                        System.out.print("Inserire nome: ");
                        String nome = sc.next();
                        System.out.print("Inserire cognome: ");
                        String cognome = sc.next();
                        System.out.print("Inserire data di nascita: ");
                        String dataNascita = sc.next();
                        Persona a = new Persona(codice, nome, cognome, df.parse(dataNascita));
                        return a;
                    } catch (ParseException e) {
                        System.out.println("Errore caricamento");
                    }
                    break;
                case 2:
                    break;
                case 3:
                    return null;
            }
        }
    }
    public static ArrayList<Persona> AggiungiPersona(Scanner sc, File filename)
    {
        try {
            System.out.print("Inserire codice fiscale: ");
            String codFiscale = sc.next();
            System.out.print("Inserire nome: ");
            String nome = sc.next();
            System.out.print("Inserire cognome: ");
            String cognome = sc.next();
            System.out.print("Inserire data di nascita: ");
            String dataNascita = sc.next();
            Persone.add(new Persona(codFiscale, nome, cognome, df.parse(dataNascita)));
            Collections.sort(Persone);
            System.out.println("Persona aggiunta con successo al database");
        } catch (ParseException e)
        {
            System.out.println("Errore caricamento");
        }
        return Persone;
    }

    public static ArrayList<Persona> RimuoviPersona(Scanner sc)
    {
        boolean b = false;
        System.out.print("Inserisci codice fiscale della persona da rimuovere: ");
        String codfisc = sc.next();
        for (Persona a : Persone) {
            if (a.getCodFiscale().equals(codfisc))
            {
                System.out.println("La persona in questione è: " + a);
                Persone.remove(a);
                System.out.println("Persona rimossa con successo.");
                b = true;
                break;
            }
        }
        if(!b)
        {
            System.out.println("Questo codice fiscale non è presente nel database.");
        }
        return Persone;
    }

    public static void AggiungiAutoPolizza(Scanner sc)
    {
        int c = 1;
        Persona p = ControlloCodice(sc, c);
        if(p!=null)
        {
            try {
                Persone.add(p);
                System.out.print("Inserire targa dell'auto: ");
                String targa = sc.next();
                System.out.print("Inserire data immatricolazione: ");
                String dataImm = sc.next();
                Automobile a = new Automobile(p, targa, df.parse(dataImm));
                c = 2;
                Persona p1 = ControlloCodice(sc, c);
                if(p1!=null)
                {
                    Automobili.add(a);
                    System.out.print("Inserire codice polizza: ");
                    String codicePolizza = sc.next();
                    System.out.print("Inserire data inizio polizza: ");
                    String dataInizio = sc.next();
                    System.out.print("Inserire data fine polizza: ");
                    String dataFine = sc.next();
                    System.out.print("Inserire premio assicurativo: ");
                    String premioAss = sc.next();
                    Polizze.add(new Polizza(p1, a, codicePolizza, df.parse(dataInizio), df.parse(dataFine), Double.parseDouble(premioAss)));
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void RegistraIncidente()
    {

    }

    public static void EstendiPolizza(Scanner sc)
    {
        System.out.println("Inserire numero polizza da estendere: ");
        String s = sc.next();
        for(Polizza pz : Polizze)
        {
            if(pz.getCodicePolizza().equals(s))
            {
                Calendar c1 = Calendar.getInstance();
                c1.setTime(pz.getDataIniz());
                c1.add(Calendar.YEAR, 1);
                pz.setDataIniz(c1.getTime());
                Calendar c2 = Calendar.getInstance();
                c2.setTime(pz.getDataFine());
                c2.add(Calendar.YEAR, 1);
                pz.setDataFine(c2.getTime());
                break;
            }

        }
    }
    public static void RimuoviAutoPolizza(Scanner sc)
    {
        System.out.println("Inserisci targa dell'auto da rimuovere");
        String s = sc.next();
        for(Automobile a : Automobili)
        {
            if(a.getTarga().equals(s))
            {
                for(Polizza pz : Polizze)
                {
                    if(pz.getA().equals(a))
                    {
                        Automobili.remove(a);
                        Polizze.remove(pz);
                        System.out.println("rimozione effettuata");
                        break;
                    }
                }
                break;
            }
        }
    }

    public static <T> void SalvaFile(ArrayList<T> array, File filename)
    {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(array);
            oos.flush();
            oos.close();
        } catch (IOException e)
        {
            System.out.println("File binario non esistente");
        }
    }
    public static void main(String[] args) {
        File filebinpers = new File("Persone.dat");
        File filebinauto = new File("Automobili.dat");
        File filebinpol = new File("Polizze.dat");
        //filebinpers.delete();
        //filebinauto.delete();
        //filebinpol.delete();
        Persone = Persona.LeggiPersone(filebinpers);
        Automobili = Automobile.LeggiAutomobili(filebinauto);
        Polizze = Polizza.LeggiPolizza(filebinpol);
        boolean b = true;
        do
        {
            System.out.println("\nPAGINA PRINCIPALE");
            System.out.println("Azioni disponibili: ");
            System.out.println("1 -> Aggiungi persona");
            System.out.println("2 -> Aggiungi automobile e rispettiva polizza");
            System.out.println("3 -> Registra incidente");
            System.out.println("4 -> Estendi polizza");
            System.out.println("5 -> Rimuovi persona");
            System.out.println("6 -> Rimuovi automobile e rispettiva polizza");
            System.out.print("Azione da eseguire: ");
            Scanner sc = new Scanner(System.in);
            int i = sc.nextInt();
            switch (i)
            {
                case 1:
                    Persone = AggiungiPersona(sc, filebinpers);
                    System.out.println("Stampa delle persone dopo l'operazione:");
                    for (Persona p : Persone)
                    {
                        System.out.println(p);
                    }
                    break;
                case 2:
                    AggiungiAutoPolizza(sc);
                    System.out.println("Stampa delle persone dopo l'operazione:");
                    for(Polizza pz : Polizze)
                    {
                        System.out.println(pz);
                    }
                    break;
                case 3:
                    RegistraIncidente();
                    break;
                case 4:
                    EstendiPolizza(sc);
                    break;
                case 5:
                    Persone = RimuoviPersona(sc);
                    System.out.println("Stampa delle persone dopo l'operazione:");
                    for (Persona p : Persone)
                    {
                        System.out.println(p);
                    }
                    break;
                case 6:
                    RimuoviAutoPolizza(sc);
                    break;
                case 9:
                    System.out.println("OP NULLA");
                    break;
            }
            System.out.println("\nVuoi effettuare qualche altra operazione? ");
            while(true)
            {
                String s1 = sc.next();
                if (s1.equals("no"))
                {
                    b=false;
                    break;
                }
                else
                {
                    if(s1.equals("si"))
                    {
                        break;
                    }
                    else
                    {
                        System.out.print("ERRORE\nInserire \"si\" o \"no\": ");
                    }
                }
            }
        }while(b);
        SalvaFile(Persone, filebinpers);
        SalvaFile(Automobili, filebinauto);
        SalvaFile(Polizze, filebinpol);
        System.out.println("Programma concluso con successo.");

    }
}