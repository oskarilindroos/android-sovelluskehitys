package org.example;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Lintu haukka = new Lintu("Haukka");
        Kala kilpikonna = new Kala("Kilpikonna");

        haukka.heraa();
        haukka.toimi();
        haukka.lepaa();

        kilpikonna.heraa();
        kilpikonna.toimi();
        kilpikonna.lepaa();

        ArrayList<Elain> elaimet = new ArrayList<>();
        elaimet.add(haukka);
        elaimet.add(kilpikonna);

        System.out.println("Aamulla:");
        for (Elain elain : elaimet) {
            elain.heraa();
        }

        for (Elain elain : elaimet) {
            elain.toimi();
        }

        for (Elain elain : elaimet) {
            elain.lepaa();
        }
    }
}