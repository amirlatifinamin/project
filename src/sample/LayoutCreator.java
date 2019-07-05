package sample;

import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class LayoutCreator {
    public static int row = 2;
    public static int column = 12;
    public static double triangleBase = 50;
    private final double diceBoardWidth = DiceBoard.width;
    private final double diceBoardHeight = DiceBoard.height;
    private final double stockpileWidth = Stockpile.pileWidth + triangleBase;
    private final double stockpileHeight = Stockpile.pileHeight;
    private double boardWidth = 15 * triangleBase + ScoreBoard.scoreBoardWidth + stockpileWidth;
    private double boardHeight = 12 * triangleBase;
    private final double scoreboardX = boardWidth - ScoreBoard.scoreBoardWidth - triangleBase/2;
    private final double scoreboardY = triangleBase/2;
    private final double diceBoardX = scoreboardX;
    private final double diceBoardY = scoreboardY + ScoreBoard.scoreBoardHeight + triangleBase/3;
    private final double stockpileX = boardWidth - ScoreBoard.scoreBoardWidth - stockpileWidth;
    private final double redStockpileY = triangleBase/2;
    private final double whiteStockPileY = redStockpileY + stockpileHeight + triangleBase;
    private Group trianglesGroup = new Group();
    private Group pieces = new Group();
    private DiceBoard diceBoard = new DiceBoard();
    private Controller controller;
    public static Triangle[] triangles = new Triangle[row*column];
    private Stockpile redStockpile = new Stockpile(stockpileX, redStockpileY, PieceType.red);
    private Stockpile whiteStockpile = new Stockpile(stockpileX, whiteStockPileY, PieceType.white);
    private UserStatistics redStats = new UserStatistics(PieceType.red);
    private UserStatistics whiteStats = new UserStatistics(PieceType.white);
    private ScoreBoard scoreBoard = new ScoreBoard(scoreboardX, scoreboardY, redStockpile, whiteStockpile,
                                                   diceBoard, redStats, whiteStats) ;


    public LayoutCreator(Controller controller){
        this.controller = controller;
    }


    public Pane layout() {
        Pane board = new Pane();
        Pane diceBoardPane = diceBoard.layoutCreator(diceBoardX, diceBoardY, scoreBoard);
        board.setPrefSize(boardWidth, boardHeight);
        board.getChildren().addAll(this.trianglesGroup, this.pieces, diceBoardPane, redStockpile, whiteStockpile, scoreBoard);
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
