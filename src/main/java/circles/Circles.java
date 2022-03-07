package circles;

import java.util.stream.Stream;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * A class to introduce Java 8 lambdas and streams.
 * @author Your name here
 */
public class Circles extends VBox {
    
    //public static final int ROWS = 4;
    //public static final int COLS = 5;
    //public static final int CELL_SIZE = 100;

    public Circles() {
        setAlignment(Pos.CENTER);
        
        
        canvas = new Pane();
        canvas.setPrefSize(5 * 150, 5 * 150);
        
        control = new HBox();
        control.setAlignment(Pos.CENTER);
        
        starter = new GridPane();
        //starter.setAlignment(Pos.BOTTOM_CENTER);
        starter.setHgap(10);
        starter.setPadding(new Insets(2));

        
        Label RowLabel = new Label("Rows");
        ROWS = new Spinner(1, 5, 4);
        ROWS.setPrefWidth(60);
        ROWS.valueProperty().addListener(e -> { canvas.getChildren().clear(); 
        addAllRowsToCanvas(makeAllRows()); });
        
        Label ColLabel = new Label("Columns");
        COLS = new Spinner(1, 5, 5);
        COLS.setPrefWidth(60);
        COLS.valueProperty().addListener(e -> { canvas.getChildren().clear(); 
           addAllRowsToCanvas(makeAllRows()); });
        
        Label xScaleLabel = new Label("X Scale");
        x_scale = new Spinner(-3, 3, 0);
        x_scale.setPrefWidth(60);
        x_scale.valueProperty().addListener(e -> { canvas.getChildren().clear(); 
        addAllRowsToCanvas(makeAllRows()); });
        
        Label yScaleLabel = new Label("Y Scale");
        y_scale = new Spinner(-3, 3, 0);
        y_scale.setPrefWidth(60);
        y_scale.valueProperty().addListener(e -> { canvas.getChildren().clear(); 
        addAllRowsToCanvas(makeAllRows()); });
        
        Label SliderLabel = new Label("Cell Size");
        CELL_SIZE = new Slider(50, 150, 100);
        CELL_SIZE.setPrefWidth(150);
        Label SliderValue = new Label("" + (int)CELL_SIZE.getValue());
        CELL_SIZE.valueProperty().addListener(e -> { canvas.getChildren().clear(); 
           addAllRowsToCanvas(makeAllRows()); 
            SliderValue.setText("" + (int)CELL_SIZE.getValue());});
        
        //adding rows and columns spinner
        starter.add(RowLabel, 0, 0);
        starter.add(ROWS, 0, 1);
        starter.add(ColLabel, 1, 0);
        starter.add(COLS, 1, 1);
        
        //adding slider and slider labels
        starter.add(SliderLabel, 2, 0);
        starter.add(CELL_SIZE, 2, 1);
        starter.add(SliderValue, 3, 1);  //note: it is 2, 1 and 3, 1 because you want the slider and the label horizontal, not vertical
        
        //adding X and Y scales/labels
        starter.add(xScaleLabel, 4, 0);
        starter.add(x_scale, 4, 1);
        starter.add(yScaleLabel, 5, 0);
        starter.add(y_scale, 5, 1);
        
        //setting all LABELS to center alignment
        GridPane.setHalignment(RowLabel, HPos.CENTER);
        GridPane.setHalignment(ColLabel, HPos.CENTER);
        GridPane.setHalignment(SliderLabel, HPos.CENTER);
        GridPane.setHalignment(xScaleLabel, HPos.CENTER);
        GridPane.setHalignment(yScaleLabel, HPos.CENTER);
        
        
        getChildren().addAll(canvas, control);
        control.getChildren().addAll(starter);
        
        addButtonHandler();  // You must write
        
        canvas.getChildren().clear(); 
        addAllRowsToCanvas(makeAllRows());
    }
    
    private void addToCanvas(Circle newCircle) {
        double fromX = (((int)(COLS.getValue())-1)*(int)CELL_SIZE.getValue()) + ((int)(CELL_SIZE.getValue())/2);
        double fromY = (((int)(ROWS.getValue())-1)*(int)CELL_SIZE.getValue()) + ((int)(CELL_SIZE.getValue())/2);
        double toX = (col * (int)(CELL_SIZE.getValue())) + ((int)(CELL_SIZE.getValue()) / 2);
        double toY = (row * (int)(CELL_SIZE.getValue())) + ((int)(CELL_SIZE.getValue()) / 2);
        newCircle.setCenterX(fromX);
        newCircle.setCenterY(fromY);
        newCircle.setFill(new Color(Math.random(), Math.random(), Math.random(), 1.0));
        
        canvas.getChildren().add(newCircle);
        
        TranslateTransition tt = new TranslateTransition(Duration.millis(500));
        tt.setNode(newCircle);
        tt.setByX(toX - fromX);
        tt.setByY(toY - fromY);
        tt.play();
        
        ScaleTransition st = new ScaleTransition(Duration.millis((250 * Math.random() + 500)));
        st.setNode(newCircle);
        st.setByX(1.0);
        st.setByY(1.0);
        st.setCycleCount(Animation.INDEFINITE);
        st.setAutoReverse(true);
        st.play();
        
    }
    
    /**
     * This method adds the handler to the button that gives
     * this application its behavior.
     */
    private void addButtonHandler() {
        ROWS.valueProperty().addListener(e -> { canvas.getChildren().clear(); 
           addAllRowsToCanvas(makeAllRows()); });
    }
    
    private Stream<Circle> makeRow() {
        return Stream.generate(() -> new Circle()).limit((int)COLS.getValue());
    }
    
    private void addRowToCanvas(Stream<Circle> stream) {
        col = 0;
        stream.forEach(c -> {addToCanvas(c);
                            col++;} );
    }
    
    private Stream<Stream<Circle>> makeAllRows() {
        return Stream.generate(() -> makeRow()).limit((int)ROWS.getValue());
    }
    
    private void addAllRowsToCanvas(Stream<Stream<Circle>> grid) {
        row = 0;
        grid.forEach(r -> {addRowToCanvas(r); row++;} );
    }
    
    private final Slider CELL_SIZE;
    private final Spinner x_scale;
    private final Spinner y_scale;
    private final Spinner ROWS;
    private final Spinner COLS;
    private final Pane canvas;
    private final GridPane starter;
    private final HBox control;
    private int row;
    private int col;
}
