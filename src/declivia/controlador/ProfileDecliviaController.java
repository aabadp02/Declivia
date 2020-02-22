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

import declivia.modelo.vo.StandardUserVo;
import declivia.modelo.vo.SuperUserVo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ProfileDecliviaController implements Initializable{
    
    @FXML
    private Label userName;
    @FXML
    private Label email;
    @FXML
    private Label closeSession;
    
    private Stage stageP;
    private Stage interfaceStage;
    private Stage firstStage;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
     
    }
    
    public void passArguments(Stage stageP, Stage interfaceStage, Stage firstStage, StandardUserVo user, int type, SuperUserVo suv)
    {
        if(type == 1)
        {
            userName.setText(user.getName());
            email.setText(user.getEmail());
        }
        else
        {
            userName.setText(suv.getName());
        }
        
        this.stageP = stageP;
        this.interfaceStage = interfaceStage;
        this.firstStage = firstStage;
    }
    
    @FXML
    private void closeSess()
    {
        firstStage.show();
        interfaceStage.close();
        stageP.close();
    }
    
}
