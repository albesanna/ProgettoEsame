import javax.xml.crypto.Data;
import java.util.Date;

public class Incidente extends Polizza{
    Polizza p;
    private int codice;
    private String codPolizza;
    private Date dataInc;
    private double importo;

    public Incidente(Polizza p, int codice, Date dataInc, double importo)
    {
        super(p.getP(), p.getA(), p.getCodicePolizza(), p.getDataIniz(), p.getDataFine(), p.getPremioAss());
        this.p = p;
        this.codice = codice;
        this.codPolizza = p.getCodicePolizza();
        this.dataInc = dataInc;
        this.importo = importo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getCodice() != this.getCodice()) return false;
        Incidente incidente = (Incidente) o;
        return codice == incidente.codice;
    }

    @Override
    public Polizza getP()
    {
        return p;
    }

    public void setP(Polizza p) {
        this.p = p;
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public String getCodPolizza() {
        return codPolizza;
    }

    public void setCodPolizza(String codPolizza) {
        this.codPolizza = codPolizza;
    }

    public Date getDataInc() {
        return dataInc;
    }

    public void setDataInc(Date dataInc) {
        this.dataInc = dataInc;
    }

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }
}
