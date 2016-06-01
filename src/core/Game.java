package core;
import java.util.ArrayList;
import verbs.*;
import areas.*;
import items.*;
import java.util.Random;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author coons5457w & pedro & Samantha
 */


public class Game {
    //Gameplay statuses
    public static final int KEEP_PLAYING = 0;
    public static final int SELF_QUIT = 1;
    public static final int WIN = 2;
    public static final int DIE = 3;
    public static final int SUICIDE = 4;
    
    public ArrayList<Verb> verbList = new ArrayList<Verb>();
    
    public static void main(String[] args){
        //self explanatory initialization
        Game game = new Game();
        Player player = new Player(10,"Carlton");
        World world = new World();
        
        
        //Register all verbs in the verbList
        
        //When a verb is updated to the new verb scheme, add  /**/ to show completion
        
        /**/game.verbList.add(new Climb());             
        /**/game.verbList.add(new Close());
        /**/game.verbList.add(new Credits());
        /**/game.verbList.add(new Curse());
        game.verbList.add(new Diagnostic());
        game.verbList.add(new Drink());
        game.verbList.add(new Drop());
        game.verbList.add(new Eat());
        game.verbList.add(new Examine());
        game.verbList.add(new Give());
        game.verbList.add(new Hello());
        game.verbList.add(new Hit());
        /**/game.verbList.add(new Inventory());
        game.verbList.add(new Listen());
        game.verbList.add(new Lock());
        /**/game.verbList.add(new Look());
        /**/game.verbList.add(new Move());
        /**/game.verbList.add(new Open());
        game.verbList.add(new Poke());
        game.verbList.add(new Pray());
        game.verbList.add(new Quit());
        game.verbList.add(new Read());
        game.verbList.add(new Score());
        game.verbList.add(new Shout());
        game.verbList.add(new Smell());
        game.verbList.add(new Stab());
        game.verbList.add(new Suicide());
        /**/game.verbList.add(new Take());
        game.verbList.add(new Taste());
        game.verbList.add(new TurnOff());
        game.verbList.add(new TurnOn());
        game.verbList.add(new Unlock());
        
        //Add all Areas to the new world
        world.addArea("Test1",new Test1(world));
        world.addArea("Test2",new Test2(world));
        world.addArea("Test3",new Test3(world));
        world.addArea("Test4",new Test4(world));
        world.addArea("Test5",new Test5(world));
        world.addArea("Test6",new Test6(world));
        world.addArea("Test7",new Test7(world));
        world.addArea("Test8",new Test8(world));
        world.addArea("Test9",new Test9(world));
        world.addArea("Test10",new Test10(world));
        
        //Setting initial area for player
        player.setCurrentArea(world.getArea("Test5"));
        player.addItem(new NoTea());
        PlayerConstruct constr = new PlayerConstruct(player, world);
        Command com;
        
        //Fun printed start stuff
        System.out.println("Welcome to\n" +
            "d8888888P                    a88888b. dP     dP\n" +
            "     .d8'                   d8'   `88 88   .d8'\n" +
            "   .d8'   .d8888b. 88d888b. 88        88aaa8P' \n" +
            " .d8'     88'  `88 88'  `88 88        88   `8b.\n" +
            "d8'       88.  .88 88       Y8.   .88 88     88\n" +
            "Y8888888P `88888P' dP        Y88888P' dP     dP\n\n");
        
        //'enter' first room to get things started
        player.getCurrentArea().enter(player);
        
        //Initial prompt setup
        Scanner reader = new Scanner(System.in);
        String input = "";
        
        //Main Game Loop
        int status = KEEP_PLAYING;
        while(status == KEEP_PLAYING){
            System.out.println("You are in room " 
                    + player.getCurrentArea().getTitle());
            
            System.out.print(">");
            input = reader.nextLine();
            System.out.println("");
            if(game.findDirection(input) != -1)
                player.getCurrentArea().interact(new Command(new Move(), null,
                        game.findDirection(input)),constr);
            //code to test parsers
            /*else if(game.verbParser(input) != null){
                System.out.println(game.verbParser(input).getTitle());
                if(game.verbParser(input).getUsageKey(1)){
                    if(game.nounParser(input,player) != null){
                        if(!game.nounParser(input,player).getName().equals("noItem"))
                            System.out.println(game.nounParser(input,player).getName());
                        else if(!game.verbParser(input).getUsageKey(0))
                            System.out.println("Ya need a noun, ya dingus");
                    }else{
                        System.out.println("Where do you expect to find one of those?");
                    }
                }
            }*/
            else if(game.verbParser(input) != null){
                //String verb = game.verbParser(input).getTitle();
                Verb verb = game.verbParser(input);
                boolean conflict = false;
                Item noun = null;
                Item[] nounArray = game.nounParser(input,player);
                if(nounArray != null){
                    if(nounArray.length == 1) noun = nounArray[0];
                    else conflict = true;
                }
                if(!conflict){
                    int direction = game.directionParser(input);
                    com = new Command(verb, noun, direction);
                    player.getCurrentArea().interact(com,constr);
                    
                    
                    
                    
                    
                    
                    /*if(verb.equals("damn")){
                        game.curse();
                    }else if(verb.equals("take")){
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.take(noun,player);
                            }
                            else System.out.println("Ya need a noun, ya dingus");
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }else if(verb.equals("look")){ game.look(player); }
                    else if(verb.equals("move")){
                        if(direction != -1) game.move(direction,player,world);
                        else System.out.println("Ya need a direction, ya dingus");
                    }else if(verb.equals("inventory")){ game.inventory(player); }
                    else if(verb.equals("examine")){
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.examine(noun);
                            }
                            else System.out.println("Ya need a noun, ya dingus");
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }else if(verb.equals("climb")){
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.climb(noun,player,world);
                            }
                            else game.climb(null,player,world);
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }else if(verb.equals("quit")){
                        status = game.quit();
                    }else if(verb.equals("score")){ game.score(player); }
                    else if(verb.equals("diagnostic")){ game.diagnostic(player); }
                    else if(verb.equals("eat")){
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.eat(noun,player);
                            }
                            else System.out.println("Ya need a noun, ya dingus");
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }else if(verb.equals("taste")){
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.taste(noun);
                            }
                            else game.taste(player.getCurrentArea());
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }else if(verb.equals("smell")){
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.smell(noun);
                            }
                            else game.smell(player.getCurrentArea());
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }else if(verb.equals("shout")){ game.shout(); }
                    else if(verb.equals("hello")){ game.hello(); }
                    else if(verb.equals("credits")){ game.credits(); }
                    else if(verb.equals("open")){
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.open(noun);
                            }
                            else System.out.println("Ya need a noun, ya dingus");
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }
                    else if(verb.equals("close")){
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.close(noun);
                            }
                            else System.out.println("Ya need a noun, ya dingus");
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }
                    else if(verb.equals("read")){
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.read(noun);
                            }
                            else System.out.println("Ya need a noun, ya dingus");
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }else if(verb.equals("drop")){
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.drop(noun,player);
                            }
                            else System.out.println("Ya need a noun, ya dingus");
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }else if(verb.equals("turn on")){
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.turnOn(noun,player);
                            }
                            else System.out.println("Ya need a noun, ya dingus");
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }else if(verb.equals("turn off")){
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.turnOff(noun);
                            }
                            else System.out.println("Ya need a noun, ya dingus");
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }else if(verb.equals("poke")){ 
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.poke(noun);
                            }
                            else System.out.println("Ya need a noun, ya dingus");
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }else if(verb.equals("listen")){
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.listen(noun);
                            }
                            else game.listen(player.getCurrentArea());
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }else if(verb.equals("drink")){
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.drink(noun,player);
                            }
                            else System.out.println("Ya need a noun, ya dingus");
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }else if(verb.equals("suicide")){ status = game.suicide(player); }
                    else if(verb.equals("hit")){
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.hit(noun,player.getCurrentArea());
                            }
                            else System.out.println("Ya need a noun, ya dingus");
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }
                    else if(verb.equals("stab")){
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.stab(noun,player.getCurrentArea(),player, new Sword());
                            }
                            else System.out.println("Ya need a noun, ya dingus");
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }else if(verb.equals("pray")){ game.pray(player); }
                    else if(verb.equals("lock")){
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.lock(noun, player, world);
                            }
                            else System.out.println("Ya need a noun, ya dingus");
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }else if(verb.equals("unlock")){
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.unlock(noun, player, world);
                            }
                            else System.out.println("Ya need a noun, ya dingus");
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }else if(verb.equals("give")){
                        if(noun != null){
                            if(!noun.getName().equals("noItem")){
                                game.give(noun, player);
                            }
                            else System.out.println("Ya need a noun, ya dingus");
                        }else{
                            System.out.println("Where do you expect to find one of those?");
                        }
                    }*/
                }else{
                    if(nounArray.length > 3){
                        System.out.print("Did you mean the ");
                        for(int i = 0; i < nounArray.length - 1; i++){
                            System.out.print(nounArray[i].getName() + ", the ");
                        }
                        System.out.println(", or the " 
                                + nounArray[nounArray.length - 1].getName() + "?");
                    }else{
                        System.out.print("Did you mean the " + nounArray[0].getName() +
                                " or the " + nounArray[1].getName() + "?");
                    }
                }
                
            }
            System.out.println("");
        }
        game.score(player);
        System.out.println("");
    }
   
    
    public Verb findVerb(String input){
        for(Verb item : this.verbList){
            if(item.hasMatching(input)) return item;
        }
        return null;
    }
    
