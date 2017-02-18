package workbook;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

public class LayerController {

    @FXML
    private AnchorPane layerAnchorPane;

    @FXML
    private ScrollPane scrollPaneId;
    
    @FXML
    private AnchorPane anchorInside;
    
	public AnchorPane getScrollAnchorPane() {
		return anchorInside;
	}
    

}