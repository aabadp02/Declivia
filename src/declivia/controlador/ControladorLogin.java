package declivia.controlador;

/**
 ***********************************************************************
 * All rights reserved © Declivia
 * Alejandro Abad Peláez
 * Javier Darío Castrillo Rodríguez
 * Naím González Sánchez
 * 
 *
 *
 * Declivia v1.0.0
 ***********************************************************************
 */

import com.jfoenix.controls.*;
import declivia.modelo.dao.StandardUserDao;
import declivia.modelo.vo.StandardUserVo;
import declivia.modelo.vo.SuperUserVo;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class ControladorLogin implements Initializable
{
    private StandardUserDao logCont; //Objeto de tipo loginController, para que el modelo-vista-controlador tenga lugar.
    private int vista;               //Valdrá 0 si está en login y 1 si está en register (así podremos cambiar de vista al hacer la animación.
    private Stage firstStage;        //Aquí guardaremos el stage que recibimos desde el main como argumento
    
    //Declaración de los diferentes componentes que formarán la interfaz gráfica
    @FXML
    private Label errorAutenticacion;
     @FXML
    private AnchorPane layoutLogin;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextField username_register;
    @FXML
    private JFXPasswordField password_register;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXButton registerLogin;
    @FXML
    private AnchorPane layoutDeclivia;
    @FXML
    private Text loginText;
    @FXML 
    private ImageView twitter_login;
    @FXML 
    private ImageView facebook_login;
    @FXML 
    private ImageView instagram_login;      
    @FXML 
    private ImageView twitter_register;
    @FXML 
    private ImageView facebook_register;
    @FXML 
    private ImageView instagram_register;
    @FXML
    private Label forgotPassword;
    @FXML
    private JFXButton enterButton;
    @FXML
    private JFXButton registerButton;
    @FXML
    private Text registerText;
    @FXML
    private Text userExistsText;
    @FXML
    private Text faltaNombre;
    @FXML
    private Text faltaContrasenia;
    @FXML
    private Text usuarioCreado;
    @FXML
    private Text faltaEmail;
    
    @FXML
    private Text camposVacios;
    
    @FXML
    private Text contraseniaCreada;
    
    @FXML
    private Label ponUsuario;
    
    @FXML
    private JFXTextField username_forgotPassword;
    @FXML
    private JFXPasswordField newPassword;
    @FXML
    private JFXPasswordField repeatNewPassword;
    
    @FXML   
    private JFXButton aceptar;
    @FXML   
    private JFXButton cancelar;
    
    @FXML
    private AnchorPane forgotPasswordPane;
    
    private int forgotPasswordVista;
    
    private int codePassword;

/***********************************************************************************************************************************
* Función login de la aplicación. 
* 1. Guardará los valores de los campos donde el usuario introduce usuario y contraseña en las variables:
*   -userString
*   -passwdString
* 
* 2. Llamada a la función comprobarDatos(String user, String password) de la clase LoginController:
*   -Esta función devolverá true si los datos introducidos son correctos y false si no lo son
* 
* 3.1 Si los datos son correctos:
*   -Igual que en el main, crea una nueva ventana para mostrar y le indicará el nombre de su controlador (en este caso
*   interfaceController) y le pasará como argumento el nombre del usuario. Por último, haciendo uso de su propio stage
*   pasado como argumentos(firstStage), cerrará la pestaña actual e inmediatamente después abrirá la nueva(stage)
* 
* 3.2 Si los datos son incorrectos:
*   -Hará visible un label de color rojo que indica que usuario o contraseña son incorrectos. (este label se hará invisible
*   de nuevo en el momento en el que los datos se introduzcan correctamente o cuando se cambie la vista
*
************************************************************************************************************************************/
    @FXML
    private void login(ActionEvent event) 
    {
        String userString =  username.getText();
        String passwdString = password.getText();
        
        StandardUserVo usuario = new StandardUserVo(userString);
        usuario.setStandardUserDao(logCont);

        if(logCont.comprobarDatos(usuario, passwdString) == 1) //Si es usuario normal y lo encuentra
        {
            System.out.println("Correcto!");
            errorAutenticacion.setVisible(false);
            
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AppInterface.fxml"));
                Parent root = (Parent) loader.load();
                InterfaceController interfaceController = loader.getController();
                
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                
                interfaceController.passArguments(usuario, stage, firstStage, 1, null);
                
                firstStage.close(); //Cerramos el login
                
                stage.show(); //Abrimos la interfaz de usuario
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        else if(logCont.comprobarDatos(usuario, passwdString) == 2)
        {
            SuperUserVo suv = new SuperUserVo(usuario.getName());
            
            System.out.println("Correcto!");
            errorAutenticacion.setVisible(false);
            
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AppInterface.fxml"));
                Parent root = (Parent) loader.load();
                InterfaceController interfaceController = loader.getController();
                
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                
                interfaceController.passArguments(null, stage, firstStage, 2, suv);
                
                firstStage.close(); //Cerramos el login
                
                stage.show(); //Abrimos la interfaz de usuario
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("INCORRECTO!");
            errorAutenticacion.setVisible(true);
        }     
    }
    
    
/***********************************************************************************************************************************
* Función register de la aplicación. 
* 1. Cogerá los datos de los campos donde el usuario introducirá usuario y contraseña que desea guardándolos en las variables:
*   -userString
*   -passwdString
* 
* 2. Llamará al objeto de tipo LoginController para que este inserte los datos del nuevo usuario en la base de datos.
* 
************************************************************************************************************************************/
    @FXML
    private void register(ActionEvent event)
    {
        String userString = username_register.getText();
        String passwdString = password_register.getText();
        String emailString = email.getText();
        
        if(userString.equals(""))
        {
            userExistsText.setVisible(false);
            faltaNombre.setVisible(true);
            faltaContrasenia.setVisible(false);
            usuarioCreado.setVisible(false);
            faltaEmail.setVisible(false);
        }
        else if(passwdString.equals(""))
        {
            userExistsText.setVisible(false);
            faltaNombre.setVisible(false);
            faltaContrasenia.setVisible(true);
            usuarioCreado.setVisible(false);
            faltaEmail.setVisible(false);
        }   
        else if(emailString.equals(""))
        {
            userExistsText.setVisible(false);
            faltaNombre.setVisible(false);
            faltaContrasenia.setVisible(false);
            usuarioCreado.setVisible(false);
            faltaEmail.setVisible(true);
        }
        else
        {
            if(logCont.createUser(userString, passwdString, emailString) == 0) //Si el usuario ya existe
            {
                userExistsText.setVisible(true);
                faltaNombre.setVisible(false);
                faltaContrasenia.setVisible(false);
                usuarioCreado.setVisible(false);
                faltaEmail.setVisible(false);
            }
            else
            {
                userExistsText.setVisible(false);
                faltaNombre.setVisible(false);
                faltaContrasenia.setVisible(false);
                usuarioCreado.setVisible(true);
                faltaEmail.setVisible(false);
            }
        }
    }

/***********************************************************************************************************************************
* Función switchView de la aplicación
* 1. Comprobará la variable global vista para saber si está en la pestaña de login o de registro.
* 
* 2.1 Si está en la pestaña de login, tendrá que poner todos los componentes del login invisibles. Luego comenzará la animación y   
* hasta que NO termine, no se harán visibles los componentes del register. Cambiamos el valor de la variable vista.
* 
* 2.2 Si está en la pestaña de register, el procedimiento es el mismo, pero a la inviersa
* 
************************************************************************************************************************************/
    @FXML
    private void switchView(ActionEvent event)
    {
        double num = 0.1;
        
        if(forgotPasswordVista == 1) //Si el layout de forgot password está puesto
        {
            num = 0.3;
        }
        
        TranslateTransition slide1 = new TranslateTransition();
        
        forgotPasswordPane.setVisible(true);
        
        slide1.setDuration(Duration.seconds(num));
        slide1.setNode(forgotPasswordPane);
        
        slide1.setToY(0);
        slide1.play();
        slide1.setOnFinished((a->{
        
            if(vista == 0)
            {
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(0.3));
                slide.setNode(layoutDeclivia);

                slide.setToX(520);
                slide.play();

                //Procedemos a hacer desparecer todo
                loginText.setVisible(false);
                twitter_login.setVisible(false);
                facebook_login.setVisible(false);
                instagram_login.setVisible(false);
                username.setVisible(false);
                password.setVisible(false);
                enterButton.setVisible(false);
                forgotPassword.setVisible(false);
                errorAutenticacion.setVisible(false);
                userExistsText.setVisible(false);
                faltaNombre.setVisible(false);
                faltaContrasenia.setVisible(false);
                usuarioCreado.setVisible(false);
                faltaEmail.setVisible(false);
                registerLogin.setText("Entra");
                ponUsuario.setVisible(false);
                camposVacios.setVisible(false);

                vista = 1;

                slide.setOnFinished((e->{
                registerText.setVisible(true);
                twitter_register.setVisible(true);
                facebook_register.setVisible(true);
                instagram_register.setVisible(true);
                username_register.setVisible(true);
                password_register.setVisible(true);
                email.setVisible(true);
                registerButton.setVisible(true);
                }));
            }
            else
            {
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(0.3));
                slide.setNode(layoutDeclivia);

                slide.setToX(0);
                slide.play();

                //Procedemos a hacer desparecer todo

                registerText.setVisible(false);
                twitter_register.setVisible(false);
                facebook_register.setVisible(false);
                instagram_register.setVisible(false);
                username_register.setVisible(false);
                password_register.setVisible(false);
                email.setVisible(false);
                registerButton.setVisible(false);
                userExistsText.setVisible(false);
                faltaNombre.setVisible(false);
                faltaContrasenia.setVisible(false);
                usuarioCreado.setVisible(false);
                faltaEmail.setVisible(false);
                ponUsuario.setVisible(false);
                camposVacios.setVisible(false);

                slide.setOnFinished((e->{
                loginText.setVisible(true);
                twitter_login.setVisible(true);
                facebook_login.setVisible(true);
                instagram_login.setVisible(true);
                username.setVisible(true);
                password.setVisible(true);
                enterButton.setVisible(true);
                forgotPassword.setVisible(true);
                errorAutenticacion.setVisible(false);

                registerLogin.setText("Regístrate");

                vista = 0;
                }));
            }
        }));

    }
    
    @FXML
    private void forgotMyPassword()
    {
        if(username.getText().equals(""))
        {
            ponUsuario.setVisible(true);
        }
        else
        {
            ponUsuario.setVisible(false);
            TranslateTransition slide = new TranslateTransition();

            forgotPasswordPane.setVisible(true);

            slide.setDuration(Duration.seconds(0.3));
            slide.setNode(forgotPasswordPane);

            slide.setToY(483);
            slide.play();
            
            slide.setOnFinished((e->{
                generateCode();
                StandardUserVo mandarCorreo = new StandardUserVo(username.getText());
                logCont.sendEmail(mandarCorreo, codePassword);
            }));
        }
    }
    
    @FXML
    private void cancelarNewPassword()
    {
        TranslateTransition slide = new TranslateTransition();
        
        forgotPasswordPane.setVisible(true);
        
        slide.setDuration(Duration.seconds(0.3));
        slide.setNode(forgotPasswordPane);
        
        slide.setToY(0);
        slide.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("Bien");
        logCont = new StandardUserDao();
        vista = 0;
        forgotPasswordVista = 0; //No está activado
        codePassword = 0;
        
        forgotPassword.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) 
            {
                forgotMyPassword();
            }
            
        });
    }

    public void initStage(Stage stage)
    {
        firstStage = stage;
    }
    
    @FXML
    private void generateCode()
    {
        codePassword = (int) (Math.random()*(10000 - 1) + 1);  
    }
    
    @FXML
    private void aceptar()
    {
        if(repeatNewPassword.getText().equals("") || username_forgotPassword.getText().equals("") || newPassword.getText().equals(""))
        {
            camposVacios.setVisible(true);
        }
        else
        {
            if(codePassword == Integer.parseInt(repeatNewPassword.getText()))
            {
                camposVacios.setVisible(false);
                System.out.println("Correcto");
                
                logCont.changePasswordLogin(username_forgotPassword.getText(), newPassword.getText());
                
                contraseniaCreada.setVisible(true);
               
                TranslateTransition slide1 = new TranslateTransition();

                forgotPasswordPane.setVisible(true);

                slide1.setDuration(Duration.seconds(0.3));
                slide1.setNode(forgotPasswordPane);

                slide1.setToY(0);
                slide1.play();
            }
        }
    } 
}
