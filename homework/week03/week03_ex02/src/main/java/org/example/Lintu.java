package org.example;

public class Lintu extends Elain{
    public Lintu(String nimi) {
        super(nimi);
    }

    @Override
    public void toimi() {
        System.out.println(super.getNimi() + " lentää");
    }
}
