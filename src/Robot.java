public class Robot {
    int x = 0;
    int y = 0;
    int f = 0;

    public Integer getX() {
        return x;
    }

    public void setX(Integer newX) {
        this.x = newX;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer newY) {
        this.y = newY;
    }

    //for face, 0 is north; 1 is east; 2 is south; 3 is west;
    public Integer getF(){
        return f;
    }

    public void setF(Integer newF){
        this.f = newF;
    }
}
