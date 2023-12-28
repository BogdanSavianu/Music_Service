package app.service.performance;

import app.model.Playlist;
import app.model.Song;
import app.model.User;
import app.service.SongService;
import app.service.UserService;
import app.single_point_access.ServiceSinglePointAccess;

import java.util.List;

public class AppPerformanceService implements PerformanceService{
    private UserService userService = ServiceSinglePointAccess.getUserService();
    private SongService songService = ServiceSinglePointAccess.getSongService();
    @Override
    public void applyLogicOnUsers() {
        List<User> users = userService.findAll();
        for(User user: users){
            user.setEmail(1+user.getEmail());
            if(user.getPlaylists()!=null)
                for(Playlist playlist: user.getPlaylists()){
                    for(Song song: playlist.getSongs()){
                        if(song.getDuration()>350){
                            song.setDuration(song.getDuration()*2);
                            songService.update(song);
                        }
                    }
                }
            userService.update(user);
        }
    }
}