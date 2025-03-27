package controller;

import view.MainView;

public class App {

    private UserHandler userHandler;
    private MainView mainView;

    public App() {
        this.userHandler = new UserHandler();
        this.mainView = new MainView();
    }

    public void runApp() {
        mainView.displayWelcomeMessage();
        userHandler.registerUser();
    }



}