    public Item[] findNoun(String input, Player player){
        ArrayList<Item> itemList = new ArrayList<Item>();
        for(int i = 0; i < player.listInventory().length; i++){
            itemList.add(player.listInventory()[i]);
        }
        for(int i = 0; i < player.getCurrentArea().listItems().length; i++){
            itemList.add(player.getCurrentArea().listItems()[i]);
        }
        ArrayList<Item> returns = new ArrayList<Item>();
        for(int i = 0; i < player.listInventory().length + 
                player.getCurrentArea().listItems().length; i++){
            if(i<player.listInventory().length){
                if(player.listInventory()[i].hasMatching(input))
                    returns.add(player.listInventory()[i]);
            }
            else{
                if(player.getCurrentArea().listItems()
                        [i-player.listInventory().length].hasMatching(input)) 
                    returns.add(player.getCurrentArea().listItems()
                            [i - player.listInventory().length]);
            }
        }
        Item[] itemArray = new Item[returns.size()];
        if(returns.size() > 0) return returns.toArray(itemArray);
        return null;
    }
    
    public int findDirection(String input){
        switch (input) {
            case "north":
            case "n":
                return World.NORTH;
            case "east":
            case "e":
                return World.EAST;
            case "south":
            case "s":
                return World.SOUTH;
            case "west":
            case "w":
                return World.WEST;
            case "northeast":
            case "ne":
                return World.NORTHEAST;
            case "southeast":
            case "se":
                return World.SOUTHEAST;
            case "southwest":
            case "sw":
                return World.SOUTHWEST;
            case "northwest":
            case "nw":
                return World.NORTHWEST;
            case "up":
            case "u":
                return World.UP;
            case "down":
            case "d":
                return World.DOWN;
            default:
                return -1;
        }
    }
    
