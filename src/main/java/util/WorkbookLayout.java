package util;

import java.util.List;
import java.util.stream.Collectors;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class WorkbookLayout {

	public static void setDragForLayerLayer(final Node node) {
		// TODO Auto-generated method stub
		node.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				System.out.println("On Drag Detected");
				/* drag was detected, start a drag-and-drop gesture */
				/* allow any transfer mode */
				Dragboard db = node.startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				// Store node ID in order to know what is dragged.
				content.putString(node.getId());
				db.setContent(content);
				event.consume();
			}
		});
	} 

	public static void setDropForLayers( final Node nodeSrc, final Node nodeDest ) {

		nodeDest.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				System.out.println("On Drag Over");

				// event.getGestureSource() != workAreaVBox &&
				if (event.getDragboard().hasString()) {
					event.acceptTransferModes(TransferMode.MOVE);
				}

				event.consume();
			}
		});

		nodeDest.setOnDragEntered(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				/* the drag-and-drop gesture entered the target */
				/* show to the user that it is an actual gesture target */
				
				if (event.getGestureSource() != nodeDest &&
		                 event.getDragboard().hasString()) {
		             ((Pane)nodeDest).setBackground(new Background(new BackgroundFill(
		            		    Color.LIGHTGRAY,
		            		    new CornerRadii(5),
		            		    Insets.EMPTY)));
		         }
				event.consume();
			}
		});

		nodeDest.setOnDragExited(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				/* mouse moved away, remove the graphical cues */
				// workAreaVBox.setFill(Color.BLACK); 
				if (event.getGestureSource() != nodeDest &&
		                 event.getDragboard().hasString()) {
		             ((Pane)nodeDest).setBackground(new Background(new BackgroundFill(
		            		    Color.WHITE,
		            		    CornerRadii.EMPTY,
		            		    Insets.EMPTY)));
		         }
				
				
				event.consume();
			}
		}); 

		nodeDest.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				// If this is a meaningful drop...
				if (db.hasString()) {
					// Get an item ID here, which was stored when the drag
					// started.
					String nodeId =   db.getString();
					String nodeDestId = ((Pane)nodeDest).getId();

					// ... search for the item in unequipped items. If it is
					// there...
					Pane ap = (Pane) nodeSrc.lookup("#" + nodeId);
					if (ap != null) {
							((Pane) nodeSrc).getChildren().remove(ap);
							((Pane) nodeDest).getChildren().add(ap);
						success = true;  
						if( ((Pane) nodeSrc).getChildren().size() < 2 ){
							List<Node> moveList = ((Pane) nodeSrc).getChildren().stream().collect(Collectors.toList());
							((Pane) nodeDest).getChildren().addAll(moveList);
						}
					}else{
						//TODO Improve code Here
						Pane apDest = (Pane) nodeDest.lookup("#" + nodeId);
						apDest.toBack();	
					}
					// ...anyway, the item is now equipped.
					// items.get(nodeId).putOn();
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});

		nodeDest.setOnDragDone(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				System.out.println("OnDrag Done");
				/* the drag and drop gesture ended */
				/* if the data was successfully moved, clear it */
				event.consume();
			}
		});

	}
} 