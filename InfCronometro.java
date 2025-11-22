package APS;

public class InfCronometro {
    private String NomEQP1;
    private String NomEQP2;
    private String VoltasEQP1;
    private String VoltasEQP2;

    // Construtor que recebe os dados diretamente de nome e voltas
    public InfCronometro(String NomEQP1,String NomEQP2, String VoltasEQP1, String VoltasEQP2) {
        this.NomEQP1 = NomEQP1;
        this.NomEQP1 = NomEQP2;
        this.VoltasEQP1 = VoltasEQP1;
        this.VoltasEQP2 = VoltasEQP2;
    }

    //getters
    public String getNomEQP1() {
        return this.NomEQP1;
    }

    public String getNomEQP2() {
        return this.NomEQP2;
    }

    public String getVoltasEQP1() {
        return this.VoltasEQP1;
    }

    public String getVoltasEQP2() {
        return this.VoltasEQP2;
    }

    //setters
    public void setNomEQP1(String NomEQP1) {
        this.NomEQP1 = NomEQP1;
    }

    public void setNomEQP2(String NomEQP2) {
        this.NomEQP2 = NomEQP2;
    }

    public void setVoltasEQP1(String VoltasEQP1) {
        this.VoltasEQP1 = VoltasEQP1;
    }

    public void setVoltasEQP2(String VoltasEQP2) {
        this.VoltasEQP2 = VoltasEQP2;
    }
}