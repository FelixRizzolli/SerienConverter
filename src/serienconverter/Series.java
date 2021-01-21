/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serienconverter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Felix Rizzolli
 */
public class Series {
    private List<Season> seasons = new ArrayList<Season>();
    private File directory;
    
    //SeriesInformations
    private String seriesName;
    
    public Series(Mode mode, String directory) {
        setDirectory(directory);
        setSeriesName(getDirectory()
                .getName()
                .replace(" (", "X0!0X")
                .split("X0!0X")[0]
        );
        scan(mode);
    }
    
    private void setSeasons(List<Season> seasons){
        this.seasons = seasons;
    }
    private void addSeason(Season season){
        this.seasons.add(season);
    }
    private void setDirectory(String dir){
        this.directory = new File(dir);
    }
    private void setSeriesName(String seriesName){
        this.seriesName = seriesName;
    }
    
    private List<Season> getSeasons(){
        return this.seasons;
    }
    private File getDirectory(){
        return this.directory;
    }
    private String getSeriesName(){
        return this.seriesName;
    }
    
    public void scan(Mode mode){
        String[] allSeasonDirectorys = getDirectory().list();

        for (String seasonDirectory : allSeasonDirectorys){
            System.out.println(seasonDirectory);
            addSeason(
                new Season(
                        getDirectory().getPath() + "/" + seasonDirectory, 
                        getSeriesName()
                )
            );
        }
        
        seasons.forEach(
                season -> season.scan(mode)
        );
    }
    public boolean rename(Mode mode){
        getSeasons().forEach(
                season -> season.rename(mode)
        );
        return true;
    }
}
