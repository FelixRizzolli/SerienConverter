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
public class Season {    
    private Mode mode;
    private List<Episode> episodes = new ArrayList<Episode>();
    private File directory;
    
    //SeasonInformations
    private String seriesName;
    private int seasonID;
    private String seasonName;
    
    public Season(Mode mode, String dir, String seriesName){
        setDirectory(dir);
        setSeriesName(seriesName);
    }
    
    private void setMode(Mode mode){
        this.mode = mode;
    }
    private void setEpisodes(List<Episode> episodes){
        this.episodes = episodes;
    }
    private void setDirectory(String dir){
        this.directory = new File(dir);
    }
    private void setSeriesName(String series){
        this.seriesName = series;
    }
    private void setSeasonID(int seasonID){
        this.seasonID = seasonID;
    }
    private void setSeasonName(String seasonName){
        this.seasonName = seasonName;
    }
    
    private Mode getMode(){
        return this.mode;
    }
    private List<Episode> getEpisodes(){
        return this.episodes;
    }
    private File getDirectory(){
        return this.directory;
    }
    private String getSeriesName(){
        return this.seriesName;
    }
    private int getSeasonID(){
        return this.seasonID;
    }
    private String getSeasonName(){
        return this.seasonName;
    }
    
    public void scan(Mode mode){
        String[] allEpisodeDirectorys = getDirectory().list(
                SerienConverter.filefilter
        );
        for (String episodeDirectory : allEpisodeDirectorys){
            System.out.println(episodeDirectory);
            episodes.add(
                    new Episode(
                            getDirectory().getPath() + "/" + episodeDirectory, 
                            getSeriesName(),
                            getSeasonID(),
                            getSeasonName()
                    )
            );
        }
        episodes.forEach(episode -> episode.scan(mode));
        if (getMode() == Mode.PEER_TO_RIZZ) {
            
        }
        else if (getMode() == Mode.RIZZ_TO_PEER) {
            
        }
    }
    public boolean rename(Mode mode){
        getEpisodes().forEach(episode -> episode.rename(mode));
        String newSeasonName = "";
        if (mode == Mode.PEER_TO_RIZZ) {
            String seasonString = (getSeasonID() < 10) 
                ? "0" + getSeasonID() 
                : "" + getSeasonID();
            newSeasonName = "Staffel [" + seasonString + "]";
        }
        else if (mode == Mode.RIZZ_TO_PEER) {
            newSeasonName = "Staffel " + getSeasonID();
        }
        System.out.println(newSeasonName);
        return false;
    }
}
