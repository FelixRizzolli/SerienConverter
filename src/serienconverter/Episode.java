/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serienconverter;

import java.io.File;

/**
 *
 * @author Felix Rizzolli
 */
public class Episode {
    private File directory; 
    
    //EpisodeInformations
    private String seriesName;
    private int seasonID;
    private String seasonName;
    private int episodeID;
    private String episodeName;
    
    public Episode(String directory, String seriesName, int seasonID, String seasonName) {
        setDirectory(directory);
        setSeriesName(seriesName);
        setSeasonID(seasonID);
        setSeasonName(seasonName);
    }
    
    private void setDirectory(String dir){
        this.directory = new File(dir);
    }
    private void setSeriesName(String seriesName){
        this.seriesName = seriesName;
    }
    private void setSeasonID(int seasonID){
        this.seasonID = seasonID;
    }
    private void setSeasonName(String seasonName){
        this.seasonName = seasonName;
    }
    private void setEpisodeID(int episodeID){
        this.episodeID = episodeID;
    }
    private void setEpisodeName(String episodeName){
        this.episodeName = episodeName;
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
    private int getEpisodeID(){
        return this.episodeID;
    }
    private String getEpisodeName(){
        return this.episodeName;
    }
    
    public void scan(Mode mode){
        String episode = getDirectory().getName();
        
        if (mode == Mode.PEER_TO_RIZZ){
            setEpisodeID(Integer.parseInt(
                    episode.split(" - ")[0]
                           .split("x")[1]
            ));
            setEpisodeName(episode.split(" - ")[1]);
        }
        else if (mode== Mode.RIZZ_TO_PEER){
            setEpisodeID(Integer.parseInt(
                    episode.split(" - ")[0]
                           .split("[")[1]
                           .split("E")[1]
            ));
            setEpisodeName(episode.split(" - ")[1]);
        }
        
    }
    public boolean rename(Mode mode){
        String newEpisodeName = "";
        if (mode == Mode.PEER_TO_RIZZ){
            String seasonString = (getSeasonID() < 10) 
                    ? "0" + getSeasonID() 
                    : "" + getSeasonID();
            String episodeString = (getSeasonID() < 10) 
                    ? "0" + getSeasonID() 
                    : "" + getSeasonID();
            newEpisodeName = (
                getSeriesName() + 
                " [S" + seasonString + "E" + episodeString + "] - " + 
                getEpisodeName()
            );
        }
        else if (mode == Mode.RIZZ_TO_PEER) {
            String seasonString = "" + getSeasonID();
            String episodeString = (getSeasonID() < 10) 
                    ? "0" + getSeasonID() 
                    : "" + getSeasonID();
            newEpisodeName = (
                seasonString + "x" + episodeString + " - " +
                getEpisodeName()
            );
        }
        System.out.println(newEpisodeName);
        return true;
    }
}
