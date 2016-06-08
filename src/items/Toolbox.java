package items;

import core.*;

public class Toolbox extends Item {

    public Toolbox() {
        super();
        this.usage()
            .take(Item.TAKABLE)
            .open(Item.CLOSED)
            .lock(Item.LOCKED);
        this.name("Toolbox")
            .synonym("toolbox")
            .description("A small red toolbox");
        this.key(KeyToolbox.class);
    }
    
    @Override
    public boolean interact(final Command command, final Context context) {
        if (command.getVerb().getTitle().equals("open")) {
            if (this.usage().lock() == Usage.Lock.UNLOCKED) {
                command.getVerb().run(command, context);
                System.out.println("The toolbox opens, revealing a shiny new "
                    + "screwdriver!");
                context.getPlayer().getCurrentArea().item(new Screwdriver());
            }
            return true;
        } else {
            return false;
        }
        
    }
    
}