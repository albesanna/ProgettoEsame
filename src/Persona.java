import javax.xml.crypto.Data;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

public class Persona implements Comparable<Persona>, Serializable {
    private String codFiscale;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    public Persona(String codFiscale, String nome, String cognome, Date dataNascita)
    {
        this.codFiscale = codFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
    }

    public String getCodFiscale()
    {
        return codFiscale;
    }

    public void setCodFiscale(String codFiscale)
    {
        this.codFiscale = codFiscale;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getCognome()
    {
        return cognome;
    }

    public void setCognome(String cognome)
    {
        this.cognome = cognome;
    }

    public Date getDataNascita()
    {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita)
    {
        this.dataNascita = dataNascita;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getCodFiscale()!=this.getCodFiscale()) return false;
        Persona persona = (Persona) o;
        return Objects.equals(codFiscale, persona.codFiscale);
    }

    @Override
    public int compareTo(Persona o)
    {
        return this.cognome.compareTo(o.cognome);
    }

    @Override
    public String toString() {
        return "codFiscale = " + codFiscale +
                " | nome = " + nome +
                " | cognome = " + cognome +
                " | dataNascita = " + df.format(dataNascita);
    }
    public static ArrayList<Persona> LeggiPersone(File filename) {
        while(true)
        {
            try {
                FileInputStream fis = new FileInputStream(filename);
                ObjectInputStream ois = new ObjectInputStream(fis);
                System.out.println("File Esistente\n");
                TestIncidenti.Persone = (ArrayList<Persona>) ois.readObject();
                for(Persona a : TestIncidenti.Persone)
                {
                    System.out.println(a + "\n");
                }
                ois.close();
                break;
            } catch (FileNotFoundException e) {
                System.out.println("File non trovato, creazione del nuovo file importando dati da Persone.txt\n");
                try {
                    filename.createNewFile();
                    FileOutputStream fos = new FileOutputStream(filename, true);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    FileReader fr = new FileReader("Persone.txt");
                    BufferedReader br = new BufferedReader(fr);
                    String firstline;
                    while ((firstline = br.readLine()) != null) {
                        System.out.println("la stringa è: " + firstline);
                        String[] p = firstline.split(", ");
                        Persona e1 = new Persona(p[0], p[1], p[2], df.parse(p[3]));
                        TestIncidenti.Persone.add(e1);
                    }
                    Collections.sort(TestIncidenti.Persone);
                    oos.writeObject(TestIncidenti.Persone);
                    System.out.println("\nInserite nel database\n");
                    oos.flush();
                    oos.close();
                } catch (IOException | ParseException r) {
                    System.out.println("Persone.txt non esiste");
                    break;
                }
            } catch (ClassNotFoundException | IOException e) {
                System.out.println("Errore nel file");
                break;
            }
        }
        return TestIncidenti.Persone;
    }
}
