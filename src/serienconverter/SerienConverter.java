/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serienconverter;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Scanner;

/**
 *
 * @author felix
 */
public class SerienConverter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        // Wo ist die Serie gespeichert?
        System.out.println("Gib den Dateipfad zur Serie ein.");
        String serie_string = s.nextLine();
        File serie_file = new File(serie_string);
        String serien_name = serie_file.getName();

        // Hole Staffeln aus der Serie
        String[] staffeln_string = serie_file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        // Welches Format hat die Serie? 
        System.out.println("\nIn welchem Format wurde die Serie gespeichert?");
        System.out.println("1 - Rizzolli");
        System.out.println("2 - Peer");
        String format = s.nextLine();
        System.out.println();

        System.out.println("Ok, die Serie ist \"" + serien_name + "\".\n");

        if (format.equals("1")) {
            // Staffel [01] -> Staffel 1
            // Serie [S01E01] - Folgentitel -> 1x01 - Folgentitel

            for (int i = 0; i < staffeln_string.length; i++) {
                System.out.println(staffeln_string[i] + "\n"); // Staffel X

                File staffel_file = new File(serie_string + "\\" + staffeln_string[i]);
                String[] folgen_string = staffel_file.list();

                // Konvertiere Staffelformat zu Peer
                String staffel_nr_string = staffeln_string[i].split(" ")[1];
                staffel_nr_string = staffel_nr_string.replace("[", "");
                staffel_nr_string = staffel_nr_string.replace("]", "");
                int staffel_nr_int = Integer.parseInt(staffel_nr_string);

                for (int ii = 0; ii < folgen_string.length; ii++) {
                    System.out.println(folgen_string[ii]); // Folge X

                    String folgenname = folgen_string[ii].split(" - ")[1];
                    String episoden_nr = folgen_string[ii].replace("[", "2801!");
                    episoden_nr = episoden_nr.split("2801!")[1];
                    episoden_nr = episoden_nr.replace("]", "2801!");
                    episoden_nr = episoden_nr.split("2801!")[0];
                    episoden_nr = episoden_nr.split("S")[1];
                    episoden_nr = episoden_nr.split("E")[1];           

                    String newTitle = staffel_nr_int + "x" + episoden_nr + " - " + folgenname;

                    // Bennene die Folge zum richtigen Format um
                    File oldFolge = new File(serie_file + "\\" + staffeln_string[i] + "\\" + folgen_string[ii]);
                    File newFolge = new File(serie_string + "\\" + staffeln_string[i] + "\\" + newTitle);
                    System.out.println(oldFolge);
                    oldFolge.renameTo(newFolge);
                }

                // Bennene die Staffel zum richtigen Format um
                File newStaffel = new File(serie_string + "\\" + "Staffel " + staffel_nr_int);
                staffel_file.renameTo(newStaffel);
                System.out.println();
            }
        } else if (format.equals("2")) {
            // Staffel 1 -> Staffel [01]
            //  1x01 - Folgentitel -> Serie [S01E01] - Folgentitel

            for (int i = 0; i < staffeln_string.length; i++) {
                System.out.println(staffeln_string[i] + "\n"); // Staffel X

                File staffel_file = new File(serie_string + "\\" + staffeln_string[i]);
                String[] folgen_string = staffel_file.list();

                // Konvertiere Staffelformat zu Rizzolli
                String staffel_nr_string = staffeln_string[i].split(" ")[1];
                int staffel_nr_int = Integer.parseInt(staffel_nr_string);
                if (staffel_nr_int < 10) {
                    staffel_nr_string = "0" + staffel_nr_int;
                } else {
                    staffel_nr_string = staffel_nr_int + "";
                }

                for (int ii = 0; ii < folgen_string.length; ii++) {
                    System.out.println(folgen_string[ii]); // Folge X

                    String folgenname = folgen_string[ii].split(" - ")[1];
                    String episoden_nr = folgen_string[ii].split("x")[1].split(" - ")[0];

                    String newTitle = serien_name + " [S" + staffel_nr_string + "E" + episoden_nr + "] - " + folgenname;

                    // Bennene die Folge zum richtigen Format um
                    File oldFolge = new File(serie_file + "\\" + staffeln_string[i] + "\\" + folgen_string[ii]);
                    File newFolge = new File(serie_string + "\\" + staffeln_string[i] + "\\" + newTitle);
                    //System.out.println(oldFolge);
                    oldFolge.renameTo(newFolge);
                }

                // Bennene die Staffel zum richtigen Format um
                File newStaffel = new File(serie_string + "\\" + "Staffel [" + staffel_nr_string + "]");
                staffel_file.renameTo(newStaffel);
                System.out.println();
            }
        }
        
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
               new StringSelection("Staffel [01]"), null
          );
    }

}
