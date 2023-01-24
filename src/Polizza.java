import javax.xml.crypto.Data;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Polizza extends Automobile {
    Automobile a;
    Persona p;
    private String codicePolizza;
    private String targaAss;
    private String codFiscInt;
    private Date dataIniz;
    private Date dataFine;
    private double premioAss;
    private static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public Polizza(Persona p, Automobile a, String codicePolizza, Date dataIniz, Date dataFine, double premioAss)
    {
        super(a.getP(), a.getTarga(), a.getDataImm());
        this.a = a;
        this.p = p;
        this.codicePolizza = codicePolizza;
        this.targaAss = a.getTarga();
        this.codFiscInt = p.getCodFiscale();
        this.dataIniz = dataIniz;
        this.dataFine = dataFine;
        this.premioAss = premioAss;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getCodicePolizza()!=this.getCodicePolizza()) return false;
        Polizza polizza = (Polizza) o;
        return codicePolizza == polizza.codicePolizza;
    }


    public Persona getIntPol()
    {
        return p;
    }

    public void setP(Persona p)
    {
        this.p = p;
    }

    public Automobile getA()
    {
        return a;
    }

    public void setA(Automobile a) {
        this.a = a;
    }

    public String getCodicePolizza() {
        return codicePolizza;
    }

    public void setCodicePolizza(String codicePolizza) {
        this.codicePolizza = codicePolizza;
    }

    public String getTargaAss() {
        return targaAss;
    }

    public void setTargaAss(String targaAss) {
        this.targaAss = targaAss;
    }

    public String getCodFiscInt() {
        return codFiscInt;
    }

    public void setCodFiscInt(String codFiscInt) {
        this.codFiscInt = codFiscInt;
    }


    public void setDataIniz(Date dataIniz) {
        this.dataIniz = dataIniz;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public double getPremioAss() {
        return premioAss;
    }

    public void setPremioAss(double premioAss) {
        this.premioAss = premioAss;
    }

    public Date getDataIniz()
    {
        return dataIniz;
    }

    @Override
    public String toString()
    {
        if(a.getP().getCodFiscale().equals(codFiscInt))
        {
          return "\nPOLIZZA:\n" +
                  "codicePolizza = " + codicePolizza +
                  " | dataIniz = " + df.format(dataIniz) +
                  " | dataFine = " + df.format(dataFine) +
                  " | premioAss = " + premioAss +
                  "\nAUTO RELATIVA A QUESTA POLIZZA: \nAutomobile di " + p.getNome()  + " " + p.getCognome() +
                "\ntarga = " + a.getTarga() +
                " | codFiscProp = " + a.getCodFiscProp() +
                " | dataImm = " + df.format(a.getDataImm()) +
                  "\nINTESTATARIO DELLA POLIZZA E PROPRIETARIO DELL'AUTO:\n" + p.toString();
        }
        else
        {
            return "\nPOLIZZA:\n" +
                    "codicePolizza = " + codicePolizza +
                    " | dataIniz = " + df.format(dataIniz) +
                    " | dataFine = " + df.format(dataFine) +
                    " | premioAss = " + premioAss +
                    "\nAUTO RELATIVA A QUESTA POLIZZA:\n" + a.toString() +
                    "\nINTESTATARIO DELLA POLIZZA:" + p.toString();
        }
    }
    public static ArrayList<Polizza> LeggiPolizza(File filename)
    {
        while(true)
        {
            try {
                FileInputStream fis = new FileInputStream(filename);
                ObjectInputStream ois = new ObjectInputStream(fis);
                System.out.println("File esistente");
                TestIncidenti.Polizze = (ArrayList<Polizza>) ois.readObject();
                for (Polizza pz : TestIncidenti.Polizze)
                {
                    System.out.println(pz);
                }
                ois.close();
                break;
            } catch (FileNotFoundException e)
            {
                System.out.print("File non trovato, creazione del nuovo file importando dati da Polizze.txt\n");
                try {
                    filename.createNewFile();
                    FileReader fr = new FileReader("Polizze.txt");
                    BufferedReader br = new BufferedReader(fr);
                    FileOutputStream fos = new FileOutputStream(filename);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    String line;
                    while((line=br.readLine())!=null)
                    {
                        String [] s = line.split(" ");
                        for(Persona p : TestIncidenti.Persone)
                        {
                            if(p.getCodFiscale().equals(s[0]))
                            {
                                for(Automobile a : TestIncidenti.Automobili)
                                {
                                    if(a.getTarga().equals(s[1]))
                                    {
                                        Polizza pz = new Polizza(p, a, s[2], df.parse(s[3]), df.parse(s[4]), Double.parseDouble(s[5]));
                                        TestIncidenti.Polizze.add(pz);
                                    }
                                }
                            }
                        }
                    }
                    oos.writeObject(TestIncidenti.Polizze);
                    System.out.println("\nInserite nel database\n");
                    oos.flush();
                    oos.close();
                } catch (IOException | ParseException ex) {
                    System.out.println("File non trovato o errore nel parse");
                    break;
                }
            } catch (IOException | ClassNotFoundException e)
            {
                System.out.println("Errore nel file");
                break;
            }
        }
        return TestIncidenti.Polizze;
    }
}
