package app.service.performance;

public interface PerformanceService {
    //for all users I will add a 1 to the beginning of their email address
    //for all the songs in their playlists I will double their duration if it surpasses 350 seconds
    void applyLogicOnUsers();
}
