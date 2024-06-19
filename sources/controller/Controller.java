package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Timer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import dao.AeroportService;
import dao.AnneeService;
import dao.CommuneService;
import dao.DepartementService;
import dao.GareService;
import dao.UserService;
import data.Aeroport;
import data.Annee;
import data.Commune;
import data.Departement;
import data.Gare;
import data.Utilisateur;
import view.AccountPage;
import view.AdministratorsPage;
import view.CommuneCreatePage;
import view.CommuneDetailsModifPage;
import view.CommuneDetailsPage;
import view.ConnectionPage;
import view.InscriptionPage;
import view.MainPage;
import view.ResetPassword;
import view.TrouverCheminCommune;
import view.misc.CodeAlert;
import view.misc.CustomAlert;
import view.ForgotPassword;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    * A boolean check if a mail code is sent or not.
    */
    private boolean codeSent;

    /**
    * A String which contain the verification code. 
    */
    private String codeString;

    /**
    * An Instance of MainPage page. 
    */
    private MainPage mainPage;

    /**
    * An Instance of TrouverCheminCommune page. 
    */
    private TrouverCheminCommune trouverCheminCommune;

    /**
    * ArrayList containing the most recent communes.
    */
    private ArrayList<Commune> communesRecente;

    /**
    * ArrayList containing all of the communes.
    */
    private List<Commune> communeToute;

    /**
    * The current user, otherwise null
    */
    private Utilisateur currentUser;

    /**
    * ArrayList containing all of Users. 
    */
    private ArrayList<Utilisateur> listeUtilisateur;

    /**
    * Instance of userServices. 
    */
    private UserService userServices;

    /**
    * Instance of AccountPage 
    */
    private AccountPage accountPage;

    /**
    * Instance of CodeAlert - Allow to display a pop-up with a TextField for enter the verification code.
    */
    private CodeAlert envoieCodeValidation;

    /**
    * Store the new mail if the user want to change his password in the AccountPage. 
    */
    private String pendingNewEmail;

    /**
    * List containing all of TrainStation 
    */
    private List<Gare> listeGare;

    /**
    * Instance of GareServices allowing to interract with bdd 
    */
    private GareService gareService;

    /**
    * Instance of CommuneService allowing to interract with the database 
    */
    private CommuneService communeService;

    /**
    * List containing all of Years 
    */
    private List<Annee> listeAnnee;

    /**
    * List containing all of the Airport 
    */
    private List<Aeroport> listeAeroport;

    /**
    * List containing all of the Department 
    */
    private List<Departement> listeDepartement;

    /**
    * Administrators Page 
    */
    private AdministratorsPage administratorsPage;

    /**
    * CommuneDetailsModifPage for change the data of a specific commune 
    */
    private CommuneDetailsModifPage communeDetailsModifPage;

    /**
    * Instance of AnneeService allowing to interract with the database 
    */
    private AnneeService anneeService;

    /**
    * Instance of DepartementService allowing to interract with the database 
    */
    private DepartementService departementService;

    /**
    * Page for create commune 
    */
    private CommuneCreatePage communeCreatePage;


    /**
    * The constructor of Controller. 
    * @param connectionPage
    */
    public Controller(MainPage mainPage) {
        this.communeCreatePage = new CommuneCreatePage();
        this.departementService = new DepartementService();
        this.anneeService = new AnneeService();
        this.communeDetailsModifPage = new CommuneDetailsModifPage();
        this.userServices = new UserService();
        this.listeUtilisateur = this.userServices.loadAllUsers();
        this.connectionPage = new ConnectionPage(this);
        this.inscriptionPage = new InscriptionPage(this);
        this.forgotPassword = new ForgotPassword(this); 
        this.resetPassword = new ResetPassword(this);
        this.trouverCheminCommune = new TrouverCheminCommune(this);
        this.accountPage = new AccountPage(this);
        this.envoieCodeValidation = new CodeAlert(this);
        this.administratorsPage = new AdministratorsPage(this);
        this.mainPage = mainPage;

        this.communeService = new CommuneService();
        this.gareService = new GareService();
    }

    /**
    * The Empty constructor of controller 
    */
    public Controller() {
        this.communeCreatePage = new CommuneCreatePage();
        this.departementService = new DepartementService();
        this.anneeService = new AnneeService();
        this.communeDetailsModifPage = new CommuneDetailsModifPage();
        this.userServices = new UserService();
        this.listeUtilisateur = this.userServices.loadAllUsers();
        this.connectionPage = new ConnectionPage(this);
        this.inscriptionPage = new InscriptionPage(this);
        this.forgotPassword = new ForgotPassword(this); 
        this.resetPassword = new ResetPassword(this);
        this.trouverCheminCommune = new TrouverCheminCommune(this);
        this.accountPage = new AccountPage(this);
        this.administratorsPage = new AdministratorsPage(this);
        this.envoieCodeValidation = new CodeAlert(this);

        this.communeService = new CommuneService();
        this.gareService = new GareService();
    }

    /**
    * Allow to mainPage to set himself in the Controller
    * @param mainPage MainPage instance.
    */
    public void setMainPage(MainPage mainPage){
        this.mainPage = mainPage;
    }









    /* ---------------------------------- */



    /**
    * Method that will detect if a movement is made in one of the views, 
    * and will redirect them to the private handle methods
    * to make the code much more readable.
    *
    * @param ActionEvent Action which is realised.
    */
    @Override
    public void handle(ActionEvent e) {
        // Redirect Actions into ConnectionPage.
        handleConnectionPageActions(e);

        // Redirect Actions into InscriptionPage.
        handleInscriptionPageActions(e);

        // Redirect Actions into ForgotPassword.
        handleForgotPasswordPageActions(e);

        // Redirect Actions into ResetPassword.
        handleResetPasswordPageActions(e);

        // Redirect Actions into MainPage.
        handleMainPageActions(e);

        // Redirect Actions into TrouverCheminCommune.
        handleTrouverCheminCommuneActions(e);

        // Redirect Actions into AccountPage.
        handleAccountPageActions(e);

        // Redirect Actions into CodeAlert.
        handleCodeAlertActions(e);

        // Redirect Actions into AdministratorsPage
        handleAdministratorPageActions(e);

        // Redirect Actions into CommuneDetailsModifPage.
        handleCommuneDetailsModifPage(e);

        // Redirect Actions into CommuneCreatePage.
        handleCommuneCreatePageActions(e);
    }






    //! ---------------- ACCOUNT PAGE
    // The following code is for Actions realised in the AccountPage.


    /**
    * Handle the Actions realised in AccountPage
    * @param e The Action Event
    */
    private void handleAccountPageActions(ActionEvent e) {
        // when disconnect button is clicked, user is null and we redirect user into mainPage.
        if(e.getSource() == this.accountPage.getDisconnectButton()){
            this.currentUser = null;
            returnToMainPage();
        }
    
        // when delete button is clicked we generate a code and send to his email
        // and we pop-up a CodeAlert page for ask him the code before deleting his account.
        if(e.getSource() == this.accountPage.getDeleteButton()){
            int code = this.userServices.generateVerificationCode();
            this.codeString = code + "";
    
            try {
                this.userServices.sendVerificationEmail(currentUser.getEmail(), this.codeString);
            } catch (IOException | MessagingException e1) {
                e1.printStackTrace();
            }
    
            this.envoieCodeValidation = new CodeAlert(this);
            envoieCodeValidation.askCode(currentUser.getEmail());
        }
    
        // when the modify button is clicked, then we change the Label into TextField 
        // Save Button is Visible and Modify Button is Not Visible.
        if(e.getSource() == this.accountPage.getModifyButton()){
            this.accountPage.getNameField().setText(this.accountPage.getNameLabel().getText());
            this.accountPage.getFirstNameField().setText(this.accountPage.getFirstNameLabel().getText());
            this.accountPage.getEmailField().setText(this.accountPage.getEmailLink().getText());
    
            this.accountPage.getNameLabel().setVisible(false);
            this.accountPage.getFirstNameLabel().setVisible(false);
            this.accountPage.getEmailLink().setVisible(false);
    
            this.accountPage.getNameField().setVisible(true);
            this.accountPage.getFirstNameField().setVisible(true);
            this.accountPage.getEmailField().setVisible(true);
    
            this.accountPage.getModifyButton().setVisible(false);
            this.accountPage.getSaveButton().setVisible(true);
        }
    
        // when save button is clicked, we check if the mail has changed, 
        // if mail is changed we check if mail is valid and doesn't exist in the database
        // if these 2 conditions is valid, we send a verification code to prove the user is the owner of the mail
        
        // Otherwise if mail doesn't changed we change the name and the firstname in the object Utilisateur and in the Database.
        if(e.getSource() == this.accountPage.getSaveButton()){
            String newEmail = this.accountPage.getEmailField().getText();

            if(isValidEmail(newEmail)){
                if(!this.currentUser.getEmail().equals(newEmail) && !this.userServices.emailExists(newEmail)){
                    int code = this.userServices.generateVerificationCode();
                    this.codeString = code + "";
                    this.pendingNewEmail = newEmail;  // Store the new email temporarily
        
                    try {
                        this.userServices.sendVerificationEmail(newEmail, this.codeString);
                    } catch (IOException | MessagingException e1) {
                        e1.printStackTrace();
                    }
        
                    this.envoieCodeValidation = new CodeAlert(this);
                    envoieCodeValidation.askCode(newEmail);
                } else {
                    CustomAlert.showAlert("Erreur", "Mail deja existant");
                }
            } else if(this.currentUser.getEmail().equals(this.currentUser.getEmail())) {
                this.accountPage.getNameLabel().setText(this.accountPage.getNameField().getText());
                this.accountPage.getFirstNameLabel().setText(this.accountPage.getFirstNameField().getText());
                this.accountPage.getEmailLink().setText(this.accountPage.getEmailField().getText());

                this.accountPage.getNameLabel().setVisible(true);
                this.accountPage.getFirstNameLabel().setVisible(true);
                this.accountPage.getEmailLink().setVisible(true);

                this.accountPage.getNameField().setVisible(false);
                this.accountPage.getFirstNameField().setVisible(false);
                this.accountPage.getEmailField().setVisible(false);

                this.accountPage.getModifyButton().setVisible(true);
                this.accountPage.getSaveButton().setVisible(false);

                String surname = this.accountPage.getNameLabel().getText();
                String name = this.accountPage.getFirstNameLabel().getText();
                String email = this.accountPage.getEmailLink().getText();
                this.userServices.updateUser(this.currentUser.getEmail(), surname, name, email);

                this.currentUser.setNom(surname);
                this.currentUser.setPrenom(name);
            }
        }
    }
    
    /**
    * Change the active stage for redirect user into MainPage. 
    */
    public void returnToMainPage(){
        try {
            Stage stage = (Stage) this.accountPage.getEmailLink().getScene().getWindow();
            this.mainPage.start(stage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }








    //! ---------------- PAGE TO HANDLE CODE SENDING
    // Following code is for actions in CodeAlert.


    /**
    * Handle the actions in CodeAlert 
    * @param e The Action Event
    */
    private void handleCodeAlertActions(ActionEvent e) {
        // when user is validating the code. (The user validate code only if he change his mail or delete his account)
        if(e.getSource() == this.envoieCodeValidation.getCloseButton()){

            // we check if the user input match with the generated code.
            if(this.envoieCodeValidation.getCodeField().getText().equals(this.codeString)){
                //if code match

                // if the user want to change his email.
                // We update user informations and we change the view for replace input by Label.
                if (this.pendingNewEmail != null) {
                    String surname = this.accountPage.getNameLabel().getText();
                    String name = this.accountPage.getFirstNameLabel().getText();
                    
                    this.userServices.updateUser(this.currentUser.getEmail(), surname, name, pendingNewEmail);
                    this.currentUser.setNom(surname);
                    this.currentUser.setPrenom(name);
                    this.currentUser.setEmail(this.pendingNewEmail);

                    this.accountPage.getEmailLink().setText(this.pendingNewEmail);
                    this.pendingNewEmail = null;

                    this.accountPage.getNameLabel().setText(this.accountPage.getNameField().getText());
                    this.accountPage.getFirstNameLabel().setText(this.accountPage.getFirstNameField().getText());
                    this.accountPage.getEmailLink().setText(this.accountPage.getEmailField().getText());

                    this.accountPage.getNameLabel().setVisible(true);
                    this.accountPage.getFirstNameLabel().setVisible(true);
                    this.accountPage.getEmailLink().setVisible(true);

                    this.accountPage.getNameField().setVisible(false);
                    this.accountPage.getFirstNameField().setVisible(false);
                    this.accountPage.getEmailField().setVisible(false);

                    this.accountPage.getModifyButton().setVisible(true);
                    this.accountPage.getSaveButton().setVisible(false);
                } else {
                    // if the user don't want to change his mail, otherwise he wanna delete his account.
                    // drop user from database and from listeUtilisateur containing all of users.
                    this.userServices.dropUser(currentUser.getEmail());
                    this.listeUtilisateur.remove(currentUser);

                    // and so the account of user don't exists anymore the currentUser is now null.
                    this.currentUser = null;
                }

                // Closing the codeValidation page.
                Stage stage2 = (Stage) this.envoieCodeValidation.getCloseButton().getScene().getWindow();
                stage2.close();

                // Launching MainPage.
                Stage stage = (Stage) this.accountPage.getDeleteButton().getScene().getWindow();
                this.mainPage.start(stage);
            } else {
                this.envoieCodeValidation.getAlertLabel().setText("Le code est incorrect !");
                this.envoieCodeValidation.getAlertLabel().setStyle("-fx-text-fill: red;");
            }
        }
    }
    
















    //! ---------------- MAINPAGE METHOD
    // Following code is for handle MainPage Actions.


    /**
    * Handle the MainPage actions. 
    * @param e The Action Event
    */
    private void handleMainPageActions(ActionEvent e) {
        // if search field is triggered.
        if(e.getSource() == this.mainPage.getSearchField()){
            // get the text from search field.
            String searchText = this.mainPage.getSearchField().getText().trim();

            // we handle search event with the text of input.
            handleSearchEvent(searchText);
        }

        // when user clicked to CheminLePlusCourt button we launch trouverCheminCommune page.
        if(e.getSource() == this.mainPage.getButtonCheminLePlusCourt()){
            try {
                Stage stage = (Stage) this.mainPage.getButtonCheminLePlusCourt().getScene().getWindow();
                this.trouverCheminCommune.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // if user clicked on the button finistere we display only the commune in the Finistere department.
        if(e.getSource() == this.mainPage.getFinistereFilterButton()){
            applyFilter(29);
            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

            this.mainPage.getFinistereFilterButton().setStyle(buttonStyleFocused);
            this.mainPage.getCoteArmorFilterButton().setStyle(buttonStyle);
            this.mainPage.getMorbihanFilterButton().setStyle(buttonStyle);
            this.mainPage.getIlleEtVilaineFilterButton().setStyle(buttonStyle);
            this.mainPage.getToutesLesCommunes().setStyle(buttonStyle);
        }

        // if user clicked on the button Morbihan, we display only the commune in the Morbihan department.
        if(e.getSource() == this.mainPage.getMorbihanFilterButton()){
            applyFilter(56);

            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

                this.mainPage.getFinistereFilterButton().setStyle(buttonStyle);
                this.mainPage.getCoteArmorFilterButton().setStyle(buttonStyle);
                this.mainPage.getMorbihanFilterButton().setStyle(buttonStyleFocused);
                this.mainPage.getIlleEtVilaineFilterButton().setStyle(buttonStyle);
                this.mainPage.getToutesLesCommunes().setStyle(buttonStyle);
        }

        // if user clicked on the button CoteArmor, we display only the commune in the CoteArmor department.
        if(e.getSource() == this.mainPage.getCoteArmorFilterButton()){
            applyFilter(22);

            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

                this.mainPage.getFinistereFilterButton().setStyle(buttonStyle);
                this.mainPage.getCoteArmorFilterButton().setStyle(buttonStyleFocused);
                this.mainPage.getMorbihanFilterButton().setStyle(buttonStyle);
                this.mainPage.getIlleEtVilaineFilterButton().setStyle(buttonStyle);
                this.mainPage.getToutesLesCommunes().setStyle(buttonStyle);
        }

        // if user clicked on the button IlleEtVillaine, we display only the commune in the IlleEtVillaine department.
        if(e.getSource() == this.mainPage.getIlleEtVilaineFilterButton()){
            applyFilter(35);

            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

            this.mainPage.getFinistereFilterButton().setStyle(buttonStyle);
            this.mainPage.getCoteArmorFilterButton().setStyle(buttonStyle);
            this.mainPage.getMorbihanFilterButton().setStyle(buttonStyle);
            this.mainPage.getIlleEtVilaineFilterButton().setStyle(buttonStyleFocused);
            this.mainPage.getToutesLesCommunes().setStyle(buttonStyle);
        }

        // if the user clicked on all communes, we display all communes.
        if(e.getSource() == this.mainPage.getToutesLesCommunes()){
            getCommune();

            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

            this.mainPage.getFinistereFilterButton().setStyle(buttonStyle);
            this.mainPage.getCoteArmorFilterButton().setStyle(buttonStyle);
            this.mainPage.getMorbihanFilterButton().setStyle(buttonStyle);
            this.mainPage.getIlleEtVilaineFilterButton().setStyle(buttonStyle);
            this.mainPage.getToutesLesCommunes().setStyle(buttonStyle);
            this.mainPage.getToutesLesCommunes().setStyle(buttonStyleFocused);
        }

        // If the user clicked on reload database, we call database for had the data stored in the database.
        if(e.getSource() == this.mainPage.getReloadDatabase()){
            try {
                this.listeAnnee = this.anneeService.getAllAnnee();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            this.mainPage.loadCommunes(getCommunesFromDataBase());
            this.administratorsPage.loadCommunes(getCommunesFromDataBase());
            this.listeUtilisateur = this.userServices.loadAllUsers();
            try {
                this.listeGare = this.gareService.getAllGares();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            CustomAlert.showAlert("Recuperation", "La recuperation depuis la base de donn\u00e9e est termin\u00e9");
        }

        if(e.getSource() == this.mainPage.getExportButton()){
            exportData();
            CustomAlert.showAlert("Export des donn\u00e9es", "Les donn\u00e9es ont bien \u00e9tait export\u00e9");
        }

        if(e.getSource() == this.mainPage.getEditData()){
            Stage stage = (Stage) this.mainPage.getExportButton().getScene().getWindow();
            this.administratorsPage.start(stage);
        }
    }

    /**
    * Allow to search a commune and update the view, from the list of commune. 
    * @param searchText User input
    */
    public void handleSearchEvent(String searchText) {
        List<Commune> filteredCommunes = getFilteredCommunes(searchText);
        this.mainPage.updateCommunesListView(filteredCommunes);
        this.mainPage.getNumberOfRow().setText(filteredCommunes.size() + " r\u00e9sultats");
    }   

    /**
    * Open a pop-up styled page for had all data from a commune.
    * @param commune
    */
    public void showCommuneDetails(Commune commune){
        try {
            CommuneDetailsPage.showCommune(commune, this);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
    * search all communes starts with the users input and put them into an ArrayList
    * @param searchText the name of the commune we search.
    * @return An ArrayList of commune.
    */
    public ArrayList<Commune> getFilteredCommunes(String searchText) {
        ArrayList<Commune> allCommunes = this.communesRecente;
        ArrayList<Commune> filteredCommunes = new ArrayList<>();
        
        
        String lowerCaseSearchText = searchText.toLowerCase();
        for (Commune commune : allCommunes) {
            if (commune.getNomCommune().toLowerCase().startsWith(lowerCaseSearchText)) {
                filteredCommunes.add(commune);
            }
        }
        //Change result label with the numbers of commune found.
        this.mainPage.getNumberOfRow().setText(filteredCommunes.size() + " r\u00e9sultats");
        return filteredCommunes;
    }

    /**
    * Search commune in the department specified in parameters, create an ArrayList containing all of these commune.
    * and change the mainPage list with the filteredCommune list.
    * @param idDep The Department ID.
    */
    public void applyFilter(int idDep) {
        ArrayList<Commune> filterList = new ArrayList<Commune>();

        for (Commune commune : this.communesRecente) {
            if (commune.getDepartement().getIdDep() == idDep) {
                filterList.add(commune);
            }
        }        

        mainPage.updateCommunesListView(filterList);
        this.mainPage.getNumberOfRow().setText(filterList.size() + " r\u00e9sultats");
    }

    /**
    * Change the list of commune displayed in the MainPage, with all communes (we display in the mainPage only most recent commune). 
    */
    public void getCommune(){
        this.mainPage.updateCommunesListView(this.communesRecente);
        this.mainPage.getNumberOfRow().setText(this.communesRecente.size() + " r\u00e9sultats");
    }
    

    /**
    * If user clicked on account image, there are two cases.
    * First the user is not connected : We redirect user to the connectionPage.
    * Second the user is connected : We redirect user to account page.
    */
    public void connectionClicked() {
        try {
            if (this.currentUser != null) {
                System.out.println("Utilisateur actuel : " + this.currentUser.getEmail());
                Stage stage = (Stage) this.mainPage.getSearchField().getScene().getWindow();
                this.accountPage.start(stage);

                this.accountPage.getFirstNameLabel().setText(currentUser.getPrenom());
                this.accountPage.getNameLabel().setText(currentUser.getNom());
                this.accountPage.getEmailLink().setText(currentUser.getEmail());
                String utilisateurEtat = "";
                if(this.userServices.userIsAdmin(currentUser.getEmail()) == 1){
                    this.currentUser.setAdmin(true);
                    utilisateurEtat = "Administrateur";
                } else {
                    this.currentUser.setAdmin(false);
                    utilisateurEtat = "Utilisateur";
                }
                this.accountPage.getAdmin().setText(utilisateurEtat);
            } else {
                System.out.println("Utilisateur non connect\u00e9.");
                Stage stage = (Stage) this.mainPage.getSearchField().getScene().getWindow();
                this.connectionPage.start(stage);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    







































    //! ---------------- AdministratorPage
    // following code is for actions realised in AdministratorPage



    /**
    * Handle action realised in Administration Page
    * 
    * @param e The Action Event
    */
    private void handleAdministratorPageActions(ActionEvent e) {
        // if search field is triggered.
        if(e.getSource() == this.administratorsPage.getSearchField()){
            // get the text from search field.
            String searchText = this.administratorsPage.getSearchField().getText().trim();

            // we handle search event with the text of input.
            handleSearchEventAdminPage(searchText);
        }

        // when user clicked to CheminLePlusCourt button we launch trouverCheminCommune page.
        if(e.getSource() == this.administratorsPage.getButtonCheminLePlusCourt()){
            try {
                Stage stage = (Stage) this.administratorsPage.getButtonCheminLePlusCourt().getScene().getWindow();
                this.trouverCheminCommune.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // if user clicked on the button finistere we display only the commune in the Finistere department.
        if(e.getSource() == this.administratorsPage.getFinistereFilterButton()){
            applyFilterAdminPage(29);
            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

            // we change style of each button to had a different border in only the selected button
            this.administratorsPage.getFinistereFilterButton().setStyle(buttonStyleFocused);
            this.administratorsPage.getCoteArmorFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getMorbihanFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getIlleEtVilaineFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getToutesLesCommunes().setStyle(buttonStyle);
        }

        // if user clicked on the button Morbihan, we display only the commune in the Morbihan department.
        if(e.getSource() == this.administratorsPage.getMorbihanFilterButton()){
            applyFilterAdminPage(56);

            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

                // we change style of each button to had a different border in only the selected button
                this.administratorsPage.getFinistereFilterButton().setStyle(buttonStyle);
                this.administratorsPage.getCoteArmorFilterButton().setStyle(buttonStyle);
                this.administratorsPage.getMorbihanFilterButton().setStyle(buttonStyleFocused);
                this.administratorsPage.getIlleEtVilaineFilterButton().setStyle(buttonStyle);
                this.administratorsPage.getToutesLesCommunes().setStyle(buttonStyle);
        }

        // if user clicked on the button CoteArmor, we display only the commune in the CoteArmor department.
        if(e.getSource() == this.administratorsPage.getCoteArmorFilterButton()){
            applyFilterAdminPage(22);

            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

                // we change style of each button to had a different border in only the selected button
                this.administratorsPage.getFinistereFilterButton().setStyle(buttonStyle);
                this.administratorsPage.getCoteArmorFilterButton().setStyle(buttonStyleFocused);
                this.administratorsPage.getMorbihanFilterButton().setStyle(buttonStyle);
                this.administratorsPage.getIlleEtVilaineFilterButton().setStyle(buttonStyle);
                this.administratorsPage.getToutesLesCommunes().setStyle(buttonStyle);
        }

        // if user clicked on the button IlleEtVillaine, we display only the commune in the IlleEtVillaine department.
        if(e.getSource() == this.administratorsPage.getIlleEtVilaineFilterButton()){
            applyFilterAdminPage(35);

            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

            // we change style of each button to had a different border in only the selected button    
            this.administratorsPage.getFinistereFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getCoteArmorFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getMorbihanFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getIlleEtVilaineFilterButton().setStyle(buttonStyleFocused);
            this.administratorsPage.getToutesLesCommunes().setStyle(buttonStyle);
        }

        // if the user clicked on all communes, we display all communes.
        if(e.getSource() == this.administratorsPage.getToutesLesCommunes()){
            getCommuneAdminPage();

            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

            // we change style of each button to had a different border in only the selected button    
            this.administratorsPage.getFinistereFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getCoteArmorFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getMorbihanFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getIlleEtVilaineFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getToutesLesCommunes().setStyle(buttonStyle);
            this.administratorsPage.getToutesLesCommunes().setStyle(buttonStyleFocused);
        }

        // If the user clicked on reload database, we call database for had the data stored in the database.
        if(e.getSource() == this.administratorsPage.getReloadDatabase()){
            this.administratorsPage.loadCommunes(getCommunesFromDataBase());
            this.listeUtilisateur = this.userServices.loadAllUsers();
            try {
                this.listeGare = this.gareService.getAllGares();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            CustomAlert.showAlert("Chargement de la base de donn\u00e9es", "La base de donn\u00e9e a \u00e9t\u00e9 recharg\u00e9");	
        }

        //Export data if the user want it.
        if(e.getSource() == this.administratorsPage.getExportButton()){
            exportData();
            CustomAlert.showAlert("Export des donn\u00e9es", "Les donn\u00e9es ont bien \u00e9tait export\u00e9");
        }

        if(e.getSource() == this.administratorsPage.getEditData()){
            Stage stage = (Stage) this.administratorsPage.getExportButton().getScene().getWindow();
            this.mainPage.start(stage);
        }

        if(e.getSource() == this.administratorsPage.getCreateCommune()){
            this.communeCreatePage.showCommune(this);
        }
    }


    /**
    * Allow to search a commune and update the view, from the list of commune. 
    * @param searchText User input
    */
    public void handleSearchEventAdminPage(String searchText) {
        List<Commune> filteredCommunes = getFilteredCommunesAdminPage(searchText);
        this.administratorsPage.updateCommunesListView(filteredCommunes);
        this.administratorsPage.getNumberOfRow().setText(filteredCommunes.size() + " r\u00e9sultats");
    }  

    /**
    * Search commune in the department specified in parameters, create an ArrayList containing all of these commune.
    * and change the mainPage list with the filteredCommune list.
    * @param idDep The Department ID.
    */
    public void applyFilterAdminPage(int idDep) {
        ArrayList<Commune> filterList = new ArrayList<Commune>();

        for (Commune commune : this.communesRecente) {
            if (commune.getDepartement().getIdDep() == idDep) {
                filterList.add(commune);
            }
        }        

        this.administratorsPage.updateCommunesListView(filterList);
        this.administratorsPage.getNumberOfRow().setText(filterList.size() + " r\u00e9sultats");
    }

     /**
    * Change the list of commune displayed in the MainPage, with all communes (we display in the mainPage only most recent commune). 
    */
    public void getCommuneAdminPage(){
        this.administratorsPage.updateCommunesListView(this.communesRecente);
        this.administratorsPage.getNumberOfRow().setText(this.communesRecente.size() + " r\u00e9sultats");
    }

     /**
    * search all communes starts with the users input and put them into an ArrayList
    * @param searchText the name of the commune we search.
    * @return An ArrayList of commune.
    */
    public ArrayList<Commune> getFilteredCommunesAdminPage(String searchText) {
        ArrayList<Commune> allCommunes = this.communesRecente;
        ArrayList<Commune> filteredCommunes = new ArrayList<>();
        
        
        String lowerCaseSearchText = searchText.toLowerCase();
        for (Commune commune : allCommunes) {
            if (commune.getNomCommune().toLowerCase().startsWith(lowerCaseSearchText)) {
                filteredCommunes.add(commune);
            }
        }
        //Change result label with the numbers of commune found.
        this.administratorsPage.getNumberOfRow().setText(filteredCommunes.size() + " r\u00e9sultats");
        return filteredCommunes;
    }

    /**
    * Open a pop-up styled page for had all data from a commune.
    * @param commune
    */
    public void editCommuneDetails(Commune commune){
        try {
            this.communeDetailsModifPage.showCommune(commune, this);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }















    // ! ---------------- CommuneCreatePage

    /**
    * Handle action realised in CommuneCreatePage
    * @param e The ActionEvent.
    */
    private void handleCommuneCreatePageActions(ActionEvent e){
        // When save button is clicked
        if (e.getSource() == this.communeCreatePage.getSaveButton()) {

            //Get all of Field.
            String nomCommune = this.communeCreatePage.getNomTextFieldValue();        
            String idCommune = this.communeCreatePage.getIdTextFieldValue();        
            String departementCommune = this.communeCreatePage.getDepartementComboBox().getValue();        
            String anneeDeDonnees = this.communeCreatePage.getAnneeTextFieldValue();        
            String populationCommune = this.communeCreatePage.getPopulationTextFieldValue();        
            String depenseCulturelleCommune = this.communeCreatePage.getDepCulturellesTextFieldValue();        
            String budgetTotalCommune = this.communeCreatePage.getBudgetTotalField().getText();        
            String nbMaisonCommune = this.communeCreatePage.getNbMaisonsText();        
            String nbAppartementCommune = this.communeCreatePage.getNbAppartementsText();        
            String prixMoyenCommune = this.communeCreatePage.getPrixM2MoyenText();        
            String prixM2MoyenCommune = this.communeCreatePage.getPrixM2MoyenText();
            String surfaceMoyenneCommune = this.communeCreatePage.getSurfaceMoyenneText();

            // Check if the year is in the database.
            List<Annee> allYearsFromDB = getYearsFromDatabase();
            Annee anneeTrouve = null;
            boolean anneeExiste = false;
            for (Annee an : allYearsFromDB) {
                if (an.getAnnee() == Integer.parseInt(anneeDeDonnees)) {
                    anneeExiste = true;
                    anneeTrouve = an;
                    break;
                }
            }
        
            // check if the id of the new commune is already in the database.
            if(!idIsInDatabase(Integer.parseInt(idCommune))){

                //check if the year is already in the database.
                if (anneeExiste) {
                    // Year exists
                    try {
                        //Recover the departement
                        Departement departementNouvelle = getDepartementByName(departementCommune);
                        Commune communeNouvelle = new Commune(
                            null, anneeTrouve, Integer.parseInt(idCommune), nomCommune, 
                            Integer.parseInt(nbMaisonCommune), Integer.parseInt(nbAppartementCommune), 
                            Integer.parseInt(prixMoyenCommune), Integer.parseInt(prixM2MoyenCommune), 
                            Integer.parseInt(surfaceMoyenneCommune), Integer.parseInt(depenseCulturelleCommune), 
                            Integer.parseInt(budgetTotalCommune), Integer.parseInt(populationCommune), departementNouvelle);
                        
                        this.communeService.insertCommuneEtDonneesAnnuelles(communeNouvelle);
                        CustomAlert.showAlert("Succ\u00e8s", "Commune mise \u00e0 jour avec succ\u00e8s.");
                    } catch (NumberFormatException e1) {	
                        CustomAlert.showAlert("Erreur", "Les nombres doivent \u00eatre des entiers valides");
                    } catch (Exception ex) {
                        CustomAlert.showAlert("Erreur", "Une erreur inattendue s'est produite");
                    }
                } else {
                    try {
                        // Recover the department
                        Departement departementNouvelle = getDepartementByName(departementCommune);
                        Annee anneeNouvelle = new Annee(Integer.parseInt(anneeDeDonnees), 1);
    
                        Commune communeNouvelle = new Commune(
                            null, anneeNouvelle, Integer.parseInt(idCommune), nomCommune, 
                            Integer.parseInt(nbMaisonCommune), Integer.parseInt(nbAppartementCommune), 
                            Integer.parseInt(prixMoyenCommune), Integer.parseInt(prixM2MoyenCommune), 
                            Integer.parseInt(surfaceMoyenneCommune), Integer.parseInt(depenseCulturelleCommune), 
                            Integer.parseInt(budgetTotalCommune), Integer.parseInt(populationCommune), departementNouvelle);
    
                        this.anneeService.insertAnnee(anneeNouvelle);
                        this.communeService.insertCommuneEtDonneesAnnuelles(communeNouvelle);
    
                        CustomAlert.showAlert("Succ\u00e8s", "Commune mise \u00e0 jour avec succ\u00e8s.");
                    } catch (NumberFormatException e1) {	
                        CustomAlert.showAlert("Erreur", "Les nombres doivent \u00eatre des entiers valides");
                    } catch (Exception ex) {
                        CustomAlert.showAlert("Erreur", "Une erreur inattendue s'est produite");
                    }
                }
            } else {
                CustomAlert.showAlert("Erreur", "L'ID de la commune est d\u00e9j\u00e0 dans la base de donn\u00e9e");
            }
            
        }
    }













    // ! ---------------- CommuneDetailsModifPage
    //Following code is for CommuneDetailsModifPage actions handling.

    /**
    * Handle the action realised in CommuneDetailsModifPage
    * 
    * @param e The Action Event
    */
    private void handleCommuneDetailsModifPage(ActionEvent e){
        //the user want to save his changes
        if (e.getSource() == this.communeDetailsModifPage.getSaveButton()) {
            try {
                // check if all of field is not empty
                if (this.communeDetailsModifPage.getIdRepLabel().getText() == null || this.communeDetailsModifPage.getIdRepLabel().getText().isEmpty() ||
                    this.communeDetailsModifPage.getNbMaisonsText() == null || this.communeDetailsModifPage.getNbMaisonsText().isEmpty() ||
                    this.communeDetailsModifPage.getNbAppartementsText() == null || this.communeDetailsModifPage.getNbAppartementsText().isEmpty() ||
                    this.communeDetailsModifPage.getPrixMoyenText() == null || this.communeDetailsModifPage.getPrixMoyenText().isEmpty() ||
                    this.communeDetailsModifPage.getPrixM2MoyenText() == null || this.communeDetailsModifPage.getPrixM2MoyenText().isEmpty() ||
                    this.communeDetailsModifPage.getSurfaceMoyenneText() == null || this.communeDetailsModifPage.getSurfaceMoyenneText().isEmpty() ||
                    this.communeDetailsModifPage.getDepCulturellesTextFieldValue() == null || this.communeDetailsModifPage.getDepCulturellesTextFieldValue().isEmpty() ||
                    this.communeDetailsModifPage.getBudgetTotalField().getText() == null || this.communeDetailsModifPage.getBudgetTotalField().getText().isEmpty() ||
                    this.communeDetailsModifPage.getPopulationTextFieldValue() == null || this.communeDetailsModifPage.getPopulationTextFieldValue().isEmpty() ||
                    this.communeDetailsModifPage.getAnneeTextFieldValue() == null || this.communeDetailsModifPage.getAnneeTextFieldValue().isEmpty()) 
                {
                    // else we display an error.
                    CustomAlert.showAlert("Erreur", "Tous les champs doivent \u00eatre remplis");
                } else {
                    try {
                        // Parsing all data, for check if there are correct.
                        String nomCommune = this.communeDetailsModifPage.getNameLabel().getText();
                        int id = Integer.parseInt(this.communeDetailsModifPage.getIdRepLabel().getText());
                        int nbMaisons = Integer.parseInt(this.communeDetailsModifPage.getNbMaisonsText());
                        int nbAppartements = Integer.parseInt(this.communeDetailsModifPage.getNbAppartementsText());
                        int prixMoyen = Integer.parseInt(this.communeDetailsModifPage.getPrixMoyenText());
                        int prixM2Moyen = Integer.parseInt(this.communeDetailsModifPage.getPrixM2MoyenText());
                        int surfaceMoyenne = Integer.parseInt(this.communeDetailsModifPage.getSurfaceMoyenneText());
                        int depCulturelles = Integer.parseInt(this.communeDetailsModifPage.getDepCulturellesTextFieldValue());
                        int budgetTotal = Integer.parseInt(this.communeDetailsModifPage.getBudgetTotalField().getText());
                        int population = Integer.parseInt(this.communeDetailsModifPage.getPopulationTextFieldValue());
                        int annee = Integer.parseInt(this.communeDetailsModifPage.getAnneeTextFieldValue());
        
                        // Recover the years available for this commune (years with data inside)
                        Commune communeAvantModif = this.communeDetailsModifPage.getCommuneAvantModif();
                        ArrayList<Integer> existingYears = getYearsForCommune(communeAvantModif);
                        List<Annee> allYearsFromDB = getYearsFromDatabase();
        
                        // Checking the available years
                        if (existingYears.contains(annee)) {
                            // Update commune and DonnéesAnnuelles
                            this.communeService.updateCommuneEtDonneesAnnuelles(id, nbMaisons, nbAppartements, prixMoyen, prixM2Moyen, surfaceMoyenne, depCulturelles, budgetTotal, population, annee);
        
                            //Getting the right commune for modify the object.
                            Commune communeWithGoodYear = getCommuneForYearAndCommune(communeAvantModif.getNomCommune(), annee);
        
                            communeWithGoodYear.setNbMaison(nbMaisons);
                            communeWithGoodYear.setNbAppart(nbAppartements);
                            communeWithGoodYear.setPrixMoyen(prixMoyen);
                            communeWithGoodYear.setPrixM2Moyen(prixM2Moyen);
                            communeWithGoodYear.setSurfaceMoy(surfaceMoyenne);
                            communeWithGoodYear.setDepCulturellesTotales(depCulturelles);
                            communeWithGoodYear.setBudgetTotal(budgetTotal);
                            communeWithGoodYear.setPopulation(population);
        
                            Annee anneeAvantModif = communeWithGoodYear.getAnnee();
                            anneeAvantModif.setAnnee(annee);
                            communeWithGoodYear.setAnnee(anneeAvantModif);
        
                            //update the list of MainPage and AdministratorPage.
                            this.administratorsPage.updateCommunesListView(this.communesRecente);
                            this.mainPage.updateCommunesListView(this.communesRecente);
        
                            CustomAlert.showAlert("Modification Commune", "Commune modifi\u00e9e avec succ\u00e8s !");
                        } else {
                            // Check if the years is in the database.
                            Annee anneeBDD = null;
                            for (Annee an : allYearsFromDB) {
                                if (an.getAnnee() == annee) {
                                    anneeBDD = an;
                                    break;
                                }
                            }
        
                            if (anneeBDD != null) {
                                // Year is in the database
                                Departement departementNouvelle = getDepartementById(Integer.parseInt(this.communeDetailsModifPage.getDepRepLabel().getText()));
                                
                                Commune communeNouvelle = new Commune(
                                    null, anneeBDD, id, nomCommune, nbMaisons, 
                                    nbAppartements, prixMoyen, prixM2Moyen, surfaceMoyenne, 
                                    depCulturelles, budgetTotal, population, departementNouvelle);
        
                                this.communeService.insertCommuneEtDonneesAnnuellesNewYear(communeNouvelle);
                                    
                                this.communeToute.add(communeNouvelle);
        
                                this.administratorsPage.updateCommunesListView(this.communesRecente);
                                this.mainPage.updateCommunesListView(this.communesRecente);
        
                                CustomAlert.showAlert("Nouvelle donnée ajout\u00e9e", "Ajout avec succ\u00e9s.");
                            } else {
                                //year isn't in the database.
                                int tauxInflation = 1;

                                Annee anneeCreer = new Annee(Integer.parseInt(this.communeDetailsModifPage.getAnneeTextFieldValue()), tauxInflation);
                                this.anneeService.insertAnnee(anneeCreer);

                                //recover department by his id.
                                Departement departementNouvelle = getDepartementById(Integer.parseInt(this.communeDetailsModifPage.getDepRepLabel().getText()));
                                
                                Commune communeNouvelle = new Commune(
                                    null, anneeCreer, id, nomCommune, nbMaisons, 
                                    nbAppartements, prixMoyen, prixM2Moyen, surfaceMoyenne, 
                                    depCulturelles, budgetTotal, population, departementNouvelle);
        
                                this.communeService.insertCommuneEtDonneesAnnuellesNewYear(communeNouvelle);
                                    
                                this.communeToute.add(communeNouvelle);
        
                                this.administratorsPage.updateCommunesListView(this.communesRecente);
                                this.mainPage.updateCommunesListView(this.communesRecente);

                                CustomAlert.showAlert("Ajout d'une nouvelle ann\u00e9e", "Ajout effectu\u00e9");
                            }
                        }
                    } catch (NumberFormatException e1) {	
                        CustomAlert.showAlert("Erreur", "Les nombres doivent \u00eatre des entiers valides");
                    }
                }
            } catch (Exception ex) {
                CustomAlert.showAlert("Erreur", "Une erreur inattendue s'est produite");
            }
        }     
    }




    













































    //! ---------------- TROUVERCHEMINCOMMUNE METHOD
    //following code is for handle all action in Trou


    /**
    * Handle the action of TrouverCheminCommune. 
    * @param e The Action Event
    */
    private void handleTrouverCheminCommuneActions(ActionEvent e){
        // when button is clicked, we send those two communes in findPath method.
        if(e.getSource() == this.trouverCheminCommune.getButton()){
            String firstCommuneText = this.trouverCheminCommune.getStartCommuneName().getText();
            String endCommuneText = this.trouverCheminCommune.getEndCommuneName().getText();

            findPath(firstCommuneText, endCommuneText);
        }

        if(e.getSource() == this.trouverCheminCommune.getExportDataButton()){
            exportData();
            CustomAlert.showAlert("Export des donn\u00e9es", "Les donn\u00e9es ont bien \u00e9tait export\u00e9");
        }

        if(e.getSource() == this.trouverCheminCommune.getMainPageButton()){
            try {
                Stage stage = (Stage) this.trouverCheminCommune.getEndCommuneName().getScene().getWindow();
                this.mainPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if(e.getSource() == this.trouverCheminCommune.getEditData()){
            try {
                Stage stage = (Stage) this.trouverCheminCommune.getEndCommuneName().getScene().getWindow();
                this.administratorsPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if(e.getSource() == this.trouverCheminCommune.getReloadDatabaseButton()){
            this.communeToute = getCommunesFromDataBase();
            this.listeUtilisateur = this.userServices.loadAllUsers();
            try {
                this.listeGare = this.gareService.getAllGares();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            CustomAlert.showAlert("Chargement Base de Donn\u00e9es", "La base de donn\u00e9es a \u00e9tait recharg\u00e9");
        }



        
    }

    /**
    * Finds the path between two communes.
    * 
    * This method searches for a path between the start and end communes specified
    * by their names. It updates the result label with appropriate messages based 
    * on the outcome of the search. If an error occurs during the search, it catches
    * the SQLException and updates the result label with an error message.
    * 
    * @param startCommuneName The name of the starting commune.
    * @param endCommuneName The name of the destination commune.
    */
    public void findPath(String startCommuneName, String endCommuneName) {
        try {
            // Step 1: Retrieve the starting and ending communes by name
            Commune startCommune = this.communeService.getCommuneByName(startCommuneName, this.communeToute);
            Commune endCommune = this.communeService.getCommuneByName(endCommuneName, this.communeToute);

            // Step 2: Check if either the start or end commune is not found
            if (startCommune == null || endCommune == null) {
                this.trouverCheminCommune.setResultLabel("Commune de d\u00e9part ou d'arriv\u00e9e introuvable.");
                return;
            }

            // Step 3: Check if the start and end communes are the same
            if (startCommune.getNomCommune().equalsIgnoreCase(endCommune.getNomCommune())) {
                this.trouverCheminCommune.setResultLabel("Commune de d\u00e9part ne peut pas être la m\u00eame que la commune d'arriv\u00e9e.");
                return;
            }

            // Step 4: Find the path between the start and end communes
            List<Commune> path = this.communeService.cheminEntreCommune(startCommune.getIdCommune(), endCommune.getIdCommune(), this.communeToute);

            // Step 5: Check if a path is found
            if (path.isEmpty()) {
                this.trouverCheminCommune.setResultLabel("Aucun chemin trouvé entre les deux communes.");
            } else {
                // Step 6: Retrieve images for the communes along the path
                ServerConnectionManager connectionManager = loadConnectionManagerFromProperties("../properties/server.properties");
                List<Integer> cityIds = new ArrayList<>();
                for (Commune commune : path) {
                    cityIds.add(commune.getIdCommune());
                }
                connectionManager.retrieveImage(cityIds);
            }
        } catch (SQLException e) {
            // Step 7: Catch any SQLException that occurs during the process
            e.printStackTrace();
            this.trouverCheminCommune.setResultLabel("Erreur lors de la recherche du chemin.");
        }
    }



    /**
    * Allow to initiate a connection with the PythonServer and get the url in the Properties file.
    * @param propertiesFile The file containing the url to the server.
    * @return A Connection.
    */
    private ServerConnectionManager loadConnectionManagerFromProperties(String propertiesFile) {
        try (FileInputStream input = new FileInputStream(propertiesFile)) {
            Properties properties = new Properties();
            properties.load(input);
            String serverURL = properties.getProperty("url");
            return new ServerConnectionManager(trouverCheminCommune, serverURL);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
    * Handles the action of finding the user's account page when a connection button is clicked.
    * If the current user is logged in, their account details are displayed.
    * If no user is logged in, the login page is displayed instead.
    */
    public void connectionClickedTrouverCheminCommune() {
        try {
            if (this.currentUser != null) {
                System.out.println("Utilisateur actuel : " + this.currentUser.getEmail());
                Stage stage = (Stage) this.trouverCheminCommune.getButton().getScene().getWindow();
                this.accountPage.start(stage);

                this.accountPage.getFirstNameLabel().setText(currentUser.getPrenom());
                this.accountPage.getNameLabel().setText(currentUser.getNom());
                this.accountPage.getEmailLink().setText(currentUser.getEmail());
                String utilisateurEtat = "";
                if(this.userServices.userIsAdmin(currentUser.getEmail()) == 1){
                    this.currentUser.setAdmin(true);
                    utilisateurEtat = "Administrateur";
                } else {
                    this.currentUser.setAdmin(false);
                    utilisateurEtat = "Utilisateur";
                }
                this.accountPage.getAdmin().setText(utilisateurEtat);
            } else {
                System.out.println("Utilisateur non connect\u00e9.");
                Stage stage = (Stage) this.trouverCheminCommune.getButton().getScene().getWindow();
                this.connectionPage.start(stage);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
























    //! ---------------- CONNECTIONPAGE METHOD
    // following code is for handle connectionpage actions.


    /**
    * Handle action of the Connection Page. 
    * @param e The Action Event
    */
    private void handleConnectionPageActions(ActionEvent e) {
        // if user clicked on link signup we redirect it in the InscriptionPage.
        if (e.getSource() == this.connectionPage.getLinkSignUp()) {
            try {
                Stage stage = (Stage) this.connectionPage.getBtnLogin().getScene().getWindow();
                this.inscriptionPage.start(stage);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        // if user clicked on forgotPassword we redirect it in the ForgotPassword.
        if (e.getSource() == this.connectionPage.getLinkForgotPassword()) {
            try {
                Stage stage = (Stage) this.connectionPage.getBtnLogin().getScene().getWindow();
                this.forgotPassword.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // if user clicked on login button
        if (e.getSource() == this.connectionPage.getBtnLogin()) {
            // We get the email and password from the user's input.
            String email = this.connectionPage.getEmailField().getText();
            String password = this.connectionPage.getPasswordField().getText();

            // error message is not visible while error not happening.
            this.connectionPage.getErrorMessageLabel().setVisible(false);

            // if user's input is empty we display an error message with ErrorMessage Label.
            if (email.isEmpty() || password.isEmpty()) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.connectionPage.getErrorMessageLabel().setText("Tous les champs doivent \u00eatre remplis.");
                this.connectionPage.getErrorMessageLabel().setVisible(true);
            
                // else if the emails isn't valid (email already exists or not in the format mail@example.com)
                // we display an error message with ErrorMessage Label.
            } else if (!isValidEmail(email) || !this.userServices.emailExists(email)) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.connectionPage.getErrorMessageLabel().setText("L'adresse e-mail n'est pas valide.");
                this.connectionPage.getErrorMessageLabel().setVisible(true);

                // else the connection is valid and we check if the (email, password) matches with the database.
            } else {
                // if matches we display a succesful message and we set the current user and show an alert.
                if (this.userServices.validateLogin(email, password)){
                    this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: green;");
                    this.connectionPage.getErrorMessageLabel().setText("Connexion Reussi !");
                    this.connectionPage.getErrorMessageLabel().setVisible(true);
                    
                    this.currentUser = searchList(email);

                    try{
                        CustomAlert.showAlert("Connexion Reussi", "Bonjour " + this.currentUser.getPrenom() + " " + this.currentUser.getNom() + " ! Redirection en cours.");
                        
                        Stage stage = (Stage) connectionPage.getBtnLogin().getScene().getWindow();
                        this.mainPage.start(stage);

                    } catch(Exception e1){
                        System.out.println(e1.getMessage());
                    }
                    
                    // if (email, password) don't match with the databse we display an Error message with ErrorMessage Label.
                } else {
                    this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.connectionPage.getErrorMessageLabel().setText("Identifiants incorrects.");
                    this.connectionPage.getErrorMessageLabel().setVisible(true);
                }
            }
        }
    }

    /**
    * Private method searching the user with his mail and return it. 
    * @param email The email of searched users
    * @return Utilisateur of the searched user, if isn't exists we return null.
    */
    private Utilisateur searchList(String email) {
        for (Utilisateur user : this.listeUtilisateur) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
    
    /**
    * Lookup if the current user is admin or not.
    *
    * @return True if admin, otherwise return false
    */
    public boolean isAdmin(){
        boolean isAdmin = false;
        if(this.currentUser == null){
            isAdmin = false;
        }else {
            //Lookup in the database if the user is admin or not.
            if(this.userServices.userIsAdmin(this.currentUser.getEmail()) == 1){
                isAdmin = true;
            }
        }
        return(isAdmin);
    }

    /**
    * Check if the current user is admin or not and change the state of EditData button in the MainPage.
    */
    public void verifyAdmin(){
        if (!isAdmin()) {
            this.mainPage.getEditData().setDisable(true);            
            //System.out.println("No admin !");
        } else {
            this.mainPage.getEditData().setDisable(false);
        }
    }


    /**
    * Check if user is Admin in TrouverCheminCommune. 
    */
    public void verifyAdminTrouverChemin(){
        if(!isAdmin()){
            this.trouverCheminCommune.getEditData().setDisable(true);
        } else {
            this.trouverCheminCommune.getEditData().setDisable(false);

        }
    }










    















    //! ---------------- INSCRIPTIONPAGE METHOD

    /**
    * Handle the action of the Inscription Page. 
    * @param e The Action Event
    */
    private void handleInscriptionPageActions(ActionEvent e){
        // when user click on SignUp button.
        if (this.inscriptionPage != null && e.getSource() == this.inscriptionPage.getBtnSignUp()) {
            String firstName = this.inscriptionPage.getFirstNameField().getText();
            String lastName = this.inscriptionPage.getLastNameField().getText();
            String email = this.inscriptionPage.getEmailField().getText();
            String password = this.inscriptionPage.getPasswordField().getText();
            String confirmPassword = this.inscriptionPage.getConfirmPasswordField().getText();

            this.inscriptionPage.getErrorMessageLabel().setVisible(false);

            // if One of field is Empty we display an ErrorMessage with ErrorMessage Label
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.inscriptionPage.getErrorMessageLabel().setText("Tous les champs doivent \u00eatre remplis.");
                this.inscriptionPage.getErrorMessageLabel().setVisible(true);

                // if the mail isn't valid we display an Error message with ErrorMessage Label.
            } else if (!isValidEmail(email)) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.inscriptionPage.getErrorMessageLabel().setText("L'adresse e-mail n'est pas valide.");
                this.inscriptionPage.getErrorMessageLabel().setVisible(true);

                // if the passwords fields doesn't matches between we display an ErrorMessage with ErrorMessage Label.
            } else if (!password.equals(confirmPassword)) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.inscriptionPage.getErrorMessageLabel().setText("Les mots de passe ne correspondent pas.");
                this.inscriptionPage.getErrorMessageLabel().setVisible(true);

                // All of fields are correct we check if the mail exists are not in the database.
            } else {
                // if mail exists we display an ErrorMessage Label.
                if (this.userServices.emailExists(email)) {
                    this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.inscriptionPage.getErrorMessageLabel().setText("L'adresse e-mail est d\u00e9j\u00e0 utilis\u00e9e.");
                    this.inscriptionPage.getErrorMessageLabel().setVisible(true);

                    // Else we create user and save it into database. And we load all of users of database.
                } else {
                    this.userServices.createUser(lastName, firstName, email, password);
                    this.inscriptionPage.getErrorMessageLabel().setStyle("-fx-text-fill: green;");
                    this.inscriptionPage.getErrorMessageLabel().setText("Compte cr\u00e9e avec succ\u00e8s !");
                    this.inscriptionPage.getErrorMessageLabel().setVisible(true);

                    this.listeUtilisateur = this.userServices.loadAllUsers();
                }
            }
        }

        // if the user click on login link, we redirect him into ConnectionPage.
        if (e.getSource() == this.inscriptionPage.getLinkLogin()) {
            try {
                Stage stage = (Stage) this.inscriptionPage.getLinkLogin().getScene().getWindow();
                this.connectionPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


















    //! ---------------- FORGOTPASSWORD METHOD


    /**
    * Handle the action of ForgotPassword page.
    * @param e The Action Event
    */
    private void handleForgotPasswordPageActions(ActionEvent e){
        // if use want to connect we redirect him to ConenctionPage.
        if (e.getSource() == this.forgotPassword.getLinkForgotPassword()) {
            try {
                Stage stage = (Stage) this.forgotPassword.getBtnLogin().getScene().getWindow();
                this.connectionPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // If the user want to get code.
        if(e.getSource() == this.forgotPassword.getBtnLogin()){
            String email;

            // if the button is recevoir code we are sending code to user.
            if(this.forgotPassword.getBtnLogin().getText().equals("Recevoir Code")){
                String mail = this.forgotPassword.getEmailField().getText();

                // if user input for mail is empty we are display an ErrorMessage with error message label.
                if(mail.isEmpty()){
                    this.forgotPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.forgotPassword.getErrorMessageLabel().setText("Tous les champs doivent etre rempli");
                    this.forgotPassword.getErrorMessageLabel().setVisible(true);

                    // we check if the user input isn't the database.
                } else if(!this.userServices.emailExists(mail)){
                    // we display an error message.
                    this.forgotPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.forgotPassword.getErrorMessageLabel().setText("Un mail deja renseign\u00e9e dans la bdd doit \u00eatre saisie");
                    this.forgotPassword.getErrorMessageLabel().setVisible(true);
                } else {
                    // Else we can send code to this email.
                    // we get mail, and generate a verification code.

                    email = this.forgotPassword.getEmailField().getText();
                    int code = this.userServices.generateVerificationCode();
                    this.codeString = String.valueOf(code);

                    // we send mail with the method in the userServices.
                    try {
                        this.userServices.sendVerificationEmail(email, codeString);
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

                // if the button is verfied (the mail is already sent)
            } else if(this.forgotPassword.getBtnLogin().getText().equals("V\u00e9rifier")){
                // we check if the user input match with the code.
                if(this.forgotPassword.getCodeField().getText().equals(this.codeString)){
                    try {
                        Stage stage = (Stage) this.forgotPassword.getBtnLogin().getScene().getWindow();
                        this.resetPassword.start(stage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    // the code isn't correct so we display an error message.
                    this.forgotPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.forgotPassword.getErrorMessageLabel().setText("Le code specifie est incorrect ! ");
                    this.forgotPassword.getErrorMessageLabel().setVisible(true);
                }
            }
        }
    }

    /**
    * Private method, which change the state of the btnLogin present in forgotPassword page.
    * First State : Get Code
    * Second State : Check
    */
    private void updateButtonState() {
        if (this.codeSent) {
            this.forgotPassword.getBtnLogin().setText("V\u00e9rifier");
            this.forgotPassword.getCodeField().setDisable(false);
        } else {
            this.forgotPassword.getBtnLogin().setText("Recevoir Code");
        }
    }


















    //! ---------------- RESETPASSWORD METHOD

    /**
    * Handle the action of ResetPassword page. 
    * @param e The Action Event
    */
    private void handleResetPasswordPageActions(ActionEvent e){
        // when user confirm with the validateButton
        if (e.getSource() == this.resetPassword.getBtnValidate()) {
            // we get password and confirm password PasswordField
            String newPassword = this.resetPassword.getFirstPassword().getText();
            String confirmPassword = this.resetPassword.getSecondPassword().getText();

            // we check if the password is the same as confirm password
            if (newPassword.equals(confirmPassword)) {

                // then we check if the password field isn't empty
                if (!newPassword.isEmpty() && !confirmPassword.isEmpty()) {
                    // we get the mail of the user
                    String email = this.forgotPassword.getEmailField().getText();
                    try {
                        // we update the user's password
                        this.userServices.updatePassword(email, newPassword);

                        // we diplay confirmation message.
                        this.resetPassword.getErrorMessageLabel().setStyle("-fx-text-fill: green; -fx-font-size: 15px;");
                        this.resetPassword.getErrorMessageLabel().setVisible(true);

                        this.resetPassword.getErrorMessageLabel().setText("Redirection...");
                        
                        // we display a validation pop-up
                        CustomAlert.showAlert("Reinitialisation du mot de passe", "Votre mot de passe est chang\u00e9.");

                        // Automatic redirection within 2 seconds.
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
                        // if the server don't respond, we assume the server is down and display error.
                        this.resetPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                        this.resetPassword.getErrorMessageLabel().setText("Connexion au service momentanément impossible.");
                        this.resetPassword.getErrorMessageLabel().setVisible(true);
                    }
                } else {
                    // we display error message if password field is null
                    this.resetPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.resetPassword.getErrorMessageLabel().setText("Les champs de mot de passe doivent être remplis !");
                    this.resetPassword.getErrorMessageLabel().setVisible(true);
                }
            } else {
                // we display an other message if the password and confirm password isn't the same
                this.resetPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.resetPassword.getErrorMessageLabel().setText("Les mots de passe ne correspondent pas !");
                this.resetPassword.getErrorMessageLabel().setVisible(true);
            }
        }

        // link to redirect in forgotPassword
        if(e.getSource() == this.resetPassword.getLinkWrongMail()){
            Stage stage = (Stage) this.resetPassword.getBtnValidate().getScene().getWindow();
            this.forgotPassword.start(stage);
        }

        // link to redirect in ConnectionPage.
        if(e.getSource() == this.resetPassword.getLinkConnection()){
            Stage stage = (Stage) this.resetPassword.getBtnValidate().getScene().getWindow();
            this.connectionPage.start(stage);
        }
    }



    

     


    









    //! ---------------- MULTICLASS METHOD

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

    /**
    * Allow to get all of the commune.
    * @return ArrayList<Commune> which contains the most recent year for each commune.
    */
    public ArrayList<Commune> getCommunesFromDataBase() {
        this.communesRecente = new ArrayList<>();
        try {
            // On récupère toutes les communes avec getAllCommunes du service commune (appelle la base de données)
            this.communeToute = this.communeService.getAllCommunes();
            
            // Utilisation d'une map pour stocker la commune la plus récente par identifiant
            HashMap<Integer, Commune> recentCommunesMap = new HashMap<>();
            
            for (Commune commune : communeToute) {
                int communeId = commune.getIdCommune();
                int currentYear = commune.getAnnee().getAnnee();
                
                // Si la commune est déjà dans la map, on compare les années
                if (recentCommunesMap.containsKey(communeId)) {
                    int mostRecentYear = recentCommunesMap.get(communeId).getAnnee().getAnnee();
                    if (currentYear > mostRecentYear) {
                        recentCommunesMap.put(communeId, commune);
                    }
                } else {
                    recentCommunesMap.put(communeId, commune);
                }
            }
            
            this.communesRecente.addAll(recentCommunesMap.values());
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return this.communesRecente;
    }




    /**
    * Allow to get all of available year of data for a Commune 
    * @param commune The commune we look for the data
    *
    * @return ArrayList of Integer which contains all of years of data.
    * 
    */
    public ArrayList<Integer> getYearsForCommune(Commune commune) {
        // we get all communes.
        List<Commune> allCommunes = this.communeToute;
        ArrayList<Integer> returnValue = new ArrayList<Integer>();

        // we get all possible year for a commune and add into an ArrayList
        for (Commune currentCommune : allCommunes) {
            if (currentCommune.getNomCommune().equals(commune.getNomCommune())) {
                returnValue.add(currentCommune.getAnnee().getAnnee());

            }
        }

        // return the arrayList containing the poosible year for a commune.
        return(returnValue);
    }


    /**
    * Allow to get all of available year in the database
    *
    * @return ArrayList of Integer which contains all of years.
    * 
    */
    public List<Annee> getYearsFromDatabase() {
        List<Annee> allYears = new ArrayList<>();
        
        try {
            allYears = this.anneeService.getAllAnnee();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return allYears;
    }


    /**
    * Retrieves all departments from the database.
    * 
    * @return A list of all departments retrieved from the database.
    */
    public List<Departement> getDepartementFromDatabase() {
        List<Departement> allDepartement = new ArrayList<>();

        try {
            allDepartement = this.departementService.getAllDepartement();
        } catch (Exception e) {
            // Handle exception (optional)
        }

        return allDepartement;
    }


    /**
    * Retrieves all departments from the database.
    * 
    * @return A list of all departments retrieved from the database.
    */
    public List<String> getDepartementNameFromDatabase() {
        List<Departement> allDepartement = new ArrayList<>();
        List<String> departementName = new ArrayList<>();

        try {
            allDepartement = this.departementService.getAllDepartement();
        } catch (Exception e) {
            // Handle exception (optional)
        }

        for (Departement departement : allDepartement) {
            departementName.add(departement.getNomDep());
        }

        return(departementName);
    }

    /**
    * Retrieves a department from the database based on the provided department ID.
    * 
    * @param idDep The ID of the department to retrieve.
    * @return The department object corresponding to the provided ID, or null if not found.
    */
    public Departement getDepartementById(int idDep) {
        try {
            this.listeDepartement = this.departementService.getAllDepartement();
        } catch (SQLException e) {
            // Handle SQLException (optional)
            e.printStackTrace();
        }
        
        // Search for the department with the specified ID
        for (Departement dep : this.listeDepartement) {
            if (dep.getIdDep() == idDep) {
                return dep;
            }
        }
        
        // Return null if department with the specified ID is not found
        return null;
    }

    public Departement getDepartementByName(String nomDep) {
        try {
            this.listeDepartement = this.departementService.getAllDepartement();
        } catch (SQLException e) {
            // Handle SQLException (optional)
            e.printStackTrace();
        }
        
        // Search for the department with the specified name
        for (Departement dep : this.listeDepartement) {
            if (dep.getNomDep().equalsIgnoreCase(nomDep)) {
                return dep;
            }
        }
        
        // Return null if department with the specified name is not found
        return null;
    }



    /**
    * Allow to get a specific commune which contains data of specified year 
    * @param communeName The name of the commune
    * @param year Year of data
    *
    * @return Data of commune for year passed in parameter
    */
    public Commune getCommuneForYearAndCommune(String communeName, int year){
        List<Commune> allCommunes = this.communeToute;
        Commune returnCommune = null;

        // we check if a commune with a specific commune and specific year is exists and return it.
        for (Commune currentCommune : allCommunes) {
            if(currentCommune.getNomCommune().equals(communeName) && currentCommune.getAnnee().getAnnee() == year){
                returnCommune = currentCommune;
                break;
            }
        }

        return(returnCommune);
    }


    /**
    * Allow to get all gare from GareService and put them into listeGare.
    */
    public void getAllGareFromDatabase(){
        try {
            this.listeGare = this.gareService.getAllGares();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean idIsInDatabase(int idCommune) {
        // Récupérer toutes les communes de la base de données
        this.communeToute = getCommunesFromDataBase();
    
        // Parcourir la liste des communes pour vérifier si l'ID existe
        for (Commune commune : this.communeToute) {
            if (commune.getIdCommune() == idCommune) {
                return true; // Si une commune avec l'ID spécifié est trouvée
            }
        }
    
        return false; // Si aucune commune avec l'ID spécifié n'est trouvée
    }
    















    //! ---------------- EXPORT DES DONNEES
    // following code is for export the data



    /**
    * Export data from different services into CSV files and compress them into a ZIP archive.
    * Retrieves necessary data from services and writes them to CSV files before creating a ZIP file.
    */
    public void exportData() {
        this.listeGare = this.communeService.getListeGare();
        this.listeAnnee = this.communeService.getListeAnnee();
        try {
            this.listeAeroport = new AeroportService().getAllAeroport();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            this.listeDepartement = new DepartementService().getAllDepartement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        communeData();
        gareData();
        anneeData();
        aeroportData();
        departementData();

        createZip();
    }

    /**
    * Export department data to a CSV file.
    * Writes department information including cultural investments and associated airports.
    */
    public void departementData() {
        String csvFile = "departementData.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            // Write CSV header
            writer.println("nomDep;idDep;invesCulture;aeroports");

            // Write each Department to the CSV file
            for (Departement departement : this.listeDepartement) {
                writer.println(departmentToCSVRow(departement));
            }

            System.out.println("Data successfully exported to " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * Convert a Department object to a CSV row string.
    * Includes department fields and associated airport names.
    *
    * @param department The Department object to convert.
    * @return A CSV formatted string representing the department's data.
    */
    private String departmentToCSVRow(Departement department) {
        StringBuilder sb = new StringBuilder();

        // Append department fields
        sb.append(department.getNomDep()).append(";");
        sb.append(department.getIdDep()).append(";");
        sb.append(department.getInvesCulture2019()).append(";");

        // Append airport names
        for (Aeroport aeroport : this.listeAeroport) {
            if (aeroport.getDepartement().getIdDep() == department.getIdDep()) {
                sb.append(aeroport.getNom()).append(",");
            }
        }
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    /**
    * Export airport data to a CSV file.
    * Writes airport information including name, address, and associated department ID.
    */
    public void aeroportData() {
        String csvFile = "aeroportData.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            // Write CSV header
            writer.println("nom;adresse;departement");

            // Write each Airport to the CSV file
            for (Aeroport aeroport : this.listeAeroport) {
                writer.println(aeroportToCSVRow(aeroport));
            }

            System.out.println("Data successfully exported to " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * Convert an Airport object to a CSV row string.
    * Includes airport name, address, and associated department ID.
    *
    * @param aeroport The Airport object to convert.
    * @return A CSV formatted string representing the airport's data.
    */
    private String aeroportToCSVRow(Aeroport aeroport) {
        return aeroport.getNom() + ";" +
                aeroport.getAdresse() + ";" +
                aeroport.getDepartement().getIdDep();
    }

    /**
    * Export year data to a CSV file.
    * Writes year information including year and inflation rate.
    */
    public void anneeData() {
        String csvFile = "anneeData.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            // Write CSV header
            writer.println("annee;tauxInflation");

            // Write each Year to the CSV file
            for (Annee annee : this.listeAnnee) {
                writer.println(anneeToCSVRow(annee));
            }

            System.out.println("Data successfully exported to " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * Convert a Year object to a CSV row string.
    * Includes year and inflation rate.
    *
    * @param annee The Year object to convert.
    * @return A CSV formatted string representing the year's data.
    */
    private String anneeToCSVRow(Annee annee) {
        return annee.getAnnee() + ";" +
                annee.getTauxInflation();
    }

    /**
    * Export train station data to a CSV file.
    * Writes train station information including code, name, type (freight/passenger), and associated commune ID.
    */
    public void gareData() {
        String csvFile = "gareData.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            // Write CSV header
            writer.println("CodeGare;NomGare;Fret;Voyageur;idCommune");

            // Write each Train Station to the CSV file
            for (Gare gare : this.listeGare) {
                writer.println(gareToCSVRow(gare));
            }

            System.out.println("Data successfully exported to " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * Convert a Train Station object to a CSV row string.
    * Includes train station code, name, type (freight/passenger), and associated commune ID.
    *
    * @param gare The Train Station object to convert.
    * @return A CSV formatted string representing the train station's data.
    */
    private String gareToCSVRow(Gare gare) {
        return gare.getCodeGare() + ";" +
                gare.getNomGare() + ";" +
                gare.isEstFret() + ";" +
                gare.isEstVoyageur() + ";" +
                gare.getCommune();
    }

    /**
    * Export commune data to a CSV file.
    * Writes commune information including ID, name, year, number of houses/apartments,
    * average price, average square meter price, average surface, cultural expenditures,
    * total budget, population, associated department ID, and associated train station.
    */
    public void communeData() {
        String csvFile = "communeData.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            // Write CSV header
            writer.println("idCommune;nomCommune;Annee;nbMaison;nbAppart;prixMoyen;prixM2Moyen;surfaceMoy;depCulturellesTotales;budgetTotal;population;departement;gare");

            // Write each Commune to the CSV file
            for (Commune commune : this.communeToute) {
                writer.println(communeToCSVRow(commune));
            }

            System.out.println("Data successfully exported to " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * Convert a Commune object to a CSV row string.
    * Includes commune ID, name, year, number of houses/apartments,
    * average price, average square meter price, average surface, cultural expenditures,
    * total budget, population, associated department ID, and associated train station.
    *
    * @param commune The Commune object to convert.
    * @return A CSV formatted string representing the commune's data.
    */
    private String communeToCSVRow(Commune commune) {
        return commune.getIdCommune() + ";" +
                commune.getNomCommune() + ";" +
                commune.getAnnee().getAnnee() + ";" +
                commune.getNbMaison() + ";" +
                commune.getNbAppart() + ";" +
                commune.getPrixMoyen() + ";" +
                commune.getPrixM2Moyen() + ";" +
                commune.getSurfaceMoy() + ";" +
                commune.getDepCulturellesTotales() + ";" +
                commune.getBudgetTotal() + ";" +
                commune.getPopulation() + ";" +
                commune.getDepartement().getIdDep() + ";" +
                commune.getGare();
    }

    /**
    * Create a ZIP archive containing all exported CSV files.
    * Deletes each CSV file after adding it to the ZIP archive.
    */
    private void createZip() {
        String zipFile = "dataExport.zip";
        String[] csvFiles = {"departementData.csv", "aeroportData.csv", "anneeData.csv", "gareData.csv", "communeData.csv"};

        try (FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (String csvFile : csvFiles) {
                addToZipFile(csvFile, zos);
                Files.delete(Paths.get(csvFile));  // Delete the CSV file after adding it to the zip
            }

            System.out.println("Files successfully compressed into " + zipFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * Add a file to a ZIP archive.
    *
    * @param fileName The name of the file to add.
    * @param zos      The ZipOutputStream to add the file to.
    * @throws IOException If an I/O error occurs.
    */
    private void addToZipFile(String fileName, ZipOutputStream zos) throws IOException {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
            zos.closeEntry();
        }
    }
}