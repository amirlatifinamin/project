package sample;

import static sample.LayoutCreator.*;


public class Controller {

    int numOfRedPiecesInBase = 5;
    int numOfWhitePiecesInBase = 5;

    public Piece makeHandlePiece(double x, double y, PieceType pieceType, int number, Graveyard graveyard, DiceBoard diceBoard) {
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
                    } else {
                        triangles[oldTriangleNumber].removePiece(piece);
                    }
                    piece.setPlaceNumber(triangles[newTriangleNumber].getNumber());
                    updateNumOfPiecesInBase(piece, oldPlace, false);
                    piece.move(newXPosition, newYPosition);
                    break;
                }
                case kill: {
                    Piece killedPiece = triangles[newTriangleNumber].getPieces().get(0);
                    killedPiece.setPlaceNumber(killedPiece.getPieceType() == pieceType.white ? -1 : 24);
                    killedPiece.setKilled(true);
                    updateNumOfPiecesInBase(piece, 0, true);
                    if(killedPiece.getPieceType() == PieceType.red){

                    }
                    graveyard.addPiece(killedPiece);
                    triangles[newTriangleNumber].killPiece();
                    newYPosition = triangles[newTriangleNumber].findCoordinationOfNewPiece();
                    triangles[newTriangleNumber].addPiece(piece);
                    if (piece.isKilled()) {
                        graveyard.removePiece(piece);
                    } else {
                        triangles[oldTriangleNumber].removePiece(piece);
                    }
                    piece.setPlaceNumber(triangles[newTriangleNumber].getNumber());
                    updateNumOfPiecesInBase(piece, oldPlace, false);
                    piece.move(newXPosition, newYPosition);
                    break;
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

        if (isInRedStockPileArea(newXPosition, newYPosition)) {
            System.out.println("salam");
            if (piece.getPieceType() == PieceType.red && numOfRedPiecesInBase == 15) {

            }
            else {
                return MoveType.None;
            }
        }

        if (isInWhiteStockPileArea(newXPosition, newYPosition)) {
            System.out.println("salam");
            if (piece.getPieceType() == PieceType.white && numOfWhitePiecesInBase == 15) {

            }
            else {
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
            return MoveType.kill;
        } else {
            if (!diceBoard.canMove(Math.abs(distance))) {
                return MoveType.None;
            }
            return MoveType.Normal;
        }
    }

    public void updateNumOfPiecesInBase(Piece piece, int oldPlace, boolean isKilled) {
        if(isKilled){
            if(piece.getPieceType()==PieceType.red){
                numOfRedPiecesInBase--;
            }
            else {
                numOfWhitePiecesInBase--;
            }
            System.out.println(numOfRedPiecesInBase);
            return;
        }

        if (piece.getPieceType() == PieceType.red && oldPlace > 5 && piece.getPlaceNumber() <= 5) {
            this.numOfRedPiecesInBase++;
        }
        if (piece.getPieceType() == PieceType.white && oldPlace < 18 && piece.getPlaceNumber() >= 18) {
            this.numOfWhitePiecesInBase++;
        }
        System.out.println(numOfRedPiecesInBase);

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
                newYPosition > 12 * triangleBase ||
                newYPosition < 0 ||
                newXPosition < triangleBase ||
                newXPosition > 14 * triangleBase ||
                (newXPosition < 8 * triangleBase && newXPosition > 7 * triangleBase) ||
                (newXPosition == piece.getOldX())) {
            distance = 0;
        } else {
            distance = triangles[newTriangleNumber].getNumber() - piece.getPlaceNumber();

        }
        return distance;
    }
}

