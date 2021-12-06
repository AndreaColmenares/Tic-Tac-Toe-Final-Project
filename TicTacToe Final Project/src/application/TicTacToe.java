/**
 * Authors: Andrea Colmenares & Korede Kolawole Comp Science 1050 Final Project
 */

package application ;

import javafx.scene.input.MouseButton ;
import javafx.scene.layout.Pane ;
import javafx.scene.layout.StackPane ;
import javafx.scene.paint.Color ;
import javafx.scene.shape.Line ;
import javafx.scene.shape.Rectangle ;
import javafx.scene.text.Font ;
import javafx.scene.text.Text ;
import javafx.stage.Stage ;
import javafx.util.Duration ;
import javafx.animation.KeyFrame ;
import javafx.animation.KeyValue ;
import javafx.animation.Timeline ;
import javafx.application.Application ;
import javafx.geometry.Pos ;
import javafx.scene.Parent ;
import javafx.scene.Scene ;
import java.util.ArrayList ;
import java.util.List ;

/**
 * This class will display the game tic tac toe and will allow to users to play by
 * using the left and right click
 * 
 * @author colmenaresa
 * @version 1.0.0 2021-11-30 Initial implementation
 */
public class TicTacToe extends Application
    {

// flag that determined if users can still play
    private boolean isGameOn = true ;
    private boolean Xturn = true ;
    // multidimensional array 3 by 3
    private Squares[][] boardGame = new Squares[ 3 ][ 3 ] ;
    // array list that will store the values in the board
    private List<Combinations> combos = new ArrayList<>() ;

    private Pane setUp = new Pane() ;

    private Parent populateGame()
        {
        setUp.setPrefSize( 400, 400 ) ;

        for ( int i = 0 ; i < 3 ; i++ )
            {
            for ( int j = 0 ; j < 3 ; j++ )
                {
                Squares tile = new Squares() ;
                tile.setTranslateX( j * 133 ) ;
                tile.setTranslateY( i * 133 ) ;

                setUp.getChildren().add( tile ) ;

                boardGame[ j ][ i ] = tile ;
                }
            }

        for ( int x = 0 ; x < 3 ; x++ )
            {
            combos.add( new Combinations( boardGame[ x ][ 0 ],
                                          boardGame[ x ][ 1 ],
                                          boardGame[ x ][ 2 ] ) ) ;
            }
        for ( int y = 0 ; y < 3 ; y++ )
            {
            combos.add( new Combinations( boardGame[ 0 ][ y ],
                                          boardGame[ 1 ][ y ],
                                          boardGame[ 2 ][ y ] ) ) ;
            }
//diagonal 
        combos.add( new Combinations( boardGame[ 0 ][ 0 ],
                                      boardGame[ 1 ][ 1 ],
                                      boardGame[ 2 ][ 2 ] ) ) ;
        combos.add( new Combinations( boardGame[ 2 ][ 0 ],
                                      boardGame[ 1 ][ 1 ],
                                      boardGame[ 0 ][ 2 ] ) ) ;

        return setUp ;
        }


    @Override
    public void start( Stage primaryStage ) throws Exception
        {
        primaryStage.setScene( new Scene( populateGame() ) ) ;
        primaryStage.show() ;
        }

    private class Combinations extends Capabilities
    {

    private Squares[] tiles ;

    public Combinations( Squares... tiles )
        {
        this.tiles = tiles ;
        }

    @Override
    public boolean isGameOver()
        {
        if ( tiles[ 0 ].getValue().isEmpty()|| ( tiles[ 0 ].getValue()!=tiles[ 1].getValue() )&& ( tiles[ 0 ].getValue()!=tiles[ 2].getValue()) )
            return false ;

        return tiles[ 0 ].getValue().equals( tiles[ 1 ].getValue() ) &&
               tiles[ 0 ].getValue().equals( tiles[ 2 ].getValue() ) ;
        
    }}
    private void checkStatusOfGame()
        {

        for ( Combinations combo : combos )
            {
            if ( combo.isGameOver() )
                {
                isGameOn = false ;
                Text gameOver = new Text( "Game Over!" ) ;
                gameOver.setFill( Color.RED ) ;
                gameOver.setFont( Font.font( 50 ) ) ;
                gameOver.setX( 85 ) ;
                gameOver.setY( 150 ) ;
                setUp.getChildren().add( gameOver ) ;
                drawWinningLine( combo ) ;

              break ;
                }
           

            }
        }

    

    private void drawWinningLine( Combinations combo )
        {
        Line line = new Line() ;
        line.setStartX( combo.tiles[ 0 ].getCenterX() ) ;
        line.setStartY( combo.tiles[ 0 ].getCenterY() ) ;
        line.setEndX( combo.tiles[ 0 ].getCenterX() ) ;
        line.setEndY( combo.tiles[ 0 ].getCenterY() ) ;
        line.setStroke( Color.WHITE ) ;
        setUp.getChildren().add( line ) ;
// set a duration to the line animation
        Timeline timeline = new Timeline() ;
        timeline.getKeyFrames()
                .add( new KeyFrame( Duration.seconds( 1 ),
                                    new KeyValue( line.endXProperty(),
                                                  combo.tiles[ 2 ].getCenterX() ),
                                    new KeyValue( line.endYProperty(),
                                                  combo.tiles[ 2 ].getCenterY() ) ) ) ;
        timeline.play() ;
        }

    private class Squares extends StackPane
        {

        private Text text = new Text() ;

        public Squares()
            {
            Rectangle border = new Rectangle( 133, 133 ) ;
            border.setFill( Color.BLACK ) ;
            border.setStroke( Color.BLUE ) ;

            text.setFont( Font.font( 69 ) ) ;

            setAlignment( Pos.CENTER ) ;
            getChildren().addAll( border, text ) ;

            setOnMouseClicked( event ->
                {
                if ( !isGameOn )
                    return ;
// Primary button equals the left click
                if ( event.getButton() == MouseButton.PRIMARY )
                    {
                    if ( !Xturn )
                        return ;

                    displayX() ;
// Prevents the user using the "X" to click again
                    Xturn = false ;
                    checkStatusOfGame() ;
                    }
// Secondary button equals right click
                else if ( event.getButton() == MouseButton.SECONDARY )
                    {
                    if ( Xturn )
                        return ;

                    displayO() ;
// only allows the player using the "X" to play
                    Xturn = true ;
                    checkStatusOfGame() ;
                    }
                } ) ;
            }


        public double getCenterX()
            {
            return getTranslateX() + 67 ;
            }


        public double getCenterY()
            {
            return getTranslateY() + 67 ;
            }


        public String getValue()
            {
            return text.getText() ;
            }


        private void displayX()
            {
            text.setText( "X" ) ;
            text.setFill( Color.WHITE ) ;
            }


        private void displayO()
            {
            text.setText( "O" ) ;
            text.setFill( Color.WHITE ) ;

            }
        }

    public static void main( String[] args )
        {
        launch( args ) ;
        }
    }