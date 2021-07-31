
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
    static ArrayList<Robot> rar = new ArrayList<Robot>();
    static ArrayList<String> commands = new ArrayList<String>();
    static ArrayList<String> validCommands = new ArrayList<String>();

    public static void main(String[] args) throws Exception {
        
        try{
            File file = new File("src/input.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                commands.add(scanner.nextLine());
            }
            scanner.close();
        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    
       //get rid of all invalid commands
        for(int i =0;i<commands.size();i++){
            if(commands.get(i).startsWith("PLACE") && commands.get(i).length() > 13){
                if(validation(commands.get(i))){ 
                    validCommands.add(commands.get(i));
                }
            }
            else if((commands.get(i).equals("MOVE") || commands.get(i).equals("LEFT") || commands.get(i).equals("RIGHT") || commands.get(i).equals("REPORT")) && validCommands.size()>0){
                validCommands.add(commands.get(i));
            }
        }
       
        //make sure validCommands is not null
        if(validCommands.size() == 0){
            System.out.println("no valid commands.");
        }else{
            setRobot(validCommands.get(0));
        
            for(int j = 1; j < validCommands.size(); j++){
                if(validCommands.get(j).startsWith("PLACE")){
                    setRobot(validCommands.get(j));
                    continue;
                }else if(validCommands.get(j).equals("REPORT")){
                    try{
                        FileWriter fw = new FileWriter("src/output.txt");
                        for(int k = 0;k<rar.size();k++){
                            String face = "face";
                            switch(rar.get(k).getF()){
                                case 0:
                                    face = "NORTH";
                                    break;
                                case 1:
                                    face = "EAST";
                                    break;
                                case 2:
                                    face = "SOUTH";
                                    break;
                                case 3:
                                    face = "WEST";
                                    break;
                                    default:
                                    System.out.println("transit error");
                            }
                            fw.write(rar.get(k).getX() + "," + rar.get(k).getY() + "," + face + "\r\n");
                            
                        }
                        fw.close();
                        System.out.println("Successfully wrote to file.");
                    } catch(IOException e){
                        System.out.println("print error");
                        e.printStackTrace();
                    }
                }
                else{
                    System.out.println("rar size is " + rar.size());
                    moveRobot(rar.get(rar.size()-1),validCommands.get(j));
                }
            }
                    
        }

        
        
    }

    //check if there is any typo in PLACE command
    public static Boolean validation(String command){
        Boolean isPlace = false;
            //check if X,Y is valid
            if(Character.isDigit(command.charAt(6)) && Character.isDigit(command.charAt(8))){
                int x = Character.getNumericValue(command.charAt(6));
                int y = Character.getNumericValue(command.charAt(8));
                //check if robot is place on table
                if(0 <= x && x <=4 && 0 <=y && y<=4){
                    //check if face value is valid.
                    String face = command.substring(10);
                    if(face.equals("NORTH") || face.equals("WEST") || face.equals("EAST") || face.equals("SOUTH")){
                        isPlace = true;
                    }
                }
            }
        return isPlace;
    }

    public static void moveRobot(Robot r, String c){
        System.out.println("command is: " + c);
        switch(c){
            case "MOVE":
                switch(r.getF()){
                    case 0:
                        if(r.getY()<5){
                            r.setY(r.getY()+1);
                        }
                        break;
                    case 1:
                        if(r.getX()<5){
                            r.setX(r.getX()+1);
                        }
                        break;
                    case 2:
                        if(r.getY()>0){
                            r.setY(r.getY()-1);
                        }
                        break;
                    case 3:
                        if(r.getX()>0){
                            r.setX(r.getX()-1);
                           
                        }
                        break;
                        default:
                        System.out.println("move error");
                }
                break;
            case "LEFT":
                if(r.getF() == 0){
                    r.setF(3);
                }else{
                    r.setF(r.getF()-1);
                }
                break;
            case "RIGHT":
                if(r.getF() == 3){
                    r.setF(0);
                }else{
                    r.setF(r.getF()+1);
                }
                break;
            
            default:
            System.out.println("move robot error");
            
        }
        
        
        
    }

    public static void setRobot(String c){
        Robot robot = new Robot();
       
        robot.setX(Character.getNumericValue(c.charAt(6)));
        robot.setY(Character.getNumericValue(c.charAt(8)));
       
            switch(c.substring(10)){
                case "NORTH":
                    robot.setF(0);
                    break;
                case "EAST":
                    robot.setF(1);
                    break;
                case "SOUTH":
                    robot.setF(2);
                    break;
                case "WEST":
                    robot.setF(3);
                    break;
                default:
                System.out.println("invalid face");
            }
        rar.add(robot);
        System.out.println("robot placed.");
    }

}