    public Verb verbParser(String input){
        input = input.toLowerCase();
        int s = 1;
        String verb = "";
        for(int i = 0; i < input.length(); i++){
            if(input.substring(i,i+1).equals(" ")) s++;
        }
        String[] inputArray = new String[s];
        int n = 0;
        for (String retval: input.split(" ")){
            inputArray[n] = retval;
            n++;
        }
        for(int i = inputArray.length; i > 0; i--){
            String verbTest = "";
            if(!inputArray[0].equals(null)) verbTest += inputArray[0];
            for(int j = 1; j < i; j++){
                verbTest += " ";
                verbTest += inputArray[j];
            }
            if(findVerb(verbTest) != null){
                verb = verbTest;
                break;
            }
        }
        if(verb != null) return findVerb(verb);
        else return null;
    }
    
    public Item[] nounParser(String input, Player player){
        input = input.toLowerCase();
        int s = 1;
        int t = 0;
        String noun = "";
        for(int i = 0; i < input.length(); i++){
            if(input.substring(i,i+1).equals(" ")) s++;
        }
        String[] inputArray = new String[s];
        String[] inputArraySinVerbos = new String[0];
        int n = 0;
        for (String retval: input.split(" ")){
            inputArray[n] = retval;
            n++;
        }
        for(int i = inputArray.length; i > 0; i--){
            String verbTest = "";
            if(!inputArray[0].equals(null)) verbTest += inputArray[0];
            for(int j = 1; j < i; j++){
                verbTest += " ";
                verbTest += inputArray[j];
            }
            if(findVerb(verbTest) != null){
                t = i;
                inputArraySinVerbos = new String[inputArray.length - t];
                break;
            }
        }
        if(inputArraySinVerbos.length == 0){
            return new Item[]{new NoItem()};
        }
        for(int i = 0; i < inputArraySinVerbos.length; i++){
            inputArraySinVerbos[i] = inputArray[i + t];
        }
        for(int i = inputArraySinVerbos.length; i > 0; i--){
            String nounTest = "";
            if(!inputArraySinVerbos[0].equals(null)) nounTest 
                    += inputArraySinVerbos[0];
            for(int j = 1; j < i; j++){
                nounTest += " ";
                nounTest += inputArraySinVerbos[j];
            }
            if(findNoun(nounTest, player) != null){
                noun = nounTest;
                break;
            }
        }
        Item[] nounArray = new Item[0];
        if(findNoun(noun, player) != null){
            nounArray = new Item[findNoun(noun, player).length];
            for(int i = 0; i < findNoun(noun, player).length; i++){
                nounArray[i] = findNoun(noun, player)[i];
            }
        }
        if(nounArray.length > 0) return nounArray;
        else return null;
    }
    
