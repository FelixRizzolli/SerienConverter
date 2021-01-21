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
    private List<Episode> episodes = new ArrayList<Episode>();
    private File directory;
    
    //SeasonInformations
    private String seriesName;
    private int seasonID = -1;
    private String seasonName;
    
    public Season(String dir, String seriesName){
        setDirectory(dir);
        setSeriesName(seriesName);
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
        
        String filename = getDirectory().getName();
        if (mode == Mode.PEER_TO_RIZZ) {
            setSeasonName(
                    filename.split(" ")[1]
            );
            setSeasonID(Integer.parseInt(getSeasonName()));
        }
        else if (mode == Mode.RIZZ_TO_PEER) {
            setSeasonName(
                    filename.replace("[", "X0!0X")
                            .split("X0!0X")[1]
                            .replace("]", "X0!0X")
                            .split("X0!0X")[0]
            );
            setSeasonID(Integer.parseInt(getSeasonName()));
        }
        
        for (String episodeDirectory : allEpisodeDirectorys){
            System.out.println(episodeDirectory);
            episodes.add(
                    new Episode(
                            getDirectory().getPath() + "/" + episodeDirectory, 
                            getSeriesName(),
                            getSeasonID()
                    )
            );
        }
        episodes.forEach(
                episode -> episode.scan(mode)
        );
    }
    public boolean rename(Mode mode){
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
        
        getEpisodes().forEach(
                episode -> episode.rename(mode)
        );
        
        getDirectory().renameTo(
                new File(
                    new File(getDirectory().getParent()).getPath() + 
                    "/" + newSeasonName
                )
        );
        return false;
    }
}
