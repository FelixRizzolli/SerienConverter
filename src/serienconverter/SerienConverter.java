/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serienconverter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Felix Peer
 * @author Felix Rizzolli
 */
public class SerienConverter {
    private static List<Series> allSeries = new ArrayList<Series>();
    public static FilenameFilter directoryfilter = new FilenameFilter() {
        @Override
        public boolean accept(File current, String name) {
            return new File(current, name).isDirectory();
        }
    };
    public static FilenameFilter filefilter = new FilenameFilter() {
        @Override
        public boolean accept(File current, String name) {
            return new File(current, name).isFile();
        }
    };
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File current_directory = new File(System.getProperty("user.dir") + "/test");
        String[] allSeriesDirectorys = current_directory.list(directoryfilter);
        
        System.out.println(current_directory);
        
        Mode converting_mode = selectMode();
        
        for (String seriesDirectory : allSeriesDirectorys){
            System.out.println(seriesDirectory);
            allSeries.add(new Series(
                converting_mode,
                current_directory.getPath() + "/" + seriesDirectory
            ));
        }
        
        allSeries.forEach(
                series -> series.rename(converting_mode)
        );
    }
    
    public static Mode selectMode(){
        System.out.println(
                "Converting Mode: \n [1] Peer to Rizz \n [2] Rizz to Peer"
        );
        int inputMode = 0;
        while (inputMode == 0){
            try {
                inputMode = Integer.parseInt(
                        new Scanner(System.in).next()
                );
                inputMode = (inputMode == 1 || inputMode == 2)
                        ? inputMode
                        : 0;
                if (inputMode == 0) System.out.println("Eingabe war ungültig!");
            } catch (Exception ex) {
                System.out.println("Eingabe war ungültig!");
            }
        }
        return (inputMode == 1) ? Mode.PEER_TO_RIZZ : Mode.RIZZ_TO_PEER;
        
    }
}