    public int directionParser(String input){
        input = input.toLowerCase();
        
        //convert String input to List, then filter out nulls and verbs
        List<String> inputList = new ArrayList<>(Arrays.asList(input.split("\\s")));
        inputList.removeIf(i -> (i == null || findVerb(i) != null));
        
        if (inputList.isEmpty() || inputList.size() > 1) return -1;
        
        return findDirection(inputList.get(0)); 
    }
    
    public void examine(Item item){
        if(item.getDescription() != null)
            System.out.println(item.getDescription());
        else System.out.println("It looks like every other " + item.getName() + 
                " you've ever seen");
    }
    
    public int quit(){
        System.out.println("Goodbye!");
        return 1;
    }
    
    public void score(Player player){
        System.out.println("Your score is: " + player.getScore());
    }
    
    public void diagnostic(Player player){
        System.out.println("Your hp is: " + player.getHp() + "/" + 
                player.getMaxHp());
    }
    
    public void eat(Item item, Player player){
        if(item.getUsageKey(2) == 2){
            if(player.getCurrentArea().hasMatching(item)) 
                player.getCurrentArea().removeItem(item);
            else if(player.hasMatching(item)) player.removeItem(item);
            System.out.println(player.getName() + " ate the " + item.getName());
            if(item.getTaste() != null) System.out.println(item.getTaste());
            else System.out.println("It tastes like every other " + 
                    item.getName() + " you've ever eaten");
        }else{
            System.out.println("I don't see how you expect to do that!");
        }
    }
    
    public void drink(Item item, Player player){
        if(item.getUsageKey(3) == 2){
            if(player.getCurrentArea().hasMatching(item)) 
                player.getCurrentArea().removeItem(item);
            else if(player.hasMatching(item)) player.removeItem(item);
            System.out.println(player.getName() + " drank the " + item.getName());
            if(item.getTaste() != null) System.out.println(item.getTaste());
            else System.out.println("It tastes like every other " + 
                    item.getName() + " you've ever drank");
        }else if(item.getUsageKey(3) == 4){
            System.out.println("You need to open it first!");
        }else{
            System.out.println("I don't see how you expect to do that!");
        }
    }
    
