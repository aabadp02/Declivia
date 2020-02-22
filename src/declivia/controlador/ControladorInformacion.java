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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class ControladorInformacion implements Initializable
{
    @FXML
    private AnchorPane infoInicio;
    @FXML
    private AnchorPane infoCursos;
    @FXML
    private AnchorPane infoDestacado;
    @FXML
    private AnchorPane infoControl;
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        
    }
    
    public void passArguments(int n)
    {
        if(n == 1)
        {
            hideEverything(infoInicio);
        }
        else if(n == 2)
        {
            hideEverything(infoCursos);
        }
        else if(n == 3)
        {
            hideEverything(infoDestacado);
        }
        else
        {
            hideEverything(infoControl);
        }
    }
    
    private void hideEverything(AnchorPane pane)
    {
        infoInicio.setVisible(false);
        infoCursos.setVisible(false);
        infoDestacado.setVisible(false);
        infoControl.setVisible(false);
        
        pane.setVisible(true);
    }
}
