import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
public class Automobile extends Persona implements Serializable {
    Persona p;
    private String targa;
    private String codFiscProp;
    private Date dataImm;
    private static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public Automobile(Persona p, String targa, Date dataImm)
    {
        super(p.getCodFiscale(), p.getNome(), p.getCognome(), p.getDataNascita());
        this.p = p;
        this.targa = targa;
        this.codFiscProp = p.getCodFiscale();
        this.dataImm = dataImm;
    }

    public String getTarga()
    {
        return targa;
    }

    public void setTarga(String targa)
    {
        this.targa = targa;
    }

    public String getCodFiscProp()
    {
        return codFiscProp;
    }

    public void setCodFiscProp(String codFiscProp)
    {
        this.codFiscProp = codFiscProp;
    }

    public Date getDataImm()
    {
        return dataImm;
    }

    public void setDataImm(Date dataImm)
    {
        this.dataImm = dataImm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getTarga()!=this.getTarga()) return false;
        Automobile that = (Automobile) o;
        return Objects.equals(targa, that.targa) ;
    }

    @Override
    public String toString() {
        return "Automobile di " + p.getNome()  + " " + p.getCognome() +
                "\ntarga = " + targa +
                " | codFiscProp = " + codFiscProp +
                " | dataImm = " + df.format(dataImm) + "\nPROPRIETARIO/A DELL'AUTO:\n" + p.toString();
    }

    public Persona getP()
    {
        return p;
    }

    public static ArrayList<Automobile> LeggiAutomobili (File filename) {
        while(true)
        {
            try {
                FileInputStream fis = new FileInputStream(filename);
                ObjectInputStream ois = new ObjectInputStream(fis);
                System.out.println("File esistente\n");
                TestIncidenti.Automobili = (ArrayList<Automobile>) ois.readObject();
                for(Automobile r : TestIncidenti.Automobili)
                {
                    System.out.println(r + "\n");
                }
                ois.close();
                break;
            } catch (FileNotFoundException e)
            {
                System.out.println("File non trovato, creazione del nuovo file importando dati da Automobili.txt\n");
                try {
                    filename.createNewFile();
                    FileReader fr = new FileReader("Automobili.txt");
                    BufferedReader br = new BufferedReader(fr);
                    FileOutputStream fos = new FileOutputStream(filename, true);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    String line;
                    while((line=br.readLine())!=null)
                    {
                        String [] s = line.split(" ");
                        System.out.println("la stringa è: " + line);
                        for(Persona p : TestIncidenti.Persone)
                        {
                            if(p.getCodFiscale().equals(s[0]))
                            {
                                Automobile a = new Automobile(p, s[1], df.parse(s[2]));
                                TestIncidenti.Automobili.add(a);
                            }
                        }
                    }
                    oos.writeObject(TestIncidenti.Automobili);
                    System.out.println("\nInserite nel database\n");
                    oos.flush();
                    oos.close();
                } catch (IOException | ParseException ex)
                {
                    System.out.println("File non trovato o errore nel parse");
                    break;
                }
            } catch (ClassNotFoundException | IOException e)
            {
                System.out.println("Errore nel file");
                break;
            }
        }
        return TestIncidenti.Automobili;
    }
}
