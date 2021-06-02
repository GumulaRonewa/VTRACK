package sample;

public class disk {
    private int diskNumber; //disk number helps keep track of the number of identified disks
    private double radius; //radius of the disk
    private int radiusClass; //which class this disk has been identified as from its radius for statistics
    private coordinates centre; //centre of the disk
    //trace pixels possibly

    public disk(double rad, int num,coordinates cen){
        diskNumber=num;
        radius=rad;
        centre=cen;
    }

    public double getRadius() {
        return radius;
    }

    public int getRadiusClass() {
        return radiusClass;
    }

    public int getDiskNumber() {
        return diskNumber;
    }

    public  coordinates getCentre(){return centre;}
}
