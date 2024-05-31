package controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import dao.CommuneService;
import dao.UserService;
import data.Commune;
import view.ConnectionPage;
import view.InscriptionPage;
import view.MainPage;
import view.ResetPassword;
import view.misc.CustomAlert;
import view.ForgotPassword;

import java.io.IOException;
import java.sql.SQLException;

import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

/**
* Class which allows linking the view and the model and controlling the information transiting between them.
* @autor O.Gunes
*/
public class Controller implements EventHandler<ActionEvent> {

    /**
    * An instance of ConnectionPage 
    */
    private ConnectionPage connectionPage;

    /**
    * An instance of InscriptionPage
    */
    private InscriptionPage inscriptionPage;

    /**
    * An instance of forgotPassword page.
    */
    private ForgotPassword forgotPassword;

    /**
    * An instance of ResetPassword page. 
    */
    private ResetPassword resetPassword;

    /**
    * A boolean check if a mail code is sent or not
    */
    private boolean codeSent;

    /**
    * A String which contain the verification code. 
    */
    private String codeString;

    private MainPage mainPage;


    private ArrayList<Commune> communes;

    /**
    * The constructor of Controller. 
    * @param connectionPage
    */
    public Controller(MainPage mainPage) {
        this.connectionPage = new ConnectionPage(this);
        this.inscriptionPage = new InscriptionPage(this);
        this.forgotPassword = new ForgotPassword(this); 
        this.resetPassword = new ResetPassword(this);
        this.mainPage = mainPage;

    }    

    /**
    * The Empty constructor of controller 
    */
    public Controller() {
        this.connectionPage = new ConnectionPage(this);
        this.inscriptionPage = new InscriptionPage(this);
        this.forgotPassword = new ForgotPassword(this);
        this.resetPassword = new ResetPassword(this);
        this.mainPage = new MainPage();
    }


    /**
    * Method which check if an event is realized.
    *
    * @param ActionEvent Check if an action is executed 
    */
    @Override
    public void handle(ActionEvent e) {
        // Gestion des actions de la page de connexion
        handleConnectionPageActions(e);

        // Gestion des actions de la page d'inscription
        handleInscriptionPageActions(e);

        // Gestion des actions de la page mot de passe oublié
        handleForgotPasswordPageActions(e);

        // Gestion des actions de la page réinitialisation du mot de passe
        handleResetPasswordPageActions(e);

        //Gestion des actions de la page principal
        handleMainPageActions(e);
    }

    private void handleMainPageActions(ActionEvent e) {
        if(e.getSource() == this.mainPage.getSearchField()){
            String searchText = this.mainPage.getSearchField().getText().trim();
            handleSearchEvent(searchText);
        }

        if(e.getSource() == this.mainPage.getImageUserIcon()){
            System.out.println("aaaa");
        }
    }

