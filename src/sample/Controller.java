package sample;

import javafx.scene.Group;
import javafx.scene.control.Alert;

import static sample.LayoutCreator.*;


public class Controller {

    private int numOfRedPiecesInBase = 5;
    private int numOfWhitePiecesInBase = 5;
    private int numOfRedPiecesInPile = 0;
    private int numOfWhitePiecesInPile = 0;
    private Graveyard graveyard;
    private DiceBoard diceBoard;
    private Stockpile redStockPile;
    private Stockpile whiteStockPile;
    public static int numOfRedKilledPieces = 0;
    public static int numOfWhiteKilledPieces = 0;

    public Controller(Graveyard graveyard,
                      DiceBoard diceBoard, Stockpile redStockPile, Stockpile whiteStockPile) {
        this.graveyard = graveyard;
        this.diceBoard = diceBoard;
        this.redStockPile = redStockPile;
        this.whiteStockPile = whiteStockPile;
    }

    public Piece makeHandlePiece(double x, double y, PieceType pieceType, int number, Group pieces) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Finished");
        alert.setHeaderText("The game is finished");
        Piece piece = new Piece(x, y, pieceType, number);
        piece.setOnMouseReleased(e -> {
            int distance;
            int oldPlace = piece.getPlaceNumber();
            double newXPosition = (double) ((int) (e.getSceneX() / triangleBase)) * triangleBase;
            double newYPosition = e.getSceneY();

            int oldTriangleNumber = piece.getPlaceNumber();
            int newTriangleNumber = findTriangleNumber(newXPosition, newYPosition);
            distance = findDistance(piece, newTriangleNumber, newXPosition, newYPosition);


            MoveType moveType = findMoveType(newXPosition, newYPosition, newTriangleNumber, piece, distance, diceBoard);

            switch (moveType) {
                case None: {
                    piece.abortMove();
                    break;
                }
                case Normal: {
                    newYPosition = triangles[newTriangleNumber].findCoordinationOfNewPiece();
                    triangles[newTriangleNumber].addPiece(piece);
                    if (piece.isKilled()) {
                        graveyard.removePiece(piece);
                        piece.setKilled(false);
                        if (piece.getPieceType() == PieceType.red) {
                            numOfRedKilledPieces--;
                        } else {
                            numOfWhiteKilledPieces--;
                        }
                        System.out.println(numOfRedKilledPieces);
                    } else {
                        triangles[oldTriangleNumber].removePiece(piece);
                    }
                    piece.setPlaceNumber(triangles[newTriangleNumber].getNumber());
                    updateNumOfPiecesInBase(piece, oldPlace, false);
                    piece.move(newXPosition, newYPosition);
                    break;
                }
                case Kill: {
                    Piece killedPiece = triangles[newTriangleNumber].getPieces().get(0);
                    int killedPieceOldPlace = killedPiece.getPlaceNumber();
                    killedPiece.setPlaceNumber(killedPiece.getPieceType() == pieceType.white ? -1 : 24);
                    killedPiece.setKilled(true);
                    if (killedPiece.getPieceType() == PieceType.red) {
                        numOfRedKilledPieces++;
                    } else {
                        numOfWhiteKilledPieces++;
                    }
                    updateNumOfPiecesInBase(killedPiece, killedPieceOldPlace, true);
                    graveyard.addPiece(killedPiece);
                    triangles[newTriangleNumber].killPiece();
                    newYPosition = triangles[newTriangleNumber].findCoordinationOfNewPiece();
                    triangles[newTriangleNumber].addPiece(piece);
                    if (piece.isKilled()) {
                        graveyard.removePiece(piece);
                        piece.setKilled(false);
                        if (piece.getPieceType() == PieceType.red) {
                            numOfRedKilledPieces--;
                        } else {
                            numOfWhiteKilledPieces--;
                        }
                        System.out.println(numOfRedKilledPieces);

                    } else {
                        triangles[oldTriangleNumber].removePiece(piece);
                    }
                    piece.setPlaceNumber(triangles[newTriangleNumber].getNumber());
                    updateNumOfPiecesInBase(piece, oldPlace, false);
                    piece.move(newXPosition, newYPosition);
                    break;
                }
                case StockPile: {
                    if (piece.getPieceType() == PieceType.red) {
                        redStockPile.addPieceToPile();
                        numOfRedPiecesInPile++;
                    } else {
                        whiteStockPile.addPieceToPile();
                        numOfWhitePiecesInPile++;
                    }
                    triangles[oldTriangleNumber].removePiece(piece);
                    pieces.getChildren().remove(piece);
                    if (numOfRedPiecesInPile == 15) {
                        alert.setContentText("Red is the winner");
                        alert.show();
                    }
                    if (numOfWhitePiecesInPile == 15) {
                        alert.setContentText("White is the winner");
                        alert.show();
                    }

                }
            }

        });
        return piece;

    }


    public int findTriangleNumber(double x, double y) {
        int xPosition = (int) (x / triangleBase);
        int yPosition = (int) (y / triangleBase);
        int column;
        column = xPosition > 7 ? 13 - xPosition : 12 - xPosition;
        return yPosition >= 7 ? 23 - column : column;
    }

    public MoveType findMoveType(double newXPosition, double newYPosition, int newTriangleNumber,
                                 Piece piece, int distance, DiceBoard diceBoard) {

        if (distance == 0) {
            return MoveType.None;
        }

        if (diceBoard.getCurrentUser() != piece.getPieceType()) {
            return MoveType.None;
        }

        if ((piece.getPieceType() == PieceType.red && numOfRedKilledPieces > 0 && !piece.isKilled()) ||
                (piece.getPieceType() == PieceType.white && numOfWhiteKilledPieces > 0 && !piece.isKilled())) {
            return MoveType.None;
        }

        if (isInRedStockPileArea(newXPosition, newYPosition)) {
            System.out.println(distance);
            if (piece.getPieceType() == PieceType.red && numOfRedPiecesInBase == 15 && diceBoard.canMove(Math.abs(distance))) {
                return MoveType.StockPile;
            } else {
                return MoveType.None;
            }
        }

        if (isInWhiteStockPileArea(newXPosition, newYPosition)) {
            if (piece.getPieceType() == PieceType.white && numOfWhitePiecesInBase == 15 && diceBoard.canMove(Math.abs(distance))) {
                return MoveType.StockPile;
            } else {
                return MoveType.None;
            }
        }

        if (distance * piece.getPieceType().moveDirection < 0) {
            return MoveType.None;
        }

        if (triangles[newTriangleNumber].getTypeOfPieces() != piece.getPieceType() &&
                triangles[newTriangleNumber].getTypeOfPieces() != null) {
            if (triangles[newTriangleNumber].getNumberOfPieces() > 1) {
                return MoveType.None;
            }
            if (!diceBoard.canMove(Math.abs(distance))) {
                return MoveType.None;
            }
            return MoveType.Kill;
        } else {
            if (!diceBoard.canMove(Math.abs(distance))) {
                return MoveType.None;
            }
            return MoveType.Normal;
        }
    }

    public void updateNumOfPiecesInBase(Piece piece, int oldPlace, boolean isKilled) {
        if (isKilled) {
            if (piece.getPieceType() == PieceType.red && oldPlace <= 5) {
                numOfRedPiecesInBase--;
            }
            if (piece.getPieceType() == PieceType.white && oldPlace >= 18) {
                numOfWhitePiecesInBase--;
            }
            return;
        }

        if (piece.getPieceType() == PieceType.red && oldPlace > 5 && piece.getPlaceNumber() <= 5) {
            this.numOfRedPiecesInBase++;
        }
        if (piece.getPieceType() == PieceType.white && oldPlace < 18 && piece.getPlaceNumber() >= 18) {
            this.numOfWhitePiecesInBase++;
        }

    }

    public boolean isInRedStockPileArea(double newXPosition, double newYPosition) {
        if (newXPosition == 15 * triangleBase && newYPosition >= redStockpileY && newYPosition <= redStockpileY + stockpileHeight) {
            return true;
        }
        return false;
    }

    public boolean isInWhiteStockPileArea(double newXPosition, double newYPosition) {
        if (newXPosition == 15 * triangleBase && newYPosition >= whiteStockPileY && newYPosition <= whiteStockPileY + stockpileHeight) {
            return true;
        }
        return false;
    }

    public int findDistance(Piece piece, int newTriangleNumber, double newXPosition, double newYPosition) {
        int distance;
        if (isInRedStockPileArea(newXPosition, newYPosition)) {
            distance = -1 - piece.getPlaceNumber();
        } else if (isInWhiteStockPileArea(newXPosition, newYPosition)) {
            distance = 24 - piece.getPlaceNumber();
        } else if ((newYPosition > 5 * triangleBase && newYPosition < 7 * triangleBase) ||
                (newYPosition > 12 * triangleBase) ||
                (newYPosition < 0) ||
                (newXPosition < triangleBase) ||
                (newXPosition >= 14 * triangleBase) ||
                (newXPosition < 8 * triangleBase && newXPosition > 7 * triangleBase) ||
                (newXPosition == piece.getOldX())) {
            distance = 0;
        } else {
            distance = triangles[newTriangleNumber].getNumber() - piece.getPlaceNumber();

        }
        return distance;
    }
}

