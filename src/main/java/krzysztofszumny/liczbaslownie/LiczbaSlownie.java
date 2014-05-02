// Zamiana liczby na slowa z polską gramatyką
// Autor: Krzysztof Szumny, na podstawie:
// pyliczba - https://github.com/dowgird/pyliczba
// Licencja: (Gnu LGPL)

package krzysztofszumny.liczbaslownie;

import java.util.ArrayList;

public class LiczbaSlownie {

    static String[] jednosci = { "", "jeden ", "dwa ", "trzy ", "cztery ",
            "pięć ", "sześć ", "siedem ", "osiem ", "dziewięć ", };

    static String[] nastki = {"dziesięć ", "jedenaście ", "dwanaście ",
            "trzynaście ", "czternaście ", "piętnaście ", "szesnaście ",
            "siedemnaście ", "osiemnaście ", "dziewiętnaście ", };

    static String[] dziesiatki = { "", "dziesięć ", "dwadzieścia ",
            "trzydzieści ", "czterdzieści ", "pięćdziesiąt ",
            "sześćdziesiąt ", "siedemdziesiąt ", "osiemdziesiąt ",
            "dziewięćdziesiąt ", };

    static String[] setki = { "", "sto ", "dwieście ", "trzysta ", "czterysta ",
            "pięćset ", "sześćset ", "siedemset ", "osiemset ",
            "dziewięćset ", };

    static String[][] grupy = { { "", "", "" },
            { "tysiąc ", "tysiące ", "tysięcy " },
            { "milion ", "miliony ", "milionów " },
            { "miliard ", "miliardy ", "miliardów " },
            { "bilion ", "biliony ", "bilionów " },
            { "biliard ", "biliardy ", "biliardów " },
            { "trylion ", "tryliony ", "tryliardów " }, };


    private static String slownie3cyfry(int liczba) {
        int je = liczba % 10;
        int dz = (liczba/10) % 10;
        int se = (liczba/100) % 10;
        String[] slowa = {"", "", ""};

        if( se > 0 ) {
            slowa[0] = LiczbaSlownie.setki[se];
        }

        if( dz == 1 ) {
            slowa[1] = LiczbaSlownie.nastki[je];
        } else {
            if (dz > 0) {
                slowa[1] = LiczbaSlownie.dziesiatki[dz];
            }
            if (je > 0) {
                slowa[2] = LiczbaSlownie.jednosci[je];
            }
        }

        return slowa[0] + slowa[1] + slowa[2];
    }

    private static int przypadek(int liczba) {
        int je = liczba % 10;
        int dz = (liczba/10) % 10;
        int typ;

        if (liczba == 1){
            typ = 0; // jeden tysiac
        } else if ( dz == 1 && je > 1){
            typ = 2; // naście tysięcy
        } else if (2 <= je && je <=4){
            typ = 1; // [k-dziesiąt/set] [dwa/trzy/czery] tysiące
        } else {
            typ = 2; // x tysięcy
        }

        return typ;
    }

    public static String liczba_slownie(long liczba) {
        if(liczba == 0)
            return "zero";

        ArrayList<Integer> trojki = new ArrayList<Integer>();
        ArrayList<String> slowa = new ArrayList<String>();

        while(liczba>0){
            trojki.add((int)(liczba % 1000));
            liczba /= 1000;
        }

        for (int i=0; i<trojki.size(); i++) {
            if (trojki.get(i) > 0){
                if (i>0){
                    int p = przypadek(trojki.get(i));
                    String w = LiczbaSlownie.grupy[i][p];
                    slowa.add(slownie3cyfry(trojki.get(i))+ " " + w);
                } else {
                    slowa.add(slownie3cyfry(trojki.get(i)));
                }
            }
        }

        String result = slowa.get(slowa.size()-1);
        for(int i = slowa.size()-2; i >= 0; i--){
            result += " " + slowa.get(i);
        }

        return result;
    }
}
