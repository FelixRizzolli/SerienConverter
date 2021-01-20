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

        for (String seriesDirectory : allSeriesDirectorys){
            System.out.println(seriesDirectory);
            allSeries.add(new Series(
                current_directory.getPath() + "/" + seriesDirectory
            ));
        }
        
        allSeries.forEach(series -> series.rename(Mode.RIZZ_TO_PEER));
    }
}
