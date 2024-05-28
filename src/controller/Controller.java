package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import view.ConnectionPage;
import view.InscriptionPage;
import view.ResetPassword;
import view.ForgotPassword;
import data.UserService;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* Class which allows linking the view and the model and controlling the information transiting between them.
* @autor O.Gunes
*/
public class Controller implements EventHandler<ActionEvent> {

    private ConnectionPage connectionPage;
    private InscriptionPage inscriptionPage;
    private ForgotPassword forgotPassword;
    private ResetPassword resetPassword;
    private boolean codeSent;
    private String codeString;

    public Controller(ConnectionPage connectionPage) {
        this.connectionPage = connectionPage;
        this.inscriptionPage = new InscriptionPage(this);
        this.forgotPassword = new ForgotPassword(this); // Initialize here
        this.resetPassword = new ResetPassword(this);
    }    

    public Controller() {
        this.connectionPage = new ConnectionPage();
        this.inscriptionPage = new InscriptionPage(this);
        this.forgotPassword = new ForgotPassword(this); // Initialize here
        this.resetPassword = new ResetPassword(this);
    }

    public void setConnectionPage(ConnectionPage connectionPage) {
        this.connectionPage = connectionPage;
    }

    public void setInscriptionPage(InscriptionPage inscriptionPage) {
        this.inscriptionPage = inscriptionPage;
    }

    @Override
    public void handle(ActionEvent e) {
        /* PAGE DE CONNEXION */
        if (e.getSource() == this.connectionPage.getLinkSignUp()) {
            try {
                Stage stage = (Stage) this.connectionPage.getBtnLogin().getScene().getWindow();
                this.inscriptionPage = new InscriptionPage(this);
                this.inscriptionPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
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
                this.connectionPage.getErrorMessageLabel().setText("Tous les champs doivent \u00eatre remplis.");
                this.connectionPage.getErrorMessageLabel().setVisible(true);
            } else if (!isValidEmail(email)) {
                this.connectionPage.getErrorMessageLabel().setText("L'adresse e-mail n'est pas valide.");
                this.connectionPage.getErrorMessageLabel().setVisible(true);
            } else {
                UserService userService = new UserService();
                if (userService.validateLogin(email, password)) {
                    this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: green;");
                    this.connectionPage.getErrorMessageLabel().setText("Connexion Reussi !");
                    this.connectionPage.getErrorMessageLabel().setVisible(true);
                } else {
                    this.connectionPage.getErrorMessageLabel().setText("Identifiants incorrects.");
                    this.connectionPage.getErrorMessageLabel().setVisible(true);
                }
            }
        }

















        /* PAGE D'INSCRIPTION */

        if (this.inscriptionPage != null && e.getSource() == this.inscriptionPage.getBtnSignUp()) {
            String firstName = this.inscriptionPage.getFirstNameField().getText();
            String lastName = this.inscriptionPage.getLastNameField().getText();
            String email = this.inscriptionPage.getEmailField().getText();
            String password = this.inscriptionPage.getPasswordField().getText();
            String confirmPassword = this.inscriptionPage.getConfirmPasswordField().getText();

            this.inscriptionPage.getErrorMessageLabel().setVisible(false);

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                this.inscriptionPage.getErrorMessageLabel().setText("Tous les champs doivent \u00eatre remplis.");
                this.inscriptionPage.getErrorMessageLabel().setVisible(true);
            } else if (!isValidEmail(email)) {
                this.inscriptionPage.getErrorMessageLabel().setText("L'adresse e-mail n'est pas valide.");
                this.inscriptionPage.getErrorMessageLabel().setVisible(true);
            } else if (!password.equals(confirmPassword)) {
                this.inscriptionPage.getErrorMessageLabel().setText("Les mots de passe ne correspondent pas.");
                this.inscriptionPage.getErrorMessageLabel().setVisible(true);
            } else {
                UserService creationUtilisateur = new UserService();
                if (creationUtilisateur.emailExists(email)) {
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










        /* PAGE MOT DE PASSE OUBLIE */

        if (e.getSource() == this.forgotPassword.getLinkForgotPassword()) {
            try {
                Stage stage = (Stage) this.forgotPassword.getBtnLogin().getScene().getWindow();
                this.connectionPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if(e.getSource() == this.forgotPassword.getBtnLogin()){
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
                    this.forgotPassword.getCodeField().setDisable(false);
                    this.forgotPassword.getErrorMessageLabel().setStyle("-fx-text-fill: green;");
                    this.forgotPassword.getErrorMessageLabel().setText("Un Mail vous a etait envoye ! ");
                    
                    String email = this.forgotPassword.getEmailField().getText();
                    int code = serviceUtilisateur.generateVerificationCode();
                    this.codeString = String.valueOf(code);
                    try {
                        serviceUtilisateur.sendVerificationEmail(email, codeString);
                    } catch (IOException e1) {
                        System.out.println(e1.getMessage());
                    }
                    this.codeSent();

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












        /* REINITIALISATION DU MOT DE PASSE */
        if(e.getSource() == this.resetPassword.getBtnValidate()){
            if(this.resetPassword.getFirstPassword().getText().equals(this.resetPassword.getSecondPassword().getText())){
                if(!this.resetPassword.getFirstPassword().getText().isEmpty() && !this.resetPassword.getSecondPassword().getText().isEmpty()){
                    this.resetPassword.getErrorMessageLabel().setStyle("-fx-text-fill: green;");
                    this.resetPassword.getErrorMessageLabel().setText("Mot de passe change avec succes !");
                    this.resetPassword.getErrorMessageLabel().setVisible(true);
                } else {
                    this.resetPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.resetPassword.getErrorMessageLabel().setText("Le champs de mot de passe doit etre remplie ! ");
                    this.resetPassword.getErrorMessageLabel().setVisible(true);
                }
            } else {
                this.resetPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.resetPassword.getErrorMessageLabel().setText("Les mots de passe ne correspondent pas ! ");
                this.resetPassword.getErrorMessageLabel().setVisible(true);
            }
        }
    }

    private void updateButtonState() {
        if (this.codeSent) {
            this.forgotPassword.getBtnLogin().setText("V\u00e9rifier");
        } else {
            this.forgotPassword.getBtnLogin().setText("Recevoir Code");
        }
    }

    private void codeSent() {
        codeSent = true;
        updateButtonState();
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}