    public void taste(Item item){
        if(item.getTaste() != null) System.out.println(item.getTaste());
        else System.out.println("It tastes like every other " + 
                item.getName() + " you've ever tried");
    }
    
    public void taste(Area area){
        if(area.getTaste() != null) System.out.println(area.getTaste());
        else System.out.println("It doesn't taste like anything");
    }
    
    public void smell(Item item){
        if(item.getSmell() != null) System.out.println(item.getSmell());
        else System.out.println("It smells like every other " + 
                item.getName());
    }
    
    public void smell(Area area){
        if(area.getSmell() != null) System.out.println(area.getSmell());
        else System.out.println("It doesn't smell like anything");
    }
    
    public void listen(Item item){
        if(item.getSound() != null) System.out.println(item.getSound());
        else System.out.println("It sounds like every other " + 
                item.getName() + " you've ever heard");
    }
    
    public void listen(Area area){
        if(area.getSound() != null) System.out.println(area.getSound());
        else System.out.println("It doesn't sound like anything");
    }
    
    public void shout(){
        System.out.println("Aaaarrrrrrrrgggggggggggggghhhhhhhhhhhhhh!");
    }
    
    public void hello(){
        Random rand = new Random();
        int n = rand.nextInt(3);
        if(n == 0) System.out.println("Good day");
        else if(n == 1) System.out.println("Hello");
        else System.out.println("Nice weather we've been having lately");
    }
    
    public void open(Item item){
        if(item.getUsageKey(4) == 2){
            if(item.getUsageKey(5) != 3){
                item.setUsageKey(4,3);
                System.out.println("You opened the " + item.getName());
            }else{
                System.out.println(item.getName() + " is locked");
            }
        }else if(item.getUsageKey(4) == 3){
            System.out.println(item.getName() + " is already open");
        }else{
            System.out.println("I don't see how you expect to do that");
        }
    }
    
    public void close(Item item){
        if(item.getUsageKey(4) == 3){
            item.setUsageKey(4,2);
            System.out.println("You closed the " + item.getName());
        }else if(item.getUsageKey(4) == 2){
            System.out.println(item.getName() + " is already closed");
        }else{
            System.out.println("I don't see how you expect to do that");
        }
    }
    
    public void turnOn(Item item,Player player){
        if(item.getUsageKey(7) == 2){
            item.setUsageKey(7,3);
            System.out.println("You turned on the " + item.getName());
            player.currentArea.enter(player);
        }else if(item.getUsageKey(7) == 3){
            System.out.println(item.getName() + " is already on");
        }else{
            System.out.println("I don't see how you expect to do that");
        }
    }
    
    public void turnOff(Item item){
        if(item.getUsageKey(7) == 3){
            item.setUsageKey(7,2);
            System.out.println("You turned off the " + item.getName());
        }else if(item.getUsageKey(7) == 2){
            System.out.println(item.getName() + " is already off");
        }else{
            System.out.println("I don't see how you expect to do that");
        }
    }
    
    public void read(Item item){
        if(item.getUsageKey(6) == 2){
            if(item.getText() != null) System.out.println(item.getText());
            else System.out.println("It says nothing important");
        }else if(item.getUsageKey(6) == 3){
            System.out.println("You can't quite make it out");
        }else{
            System.out.println("I don't see how you expect to do that");
        }
    }
    
    public void drop(Item item, Player player){
        if(player.hasMatching(item)){
            if(item.getUsageKey(1) == 1){
                player.removeItem(item);
                player.getCurrentArea().addItem(item);
                System.out.println("You dropped " + item.getName());
            }else{
                System.out.println("I'm sorry " + player.getName() + 
                        ", I can't allow you to do that");
            }
        }else{
            System.out.println("You don't have the " + item.getName());
        }
    }
    
    public void poke(Item item){
        System.out.println("You poked the " + item.getName());
        System.out.println("Good Job!");
    }
    
