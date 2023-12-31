package app.controller.gui;

import app.model.User;
import app.service.UserService;
import app.single_point_access.GUIFrameSinglePointAccess;
import app.single_point_access.ServiceSinglePointAccess;
import app.view.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

    private LoginView loginView;

    private UserService userService = ServiceSinglePointAccess.getUserService();

    public void startLogic() {
        loginView = new LoginView();
        GUIFrameSinglePointAccess.changePanel(loginView.getLoginPanel(), "Login");

        loginView.getLogInButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = loginView.getTextFieldName().getText();
                String password = new String(loginView.getPasswordField().getPassword());

                User user = userService.login(name, password);
                if (user != null) {
                    UserDetailsController userDetailsController = new UserDetailsController();
                    userDetailsController.startLogic(user);
                } else {
                    GUIFrameSinglePointAccess.showDialogMessage("Invalid username or password");
                }
            }
        });
    }
}
