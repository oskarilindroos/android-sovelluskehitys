package org.example;

public class Kala extends Elain {
    public Kala(String nimi) {
        super(nimi);
    }

    @Override
    public void toimi() {
        System.out.println(super.getNimi() + " ui");
    }
}
