package items;

import core.*;

public class Sword extends Item{

    public Sword(){
        super();
        this.usage().take(Usage.Take.TAKABLE);
        this.setName("Elvish Sword");
        this.addSynonym("elvish sword");
        this.addSynonym("sword");
    }

    @Override
    public void interact(final Command command, final Context context){

    }
}
