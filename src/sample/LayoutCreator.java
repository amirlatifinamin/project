package sample;

import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class LayoutCreator {
    public static int row = 2;
    public static int column = 12;
    public static double triangleBase = 50;
    private final double diceBoardWidth = 215;
    private final double diceBoardHeight = 300;
    private final double stockpileWidth = Stockpile.pileWidth + triangleBase;
    private final double stockpileHeith = Stockpile.pieceHeight;
    private double boardWidth = 15 * triangleBase + diceBoardWidth + stockpileWidth;
    private double boardHeight = 12 * triangleBase;
    private final double diceBoardX = boardWidth - diceBoardWidth;
    private final double diceBoardY = (boardHeight - diceBoardHeight)/2;
    private final double stockpileX = boardWidth - diceBoardWidth - stockpileWidth;
    private final double redStockpileY = triangleBase/2;
    private final double whiteStockPileY = redStockpileY + stockpileHeith + triangleBase/2;
    private Group trianglesGroup = new Group();
    private Group pieces = new Group();
    private DiceBoard diceBoard = new DiceBoard();
    private Controller controller;
    public static Triangle[] triangles = new Triangle[row*column];

    public LayoutCreator(Controller controller){
        this.controller = controller;
    }


    public Pane layout() {
        Pane board = new Pane();
        Pane diceBoardPane = diceBoard.layoutCreator(diceBoardX, diceBoardY);
        Stockpile redStockpile = new Stockpile(stockpileX, redStockpileY, PieceType.red);
        board.setPrefSize(boardWidth, boardHeight);
        board.getChildren().addAll(this.trianglesGroup, this.pieces, diceBoardPane, redStockpile);
        board.setStyle("-fx-background-color: #21242E");
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
        for (int index = 1; index <= column; index++) {
            int initialNumOfPieces = findInitialNumOfPieces(index);
            Triangle upTriangle = new Triangle((index <= column / 2 ? index : index + 1) * triangleBase, 0.0,
                    index % 2 == 0 ? TriangleType.upRed : TriangleType.upWhite, this.controller, 12 - index);
            triangles[12-index] = upTriangle;
            Triangle downTriangle = new Triangle((index <= column / 2 ? index : index + 1) * triangleBase, boardHeight / 2 + triangleBase,
                    index % 2 == 0 ? TriangleType.downWhite : TriangleType.downRed, this.controller, 11 + index);
            triangles[11+index] = downTriangle;
            Group upPieces = upTriangle.initializePieces(initialNumOfPieces);
            Group downPieces = downTriangle.initializePieces(initialNumOfPieces);
            this.pieces.getChildren().addAll(upPieces,downPieces);
            this.trianglesGroup.getChildren().addAll(upTriangle, downTriangle);

        }
    }

}
