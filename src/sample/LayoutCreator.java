package sample;

import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class LayoutCreator {
    public static int row = 2;
    public static int column = 12;
    public static double triangleBase = 50;
    private final double diceBoardWidth = 425;
    private final double diceBoardHeight = 500;
    private double boardWidth = 15 * triangleBase + diceBoardWidth;
    private double boardHeight = 12 * triangleBase;
    private final double diceBoardX = boardWidth - diceBoardWidth;
    private final double diceBoardY = (boardHeight - diceBoardHeight)/2;
    private Group triangles = new Group();
    private Group pieces = new Group();
    private DiceBoard diceBoard = new DiceBoard();


    public Pane layout() {
        Pane board = new Pane();
        Pane diceBoardPane = diceBoard.layoutCreator(diceBoardX, diceBoardY);
        board.setPrefSize(boardWidth, boardHeight);
        board.getChildren().addAll(this.triangles, this.pieces);
        board.setStyle("-fx-background-color: #21242E");
        board.getChildren().addAll(diceBoardPane);
        initializeTriangles();
        return board;
    }

    private int findInitialNumOfPieces(int index) {
        int initialNumOfPieces;
        switch (index) {
            case 1: {
                initialNumOfPieces = 5;
                break;
            }
            case 5: {
                initialNumOfPieces = 3;
                break;
            }
            case 7: {
                initialNumOfPieces = 5;
                break;
            }
            case 12: {
                initialNumOfPieces = 2;
                break;
            }
            default: {
                initialNumOfPieces = 0;
                break;
            }
        }
        return initialNumOfPieces;
    }


    public void initializeTriangles(){
        Group triangles = new Group();
        for (int index = 1; index <= column; index++) {
            int initialNumOfPieces = findInitialNumOfPieces(index);
            Triangle upTriangle = new Triangle((index <= column / 2 ? index : index + 1) * triangleBase, 0.0,
                    index % 2 == 0 ? TriangleType.upRed : TriangleType.upWhite, 12 - index, initialNumOfPieces);
            Triangle downTriangle = new Triangle((index <= column / 2 ? index : index + 1) * triangleBase, boardHeight / 2 + triangleBase,
                    index % 2 == 0 ? TriangleType.downWhite : TriangleType.downRed, 11 + index, initialNumOfPieces);
            triangles.getChildren().addAll(upTriangle, downTriangle);
            Group upPieces = upTriangle.initializePieces(initialNumOfPieces);
            Group downPieces = downTriangle.initializePieces(initialNumOfPieces);
            this.pieces.getChildren().addAll(upPieces,downPieces);
        }
        this.triangles.getChildren().addAll(triangles);
    }

}
