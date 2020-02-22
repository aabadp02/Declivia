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

package declivia.controlador;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import declivia.modelo.vo.StandardUserVo;
import declivia.modelo.vo.SuperUserVo;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SettingsDecliviaController implements Initializable
{
    @FXML
    private Label cambiarNombre;
    @FXML
    private Label cambiarCorreo;
    @FXML
    private Label cambiarContrasenia;
    @FXML
    private Label copiaSeguridad;
    @FXML
    private Label ajustes;
    
    @FXML
    private AnchorPane cambioNombre;
    @FXML
    private AnchorPane cambioCorreo;
    @FXML
    private AnchorPane cambioContrasenia;
    @FXML
    private AnchorPane copiaSeguridadPane;
    
    @FXML
    private JFXTextField nuevoNombre;
    @FXML
    private JFXTextField nuevoCorreo;
    @FXML
    private JFXPasswordField nuevaContrasenia;
    @FXML
    private JFXPasswordField contrasenia;
    @FXML
    private JFXPasswordField contrasenia_email;
    @FXML
    private JFXPasswordField contraseniaActual;
    @FXML
    private JFXPasswordField contrasenia_Copia;
    @FXML
    private JFXPasswordField contraseniaAdmin_Copia;
    
    @FXML
    private Label campoVacio_nombre;
    @FXML
    private Label campoVacio_correo;
    @FXML
    private Label campoVacio_contrasenia;
    @FXML
    private Label contraseniaErronea;
    @FXML
    private Label contraseniaErronea_correo;
    @FXML
    private Label contraseniaErronea_contrasenia;
    
    private int type;
    
    private Stage thisStage;
    private Stage interfaceStage;
    private Stage firstStage;
    
    private StandardUserVo user;
    private SuperUserVo sv;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
    
    }
    
    public void passArguments(StandardUserVo user, Stage thisStage, Stage interfaceStage, Stage firstStage, int type, SuperUserVo sv)
    {
        if(type == 1)
        {
            this.user = user;
        }
        else
        {
            this.sv = sv;
            cambiarNombre.setVisible(false);
            cambiarCorreo.setVisible(false);
        }
        
        this.type = type;
        
        this.thisStage = thisStage;
        this.interfaceStage = interfaceStage;
        this.firstStage = firstStage;
    }
    
    @FXML
    private void cambNombre()
    {
        cambioNombre.setVisible(true);
    }
    
    @FXML
    private void cambCorreo()
    {
        cambioNombre.setVisible(false);
        cambioCorreo.setVisible(true);
    }
    
    @FXML
    private void cambContrasenia()
    {
        cambioContrasenia.setVisible(true);
    }
    
    @FXML
    private void copiaSeguridad()
    {
        copiaSeguridadPane.setVisible(true);
        
        if(type == 1)
        {
            contraseniaAdmin_Copia.setVisible(true);
        }
        else
        {
            contraseniaAdmin_Copia.setVisible(false);
        }

    }
    
    @FXML
    private void aceptarCambNombre()
    {
        if(nuevoNombre.getText().equals("") || contrasenia.getText().equals(""))
        {
            campoVacio_nombre.setVisible(true);
        }
        else
        {
            int res = user.changeName(nuevoNombre.getText(), contrasenia.getText());
                        
            if(res == 0) //Salió mal
            {
                campoVacio_nombre.setVisible(false);
                contraseniaErronea.setVisible(true);
            }
            else
            {
                thisStage.close();
                interfaceStage.close();
                firstStage.show();
            }
        }
    }
    
    @FXML
    private void aceptarCambCorreo()
    {
        if(nuevoCorreo.getText().equals("") || contrasenia_email.getText().equals(""))
        {
            campoVacio_correo.setVisible(true);
        }
        else
        {
            int res = user.changeEmail(nuevoCorreo.getText(), contrasenia_email.getText());
                        
            if(res == 0) //Salió mal
            {
                campoVacio_correo.setVisible(false);
                contraseniaErronea_correo.setVisible(true);
            }
            else
            {
                thisStage.close();
                interfaceStage.close();
                firstStage.show();
            }
        }
    }
    
    @FXML
    private void aceptarCambContrasenia()
    {
        if(type == 1)
        {
            if(nuevaContrasenia.getText().equals("") || contraseniaActual.getText().equals(""))
            {
                campoVacio_contrasenia.setVisible(true);
            }
            else
            {
                int res = user.changePassword(nuevaContrasenia.getText(), contraseniaActual.getText());

                if(res == 0) //Salió mal
                {
                    campoVacio_contrasenia.setVisible(false);
                    contraseniaErronea_contrasenia.setVisible(true);
                }
                else
                {
                    thisStage.close();
                    interfaceStage.close();
                    firstStage.show();
                }
            }
        }
        else
        {
            if(nuevaContrasenia.getText().equals("") || contraseniaActual.getText().equals(""))
            {
                campoVacio_contrasenia.setVisible(true);
            }
            else
            {
                int res = sv.changePassword(nuevaContrasenia.getText(), contraseniaActual.getText());

                if(res == 0) //Salió mal
                {
                    campoVacio_contrasenia.setVisible(false);
                    contraseniaErronea_contrasenia.setVisible(true);
                }
                else
                {
                    thisStage.close();
                    interfaceStage.close();
                    firstStage.show();
                }
            }
        }
    }
    
    @FXML
    private void copiaSeguridad_Aceptar()
    {
        if(type == 1)
        {
            try {
                user.backup(contraseniaAdmin_Copia.getText());
            } catch (Exception ex) {
                Logger.getLogger(SettingsDecliviaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            try {
                sv.backup();
            } catch (Exception ex) {
                Logger.getLogger(SettingsDecliviaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void toAjustes()
    {
        cambioNombre.setVisible(false);
        cambioCorreo.setVisible(false);
        cambioContrasenia.setVisible(false);
        copiaSeguridadPane.setVisible(false);
    }
}
