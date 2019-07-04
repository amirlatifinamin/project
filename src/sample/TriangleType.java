package sample;

public enum TriangleType {
    upRed(1),upWhite(1),downRed(-1),downWhite(-1);
    int direction;

    TriangleType(int direction){
        this.direction = direction;
    }
}