    public void connectionClicked(){
        try {
            Stage stage = (Stage) this.mainPage.getSearchField().getScene().getWindow();
            this.connectionPage.start(stage);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    /**
    * Handle action of the Connection Page. 
    * @param e The Action Event
    */
    private void handleConnectionPageActions(ActionEvent e) {
        if (e.getSource() == this.connectionPage.getLinkSignUp()) {
            try {
                Stage stage = (Stage) this.connectionPage.getBtnLogin().getScene().getWindow();
                this.inscriptionPage.start(stage);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            
        }

        if (e.getSource() == this.connectionPage.getLinkForgotPassword()) {
            try {
                Stage stage = (Stage) this.connectionPage.getBtnLogin().getScene().getWindow();
                this.forgotPassword.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == this.connectionPage.getBtnLogin()) {
            String email = this.connectionPage.getEmailField().getText();
            String password = this.connectionPage.getPasswordField().getText();

            this.connectionPage.getErrorMessageLabel().setVisible(false);

            if (email.isEmpty() || password.isEmpty()) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.connectionPage.getErrorMessageLabel().setText("Tous les champs doivent \u00eatre remplis.");
                this.connectionPage.getErrorMessageLabel().setVisible(true);
            } else if (!isValidEmail(email)) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.connectionPage.getErrorMessageLabel().setText("L'adresse e-mail n'est pas valide.");
                this.connectionPage.getErrorMessageLabel().setVisible(true);
            } else {
                UserService userService = new UserService();
                if (userService.validateLogin(email, password)) {
                    this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: green;");
                    this.connectionPage.getErrorMessageLabel().setText("Connexion Reussi !");
                    this.connectionPage.getErrorMessageLabel().setVisible(true);

                    
                    CustomAlert.showAlert("Connexion Reussi", "Vous vous etes connecté avec succès. Redirection dans 3 secondes");

                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Platform.runLater(() -> {
                                    Stage stage = (Stage) connectionPage.getBtnLogin().getScene().getWindow();
                                    mainPage.start(stage);
                                });
                            }
                        }, 3000);

                    //CONNEXION A LA PAGE PRINCIPAL

                } else {
                    this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.connectionPage.getErrorMessageLabel().setText("Identifiants incorrects.");
                    this.connectionPage.getErrorMessageLabel().setVisible(true);
                }
            }
        }
    }


    /**
    * Handle the action of the Inscription Page. 
    * @param e The Action Event
    */
    private void handleInscriptionPageActions(ActionEvent e){
        if (this.inscriptionPage != null && e.getSource() == this.inscriptionPage.getBtnSignUp()) {
            String firstName = this.inscriptionPage.getFirstNameField().getText();
            String lastName = this.inscriptionPage.getLastNameField().getText();
            String email = this.inscriptionPage.getEmailField().getText();
            String password = this.inscriptionPage.getPasswordField().getText();
            String confirmPassword = this.inscriptionPage.getConfirmPasswordField().getText();

            this.inscriptionPage.getErrorMessageLabel().setVisible(false);

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.inscriptionPage.getErrorMessageLabel().setText("Tous les champs doivent \u00eatre remplis.");
                this.inscriptionPage.getErrorMessageLabel().setVisible(true);
            } else if (!isValidEmail(email)) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.inscriptionPage.getErrorMessageLabel().setText("L'adresse e-mail n'est pas valide.");
                this.inscriptionPage.getErrorMessageLabel().setVisible(true);
            } else if (!password.equals(confirmPassword)) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.inscriptionPage.getErrorMessageLabel().setText("Les mots de passe ne correspondent pas.");
                this.inscriptionPage.getErrorMessageLabel().setVisible(true);
            } else {
                UserService creationUtilisateur = new UserService();
                if (creationUtilisateur.emailExists(email)) {
                    this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.inscriptionPage.getErrorMessageLabel().setText("L'adresse e-mail est d\u00e9j\u00e0 utilis\u00e9e.");
                    this.inscriptionPage.getErrorMessageLabel().setVisible(true);
                } else {
                    creationUtilisateur.createUser(lastName, firstName, email, password);
                    this.inscriptionPage.getErrorMessageLabel().setStyle("-fx-text-fill: green;");
                    this.inscriptionPage.getErrorMessageLabel().setText("Compte cr\u00e9e avec succ\u00e8s !");
                    this.inscriptionPage.getErrorMessageLabel().setVisible(true);
                }
            }
        }

        if (e.getSource() == this.inscriptionPage.getLinkLogin()) {
            try {
                Stage stage = (Stage) this.inscriptionPage.getLinkLogin().getScene().getWindow();
                this.connectionPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
    * Handle the action of ForgotPassword page.
    * @param e The Action Event
    */
    private void handleForgotPasswordPageActions(ActionEvent e){
        if (e.getSource() == this.forgotPassword.getLinkForgotPassword()) {
            try {
                Stage stage = (Stage) this.forgotPassword.getBtnLogin().getScene().getWindow();
                this.connectionPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if(e.getSource() == this.forgotPassword.getBtnLogin()){
            String email;
            if(this.forgotPassword.getBtnLogin().getText().equals("Recevoir Code")){
                UserService serviceUtilisateur = new UserService();
                String mail = this.forgotPassword.getEmailField().getText();
                if(mail.isEmpty()){
                    this.forgotPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.forgotPassword.getErrorMessageLabel().setText("Tous les champs doivent etre rempli");
                    this.forgotPassword.getErrorMessageLabel().setVisible(true);
                } else if(!serviceUtilisateur.emailExists(mail)){
                    this.forgotPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.forgotPassword.getErrorMessageLabel().setText("Un mail deja renseignee dans la bdd doit etre entrer");
                    this.forgotPassword.getErrorMessageLabel().setVisible(true);
                } else {

                    email = this.forgotPassword.getEmailField().getText();
                    int code = serviceUtilisateur.generateVerificationCode();
                    this.codeString = String.valueOf(code);

                    try {
                        serviceUtilisateur.sendVerificationEmail(email, codeString);
                    } catch (IOException e1) {
                        System.out.println(e1.getMessage());
                    } catch (MessagingException e2){
                        System.out.println(e2.getMessage());
                    }
                    this.codeSent = true;
                    updateButtonState();
                    CustomAlert.showAlert("Email envoy\u00e9", "Un email avec le code de v\u00e9rification a \u00e9t\u00e9 envoy\u00e9 \u00e0 " + email);


                    System.out.println(codeString);
                }
            } else if(this.forgotPassword.getBtnLogin().getText().equals("V\u00e9rifier")){
                if(this.forgotPassword.getCodeField().getText().equals(this.codeString)){
                    try {
                        Stage stage = (Stage) this.forgotPassword.getBtnLogin().getScene().getWindow();
                        this.resetPassword.start(stage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    this.forgotPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.forgotPassword.getErrorMessageLabel().setText("Le code specifie est incorrect ! ");
                    this.forgotPassword.getErrorMessageLabel().setVisible(true);
                }
            }
        }
    }

    /**
    * Handle the action of ResetPassword page. 
    * @param e The Action Event
    */
    private void handleResetPasswordPageActions(ActionEvent e){
        if (e.getSource() == this.resetPassword.getBtnValidate()) {
            String newPassword = this.resetPassword.getFirstPassword().getText();
            String confirmPassword = this.resetPassword.getSecondPassword().getText();

            if (newPassword.equals(confirmPassword)) {
                if (!newPassword.isEmpty() && !confirmPassword.isEmpty()) {
                    String email = this.forgotPassword.getEmailField().getText();
                    UserService userService = new UserService();

                    try {
                        userService.updatePassword(email, newPassword);

                        this.resetPassword.getErrorMessageLabel().setStyle("-fx-text-fill: green; -fx-font-size: 15px;");
                        this.resetPassword.getErrorMessageLabel().setVisible(true);

                        this.resetPassword.getErrorMessageLabel().setText("Redirection...");
                        
                        CustomAlert.showAlert("Reinitialisation du mot de passe", "Votre mot de passe est change.");

                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Platform.runLater(() -> {
                                    Stage stage = (Stage) resetPassword.getBtnValidate().getScene().getWindow();
                                    connectionPage.start(stage);
                                });
                            }
                        }, 3000);

                    } catch (SQLException ex) {
                        this.resetPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                        this.resetPassword.getErrorMessageLabel().setText("Connexion au service momentanément impossible.");
                        this.resetPassword.getErrorMessageLabel().setVisible(true);
                    }
                } else {
                    this.resetPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.resetPassword.getErrorMessageLabel().setText("Les champs de mot de passe doivent être remplis !");
                    this.resetPassword.getErrorMessageLabel().setVisible(true);
                }
            } else {
                this.resetPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.resetPassword.getErrorMessageLabel().setText("Les mots de passe ne correspondent pas !");
                this.resetPassword.getErrorMessageLabel().setVisible(true);
            }
        }

        if(e.getSource() == this.resetPassword.getLinkWrongMail()){
            Stage stage = (Stage) this.resetPassword.getBtnValidate().getScene().getWindow();
            this.forgotPassword.start(stage);
        }

        if(e.getSource() == this.resetPassword.getLinkConnection()){
            Stage stage = (Stage) this.resetPassword.getBtnValidate().getScene().getWindow();
            this.connectionPage.start(stage);
        }
    }





    /**
    * Private method, which change the state of the btnLogin present in forgotPassword page.
    */
    private void updateButtonState() {
        if (this.codeSent) {
            this.forgotPassword.getBtnLogin().setText("V\u00e9rifier");
            this.forgotPassword.getCodeField().setDisable(false);
        } else {
            this.forgotPassword.getBtnLogin().setText("Recevoir Code");
        }
    }

    /**
    * Check if a mail is valid with the format mail@subdomain.extension 
    *
    * @param email The checked mail
    * @return True if valid mail, else return false.
    */
    private boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void handleSearchEvent(String searchText) {
        List<Commune> filteredCommunes = getFilteredCommunes(searchText);
        this.mainPage.updateCommunesListView(filteredCommunes);
        this.mainPage.getNumberOfRow().setText(filteredCommunes.size() + " resultat");
    }    


    /**
     * Méthode pour récupérer la liste des communes depuis la base de données.
     * @return Une liste de noms de communes.
     */
    public ArrayList<Commune> getCommunes() {
        this.communes = new ArrayList<Commune>();
        CommuneService communeService = new CommuneService();

        try {
            this.communes = (ArrayList) communeService.getAllCommunes();
            this.mainPage.getNumberOfRow().setText(this.communes.size() + " resultat");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return communes;
    }

    public ArrayList<Commune> getFilteredCommunes(String searchText) {
        ArrayList<Commune> allCommunes = this.communes;
        ArrayList<Commune> filteredCommunes = new ArrayList<>();
        
        
        String lowerCaseSearchText = searchText.toLowerCase();
        for (Commune commune : allCommunes) {
            if (commune.getNomCommune().toLowerCase().startsWith(lowerCaseSearchText)) {
                filteredCommunes.add(commune);
            }
        }
        this.mainPage.getNumberOfRow().setText(filteredCommunes.size() + " resultat");
        return filteredCommunes;
    }
}