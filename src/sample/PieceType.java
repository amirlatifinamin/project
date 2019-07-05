package sample;

public enum PieceType {
    red(-1),white(1);
    int moveDirection;
    PieceType(int moveDirection){
        this.moveDirection = moveDirection;
    }
}