    public int suicide(Player player){
        System.out.println("Whelp, goodbye, I guess");
        System.out.println(player.getName() + " commited suicide");
        return 4;
    }
    
    public void hit(Item hitee, Area currentArea){
        if(hitee.getUsageKey(11) == 2){
            hitee.drop(currentArea);
            System.out.println("You hit the " + hitee.getName());
            if(hitee.getInside() != null) System.out.println("It dropped the " 
                    + hitee.getInside().getName());
            hitee.drop(currentArea);
            currentArea.removeItem(hitee);
        }else{
            System.out.println("Now why would you do that?");
        }
        
    }
    
    public void stab(Item stabee, Area currentArea, Player player, Item sword){
        boolean test = false;
        for(int i = 0; i < player.listInventory().length; i++){
            if(player.listInventory()[i].getName().equals(sword.getName())) 
                test = true;
        }
        if(test){
            if(stabee.getUsageKey(11) == 2 || stabee.getUsageKey(11) == 3){
                stabee.drop(currentArea);
                System.out.println("You stabbed the " + stabee.getName());
                if(stabee.getInside() != null) System.out.println("It dropped the " 
                        + stabee.getInside().getName());
                stabee.drop(currentArea);
                currentArea.removeItem(stabee);
            }else{
                System.out.println("Now why would you do that?");
            }
        }else{
            System.out.println("You need the " + sword.getName() 
                    + " to stab the " + stabee.getName());
        }
    }
    
    public void pray(Player player){
        player.setPrayer(true);
        System.out.println(player.getName() + " prayed!");
    }
    
    public void lock(Item item, Player player,World world){
        ArrayList<Item> keys = item.getKey();
        if(keys != null){
            boolean test = false;
            for(int i = 0; i < player.listInventory().length; i++){
                if(player.listInventory()[i].getName().equals(keys.get(0).getName())) 
                    test = true;
            }
            if(item.getUsageKey(5) == 2){
                if(test){
                    item.setUsageKey(5,3);
                    System.out.println("You locked the " + item.getName());
                    if(item.getPortal() != null)
                        item.getPortal().lock();
                    if(item.hasMatching("door"))
                        item.synchronizeDoor(world,player.getCurrentArea());
                }else{
                    System.out.println("You need a key for that");
                }
            }else if(item.getUsageKey(5) == 3){
                System.out.println("The " + item.getName() + " is already locked!");
            }
        }else{
            System.out.println("Now, how do you expect to do that?");
        }
    }
    
    public void unlock(Item item, Player player, World world){
        ArrayList<Item> keys = item.getKey();
        if(keys != null){
            boolean test = false;
            for(int i = 0; i < player.listInventory().length; i++){
                if(player.listInventory()[i].getName().equals(keys.get(0).getName())) 
                    test = true;
            }
            if(item.getUsageKey(5) == 3){
                if(test){
                    item.setUsageKey(5,2);
                    System.out.println("You unlocked the " + item.getName());
                    if(item.getPortal() != null)
                        item.getPortal().unlock();
                    if(item.hasMatching("door"))
                        item.synchronizeDoor(world,player.getCurrentArea());
                }else{
                    System.out.println("You need a key for that");
                }
            }else if(item.getUsageKey(5) == 2){
                System.out.println("The " + item.getName() + " is already unlocked!");
            }
        }else{
            System.out.println("Now, how do you expect to do that?");
        }
    }

    public void give(Item item, Player player){
        if(player.hasMatching(item)){
            Item receiver;
            receiver = null;
            for(int i = 0; i < player.getCurrentArea().listItems().length; i++){
                if(player.getCurrentArea().listItems()[i].getUsageKey(14) == 2){
                    receiver = player.getCurrentArea().listItems()[i];
                    break;
                }
            }
            if(receiver != null){
                receiver.receive(item);
                player.removeItem(item);
                System.out.println("You gave the " + item.getName() + " to "
                + receiver.getName());
            }else{
                System.out.println("I don't see who you'd want to give that to");
            }
        }else{
            System.out.println("You don't even have a " + item.getName());
        }
    }
    
}
