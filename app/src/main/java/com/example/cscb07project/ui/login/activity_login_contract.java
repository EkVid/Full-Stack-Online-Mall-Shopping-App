package com.example.cscb07project.ui.login;

public interface activity_login_contract {
    interface View {
        void progressBarVisibility(int i);
        void loggedIn();
        void doToast(String message);

    }

    interface Presenter {
        void doLoginEmail(String email, String password);
        void doToast(String message);
        void doCheckLoggedIn();
        void isLoggedIn();
        void changeProgressBarVisibility(int mode);
    }

    interface Model {
        void loggingInUser(String email, String password);
        void checkLoggedIn();
        void changeProgressBarVisibility(int mode);
        void doToastView(String message);

    }
}
