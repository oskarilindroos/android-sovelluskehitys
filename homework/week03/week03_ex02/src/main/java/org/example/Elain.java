package org.example;

public class Elain {
    private String nimi;

    public Elain(String nimi) {
        this.nimi = nimi;
    }

    public String getNimi() {
        return nimi;
    }

    public void heraa() {
        System.out.println(nimi + " herää");
    }

    public void lepaa() {
        System.out.println(nimi + " lepää");
    }

    public void toimi() {
        System.out.println(nimi + " toimii");
    }
}
