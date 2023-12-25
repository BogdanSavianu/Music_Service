package app.controller.gui;

import app.model.User;
import app.service.UserService;
import app.single_point_access.GUIFrameSinglePointAccess;
import app.single_point_access.ServiceSinglePointAccess;
import app.view.UserDetailsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserDetailsController {

    private UserDetailsView userDetailsView;

    private UserService userService = ServiceSinglePointAccess.getUserService();


    public void startLogic(User user) {
        userDetailsView = new UserDetailsView();
        GUIFrameSinglePointAccess.changePanel(userDetailsView.getMainPanel(), "Welcome " + user.getName());

        userDetailsView.getNameValue().setText(user.getName());
        userDetailsView.getSalaryValue().setText(user.getEmail());
        userDetailsView.getStreetValue().setText(user.getPassword());
        userDetailsView.getCityValue().setText(user.getPlaylists().toString());

        userDetailsView.getLogOutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginController loginController = new LoginController();
                loginController.startLogic();
            }
        });
    }
}
