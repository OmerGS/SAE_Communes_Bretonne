package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import view.ConnectionPage;
import view.InscriptionPage;
import data.UserService;

/**
* Class which allows linking the view and the model and controlling the information transiting between them. 
* @author O.Gunes, B.Campion, R.Peron
*/
public class Controller implements EventHandler<ActionEvent> {

    private ConnectionPage connectionPage;
    private InscriptionPage inscriptionPage;

    public Controller(ConnectionPage view){
        this.connectionPage = view;
    }

    public Controller(){
        this.connectionPage = null;
        this.inscriptionPage = null;
    }

    public void setConnectionPage(ConnectionPage connectionPage){
        this.connectionPage = connectionPage;
    }

    public void setInscriptionPage(InscriptionPage inscriptionPage){
        this.inscriptionPage = inscriptionPage;
    }

    @Override
    public void handle(ActionEvent e) {
        /* PAGE DE CONNEXION */
        if (e.getSource() == this.connectionPage.getLinkSignUp()) {
            try {
                Stage stage = (Stage) this.connectionPage.getBtnLogin().getScene().getWindow();
                // Initialisation de la page d'inscription avec le contrôleur actuel
                inscriptionPage = new InscriptionPage(this);
                inscriptionPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } 

        if (e.getSource() == this.connectionPage.getLinkForgotPassword()) {
            System.out.println("Mot de passe oublié !");
        } 

        if (e.getSource() == this.connectionPage.getBtnLogin()) {
            String email = this.connectionPage.getEmailField().getText();
            String password = this.connectionPage.getPasswordField().getText();
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);
        }



        /* PAGE D'INSCRIPTION */
        if(e.getSource() == this.inscriptionPage.getBtnSignUp()){
            String firstName = this.inscriptionPage.getFirstNameField().getText();
            String lastName = this.inscriptionPage.getLastNameField().getText();
            String email = this.inscriptionPage.getEmailField().getText();
            String password = this.inscriptionPage.getPasswordField().getText();
            String confirmPassword = this.inscriptionPage.getConfirmPasswordField().getText();
            
            if(password.equals(confirmPassword)){
                UserService creationUtilisateur = new UserService();
                creationUtilisateur.createUser(lastName, firstName, email, password);
            } else {
                System.out.println("Pas le meme mdp");
            }
        }
    }
}
